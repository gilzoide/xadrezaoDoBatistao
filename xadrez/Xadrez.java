/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 30/03/2014
 */
package xadrez;
/**
 * @todo cheque na jogada
 * @todo roque
 */

import ui.Gui;
import ui.Cor;
import ui.Jogador;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

/**
 * Xadrez é a classe motora do jogo, com main e muito mais!
 */
public class Xadrez {
	private	static Jogador J1;
	private static Jogador J2;
	private static Jogador jogador_da_vez;
	
	/**
	 * Ctor, a ser chamado ali em 'jogar' - inicia o motor do jogo (com os 'new')
	 */
	private Xadrez () {
		J1 = new Jogador (Cor.BRANCO);
		J2 = new Jogador (Cor.PRETO);
	}
	
	/**
	 * A main!
	 */
	public static void main (String[] args) {
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
	// movimentos possíveis da peça marcada (ali pra 'cliquei')
	private static ArrayList<Movimento> mov;
	/**
	 * Jogada em si: clica em uma peça [pra mover] e em outra pra mover pra lá
	 * 
	 * São dois estados: peça não marcada / peça marcada
	 */
	public void cliquei (Point P) {
		// casa clicada
		Casa atual = Tabuleiro.getTabuleiro ().getCasa (P);
		if (casa_marcada == false) {
			// se tem peça lá dentro
			if (atual.estaOcupadaCor (jogador_da_vez.getCor ())) {
				anterior = atual;
				mov = atual.getPeca ().possiveisMovimentos ();
				// se não tem movimento possível, nem adianta, cara =/
				if (mov.isEmpty ())
					return;
				
				// pra cada movimento possível faz ele aparecer possível
				for (Movimento m : mov)
					m.printPossivel ();
				
				// marquei a casa ;]
				casa_marcada = true;
			}
		}
		else {	// casa_marcada = true
			boolean clicou_movimento_previsto = false;
			Movimento a_ser_feito = null;
			// pra cada movimento anteriormente previso,
			for (Movimento m : mov) {
				// vê se o clicado atual é um movimento previsto
				if (m.ehEsseMovimento (P)) {
					// se sim, marca pra mover lá! (e marca q clicou em um previsto)
					a_ser_feito = m;
					clicou_movimento_previsto = true;
				}
				// descolore os quadradim de possibilidade
				m.unPrintPossivel ();
			}
			if (clicou_movimento_previsto) {
				a_ser_feito.jogaNoLog ();
				a_ser_feito.mover ();
				trocaJogador ();
			}
			casa_marcada = false;
		}
	}
	
	/**
	 * Inverte o jogador - passa a vez
	 */
	 private void trocaJogador () {
		 jogador_da_vez = (jogador_da_vez == J1) ? J2 : J1;
		 jogador_da_vez.update ();
		 Gui.getTela ().trocaJogador (jogador_da_vez);
	 }
	 /**
	  * Começa um novo jogo!
	  */
	 public static void novoJogo () {
		 // reconstrói as peças no tabuleiro
		 Tabuleiro.getTabuleiro ().novoJogo ();
		 // reinicia os jogadores
		 J1.novoJogo ();
		 J2.novoJogo ();
		 // só pra constar, o jogador branco é que começa
		 jogador_da_vez = J1;
	 }
}
