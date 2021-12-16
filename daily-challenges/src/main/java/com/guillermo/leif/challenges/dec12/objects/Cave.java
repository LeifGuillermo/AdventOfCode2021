package com.guillermo.leif.challenges.dec12.objects;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Cave {
	boolean isLargeCave;
	boolean hasBeenVisited;
	private String caveName;

	@Setter(AccessLevel.NONE)
	private List<Cave> connections;


	@Setter(AccessLevel.NONE)
	private List<String> connectionNames;

	public Cave(String caveName, List<AbstractMap.SimpleEntry<String, String>> caveNames) {
		this.isLargeCave = StringUtils.isAllUpperCase(caveName);
		this.hasBeenVisited = false;
		this.caveName = caveName;
		this.connectionNames = buildConnectionNames(caveNames);
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

	public void buildCaveConnections(List<Cave> allCaves) {
		List<Cave> connections = new ArrayList<>();
		for (Cave cave : allCaves) {
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
		if (!(o instanceof Cave)) {
			return false;
		}
		return ((Cave) o).getCaveName().equals(getCaveName());
	}
}
