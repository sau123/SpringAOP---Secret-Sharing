package com.saumeel.SpringAOP.model;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Repository;

public class Secret {
//	Person[] owners;
	ArrayList<String> owners;
	String name;
	UUID uuid;
	
	public Secret(){
		owners = new ArrayList<String>();
	}	
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getOwners() {
		return owners;
	}
	public void setOwners(ArrayList<String> owners) {
		this.owners = owners;
	}
	
	// apart from POJO
	
	//add owner
	public void addOwner(String name){
		owners.add(name);
	}
	
	// return first owner
	public String getOwner(){
		return owners.get(0);
	}

	
}
