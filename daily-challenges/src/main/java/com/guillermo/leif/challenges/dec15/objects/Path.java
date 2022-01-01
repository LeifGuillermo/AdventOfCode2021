package com.guillermo.leif.challenges.dec15.objects;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Path {
    private List<Cave> caves = new ArrayList<>();
    private boolean validPathToEnd = false;
    private int risk = 0;

    public boolean caveHasBeenVisited(Cave caveToCheck) {
        for (Cave cave : caves) {
            if (cave.getName().equals(caveToCheck.getName())) {
                return cave.isVisited();
            }
        }
        return false;
    }

    public void addRisk(int risk) {
        this.risk += risk;
    }

    public String toString() {
        return "Context valid: " + validPathToEnd + ", Sum: " + sumRisks();
    }

    public int sumRisks() {
        int sum = 0;
        for (Cave cave : caves) {
            sum += cave.risk;
        }
        return sum;
    }
}
