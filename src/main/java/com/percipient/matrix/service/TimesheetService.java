package com.percipient.matrix.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.percipient.matrix.dao.EmployeeRepository;
import com.percipient.matrix.dao.TimesheetRepository;
import com.percipient.matrix.domain.Employee;
import com.percipient.matrix.domain.Timesheet;
import com.percipient.matrix.domain.TimesheetItem;
import com.percipient.matrix.session.UserInfo;
import com.percipient.matrix.util.DateUtil;
import com.percipient.matrix.util.HibernateUtil;
import com.percipient.matrix.view.HrTimesheetView;
import com.percipient.matrix.view.TSCostCenterView;
import com.percipient.matrix.view.TimesheetItemView;
import com.percipient.matrix.view.TimesheetView;

public interface TimesheetService {

    public List<TimesheetView> getTimesheetPreview();

    public TimesheetView getTimesheet(Date weekEnding);

    public List<TimesheetView> getTimesheets();

    public TimesheetView createTimesheet(Date asDate);

    public void saveTimesheet(TimesheetView timesheetView);

    public void deleteTimesheet(Integer timesheetId);

    public TimesheetView addCostCodeRow(Integer timesheetId);

    public void deleteCostCodeRow(Integer timesheetId, String costCode);

    public TimesheetView getTimesheet(Integer timesheetId);

    // HR Timesheet Management functions

    public List<HrTimesheetView> getTimesheetsByStatus(String status);

}

