package com.guillermo.leif.challenges.dec12.objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Getter
@Setter
@ToString
public class Cavern {
    String name;
    Boolean hasBeenVisited;
    CaveSize caveSize;

    public Cavern(String name) {
        if (StringUtils.isAllUpperCase(name)) {
            this.caveSize = CaveSize.LARGE;
        } else {
            this.caveSize = CaveSize.SMALL;
        }
    }

    public Cavern(List<CaveSystem> caveSystemList, String caveName) {
        for (CaveSystem caveSystem : caveSystemList) {
            if (caveSystem.getCaveName().equals(caveName)) {
                createCavern(caveSystem);
                return;
            }
        }
    }

    public Cavern(CaveSystem caveSystem) {
        this.name = caveSystem.getCaveName();
        this.hasBeenVisited = false;
        this.caveSize = caveSystem.isLargeCave ? CaveSize.LARGE : CaveSize.SMALL;
    }

    public void createCavern(CaveSystem caveSystem) {
        this.name = caveSystem.getCaveName();
        this.hasBeenVisited = false;
        this.caveSize = caveSystem.isLargeCave ? CaveSize.LARGE : CaveSize.SMALL;
    }
}
