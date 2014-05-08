/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 20/04/2014
 */

package xadrez;

import java.io.*;
/**
 * Salva e carrega a partida
 */
public class SessionManager {
	private Partida partida;

	/**
	 * Salva a partida
	 */
	public void salvaPartida (String filename) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
		out.writeObject (partida);
	}
	/**
	 * Carrega partida
	 */
	public void carregaPartida (String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
		partida = (Partida) in.readObject ();
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
