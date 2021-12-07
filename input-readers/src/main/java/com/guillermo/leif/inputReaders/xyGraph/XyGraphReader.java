package com.guillermo.leif.inputReaders.xyGraph;

import com.guillermo.leif.inputReaders.AllLineReader;
import org.javatuples.KeyValue;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class XyGraphReader extends AllLineReader {

    public List<KeyValue<XyCoordinate, XyCoordinate>> readCoordinates(String fileName) throws IOException {
        List<String> allLines = readAllLinesFromFile(fileName);
        return convertAllLinesToCoordinates(allLines);
    }

    private List<KeyValue<XyCoordinate, XyCoordinate>> convertAllLinesToCoordinates(List<String> allLines) {
        return allLines.stream().map(this::getCoordinatesFromStringLine).collect(Collectors.toList());
    }

    private KeyValue<XyCoordinate, XyCoordinate> getCoordinatesFromStringLine(String line) {
        String[] coordinateArray = line.split(" -> ");

        String[] startCoordinateSplit = coordinateArray[0].split(",");
        String[] endCoordinateSplit = coordinateArray[1].split(",");

        XyCoordinate startCoordinate = XyCoordinate.builder()
                .x(Integer.parseInt(startCoordinateSplit[0]))
                .y(Integer.parseInt(startCoordinateSplit[1]))
                .build();

        XyCoordinate endCoordinate = XyCoordinate.builder()
                .x(Integer.parseInt(endCoordinateSplit[0]))
                .y(Integer.parseInt(endCoordinateSplit[1]))
                .build();

        return new KeyValue<>(startCoordinate, endCoordinate);
    }
}
