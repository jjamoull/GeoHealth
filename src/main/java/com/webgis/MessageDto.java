package com.webgis;

/**
 * Data Transfer Object for simple message responses.
 * Used for status messages.
 */
public record MessageDto (
        String message
) {}