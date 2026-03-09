package com.webgis.map.finalmap.dto;

/**
 * Data Transfer Object containing needed fields to the list of maps.
 */
public record MapListDto (
    Long id,
    String title,
    String description
){}