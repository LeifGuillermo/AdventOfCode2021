package com.guillermo.leif.inputReaders.bingoBoard;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString
@Slf4j
public class SingleBingoBoard {
	private List<List<BoardElement>> boardGrid = new ArrayList<>();

	public void addLineToBoardGrid(List<Integer> line) {
		List<BoardElement> boardElementLine = new ArrayList<>();
		for (Integer elementValue : line) {
			BoardElement boardElement = new BoardElement();
			boardElement.setValue(elementValue);
			boardElementLine.add(boardElement);
		}
		boardGrid.add(boardElementLine);
	}

	public void markElementOnBoard(Integer elementValueToMark) {
		for (List<BoardElement> row : boardGrid) {
			for (BoardElement element : row) {
				if (element.getValue().equals(elementValueToMark)) {
					element.setMarked(true);
				}
			}
		}
	}

	public boolean checkForBoardWin() {
		return checkRowsForBoardWin() || checkColumnsForBoardWin();
	}

	private boolean checkRowsForBoardWin() {
		for (List<BoardElement> row : boardGrid) {
			int unmarked = (int) row.stream().filter(element -> !element.getMarked()).count();
			if (unmarked == 0) {
				return true;
			}
		}
		return false;
	}

	private boolean checkColumnsForBoardWin() {
		int height = boardGrid.size();
		int width = boardGrid.get(0).size();

		for (int columnIndex = 0; columnIndex < width; columnIndex++) {
			int numMarked = 0;
			for (List<BoardElement> boardElements : boardGrid) {
				BoardElement element = boardElements.get(columnIndex);
				if (element.getMarked()) {
					numMarked++;
				} else {
					break;
				}
			}
			if (height == numMarked) {
				return true;
			}
		}
		return false;
	}

	public Integer calculateFinalScore(Integer winningNumber) {
		List<Integer> unmarkedNumbers = new ArrayList<>();

		for (List<BoardElement> boardElements : boardGrid) {
			for (BoardElement boardElement : boardElements) {
				if (!boardElement.getMarked()) {
					unmarkedNumbers.add(boardElement.getValue());
				}
			}
		}

		Integer sum = unmarkedNumbers.stream().reduce(0, Integer::sum);

		log.info("Sum: " + sum + ", winning number: " + winningNumber);
		log.info("Winning board: " + boardGrid);
		return sum * winningNumber;
	}
}
