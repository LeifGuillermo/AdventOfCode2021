package com.guillermo.leif.inputReaders.bingoBoard;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class BoardElement {
	private Integer value;
	private Boolean marked = false;
}
