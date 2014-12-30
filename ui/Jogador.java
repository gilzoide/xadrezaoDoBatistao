/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 29/06/2014
 */
package ui;

import xadrez.tabuleiro.Tabuleiro;
import xadrez.tabuleiro.Simulador;
import xadrez.movimento.Movimento;
import xadrez.peca.Peca;
import xadrez.peca.Peao;
import xadrez.peca.Rei;
import xadrez.peca.PromoveuException;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

public class Jogador implements Serializable {
	private Cor cor;
	private String nome;	// nome do jogador; padrão: "Jogador 'cor'"
	/// Referências de peças importantes (que têm update);
	private ArrayList<Peca> todas_pecas;
	private ArrayList<Peao> piaums;
	private Rei reizaum;
	private boolean roque_maior, roque_menor;	/// posso fazer roque? (maior e menor)
	/// Todos os movimentos possíveis naquela rodada!
	private ArrayList<Movimento> movs;
	/// relógio
	private Relogio relogio;


	/**
	 * Ctor
	 */
	public Jogador (Cor nova_cor) {
		cor = nova_cor;
		nome = "Jogador " + cor;
		piaums = new ArrayList<> ();
		todas_pecas = new ArrayList<> ();
		movs = new ArrayList<> ();
		relogio = new Relogio (nome);
	}
	/**
	 * Marca as peças importantes (com base na posição de começo de jogo)
	 * 
	 * @note peças importantes = peões + rei
	 */
	public void novoJogo () {
		todas_pecas.clear ();
		piaums.clear ();
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		
		// linha dos peões
		int linha = (cor == Cor.BRANCO) ? 6 : 1;
		for (int i = 0; i < 8; i++) {
			todas_pecas.add (tab.getCasa (linha, i).getPeca ());
			piaums.add ((Peao) tab.getCasa (linha, i).getPeca ());
		}
		// linha dos não peões
		linha = (cor == Cor.BRANCO) ? 7 : 0;
		for (int i = 0; i < 8; i++) {
			todas_pecas.add (tab.getCasa (linha, i).getPeca ());
		}
		// e o reizão xD
		reizaum = (Rei) tab.getCasa (linha, 4).getPeca ();
		// por enquanto posso fazer qualquer roque
		roque_maior = roque_menor = true;
		
		// reseta o relógio
		relogio.setTempo (0);
		relogio.start ();
	}
	
	/**
	 * Update por jogada: movimentos possíveis, domina campo e checa xeque
	 */
	public void update () {
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				tab.getCasa (i, j).removeDominio (cor);
		}
		
		// recalcula movimentos possíveis e domina o campo
		movs.clear ();
		for (Peca P : todas_pecas) {
			if (!P.estaMorto ()) {
				ArrayList<Movimento> aux = P.possiveisMovimentos ();
								
				// se não tem movimento possível, indice_comeco e indice_fim serão iguais
				P.setIndiceComeco (movs.size ());
				movs.addAll (aux);
				P.setIndiceFim (movs.size ());
			}
		}
	}
	/**
	 * Update do rei: precisa ser feito depois dos updates dos 2 jogadores
	 */
	public void updateRei () {
		ArrayList<Movimento> aux = new ArrayList<> ();
		aux.addAll (reizaum.Roques (roque_maior, roque_menor));
		aux.addAll (reizaum.possiveisMovimentos ());
		// se não tem movimento possível, indice_comeco e indice_fim serão iguais
		reizaum.setIndiceComeco (movs.size ());
		movs.addAll (aux);
		reizaum.setIndiceFim (movs.size ());
	}
	
	/**
	 * Checa se jogador está em cheque, ou até mate!
	 */
	public void checaXeque (boolean refresh) {
		// se não tem mais movimentos, é mate!
		if (!possoMover ()) {
			if (estaXeque ())
				// xeque mate!
				Gui.getTela ().mate (this);
			else
				// stalemate!
				Gui.getTela ().empata ();
		}
		// talvez não mate, mas um xequezinho básico
		else if (estaXeque ()) {
			Gui.getTela ().xeque (this, refresh);
		}
	}
	/**
	 * Verifica se todos os movimentos são realmente possíveis (se não deixa o rei em xeque)
	 */
	private boolean possoMover () {
		for (Movimento m : movs) {
			// pega sua simulação
			Simulador sim = m.simula ();
			// se deixar em xeque, nem posso fazer esse movimento =/
			if (estaXeque (sim))
				m.naoPosso ();
		}
		
		for (Movimento m : movs) {
			// se posso qualquer um, posso algum
			if (m.getPosso ())
				return true;
		}
		
		return false;
	}
	
	/**
	 * Rei deste jogador está em xeque?
	 * 
	 * Seja no tabuleiro real, ou em um simulador, esta função diz se sim ou não =]
	 */
	public boolean estaXeque () {
		return Tabuleiro.getTabuleiro ().getCasa (reizaum.getCoord ()).getDominio ().ameaca (cor);
	}
	public boolean estaXeque (Simulador sim) {
		return sim.getCasa (sim.getReizaum (this.cor)).getDominio ().ameaca (cor);
	}
	/**
	 * Dá um update nos peões - só pra quem acabou de jogar
	 */
	public void updatePiaums () {
		for (Peao P : piaums) {
			if (!P.estaMorto ()) {
				try {
					P.update (false);
				}
				catch (PromoveuException ex) {
					Peca nova = ex.getNova ();
					addPeca (nova);
				}
			}
		}
	}
	
	/* SETTERS */
	public void setRoques (boolean maior, boolean menor) {
		this.roque_maior = maior;
		this.roque_menor = menor;
	}
	public void addPeca (Peca nova) {
		todas_pecas.add (nova);
	}
	public void setNome (String novo_nome) {
		this.nome = novo_nome;
	}
	/**
	 * Seta as peças guardadas pelo Jogador
	 * 
	 * Serve pra load game, a partir de Snapshots
	 */
	public void setPecas (ArrayList<Peca> pecas) {
		// limpa o que tiver lá dentro, pra refazer
		piaums.clear ();
		todas_pecas.clear ();
		
		for (Peca P : pecas) {
			todas_pecas.add (P);
			if (P instanceof Peao)
				piaums.add ((Peao) P);
			else if (P instanceof Rei)
				reizaum = (Rei) P;
		}
	}
	
	/* GETTERS */
	public Cor getCor () {
		return cor;
	}
	public List<Movimento> getMovs (Peca P) {
		return movs.subList (P.getIndiceComeco (), P.getIndiceFim ());
	}
	public boolean getRoqueMaior () {
		return roque_maior;
	}
	public boolean getRoqueMenor () {
		return roque_menor;
	}
	public Relogio getRelogio () {
		return relogio;
	}
	public void printPecas () {
		System.out.print (this + " ");
		for (Peca P : todas_pecas)
			System.out.println (P + " " + P.getCoord ());
		for (Peao P : piaums)
			System.out.print ("peão" + P.getCoord ());
			
		System.out.print ('\n');
	}
	
	
	@Override
	public String toString () {
		return nome;
	}
}
