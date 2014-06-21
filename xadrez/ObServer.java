/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 11/05/2014
 */
package xadrez;

import xadrez.movimento.Movimento;
import ui.Gui;
import ui.Cor;
import ui.Jogador;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Conexão Server/Cliente.
 * 
 * Implementa o padrão de projeto Observer, tendo como observado o Xadrez.
 */
public class ObServer extends Thread {
	private static boolean usando_rede = false;
	private ObjectInputStream entrada;
	private ObjectOutputStream saida;
	private Xadrez observado;
	private ServerSocket ouvido;
	private Socket conexao;
	private boolean devo_comunicar;
	
	private final int port = 8008;		/// A porta a ser usada, sempre a mesma
	
	/// De que lado da conexão estou?
	public enum Lado {
		SERVIDOR,
		CLIENTE
	}
	private Lado lado;
	
	/**
	 * Ctor
	 */
	public ObServer (Lado lado, Xadrez observado) {
		this.lado = lado;
		this.observado = observado;
		devo_comunicar = false;
	}
	
	public void run () {
		abreConexao (lado);
		while (true) {
			comunica (true);
		}
	}
	
	private void abreConexao (Lado lado) {
		try {
			if (lado == Lado.SERVIDOR) {
				ouvido = new ServerSocket (port);
				conexao = ouvido.accept ();
			}
			else {
				conexao = new Socket ();
				conexao.connect (new InetSocketAddress (Gui.getTela ().getEndereco (), port), 5000);
			}
			
			saida = new ObjectOutputStream (conexao.getOutputStream ());
			entrada = new ObjectInputStream (conexao.getInputStream ());
			usando_rede = true;
			System.err.println ("Conectado!");
			
			if (lado == Lado.CLIENTE)
				comunica (false);
		}
		catch (UnknownHostException ex) {
			System.err.println ("Servidor não encontrado!");
		}
		catch (IOException ex) {
			System.err.println ("Falha na abertura da conexão!\n");
			ex.printStackTrace ();
		}
		catch (IllegalArgumentException ex) {
			System.err.println ("Cancelou jogo em rede. Iniciando em modo offline");
		}
	}
	
	/**
	 * Retorna se jogo está em rede ou offline
	 */
	public static boolean estaEmRede () {
		return usando_rede;
	}
	
	
	/**
	 * Manda o movimento feito e recebe o do outro jogador
	 */
	public void comunica (boolean devo_mandar) {
		try {
			if (devo_comunicar) {
				Partida P;
				if (devo_mandar) {
					// manda
					P = observado.getPartida ();
					saida.writeObject (P);
					System.out.println ("mandei partida: " + P);
				}
				// recebe
				P = (Partida) entrada.readObject ();
				observado.setPartida (P);
				observado.refreshSnap ();
				System.out.println ("recebi partida: " + P);
				
				devo_comunicar = false;
			}
		}
		catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace ();
		}
	}
	
	public void update () {
		devo_comunicar = true;
	}
	
	/**
	 * Servidor é o Jogador Branco e Cliente é o Preto.
	 * Se as informações baterem, posso jogar!
	 */
	boolean possoJogar (Jogador J) {
		if (J.getCor () == Cor.BRANCO && lado == Lado.SERVIDOR || J.getCor () == Cor.PRETO && lado == Lado.CLIENTE)
			return true;
		else
			return false;
	}
	
	// Ao coletar um ObServer, fecha os canais
	@Override
	protected void finalize () throws Throwable {
		entrada.close ();
		saida.close ();
		conexao.close ();
		if (ouvido != null)
			ouvido.close ();
	}
}
