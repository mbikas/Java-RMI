package com.bikas.rmi;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HospitalServer {

	public static String log = "";
	
	/**
	 * outputs the log messages in console and also in the UI
	 * @param s message to log
	 */
	public static void logMessage(String s)
	{
		System.out.println(s);
		log += s + "\n\n";
		outputArea.setText(log);
	}
	
	/**
	 * initializes the ip addresses and port numbers of Hospital Server
	 * @throws UnknownHostException
	 */
	public void setValues() throws UnknownHostException
	{
		ipHospital.setText(getIPAddress());
		ipHospital.setEnabled(false);
		ipHospital.setEditable(false);
		portHospital.setText(""+Constants.RMI_PORT_HOSPITAL);
		
	}
	
	/**
	 * Starts the Hospital Server for incoming client (Insurance)
	 * Create registry entry to the given port (default port 5003)
	 * 
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	private void startServer() throws RemoteException, AlreadyBoundException{
		try{
			HospitalImplementaion impl = new HospitalImplementaion();
			Registry registry = LocateRegistry.createRegistry(Integer.parseInt(portHospital.getText()));
			registry.bind(Constants.RMI_ID, impl); 
			logMessage("Hospital Server Started");
			
		} catch (Exception exp)
		{
			logMessage("ObjID already in use.\nPlease kill the process running at port 5003");
			exp.printStackTrace();
		} 
		
	}
	
	/**
	 * @return returns the IP address of the current machine
	 * @throws UnknownHostException
	 */
	private String getIPAddress() throws UnknownHostException{
		return  InetAddress.getLocalHost().getHostAddress();
	}
	
	
	
	
	
	
	
	
	private JFrame frame;
	private JTextField ipHospital;
	private JTextField portHospital;
	private static JTextArea outputArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HospitalServer window = new HospitalServer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws UnknownHostException 
	 */
	public HospitalServer() throws UnknownHostException {
		initialize();
		setValues();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 558, 463);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblPatientClient = new JLabel("RMI Server: Hospital\n");
		lblPatientClient.setBounds(198, 6, 182, 16);
		frame.getContentPane().add(lblPatientClient);
		
		JLabel lblNewLabel = new JLabel("HOSPITAL SERVER:");
		lblNewLabel.setBounds(6, 38, 133, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("IP ADDRESS");
		lblNewLabel_1.setBounds(30, 57, 106, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblPortNumber = new JLabel("PORT NUMBER");
		lblPortNumber.setBounds(295, 57, 106, 16);
		frame.getContentPane().add(lblPortNumber);
		
		ipHospital = new JTextField();
		ipHospital.setBounds(134, 51, 145, 28);
		frame.getContentPane().add(ipHospital);
		ipHospital.setColumns(10);
		
		portHospital = new JTextField();
		portHospital.setBounds(399, 51, 145, 28);
		frame.getContentPane().add(portHospital);
		portHospital.setColumns(10);
		
		JButton btnNewButton = new JButton("Start Hospital Server");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//checking input validity
				if(portHospital.getText().isEmpty() || ipHospital.getText().isEmpty()){
					logMessage("Invalid Input");
					return;
				} else if(Constants.isInteger(portHospital.getText()) == false)
				{
					logMessage("Port number should be a positive integer.");
					return;
				} 
				
				
				try {
					startServer();
				} catch (RemoteException | AlreadyBoundException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(198, 112, 157, 29);
		frame.getContentPane().add(btnNewButton);
		
		JLabel label = new JLabel("Logs:");
		label.setBounds(6, 140, 37, 16);
		frame.getContentPane().add(label);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 170, 538, 260);
		frame.getContentPane().add(scrollPane);
		
		outputArea = new JTextArea();
		scrollPane.setViewportView(outputArea);
		frame.setVisible (true);
		
		
		
	}
}
