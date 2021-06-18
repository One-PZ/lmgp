package com.animee.lmgp.common;

public enum State {
    UNUNITED(1,"未连接"),
    CONNECTED(2,"已连接"),
    TEST(3,"正在检测");

    Integer val;
    String message;

    State(Integer val, String message) {
        this.val = val;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return null;
        } else {
            for (State state : State.values()) {
                if (state.getVal().equals(value)) {
                    return state.getMessage();
                }
            }
            return null;
        }
    }
}
