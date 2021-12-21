package com.guillermo.leif.challenges.dec12.objects;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Slf4j
public class Path<E> extends LinkedList<E> {
    List<String> visitedSmallCaves = new ArrayList<>();
    private boolean isValidPath = true;

    public Path() {
    }

    public Path(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    public void addVisitedSmallCave(String caveName) {
        visitedSmallCaves.add(caveName);
    }

    @Override
    public String toString() {
        String string = super.toString();
        if (!isValidPath) {
            string = "invalid:" + string;
        }
        return string;
    }
}
