package com.itelis.worker.dev.experteofeed;


import java.io.Serializable;
import java.util.Arrays;

import com.itelis.worker.dev.jesque.Job;

import net.greghaines.jesque.Config;
import net.greghaines.jesque.ConfigBuilder;
import net.greghaines.jesque.worker.Worker;
import net.greghaines.jesque.worker.WorkerImpl;

/**
 * 
 * @author echokodjeu
 *
 */
public class Experteofeed implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args){
		
		// Configuration
		final Config config = new ConfigBuilder().withJobPackage("com.itelis.worker.dev.jesque").withHost("192.168.8.30").withPort(6390).build();
		
		// Start a worker to run jobs from the queue
		final Worker worker = new WorkerImpl(config, Arrays.asList("experteo-integration"), Arrays.asList(Job.class));
		final Thread workerThread = new Thread(worker);
		workerThread.start();
		
		// Normally, we'd just keep running but for demo purposes we'll just wait a few secs then shutdown
		try { Thread.sleep(5000); } catch (Exception e){} // Give ourselves time to process
		worker.end();
		try { workerThread.join(); } catch (Exception e){ e.printStackTrace(); }
		
	}
}
