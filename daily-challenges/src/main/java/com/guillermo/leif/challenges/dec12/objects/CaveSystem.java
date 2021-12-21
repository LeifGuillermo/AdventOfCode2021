package com.guillermo.leif.challenges.dec12.objects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ToString
@Getter
@Setter
public class CaveSystem {
    boolean isLargeCave;
    private String caveName;

    @Setter(AccessLevel.NONE)
    private List<CaveSystem> connections;


    @Setter(AccessLevel.NONE)
    private List<String> connectionNames;

    public CaveSystem(String caveName, List<AbstractMap.SimpleEntry<String, String>> caveNames) {
        this.isLargeCave = StringUtils.isAllUpperCase(caveName);
        this.caveName = caveName;
        this.connectionNames = new ArrayList<>(buildConnectionNames(caveNames));
        this.connections = new ArrayList<>();
    }

    private List<String> buildConnectionNames(List<AbstractMap.SimpleEntry<String, String>> caveNames) {
        List<String> connectionNames = new ArrayList<>();
        for (AbstractMap.SimpleEntry<String, String> entry : caveNames) {
            if (Objects.equals(entry.getKey(), this.getCaveName())) {
                connectionNames.add(entry.getValue());
            } else if (Objects.equals(entry.getValue(), this.getCaveName())) {
                connectionNames.add(entry.getKey());
            }
        }
        return connectionNames;
    }

    public void buildCaveConnections(List<CaveSystem> allCaves) {
        List<CaveSystem> connections = new ArrayList<>();
        for (CaveSystem cave : allCaves) {
            if (connectionNames.contains(cave.getCaveName())) {
                connections.add(cave);
            }
        }
        this.connections = connections;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CaveSystem)) {
            return false;
        }
        return ((CaveSystem) o).getCaveName().equals(getCaveName());
    }

/*    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getCaveName() + "->[");
        for (Cave cave : connections) {
            stringBuilder.append(cave.caveName);
            stringBuilder.append(", ");
        }
        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }*/

    @Override
    public String toString() {
        return this.getCaveName();
    }
}
