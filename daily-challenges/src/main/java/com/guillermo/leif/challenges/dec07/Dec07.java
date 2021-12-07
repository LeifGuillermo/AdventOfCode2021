package com.guillermo.leif.challenges.dec07;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.inputReaders.crabLocation.CrabLocationReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
@Challenge7
public class Dec07 implements Challenge {

    private CrabLocationReader crabLocationReader;

    @Autowired
    public Dec07(CrabLocationReader crabLocationReader) {
        this.crabLocationReader = crabLocationReader;
    }

    @Override
    public void solveProblem() throws Exception {
        final String filePath = "daily-challenges/src/main/resources/inputFiles/day7/puzzleInput.txt";
        final String sampleFilePath = "daily-challenges/src/main/resources/inputFiles/day7/sample1.txt";

        List<Integer> crabLocations = crabLocationReader.getCrabLocations(sampleFilePath);
        log.info("Crab locations: " + Arrays.toString(crabLocations.toArray()));

        int locationsSize = crabLocations.size();
        int halfSize = locationsSize / 2;

        List<Integer> result = findShortestDistance(crabLocations.subList(0, halfSize), crabLocations.subList(halfSize, locationsSize));

        result = result.stream().sorted().collect(Collectors.toList());

    }

    private List<Integer> findShortestDistance(List<Integer> firstHalf, List<Integer> secondHalf) {
        int firstHalfSize = firstHalf.size();
        int halfFirstHalf = firstHalfSize / 2;

        int secondHalfSize = secondHalf.size();
        int halfSecondHalf = secondHalfSize / 2;

        if (firstHalfSize == 1 && secondHalfSize > 1) {
            return List.of(
                    (firstHalf.get(0) + findShortestDistance(
                            secondHalf.subList(0, halfSecondHalf),
                            secondHalf.subList(halfSecondHalf, secondHalfSize)).get(0))
                            / 2);
        }

        if (secondHalfSize == 1 && firstHalfSize > 1) {
            return List.of(
                    (secondHalf.get(0) + findShortestDistance(
                            firstHalf.subList(0, halfFirstHalf),
                            firstHalf.subList(halfFirstHalf, firstHalfSize)).get(0))
                            / 2);
        }

        if (firstHalfSize == 1 && secondHalfSize == 1) {
            return List.of((firstHalf.get(0) + secondHalf.get(0)) / 2);
        }

        List<Integer> firstHalfA = firstHalf.subList(0, halfFirstHalf);
        List<Integer> firstHalfB = firstHalf.subList(halfFirstHalf, firstHalfSize);
        List<Integer> secondHalfA = secondHalf.subList(0, halfSecondHalf);
        List<Integer> secondHalfB = secondHalf.subList(halfSecondHalf, secondHalfSize);

        return findShortestDistance(findShortestDistance(firstHalfA, firstHalfB), findShortestDistance(secondHalfA, secondHalfB));

    }

}
