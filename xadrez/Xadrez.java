/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 20/04/2014
 */
package xadrez;
/**
 * @todo sons?! xD
 * @todo pt2 → relógios, exceção, desfaz/refaz, save/load
 */

import ui.Gui;
import ui.Cor;
import ui.Jogador;

import xadrez.tabuleiro.Casa;
import xadrez.tabuleiro.Tabuleiro;
import xadrez.movimento.Movimento;

import java.awt.Point;
import java.util.ArrayList;
import java.lang.NullPointerException;

import javax.swing.SwingUtilities;

/**
 * Xadrez é a classe motora do jogo, com main e muito mais!
 */
public class Xadrez {
	private	static Jogador J1;
	private static Jogador J2;
	private static Jogador jogador_da_vez;
	private static boolean partida;	// a partida tá rolando?
	
	/**
	 * Ctor, a ser chamado ali em 'jogar' - inicia o motor do jogo (com os 'new')
	 */
	private Xadrez () {
		J1 = new Jogador (Cor.BRANCO);
		J2 = new Jogador (Cor.PRETO);
		mov = new ArrayList<> ();
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
		if (partida) {
			// casa clicada
			Casa atual = Tabuleiro.getTabuleiro ().getCasa (P);
			if (casa_marcada == false) {
				// se tem peça lá dentro
				if (atual.estaOcupadaCor (jogador_da_vez.getCor ())) {
					anterior = atual;
					mov.clear ();
					try {
						mov.addAll (jogador_da_vez.getMovs (atual.getPeca ()));
					}
					// se não tem movimento possível, nem adianta, cara =/
					catch (NullPointerException ex) {
						return;
					}
					
					// pra cada movimento possível faz ele aparecer possível
					for (Movimento m : mov)
						m.printPossivel ();
					
					// marquei a casa ;]
					casa_marcada = true;
				}
			}
			else {	// casa_marcada = true
				Movimento a_ser_feito = null;
				// pra cada movimento anteriormente previso,
				for (Movimento m : mov) {
					// vê se o clicado atual é um movimento previsto
					if (m.ehEsseMovimento (P)) {
						// se sim, marca pra mover lá! (e marca q clicou em um previsto)
						a_ser_feito = m;
					}
					// descolore os quadradim de possibilidade
					m.unPrintPossivel ();
				}
				if (a_ser_feito != null) {
					a_ser_feito.jogaNoLog ();
					a_ser_feito.mover (jogador_da_vez);
					trocaJogador ();
				}
				casa_marcada = false;
			}
		}
	}
	
	/**
	 * Inverte o jogador - passa a vez
	 */
	private void trocaJogador () {
		if (partida) {
			// peões que tinham andado 2 casas agora não podem mais ser tomados por en passant
			jogador_da_vez.updatePiaums ();
			// troca o jogador
			jogador_da_vez = outroJogador ();
			Gui.getTela ().trocaJogador (jogador_da_vez);
			
			// checa possíveis movimentos (e já adiciona domínio nas casas)
			jogador_da_vez.update ();	// o da vez agora
			outroJogador ().update ();	// quem acabou de jogar
			// depois do domínio pronto, faz o rolê pros reis (que dependem do passo anterior)
			outroJogador ().updateRei ();	// quem acabou de jogar
			jogador_da_vez.updateRei ();	// o da vez agora
			
			// verifica situação de quem vai jogar: xeque/mate
			jogador_da_vez.checaXeque ();
		}
	}
	private Jogador outroJogador () {
		return (jogador_da_vez == J1) ? J2 : J1;
	}
	/* GETTERS */
	public static Jogador getDaVez () {
		return jogador_da_vez;
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
		// checa movimentos possíveis
		J1.update ();
		J2.update ();
		// só pra constar, o jogador branco é que começa
		jogador_da_vez = J1;
		// jogo tá rolando!
		partida = true;
	}
	/**
	 * Acabou a partida, seja pelo motivo que for
	 */
	public static void acabaPartida () {
		partida = false;
	}
}
