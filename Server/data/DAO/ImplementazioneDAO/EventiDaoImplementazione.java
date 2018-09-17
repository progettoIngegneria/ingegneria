package DAO.ImplementazioneDAO;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;

import DAO.*;
import entita.Eventi;

public class EventiDaoImplementazione implements EventiDAO {

	@Override
	public void Create(Connection con, String nome, String descr, String citta, String categoria, LocalDate data,
			double prezzo, int biglietti, String nomeImmagine) {
		try {
			//settaggio per la data	
			java.sql.Date date = java.sql.Date.valueOf(data);
	
			//settaggio per importare l'immagine
			String path="A:\\Universita\\INGSFW\\Documentazione\\Codice\\Server\\Server\\images";
			char FS=File.separatorChar;
			File file = new File(path + FS +nomeImmagine);
			InputStream fin = new java.io.FileInputStream(file);
			int fileLength = (int)file.length();
		
			//preparedStatement per la query
			PreparedStatement prepared = con.prepareStatement("insert into eventi (Nome,Descrizione,Citta,Categoria,Data,Prezzo,BigliettiDisponibili,Immagini) values (?,?,?,?,?,?,?,?)");
			prepared.setString(1, nome);//nome
			prepared.setString(2, descr);//descrizione
			prepared.setString(3, citta);//citta
			prepared.setString(4, categoria);//categoria
			prepared.setDate(5, date);//data
			prepared.setDouble(6, prezzo);//prezzo
			prepared.setInt(7, biglietti);//biglietti disponibili
			prepared.setBinaryStream (8, fin, fileLength);//immagine
			prepared.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public LinkedList<Eventi> Research(Connection con, String nome, String descr, String citta, String categoria, LocalDate data1, LocalDate data2,
			double prezzo, int biglietti) {
		LinkedList<Eventi> listaEventi=new LinkedList<Eventi>();
		try {
			Statement stm= con.createStatement();
			ResultSet rs;
			if(nome==null && descr==null && citta==null && categoria==null && data1==null && data2==null && prezzo==0 && biglietti==0) rs = stm.executeQuery("select * from eventi ");
			else {
				String query="select * from eventi where ";
				if(nome != null) query = query + "Nome='"+nome+"'";
				if(descr != null) query = query + " && Descrizione='"+descr+"'";
				if(citta != null) query = query + " && Citta='"+citta+"'";
				if(categoria != null) query = query + " && Catehoria='"+categoria+"'";
				if(data1 != null && data2 != null) query = query + " && (Data BETWEEN '"+data1+"' AND '"+data2 +"')";
				else {
					if(data1 != null) query = query + " && Data >= '"+data1+"'";
					else query = query + " && Data <= '"+data2+"'";
				}
				if(prezzo != 0) query = query + " && Prezzo='"+prezzo+"'";
				if(biglietti != 0) query = query + " && BigliettiDisponibili='"+biglietti+"'";
				rs = stm.executeQuery(query);
			}
			Eventi temp=new Eventi();
				while (rs.next()) {
					temp.setNome(rs.getString("Nome"));//
					temp.setCategoria(rs.getString("Categoria"));//
					temp.setBiglietti(rs.getInt("BigliettiDisponibili"));
					temp.setCitta(rs.getString("Citta"));//
					temp.setData(rs.getDate("Data"));//
					temp.setDescr(rs.getString("Descrizione"));//
					temp.setCodice(rs.getInt("Codice"));
					temp.setPrezzo(rs.getDouble("Prezzo"));//
					listaEventi.add(temp);
				}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return listaEventi;
	}

	@Override
	public void Update(Connection con,  String nome, int codiceUpdate, String descr, String citta, String categoria, LocalDate data,
			double prezzo, int biglietti, String nomeImmagine) {
		try {
			PreparedStatement prepared = con.prepareStatement("update eventi set Nome=?, Descrizione = ?, Citta=?, Categoria=?,"
					+ "Data=?, Prezzo=?, BigliettiDisponibili=? where Codice = ?");
			prepared.setString(1, nome);//nome
			prepared.setString(2, descr);//descrizione
			prepared.setString(3, citta);//citta
			prepared.setString(4, categoria);//categoria
			//settaggio per la data	
			java.sql.Date date = java.sql.Date.valueOf(data);
			prepared.setDate(5,date);//data
			prepared.setDouble(6, prezzo);//prezzo
			prepared.setInt(7, biglietti);//numero biglietti disponibili
			prepared.setInt(8, codiceUpdate);//codice evento modificato

			prepared.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void Delete(Connection con, int codiceDelete) {
		try {
			PreparedStatement prepared = con.prepareStatement("delete from eventi where Codice= ? ");
			prepared.setInt(1, codiceDelete);
			prepared.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
