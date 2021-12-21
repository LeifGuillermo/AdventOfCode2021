package com.guillermo.leif.challenges.dec12.objects;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class PathMapper {
    private List<PathContext> allPaths = new ArrayList<>();

    // Current Cave should start as startCave
    public void mapPathsFromStartToEnd(final List<CaveSystem> allCaves, String currentCaveName, PathContext pathContext) throws Exception {
        Cavern currentCavern = new Cavern(allCaves, currentCaveName);
        if (currentCavern.getCaveSize().equals(CaveSize.SMALL)) {
            currentCavern.setHasBeenVisited(true);
        }

        PathContext currentContext = new PathContext();
        currentContext.setCaves(new ArrayList<>(pathContext.getCaves()));
        currentContext.caves.add(currentCavern);

        if (currentCavern.name.equals("end")) {
            allPaths.add(currentContext);
        }

        final CaveSystem caveSystem = findLocationFromName(allCaves, currentCaveName);
        List<Cavern> connections = getConnections(caveSystem, pathContext);
        List<Cavern> visitableConnections = connections.stream().filter(connection -> connection.getCaveSize().equals(CaveSize.LARGE) || !connection.getHasBeenVisited()).collect(Collectors.toList());

        if (visitableConnections.isEmpty()) {
            addInvalidPaths(false, currentContext);
        }

        for (Cavern cavern : visitableConnections) {
            mapPathsFromStartToEnd(allCaves, cavern.getName(), currentContext);
        }
    }

    CaveSystem findLocationFromName(List<CaveSystem> allCaves, String cave) throws Exception {
        for (CaveSystem caveSystem : allCaves) {
            if (caveSystem.getCaveName().equals(cave)) {
                return caveSystem;
            }
        }
        throw new Exception("Couldn't find cave with name: " + cave);
    }

    List<Cavern> getConnections(CaveSystem caveSystem, PathContext pathContext) {
        List<Cavern> caverns = caveSystem.getConnections().stream().map(Cavern::new).collect(Collectors.toList());
        return caverns.stream().map(cavern -> {
            cavern.hasBeenVisited = pathContext.caveHasBeenVisited(cavern);
            return cavern;
        }).collect(Collectors.toList());
    }

    private void addInvalidPaths(boolean addPaths, PathContext currentPathContext) {
        if (addPaths) {
            allPaths.add(currentPathContext);
        }
    }
}
