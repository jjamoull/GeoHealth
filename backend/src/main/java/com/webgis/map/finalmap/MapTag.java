package com.webgis.map.finalmap;

public enum MapTag {
    DRY("dry"), WET("wet"), EBOLA("ebola"), RIFT_VALLEY_FEVER("rift_valley_fever");

    final private String value;

    MapTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MapTag fromValue(String value) {
        for (MapTag tag : values()) {
            if (tag.value.equalsIgnoreCase(value)) {
                return tag;
            }
        }
        throw new IllegalArgumentException("Unknown tag: " + value);
    }
}