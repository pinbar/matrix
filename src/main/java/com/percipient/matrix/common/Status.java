package com.percipient.matrix.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;



public enum Status {
    PENDING("pending"), REJECTED("rejected"), APPROVED("approved"), SUBMITTED("submitted"); 

    private String actionVal;
    private static final Map<String, Status> lookup = new HashMap<String, Status>();

    static {
        for (Status s : EnumSet.allOf(Status.class))
            lookup.put(s.getVal(), s);
    }

    private Status(String val) {
        actionVal = val;
    }

    public String getVal() {
        return actionVal;
    }

    public static Status get(String val) {
        return lookup.get(val);
    }
};