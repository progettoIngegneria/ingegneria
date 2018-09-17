package ServerMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import DAO.*;
import DAO.ImplementazioneDAO.*;
import java.sql.Connection;

public class MainServer {
/*
 * Il server deve gestire tutte le richieste inerenti al database condiviso in rete, quindi dovrà gestire il multithreading con relative
 * sincronizzazioni per le varie operazioni.
 * Deve fornire i risultati delle operazioni richieste in caso di ricerca, inviando al client i risultati delle query.
 * Deve avere una connessione disponibile a tutti.
 */
	
	protected int numPort = 8081;//modificare su host altervista	
	protected ServerSocket serverSocket = null;
	protected Socket clientSocket = null;
	protected Connection connessioneDB = null;
	protected AmministratoriDAO admin = new AmministratoriDaoImplementazione();
	protected AcquistoBigliettiDAO buy = new AcquistoBigliettiDaoImplementazione();
	protected ClientiDAO clientiStore = new ClientiDaoImplementazione();
	protected EventiDAO eventiStore = new EventiDaoImplementazione();
	
	
	public synchronized void ServizioOperativo() throws Exception {
		Runnable esegui = new Runnable() {
			@Override
			public void run() {
				System.out.print("Client connesso...");
				BufferedReader in=null;
				InputStreamReader isr;
				try {
					isr = new InputStreamReader(clientSocket.getInputStream());
					in = new BufferedReader(isr);
					String isAmministratore = in.readLine();
		System.out.println(isAmministratore);
		System.out.println("Letto");
					String applicativo = "Applicativo";
					
					OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
					BufferedWriter bw = new BufferedWriter(osw);
					PrintWriter out = new PrintWriter(bw, true);
					if(isAmministratore.compareTo(applicativo) == 0) {
						ServerApplicativo app = new ServerApplicativo(serverSocket, clientSocket, connessioneDB);
						out.println("ok");
						app.EseguiServerApplicativo();
					} else {
						ServerSito sito = new ServerSito(serverSocket, clientSocket, connessioneDB);
						out.println("ok");
						sito.EseguiServerSito();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		};
		serverSocket = new ServerSocket(numPort);
		System.out.println("EchoServer: started ");
		System.out.println("Server Socket: " + serverSocket);
		ConnessioneDatabase connect = new ConnessioneDatabase();
		connessioneDB =  connect.Connect();
		while(true) {//server sempre disponibile a nuove connessioni
			// bloccante finchè non avviene una connessione
			clientSocket = serverSocket.accept();
			System.out.println("Connection accepted: "+ clientSocket);
			Thread client = new Thread(esegui);
			client.start();
		}
	}
	
	public static void main(String[] args) {
		MainServer main = new MainServer();
		try{
			main.ServizioOperativo();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
