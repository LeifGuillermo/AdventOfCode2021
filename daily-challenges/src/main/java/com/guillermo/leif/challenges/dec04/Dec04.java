package com.guillermo.leif.challenges.dec04;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.inputReaders.bingoBoard.BingoReader;
import com.guillermo.leif.inputReaders.bingoBoard.SingleBingoBoard;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Challenge4
public class Dec04 implements Challenge {

	private final BingoReader bingoReader;

	@Autowired
	public Dec04(BingoReader bingoReader) {
		this.bingoReader = bingoReader;
	}

	@Override
	public void solveProblem() throws Exception {
		final String filePath = "daily-challenges/src/main/resources/inputFiles/day4/bingoInput.txt";
		final String sampleFilePath = "daily-challenges/src/main/resources/inputFiles/day4/sample1.txt";

		List<Integer> drawnNumbers = bingoReader.getDrawnNumberList(filePath);
		List<SingleBingoBoard> bingoBoards = bingoReader.getBingoBoards(filePath);

		KeyValue<Integer, SingleBingoBoard> winningInfo = findLastRemainingBoard(drawnNumbers, bingoBoards);
		Integer winningNumber = winningInfo.getKey();
		SingleBingoBoard winningBoard = winningInfo.getValue();
		Integer finalScore = winningBoard.calculateFinalScore(winningNumber);

		log.info("FINAL SCORE: " + finalScore);
	}

	private KeyValue<Integer, SingleBingoBoard> findLastRemainingBoard(List<Integer> drawnNumbers, List<SingleBingoBoard> bingoBoards) throws Exception {
		ArrayList<Integer> winningIndexes = new ArrayList<>();

		Integer lastWinningNumber = null;
		for (Integer drawnNumber : drawnNumbers) {
			List<Integer> winningIndex = findNextWinners(drawnNumber, bingoBoards, winningIndexes);
			if (winningIndex.isEmpty()) {
				continue;
			}
			winningIndexes.addAll(winningIndex);
			if (winningIndexes.size() == bingoBoards.size()) {
				lastWinningNumber = drawnNumber;
				break;
			}
		}

		Integer lastWinner = winningIndexes.get(winningIndexes.size() - 1);
		SingleBingoBoard lastWinningBoard = bingoBoards.get(lastWinner);
		return new KeyValue<>(lastWinningNumber, lastWinningBoard);
	}

	private List<Integer> findNextWinners(Integer drawnNumber, List<SingleBingoBoard> bingoBoards, List<Integer> winningIndexes) throws Exception {
		List<Integer> newWinningIndexes = new ArrayList<>();
		for (int boardIndex = 0; boardIndex < bingoBoards.size(); boardIndex++) {
			if (winningIndexes.contains(boardIndex)) {
				continue;
			}
			SingleBingoBoard bingoBoard = bingoBoards.get(boardIndex);
			bingoBoard.markElementOnBoard(drawnNumber);
			if (bingoBoard.checkForBoardWin()) {
				newWinningIndexes.add(boardIndex);
			}
		}
		return newWinningIndexes;
	}

}
