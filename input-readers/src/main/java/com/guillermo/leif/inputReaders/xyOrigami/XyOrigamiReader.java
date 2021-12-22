package com.guillermo.leif.inputReaders.xyOrigami;

import com.guillermo.leif.inputReaders.AllLineReader;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

@Component
public class XyOrigamiReader extends AllLineReader {

    public List<Point> readCoordinates(String filePath) throws IOException {
        List<String> allLines = readAllLinesFromFile(filePath);

        List<Point> points = new ArrayList<>();

        for (String line : allLines) {
            if (line.isBlank()) {
                break;
            }
            String[] splitLine = line.split(",");
            int x = Integer.parseInt(splitLine[0]);
            int y = Integer.parseInt(splitLine[1]);
            Point newPoint = new Point(x, y);
            points.add(newPoint);
        }

        return points;
    }

    public List<AbstractMap.SimpleEntry<String, Integer>> getFoldInstructions(String filePath) throws IOException {
        List<String> allLines = readAllLinesFromFile(filePath);

        List<AbstractMap.SimpleEntry<String, Integer>> instructions = new ArrayList<>();

        int i = 0;
        do {
            i++;
        } while (!allLines.get(i).isBlank());
        i++;

        while (i < allLines.size()) {
            String line = allLines.get(i);
            String[] splitLine = line.split(" ");
            String[] splitInstructions = splitLine[2].split("=");
            String axis = splitInstructions[0];
            Integer lineCoordinate = Integer.parseInt(splitInstructions[1]);
            instructions.add(new AbstractMap.SimpleEntry<>(axis, lineCoordinate));
            i++;
        }
        return instructions;
    }

}
