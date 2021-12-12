package com.guillermo.leif.challenges.dec09;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public class Location {
    private final Integer xLocation;
    private final Integer yLocation;

    private Boolean wasSeenInBasin = false;

    private Integer upValue;
    private Integer downValue;
    private Integer leftValue;
    private Integer rightValue;

    private Integer thisValue;

    public Location(Integer xLocation, Integer yLocation, List<List<Integer>> heightMap) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;

        this.thisValue = heightMap.get(yLocation).get(xLocation);

        setUpValue(heightMap);
        setDownValue(heightMap);
        setLeftValue(heightMap);
        setRightValue(heightMap);
    }

    public Integer findNumberOfBasinNodes(List<List<Location>> allLocations) {
        if (9 == thisValue || wasSeenInBasin) {
            return 0;
        }
        wasSeenInBasin = true;
        Integer numBasinNeighbors = checkNeighborsForBasinValue(allLocations);

        return numBasinNeighbors + 1;
    }

    private Integer checkNeighborsForBasinValue(List<List<Location>> allLocations) {
        Integer sum = 0;
        if (null != upValue) {
            sum += allLocations.get(yLocation - 1).get(xLocation).findNumberOfBasinNodes(allLocations);
        }
        if (null != downValue) {
            sum += allLocations.get(yLocation + 1).get(xLocation).findNumberOfBasinNodes(allLocations);
        }
        if (null != leftValue) {
            sum += allLocations.get(yLocation).get(xLocation - 1).findNumberOfBasinNodes(allLocations);
        }
        if (null != rightValue) {
            sum += allLocations.get(yLocation).get(xLocation + 1).findNumberOfBasinNodes(allLocations);
        }
        return sum;
    }

    // risk level is 1 + height;
    public Integer returnRiskLevelOrZeroIfNotLowPoint() {
        List<Integer> nonNullValues = getNonNullNeighbors();
        for (Integer value : nonNullValues) {
            if (value <= thisValue) {
                return 0;
            }
        }
        return 1 + thisValue;
    }

    List<Integer> getNonNullNeighbors() {
        List<Integer> nonNullValues = new ArrayList<>();
        if (null != upValue) {
            nonNullValues.add(upValue);
        }
        if (null != downValue) {
            nonNullValues.add(downValue);
        }
        if (null != leftValue) {
            nonNullValues.add(leftValue);
        }
        if (null != rightValue) {
            nonNullValues.add(rightValue);
        }
        return nonNullValues;
    }

    public void setUpValue(List<List<Integer>> heightMap) {
        Integer upValue = null;
        if (this.yLocation > 0) {
            upValue = heightMap.get(this.yLocation - 1).get(this.xLocation);
        }
        this.upValue = upValue;
    }

    public void setDownValue(List<List<Integer>> heightMap) {
        Integer downValue = null;
        if (this.yLocation < heightMap.size() - 1) {
            downValue = heightMap.get(this.yLocation + 1).get(this.xLocation);
        }
        this.downValue = downValue;
    }

    public void setLeftValue(List<List<Integer>> heightMap) {
        Integer leftValue = null;
        if (this.xLocation > 0) {
            leftValue = heightMap.get(this.yLocation).get(this.xLocation - 1);
        }
        this.leftValue = leftValue;
    }

    public void setRightValue(List<List<Integer>> heightMap) {
        Integer rightValue = null;
        if (this.xLocation < heightMap.get(0).size() - 1) {
            rightValue = heightMap.get(this.yLocation).get(this.xLocation + 1);
        }
        this.rightValue = rightValue;
    }
}
