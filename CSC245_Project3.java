package edu.arapahoe.csc245;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;
import com.google.gson.reflect.*;

////////////////////////////////////////////////////////////////////////////////////
//
// This program was created for Arapahoe Community College's CSC-245 course and
// identifies the current temperature for a location using the Open Weather Map API.
//
// The use of the API (openweathermap.org) was applied for and access granted 202010321
// The key comes with several technical constraints regarding its usage, including:
//     Hourly forecast: unavailable
//     Daily forecast: unavailable
//     Calls per minute: 60
//     3 hour forecast: 5 days
//
// Details on the use of the API can be found here:
//     https://openweathermap.org/current
//
// The default location is Castle Rock, CO (encoded as Castle Rock, US) but can be
// changed, as required.
//
// CSC 245 Secure Software Development
//
// Change log:
//      20210321 API access granted
//      20210322 Initially created (ddl)
//
// Dependencies:
//      gson-2.2.2.jar is needed for correct functioning
//		Oracle JDK 1.8
//
///////////////////////////////////////////////////////////////////////////////////
public class CSC245_Project3 {
	// Java Maps are used with many API interactions. OpenWeatherMap also uses Java Maps.
	public static Map<String, Object> jsonToMap(String str) {
		Map<String, Object> map = new Gson().fromJson(
				str, new TypeToken<HashMap<String, Object>>() {}.getType()
				);
		return map;
	}
			/* 
				MITIGATION 1: Java Coding Guidelines #22: Minimize the scope of variables
			 	  - ADDED String urlString to getTempForCity parameters with MITIGATION 1
			 */
	
	public static String getTempForCity (String cityString, String api_key, String urlString) {
		// RENAMED String urlString to urlStr - ADDED String urlString to parameter ^^^^^^^ with MITIGATION 1
		String urlStr = "http://api.openweathermap.org/data/2.5/weather?q=" + cityString + "&appid=" + api_key + "&units=imperial";
		try {
			StringBuilder result = new StringBuilder();
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			
			/* 
				MITIGATION 2: Java Coding Guidelines #54: Use braces for the body of an if, for, or while statement
				MITIGATION 3: Printing data from JSON file to console
				MITIGATION 4: CERT Oracle Secure Coding Standard for Java ERR01-J: DO not allow exceptions to expose sensitive information
			*/
			while ((line = rd.readLine()) != null) { // ADDED brace with MITIGATION 2 - opening while statements with braces for while loop
				result .append(line);
			} // ADDED brace with MITIGATION 2 - closing while statements with braces for while loop
			
			// REMOVED System.out.println(result); with MITIGATION 3 - Unwanted data from JSON file printing to console
			
			Map<String, Object > respMap = jsonToMap (result.toString());
			Map<String, Object > mainMap = jsonToMap (respMap.get("main").toString());
			
			return mainMap.get("temp").toString();

		} catch (IOException e){
			System.out.println(e.getMessage());
			// REMOVED "Temp not available (API problem?)" with MITIGATION 4
			return "Error, something went wrong."; // ADDED String "Error, something went wrong."
		}
	}

	public static void main(String[] args) {
			/*	
			 	MITIGATION 5: Java Coding Guidelines #38: Do not declare more than one variable per declaration
			 	MITIGATION 6: Java Coding Guidelines #39: Use meaningful symbolic constants to represent literal values in program logic
			 	MITIGATION 7: Java Coding Guidelines #55: Do not place a semicolon immediately following an if, for, or while condition
			 	MITIGATION 8: Java Coding Guidelines #22: Minimize the scope of variables
			 	MITIGATION 9: CERT Oracle Secure Coding Standard for Java FIO14-J: Perform proper cleanup at program termination
			*/
			// REMOVED String owm = "ae287b97260b43f6163b4ff080c5b11b", LOCATION = "Castle Rock, US"; with MITIGATION 5
			// ADDED String apiKEY = "ae287b97260b4f6163b4ff080c5b11b"; with MITIGATION 5
			// ADDED final with MITIGATION 6 -api key for Open Weather Maps
		final String apiKEY = "ae287b97260b43f6163b4ff080c5b11b";
			// ADDED String LOCATION = "Denver, CO US"; with MITIGATION 5
			// ADDED final with MITIGATION 6 -geological location
		final String LOCATION = "Denver, CO US";
			// REMOVED String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION + "&appid=" + owm + "&units=imperial"; with MITIGATION 5
			// ADDED final with MITIGATION 6 -api url for Open Weather Maps
		final String apiURL = "http://api.openweathermap.org/data/2.5/weather?q=";
		
			// REMOVED for(int i = 0; i < 10; i++;); with MITIGATION 7 - semicolon immediately after for loop parentheses & irrelevant for loop 
		System.out.println("Current temperature in " + LOCATION +" is: " + getTempForCity(LOCATION,apiKEY,apiURL) + " degrees.");
		
			// REMOVED urlString = ""; with MITIGATION 8 - avoiding the larger scope of using certain variables in certain areas of code that doesn't
			// use the variable
		
		System.exit(1); // ADDED System.exit(1); with MITIGATION 9 - proper termination method to allow for proper cleanup
	}
}



