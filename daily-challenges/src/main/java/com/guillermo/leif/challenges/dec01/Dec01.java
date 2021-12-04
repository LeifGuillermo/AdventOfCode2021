package com.guillermo.leif.challenges.dec01;

import com.guillermo.leif.challenges.Challenge;
import com.guillermo.leif.inputReaders.AllLineReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Challenge1
public class Dec01 implements Challenge {
    private AllLineReader allLineReader;

    @Autowired
    public Dec01(AllLineReader allLineReader) {
        this.allLineReader = allLineReader;
    }

    @Override
    public void solveProblem() throws IOException {
        final String filePath = "daily-challenges/src/main/resources/inputFiles/day1/sonarSweepInput.txt";
        final String sampleFilePath = "daily-challenges/src/main/resources/inputFiles/day1/sample1.txt";
        final String sampleFilePath2 = "daily-challenges/src/main/resources/inputFiles/day1/sample2.txt";

        List<Integer> measurements = allLineReader.readAllLinesFromFileAsIntegers(filePath);
        log.info("Individual Measurement increases: " + countMeasurementIncreases(measurements));
        List<Integer> slidingWindowMeasurements = createSlidingWindowMeasurementListFromMeasurements(measurements);
        log.info("Sliding Window Measurement increases: " + countMeasurementIncreases(slidingWindowMeasurements));

    }

    private long countMeasurementIncreases(List<Integer> measurements) {
        long count = 0;
        for (int currentMeasurementIndex = 1; currentMeasurementIndex < measurements.size(); currentMeasurementIndex++) {
            int previousMeasurementIndex = currentMeasurementIndex - 1;

            int currentMeasurement = measurements.get(currentMeasurementIndex);
            int previousMeasurement = measurements.get(previousMeasurementIndex);
            if (currentMeasurement > previousMeasurement) {
                count++;
            }
        }
        return count;
    }

    private List<Integer> createSlidingWindowMeasurementListFromMeasurements(List<Integer> measurements) {
        List<Integer> windowedMeasurements = new ArrayList<>();

        for (int i = 0, j = 1, k = 2; k < measurements.size(); i++, j++, k++) {
            int sum = measurements.get(i) + measurements.get(j) + measurements.get(k);
            windowedMeasurements.add(sum);
        }

        return windowedMeasurements;
    }


}
