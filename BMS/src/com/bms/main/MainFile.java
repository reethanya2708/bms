package com.bms.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class MainFile {
	private static final int NULL = 0;
	static String u="root";
	static String p="system";
	static String url="jdbc:mysql://localhost:3306/bms_pro";
	public  static String sql;
	public static Object account;
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		int a=1;
		while (true) {
			Scanner s=new Scanner(System.in);
			menu();
			System.out.println("Select Your Choice: ");
			int c=s.nextInt();
			if(c==1) {
				System.out.println("Open Account");
				open_ac();
			}
			else if(c==2) {
				System.out.println("Deposit Money");
				
				deposit(c, c, c);
			}
			else if(c==3) {
				System.out.println("Withdraw Money");
				withdraw(c, c, c);
			}
			else if(c==4) {
				System.out.println("Balance Enquire");
				bal_enq();
			}
			
			
			
				
			
			else if(c==5) {
				System.out.println("Program Stopped");
				break;
			}else {
				System.out.println("Enter the Valid Number");
			}
			
		}
	}
	
	

	

	public static void bal_enq() throws SQLException, ClassNotFoundException
    {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c=DriverManager.getConnection(url,u,p);
		
        try {
 
           Scanner s=new Scanner(System.in);
			int acc;
			System.out.println("enter account number:");
			 acc=s.nextInt();
			// query
            String sql = "select * from account where acc_no="
                  + acc;
            
			PreparedStatement st
                = c.prepareStatement(sql);
 
            ResultSet rs = st.executeQuery(sql);
            System.out.println(
                "-----------------------------------------------------------");
            System.out.printf("%12s %10s %10s\n",
                              "Account No", "Name",
                              "Balance");
 
            // Execution
 
            while (rs.next()) {
                System.out.printf("%12d %10s %10d.00\n",
                                  rs.getInt("acc_no"),
                                  rs.getString("name"),
                                  rs.getInt("opening_bal"));
            }
            System.out.println(
                "-----------------------------------------------------------\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
	public static boolean withdraw(int sender_ac,int reveiver_ac,int amount) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c=DriverManager.getConnection(url,u,p);
		Scanner s2=new Scanner(System.in);

		System.out.println("enter amount to withdraw:");
		 amount = s2.nextInt();
		System.out.println("enter account number to withdraw amount:");
		 sender_ac = s2.nextInt();

		
		// validation
		if (reveiver_ac == NULL || amount == NULL) {
		System.out.println("All Field Required!");
		return false;
		}
		try { c.setAutoCommit(false);
		String sql = "select * from account where acc_no="+ sender_ac;
		PreparedStatement ps
		= c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
		if (rs.getInt("opening_bal") < amount) {
		System.out.println(
		"Insufficient Balance!");
		return false;
		}
		}

		Statement st = c.createStatement();


		c.setSavepoint();

		sql = "update account set opening_bal=opening_bal-"+ amount + " where acc_no=" + sender_ac;
		if (st.executeUpdate(sql) == 1) {
		System.out.println("Amount withdrawn!");
		}

		
		sql = "update account set opening_bal=opening_bal+"+ amount + " where acc_no=" + reveiver_ac;st.executeUpdate(sql);

		c.commit();
		return true;
		}
		catch (Exception e) {
		e.printStackTrace();
		c.rollback();
		}

		return false;
		
	    
		
	}


public static boolean deposit( int sender_ac,int reveiver_ac,int amount)throws SQLException // transfer money method
, ClassNotFoundException
{Class.forName("com.mysql.cj.jdbc.Driver");
Connection c=DriverManager.getConnection(url,u,p);
Scanner s2=new Scanner(System.in);

System.out.println("enter amount to deposit:");
amount=s2.nextInt();
System.out.println("enter account number to deposit:");
reveiver_ac=s2.nextInt();

// validation
if (reveiver_ac == NULL || amount == NULL) {
System.out.println("All Field Required!");
return false;
}
try { c.setAutoCommit(false);
String sql = "select * from account where acc_no="+ sender_ac;
PreparedStatement ps
= c.prepareStatement(sql);
ResultSet rs = ps.executeQuery();

if (rs.next()) {
if (rs.getInt("opening_bal") < amount) {
System.out.println(
"Insufficient Balance!");
return false;
}
}

Statement st = c.createStatement();


c.setSavepoint();

sql = "update account set opening_bal=opening_bal-"+ amount + " where acc_no=" + sender_ac;
if (st.executeUpdate(sql) == 1) {
System.out.println("Amount Debited!");
}

// credit
sql = "update account set opening_bal=opening_bal+"+ amount + " where acc_no=" + reveiver_ac;st.executeUpdate(sql);

c.commit();
return true;
}
catch (Exception e) {
e.printStackTrace();
c.rollback();
}
// return
return false;
}

	public static void  menu() {
		System.out.println("*****************************");
		System.out.println("*     India Overseas Bank   *");
		System.out.println("*****************************");
		System.out.println("1. Open New Account");
		System.out.println("2. Deposit");
		System.out.println("3. Withdraw");
		System.out.println("4. Balance");
		System.out.println("5. Stop program");
		System.out.println("_____________________________");

	}
	public static void open_ac() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c=DriverManager.getConnection(url,u,p);
		Scanner s2=new Scanner(System.in);
		
		System.out.println("Enter ur Name: ");
		String name=s2.nextLine();
		System.out.println("Enter ur Phone: ");
		int phone=s2.nextInt();
		System.out.println("Enter ur DOB: ");
		int dob=s2.nextInt();
		System.out.println("Enter ur Account No: ");
		int acc=s2.nextInt();
		System.out.println("Enter ur Opening Balance: ");
		int open_bal=s2.nextInt();
		
		String q="insert into account values (?,?,?,?,?)";
		PreparedStatement ps=c.prepareStatement(q);
		ps.setString(1, name);
		ps.setInt(2, phone);
		ps.setInt(3, dob);
		
		ps.setInt(4, acc);
		ps.setInt(5, open_bal);
		ps.execute();
		c.close();
		System.out.println("Data inserted Sucessfully....");
		
		
		
		
	}
}

