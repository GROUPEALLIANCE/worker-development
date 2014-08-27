package com.itelis.worker.dev.jesque;

import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * Recuperation du flux
 * 
 * @author echokodjeu
 *
 */
public class Job implements Runnable{
	private static final Logger log = LoggerFactory.getLogger(Job.class);

	HashMap<String, String> data = new HashMap<String, String>();

	public Job( final HashMap<String, String> datalist)
	{

		 data = datalist;
	}
	Formatter output = new Formatter();
	Formatter outputConfig = new Formatter();
	String filename="";
	String queue="";
	public void run()
	{
		//log.info("Job.run() {} {} {} ", new Object[]{this.b, this.s, this.l});
		
		//Ouverture ConfigFile
		  try {
				outputConfig= new Formatter("ConfigFile");
			} catch (FileNotFoundException e1) {
						e1.printStackTrace();
				outputConfig.close();
		        System.out.println("File Doesn't Exist !!!");
			}
		
		//Recup filename
		for (String sdFlux:data.keySet()){     
		    if(("filename".equals(sdFlux))){
		    	filename=new String(data.get(sdFlux));	
		    	outputConfig.format(new String(filename));
	            System.out.println(new String(filename));
	          }		
		}
		//Ouveture ConfigQueue
		
		//Recup queue
		for (String sdFlux:data.keySet()){  
		    if(("queue".equals(sdFlux))){
		    	queue=new String(data.get(sdFlux));
	            System.out.println(new String(queue));
	          }   
			
		}
		
		//Ouverture fichier xml
		  try {
			output= new Formatter(filename);
		} catch (FileNotFoundException e1) {
					e1.printStackTrace();
			output.close();
	        System.out.println("File Doesn't Exist !!!");
		}
		  
	  //TODO:List<Object> flux=Arrays.asList(new Object[]{this.b});
		
	 //Recup raw_xml
		for (String sdFlux:data.keySet()){ 
	        byte[] encoded = data.get(sdFlux).getBytes();
	        //decoding byte array into base64
	        byte[] decoded = Base64.decodeBase64(encoded);      
	        
	        if(("raw_xml".equals(sdFlux))){
	            output.format(new String(decoded).concat("\n"));
	            System.out.println(new String(decoded));
	          }
					
		}
			
		outputConfig.close();
	    output.close();
		try { Thread.sleep(100); } catch (Exception e){}
	}

}
