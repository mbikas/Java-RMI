package com.bikas.rmi;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PatientClient {

	//variable used to keep log messages
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
	 * initializes the ip addresses and port numbers for the Health Exchange Server
	 */
	public void setValues()
	{
		ipHealthExchange.setText(Constants.RMI_ADDRESS_HEALTH_EXCHANGE);
		portHealthExchange.setText(""+Constants.RMI_PORT_HEALTH_EXCHANGE);
	}
	
	/**
	 * Invokes Health Exchange using Health Exchange Server ip, port to get the doctor name
	 * passes the patientName through DoctorInterface
	 * 
	 * @param patientName patient Name Given by the user
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	private void getDoctorHandler(String patientName) throws RemoteException, AlreadyBoundException{
		
		logMessage("Looking doctor for Object "+ patientName.hashCode());
		
		try{
			//getting the registry for Health Exchange Server
			Registry registry = LocateRegistry.getRegistry(ipHealthExchange.getText(), Integer.parseInt(portHealthExchange.getText()));
			DoctorInterfce doctor = (DoctorInterfce) registry.lookup(Constants.RMI_ID);
			
			
			String response = doctor.getDoctor(patientName);
			
			logMessage(response);			
		} catch (Exception exp)
		{
			String errorMessage = "Doctor Not Found for Patient " + patientName;
			errorMessage += "\nError during connecting Health Exchange server";
			logMessage(errorMessage);
			exp.printStackTrace();
		} 
		
	}
	
	
	
	
	
	private JFrame frame;
	private JTextField ipHealthExchange;
	private JTextField portHealthExchange;
	private JTextField patientNameText;
	private static JTextArea outputArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientClient window = new PatientClient();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PatientClient() {
		initialize();
		setValues();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 558, 464);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblPatientClient = new JLabel("RMI Client: Patient");
		lblPatientClient.setBounds(198, 6, 139, 16);
		frame.getContentPane().add(lblPatientClient);
		
		JLabel lblNewLabel = new JLabel("HEALTH EXCHANGE SERVER:");
		lblNewLabel.setBounds(6, 38, 184, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("IP ADDRESS");
		lblNewLabel_1.setBounds(30, 57, 106, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblPortNumber = new JLabel("PORT NUMBER");
		lblPortNumber.setBounds(295, 57, 106, 16);
		frame.getContentPane().add(lblPortNumber);
		
		ipHealthExchange = new JTextField();
		ipHealthExchange.setBounds(134, 51, 145, 28);
		frame.getContentPane().add(ipHealthExchange);
		ipHealthExchange.setColumns(10);
		
		portHealthExchange = new JTextField();
		portHealthExchange.setBounds(399, 51, 145, 28);
		frame.getContentPane().add(portHealthExchange);
		portHealthExchange.setColumns(10);
		
		JButton btnNewButton = new JButton("Get Doctor");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String patientName = patientNameText.getText();
				
				//checking input validity
				if(Constants.isInteger(portHealthExchange.getText()) == false)
				{
					logMessage("Port number should be a positive integer.");
					return;
				} else if(ipHealthExchange.getText().isEmpty()  || portHealthExchange.getText().isEmpty()){
					logMessage("Invalid Input");
					return;
				} else if(patientName.equalsIgnoreCase(""))
				{
					logMessage("Please enter patient name");
					return;
				}
				
				try {
					getDoctorHandler(patientName);
				} catch (RemoteException | AlreadyBoundException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(295, 116, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblLogs = new JLabel("Logs:");
		lblLogs.setBounds(6, 146, 37, 16);
		frame.getContentPane().add(lblLogs);
		
		patientNameText = new JTextField();
		patientNameText.setColumns(10);
		patientNameText.setBounds(31, 115, 243, 28);
		frame.getContentPane().add(patientNameText);
		
		JLabel lblEnterPatientName = new JLabel("Enter Patient Name:");
		lblEnterPatientName.setBounds(6, 91, 133, 16);
		frame.getContentPane().add(lblEnterPatientName);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 176, 538, 260);
		frame.getContentPane().add(scrollPane);
		
		outputArea = new JTextArea();
		scrollPane.setViewportView(outputArea);
		frame.setVisible (true);
	}
}
