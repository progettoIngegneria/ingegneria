package DAO;

import java.sql.Connection;

import entita.Amministratori;

public interface AmministratoriDAO {
	public void Create(Connection con, String nome, String usr, String paswd);//insert
	public Amministratori Research(Connection con, String usr, String paswd);
	public void Update(Connection con, String nome, String usr, String paswd);
	public void Delete(Connection con, String nome, String usr, String paswd);
}
