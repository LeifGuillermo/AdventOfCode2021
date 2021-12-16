package com.guillermo.leif.inputReaders;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CaveConnectionsReader extends AllLineReader {

	public List<AbstractMap.SimpleEntry<String, String>> readConnectionsIntoMap(String filePath) throws IOException {
		List<String> allLines = readAllLinesFromFile(filePath);

		List<AbstractMap.SimpleEntry<String, String>> connectionMap = new ArrayList<>();
		for (String line : allLines) {
			String[] splitLine = line.split("-");
			connectionMap.add(new AbstractMap.SimpleEntry<>(splitLine[0], splitLine[1]));
		}

		return connectionMap;
	}
}
