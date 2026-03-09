package com.webgis.map.finalMap.dto;

/**
 * Data Transfer Object containing needed fields to the list of maps.
 */
public record MapListDto (
    Long id,
    String title,
    String description
){}