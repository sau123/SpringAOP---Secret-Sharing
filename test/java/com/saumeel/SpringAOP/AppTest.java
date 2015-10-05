package com.saumeel.SpringAOP;

import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.saumeel.SpringAOP.model.Secret;
import com.saumeel.SpringAOP.service.SecretService;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test cases are written in this class.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

//Test A : Bob cannot read Alice’s secret, which has not been shared with Bob //fail
    @org.junit.Test
    public void testA()  
    {
    	boolean flag =false;
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
    	SecretService secretService = (SecretService)ctx.getBean("secretServiceImple");
    	try
    	{
    	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
     	Secret s = secretService.readSecret("Bob", aliceSecret);
     	
     	flag = true;     	
     	assertTrue(flag);
    	}
    	catch(Exception e){
    		assertTrue(flag);
    	}
    }
    
//Test B : Alice shares a secret with Bob, and Bob can read it.
      @org.junit.Test
      public void testB()  
      {
      	boolean flag =false;
      	ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
      	SecretService secretService = (SecretService)ctx.getBean("secretServiceImple");
      	try
      	{
      	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	secretService.shareSecret("Alice", aliceSecret, "Bob");
       	Secret s = secretService.readSecret("Bob", aliceSecret);
       	
       	flag = true;
       	
       	assertTrue(flag);
      	}
      	catch(Exception e){
      		assertTrue(flag);
      	}
      }
      
//Test C: Alice shares a secret with Bob, and Bob shares Alice’s key with Carl, and Carl can read this secret.
      @org.junit.Test
      public void testC()  
      {
      	boolean flag =false;
      	ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
      	SecretService secretService = (SecretService)ctx.getBean("secretServiceImple");
      	try
      	{
      	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	secretService.shareSecret("Alice", aliceSecret, "Bob");
      	secretService.shareSecret("Bob", aliceSecret, "Carl");
       	Secret s = secretService.readSecret("Carl", aliceSecret);
       	
       	flag = true;
       	
       	assertTrue(flag);
      	}
      	catch(Exception e){
      		assertTrue(flag);
      	}
      }

//Test D: Alice shares her secret with Bob; Bob shares Carl’s secret with Alice and encounters UnauthorizedException.
      @org.junit.Test
      public void testD()  
      {
      	boolean flag =false;
      	ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
      	SecretService secretService = (SecretService)ctx.getBean("secretServiceImple");
      	try
      	{
      	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	secretService.shareSecret("Alice", aliceSecret, "Bob");      	
      	UUID carlSecret = secretService.storeSecret("Carl", new Secret());      	
      	secretService.shareSecret("Bob", carlSecret, "Alice");
       	       	
       	flag = true;
       	
       	assertTrue(flag);
      	}
      	catch(Exception e){
      		assertTrue(flag);
      	}
      }

//Test E: Alice shares a secret with Bob, Bob shares it with Carl, Alice unshares it with Carl, and Carl cannot read this secret anymore.
      @org.junit.Test
      public void testE()  
      {
      	boolean flag =false;
      	ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
      	SecretService secretService = (SecretService)ctx.getBean("secretServiceImple");
      	try
      	{
      	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	secretService.shareSecret("Alice", aliceSecret, "Bob");
      	secretService.shareSecret("Bob", aliceSecret, "Carl");
      	secretService.unshareSecret("Alice", aliceSecret, "Carl");
      	secretService.readSecret("Carl", aliceSecret);
       	
       	
       	flag = true;
       	
       	assertTrue(flag);
      	}
      	catch(Exception e){
      		assertTrue(flag);
      	}
      }

//Test F: Alice shares a secret with Bob and Carl; Carl shares it with Bob, then Alice unshares it with Bob; Bob cannot read this secret anymore.
      @org.junit.Test
      public void testF()  
      {
      	boolean flag =false;
      	ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
      	SecretService secretService = (SecretService)ctx.getBean("secretServiceImple");
      	try
      	{
      	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	secretService.shareSecret("Alice", aliceSecret, "Bob");
      	secretService.shareSecret("Alice", aliceSecret, "Carl");
      	secretService.shareSecret("Carl", aliceSecret, "Bob");
      	secretService.unshareSecret("Alice", aliceSecret, "Bob");
      	secretService.readSecret("Bob", aliceSecret);       	
       	
       	flag = true;
       	
       	assertTrue(flag);
      	}
      	catch(Exception e){
      		assertTrue(flag);
      	}
      }

// Test G: Alice shares a secret with Bob; Bob shares it with Carl, and then unshares it with Carl. Carl can still read this secret.
      @org.junit.Test
      public void testG()  
      {
      	boolean flag =false;
      	ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
      	SecretService secretService = (SecretService)ctx.getBean("secretServiceImple");
      	try
      	{
      	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	secretService.shareSecret("Alice", aliceSecret, "Bob");
      	secretService.shareSecret("Bob", aliceSecret, "Carl");
      	secretService.unshareSecret("Bob", aliceSecret, "Carl");
      	secretService.readSecret("Carl", aliceSecret);    
      	
      	// control should go to next line, check
       	
       	flag = true;
       	
       	assertTrue(flag);
      	}
      	catch(Exception e){
      		assertTrue(flag);
      	}
      }

//Test H: Alice shares a secret with Bob; Carl unshares it with Bob, and encounters UnauthorizedException.
      @org.junit.Test
      public void testH()  
      {
      	boolean flag =false;
      	ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
      	SecretService secretService = (SecretService)ctx.getBean("secretServiceImple");
      	try
      	{
      	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	secretService.shareSecret("Alice", aliceSecret, "Bob");
        secretService.unshareSecret("Carl", aliceSecret, "Bob");
      	
      	// control should go to next line, check
       	
       	flag = true;
       	
       	assertTrue(flag);
      	}
      	catch(Exception e){
      		assertTrue(flag);
      	}
      }
      
//Test I: Alice shares a secret with Bob; Bob shares it with Carl; Alice unshares it with Bob; Bob shares it with Carl with again, and encounters UnauthorizedException. 
      @org.junit.Test
      public void testI()  
      {
      	boolean flag =false;
      	ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
      	SecretService secretService = (SecretService)ctx.getBean("secretServiceImple");
      	try
      	{
      	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	secretService.shareSecret("Alice", aliceSecret, "Bob");
        secretService.shareSecret("Bob", aliceSecret, "Carl");
        secretService.unshareSecret("Alice", aliceSecret, "Bob");
        secretService.shareSecret("Bob", aliceSecret, "Carl");
      	
       	flag = true;
       	
       	assertTrue(flag);
      	}
      	catch(Exception e){
      		assertTrue(flag);
      	}
      }

//Test J: Alice stores the same secrete object twice, and get two different UUIDs
      @org.junit.Test
      public void testJ()  
      {
      	boolean flag =false;
      	ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
      	SecretService secretService = (SecretService)ctx.getBean("secretServiceImple");
      	try
      	{
      	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	UUID aliceSecret1 = secretService.storeSecret("Alice", new Secret());

      	if(!aliceSecret.equals(aliceSecret1))
       	flag = true;
       	
       	assertTrue(flag);
      	}
      	catch(Exception e){
      		assertTrue(flag);
      	}
      }
      
}
