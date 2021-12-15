package com.guillermo.leif.challenges.dec11;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.inputReaders.IntegerMapReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
@Challenge11
public class Dec11 implements Challenge {

    private IntegerMapReader integerMapReader;

    @Autowired
    public Dec11(IntegerMapReader integerMapReader) {
        this.integerMapReader = integerMapReader;
    }

    @Override
    public void solveProblem() throws Exception {
        final String sampleFilePath = "daily-challenges/src/main/resources/inputFiles/day11/sample1.txt";
        final String sampleFilePath2 = "daily-challenges/src/main/resources/inputFiles/day11/sample2.txt";
        final String filePath = "daily-challenges/src/main/resources/inputFiles/day11/puzzleInput.txt";
        log.info("Day 11");

//        calculatePart1(filePath);
        calculatePart2(filePath);
    }

    private void calculatePart2(String filePath) throws IOException {
        log.info("Part 2");
        List<List<Integer>> map = integerMapReader.readMap(filePath);
        List<List<DumboOctopus>> dumboOctopusMap = constructDumboOctopusMap(map);
        populateNeighbors(dumboOctopusMap);

        findFirst100Flash(dumboOctopusMap);
    }

    private void calculatePart1(String filePath) throws IOException {
        log.info("Part 1");
        List<List<Integer>> map = integerMapReader.readMap(filePath);
        List<List<DumboOctopus>> dumboOctopusMap = constructDumboOctopusMap(map);
        populateNeighbors(dumboOctopusMap);

        int numSteps = 100;
        int flashes = runSteps(numSteps, dumboOctopusMap);
        log.info("Flashes: " + flashes);
    }

    private int runSteps(int numSteps, List<List<DumboOctopus>> dumboOctopusMap) {
        int numFlashes = 0;
        for (int step = 1; step <= numSteps; step++) {
            numFlashes += performSingleStep(dumboOctopusMap, step);
            if(numFlashes == 100) {
                log.info("FLASHED 100! at: " + step);
            }
        }
        return numFlashes;
    }

    private void findFirst100Flash(List<List<DumboOctopus>> dumboOctopusMap) {
        int numFlashes = 0;
        int step = 1 ;
        while(numFlashes != 100) {
            numFlashes = 0;
            numFlashes = performSingleStep(dumboOctopusMap, step);
            if(numFlashes == 100) {
                log.info("FLASHED 100! at: " + step);
            }
            step++;
        }
    }

    private Integer performSingleStep(List<List<DumboOctopus>> dumboOctopusMap, int step) {
        List<DumboOctopus> dumboOctopusList = dumboOctopusMap.stream().flatMap(Collection::stream).collect(Collectors.toList());
        log.info("Step " + step);
        increaseEnergyLevels(dumboOctopusList);


        cascade(dumboOctopusList);
        int numFlashes = expendEnergy(dumboOctopusList);


        log.info("NumFlashes " + numFlashes);
        printDumboOctopusMap(dumboOctopusMap);

        return numFlashes;
    }

    private int expendEnergy(List<DumboOctopus> dumboOctopi) {
        int numFlashes = 0;
        for (DumboOctopus dumboOctopus : dumboOctopi) {
            numFlashes += dumboOctopus.expendEnergy();
        }
        return numFlashes;
    }

    private void increaseEnergyLevels(List<DumboOctopus> dumboOctopi) {
        for (DumboOctopus dumboOctopus : dumboOctopi) {
            dumboOctopus.increaseEnergyLevel();
        }
    }

    private void cascade(List<DumboOctopus> dumboOctopi) {
        List<DumboOctopus> readyToFlash;
        do {
            for (DumboOctopus dumboOctopus : dumboOctopi) {
                dumboOctopus.cascade();
            }
            readyToFlash = dumboOctopi.stream().filter(oct -> !oct.isHasFlashed() && oct.getEnergyLevel() > 9).collect(Collectors.toList());
        } while (readyToFlash.size() > 0);
    }



    private void populateNeighbors(List<List<DumboOctopus>> dumboOctopusMap) {
        for (List<DumboOctopus> dumboOctopusList : dumboOctopusMap) {
            for (DumboOctopus dumboOctopus : dumboOctopusList) {
                dumboOctopus.setNeighbors();
            }
        }
    }

    private void printDumboOctopusMap(List<List<DumboOctopus>> map) {
        for (List<DumboOctopus> dumboOctopusList : map) {
            log.info(Arrays.toString(dumboOctopusList.stream().map(octo -> String.format("%2d", octo.getEnergyLevel()).replace(" 0", "@@")).toArray()));
        }
    }

    private List<List<DumboOctopus>> constructDumboOctopusMap(List<List<Integer>> energyMap) {
        List<List<DumboOctopus>> dumboOctopusMap = new ArrayList<>();
        for (int y = 0; y < energyMap.size(); y++) {
            List<DumboOctopus> dumboOctopusList = new ArrayList<>();
            for (int x = 0; x < energyMap.get(y).size(); x++) {
                dumboOctopusList.add(new DumboOctopus(x, y, energyMap.get(y).get(x)));
            }
            dumboOctopusMap.add(dumboOctopusList);
        }
        for (List<DumboOctopus> dumboOctopusList : dumboOctopusMap) {
            for (DumboOctopus dumboOctopus : dumboOctopusList) {
                dumboOctopus.setDumboOctopusList(dumboOctopusMap);
            }
        }
        return dumboOctopusMap;
    }
}
