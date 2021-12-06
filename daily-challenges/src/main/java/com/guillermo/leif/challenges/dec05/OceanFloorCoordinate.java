package com.guillermo.leif.challenges.dec05;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OceanFloorCoordinate {
    Integer x, y;
    Integer count;

    public OceanFloorCoordinate() {
        this.count = 0;
    }

    public OceanFloorCoordinate(int x, int y, int count) {
        this.x = x;
        this.y = y;
        this.count = count;
    }
}
