package com.bikas.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 
 * @author bikas
 *
 */
public class HospitalImplementaion extends UnicastRemoteObject implements DoctorInterfce{

	/**
	 * The serialVersionUID is used as a version control in a Serializable class.
	 */
	private static final long serialVersionUID = 3L;

	protected HospitalImplementaion() throws RemoteException {
		super();
	}

	@Override
	/**
	 * returns doctor name based on the patient name
	 * if patient name contains the string Bikas return Dr. X
	 * else return Dr. Y
	 * @return doctor name
	 * @param patientName name of the patient getting from Insurance
	 */
	public String getDoctor(Object object) throws RemoteException {
		
		String patientName = (String)object;
		HospitalServer.logMessage("Found Doctor for " + patientName + ". (Object ID: " + object.hashCode() + ")");
		
		if(patientName.toLowerCase().contains("bikas"))
			return "Dr. X" + " is assigned for Patient " + patientName;
		else
			return "Dr. Y" + " is assigned for Patient " + patientName;
	}

}
