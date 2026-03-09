package com.webgis.map.finalmap.dto;

/**
 * Data Transfer Object containing needed fields for a map's info.
 */
public record MapDto(
    Long id,
    String title,
    String description,
    String fileGeoJson
){}