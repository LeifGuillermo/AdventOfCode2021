package com.guillermo.leif.challenges.dec15.objects;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Setter
public class Cave {
    boolean visited;
    int x, y, risk;
    String name;

    Cave left = null;
    Cave right = null;
    Cave up = null;
    Cave down = null;

    Cave lowestRiskNeighbor = null;
    Integer lowestKnownRiskToStart = null;

    public Cave(Integer risk, int x, int y) {
        visited = false;
        this.x = x;
        this.y = y;
        this.risk = risk;
        this.name = "x:" + x + ",y:" + y;
    }

    public void setNeighbors(List<List<Cave>> caveMap) {
        int height = caveMap.size();
        int width = caveMap.get(0).size();
        if (x > 0) {
            left = caveMap.get(y).get(x - 1);
        }
        if (x < width - 1) {
            right = caveMap.get(y).get(x + 1);
        }

        if (y > 0) {
            up = caveMap.get(y - 1).get(x);
        }
        if (y < height - 1) {
            down = caveMap.get(y + 1).get(x);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Cave)) {
            return false;
        }
        Cave other = (Cave) o;
        return other.name.equals(this.name);

    }

}
