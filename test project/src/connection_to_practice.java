
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.sql.Connection;
import java.sql.*;
public class connection_to_practice {
	public static void main(String[] args) throws Exception{
		Object[] options = {
				"ISS",
				"Mortality"
				
		};
		// TODO Auto-generated method stub
		ResultSet res = null;
		PreparedStatement Statement = null;
		Connection con = null;
		int lowISScutoff = 16;
		int lowISS = 0;
		int highISS = 0;
		int nulls = 0;
		int alive = 0;
		int dead = 0;
		int idk = 0;	
		ArrayList<String> reason = new ArrayList<String>();
		
		String [] choices = {"MORTALITY", "ISS", "CAUSE CODE"};
		String input = (String) JOptionPane.showInputDialog(null, "Which info do you want?",
		        "Choices", JOptionPane.QUESTION_MESSAGE, null, // Use
		                                                                        // default
		                                                                        // icon
		        choices, // Array of choices
		        choices[0]); // Initial choice  
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost:8889/actual_stuff_practice", "root", "root");
			//System.out.println("Connection Mode");
			Statement = con.prepareStatement("SELECT * FROM TABLE1");
			
			con.createStatement(res.TYPE_SCROLL_INSENSITIVE, res.CONCUR_READ_ONLY);
			
			res = Statement.executeQuery();
			//res.last();
			//System.out.println(res.getRow());
			//res.beforeFirst();
			//tables.TBL_NAME.displayData(res);
			//res.beforeFirst();
			
			
			//ISS IS DEALT WITH
			
			if(input.equals("ISS")) {
			while(res.next()) {
				//System.out.println(res.getInt(1)+"**"+res.getString(2));
				//x+=res.getInt(1);
			//	if(!res.getString(13).equals(" NA") || !res.getString(13).equals("UNK")) {
				if(isInteger(res.getObject(13))) {
				if(res.getInt(13)!=0) {
					if(res.getInt(13)<lowISScutoff)
						lowISS++;
					if(res.getInt(13)>lowISScutoff-1)
						highISS++;
				}
				
			}
				else {
					nulls++;
				}
			}
			JOptionPane.showMessageDialog(null, "Low ISS: "+lowISS+" High ISS: "+highISS+"\n"+"Amount of data that couldn't be read (ISS): "+nulls);
//			System.out.println("Low ISS: "+lowISS+" High ISS:"+highISS);
//			System.out.println("Amount of data that couldn't be read (ISS): "+nulls);
			}
			//resets res to the first row
			res.beforeFirst();
			
			//MORTALITY IS DEALT WITH 
			
			if(input.equals("MORTALITY")) {
			while(res.next()) {
				String mortality = res.getObject(30).toString();
				//mortality.replaceAll("\\s+","");
				mortality = mortality.trim();
				if(mortality.equals("A")) {
					alive++;
				}
				else {
				if(mortality.equals("D")) {
					dead++;
				}
				else {
					if(!mortality.equals("A") || !mortality.equals("B"))
						idk++;
				}
				}
				
			}
			JOptionPane.showMessageDialog(null, "Alive: "+alive+" Dead: "+dead+"\n"+"Amount of data that couldn't be read (alive or dead): "+idk);
		}
			//resets res to the first row
			res.beforeFirst();
			
		if(input.equals("CAUSE CODE")) {
			while(res.next()) {
				String whyinjured = res.getObject(3).toString();
				whyinjured = whyinjured.trim();
				boolean alreadythere = false;
				for(String s: reason) {
					if(s.equals(whyinjured))
						alreadythere = true;
				}
				
			}
		}
		}
		catch(Exception e) {
			System.err.println(e);}
		finally {
			if(res!=null) {
				res.close();
				//System.out.println("resultset closed");
			}
			if(Statement!=null) {
				Statement.close();
				//System.out.println("statement closed");
			}
			if(con!=null) {
				con.close();
				//System.out.println("connection closed");
			}
		}
		
	
	}
	public static boolean isInteger(Object object) {
		if(object instanceof Integer) {
			return true;
		} else {
			String string = object.toString();
			
			try {
				Integer.parseInt(string);
			} catch(Exception e) {
				return false;
			}	
		}
	  
	    return true;
	}
}
//