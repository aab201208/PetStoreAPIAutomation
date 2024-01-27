package api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payloads.User;
import io.restassured.response.Response;

public class UserTests {

	Faker faker;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setup()
	{
		faker = new Faker();
		userPayload = new User();
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		logger = LogManager.getLogger(this.getClass());
		logger.debug("debugging.....");
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("********Creating User*********");
		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("********User is created*********");
	}
	
	@Test(priority=2)
	public void testGetUserByName()
	{
		logger.info("********Getting User Info*********");
		Response response  = UserEndPoints.readUser(userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode() , 200);
		logger.info("********User Info is displayed*********");
	}
	
	@Test(priority=3)
	public void testUpdateUserByName()
	{
		logger.info("********Updating User Info*********");
		//Updating firstname, lastname and email
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoints.updateUser(userPayload.getUsername(), userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		Response responseafterupdate  = UserEndPoints.readUser(userPayload.getUsername());
		responseafterupdate.then().log().all();
		Assert.assertEquals(responseafterupdate.getStatusCode() , 200);
		
		logger.info("********User Info is updated*********");
	}
	
	@Test(priority=4)
	public void testDeleteUserByName()
	{
		logger.info("********Deleting User*********");
		Response response = UserEndPoints.deleteUser(userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("********User is deleted*********");
	}
}
