package com.perf.DBPerformanceTestUtil;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


public class App 
{
    public static void main( String[] args ) throws ClassNotFoundException, InterruptedException
    {
    	Class.forName("com.mysql.jdbc.Driver");
    	final Random random = new Random();
    	List<Thread> threads = new ArrayList<Thread>();
        
        Runnable runthread = new Runnable() {
			
			@Override
			public void run() {
				System.out.println("THreadId" + Thread.currentThread().getId());
				
				Connection jdbcConnection = null;
				try {
					jdbcConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "Backspace_2020");
					for(int p=0; p < 50000; p++) {
						
						System.out.println("Current step" + p);
						java.sql.PreparedStatement stmt = jdbcConnection.prepareStatement("Insert into users(username, firstname, lastname, email, department) values (?,?,?,?,?)");
						stmt.setString(1, "UN" + random.nextInt(1000000));
						stmt.setString(2, "Ramlal" + p);
						stmt.setString(3, "Sharma" + p);
						stmt.setString(4, "r@testdomain.local" + p);
						stmt.setString(5, "Sales");
						
						try {
							stmt.execute();
							System.out.println("THreadId execute sucess" + Thread.currentThread().getId());
						}catch (SQLException e) {
							//e.printStackTrace();
						}
						
				}
				}catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				finally {
					
					try {
						jdbcConnection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					} 
				}
				
				}
				
			};
		
        
        for(int i=0;i<30;i++) {
        
        	Thread thr = new Thread(runthread);
        	thr.start();
        	threads.add(thr);
        }
        //Join to main to avoid program termination before threads finish
        for (Thread thread : threads) {
        	thread.join();
			
		}
        
        	
        	
        	
        }
}

