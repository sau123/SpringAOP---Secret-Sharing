Description : This project is to understand the secret sharing concept using Spring AOP implemented with AspectJ.

Instruction to run the application : The system should have maven installed so that the dependencies in the pom.xml
get downloaded when the project is run. Open the AppTest.java(jUnit Test Cases) and run it using IDE or command line. 
 
The access control logic and the logging aspects of the project are implemented using the SpringAOP with AspectJ.
Below are the control logic to be implemented for each of the functionality.


UUID storeSecret(String userId, Secret secret)
// Duplicate or null check performed

Secret readSecret(String userId, UUID secretId) 
// User who owns the secret or if the secret is shared with the user, the user can read the secret. 
// Unauthorized Exception is thrown if the user doesn't own the secret and tries to read it.

void shareSecret(String userId, UUID secretId, String targetUserId)
// Secret can be share only by someone who owns or shares the secret.
// If the user tries to share the secret and doesn't own or share it, Unauthorized exception is thrown.
// If the owner tries to share the secret with himself, the action is silently ignored.

void unshareSecret(String userId, UUID secretId, String targetUserId)
// Secret can be unshared only by the owner, if a person who shares the secret tries to unshare it, the action will be silently ignored.
// There is no effect if the owner tries to unshare the secret with himself.