@Service
class TimesheetServiceImpl implements TimesheetService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    private UserInfo userInfo;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    HibernateUtil hibernateUtil;

    @Transactional
    public List<HrTimesheetView> getTimesheetsByStatus(String status) {

        List<Timesheet> timesheets = timesheetRepository
                .getTimesheetsByStatus(status);
        List<HrTimesheetView> hrTimesheets = getHrTimesheetViewListFromTimesheetList(timesheets);
        return hrTimesheets;
    }

    private List<HrTimesheetView> getHrTimesheetViewListFromTimesheetList(
            List<Timesheet> timesheets) {
        List<HrTimesheetView> hrTimesheetViewList = new ArrayList<HrTimesheetView>();
        HrTimesheetView hrTimesheetView;
        for (Timesheet timesheet : timesheets) {
            hrTimesheetView = new HrTimesheetView();
            hrTimesheetView.setTimesheetId(timesheet.getId());
            hrTimesheetView.setStatus(StringUtils.capitalize(timesheet
                    .getStatus()));
            hrTimesheetView.setHours(getTotalHours(timesheet));
            hrTimesheetView.setWeekEnding(dateUtil.getAsString(timesheet
                    .getWeekEnding()));
            Employee employee = employeeRepository.getEmployee(timesheet
                    .getEmployeeId());
            hrTimesheetView.setEmployeeId(timesheet.getEmployeeId());
            hrTimesheetView.setEmployeeName(employee.getFirstName() + " "
                    + employee.getLastName());
            hrTimesheetViewList.add(hrTimesheetView);
        }
        return hrTimesheetViewList;
    }

    private Double getTotalHours(Timesheet timesheet) {
        Double totalHours = 0.0;
        for (TimesheetItem item : timesheet.getTimesheetItems()) {
            totalHours += item.getHours();
        }
        return totalHours;
    }

    @Transactional
    public List<TimesheetView> getTimesheetPreview() {

        Employee employee = employeeRepository.getEmployeeByUserName(userInfo
                .getUserName());
        List<Timesheet> timesheetList = timesheetRepository
                .getTimesheets(employee);
        Collections.sort(timesheetList, DateComparator);
        List<TimesheetView> timesheetViewList = new ArrayList<TimesheetView>();

        if (timesheetList.size() == 0 || !hasCurrentWeekEnding(timesheetList)) {
            TimesheetView tsv = createTimesheet(dateUtil
                    .getCurrentWeekEndingDate());
            timesheetViewList.add(tsv);
        }

        for (Timesheet timesheet : timesheetList) {
            timesheetViewList.add(getTimeSheetView(timesheet));
        }

        return timesheetViewList;
    }

    private boolean hasCurrentWeekEnding(List<Timesheet> timesheetList) {
        Date weekEndDate = dateUtil.getCurrentWeekEndingDate();
        for (Timesheet ts : timesheetList) {
            if (weekEndDate.equals(ts.getWeekEnding())) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public TimesheetView getTimesheet(Integer timesheetId) {
        Timesheet timesheet = timesheetRepository.getTimesheet(timesheetId);
        TimesheetView timesheetView = null;
        if (timesheet != null) {
            timesheetView = getTimeSheetView(timesheet);
        } else {
            timesheetView = createTimesheet(dateUtil.getCurrentWeekEndingDate());
        }
        return timesheetView;
    }

    @Override
    @Transactional
    public TimesheetView getTimesheet(Date weekEnding) {

        Employee employee = employeeRepository.getEmployeeByUserName(userInfo
                .getUserName());
        Timesheet timesheet = timesheetRepository.getTimesheet(employee,
                weekEnding);
        TimesheetView timesheetView = null;
        if (timesheet != null) {
            timesheetView = getTimeSheetView(timesheet);
        } else {
            timesheetView = createTimesheet(weekEnding);
        }
        return timesheetView;
    }

    @Transactional
    public List<TimesheetView> getTimesheets() {

        Employee employee = employeeRepository.getEmployeeByUserName(userInfo
                .getUserName());
        List<Timesheet> timesheetList = timesheetRepository
                .getTimesheets(employee);

        List<TimesheetView> timesheetViewList = new ArrayList<TimesheetView>();
        if (timesheetList.size() == 0 || !hasCurrentWeekEnding(timesheetList)) {
            TimesheetView tsv = createTimesheet(dateUtil
                    .getCurrentWeekEndingDate());
            timesheetViewList.add(tsv);
        } else {
            for (Timesheet timesheet : timesheetList) {
                TimesheetView timesheetView = getTimeSheetPreview(timesheet);
                timesheetViewList.add(timesheetView);
            }
        }

        return timesheetViewList;
    }

    @Override
    @Transactional
    public void saveTimesheet(TimesheetView timesheetView) {
        Timesheet ts = getTimesheetFromView(timesheetView);
        timesheetRepository.save(ts);
    }

    @Override
    @Transactional
    public void deleteTimesheet(Integer timesheetId) {
        Timesheet timesheet = timesheetRepository.getTimesheet(timesheetId);

        // TODO delete any attachments for this timesheet
        timesheetRepository.delete(timesheet);
    }

    @Override
    @Transactional
    public TimesheetView createTimesheet(Date weekEndingDate) {
        TimesheetView timesheetView = new TimesheetView();
        timesheetView.setWeekEnding(dateUtil.getAsString(weekEndingDate));
        timesheetView.setStatus("pending");

        List<TSCostCenterView> tsCCViews = new ArrayList<TSCostCenterView>();
        TSCostCenterView blankTSCCView = addTSCostCenterView(weekEndingDate);
        tsCCViews.add(blankTSCCView);
        timesheetView.setTsCostCenters(tsCCViews);

        return timesheetView;
    }

    @Override
    @Transactional
    public TimesheetView addCostCodeRow(Integer timesheetId) {
        TimesheetView timesheetView;
        Date weekEnding;
        if (null == timesheetId) {
            timesheetView = new TimesheetView();
            timesheetView.setTsCostCenters(new ArrayList<TSCostCenterView>());
            weekEnding = dateUtil.getCurrentWeekEndingDate();
        } else {
            Timesheet timesheet = timesheetRepository.getTimesheet(timesheetId);
            timesheetView = getTimeSheetView(timesheet);
            weekEnding = timesheet.getWeekEnding();
        }
        timesheetView.setWeekEnding(dateUtil.getAsString(weekEnding));
        TSCostCenterView blankTSCCView = addTSCostCenterView(weekEnding);
        timesheetView.getTsCostCenters().add(blankTSCCView);
        return timesheetView;
    }

    @Override
    @Transactional
    public void deleteCostCodeRow(Integer timesheetId, String costCode) {
        if (null == timesheetId || null == costCode) {
            return;
        }
        Timesheet timesheet = timesheetRepository.getTimesheet(timesheetId);
        Set<TimesheetItem> tsItemSet = timesheet.getTimesheetItems();
        Set<TimesheetItem> tmpItemSet = new HashSet<TimesheetItem>();
        for (TimesheetItem tsItem : tsItemSet) {
            if (tsItem.getCostCode().equalsIgnoreCase(costCode)) {
                tmpItemSet.add(tsItem);
            }
        }
        // removes relationship
        timesheet.getTimesheetItems().removeAll(tmpItemSet);
        // removes the child entities
        timesheetRepository.deleteTimesheetItems(tmpItemSet);
    }

    private Timesheet getTimesheetFromView(TimesheetView timesheetView) {

        Timesheet timesheet;
        String status = "pending";
        if (timesheetView.getId() != null) {
            timesheet = timesheetRepository.getTimesheet(timesheetView.getId());
            if (StringUtils.isNotBlank(timesheetView.getStatus())) {
                status = timesheetView.getStatus();
            }
            
        } else {
            timesheet = new Timesheet();
            Employee employee = employeeRepository
                    .getEmployeeByUserName(userInfo.getUserName());
            timesheet.setEmployeeId(employee.getId());
            timesheet.setWeekEnding(dateUtil.getAsDate(timesheetView
                    .getWeekEnding()));
        }
        timesheet.setStatus(status.toLowerCase());
        timesheet
                .setTimesheetItems(getTimesheetItems(timesheetView, timesheet));

        return timesheet;
    }

    private Set<TimesheetItem> getTimesheetItems(TimesheetView timesheetView,
            Timesheet timesheet) {
        Set<TimesheetItem> tsItems = new HashSet<TimesheetItem>();
        for (TSCostCenterView tsCCview : timesheetView.getTsCostCenters()) {
            for (TimesheetItemView tsItemview : tsCCview.getTimesheetItems()) {
                TimesheetItem item;
                if (tsItemview.getId() != null) {
                    item = timesheetRepository.getTimesheetItem(tsItemview
                            .getId());
                } else {
                    item = new TimesheetItem();
                }
                item.setId(tsItemview.getId());
                item.setCostCode(tsCCview.getCostCode());
                item.setHours(tsItemview.getHours());
                item.setDate(dateUtil.getAsDate(tsItemview.getDate()));
                item.setTimesheet(timesheet);
                tsItems.add(item);
            }
        }
        return tsItems;
    }

    private TimesheetView getTimeSheetPreview(Timesheet timesheet) {
        TimesheetView timesheetView = new TimesheetView();
        timesheetView.setId(timesheet.getId());
        timesheetView.setStatus(timesheet.getStatus());
        timesheetView.setWeekEnding(dateUtil.getAsString(timesheet
                .getWeekEnding()));
        return timesheetView;
    }

    private TimesheetView getTimeSheetView(Timesheet timesheet) {
        TimesheetView timesheetView = new TimesheetView();
        timesheetView.setId(timesheet.getId());
        timesheetView.setStatus(timesheet.getStatus());
        timesheetView.setWeekEnding(dateUtil.getAsString(timesheet
                .getWeekEnding()));
        List<TSCostCenterView> tsCCViewList = getTSCostCenterView(timesheet);
        // sort rows by cost code ascending
        Collections.sort(tsCCViewList, TSItemCostCodeComparator);
        timesheetView.setTsCostCenters(tsCCViewList);

        return timesheetView;
    }

    private List<TSCostCenterView> getTSCostCenterView(Timesheet timesheet) {
        List<TSCostCenterView> tsCCViewList = new ArrayList<TSCostCenterView>();
        Map<String, List<TimesheetItemView>> costCodeTimesheetItemsMap = new HashMap<String, List<TimesheetItemView>>();
        for (TimesheetItem tsItem : timesheet.getTimesheetItems()) {
            if (costCodeTimesheetItemsMap.containsKey(tsItem.getCostCode())) {
                costCodeTimesheetItemsMap.get(tsItem.getCostCode()).add(
                        getTimeSheetItemView(tsItem));
            } else {
                List<TimesheetItemView> tsItemsList = new ArrayList<TimesheetItemView>();
                tsItemsList.add(getTimeSheetItemView(tsItem));
                costCodeTimesheetItemsMap
                        .put(tsItem.getCostCode(), tsItemsList);
            }
        }

        for (Map.Entry<String, List<TimesheetItemView>> tsCostCenter : costCodeTimesheetItemsMap
                .entrySet()) {
            TSCostCenterView tsCCView = new TSCostCenterView();
            tsCCView.setCostCode(tsCostCenter.getKey());
            for (TimesheetItemView tsItemView : tsCostCenter.getValue()) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateUtil.getAsDate(tsItemView.getDate()));
                populateTSCCViewDayOfWeek(tsCCView, tsItemView, cal);
            }
            tsCCViewList.add(tsCCView);
        }

        return tsCCViewList;
    }

    private TimesheetItemView getTimeSheetItemView(TimesheetItem tsItem) {
        TimesheetItemView tsItemView = new TimesheetItemView();
        tsItemView.setId(tsItem.getId());
        tsItemView.setHours(tsItem.getHours());
        tsItemView.setDate(dateUtil.getAsString(tsItem.getDate()));
        tsItemView.setCostCode(tsItem.getCostCode());
        return tsItemView;
    }

    private TSCostCenterView addTSCostCenterView(Date date) {
        TSCostCenterView tsCCView = new TSCostCenterView();
        tsCCView.setCostCode(StringUtils.EMPTY);
        Iterator it = DateUtils.iterator(date, DateUtils.RANGE_WEEK_MONDAY);
        while (it.hasNext()) {
            Calendar cal = (Calendar) it.next();
            TimesheetItemView tsItemview = new TimesheetItemView();
            tsItemview.setHours(0.00);
            tsItemview.setDate(dateUtil.getAsString(cal.getTime()));
            populateTSCCViewDayOfWeek(tsCCView, tsItemview, cal);
        }
        return tsCCView;
    }

    private void populateTSCCViewDayOfWeek(TSCostCenterView tsCCView,
            TimesheetItemView tsItemview, Calendar cal) {

        String dayOfWeek = cal.getDisplayName(Calendar.DAY_OF_WEEK,
                Calendar.LONG, Locale.getDefault());

        if (dayOfWeek.equalsIgnoreCase("Monday")) {
            tsCCView.setMonday(tsItemview);
        } else if (dayOfWeek.equalsIgnoreCase("Tuesday")) {
            tsCCView.setTuesday(tsItemview);
        } else if (dayOfWeek.equalsIgnoreCase("Wednesday")) {
            tsCCView.setWednesday(tsItemview);
        } else if (dayOfWeek.equalsIgnoreCase("Thursday")) {
            tsCCView.setThursday(tsItemview);
        } else if (dayOfWeek.equalsIgnoreCase("Friday")) {
            tsCCView.setFriday(tsItemview);
        } else if (dayOfWeek.equalsIgnoreCase("Saturday")) {
            tsCCView.setSaturday(tsItemview);
        } else if (dayOfWeek.equalsIgnoreCase("Sunday")) {
            tsCCView.setSunday(tsItemview);
        }
    }

    private static Comparator<Timesheet> DateComparator = new Comparator<Timesheet>() {
        // reverse chronological
        @Override
        public int compare(Timesheet o1, Timesheet o2) {
            return o2.getWeekEnding().compareTo(o1.getWeekEnding());
        }

    };

    private static Comparator<TSCostCenterView> TSItemCostCodeComparator = new Comparator<TSCostCenterView>() {
        // ascending order
        @Override
        public int compare(TSCostCenterView o1, TSCostCenterView o2) {
            return o1.getCostCode().compareTo(o2.getCostCode());
        }

    };

}
