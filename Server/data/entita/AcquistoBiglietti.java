package entita;

public class AcquistoBiglietti {
	private int CodiceCliente, CodiceEvento, numBiglietti, CodiceAcquisto;
	private String nomeFileQr;
	public int getNumBiglietti() {
		return numBiglietti;
	}
	public void setNumBiglietti(int numBiglietti) {
		this.numBiglietti = numBiglietti;
	}
	public int getCodiceCliente() {
		return CodiceCliente;
	}
	public void setCodiceCliente(int codiceCliente) {
		CodiceCliente = codiceCliente;
	}
	public String getNomeFileQr() {
		return nomeFileQr;
	}
	public void setNomeFileQr(String nomeFileQr) {
		this.nomeFileQr = nomeFileQr;
	}
	public int getCodiceEvento() {
		return CodiceEvento;
	}
	public void setCodiceEvento(int codiceEvento) {
		CodiceEvento = codiceEvento;
	}
	public int getCodiceAcquisto() {
		return CodiceAcquisto;
	}
	public void setCodiceAcquisto(int codiceAcquisto) {
		CodiceAcquisto = codiceAcquisto;
	}
}
