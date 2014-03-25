/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package xadrez;

import ui.Gui;
import ui.Jogador;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

/**
 * Xadrez é a classe motora do jogo, com main e muito mais!
 */
public class Xadrez {
	/**
	 * A main!
	 */
	public static void main (String[] args) {
		Jogador J1 = new Jogador();
		Jogador J2 = new Jogador();
		//Tabuleiro tab = Tabuleiro.getTabuleiro ();

		SwingUtilities.invokeLater (new Runnable() {
			@Override
			public void run () {
				jogar ();
			}
        });
	}
	
	private static void jogar () {
		Gui tela = Gui.getTela ();
		tela.init (new Xadrez ());
		tela.setVisible (true);
	}
	
	
	// diz se já clicou em uma casa válida (ali pra 'cliquei')
	private static boolean casa_marcada = false;
	// casa anterior clicada, se já tivar marcada (ali pra 'cliquei')
	private static Casa anterior = null;
	// movimentos possíveis da peça marcada
	private static ArrayList<Movimento> mov;
	
	public void cliquei (int linha, int coluna) {
		// casa clicada
		Casa atual = Tabuleiro.getTabuleiro ().getCasa (linha, coluna);
		if (casa_marcada == false) {
			// se tem peça lá dentro
			if (atual.estaOcupada ()) {
				anterior = atual;
				mov = atual.getPeca ().possiveisMovimentos (atual.getLinha (), atual.getColuna ());
				casa_marcada = true;
			}
		}
		else {
			mov.get (0).mover ();
			System.out.println ("tinha marcado a " + anterior.getLinha () + " " + anterior.getColuna ());
			casa_marcada = false;
		}
	}
}
