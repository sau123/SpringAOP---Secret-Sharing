package com.saumeel.SpringAOP.aspect;

import java.util.HashMap;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.saumeel.SpringAOP.model.Secret;
import com.saumeel.SpringAOP.service.SecretServiceImplementation;
/*
 * This class(Aspect) takes care of the Acess Control Logic.
 */
@Aspect
public class LogicAspect {

	@Before("execution(* com.saumeel.SpringAOP.service.SecretServiceImplementation.storeSecret(..))")
	public void storeSecret(JoinPoint joinpoint){
		// duplicate or null check performed
		try
		{
			Object k[] = joinpoint.getArgs();

			String userID = (String)k[0];
			Secret secret = (Secret)k[1];
			if(secret==null || userID==null || userID=="")
				throw new NullPointerException();
		}
		catch(NullPointerException e)
		{
			throw new UnauthorizedException("Input can't be null. (Initialize your secret !)");
		}

	}

	@Before("execution(* com.saumeel.SpringAOP.service.SecretServiceImplementation.readSecret(..))")
	public void readSecret(JoinPoint joinpoint){
		// user who owns the secret or if the secret is shared with the user, the user can read the secret. 
		// Unauthorized Exception is thrown if the user doesn't own the secret and tries to read it.
		Object k[] = joinpoint.getArgs();

		String userID = (String)k[0];
		UUID secretID = (UUID)k[1];

		SecretServiceImplementation ko = (SecretServiceImplementation)joinpoint.getTarget();
		HashMap<UUID, Secret> map = ko.getMap();

		if(!map.containsKey(secretID))
			throw new UnauthorizedException("Secret doesn't exist!");
		else{
			Secret secret = map.get(secretID);
			if(!secret.getOwners().contains(userID))
				throw new UnauthorizedException(userID+" can't read the secret with ID "+secret.getUuid()); 
		}
	}

	@Around("execution(* com.saumeel.SpringAOP.service.SecretServiceImplementation.shareSecret(..))")
	public void share(ProceedingJoinPoint pjp) {
		// secret can be share only by someone who owns or shares the secret.
		// if the user tries to share the secret and doesn't own or share it, Unauthorized exception is thrown.
		// if the owner tries to share the secret with himself, the action is silently ignored.

		Object k[] = pjp.getArgs();

		String userID = (String)k[0];
		UUID secretID = (UUID)k[1];
		String targetUserID = (String)k[2];

		SecretServiceImplementation ko = (SecretServiceImplementation)pjp.getTarget();
		HashMap<UUID, Secret> map = ko.getMap();

		Secret s = map.get(secretID);
		// Exception if user doesn't own or share the secret
		if(!s.getOwners().contains(userID)){
			throw new UnauthorizedException(userID+" can't share this secret with ID "+s.getUuid());
		}

		if(!s.getOwners().contains(targetUserID))
			try{
				pjp.proceed();
			}
		catch(Throwable e){}

	}

	@Around("execution(* com.saumeel.SpringAOP.service.SecretServiceImplementation.unshareSecret(..))")
	public void unshare(ProceedingJoinPoint pjp) throws Throwable{
		// secret can be unshared only by the owner, if a person who shares the secret tries to unshare it, the action will be silently ignored.
		// There is no effect if the owner tries to unshare the secret with himself.
		Object k[] = pjp.getArgs();

		String userID = (String)k[0];
		UUID secretID = (UUID)k[1];
		String targetUserID = (String)k[2];

		SecretServiceImplementation ko = (SecretServiceImplementation)pjp.getTarget();
		HashMap<UUID, Secret> map = ko.getMap();
		Secret s = map.get(secretID);


		if (!s.getOwners().contains(userID))
			throw new UnauthorizedException(userID + " can't unshare the secret with ID "+s.getUuid());


		//if userId is not the owner, but still present in the list of owners, proceed.
		if(s.getOwners().get(0).equals(userID))
			pjp.proceed();
	}

}
