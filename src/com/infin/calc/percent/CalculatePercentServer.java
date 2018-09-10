package com.infin.calc.percent;

import java.io.*;
import java.net.*;

public class CalculatePercentServer {
	ServerSocket serverSock;
	Socket sock;
	PrintWriter writer;
	InputStreamReader isReader;
	BufferedReader reader;
	public void go() {
		setUpNetworking();
		sendResult(calculatePercents(recieveData()));
		closeConnections();
	}
	private void setUpNetworking() {
		try {
			serverSock = new ServerSocket(4242);
			sock = serverSock.accept();
			writer = new PrintWriter(sock.getOutputStream());
			isReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(isReader);
			System.out.println("Connection established to client");	

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	private String recieveData() {
		String message = null;
		try {
			while ((message = reader.readLine()) != null) {
				System.out.println("read: " + message);
				return message;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return message;
	}
	private void closeConnections() {
		try {
			sock.close();
			writer.close();
			reader.close();
			System.out.println("Connections closed");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	private double calculatePercents(String percentData) {
		String[] data = percentData.split(",");
		double wholeNumber = Double.parseDouble(data[0]);
		double percent = Double.parseDouble(data[1]);
		return (wholeNumber / 100) * percent;
	}
	private void sendResult(double result) {
		try {
			writer.println("Result is: " + result + "%");
			writer.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void main(String[] args) {
		CalculatePercentServer calcPercent = new CalculatePercentServer();
		calcPercent.go();
	}

}
