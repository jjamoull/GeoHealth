package com.webgis.map.finalmap.dto;

/**
 * Data Transfer Object containing needed fields to the list of maps.
 */
public record FinalMapListDto(
    Long id,
    String title,
    String description,
    java.util.List<com.webgis.map.finalmap.MapTag> tags){}