package DAO;

import java.sql.Connection;

import entita.AcquistoBiglietti;

public interface AcquistoBigliettiDAO {
	public void Create(Connection con,int codiceCL, int codiceEv, int numBiglietti);//insert
	public AcquistoBiglietti Research(Connection con, int codiceCL, int codiceEv);
	public void Update(Connection con, int codiceCL, int codiceEv);
	public void Delete(Connection con, int codiceCL, int codiceEv);
}
