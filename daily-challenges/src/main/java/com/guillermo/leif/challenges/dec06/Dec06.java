package com.guillermo.leif.challenges.dec06;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.inputReaders.lanternFishReader.LanternFishReader;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@Challenge6
public class Dec06 implements Challenge {

	private LanternFishReader lanternFishReader;

	@Autowired
	public Dec06(LanternFishReader lanternFishReader) {
		this.lanternFishReader = lanternFishReader;
	}

	@Override
	public void solveProblem() throws Exception {
		final String filePath = "daily-challenges/src/main/resources/inputFiles/day6/puzzleInput.txt";
		final String sampleFilePath = "daily-challenges/src/main/resources/inputFiles/day6/sample1.txt";

		List<Integer> ages = lanternFishReader.getLanternFishAges(sampleFilePath);

		log.info("inputAges: " + Arrays.toString(ages.toArray()));
	}


}
