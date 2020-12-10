package com.nativeboyz.vmall.models;

public enum ActionType {

    DELETE(0), SAVE(1), UPDATE(2);

    private final int value;

    ActionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
