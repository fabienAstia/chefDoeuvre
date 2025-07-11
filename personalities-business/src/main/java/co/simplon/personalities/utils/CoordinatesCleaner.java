package co.simplon.personalities.utils;

import co.simplon.personalities.dtos.CoordinatesView;
import org.springframework.util.StringUtils;

import java.util.List;

import static java.util.Objects.isNull;

public class CoordinatesCleaner {

    public static List<CoordinatesView> fillMissingCoordinates(List<CoordinatesView> allCoordinates) {
        return allCoordinates.stream()
                .map(coordinate -> {
//                    if (isNull(coordinate.getLon()) || isNull(coordinate.getLat())) {
//                        coordinate.setLon(String.valueOf(0.0));
//                        coordinate.setLat(String.valueOf(0.0));
//                    }
//                    if (isNull(coordinate) || StringUtils.(coordinate.getLon()) || isNull(coordinate.getLat())) {
//                        coordinate.setLon(String.valueOf(0.0));
//                        coordinate.setLat(String.valueOf(0.0));
//                    }
                    return coordinate;
                }).toList();
    }
}
