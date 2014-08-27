package com.itelis.worker.dev.export;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;



/**
 * Recuperation des sources 
 * nom de fichier et nom de la queue
 * 
 * @author echokodjeu
 *
 */
public class ReportAction {


	public String filename="file";
	public String queue="queue";


	public ReportAction() {
		filename=render();
		queue=render1();
	}

	public  String render() {

		File worker = new File("configFile");
		String tmpLine="";
		try{
			Scanner scn = new Scanner(worker);
			while(scn.hasNext()){String line = scn.nextLine();
			//System.out.println(line);
			if(!(line.equals(""))) tmpLine=new String(line);


			}

		}
		catch(FileNotFoundException fnfe){
			System.out.println("File Doesn't Exist!!!");    

		}
		return new String(tmpLine);

	}

	public String render1() {

		File worker = new File("configQueue");
		String tmpLine="";
		try{
			Scanner scn = new Scanner(worker);
			while(scn.hasNext()){String line = scn.nextLine();
			//System.out.println(line);
			if(!(line.equals(""))) tmpLine=new String(line);

			}

		}
		catch(FileNotFoundException fnfe){
			System.out.println("File Doesn't Exist!!!");    

		}
		return new String(tmpLine);

	}

	public static void main(String[]args){

		File Lauren = new File("worker.txt");
		try{
			Scanner scn = new Scanner(Lauren);
			while(scn.hasNext()){String line = scn.nextLine();
			System.out.println(line);
			}

		}
		catch(FileNotFoundException fnfe){
			System.out.println("File Doesn't Exist!!!");    

		}

		Formatter output = new Formatter();
		try{
			output= new Formatter("worker.txt");
			output.format("Lauren");
			output.format("\nJohn Doe");
			output.close();
		}

		catch(FileNotFoundException fnfe){
			output.close();
			System.out.println("File Doesn't Exist !!!");
		}


		finally {
			output.close();

		}

	}

}
