package com.guillermo.leif.challenges.dec06;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class LanternFish {

	int age;
	List<LanternFish> spawn;

	public LanternFish(int age) {
		this.age = age;
		this.spawn = new ArrayList<>();
	}
}
