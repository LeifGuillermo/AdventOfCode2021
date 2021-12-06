package com.guillermo.leif.inputReaders.lanternFishReader;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.guillermo.leif.inputReaders.AllLineReader;

@Component
public class LanternFishReader extends AllLineReader {

	public List<Integer> getLanternFishAges(String fileName) throws IOException {
		List<String> lines = readAllLinesFromFile(fileName);
		return Stream.of(lines.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());
	}

}
