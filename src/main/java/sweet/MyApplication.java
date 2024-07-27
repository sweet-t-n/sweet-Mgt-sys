package sweet;

import java.util.ArrayList;

public class MyApplication {
    static ArrayList<User> List_user = new ArrayList<User>();
	public boolean is_logged_in;
	
	
	
	
	
	
	  public MyApplication() {

	        List_user.add(new User("tasneem", "12345"));
	        List_user.add(new User("nareman", "99999"));
	    }
	  
	  
	public static ArrayList<User> getList_user() {
		return List_user;
	}


	public static void setList_user(ArrayList<User> list_user) {
		List_user = list_user;
	}


	


	


	   
	}
