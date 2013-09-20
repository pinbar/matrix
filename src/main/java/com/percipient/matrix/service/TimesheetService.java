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
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.percipient.matrix.common.Status;
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

    public TimesheetView getTimesheet(Date weekEnding, Integer employeeId);

    public List<TimesheetView> getTimesheets();

    public TimesheetView createTimesheet(Date tsDate, Integer employeeId);

    public void saveTimesheet(TimesheetView timesheetView);

    public void deleteTimesheet(Integer timesheetId);

    public TimesheetView addCostCodeRow(Integer timesheetId);

    public void deleteCostCodeRow(Integer timesheetId, String costCode);

    public TimesheetView getTimesheet(Integer timesheetId);

    // HR Timesheet Management functions

    public List<HrTimesheetView> getTimesheetsByStatus(String status);

    public List<HrTimesheetView> getReporteeTimesheetsByStatus(String status,
            List<Integer> reporteeIds);

    public void saveTimesheets(List<HrTimesheetView> timesheetViews);

}

@Service
class TimesheetServiceImpl implements TimesheetService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    // private UserInfo userInfo;
    private javax.inject.Provider<UserInfo> userInfo;

    @Autowired
    DateUtil dateUtil;

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    HibernateUtil hibernateUtil;

    @Transactional
    public List<HrTimesheetView> getTimesheetsByStatus(String status) {

        List<Timesheet> timesheets = timesheetRepository
                .getTimesheetsByStatus(status);
        List<HrTimesheetView> hrTimesheets = getHrTimesheetViewListFromTimesheetList(timesheets);
        return hrTimesheets;
    }

    @Transactional
    public List<HrTimesheetView> getReporteeTimesheetsByStatus(String status,
            List<Integer> reporteeIds) {

        List<Timesheet> timesheets = timesheetRepository
                .getReporteeTimesheetsByStatus(status, reporteeIds);
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

            hrTimesheetView.setManagerId(employee.getManagerId());
            if (null != employee.getManagerId()) {
                Employee manager = employeeRepository.getEmployee(employee
                        .getManagerId());
                hrTimesheetView.setManagerName(manager.getFirstName() + " "
                        + manager.getLastName());
            }
            setTimeSheetWarnings(timesheet, hrTimesheetView);
            hrTimesheetViewList.add(hrTimesheetView);
        }
        return hrTimesheetViewList;
    }

    private void setTimeSheetWarnings(Timesheet timesheet,
            HrTimesheetView hrTimesheetView) {
        Map<Date, Double> dateHoursMap = new HashMap<Date, Double>();
        Set<TimesheetItem> tsItemList = timesheet.getTimesheetItems();
        for (TimesheetItem tsItem : tsItemList) {
            if (dateHoursMap.containsKey(tsItem.getDate())) {
                dateHoursMap
                        .put(tsItem.getDate(),
                                (tsItem.getHours() + dateHoursMap.get(tsItem
                                        .getDate())));
            } else {
                dateHoursMap.put(tsItem.getDate(), tsItem.getHours());
            }

            if (tsItem.getHours() > 0 && isWeekend(tsItem.getDate())) {
                populateWeekendWarningMessage(hrTimesheetView, tsItem.getDate());
            }
        }

        for (Entry<Date, Double> entry : dateHoursMap.entrySet()) {
            if (entry.getValue() > 8.00) {
                populateOTWarningMessage(hrTimesheetView, entry.getKey());
            }
        }
        if (hrTimesheetView.getHours() > 40) {
            populateHoursForWeekExceedWarning(hrTimesheetView);
        }
        if (hrTimesheetView.getHours() < 40) {
            populateHoursForWeekNotMinimumWarning(hrTimesheetView);
        }
    }

    private boolean isWeekend(Date date) {
        Calendar cal = Calendar.getInstance(LocaleContextHolder.getLocale());
        cal.setTime(date);
        int dow = cal.get(Calendar.DAY_OF_WEEK);
        if (dow == Calendar.SUNDAY || dow == Calendar.SATURDAY) {
            return true;
        }
        return false;
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

        Integer employeeId = userInfo.get().getEmployee().getId();
        List<Timesheet> timesheetList = timesheetRepository
                .getTimesheets(employeeId);
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

        Integer employeeId = userInfo.get().getEmployee().getId();
        Timesheet timesheet = timesheetRepository.getTimesheet(employeeId,
                weekEnding);
        TimesheetView timesheetView = null;
        if (timesheet != null) {
            timesheetView = getTimeSheetView(timesheet);
        } else {
            timesheetView = createTimesheet(weekEnding);
        }
        return timesheetView;
    }

    @Override
    @Transactional
    public TimesheetView getTimesheet(Date weekEnding, Integer employeeId) {
        Timesheet timesheet = timesheetRepository.getTimesheet(employeeId,
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

        Integer employeeId = userInfo.get().getEmployee().getId();
        List<Timesheet> timesheetList = timesheetRepository
                .getTimesheets(employeeId);

        List<TimesheetView> timesheetViewList = new ArrayList<TimesheetView>();
        TimesheetView currentTSV = null;
        if (!hasCurrentWeekEnding(timesheetList)) {
            currentTSV = createTimesheet(dateUtil.getCurrentWeekEndingDate());
        }
        for (Timesheet timesheet : timesheetList) {
            TimesheetView timesheetView = getTimeSheetPreview(timesheet);
            timesheetViewList.add(timesheetView);
        }
        if (null != currentTSV) {
            timesheetViewList.add(currentTSV);
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
    public void saveTimesheets(List<HrTimesheetView> hrTimesheetViews) {
        List<Timesheet> tsList = getTimesheetsFromHRViews(hrTimesheetViews);
        timesheetRepository.save(tsList);
    }

    @Override
    @Transactional
    public void deleteTimesheet(Integer timesheetId) {
        Timesheet timesheet = timesheetRepository.getTimesheet(timesheetId);

        // TODO delete any attachments for this timesheet
        timesheetRepository.delete(timesheet);
    }

    /**
     * 
     * @see com.percipient.matrix.service.TimesheetService#createTimesheet(java.util.Date)
     *      only creates views
     */

    private TimesheetView createTimesheet(Date weekEndingDate) {

        Integer employeeId = userInfo.get().getEmployee().getId();
        TimesheetView timesheetView = new TimesheetView();
        timesheetView.setWeekEnding(dateUtil.getAsString(weekEndingDate));
        timesheetView.setStatus(Status.PENDING.getVal());
        timesheetView.setEmployeeId(employeeId);
        List<TSCostCenterView> tsCCViews = new ArrayList<TSCostCenterView>();
        TSCostCenterView blankTSCCView = addTSCostCenterView(weekEndingDate);
        tsCCViews.add(blankTSCCView);
        timesheetView.setTsCostCenters(tsCCViews);

        return timesheetView;
    }

    @Override
    @Transactional
    public TimesheetView createTimesheet(Date weekEnding, Integer employeeId) {
        Timesheet timesheet = timesheetRepository.getTimesheet(employeeId,
                weekEnding);
        TimesheetView timesheetView = null;
        if (timesheet != null) {
            timesheetView = getTimeSheetView(timesheet);
        } else {
            timesheetView = createTimesheet(weekEnding);
            timesheetView.setEmployeeId(employeeId);
        }
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

    private List<Timesheet> getTimesheetsFromHRViews(
            List<HrTimesheetView> timesheetViews) {
        List<Timesheet> timeSheetList = new ArrayList<Timesheet>();
        for (HrTimesheetView view : timesheetViews) {
            timeSheetList.add(getTimesheetFromHRView(view));
        }

        return timeSheetList;
    }

    private Timesheet getTimesheetFromHRView(HrTimesheetView view) {
        Timesheet ts = timesheetRepository.getTimesheet(view.getTimesheetId());
        ts.setStatus(view.getStatus().toLowerCase());
        ts.setEmployeeId(view.getEmployeeId());
        ts.setWeekEnding(dateUtil.getAsDate(view.getWeekEnding()));
        return ts;
    }

    private Timesheet getTimesheetFromView(TimesheetView timesheetView) {

        Timesheet timesheet;
        if (timesheetView.getId() != null) {
            timesheet = timesheetRepository.getTimesheet(timesheetView.getId());

        } else {
            timesheet = new Timesheet();
            timesheet.setEmployeeId(timesheetView.getEmployeeId());
            timesheet.setWeekEnding(dateUtil.getAsDate(timesheetView
                    .getWeekEnding()));
        }
        timesheet.setStatus(timesheetView.getStatus().toLowerCase());
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
        timesheetView.setEmployeeId(timesheet.getEmployeeId());
        timesheetView.setStatus(timesheet.getStatus());
        timesheetView.setWeekEnding(dateUtil.getAsString(timesheet
                .getWeekEnding()));
        // TODO implement the same for HRTimesheetView??
        setHoursByCategory(timesheet, timesheetView);

        return timesheetView;
    }

    private TimesheetView getTimeSheetView(Timesheet timesheet) {
        TimesheetView timesheetView = new TimesheetView();
        timesheetView.setId(timesheet.getId());
        timesheetView.setEmployeeId(timesheet.getEmployeeId());
        timesheetView.setStatus(null != timesheet.getStatus() ? timesheet
                .getStatus() : Status.PENDING.getVal());
        timesheetView.setWeekEnding(dateUtil.getAsString(timesheet
                .getWeekEnding()));
        setTSCostCenterView(timesheet, timesheetView);

        return timesheetView;
    }

    private void setTSCostCenterView(Timesheet timesheet,
            TimesheetView timesheetView) {
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

        // sort rows by cost code ascending
        Collections.sort(tsCCViewList, TSItemCostCodeComparator);
        timesheetView.setTsCostCenters(tsCCViewList);
    }

    private void setHoursByCategory(Timesheet timesheet,
            TimesheetView timesheetView) {
        Double ptoHours = 0.00;
        Double overTimeHours = 0.00;
        Double regularHours = 0.00;
        Double totalHours = 0.00;
        Double weekendHours = 0.00;

        Set<TimesheetItem> timesheetItems = timesheet.getTimesheetItems();
        for (TimesheetItem tsItem : timesheetItems) {
            // TODO get the cost centers for client=PTO (can this be configured)
            if (tsItem.getCostCode().equalsIgnoreCase("VAC")
                    || tsItem.getCostCode().equalsIgnoreCase("HOL")
                    || tsItem.getCostCode().equalsIgnoreCase("SIC")) {
                ptoHours += tsItem.getHours();
            } else if (isWeekend(tsItem.getDate())) {
                weekendHours += tsItem.getHours();
            } else {
                if (tsItem.getHours() > 8.00) {
                    overTimeHours += tsItem.getHours() - 8.00;
                    regularHours += 8.00;
                } else {
                    regularHours += tsItem.getHours();
                }
            }
            totalHours += tsItem.getHours();
        }

        timesheetView.setPtoHours(ptoHours);
        timesheetView.setWeekendHours(weekendHours);
        timesheetView.setOverTimeHours(overTimeHours);
        timesheetView.setRegularHours(regularHours);
        timesheetView.setTotalHours(totalHours);
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
                Calendar.LONG, LocaleContextHolder.getLocale());

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

    private void populateWeekendWarningMessage(HrTimesheetView hrTimesheetView,
            Date date) {
        List<String> warnings = hrTimesheetView.getWarnings();
        Locale locale = LocaleContextHolder.getLocale();
        if (warnings == null) {
            warnings = new ArrayList<String>();
        }
        String formatDate = dateUtil.getAsString(date);
        warnings.add(messageSource.getMessage("timesheet.weekend.warning",
                new Object[] { formatDate }, locale));
        hrTimesheetView.setWarnings(warnings);
    }

    private void populateOTWarningMessage(HrTimesheetView hrTimesheetView,
            Date date) {
        List<String> warnings = hrTimesheetView.getWarnings();
        Locale locale = LocaleContextHolder.getLocale();
        if (warnings == null) {
            warnings = new ArrayList<String>();
        }
        String formatDate = dateUtil.getAsString(date);
        warnings.add(messageSource.getMessage("timesheet.OT.warning",
                new Object[] { formatDate }, locale));
        hrTimesheetView.setWarnings(warnings);
    }

    private void populateHoursForWeekExceedWarning(
            HrTimesheetView hrTimesheetView) {
        List<String> warnings = hrTimesheetView.getWarnings();
        Locale locale = LocaleContextHolder.getLocale();
        if (warnings == null) {
            warnings = new ArrayList<String>();
        }
        warnings.add(messageSource.getMessage(
                "timesheet.overfortyhours.warning",
                new Object[] { hrTimesheetView.getWeekEnding() }, locale));
        hrTimesheetView.setWarnings(warnings);
    }

    private void populateHoursForWeekNotMinimumWarning(
            HrTimesheetView hrTimesheetView) {
        List<String> warnings = hrTimesheetView.getWarnings();
        Locale locale = LocaleContextHolder.getLocale();
        if (warnings == null) {
            warnings = new ArrayList<String>();
        }
        warnings.add(messageSource.getMessage(
                "timesheet.underfortyhours.warning",
                new Object[] { hrTimesheetView.getWeekEnding() }, locale));
        hrTimesheetView.setWarnings(warnings);
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
