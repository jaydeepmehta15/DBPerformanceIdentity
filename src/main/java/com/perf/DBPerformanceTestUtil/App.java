package com.perf.DBPerformanceTestUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class App 
{
    public static void main( String[] args ) throws ClassNotFoundException
    {
    	Class.forName(null);
        
        Runnable runthread = new Runnable() {
			
			@Override
			public void run() {
				System.out.println("THreadId" + Thread.currentThread().getId());
				
				Connection jdbcConnection = null;
				try {
					jdbcConnection = DriverManager.getConnection(null, null, null);
					for(int p=0; p < 50000; p++) {
						
						java.sql.PreparedStatement stmt = jdbcConnection.prepareStatement("Insert into users(username, firstname, lastname, email, department) values (?,?,?,?,?)");
						stmt.setString(0, "UN" + p);
						stmt.setString(1, "Ramlal" + p);
						stmt.setString(2, "Sharma" + p);
						stmt.setString(3, "r@testdomain.local" + p);
						stmt.setString(4, "Sales");
						
						stmt.execute();
						System.out.println("THreadId execute sucess" + Thread.currentThread().getId());
				}
				}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					
					try {
						jdbcConnection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				}
				
			};
		
        
        for(int i=0;i<10;i++) {
        
        	Thread thr = new Thread(runthread);
        	thr.start();
        	
        }
        	
        	
        	
        	
        }
}

