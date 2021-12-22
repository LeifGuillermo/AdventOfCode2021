package com.guillermo.leif.challenges.dec12.objects;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PathContext implements Serializable {
    public List<Cavern> caves = new ArrayList<>();// Large caves are always false. Small caves are true if visited.
    private String revisitableSmallCaveName;
    private Boolean revisitableCaveWasVisited;

    public boolean caveHasBeenVisited(Cavern cavernToCheck) {
        for (Cavern cavern : caves) {
            if (cavern.getName().equals(cavernToCheck.getName())) {
                return cavern.getHasBeenVisited();
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("[");
        for (Cavern cavern : caves) {
            builder.append(cavern.getName()).append(", ");
        }
        if (builder.length() >= 2) {
            builder.setLength(builder.length() - 2);
        }
        builder.append("]");
        return builder.toString();
    }

    public String metaInfo() {
        return "revisitSmallLCaveName: " + revisitableSmallCaveName + ", isSmallCaveVisitedAlready: " + revisitableCaveWasVisited;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PathContext)) {
            return false;
        }

        PathContext other = (PathContext) o;

        return this.toString().equals(other.toString());
    }
}
