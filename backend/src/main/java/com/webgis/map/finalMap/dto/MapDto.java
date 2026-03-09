package com.webgis.map.finalMap.dto;

/**
 * Data Transfer Object containing needed fields for a map's info.
 */
public record MapDto(
    Long id,
    String title,
    String description,
    String fileGeoJson
){}