package com.guillermo.leif.challenges.dec06;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private Map<Long, Long> lanternFishAgeCountMap = new HashMap<>();

	@Autowired
	public Dec06(LanternFishReader lanternFishReader) {
		this.lanternFishReader = lanternFishReader;
	}

	@Override
	public void solveProblem() throws Exception {
		final String filePath = "daily-challenges/src/main/resources/inputFiles/day6/puzzleInput.txt";
		final String sampleFilePath = "daily-challenges/src/main/resources/inputFiles/day6/sample1.txt";

		List<Long> ages = lanternFishReader.getLanternFishAges(filePath);
		addAllFishToMap(ages);
		log.info("inputAges: " + Arrays.toString(ages.toArray()));

		int NUM_DAYS_TO_SPAWN = 256;
		long start = System.currentTimeMillis();
		for (int day = 0; day < NUM_DAYS_TO_SPAWN; day++) {
			passOneSpawningDay();
//			printFish();
		}

		Long sum = 0L;
		for (Long count : lanternFishAgeCountMap.values()) {
			sum += count;
		}
		long end = System.currentTimeMillis();
		log.info("TOTAL TIME IN MILLISECONDS: " + (end-start));

		log.info("SUM: " + sum);
	}

	// for debugging
	private void printFish() {
		StringBuilder builder = new StringBuilder();
		for (Long key : lanternFishAgeCountMap.keySet()) {
			for (int i = 0; i < lanternFishAgeCountMap.get(key); i++) {
				builder.append(key).append(",");
			}
		}
		builder.setLength(builder.length() - 1);

		log.info(builder.toString());

	}

	private void countTotalFishAndLog() {


	}

	private void passOneSpawningDay() {
		updateAges();
		spawnNewFishFromSixes();
	}

	private void updateAges() {
		Map<Long, Long> newMap = new HashMap<>();


		lanternFishAgeCountMap.forEach((existingAge, existingCount) -> {
			long newAge = existingAge - 1;
			newMap.put(newAge, existingCount);
		});

		long zeros = lanternFishAgeCountMap.getOrDefault(0L, 0L);
		long sevens = lanternFishAgeCountMap.getOrDefault(7L, 0L);
		newMap.put(6L, sevens + zeros);
		newMap.remove(-1L);

		newMap.put(8L, zeros);

		this.lanternFishAgeCountMap = newMap;
	}

	private void spawnNewFishFromSixes() {

	}

	public void addAllFishToMap(List<Long> ages) {
		for (Long age : ages) {
			addSingleFishToMap(age);
		}
	}

	private void addSingleFishToMap(Long age) {
		long count = 0;
		if (lanternFishAgeCountMap.containsKey(age)) {
			count = lanternFishAgeCountMap.get(age);
		}
		lanternFishAgeCountMap.put(age, count + 1);
	}


}
