package com.bikas.rmi;


/**
 * 
 * @author bikas
 *
 */

public class Constants {
	
	
	//RMI application ID, used by Hospital, Insurance, Health Exchange and Client
	public static final String RMI_ID = "DoctorRMI";
	
	public static final String RMI_ADDRESS_HEALTH_EXCHANGE = "localhost";//"10.0.0.3";		//ip address for Health Exchange Server
	public static final String RMI_ADDRESS_INSURANCE = "localhost";//"10.0.0.6";	//ip address for Insurance Server
	public static final String RMI_ADDRESS_HOSPITAL = "localhost";					//ip address for Hospital Server
	
	
	public static final int RMI_PORT_HEALTH_EXCHANGE = 5001;			//port number for Health Exchange Server
	public static final int RMI_PORT_INSURANCE = 5002;		//port number for Insurance Server
	public static final int RMI_PORT_HOSPITAL = 5003;		//port number for Hospital Server
	
	
	/**
	 * utility function to check if a given string is integer
	 * @param s string to check
	 * @return true if integer otherwise false
	 */
	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        /*
	    	if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        */
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
}
