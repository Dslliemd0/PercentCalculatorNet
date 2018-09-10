package com.infin.calc.percent;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CalculatePercentClient {
	Scanner inputVal = new Scanner(System.in);
	Scanner inputVal2 = new Scanner(System.in);
	Socket sock;
	PrintWriter writer;
	InputStreamReader isReader;
	BufferedReader reader;
	public void go() {
		setUpNetworking();
		sendData();
		recieveResult();
		closeConnections();
	}
	private void setUpNetworking() {
		try {
			sock = new Socket("127.0.0.1", 4242);
			writer = new PrintWriter(sock.getOutputStream());
			isReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(isReader);
			System.out.println("Connection established to server");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	private String askPercentToCalculate() {
		System.out.println("Input whole number to calculate percents form it:");
		double wholeNumber = inputVal.nextDouble();
		System.out.println("Input percent to calculate from previously inputed number:");
		double percent = inputVal2.nextDouble();
		return wholeNumber + "," + percent;
	}
	private void sendData() {
		try {
			writer.println(askPercentToCalculate());
			writer.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	private void recieveResult() {
		String result = null;
		try {
			while ((result = reader.readLine()) != null) {
				System.out.println(result);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
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
	public static void main(String[] args) {
		CalculatePercentClient calcPercent = new CalculatePercentClient();
		calcPercent.go();
	}
}
