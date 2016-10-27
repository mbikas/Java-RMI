package com.bikas.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * 
 * @author bikas
 *
 */

public class HealthExchangeImplementation extends UnicastRemoteObject implements DoctorInterfce{

	
	/**
	 * The serialVersionUID is used as a version control in a Serializable class.
	 */
	private static final long serialVersionUID = 1L;

	protected HealthExchangeImplementation() throws RemoteException {
		super();
	}
	
	private int portNumber;
	private String ipAddress;
	
	/**
	 * 
	 * @param portNumber port number of the Insurance Server
	 */
	public void setPortNumebr(int portNumber){
		this.portNumber = portNumber;
	}
	
	/**
	 * 
	 * @param ipAddress ip address of the Insurance Server
	 */
	public void setIPAddress(String ipAddress){
		this.ipAddress = ipAddress;
	}
	
	
	@Override
	/**
	 * Implementation of the DoctorInterface
	 * Invokes InsuranceServer using ip and port to get the doctor name
	 */
	public String getDoctor(Object object) throws RemoteException {
		
		
		try {
			Registry registry = LocateRegistry.getRegistry(ipAddress, portNumber);
			DoctorInterfce doctor = (DoctorInterfce)registry.lookup(Constants.RMI_ID);
			HealthExchangeServer.logMessage("Looking doctor for Object "+ object.hashCode());
			return doctor.getDoctor(object);
			
		} catch (Exception e) {
			String errorMessage = "Doctor Not Found for Object " + object.hashCode();
			errorMessage += "\nError during connecting Insurance server";
			HealthExchangeServer.logMessage(errorMessage);
			return errorMessage;
		}
		
	}

}
