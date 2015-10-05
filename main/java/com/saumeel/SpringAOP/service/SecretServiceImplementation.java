package com.saumeel.SpringAOP.service;

import java.util.HashMap;
import java.util.UUID;

import com.saumeel.SpringAOP.model.Secret;
/*
 * This is the basic implementation of the Interface SecretService, where all the functionalities are defined.
 * The access control logic & logging is taken care by Spring Aspect Oriented programming with AspectJ. 
 */
public class SecretServiceImplementation implements SecretService{

	HashMap<UUID, Secret> map = new HashMap<UUID, Secret>();

	public HashMap<UUID, Secret> getMap() {
		return map;
	}

	public void setMap(HashMap<UUID, Secret> map) {
		this.map = map;
	}

	public UUID storeSecret(String userId, Secret secret) {	
		// when storeSecret is called, uuid is set to the secret.
		UUID firstUUID = UUID.randomUUID();
		secret.setUuid(firstUUID);
		secret.addOwner(userId);

		map.put(secret.getUuid(), secret);
		return secret.getUuid();
	}

	public Secret readSecret(String userId, UUID secretId) {
		return map.get(secretId);
	}

	public void shareSecret(String userId, UUID secretId, String targetUserId) {
		Secret s = map.get(secretId);
		s.addOwner(targetUserId);
	}

	public void unshareSecret(String userId, UUID secretId, String targetUserId) {
		Secret s = map.get(secretId);
		s.getOwners().remove(targetUserId);
	}

}
