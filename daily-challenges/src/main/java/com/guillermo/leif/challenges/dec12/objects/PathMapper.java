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

    // Current Cave should start as startCave
    public void mapPathsFromStartToEnd2(final List<CaveSystem> allCaves, String currentCaveName, PathContext pathContext) throws Exception {
        Cavern currentCavern = new Cavern(allCaves, currentCaveName);

        PathContext currentContext = new PathContext();
        currentContext.setCaves(copyCavernsFromContext(pathContext));
        currentContext.setRevisitableSmallCaveName(pathContext.getRevisitableSmallCaveName());
        currentContext.setRevisitableCaveWasVisited(pathContext.getRevisitableCaveWasVisited());

        boolean revisitableCaveWasVisited = currentContext.getRevisitableCaveWasVisited();
        boolean isRevisitableCave = currentCaveName.equals(currentContext.getRevisitableSmallCaveName());

        if (currentCavern.getCaveSize().equals(CaveSize.SMALL) && isRevisitableCave && !revisitableCaveWasVisited) {
            currentContext.setRevisitableCaveWasVisited(true);
        } else if (currentCavern.getCaveSize().equals(CaveSize.SMALL) && isRevisitableCave && revisitableCaveWasVisited) {
            currentCavern.setHasBeenVisited(true);
            updateContextForSecondSmallVisit(currentCaveName, currentContext);
        } else if (currentCavern.getCaveSize().equals(CaveSize.SMALL)) {
            currentCavern.setHasBeenVisited(true);
        }

        currentContext.caves.add(currentCavern);

        if (currentCavern.getName().equals("end")) {
            allPaths.add(currentContext);
            return;
        }

        final CaveSystem caveSystem = findLocationFromName(allCaves, currentCaveName);

        List<Cavern> connections = getConnections(caveSystem, currentContext);
        List<Cavern> visitableConnections = getVisitableConnections(connections);

        if (visitableConnections.isEmpty()) {
            addInvalidPaths(false, currentContext);
            return;
        }

        for (Cavern cavern : visitableConnections) {
            mapPathsFromStartToEnd2(allCaves, cavern.getName(), currentContext);
        }
    }

    public void updateContextForSecondSmallVisit(String caveName, PathContext pathContext) {
        List<Cavern> caverns = pathContext.getCaves();
        for (Cavern cavern : caverns) {
            if (cavern.getName().equals(caveName)) {
                cavern.setHasBeenVisited(true);
            }
        }
        pathContext.setCaves(caverns);
    }

    public List<Cavern> copyCavernsFromContext(PathContext context) {
        List<Cavern> contextCaverns = context.getCaves();
        List<Cavern> newCaverns = new ArrayList<>();
        for (Cavern cavern : contextCaverns) {
            Cavern newCavern = new Cavern(cavern.getName(), cavern.getHasBeenVisited(), cavern.getCaveSize());
            newCaverns.add(newCavern);
        }
        return newCaverns;
    }

    CaveSystem findLocationFromName(List<CaveSystem> allCaves, String cave) throws Exception {
        for (CaveSystem caveSystem : allCaves) {
            if (caveSystem.getCaveName().equals(cave)) {
                return caveSystem;
            }
        }
        throw new Exception("Couldn't find cave with name: " + cave);
    }

    List<Cavern> getVisitableConnections(List<Cavern> connections) {
        return connections.stream().filter(connection -> (connection.getCaveSize().equals(CaveSize.LARGE) || !connection.getHasBeenVisited()))
                .collect(Collectors.toList());
    }

    List<Cavern> getConnections(CaveSystem caveSystem, PathContext pathContext) {
        return caveSystem.getConnections().stream().map(cavern ->
        {
            Cavern cave = new Cavern(cavern);
            cave.setHasBeenVisited(pathContext.caveHasBeenVisited(cave));
            return cave;
        }).collect(Collectors.toList());
    }

    private void addInvalidPaths(boolean addPaths, PathContext currentPathContext) {
        if (addPaths) {
            allPaths.add(currentPathContext);
        }
    }
}
