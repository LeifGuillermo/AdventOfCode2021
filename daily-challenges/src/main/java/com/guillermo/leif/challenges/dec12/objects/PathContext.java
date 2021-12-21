package com.guillermo.leif.challenges.dec12.objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
public class PathContext implements Serializable {
    // Large caves are always false. Small caves are true if visited.
    public List<Cavern> caves = new ArrayList<>();

    // Don't forget to remove start and end caves.
    private List<Cavern> allSmallCaves = new ArrayList<>();
    private int revisitSmallCaveIndex;
    private boolean smallCaveDoubleVisited;

    public Cavern getRevisitableCave() {
        return allSmallCaves.get(revisitSmallCaveIndex);
    }



    public boolean caveHasBeenVisited(Cavern cavernToCheck) {
        for (Cavern cavern : caves) {
            if (cavern.getName().equals(cavernToCheck.getName())) {
                return cavern.getHasBeenVisited();
            }
        }
        return false;
    }

}
