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


@Slf4j
@Component
@Challenge15
public class Dec15 implements Challenge {
    private static final String day = "day15";

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

        calculatePart1(sampleFilePath1);
//        calculatePart2(sampleFilePath1);
    }

    private void calculatePart1(String filePath) throws Exception {
        log.info("Part 1");
        List<List<Integer>> riskMap = inputReader.readAllLines(filePath);
        List<List<Cave>> caveMap = convertRiskMapToCaveMap(riskMap);
        updateAllCavesWithNeighbors(caveMap);
        printRiskMap(riskMap);

        Cave startCave = caveMap.get(0).get(0);
        startCave.setName("startCave");
        Cave endCave = caveMap.get(caveMap.size() - 1).get(caveMap.get(0).size() - 1);

        List<Path> contexts = findPaths(startCave, endCave, new Path(), new ArrayList<>());
        for (Path context : contexts) {
            log.info(context.toString());
        }

    }

    private List<Path> findPaths(Cave currentCave, Cave endCave, Path path, List<Path> paths) {
        currentCave.setVisited(true);

        Path newPath = new Path();
        List<Cave> caves = new ArrayList<>(path.getCaves());
        caves.add(currentCave);
        if (!currentCave.getName().equals("startCave")) {
            newPath.addRisk(currentCave.getRisk());
        }
        newPath.setCaves(caves);

        // ran into a dead-end or path is getting considerably long and probably is not low-risk.
        if (newPath.getCaves().size() > 30 ||
                (null == currentCave.getUp() || newPath.caveHasBeenVisited(currentCave.getUp()))
                &&
                (null == currentCave.getDown() || newPath.caveHasBeenVisited(currentCave.getDown()))
                &&
                (null == currentCave.getLeft() || newPath.caveHasBeenVisited(currentCave.getLeft()))
                &&
                (null == currentCave.getRight() || newPath.caveHasBeenVisited(currentCave.getRight()))) {
            newPath.setValidPathToEnd(false);
            paths.add(newPath);
            return paths;
        }

        paths.add(newPath);

        // found the end-node
        if (currentCave.equals(endCave)) {
            return paths;
        }

        if (null != currentCave.getRight() && !path.caveHasBeenVisited(currentCave.getRight())) {
            List<Path> rightContexts = findPaths(currentCave.getRight(), endCave, newPath, paths);
            paths.addAll(rightContexts);
        }
        if (null != currentCave.getDown() && !path.caveHasBeenVisited(currentCave.getDown())) {
            List<Path> downContexts = findPaths(currentCave.getDown(), endCave, newPath, paths);
            paths.addAll(downContexts);

        }

        if (null != currentCave.getUp() && !path.caveHasBeenVisited(currentCave.getUp())) {
            List<Path> upContexts = findPaths(currentCave.getUp(), endCave, newPath, paths);
            paths.addAll(upContexts);
        }
        if (null != currentCave.getLeft() && !path.caveHasBeenVisited(currentCave.getLeft())) {
            List<Path> leftContexts = findPaths(currentCave.getLeft(), endCave, newPath, paths);
            paths.addAll(leftContexts);

        }



        return paths;

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

    private void updateAllCavesWithNeighbors(List<List<Cave>> caveMap) {
        for (List<Cave> caveList : caveMap) {
            for (Cave cave : caveList) {
                cave.setNeighbors(caveMap);
            }
        }
    }

}
