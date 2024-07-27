package najah.edu.acceptance;


import sweet.MyApplication;
import sweet.User;

import static org.junit.Assert.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;




public class loginsteps {
	 MyApplication app;
	  
	 public loginsteps(  MyApplication ob) {
	 super();
		this.app=ob;
	 }

	 @Given("I am not in system")
	 public void iAmNotInSystem() {
	     assertFalse(app.is_logged_in);
	    
	 }
	 @When("set username {string} and pass {string}")
	 public void setUsernameAndPass(String name, String pass) {
		 User user = new User(name,pass);
		 
		 for(User u :  app.getList_user()) {
			 if(user.equals(u)) {
				 app.is_logged_in=true;
				 break;
			 }
		 }
	     
	   
	 }
	 @Then("login succeed")
	 public void loginSucceed() {
	    //assertTrue(app.is_logged_in);
	 }
	 
	 @Then("welcome message will be appeared")
		 public void welcomeMessageWillBeAppeared() {
		       
		       
		    }
	 
	

	 @When("set innalid username {string} and pass {string}")
	 public void setInnalidUsernameAndPass(String string, String string2) {
	    
	 }
	 @Then("user is now out of system")
	 public void userIsNowOutOfSystem() {
	    
	 }
	 @Then("failed ligin message will be appeared")
	 public void failedLiginMessageWillBeAppeared() {
	     
	 }






}
