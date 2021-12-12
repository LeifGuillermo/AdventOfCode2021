package com.guillermo.leif.inputReaders.signalPatternNotes;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

@Getter
public class ScreenDigit {
	Set<Character> segments = new HashSet<>();

	public void add(Character segment) {
		segments.add(segment);
	}

	public void addAll(Set<Character> segments) {
		this.segments.addAll(segments);
	}
}
