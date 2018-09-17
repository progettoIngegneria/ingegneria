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
import java.util.Iterator;
import java.util.LinkedList;

import DAO.AcquistoBigliettiDAO;
import DAO.ClientiDAO;
import DAO.EventiDAO;
import DAO.ImplementazioneDAO.AcquistoBigliettiDaoImplementazione;
import DAO.ImplementazioneDAO.ClientiDaoImplementazione;
import DAO.ImplementazioneDAO.EventiDaoImplementazione;
import entita.Clienti;
import entita.Eventi;
/*
 * In questa classe verranno gestite tutte le operazioni richieste dai client collegati al sito web
 */

/*
 * FORMATO MESSAGGI:
 * Il formato dei messaggi in arrivo devono rispettare rigorosamente l'ordine dei parametri 
 */

public class ServerSito {
	protected ServerSocket serverSocket = null;
	protected Socket clientSocket = null;
	protected Connection connessioneDB = null;
	protected AcquistoBigliettiDAO buy = new AcquistoBigliettiDaoImplementazione();
	protected ClientiDAO clientiStore = new ClientiDaoImplementazione();
	protected EventiDAO eventiStore = new EventiDaoImplementazione();
	protected BufferedReader in=null;
	protected PrintWriter out=null;
	
	
	public ServerSito(ServerSocket server, Socket client, Connection db) {
		serverSocket = server;
		clientSocket = client;
		connessioneDB = db;
	}
	
	/*
	 * Questo metodo gestisce il server per il sito
	 */
	public void EseguiServerSito() {
		// creazione stream di input da clientSocket
		System.out.println("Sono il sito");
		return;}
		/*
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
			Clienti client = null;
			while(!fineSessione) {
				comandiSito = in.readLine();
				System.out.println("Comando richiesto dal sito: " + comandiSito);
				out.println(avanzaRichiesta);
				comando = Integer.parseInt(comandiSito);
				switch(comando) {
				case 1: RegistrazioneNuovoCliente();//ok
					break;
				case 2: AcquistoBigliettiEvento(client);//ok
					break;
				case 3: client = LoginCliente();//ok
					break;
				case 4: RicercaEventi();
					break;
				case 5: MostraTuttiEventi();
					break;
				}
				out.println(avanzaRichiesta);
			}
			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
	public void RegistrazioneNuovoCliente() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
			String date = in.readLine();
			//convert String to LocalDate
			LocalDate data = LocalDate.parse(date, formatter);
			clientiStore.Create(connessioneDB, in.readLine(), in.readLine(), in.readLine(), in.readLine(), data, in.readLine(), in.readLine(),in.readLine(),in.readLine(),in.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//invio messaggio di errore al sito per mancata registrazione
			out.println("Errore");
		}
		//invio messaggio di conferma al sito di avvenuta registrazione con successo
	}
	
	public Clienti LoginCliente() {
		Clienti client = new Clienti(); 
		try {
			client = clientiStore.Research(connessioneDB, in.readLine(),in.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			out.println("Errore");
			e.printStackTrace();
		}
		if ( client == null ) {
			System.out.println("Utente non registrato");
			return null;
		}
		else client.setLoggato(true);
		return client;
	}

	public void MostraTuttiEventi() {
		LinkedList<Eventi> lista = new LinkedList<>();
		lista = eventiStore.Research(connessioneDB, null, null, null, null, null, null, 0, 0); //ritorna l'elenco completo degli eventi
		//invio al sito l'elenco completo di tutti gli eventi disponibili
		Iterator<Eventi> itr = lista.iterator();
		while(itr.hasNext()) {
			Eventi temp = itr.next();
			//mettere tutti i campi
			out.println(temp.getNome());
			out.println(temp.getDescr());
			out.println(temp.getCategoria());
			out.println(temp.getCitta());
			out.print(temp.getData());
			out.println(temp.getBiglietti());
			out.println(temp.getPrezzo());
			out.println(temp.getNomeImmagine());
		}
	}

	
	public void AcquistoBigliettiEvento(Clienti client) {
		if (client == null ) return;
		try {
			//prima lettura è il codice dell'evento che si ricaverà il sito facendo una chiamata opportuna
			String temp;
			temp = in.readLine();
			int codiceEv = Integer.parseInt(temp);
			//la seconda lettura è il numero di biglietti da prendere
			temp = in.readLine();
			int numBiglietti = Integer.parseInt(temp);
			buy.Create(connessioneDB, client.getCodice(), codiceEv, numBiglietti);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//invio messaggio di errore al sito per mancanza acquisto biglietti
			out.println("Errore");
		}
		//invio al sito di avvenuta transizione per l'acquisto dei biglietti
	}
	
	public void RicercaEventi() {
		LinkedList<Eventi> lista = new LinkedList<>();
		try {
			lista = eventiStore.Research(connessioneDB, in.readLine(), null, null, null, null, null, 0, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			out.println("Errore");
			//e.printStackTrace();
		}
		//invio lista al sito
		Iterator<Eventi> itr = lista.iterator();
		while(itr.hasNext()) {
			Eventi temp = itr.next();
			//mettere tutti i campi
			out.println(temp.getNome());
			out.println(temp.getDescr());
			out.println(temp.getCategoria());
			out.println(temp.getCitta());
			out.print(temp.getData());
			out.println(temp.getBiglietti());
			out.println(temp.getPrezzo());
			out.println(temp.getNomeImmagine());
		}
	}
}
