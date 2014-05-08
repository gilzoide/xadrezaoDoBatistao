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
	public void salvaPartida (File file) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject (partida);
	}
	/**
	 * Carrega partida
	 */
	public void carregaPartida (File file) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
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
