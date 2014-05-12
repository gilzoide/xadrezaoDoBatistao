/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 20/04/2014
 */
package xadrez;
/**
 * @todo pt2 → relógio - load
 * @todo pt2 → exceção
 */

import ui.Gui;
import ui.Cor;
import ui.Jogador;
import ui.Relogio;

import xadrez.tabuleiro.Casa;
import xadrez.tabuleiro.Tabuleiro;
import xadrez.tabuleiro.Snapshot;
import xadrez.movimento.Movimento;
import xadrez.movimento.IllegalChessMovement;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.SwingUtilities;


/**
 * Xadrez é a classe motora do jogo, com main e muito mais!
 */
public class Xadrez {
	private static Jogador jogador_da_vez;
	private static Partida P;

	private static boolean partida;	// a partida tá rolando?

	/**
	 * Ctor, a ser chamado ali em 'jogar' - inicia o motor do jogo (com os 'new')
	 */
	private Xadrez () {
		P = new Partida ();
		jogador_da_vez = P.J1;
		mov = new ArrayList<> ();
	}
	
	/**
	 * A main!
	 */
	public static void main (String[] args) {
		SwingUtilities.invokeLater (new Runnable () {
			@Override
			public void run () {
				Gui tela = Gui.getTela ();
				tela.init (new Xadrez ());
			}
        });
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
	public void cliquei (Point p) {
		if (partida) {
			// casa clicada
			Casa atual = Tabuleiro.getTabuleiro ().getCasa (p);
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
					if (m.ehEsseMovimento (p)) {
						// se sim, marca pra mover lá!
						a_ser_feito = m;
					}
					// descolore os quadradim de possibilidade
					m.unPrintPossivel ();
				}
				if (a_ser_feito != null) {
					// joga movimento no log
					a_ser_feito.jogaNoLog ();
					
					// move, e troca jogador
					a_ser_feito.mover (jogador_da_vez);
					trocaJogador ();


					/*  snapshots  */
					P.snap_atual++;
					// se tava desfeito movimento, remove os que tinha pra frente
					if (P.snap_atual < P.historico.size ()) {
						P.historico.removeAll (P.historico.subList (P.snap_atual, P.historico.size ()));
					}
					P.historico.add (new Snapshot (a_ser_feito));
				}
				casa_marcada = false;
			}
		}
	}
	
	/**
	 * Desfaz o último movimento
	 */
	public void desfazerMovimento () {
		if (P.snap_atual > 0) {
			P.snap_atual--;

			refreshSnap ();
		}
	}
	/**
	 * Refaz o último movimento
	 */
	public void refazerMovimento () {
		if (P.snap_atual < P.historico.size () - 1) {
			P.snap_atual++;

			refreshSnap ();
		}
	}
	
	public void refreshSnap () {
		if (partida) {
			P.historico.get (P.snap_atual).povoaTabuleiro (P.J1, P.J2);
			Gui.getTela ().setLog (P.historico.get (P.snap_atual).getLog ());
			
			trocaJogador (P.snap_atual % 2 == 0 ? P.J2 : P.J1);
		}
	}
	
	
	/**
	 * Inverte o jogador - passa a vez
	 */
	private void trocaJogador (Jogador J) {
		jogador_da_vez = J;

		trocaJogador ();
	}
	private void trocaJogador () {
		if (partida) {
			// peões que tinham andado 2 casas agora não podem mais ser tomados por en passant
			jogador_da_vez.updatePiaums ();
			
			// relógio do da vez para - vez do otro agora
			jogador_da_vez.getRelogio ().stop ();
			// e começa o do outro!
			outroJogador ().getRelogio ().start ();
			
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
	public static Jogador outroJogador () {
		return (jogador_da_vez == P.J1) ? P.J2 : P.J1;
	}
	
	/**
	 * Começa um novo jogo!
	 */
	public static void novoJogo () {
		// reconstrói as peças no tabuleiro
		Tabuleiro.getTabuleiro ().novoJogo ();
		P.novoJogo ();
		jogador_da_vez = P.J1;
		// só pra constar, o jogador branco é que começa
		// jogo tá rolando!
		partida = true;
	}
	/**
	 * Acabou a partida, seja pelo motivo que for
	 */
	public static void acabaPartida () {
		partida = false;
		P.relogio.stop ();
		P.J1.getRelogio ().stop ();
		P.J2.getRelogio ().stop ();
	}
	
	/* GETTERS */
	public static Jogador getDaVez () {
		return jogador_da_vez;
	}
	public Partida getPartida () {
		return P;
	}
	public Relogio getRelogio () {
		return P.relogio;
	}
	/* SETTERS */
	public void setPartida (Partida P) {
		this.P = P;
	}
}
