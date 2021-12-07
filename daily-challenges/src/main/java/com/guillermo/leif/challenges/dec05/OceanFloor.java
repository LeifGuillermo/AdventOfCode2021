package com.guillermo.leif.challenges.dec05;

import com.guillermo.leif.inputReaders.xyGraph.XyCoordinate;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.KeyValue;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Component
public class OceanFloor {
    private List<List<OceanFloorCoordinate>> ventLocations;
    private Integer minXCoordinate, maxXCoordinate, minYCoordinate, maxYCoordinate;

    private enum VentDirection {
        HORIZONTAL,
        VERTICAL,
        DIAGONAL
    }

    private void initializeVentLocations() {
        ventLocations = new ArrayList<>();
        int xSize = maxXCoordinate + 1;
        int ySize = maxYCoordinate + 1;
        for (int y = 0; y <= ySize; y++) {
            List<OceanFloorCoordinate> coordinates = new ArrayList<>();
            for (int x = 0; x <= xSize; x++) {
                coordinates.add(new OceanFloorCoordinate());
            }
            ventLocations.add(coordinates);
        }
    }

    public void mapVentLocations(List<KeyValue<XyCoordinate, XyCoordinate>> ventCoordinates) {
        setMinAndMaxCoordinates(ventCoordinates);
        initializeVentLocations();

        for (KeyValue<XyCoordinate, XyCoordinate> coordinate : ventCoordinates) {
            VentDirection direction = determineVentDirectionForSingleLine(coordinate);
            mapVentLocationsForSingleLine(direction, coordinate);
//            logVentLocations(coordinate);
        }

        countOverlaps();
    }

    private void logVentLocations(KeyValue<XyCoordinate, XyCoordinate> coordinate) {
        log.info("VENT_LOCATIONS for " + coordinate);
        for (int y = 0; y <= maxYCoordinate; y++) {
            StringBuilder currentLine = new StringBuilder();
            for (int x = 0; x <= maxXCoordinate; x++) {
                currentLine.append(ventLocations.get(x).get(y).getCount());
                currentLine.append(",");
            }
            currentLine.setLength(currentLine.length() - 1);
            log.info(currentLine.toString());
        }
    }

    private void countOverlaps() {
        int count = 0;
        for (int y = 0; y <= maxYCoordinate; y++) {
            for (int x = 0; x <= maxXCoordinate; x++) {
                if (ventLocations.get(x).get(y).getCount() > 1) {
                    count++;
                }
            }

        }
        log.info("VENT_OVERLAPS: " + count);
    }

    private void setMinAndMaxCoordinates(List<KeyValue<XyCoordinate, XyCoordinate>> ventCoordinates) {

        for (KeyValue<XyCoordinate, XyCoordinate> coordinates : ventCoordinates) {
            XyCoordinate startCoordinate = coordinates.getKey();
            XyCoordinate endCoordinate = coordinates.getValue();

            setMinXCoordinateIfLess(startCoordinate.getX());
            setMaxXCoordinateIfGreater(startCoordinate.getX());
            setMinYCoordinateIfLess(startCoordinate.getY());
            setMaxYCoordinateIfGreater(startCoordinate.getY());

            setMinXCoordinateIfLess(endCoordinate.getX());
            setMaxXCoordinateIfGreater(endCoordinate.getX());
            setMinYCoordinateIfLess(endCoordinate.getY());
            setMaxYCoordinateIfGreater(endCoordinate.getY());
        }
    }

    private void setMaxXCoordinateIfGreater(Integer coordinate) {
        if (null == maxXCoordinate || coordinate > maxXCoordinate) {
            maxXCoordinate = coordinate;
        }
    }

    private void setMinXCoordinateIfLess(Integer coordinate) {
        if (null == minXCoordinate || coordinate < minXCoordinate) {
            minXCoordinate = coordinate;
        }
    }

