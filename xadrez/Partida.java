/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 29/06/2014
 */
package xadrez;

import ui.Jogador;
import ui.Relogio;
import ui.Cor;
import xadrez.tabuleiro.Snapshot;

import java.io.Serializable;

import java.util.ArrayList;

/**
 * Uma partida!
 * 
 * Tem tudo o que deve ser salvo na vida (por isso é Serializable)
 */
public class Partida implements Serializable {
	ArrayList<Snapshot> historico;	// guarda o histórico de snapshots
	int snap_atual;	// snap atual
	Jogador J1;
	Jogador J2;
	Relogio relogio;

	/**
	 * Ctor
	 */
	public Partida () {
		J1 = new Jogador (Cor.BRANCO);
		J2 = new Jogador (Cor.PRETO);
		historico = new ArrayList<> ();
		relogio = new Relogio ("Tempo total");
	}

	/**
	 * E recomeeeeeeça a partiiiiida
	 */
	public void novoJogo () {
		// limpa o histórico de jogadas, pondo o snap do tabuleiro inicial
		historico.clear ();
		historico.add (new Snapshot (null));
		snap_atual = 0;

		// reinicia os relógios
		relogio.setTempo (0);
		relogio.start ();

		// reinicia os jogadores
		J1.novoJogo ();
		J2.novoJogo ();
		// checa movimentos possíveis
		J1.update ();
		J2.update ();		
	}
	
	/* GETTERS */
	public Relogio getRelogio () {
		return relogio;
	}
	public Jogador getJ1 () {
		return J1;
	}
	public Jogador getJ2 () {
		return J2;
	}
	@Override
	public String toString () {
		String aux = "J1: " + J1 + "\tJ2: " + J2 + "\n";
		aux += historico.get (snap_atual).toString ();
		return aux;
	}
}
