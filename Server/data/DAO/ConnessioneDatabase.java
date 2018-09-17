package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {
	public Connection Connect() {
		Connection conn = null;
		String connectionString="jdbc:mysql://localhost:3306/eventmanagerdb?user=root&password=&useSSL=false";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException e){
			System.out.println(e);
		}
		try {
			conn = DriverManager.getConnection(connectionString );
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public void Disconnect(Connection con) {
		try{
			con.close();
		}catch(SQLException e) {
			System.out.println("Errore chiusura");
		}
	}
}
