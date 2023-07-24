package com.fitstir.fitstirapp.ui.utility.enums;

public enum ReminderChannels {
    COME_BACK_REMINDERS(0);

    private final int value;
    private ReminderChannels(int value) { this.value = value; }

    public int getValue() { return value; }
}
