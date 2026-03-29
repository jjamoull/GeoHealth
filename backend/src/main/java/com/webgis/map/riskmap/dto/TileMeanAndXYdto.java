package com.webgis.map.riskmap.dto;

/**dto for requesting tile block mean and its position*/
public record TileMeanAndXYdto(float mean, int tileX, int tileY , int blockX, int blockY) {
}
