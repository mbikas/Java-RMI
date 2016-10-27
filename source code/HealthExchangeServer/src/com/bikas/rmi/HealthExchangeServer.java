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

public class HealthExchangeServer {
	
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
		ipHealthExchange.setText(getIPAddress());
		ipHealthExchange.setEnabled(false);
		ipHealthExchange.setEditable(false);
		portHealthExchange.setText(""+Constants.RMI_PORT_HEALTH_EXCHANGE);
		
		ipInsurance.setText(""+Constants.RMI_ADDRESS_INSURANCE);
		portInsurance.setText(""+Constants.RMI_PORT_INSURANCE);
		
	}
	
	/**
	 * Starts the Health Exchange Server for incoming client (Patient)
	 * Create registry entry to the given port (default port 5001)
	 * 
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	private void startServer() throws RemoteException, AlreadyBoundException{
		try{
			HealthExchangeImplementation impl = new HealthExchangeImplementation();
			impl.setIPAddress(ipInsurance.getText());
			impl.setPortNumebr(Integer.parseInt(portInsurance.getText()));
			
			Registry registry = LocateRegistry.createRegistry(Integer.parseInt(portHealthExchange.getText()));
			registry.bind(Constants.RMI_ID, impl); 
			logMessage("Health Exchange Server Started");
			
		} catch (Exception exp)
		{
			logMessage("ObjID already in use.\nPlease kill the process running at port 5001");
			exp.printStackTrace();
		} 
		
	}
	
	/**
	 * @return returns the IP address of the current machine
	 * @throws UnknownHostException
	 */
	private String getIPAddress() throws UnknownHostException{
		return InetAddress.getLocalHost().getHostAddress();
	}
	
	
	
	
	
	private JFrame frame;
	private JTextField ipHealthExchange;
	private JTextField portHealthExchange;
	private JTextField ipInsurance;
	private JTextField portInsurance;
	private static JTextArea outputArea;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HealthExchangeServer window = new HealthExchangeServer();
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
	public HealthExchangeServer() throws UnknownHostException {
		initialize();
		setValues();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 558, 492);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblPatientClient = new JLabel("RMI Server/Client: Health Exchage");
		lblPatientClient.setBounds(174, 6, 223, 16);
		frame.getContentPane().add(lblPatientClient);
		
		JLabel lblNewLabel = new JLabel("HEALTH EXCHANGE SERVER:");
		lblNewLabel.setBounds(6, 38, 198, 16);
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
		
		JButton btnNewButton = new JButton("Start Health Exchage Server");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//checking input validity
				if(portHealthExchange.getText().isEmpty() || portInsurance.getText().isEmpty() || ipInsurance.getText().isEmpty()){
					logMessage("Invalid Input");
					return;
				} else if(Constants.isInteger(portHealthExchange.getText()) == false || Constants.isInteger(portInsurance.getText()) == false)
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
		btnNewButton.setBounds(174, 142, 196, 29);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblInsuranceServer = new JLabel("INSURANCE SERVER:");
		lblInsuranceServer.setBounds(6, 85, 133, 16);
		frame.getContentPane().add(lblInsuranceServer);
		
		JLabel label_1 = new JLabel("IP ADDRESS");
		label_1.setBounds(30, 104, 106, 16);
		frame.getContentPane().add(label_1);
		
		ipInsurance = new JTextField();
		ipInsurance.setText("localhost");
		ipInsurance.setColumns(10);
		ipInsurance.setBounds(134, 98, 145, 28);
		frame.getContentPane().add(ipInsurance);
		
		portInsurance = new JTextField();
		portInsurance.setText("5001");
		portInsurance.setColumns(10);
		portInsurance.setBounds(399, 98, 145, 28);
		frame.getContentPane().add(portInsurance);
		
		JLabel label_2 = new JLabel("PORT NUMBER");
		label_2.setBounds(295, 104, 106, 16);
		frame.getContentPane().add(label_2);
		
		JLabel label = new JLabel("Logs:");
		label.setBounds(6, 167, 37, 16);
		frame.getContentPane().add(label);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 197, 538, 260);
		frame.getContentPane().add(scrollPane);
		
		outputArea = new JTextArea();
		scrollPane.setViewportView(outputArea);
		frame.setVisible (true);
	}
}
