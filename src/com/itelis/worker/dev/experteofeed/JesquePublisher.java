package com.itelis.worker.dev.experteofeed;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import net.greghaines.jesque.Config;
import net.greghaines.jesque.ConfigBuilder;
import net.greghaines.jesque.Job;
import net.greghaines.jesque.client.ClientImpl;



import org.apache.commons.codec.binary.Base64;

public class JesquePublisher {
	public JesquePublisher(){}
	
	public static void publisher(String wsdl){

		//Read Data From File

		String data="";
		String name="";
		String wsdltemp=wsdl;

		try {

			File wsdlFile = new File(wsdltemp);

			name=wsdlFile.getName();


			Scanner scanner = new Scanner(wsdlFile);
			while (scanner.hasNextLine()) {
				// System.out.println(scanner.nextLine());
				data=data.concat(scanner.nextLine()).concat("\n");

			}          
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//Encoding  byte array into base 64
		byte[] encoded = Base64.encodeBase64(data.getBytes()); 
		String strData =new String(encoded);

		// Configuration
		final Config config = new ConfigBuilder().withJobPackage("com.itelis.worker.dev.jesque").withHost("192.168.8.30").withPort(6390).build();

		// Add a job to the queue
		HashMap<String, String> dataload = new HashMap<String, String>();
		dataload.put("raw_xml", strData);
		dataload.put("filename", name);
		dataload.put("queue", "experteo-development");
		
		final Job job = new Job("Job", new Object[]{dataload});


		final ClientImpl client = new ClientImpl(config);

		client.enqueue("experteo-development", job);

		client.end();

	}



	public static void main(String[] args){
		//Read Data From File

		String data="";
		String name="";

		try {

			File wsdlFile = new File("20140623-182103-gji1usik.xml");

			name=wsdlFile.getName();


			Scanner scanner = new Scanner(wsdlFile);
			while (scanner.hasNextLine()) {
				// System.out.println(scanner.nextLine());
				data=data.concat(scanner.nextLine()).concat("\n");

			}          
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//Encoding  byte array into base 64
		byte[] encoded = Base64.encodeBase64(data.getBytes()); 
		String strData =new String(encoded);

		// Configuration
		final Config config = new ConfigBuilder().withJobPackage("com.itelis.worker.dev.jesque").withHost("192.168.8.30").withPort(6390).build();

		// Add a job to the queue
		HashMap<String, String> dataload = new HashMap<String, String>();
		dataload.put("raw_xml", strData);
		dataload.put("filename", name);
		dataload.put("queue", "experteo-development");
		
		final Job job = new Job("Job", new Object[]{dataload});


		final ClientImpl client = new ClientImpl(config);

		client.enqueue("experteo-development", job);

		client.end();


	}

}