    private void setMaxYCoordinateIfGreater(Integer coordinate) {
        if (null == maxYCoordinate || coordinate > maxYCoordinate) {
            maxYCoordinate = coordinate;
        }
    }

    private void setMinYCoordinateIfLess(Integer coordinate) {
        if (null == minYCoordinate || coordinate < minYCoordinate) {
            minYCoordinate = coordinate;
        }
    }

    private VentDirection determineVentDirectionForSingleLine(KeyValue<XyCoordinate, XyCoordinate> coordinate) {
        XyCoordinate startCoordinate = coordinate.getKey();
        XyCoordinate endCoordinate = coordinate.getValue();

        if (startCoordinate.getX() == endCoordinate.getX()) {
            return VentDirection.VERTICAL;
        } else if (startCoordinate.getY() == endCoordinate.getY()) {
            return VentDirection.HORIZONTAL;
        } else {
            return VentDirection.DIAGONAL;
        }
    }

    private void mapVentLocationsForSingleLine(VentDirection direction, KeyValue<XyCoordinate, XyCoordinate> coordinates) {
        switch (direction) {
            case HORIZONTAL:
                mapHorizontalVentLocationsForSingleLine(coordinates);
                break;
            case VERTICAL:
                mapVerticalVentLocationsForSingleLine(coordinates);
                break;
            default:
                mapDiagonalVentLocationsForSingleLine(coordinates);
//                log.info("Diagonal Vent Direction found: " + coordinates);
        }
    }

    private void mapHorizontalVentLocationsForSingleLine(KeyValue<XyCoordinate, XyCoordinate> coordinates) {
        int y = coordinates.getKey().getY();

        int x1 = coordinates.getKey().getX();
        int x2 = coordinates.getValue().getX();

        int direction = determineLineDirection(x1, x2); // 1 = right, -1 = left

        for (int x = x1; (direction < 0 ? x >= x2 : x <= x2); x += direction) {
            int existingCount = getExistingCount(x, y);
            ventLocations.get(x).set(y, new OceanFloorCoordinate(x, y, existingCount + 1));
        }
    }

    private int getExistingCount(int x, int y) {
        return (null == ventLocations.get(x).get(y).getCount())
                ? 0
                : ventLocations.get(x).get(y).getCount();
    }

    private void mapVerticalVentLocationsForSingleLine(KeyValue<XyCoordinate, XyCoordinate> coordinates) {
        int x = coordinates.getKey().getX();

        int y1 = coordinates.getKey().getY();
        int y2 = coordinates.getValue().getY();

        int direction = determineLineDirection(y1, y2); // 1 = right, -1 = left

        for (int y = y1; (direction < 0 ? y >= y2 : y <= y2); y += direction) {
            int existingCount = getExistingCount(x, y);
            ventLocations.get(x).set(y, new OceanFloorCoordinate(x, y, existingCount + 1));
        }
    }

    private void mapDiagonalVentLocationsForSingleLine(KeyValue<XyCoordinate, XyCoordinate> coordinates) {
        int point1x = coordinates.getKey().getX();
        int point2x = coordinates.getValue().getX();
        int point1y = coordinates.getKey().getY();
        int point2y = coordinates.getValue().getY();

        int yDirection = determineLineDirection(point1y, point2y); // 1 = down, -1 = up
        int xDirection = determineLineDirection(point1x, point2x); // 1 = right, -1 = left

        for (int y = point1y, x = point1x;
             (yDirection < 0 ? y >= point2y : y <= point2y) && (xDirection < 0 ? x >= point2x : x <= point2x);
             y += yDirection, x += xDirection) {
            int existingCount = getExistingCount(x, y);
            ventLocations.get(x).set(y, new OceanFloorCoordinate(x, y, existingCount + 1));
        }
    }

    // 1 = right or down, -1 = left or up
    private int determineLineDirection(int pointA, int pointB) {
        return pointB < pointA ? -1 : 1;
    }

}
