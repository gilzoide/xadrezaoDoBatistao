/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 09/03/2014
 */
package xadrez;

import ui.*;
import javax.swing.SwingUtilities;

class Xadrez {
	public static void main (String[] args) {
		Jogador J1 = new Jogador();
		Jogador J2 = new Jogador();
		//Tabuleiro tab = Tabuleiro.getTabuleiro ();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Gui tela = Gui.getTela ();
				tela.setVisible (true);
			}
        });
	}
}
