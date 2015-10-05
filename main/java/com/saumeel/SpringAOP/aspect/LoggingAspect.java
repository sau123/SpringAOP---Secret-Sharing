package com.saumeel.SpringAOP.aspect;

import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.saumeel.SpringAOP.model.Secret;


/**
 * This class(Aspect) takes care of all the logging done throughout the system.
 * @author Saumeel
 *
 */
@Aspect
public class LoggingAspect {

	@After("execution(* com.saumeel.SpringAOP.service.SecretServiceImplementation.storeSecret(..))")
	public void storeSecret(JoinPoint joinpoint){
		Object obj[]=joinpoint.getArgs();

		String userID = (String)obj[0];
		Secret secret = (Secret)obj[1];

		System.out.println(userID +" creates the secret with ID "+secret.getUuid());
	}

	@After("execution(* com.saumeel.SpringAOP.service.SecretServiceImplementation.readSecret(..))")
	public void readSecret(JoinPoint joinpoint){
		Object obj[]=joinpoint.getArgs();

		String userID = (String)obj[0];
		UUID secretID = (UUID)obj[1];

		System.out.println(userID +" reads the secret of ID "+secretID);
	}

	@After("execution(* com.saumeel.SpringAOP.service.SecretServiceImplementation.shareSecret(..))")
	public void shareSecret(JoinPoint joinpoint){
		Object obj[]=joinpoint.getArgs();

		String userID = (String)obj[0];
		UUID secretID = (UUID)obj[1];
		String targetUserID = (String)obj[2];

		System.out.println(userID +" shares the secret of ID "+secretID+" with "+targetUserID);
	}


	@After("execution(* com.saumeel.SpringAOP.service.SecretServiceImplementation.unshareSecret(..))")
	public void unshareSecret(JoinPoint joinpoint){
		Object obj[]=joinpoint.getArgs();

		String userID = (String)obj[0];
		UUID secretID = (UUID)obj[1];
		String targetUserID = (String)obj[2];

		System.out.println(userID +" unshares the secret of ID "+secretID+" with "+targetUserID);
	}	

}
