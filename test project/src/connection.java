import java.sql.DriverManager;
//import java.sql.Connection;
import java.sql.*;
public class connection {
	

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		ResultSet res = null;
		PreparedStatement Statement = null;
		Connection con = null;
		int x=0;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost:8889/CSV_DB", "root", "root");
			System.out.println("Connection Mode");
			Statement = con.prepareStatement("SELECT * FROM TBL_NAME");
			
			con.createStatement(res.TYPE_SCROLL_INSENSITIVE, res.CONCUR_READ_ONLY);
			
			res = Statement.executeQuery();
			res.last();
			System.out.println(res.getRow());
			res.beforeFirst();
			//tables.TBL_NAME.displayData(res);
			res.beforeFirst();
			while(res.next()) {
				System.out.println(res.getInt(1)+"**"+res.getString(2));
				x+=res.getInt(1);
				
			}
		}
		catch(Exception e) {
			System.err.println(e);}
		finally {
			if(res!=null) {
				res.close();
				System.out.println("resultset closed");
			}
			if(Statement!=null) {
				Statement.close();
				System.out.println("statement closed");
			}
			if(con!=null) {
				con.close();
				System.out.println("connection closed");
			}
		}
		System.out.println(x);
	}

}
