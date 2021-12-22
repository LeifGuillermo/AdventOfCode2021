package com.guillermo.leif.challenges.dec12;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.challenges.dec12.objects.CaveSystem;
import com.guillermo.leif.challenges.dec12.objects.Cavern;
import com.guillermo.leif.challenges.dec12.objects.PathContext;
import com.guillermo.leif.challenges.dec12.objects.PathMapper;
import com.guillermo.leif.inputReaders.CaveConnectionsReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Component
@Challenge12
public class Dec12 implements Challenge {

    List<LinkedList<CaveSystem>> finishedPaths = new ArrayList<>();
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
        final String filePath = "daily-challenges/src/main/resources/inputFiles/day12/puzzleInput.txt";
        log.info("Day 12");

//        calculatePart1(filePath);
        calculatePart2(filePath);
    }

    private void calculatePart1(String filePath) throws Exception {
        log.info("Part 1");

        List<CaveSystem> allCaves = buildNewConnectionMapCaveList(filePath);
        log.info("All Caves: " + Arrays.toString(allCaves.toArray()));

        PathMapper pathMapper = new PathMapper();
        pathMapper.mapPathsFromStartToEnd(allCaves, "start", new PathContext());

        log.info("Finished");
        log.info("List of Paths size: " + pathMapper.getAllPaths().size());

        log.info("Paths:");
//        for (PathContext path : pathMapper.getAllPaths()) {
//            log.info(path.toString());
//        }

    }

    private void calculatePart2(String filePath) throws Exception {
        log.info("Part 2");
        List<CaveSystem> allCaves = buildNewConnectionMapCaveList(filePath);

        List<Cavern> smallCaverns = allCaves.stream().filter(cave -> !cave.isLargeCave()).map(Cavern::new)
                .filter(cavern -> !cavern.getName().equals("start") && !cavern.getName().equals("end"))
                .collect(Collectors.toList());

        log.debug("SmallCaverns: " + Arrays.toString(smallCaverns.toArray()));

        List<PathContext> allPaths = new ArrayList<>();
        for (Cavern cavern : smallCaverns) {
            PathMapper pathMapper = new PathMapper();
            PathContext pathContext = new PathContext();
            pathContext.setRevisitableSmallCaveName(cavern.getName());
            pathContext.setRevisitableCaveWasVisited(false);

            log.info(pathContext.metaInfo());

            pathMapper.mapPathsFromStartToEnd2(allCaves, "start", pathContext);

            allPaths.addAll(pathMapper.getAllPaths());
        }

        Set<PathContext> pathContextSet = new HashSet<>(allPaths);

        for (PathContext path : pathContextSet) {
            log.info(path.toString());
        }
        log.info("Num Paths: " + pathContextSet.size());

    }

    public List<CaveSystem> buildNewConnectionMapCaveList(String filePath) throws IOException {
        List<AbstractMap.SimpleEntry<String, String>> connectionMap = caveConnectionsReader.readConnectionsIntoMap(filePath);
        return buildCaveList(connectionMap);
    }

    private List<CaveSystem> buildCaveList(List<AbstractMap.SimpleEntry<String, String>> connectionMap) {
        List<CaveSystem> caveList = new ArrayList<>();
        for (AbstractMap.SimpleEntry<String, String> connection : connectionMap) {
            CaveSystem keyCave = new CaveSystem(connection.getKey(), connectionMap);
            CaveSystem valueCave = new CaveSystem(connection.getValue(), connectionMap);
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

    private void connectCaves(List<CaveSystem> caveList) {
        for (CaveSystem cave : caveList) {
            cave.buildCaveConnections(caveList);
        }
    }


}
