package com.saumeel.SpringAOP;

import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.saumeel.SpringAOP.model.Secret;
import com.saumeel.SpringAOP.service.SecretService;
import com.saumeel.SpringAOP.service.SecretServiceImplementation;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	     	
     	ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
      	SecretService secretService = (SecretService)ctx.getBean("secretServiceImple");
      	
      	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	secretService.shareSecret("Alice", aliceSecret, "Bob");

      	UUID carlSecret = secretService.storeSecret("Carl", new Secret());
      	secretService.shareSecret("Bob", carlSecret, "Carl");

    }
}
