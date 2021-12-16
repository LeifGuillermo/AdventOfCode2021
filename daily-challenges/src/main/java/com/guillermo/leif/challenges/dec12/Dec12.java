package com.guillermo.leif.challenges.dec12;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.challenges.dec12.objects.Cave;
import com.guillermo.leif.inputReaders.CaveConnectionsReader;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
@Challenge12
public class Dec12 implements Challenge {

	private CaveConnectionsReader caveConnectionsReader;

	@Autowired
	public Dec12(CaveConnectionsReader caveConnectionsReader) {
		this.caveConnectionsReader = caveConnectionsReader;
	}

	@Override
	public void solveProblem() throws Exception {
		final String sampleFilePath1 = "daily-challenges/src/main/resources/inputFiles/day12/sample1.txt";
		final String sampleFilePath2 = "daily-challenges/src/main/resources/inputFiles/day12/sample2.txt";
		final String sampleFilePath3 = "daily-challenges/src/main/resources/inputFiles/day12/sample3.txt";
		final String sampleFilePath4 = "daily-challenges/src/main/resources/inputFiles/day12/sample4.txt";
		final String filePath = "daily-challenges/src/main/resources/inputFiles/day11/puzzleInput.txt";
		log.info("Day 12");

		calculatePart1(sampleFilePath1);
//		calculatePart2(sampleFilePath1);
	}

	private void calculatePart1(String filePath) throws Exception {
		log.info("Part 1");
		List<AbstractMap.SimpleEntry<String, String>> connectionMap = caveConnectionsReader.readConnectionsIntoMap(filePath);
		log.info("Connection Map:\n" + Arrays.toString(connectionMap.toArray()));

		List<Cave> allCaves = buildCaveList(connectionMap);
		Cave startCave = findCaveWithName(allCaves, "start");
		Cave endCave = findCaveWithName(allCaves, "end");

		int numPaths = traverseCaveSystemAndCountPaths(0, startCave, endCave);
		log.info("Number of paths: " + numPaths);

	}

	/**
	 * 1. finds all caves connected to the current cave (breadth-first-search).
	 * 2. if cave is traversable traverses it (this should add on to depth of each path)
	 */
	private int traverseCaveSystemAndCountPaths(int treeDepth, Cave currentCave, Cave endCave) {
		int numPaths = 0;

		if (currentCave != endCave) {
			treeDepth++;
			// count + paths
			for (Cave connection : currentCave.getConnections()) {
				if (connection.isLargeCave() || !connection.isHasBeenVisited()) {
					numPaths++;
				}
			}
			for (Cave connection : currentCave.getConnections()) {
				if (connection.isLargeCave() || !connection.isHasBeenVisited()) {
					if (!connection.isLargeCave()) {
						connection.setHasBeenVisited(true);
					}
					return numPaths + traverseCaveSystemAndCountPaths(treeDepth, connection, endCave);
				}
				else if(!connection.isLargeCave() && connection.isHasBeenVisited()) {
					return 0;
				}
			}
			return 0;
		} else {
			return 1;
		}

	}

	private Cave findCaveWithName(List<Cave> caveList, String caveName) throws Exception {
		for (Cave cave : caveList) {
			if (cave.getCaveName().equals(caveName)) {
				return cave;
			}
		}
		throw new Exception("Could not find cave: " + caveName);
	}

	private List<Cave> buildCaveList(List<AbstractMap.SimpleEntry<String, String>> connectionMap) {
		List<Cave> caveList = new ArrayList<>();
		for (AbstractMap.SimpleEntry<String, String> connection : connectionMap) {
			Cave keyCave = new Cave(connection.getKey(), connectionMap);
			Cave valueCave = new Cave(connection.getValue(), connectionMap);
			if (!caveList.contains(keyCave)) {
				caveList.add(keyCave);
			}
			if (!caveList.contains(valueCave)) {
				caveList.add(valueCave);
			}
		}
		connectCaves(caveList);
		return caveList;
	}

	private void connectCaves(List<Cave> caveList) {
		for (Cave cave : caveList) {
			cave.buildCaveConnections(caveList);
		}
	}


	private void calculatePart2(String filePath) throws IOException {
		log.info("Part 2");

	}


}
