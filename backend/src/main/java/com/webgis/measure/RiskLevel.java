package com.webgis.measure;

public enum RiskLevel {
    UNDEFINED(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    private final int score;

    RiskLevel(int score) {
        this.score = score;
    }

    /**
     * Get the score associated to a riskLevel
     *
     * @return the score linked to the riskLevel
     */
    public int getScore() {
        return score;
    }

    /**
     * Turn a string type into a RiskLevel type
     *
     * @param str the string you want to convert into a riskLevel
     *
     * @return the riskLevel equivalent to the string given in parameters
     */
    public static RiskLevel fromString(String str) {
        if (str == null) {
            throw new IllegalArgumentException("RiskLevel cannot be null");
        }
        return RiskLevel.valueOf(str.trim().toUpperCase());
    }
}
