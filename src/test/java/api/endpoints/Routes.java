package api.endpoints;


//Create Routes.java class under endpoints package which will contain all URLs
public class Routes {

	public static String baseURL = "https://petstore.swagger.io/v2";
	//User module URLs
	public static String post_url= baseURL+"/user";
	public static String get_url= baseURL+"/user/{username}";
	public static String update_url= baseURL+"/user/{username}";
	public static String delete_url= baseURL+"/user/{username}";
	
}
