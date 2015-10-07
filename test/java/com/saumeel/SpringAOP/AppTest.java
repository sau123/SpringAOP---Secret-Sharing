package com.saumeel.SpringAOP;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.saumeel.SpringAOP.aspect.UnauthorizedException;
import com.saumeel.SpringAOP.model.Secret;
import com.saumeel.SpringAOP.service.SecretService;
//import junit.framework.Test;



/**
 * Unit test cases are written in this class.
 */
public class AppTest
{
	ApplicationContext ctx;
	SecretService secretService;
	
@Before
public void setUp(){
	ctx = new ClassPathXmlApplicationContext("spring.xml");
	secretService = (SecretService)ctx.getBean("secretServiceImple");
}

//Test A : Bob cannot read Alice’s secret, which has not been shared with Bob //fail
    @Test(expected = UnauthorizedException.class)
    public void testA()  
    {
    	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
     	Secret s = secretService.readSecret("Bob", aliceSecret);
    }
    
//Test B : Alice shares a secret with Bob, and Bob can read it.
      @Test
      public void testB()  
      {
      	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	secretService.shareSecret("Alice", aliceSecret, "Bob");
       	Secret s = secretService.readSecret("Bob", aliceSecret);
      }
      
//Test C: Alice shares a secret with Bob, and Bob shares Alice’s key with Carl, and Carl can read this secret.
      @Test
      public void testC()  
      {
      	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	secretService.shareSecret("Alice", aliceSecret, "Bob");
      	secretService.shareSecret("Bob", aliceSecret, "Carl");
       	Secret s = secretService.readSecret("Carl", aliceSecret);
      }

//Test D: Alice shares her secret with Bob; Bob shares Carl’s secret with Alice and encounters UnauthorizedException.
      @Test(expected = UnauthorizedException.class)
      public void testD()  
      {
      	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	secretService.shareSecret("Alice", aliceSecret, "Bob");      	
      	UUID carlSecret = secretService.storeSecret("Carl", new Secret());      	
      	secretService.shareSecret("Bob", carlSecret, "Alice");
      }

//Test E: Alice shares a secret with Bob, Bob shares it with Carl, Alice unshares it with Carl, and Carl cannot read this secret anymore.
      @Test(expected = UnauthorizedException.class)
      public void testE()  
      {
      	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	secretService.shareSecret("Alice", aliceSecret, "Bob");
      	secretService.shareSecret("Bob", aliceSecret, "Carl");
      	secretService.unshareSecret("Alice", aliceSecret, "Carl");
      	secretService.readSecret("Carl", aliceSecret);
      }

//Test F: Alice shares a secret with Bob and Carl; Carl shares it with Bob, then Alice unshares it with Bob; Bob cannot read this secret anymore.
      @Test(expected = UnauthorizedException.class)
      public void testF()  
      {
    	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	secretService.shareSecret("Alice", aliceSecret, "Bob");
      	secretService.shareSecret("Alice", aliceSecret, "Carl");
      	secretService.shareSecret("Carl", aliceSecret, "Bob");
      	secretService.unshareSecret("Alice", aliceSecret, "Bob");
      	secretService.readSecret("Bob", aliceSecret);       	
      }

// Test G: Alice shares a secret with Bob; Bob shares it with Carl, and then unshares it with Carl. Carl can still read this secret.
      @Test
      public void testG()  
      {
      	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	secretService.shareSecret("Alice", aliceSecret, "Bob");
      	secretService.shareSecret("Bob", aliceSecret, "Carl");
      	secretService.unshareSecret("Bob", aliceSecret, "Carl");
      	secretService.readSecret("Carl", aliceSecret);    
      }

//Test H: Alice shares a secret with Bob; Carl unshares it with Bob, and encounters UnauthorizedException.
      @Test(expected=UnauthorizedException.class)
      public void testH()  
      {
    	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	secretService.shareSecret("Alice", aliceSecret, "Bob");
        secretService.unshareSecret("Carl", aliceSecret, "Bob");
      }
      
//Test I: Alice shares a secret with Bob; Bob shares it with Carl; Alice unshares it with Bob; Bob shares it with Carl with again, and encounters UnauthorizedException. 
      @Test(expected = UnauthorizedException.class)
      public void testI()  
      {
      	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	secretService.shareSecret("Alice", aliceSecret, "Bob");
        secretService.shareSecret("Bob", aliceSecret, "Carl");
        secretService.unshareSecret("Alice", aliceSecret, "Bob");
        secretService.shareSecret("Bob", aliceSecret, "Carl");
      }

//Test J: Alice stores the same secrete object twice, and get two different UUIDs
      @Test
      public void testJ()  
      {
      	UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
      	UUID aliceSecret1 = secretService.storeSecret("Alice", new Secret());
      	boolean isSecretEqual = (aliceSecret==aliceSecret1);
      	assertEquals(false, isSecretEqual);
      }
      
}
