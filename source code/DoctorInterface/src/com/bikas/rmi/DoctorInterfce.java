package com.bikas.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *  common interface used by Client, Health Exchange, Insurance and Hospital
 * 
 * @author bikas
 *
 */
public interface DoctorInterfce extends Remote {

	public String getDoctor(Object object) throws RemoteException;
}
