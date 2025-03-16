package com.personalities.services;

public enum PsychPref {
    EXTRAVERSION("E"),
    INTROVERSION("I"),
    INTUITION("N"),
    SENSATION("S"),
    THINKING("T"),
    FEELING("F"),
    PERCEPTION("P"),
    JUDGMENT("J");

    private String prefCode;

    PsychPref(String prefCode) {
        this.prefCode = prefCode;
    }

    public String getPrefCode() {
        return prefCode;
    }
}
