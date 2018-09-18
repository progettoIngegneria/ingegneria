package ServerMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import DAO.AcquistoBigliettiDAO;
import DAO.AmministratoriDAO;
import DAO.EventiDAO;
import DAO.ImplementazioneDAO.AcquistoBigliettiDaoImplementazione;
import DAO.ImplementazioneDAO.AmministratoriDaoImplementazione;
import DAO.ImplementazioneDAO.EventiDaoImplementazione;
import entita.Eventi;

/*
 * In questa classe verranno gestite tutte le operazioni inerenti all'applicativo desktop java
 */

public class ServerApplicativo {
	protected ServerSocket serverSocket = null;
	protected Socket clientSocket = null;
	protected Connection connessioneDB = null;
	protected AcquistoBigliettiDAO buy = new AcquistoBigliettiDaoImplementazione();
	protected EventiDAO eventiStore = new EventiDaoImplementazione();
	protected AmministratoriDAO adminStore = new AmministratoriDaoImplementazione();
	protected BufferedReader in=null;
	protected PrintWriter out=null;
	
	public ServerApplicativo(ServerSocket server, Socket client, Connection db) {
		serverSocket = server;
		clientSocket = client;
		connessioneDB = db;
	}
	/*
	 * Il server per prima deve poter permettere il login all'amministratore e finchè non va a buon fine il login, le operazioni non sono disponibili
	 */
	public void EseguiServerApplicativo() {
		
		System.out.print("Ci sono...");
		// creazione stream di input da clientSocket
				InputStreamReader isr;
				String comandiSito;
				int comando;
				String avanzaRichiesta = "ok"; //variabile di servizio che mi serve per far continuare la comunicazione
				try {
					isr = new InputStreamReader(clientSocket.getInputStream());
					in = new BufferedReader(isr);
					// creazione stream di output su clientSocket
					OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
					BufferedWriter bw = new BufferedWriter(osw);
					out = new PrintWriter(bw, true);
					//ciclo di ricezione dal client e invio di risposta
		
					boolean fineSessione = false;
					while(adminStore.Research(connessioneDB,in.readLine(), in.readLine()).getUsr()==null) {
						//invio all'applicativo messaggio errore per login non effettuato
						System.out.println("Errore login");
					}
				System.out.println("Login effettuato");
					out.println(avanzaRichiesta);
					while(!fineSessione) {
						comandiSito = in.readLine();
						System.out.println("Comando richiesto dal sito: " + comandiSito);
						out.println(avanzaRichiesta);
						comando = Integer.parseInt(comandiSito);
						switch(comando) {
						case 1: InserimentoNuovoEvento();
							break;
						case 2: RicercaEventi();
							break;
						case 3: ModificaEvento();
							break;
						case 4: CancellazioneEvento();
							break;
						}
						out.println(avanzaRichiesta);
					}
					
					out.close();
					in.close();
					clientSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//invio messaggio d'errore all'applicativo
					//e.printStackTrace();
					out.println("Errore");
				}
	}
	
	public void InserimentoNuovoEvento() {
		//deve essere inviata prima la data poiche' deve essere prima processata per poi essere passata alla funzione di inserimento
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		String date;
		try {
			date = in.readLine();
			//convert String to LocalDate
			LocalDate data = LocalDate.parse(date, formatter);
			eventiStore.Create(connessioneDB, in.readLine(), in.readLine(), in.readLine(), in.readLine(), data, Double.parseDouble(in.readLine()), Integer.parseInt(in.readLine()), in.readLine());
		} catch (IOException e) {
			System.out.println("Errore IO:" + e.getMessage());
			out.println("Errore");//informo il client dell'errore verificatosi
		}
		//invio messaggio di conferma dell'inserimento del nuovo evento
		out.println("ok");
	}
	
	public void ModificaEvento() {
		//viene ricavato il codice dell'evento da modificare, in maniera tale che si prendono tutti i campi e poi si modifica quello selezionato
		//dall'applicativo tutti i form vengono riempiti dai valori correnti dell'evento, poi i stessi valori del form verranno passati al server per poi 
		//riscriverli nel database con le opportune modifiche
		//viene passata prima la data per processarla
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy"); //formattazione
		String tempData;
		try {
			tempData = in.readLine();
			LocalDate data = LocalDate.parse(tempData, formatter);
			eventiStore.Create(connessioneDB, in.readLine(),in.readLine(), in.readLine(), in.readLine(), data, Double.parseDouble(in.readLine()), Integer.parseInt(in.readLine()),in.readLine());
		} catch (IOException e) {
			//invio messaggio errore
			out.println("Errore");//informo il client dell'errore verificatosi
			System.out.println("Errore IO:" + e.getMessage());
		}
		//invio messaggio di avvenuta modifica
	}
	
	public void RicercaEventi(){
		//per la ricerca devono essere passati tutti i campi, e se non sono stati riempiti si passa il valore null
		LinkedList<Eventi> listaEventi = new LinkedList<>();
		//si inviano prima le due date, in sequenza crescente in maniera da poterle processare
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy"); //formattazione
		//prima data
		String tempData;
		LocalDate data1, data2;
		try {
			tempData = in.readLine();
			if(!tempData.equals("null")) {
				//convert String to LocalDate
				data1 = LocalDate.parse(tempData, formatter);
			} else data1 = null;
			//seconda data
			tempData = in.readLine();
			if(!tempData.equals("null")) {
				data2 = LocalDate.parse(tempData, formatter);
			} else data2 = null;
			listaEventi = eventiStore.Research(connessioneDB, in.readLine(), in.readLine(), in.readLine(), in.readLine(), data1, data2, Double.parseDouble(in.readLine()), Integer.parseInt(in.readLine()));
		} catch (IOException e) {
			System.out.println("Errore IO:" + e.getMessage());
			//invio messaggio di errore
			out.println("Errore");//informo il client dell'errore verificatosi
		}
		//invio lista eventi all'applicativo
		if (listaEventi == null) {
			System.out.println("No Result");
		}
		out.println("ok");
		for(Eventi i : listaEventi) {
			out.println(i.getCodice());//codice
			out.println(i.getNome());//nome
			out.println(i.getDescr());//descrizione
			out.println(i.getCitta());//citta
			out.println(i.getCategoria());//categoria
			out.println(i.getData());//data
			out.println(i.getPrezzo());//prezzo
			out.println(i.getBiglietti());//biglietti
			out.println(i.getNomeImmagine());//nomeImmagine
			out.println("si");//informo il client che ci sono ancora record da inviare
		}
	}
	
	public void CancellazioneEvento() {
		//il codice lo ricaverà il sito eseguendo prima la ricerca e poi passare il codice
		try {
			eventiStore.Delete(connessioneDB, Integer.parseInt(in.readLine()));
		} catch (NumberFormatException | IOException e) {
			System.out.println("Errore cancellazione:"+ e.getMessage());
			out.println("Errore");//informo il client dell'errore verificatosi
		}
	}
	
}
