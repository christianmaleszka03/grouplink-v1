package de.grouplink.grouplinkvaadin.data.enums;

public enum Gender {

    MALE("Männlich"),
    FEMALE("Weiblich"),
    NON_BINARY("Keine Angabe");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
