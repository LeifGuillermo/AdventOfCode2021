package com.guillermo.leif.inputReaders.bingoBoard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.guillermo.leif.inputReaders.AllLineReader;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BingoReader extends AllLineReader {


	public List<SingleBingoBoard> getBingoBoards(String fileName) throws Exception {
		List<String> lines = readAllLinesFromFile(fileName);

		BingoBoardDimensions dimensions = new BingoBoardDimensions(lines);

		List<SingleBingoBoard> bingoBoards = convertLineInputToBoards(lines, dimensions);

		log.info(Arrays.toString(bingoBoards.toArray()));
		return bingoBoards;
	}


	private List<SingleBingoBoard> convertLineInputToBoards(List<String> lines, BingoBoardDimensions dimensions) throws Exception {
		lines.remove(0);
		lines.remove(0);
		List<SingleBingoBoard> bingoBoards = new ArrayList<>();

		SingleBingoBoard singleBingoBoard = new SingleBingoBoard();

		int currentLineIndex = 0;
		while (currentLineIndex < lines.size()) {
			String line = lines.get(currentLineIndex);
			if (!line.isBlank()) {
				List<Integer> boardLine = Util.convertSingleBingoInputRowToListOfIntegers(line);
				singleBingoBoard.addLineToBoardGrid(boardLine);
			} else {
				bingoBoards.add(singleBingoBoard);
				singleBingoBoard = new SingleBingoBoard();
			}
			currentLineIndex++;
		}
		bingoBoards.add(singleBingoBoard);

		return bingoBoards;
	}

	public List<Integer> getDrawnNumberList(String fileName) throws IOException {
		List<String> lines = readAllLinesFromFile(fileName);
		String line = lines.get(0);
		String[] drawnNumberStrings = line.trim().split(",");
		return Stream.of(drawnNumberStrings).map(Integer::parseInt).collect(Collectors.toList());
	}

}
