/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 11/05/2014
 */

package xadrez;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.io.*;

/**
 * Salva e carrega a partida
 */
public class SessionManager {
	private Xadrez xadrez;
	private Partida partida;
	private Timer timer;
	
	public SessionManager (Xadrez xadrez) {
		this.xadrez = xadrez;
		
		timer = new Timer (10000, new ActionListener () {
			public void actionPerformed (ActionEvent evt) {
				try {
					salvaPartida (new File (".", "autosave.sav"));
				}
				catch (IOException ex) {
					System.out.println ("Falha no autosave");
					ex.printStackTrace ();
				}
			}
		});
		
		timer.start ();
	}

	/**
	 * Salva a partida
	 */
	public void salvaPartida (File file) throws IOException {
		partida = xadrez.getPartida ();
		ObjectOutputStream out = new ObjectOutputStream (new FileOutputStream(file));
		out.writeObject (partida);
		out.close ();
	}
	/**
	 * Carrega partida
	 */
	public void carregaPartida (File file) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream (new FileInputStream(file));
		partida = (Partida) in.readObject ();
		xadrez.setPartida (partida);
		in.close ();
	}

	/* GETTERS */
	public Partida getPartida () {
		return partida;
	}
	/* SETTERS */
	public void setPartida (Partida partida) {
		this.partida = partida;
	}
}
