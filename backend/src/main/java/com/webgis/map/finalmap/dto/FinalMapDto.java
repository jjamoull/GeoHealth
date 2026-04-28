package com.webgis.map.finalmap.dto;

import com.webgis.map.finalmap.MapTag;

import java.util.List;

/**
 * Data Transfer Object containing needed fields for a map's info.
 */
public record FinalMapDto(
    Long id,
    String title,
    String description,
    List<MapTag> tags,
    String fileGeoJson,
    Long rasterMapId
){}