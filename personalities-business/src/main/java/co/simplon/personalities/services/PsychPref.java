package co.simplon.personalities.services;

public enum PsychPref {
    EXTRAVERSION("E", "I"),
    INTROVERSION("I", "E"),
    INTUITION("N", "S"),
    SENSATION("S", "N"),
    THINKING("T", "F"),
    FEELING("F", "T"),
    PERCEPTION("P", "J"),
    JUDGMENT("J", "P");

    private String code;
    private String complementary;

    PsychPref(String code, String complementary) {
        this.code = code;
        this.complementary = complementary;
    }

    public String getCode() {
        return code;
    }

    public String getComplementary() {
        return complementary;
    }
}
