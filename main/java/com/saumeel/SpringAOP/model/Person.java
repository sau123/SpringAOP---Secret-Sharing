package com.saumeel.SpringAOP.model;

import java.util.UUID;
// this class not required.
public class Person {
	String name;
	UUID personUID;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UUID getPersonUID() {
		return personUID;
	}
	public void setPersonUID(UUID personUID) {
		this.personUID = personUID;
	}
	
}
