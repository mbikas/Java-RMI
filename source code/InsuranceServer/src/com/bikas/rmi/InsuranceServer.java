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

public class InsuranceServer {

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
	 * initializes the ip addresses and port numbers for both Health Exchange Server and Insurance Server
	 * @throws UnknownHostException
	 */
	public void setValues() throws UnknownHostException
	{
		ipInsurance.setText(getIPAddress());
		ipInsurance.setEnabled(false);
		ipInsurance.setEditable(false);
		portInsurance.setText(""+Constants.RMI_PORT_INSURANCE);
		
		ipHospital.setText(""+Constants.RMI_ADDRESS_HOSPITAL);
		portHospital.setText(""+Constants.RMI_PORT_HOSPITAL);
		
	}
	
	/**
	 * Starts the Insurance Server for incoming client (Health Exchange)
	 * Create registry entry to the given port (default port 5002)
	 * 
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	private void startServer() throws RemoteException, AlreadyBoundException{
		try{
			InsuranceImplementation impl = new InsuranceImplementation();
			impl.setIPAddress(ipHospital.getText());
			impl.setPortNumebr(Integer.parseInt(portHospital.getText()));
			
			Registry registry = LocateRegistry.createRegistry(Integer.parseInt(portInsurance.getText()));
			registry.bind(Constants.RMI_ID, impl); 
			
			logMessage("Insurance Server Started");
			
		} catch (Exception exp)
		{
			logMessage("ObjID already in use.\nPlease kill the process running at port 5002");
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
	private JTextField ipInsurance;
	private JTextField portInsurance;
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
					InsuranceServer window = new InsuranceServer();
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
	public InsuranceServer() throws UnknownHostException {
		initialize();
		setValues();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 558, 493);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblPatientClient = new JLabel("RMI Server/Client: Insurance Company\n");
		lblPatientClient.setBounds(164, 6, 250, 16);
		frame.getContentPane().add(lblPatientClient);
		
		JLabel lblNewLabel = new JLabel("INSURANCE SERVER:");
		lblNewLabel.setBounds(6, 38, 133, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("IP ADDRESS");
		lblNewLabel_1.setBounds(30, 57, 106, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblPortNumber = new JLabel("PORT NUMBER");
		lblPortNumber.setBounds(295, 57, 106, 16);
		frame.getContentPane().add(lblPortNumber);
		
		ipInsurance = new JTextField();
		ipInsurance.setBounds(134, 51, 145, 28);
		frame.getContentPane().add(ipInsurance);
		ipInsurance.setColumns(10);
		
		portInsurance = new JTextField();
		portInsurance.setBounds(399, 51, 145, 28);
		frame.getContentPane().add(portInsurance);
		portInsurance.setColumns(10);
		
		JButton btnNewButton = new JButton("Start Insurance Server");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//checking input validity
				if(portInsurance.getText().isEmpty() || portHospital.getText().isEmpty() || ipHospital.getText().isEmpty()){
					logMessage("Invalid Input");
					return;
				} else if(Constants.isInteger(portHospital.getText()) == false || Constants.isInteger(portInsurance.getText()) == false )
				{
					logMessage("Port number should be a positive integer.");
					return;
				} else 
				
				
				try {
					startServer();
				} catch (RemoteException | AlreadyBoundException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(194, 138, 171, 29);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblInsuranceServer = new JLabel("HOSPITAL SERVER:");
		lblInsuranceServer.setBounds(6, 85, 133, 16);
		frame.getContentPane().add(lblInsuranceServer);
		
		JLabel label_1 = new JLabel("IP ADDRESS");
		label_1.setBounds(30, 104, 106, 16);
		frame.getContentPane().add(label_1);
		
		ipHospital = new JTextField();
		ipHospital.setText("localhost");
		ipHospital.setColumns(10);
		ipHospital.setBounds(134, 98, 145, 28);
		frame.getContentPane().add(ipHospital);
		
		portHospital = new JTextField();
		portHospital.setText("5001");
		portHospital.setColumns(10);
		portHospital.setBounds(399, 98, 145, 28);
		frame.getContentPane().add(portHospital);
		
		JLabel label_2 = new JLabel("PORT NUMBER");
		label_2.setBounds(295, 104, 106, 16);
		frame.getContentPane().add(label_2);
		
		JLabel label = new JLabel("Logs:");
		label.setBounds(6, 168, 37, 16);
		frame.getContentPane().add(label);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 198, 538, 260);
		frame.getContentPane().add(scrollPane);
		
		outputArea = new JTextArea();
		scrollPane.setViewportView(outputArea);
		frame.setVisible (true);
	}
}
