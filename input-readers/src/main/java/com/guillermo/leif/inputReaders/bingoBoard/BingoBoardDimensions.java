package com.guillermo.leif.inputReaders.bingoBoard;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BingoBoardDimensions {
	private int boardWidth = -1;
	private int boardHeight = -1;

	public BingoBoardDimensions(List<String> lines) {
		List<String> boards = new ArrayList<>(lines);
		boards.remove(0);
		boards.remove(0);

		scanBingoBoardWidth(boards.get(0));
		scanBingoBoardHeight(boards);
	}

	public void scanBingoBoardWidth(String boardRow) {
		this.boardWidth = Util.convertSingleBingoInputRowToListOfIntegers(boardRow).size();
	}


	public void scanBingoBoardHeight(List<String> boards) {
		int i = 0;
		while (!boards.get(i).isBlank()) {
			i++;
		}
		this.boardHeight = i;
	}


}
