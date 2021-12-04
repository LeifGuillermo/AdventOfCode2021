package com.guillermo.leif.inputReaders.bingoBoard;

import java.util.List;
import java.util.stream.Collectors;

public class Util {
	protected static List<Integer> convertSingleBingoInputRowToListOfIntegers(String boardRow) {
		String trimmedRow = boardRow.trim();
		List<String> rowList = List.of(trimmedRow.split(" "));
		return rowList.stream()
				.filter(item -> !item.isBlank())
				.map(Integer::parseInt)
				.collect(Collectors.toList());
	}
}
