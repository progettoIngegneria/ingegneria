package DAO.ImplementazioneDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DAO.*;
import entita.Amministratori;
public class AmministratoriDaoImplementazione implements AmministratoriDAO {

	@Override
	public void Create(Connection con, String nome, String usr, String paswd) {
		try {
			PreparedStatement prepared = con.prepareStatement("insert into amministratori (Nome, Username, Password) values (?,?,?)");
			prepared.setString(1, nome);
			prepared.setString(2, usr);
			prepared.setString(3, paswd);
			prepared.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Amministratori Research(Connection con, String usr, String paswd) {
		Amministratori admin = new Amministratori();
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("select * from amministratori where Username='"+usr+"' && Password='"+paswd+"'");
			while (rs.next()) {
				admin.setNome(rs.getString("Nome"));
				admin.setUsr(rs.getString("Username"));
				admin.setPaswd(rs.getString("Password"));
	    	}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return admin;
	}

	@Override
	public void Update(Connection con, String nome, String usr, String paswd) {}

	@Override
	public void Delete(Connection con, String nome, String usr, String paswd) {}
	
}
