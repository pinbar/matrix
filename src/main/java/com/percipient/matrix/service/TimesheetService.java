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
import com.percipient.matrix.display.TSCostCenterView;
import com.percipient.matrix.display.TimesheetItemView;
import com.percipient.matrix.display.TimesheetView;
import com.percipient.matrix.domain.Employee;
import com.percipient.matrix.domain.Timesheet;
import com.percipient.matrix.domain.TimesheetItem;
import com.percipient.matrix.session.UserInfo;
import com.percipient.matrix.util.DateUtil;

public interface TimesheetService {

    public List<TimesheetView> getTimesheetPreview();

    public TimesheetView getTimesheet(Date weekEnding);

    public List<TimesheetView> getTimesheets();

    public TimesheetView createTimesheet(Date asDate);

    public TimesheetView saveTimesheet(TimesheetView timesheetView);

    public void deleteTimesheet(Integer timesheetId);

    public TimesheetView addCostCodeRow(Integer timesheetId);

    public void deleteCostCodeRow(Integer timesheetId, String costCode);

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

    @Transactional
    public TimesheetView getTimesheet(Date weekEnding) {

        Employee employee = employeeRepository.getEmployeeByUserName(userInfo
                .getUserName());
        /*
         * List<Timesheet> timesheetList = timesheetRepository
         * .getTimesheets(employee); TimesheetView timesheetView = null;
         * 
         * for (Timesheet timesheet : timesheetList) { if
         * (weekEnding.compareTo(timesheet.getWeekEnding()) != 0) { continue; }
         * return getTimeSheetView(timesheet); }
         */
        Timesheet timesheet = timesheetRepository.getTimesheet(employee,
                weekEnding);
        TimesheetView timesheetView = getTimeSheetView(timesheet);
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
    public TimesheetView saveTimesheet(TimesheetView timesheetView) {
        Timesheet ts = getTimesheetFromView(timesheetView);
        timesheetRepository.save(ts);
        return getTimeSheetView(ts);
    }

    @Override
    @Transactional
    public void deleteTimesheet(Integer timesheetId) {
        Timesheet timesheet = timesheetRepository.getTimesheet(timesheetId);
        timesheetRepository.delete(timesheet);
    }

    @Override
    @Transactional
    public TimesheetView createTimesheet(Date date) {
        TimesheetView timesheetView = new TimesheetView();
        timesheetView.setWeekEnding(dateUtil.getAsString(date));
        timesheetView.setStatus("PENDING");

        List<TSCostCenterView> tsCCViews = new ArrayList<TSCostCenterView>();
        TSCostCenterView blankTSCCView = addTSCostCenterView(date);
        tsCCViews.add(blankTSCCView);
        timesheetView.setTsCostCenters(tsCCViews);

        // save the newly created timesheet for later retrieval
        timesheetView = saveTimesheet(timesheetView);

        return timesheetView;
    }

    @Override
    @Transactional
    public TimesheetView addCostCodeRow(Integer timesheetId) {
        Timesheet timesheet;
        if (null == timesheetId) {
            timesheet = getTimesheetFromView(createTimesheet(dateUtil
                    .getCurrentWeekEndingDate()));
        } else {
            timesheet = timesheetRepository.getTimesheet(timesheetId);
        }
        TimesheetView timesheetView = getTimeSheetView(timesheet);
        TSCostCenterView blankTSCCView = addTSCostCenterView(timesheet
                .getWeekEnding());
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
        timesheet.getTimesheetItems().removeAll(tmpItemSet);
    }

    private Timesheet getTimesheetFromView(TimesheetView timesheetView) {
        Timesheet timesheet = new Timesheet();
        timesheet.setId(timesheetView.getId());
        Employee employee = employeeRepository.getEmployeeByUserName(userInfo
                .getUserName());
        timesheet.setEmployeeId(employee.getId());
        timesheet.setStatus("PENDING");
        timesheet.setWeekEnding(dateUtil.getAsDate(timesheetView
                .getWeekEnding()));
        timesheet.setTimesheetItems(getTimesheetItems(timesheetView));
        return timesheet;
    }

    private Set<TimesheetItem> getTimesheetItems(TimesheetView timesheetView) {
        Set<TimesheetItem> tsItems = new HashSet<TimesheetItem>();
        for (TSCostCenterView tsCCview : timesheetView.getTsCostCenters()) {
            for (TimesheetItemView tsItemview : tsCCview.getTimesheetItems()) {
                TimesheetItem item = new TimesheetItem();
                item.setId(tsItemview.getId());
                item.setCostCode(tsCCview.getCostCode());
                item.setHours(tsItemview.getHours());
                item.setDate(dateUtil.getAsDate(tsItemview.getDate()));
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

    public static Comparator<Timesheet> DateComparator = new Comparator<Timesheet>() {

        /*
         * reverse chronological
         */
        @Override
        public int compare(Timesheet o1, Timesheet o2) {
            return o2.getWeekEnding().compareTo(o1.getWeekEnding());
        }

    };

    public static Comparator<TSCostCenterView> TSItemCostCodeComparator = new Comparator<TSCostCenterView>() {

        /*
         * ascending order
         */
        @Override
        public int compare(TSCostCenterView o1, TSCostCenterView o2) {
            return o1.getCostCode().compareTo(o2.getCostCode());
        }

    };

}
