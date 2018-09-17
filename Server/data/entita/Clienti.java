package entita;

import java.util.Date;

public class Clienti {
	private String nome, cognome, usr, paswd, cell, PaeseNatale, ComuneNatale, indirizzo, email;
	private Date Nascita;
	private boolean loggato;
	private int Codice;
	
	public Clienti() {
		nome = null;
		cognome = null;
		usr = null;
		paswd = null;
		cell = null;
		PaeseNatale = null;
		ComuneNatale = null;
		indirizzo = null;
		email = null;
		loggato = false;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getNascita() {
		return Nascita;
	}
	public void setNascita(Date nascita) {
		Nascita = nascita;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getPaswd() {
		return paswd;
	}
	public void setPaswd(String paswd) {
		this.paswd = paswd;
	}
	public String getUsr() {
		return usr;
	}
	public void setUsr(String usr) {
		this.usr = usr;
	}
	public String getComuneNatale() {
		return ComuneNatale;
	}
	public void setComuneNatale(String comuneNatale) {
		ComuneNatale = comuneNatale;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public String getPaeseNatale() {
		return PaeseNatale;
	}
	public void setPaeseNatale(String paeseNatale) {
		PaeseNatale = paeseNatale;
	}
	public int getCodice() {
		return Codice;
	}
	public void setCodice(int codice) {
		Codice = codice;
	}
	public boolean isLoggato() {
		return loggato;
	}
	public void setLoggato(boolean loggato) {
		this.loggato = loggato;
	}
}
