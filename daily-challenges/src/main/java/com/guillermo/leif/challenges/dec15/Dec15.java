package com.guillermo.leif.challenges.dec15;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.challenges.dec15.objects.Cave;
import com.guillermo.leif.challenges.dec15.objects.Path;
import com.guillermo.leif.inputReaders.day15.InputReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Slf4j
@Component
@Challenge15
public class Dec15 implements Challenge {
    private static final String day = "day15";
    private String START_CAVE_NAME = "startCave";

    private InputReader inputReader;

    @Autowired
    public Dec15(InputReader inputReader) {
        this.inputReader = inputReader;
    }

    @Override
    public void solveProblem() throws Exception {
        final String sampleFilePath1 = "daily-challenges/src/main/resources/inputFiles/" + day + "/sample1.txt";
        final String filePath = "daily-challenges/src/main/resources/inputFiles/" + day + "/puzzleInput.txt";
        log.info(day);

        calculatePart1(filePath);
//        calculatePart2(sampleFilePath1);
    }

    private void calculatePart1(String filePath) throws Exception {
        log.info("Part 1");
        List<List<Integer>> riskMap = inputReader.readAllLines(filePath);
        List<List<Cave>> caveMap = convertRiskMapToCaveMap(riskMap);
        Cave startCave = caveMap.get(0).get(0);
        startCave.setName(START_CAVE_NAME);

        updateAllCavesWithNeighbors(caveMap);
        printRiskMap(riskMap);
        updateCaveMapWithLowestRiskMap(caveMap);

        Cave endCave = caveMap.get(caveMap.size() - 1).get(caveMap.get(0).size() - 1);
        log.info(endCave.getLowestKnownRiskToStart().toString());

//        List<Path> contexts = findPaths(startCave, endCave, new Path(), new ArrayList<>());
//        for (Path context : contexts) {
//            log.info(context.toString());
//        }

    }

    private List<Path> findPaths(Cave currentCave, Cave endCave, Path path, List<Path> paths) {
        currentCave.setVisited(true);

        Path newPath = new Path();
        List<Cave> caves = new ArrayList<>(path.getCaves());
        caves.add(currentCave);
        newPath.setRisk(path.getRisk());
        if (!currentCave.getName().equals(START_CAVE_NAME)) {
            newPath.addRisk(currentCave.getRisk());
        }
        newPath.setCaves(caves);


        // found the end-node
        if (currentCave.equals(endCave)) {
            newPath.setValidPathToEnd(true);
            paths.add(newPath);
            return paths;
        }
        paths.add(newPath);

        Cave nextCave = getVisitableCaveWithLeastRisk(currentCave, path);
        while (nextCave == null) {

        }
        if (nextCave == null) {
            newPath.setValidPathToEnd(false);
            paths.add(newPath);
            return paths;
        }

        List<Path> lowRisKPath = findPaths(nextCave, endCave, newPath, paths);
        paths.addAll(lowRisKPath);


        return paths;
    }

    private Cave getVisitableCaveWithLeastRisk(Cave cave, Path path) {
        List<Cave> cavesToCompare = new ArrayList<>();
        if (null != cave.getRight() && !path.caveHasBeenVisited(cave.getRight())) {
            cavesToCompare.add(cave.getRight());
        }
        if (null != cave.getDown() && !path.caveHasBeenVisited(cave.getDown())) {
            cavesToCompare.add(cave.getDown());
        }
        if (null != cave.getUp() && !path.caveHasBeenVisited(cave.getUp())) {
            cavesToCompare.add(cave.getUp());
        }
        if (null != cave.getLeft() && !path.caveHasBeenVisited(cave.getLeft())) {
            cavesToCompare.add(cave.getLeft());
        }

        if (cavesToCompare.isEmpty()) {
            return null;
        }

        Cave lowestRiskCave = cavesToCompare.get(0);
        for (Cave c : cavesToCompare) {
            if (c.getRisk() < lowestRiskCave.getRisk()) {
                lowestRiskCave = c;
            }
        }

        return lowestRiskCave;
    }

    private void printRiskMap(List<List<Integer>> riskMap) {
        for (List<Integer> row : riskMap) {
            log.info(Arrays.toString(row.toArray()));
        }

    }

    private void calculatePart2(String filePath) throws Exception {
        log.info("Part 2");
    }

    private List<List<Cave>> convertRiskMapToCaveMap(List<List<Integer>> riskMap) {
        List<List<Cave>> caveMap = new ArrayList<>();

        for (int y = 0; y < riskMap.size(); y++) {
            List<Cave> caveList = new ArrayList<>();
            List<Integer> row = riskMap.get(y);
            for (int x = 0; x < row.size(); x++) {
                caveList.add(new Cave(row.get(x), x, y));
            }
            caveMap.add(caveList);
        }

        return caveMap;
    }

    private void updateCaveMapWithLowestRiskMap(List<List<Cave>> caveMap) {
        for (List<Cave> row : caveMap) {
            for (Cave cave : row) {
                if (Objects.equals(cave.getName(), START_CAVE_NAME)) {
                    cave.setLowestRiskNeighbor(null);
                    cave.setLowestKnownRiskToStart(0);
                    cave.setRisk(0);
                }
                cave.setUp(setNeighborCaveLowestRiskToThisCaveIfItIsLowestRiskPath(cave, cave.getUp()));
                cave.setDown(setNeighborCaveLowestRiskToThisCaveIfItIsLowestRiskPath(cave, cave.getDown()));
                cave.setLeft(setNeighborCaveLowestRiskToThisCaveIfItIsLowestRiskPath(cave, cave.getLeft()));
                cave.setRight(setNeighborCaveLowestRiskToThisCaveIfItIsLowestRiskPath(cave, cave.getRight()));
            }
        }
    }

    private Cave setNeighborCaveLowestRiskToThisCaveIfItIsLowestRiskPath(Cave thisCave, Cave neighborCave) {
        if (neighborCave == null) {
            return null;
        }
        Integer thisRisk = thisCave.getLowestKnownRiskToStart();
        Integer neighborRiskTotal = neighborCave.getLowestKnownRiskToStart();
        int thisRiskToCompare = thisRisk + neighborCave.getRisk();
        if (neighborRiskTotal == null || thisRiskToCompare < neighborRiskTotal) {
            neighborCave.setLowestRiskNeighbor(thisCave);
            neighborCave.setLowestKnownRiskToStart(thisRiskToCompare);
        }
        return neighborCave;
    }

    private void updateAllCavesWithNeighbors(List<List<Cave>> caveMap) {
        for (List<Cave> caveList : caveMap) {
            for (Cave cave : caveList) {
                cave.setNeighbors(caveMap);
            }
        }
    }

}
