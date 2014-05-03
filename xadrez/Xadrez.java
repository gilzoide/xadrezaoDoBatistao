/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 20/04/2014
 */
package xadrez;
/**
 * @todo sons?! xD
 * @todo pt2 → relógios
 * @todo pt2 → exceção
 * @todo pt2 → refaz
 * @todo pt2 → save/load (autosave tb)
 */

import ui.Gui;
import ui.Cor;
import ui.Jogador;

import xadrez.tabuleiro.Casa;
import xadrez.tabuleiro.Tabuleiro;
import xadrez.tabuleiro.Snapshot;
import xadrez.movimento.Movimento;

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
	
	private static ArrayList<Snapshot> historico;	// guarda o histórico de snapshots
	private static int snap_atual;	// snap atual
	
	private static boolean partida;	// a partida tá rolando?
	
	/**
	 * Ctor, a ser chamado ali em 'jogar' - inicia o motor do jogo (com os 'new')
	 */
	private Xadrez () {
		J1 = new Jogador (Cor.BRANCO);
		J2 = new Jogador (Cor.PRETO);
		mov = new ArrayList<> ();
		historico = new ArrayList<> ();
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
	// movimentos possíveis da peça marcada (ali pra 'cliquei')
	private static ArrayList<Movimento> mov;
	/**
	 * Jogada em si: clica em uma peça [pra mover] e em outra pra mover pra lá
	 * 
	 * Há dois estados: peça não marcada / peça marcada
	 */
	public void cliquei (Point P) {
		if (partida) {
			// casa clicada
			Casa atual = Tabuleiro.getTabuleiro ().getCasa (P);
			if (casa_marcada == false) {
				// se tem peça lá dentro
				if (atual.estaOcupadaCor (jogador_da_vez.getCor ())) {
					mov.clear ();
					
					mov.addAll (jogador_da_vez.getMovs (atual.getPeca ()));

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
						// se sim, marca pra mover lá!
						a_ser_feito = m;
					}
					// descolore os quadradim de possibilidade
					m.unPrintPossivel ();
				}
				if (a_ser_feito != null) {
					// move, e troca jogador
					a_ser_feito.mover (jogador_da_vez);
					trocaJogador ();

					// joga movimento no log e cria snapshot
					a_ser_feito.jogaNoLog ();
					historico.add (new Snapshot (snap_atual, a_ser_feito));
					snap_atual = historico.size () - 1;
				}
				casa_marcada = false;
			}
		}
	}
	
	/**
	 * Desfaz o último movimento
	 */
	public void desfazerMovimento () {
		snap_atual = historico.get (snap_atual).getLast ();
		historico.get (snap_atual).povoaTabuleiro (J1, J2);
		// se for rodada inicial, troca a partir do jogador preto
		if (snap_atual == 0)
			jogador_da_vez = J2;

		trocaJogador ();
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
			outroJogador ().update ();	// quem acabou de jogar
			jogador_da_vez.update ();	// o da vez agora
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
		// limpa o histórico de jogadas, pondo o snap do tabuleiro inicial
		historico.clear ();
		historico.add (new Snapshot (0, null));
		snap_atual = 0;
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
