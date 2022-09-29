package com.ibm.dba.dmoe.adaptors.kie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProcessVariable {

	private String name;
	private String value;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "variable: {name=" + getName() + ", value=" + getValue() + "}";
	}
}