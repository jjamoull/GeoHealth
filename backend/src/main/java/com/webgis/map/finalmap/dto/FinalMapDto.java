package com.webgis.map.finalmap.dto;

/**
 * Data Transfer Object containing needed fields for a map's info.
 */
public record FinalMapDto(
    Long id,
    String title,
    String description,
    String fileGeoJson,
    Long rasterMapId
){}