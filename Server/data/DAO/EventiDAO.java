package DAO;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.LinkedList;

import entita.Eventi;

public interface EventiDAO {
	public void Create(Connection con, String nome, String descr, String citta, String categoria, LocalDate data, double prezzo, int biglietti, String nomeImmagine);//insert
	public LinkedList<Eventi> Research(Connection con, String nome, String descr, String citta, String categoria, LocalDate data1, LocalDate data2, double prezzo, int biglietti);
	public void Update(Connection con, String nome, int codiceUpdate, String descr, String citta, String categoria, LocalDate data, double prezzo, int biglietti, String nomeImmagine);
	public void Delete(Connection con, int codiceDelete);
}

//String nomeImmagine sarà il nome del file dell'immagine, il percorso sarà costruito all'interno della funzione