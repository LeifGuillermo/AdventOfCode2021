package com.guillermo.leif.inputReaders.lanternFishReader;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.guillermo.leif.inputReaders.AllLineReader;

@Component
public class LanternFishReader extends AllLineReader {

	public List<Integer> getLanternFishAges(String fileName) throws IOException {
		return readAllLinesFromFileAsIntegers(fileName);
	}
}
