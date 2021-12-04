package com.guillermo.leif.inputReaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.javatuples.KeyValue;
import org.javatuples.Tuple;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AllLineReader {

	public List<String> readAllLinesFromFile(String fileName) throws IOException {
		return Files.readAllLines(Paths.get(fileName));
	}

	public List<Integer> readAllLinesFromFileAsIntegers(String fileName) throws IOException {
		return convertToIntegerList(readAllLinesFromFile(fileName));
	}

	private List<Integer> convertToIntegerList(List<String> stringList) {
		return stringList.stream().map(Integer::parseInt).collect(Collectors.toList());
	}

	public List<Tuple> readAllLinesFromFileAsTuples(String fileName) throws IOException {
		return getSubmarineInstructionTuplesFromLineStrings(readAllLinesFromFile(fileName));
	}

	private List<Tuple> getSubmarineInstructionTuplesFromLineStrings(List<String> lines) {
		List<String[]> splitLines = lines.stream().map(line -> line.split(" ")).collect(Collectors.toList());

		List<Tuple> tupleList = new ArrayList<>();

		for (String[] line : splitLines) {
			Tuple tuple = new KeyValue<>(line[0].trim().toLowerCase(), Integer.parseInt(line[1]));
			tupleList.add(tuple);
		}

		return tupleList;
	}


}
