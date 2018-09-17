package DAO.ImplementazioneDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import DAO.*;
import entita.Clienti;

public class ClientiDaoImplementazione implements ClientiDAO {

	@Override
	public void Create(Connection con, String nome, String cognome, String usr, String paswd, LocalDate Nascita, String cell,
			String PaeseNatale, String ComuneNatale, String indirizzo, String email) {
		try {
			//settaggio per la data	
			java.sql.Date data = java.sql.Date.valueOf(Nascita);
			//preparedStatement per la query
			PreparedStatement prepared = con.prepareStatement("insert into clienti (Nome,Cognome,Username,Password,DataNascita,Cellulare,PaeseNascita,ComuneNascita,Indirizzo,Email) values (?,?,?,?,?,?,?,?,?,?)");
			prepared.setString(1, nome);//Nome
			prepared.setString(2, cognome);//Cognome
			prepared.setString(3, usr);//Username
			prepared.setString(4, paswd);//Password
			prepared.setDate(5, data);//dataNascita
			prepared.setString(6,cell);//Cellulare
			prepared.setString(7,PaeseNatale);//Paese di Nascita
			prepared.setString (8,ComuneNatale);//Comune di Nascita
			prepared.setString (9,indirizzo);//Indirizzo
			prepared.setString (10,email);//email
			prepared.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Clienti Research(Connection con, String usr, String paswd) {
		Clienti cliente = new Clienti();
		try {
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("select * from clienti where Username='"+usr+"' &&Password='"+paswd+"'");
			while (rs.next()) {
				cliente.setCodice(rs.getInt("Codice"));
				cliente.setNome(rs.getString("Nome"));
				cliente.setCognome(rs.getString("Cognome"));
				cliente.setUsr(rs.getString("Username"));
				cliente.setPaswd(rs.getString("Password"));
				cliente.setNascita(rs.getDate("DataNascita"));
				cliente.setCell(rs.getString("Cellulare"));
				cliente.setPaeseNatale(rs.getString("paeseNatale"));
				cliente.setComuneNatale(rs.getString("ComuneNascita"));
				cliente.setIndirizzo(rs.getString("Indirizzo"));
				cliente.setEmail(rs.getString("Email"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return cliente;
	}

	@Override
	public void Update(Connection con, String nome, String cognome, String usr, String paswd, LocalDate Nascita, String cell,
			String PaeseNatale, String ComuneNatale, String indirizzo, String email) {}

	@Override
	public void Delete(Connection con, String nome, String cognome, String usr, String paswd, LocalDate Nascita, String cell,
			String PaeseNatale, String ComuneNatale, String indirizzo, String email) {}
	}
