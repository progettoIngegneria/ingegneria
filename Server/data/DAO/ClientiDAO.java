package DAO;

import java.sql.Connection;
import java.time.LocalDate;

import entita.Clienti;

public interface ClientiDAO {
	public void Create(Connection con, String nome, String cognome, String usr, String paswd, LocalDate Nascita, String cell, String PaeseNatale, String ComuneNatale, String indirizzo, String email);//insert
	public Clienti Research(Connection con, String usr, String paswd);
	public void Update(Connection con, String nome, String cognome, String usr, String paswd, LocalDate Nascita, String cell, String PaeseNatale, String ComuneNatale, String indirizzo, String email);
	public void Delete(Connection con, String nome, String cognome, String usr, String paswd, LocalDate Nascita, String cell, String PaeseNatale, String ComuneNatale, String indirizzo, String email);
}
