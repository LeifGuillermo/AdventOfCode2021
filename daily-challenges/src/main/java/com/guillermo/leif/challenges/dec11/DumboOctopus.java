package com.guillermo.leif.challenges.dec11;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ToString
@Getter
public class DumboOctopus {
    private final int xLocation;
    private final int yLocation;
    private List<List<DumboOctopus>> dumboOctopusList = new ArrayList<>();


    private int energyLevel;
    @Setter
    private boolean hasFlashed = false;

    @Getter(AccessLevel.NONE)
    private DumboOctopus upNeighbor;
    @Getter(AccessLevel.NONE)
    private DumboOctopus downNeighbor;
    @Getter(AccessLevel.NONE)
    private DumboOctopus leftNeighbor;
    @Getter(AccessLevel.NONE)
    private DumboOctopus rightNeighbor;

    @Getter(AccessLevel.NONE)
    private DumboOctopus upRightNeighbor;
    @Getter(AccessLevel.NONE)
    private DumboOctopus downRightNeighbor;
    @Getter(AccessLevel.NONE)
    private DumboOctopus upLeftNeighbor;
    @Getter(AccessLevel.NONE)
    private DumboOctopus downLeftNeighbor;


    public DumboOctopus(int xLocation, int yLocation, int energyLevel) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.energyLevel = energyLevel;
    }

    public void setDumboOctopusList(List<List<DumboOctopus>> dumboOctopusList) {
        this.dumboOctopusList = dumboOctopusList;
    }

    public void setNeighbors() {
        setUpNeighbor(dumboOctopusList);
        setDownNeighbor(dumboOctopusList);
        setLeftNeighbor(dumboOctopusList);
        setRightNeighbor(dumboOctopusList);
        setUpLeftNeighbor(dumboOctopusList);
        setUpRightNeighbor(dumboOctopusList);
        setDownLeftNeighbor(dumboOctopusList);
        setDownRightNeighbor(dumboOctopusList);
    }

    public int expendEnergy() {
        this.hasFlashed = false;
        if (this.energyLevel > 9) {
            this.energyLevel = 0;
            return 1;
        }
        return 0;
    }

    public void increaseEnergyLevel() {
        energyLevel++;
    }

    public void cascade() {
        if (energyLevel > 9 && !hasFlashed) {
            List<DumboOctopus> nonNullNeighbors = getNonNullNeighbors();
            increaseNeighborEnergyLevels(nonNullNeighbors);
            hasFlashed = true;
        }
    }

//    private int cascadeNeighbors(List<DumboOctopus> nonNullNeighbors) {
//        int numFlashes = 0;
//        for (DumboOctopus neighbor : nonNullNeighbors) {
//            if (neighbor.getEnergyLevel() > 9) {
//                numFlashes += neighbor.cascade();
//            }
//        }
//        return numFlashes;
//    }

    public void increaseNeighborEnergyLevels(List<DumboOctopus> nonNullNeighbors) {
        for (DumboOctopus neighbor : nonNullNeighbors) {
            neighbor.increaseEnergyLevel();
        }
    }


    private List<DumboOctopus> getNonNullNeighbors() {
        List<DumboOctopus> nonNullList = new ArrayList<>();
        addNeighborIfNotNull(nonNullList, upNeighbor);
        addNeighborIfNotNull(nonNullList, downNeighbor);
        addNeighborIfNotNull(nonNullList, leftNeighbor);
        addNeighborIfNotNull(nonNullList, rightNeighbor);
        addNeighborIfNotNull(nonNullList, upRightNeighbor);
        addNeighborIfNotNull(nonNullList, downRightNeighbor);
        addNeighborIfNotNull(nonNullList, upLeftNeighbor);
        addNeighborIfNotNull(nonNullList, downLeftNeighbor);
        return nonNullList;
    }

    private void addNeighborIfNotNull(List<DumboOctopus> dumboOctopiList, DumboOctopus neighbor) {
        if (null != neighbor) {
            dumboOctopiList.add(neighbor);
        }
    }

    public void setUpRightNeighbor(List<List<DumboOctopus>> octopusMap) {
        DumboOctopus upRightNeighbor = null;
        if (this.yLocation >= 1 && this.xLocation < (octopusMap.get(0).size() - 1)) {
            upRightNeighbor = octopusMap.get(yLocation - 1).get(xLocation + 1);
        }
        this.upRightNeighbor = upRightNeighbor;
    }

    public void setDownRightNeighbor(List<List<DumboOctopus>> octopusMap) {
        DumboOctopus downRightNeighbor = null;
        if ((this.yLocation < octopusMap.size() - 1) && (this.xLocation < (octopusMap.get(0).size() - 1))) {
            downRightNeighbor = octopusMap.get(yLocation + 1).get(xLocation + 1);
        }
        this.downRightNeighbor = downRightNeighbor;
    }

    public void setUpLeftNeighbor(List<List<DumboOctopus>> octopusMap) {
        DumboOctopus upLeftNeighbor = null;
        if ((this.yLocation >= 1) && this.xLocation >= 1) {
            upLeftNeighbor = octopusMap.get(yLocation - 1).get(xLocation - 1);
        }
        this.upLeftNeighbor = upLeftNeighbor;
    }


    public void setDownLeftNeighbor(List<List<DumboOctopus>> octopusMap) {
        DumboOctopus downLeftNeighbor = null;
        if ((this.yLocation < octopusMap.size() - 1) && this.xLocation >= 1) {
            downLeftNeighbor = octopusMap.get(yLocation + 1).get(xLocation - 1);
        }
        this.downLeftNeighbor = downLeftNeighbor;
    }

    private void setUpNeighbor(List<List<DumboOctopus>> octopusMap) {
        DumboOctopus upNeighbor = null;
        if (this.yLocation >= 1) {
            upNeighbor = octopusMap.get(yLocation - 1).get(xLocation);
        }
        this.upNeighbor = upNeighbor;
    }

    private void setDownNeighbor(List<List<DumboOctopus>> octopusMap) {
        DumboOctopus downNeighbor = null;
        if (this.yLocation < octopusMap.size() - 1) {
            downNeighbor = octopusMap.get(yLocation + 1).get(xLocation);
        }
        this.downNeighbor = downNeighbor;
    }

    private void setLeftNeighbor(List<List<DumboOctopus>> octopusMap) {
        DumboOctopus leftNeighbor = null;
        if (this.xLocation >= 1) {
            leftNeighbor = octopusMap.get(yLocation).get(xLocation - 1);
        }
        this.leftNeighbor = leftNeighbor;
    }

    private void setRightNeighbor(List<List<DumboOctopus>> octopusMap) {
        DumboOctopus rightNeighbor = null;
        if (this.xLocation < octopusMap.get(0).size() - 1) {
            rightNeighbor = octopusMap.get(yLocation).get(xLocation + 1);
        }
        this.rightNeighbor = rightNeighbor;
    }
}
