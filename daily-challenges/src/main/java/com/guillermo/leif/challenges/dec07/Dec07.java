package com.guillermo.leif.challenges.dec07;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.inputReaders.crabLocation.CrabLocationReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;


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

        List<Integer> crabLocations = crabLocationReader.getCrabLocations(filePath);
        log.info("Crab locations: " + Arrays.toString(crabLocations.toArray()));

        findSmallestFuelCost(crabLocations);

    }

    private void findSmallestFuelCost(List<Integer> crabs) {
        int min = crabs.stream().mapToInt(i -> i).min().orElseThrow(NoSuchElementException::new);
        int max = crabs.stream().mapToInt(i -> i).max().orElseThrow(NoSuchElementException::new);

        int currentMinFuel = Integer.MAX_VALUE;
        int minPoint = -1;

        for (int point = min; point < max; point++) {
//            int currentFuelCost = countTotalFuelCostToPoint(point, crabs);
            int currentFuelCost = countTotalFuelCostToPointAccumulated(point, crabs);
            if (currentFuelCost < currentMinFuel) {
                currentMinFuel = currentFuelCost;
                minPoint = point;
            }
        }

        log.info("Min Fuel: " + currentMinFuel);
        log.info("Min Point: " + minPoint);
    }

    private int countTotalFuelCostToPointAccumulated(int point, List<Integer> crabs) {
        int totalFuel = 0;

        for (int crabLocation : crabs) {
            int distance = Math.abs(crabLocation - point);
            for (int i = 1; i <= distance; i++) {
                totalFuel += i;
            }
        }

        return totalFuel;
    }

    private int countTotalFuelCostToPoint(int point, List<Integer> crabs) {
        return crabs.stream().reduce(0, (fuelCost, crab) -> fuelCost += Math.abs(point - crab));
    }

}
