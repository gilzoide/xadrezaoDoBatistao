/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 29/06/2014
 */
package xadrez.movimento;

import ui.Jogador;
import ui.Gui;
import ui.Icone;

import xadrez.tabuleiro.Casa;
import xadrez.tabuleiro.Tabuleiro;
import xadrez.tabuleiro.Simulador;
import xadrez.peca.Peca;
import xadrez.peca.Peao;
import xadrez.peca.Rei;
import xadrez.peca.Torre;
import xadrez.peca.PromoveuException;

import java.awt.Point;

import javax.swing.ImageIcon;

import java.io.Serializable;

public class Movimento implements Serializable {
	protected Casa donde, pronde;		// Casas: donde saiu, pronde vai
	protected boolean posso;	// Posso fazer esse movimento? Bom, só saberei depois de simular o movimento
	
	protected String notacao_extra;	// notação extra (que nem sempre ocorre): toma peça
	
	private static int num;		// Número total de jogadas
	
	/**
	 * Ctor: ator move de 'donde' pra 'pronde'
	 */
	public Movimento (Casa donde, Casa pronde) {
		this.donde = donde;
		this.pronde = pronde;
		notacao_extra = "";
		posso = true;
	}
	/**
	 * Quando escolher qual movimento realmente fazer, chame o método 'Mover' dele
	 */
	public void mover (Jogador ator) {
		// mais um movimento!
		num++;
		
		// se tinha alguém lá, morreu =P
		Peca aux = pronde.getPeca ();
		if (aux != null)
			aux.morre ();

		// pega a peça a ser movida e atualiza sua posição
		aux = donde.getPeca ();
		aux.setCoord (pronde.getCoord ());
		// se for um peão, podem acontecer coisas muito loucas
		if (aux instanceof Peao) {
			try {
				((Peao)aux).update (true);
			}
			// se não é peão mais (foi promovido, UHUL xD)
			catch (PromoveuException ex) {
				Peca nova = ex.getNova ();
				ator.addPeca (nova);
			}
		}
		if (aux instanceof Rei) {
			ator.setRoques (false, false);
		}
		if (aux instanceof Torre) {
			if (aux.getCoord ().getX () == 7)
				ator.setRoques (ator.getRoqueMaior (), false);
			else
				ator.setRoques (false, ator.getRoqueMenor ());
		}
		
		
		pronde.setPeca (aux);
		pronde.atualizaIcone ();
		donde.setPeca (null);
		donde.atualizaIcone ();
	}
	// pro simulador, pra não afetar nenhum dos reais jogadores, nem a GUI
	public void mover () {
		// pega a peça a ser movida e atualiza sua posição
		Peca aux = donde.getPeca ();
		aux.setCoord (pronde.getCoord ());

		pronde.setPeca (aux);
		donde.setPeca (null);
	}
	
	/**
	 * Simula o movimento, pra ver se pode (se não deixa o rei em xeque)
	 */
	public Simulador simula () {
		Simulador sim = new Simulador (donde, pronde);
		return sim;
	}
	
	/**
	 * Icone do rolê fica colorido, assim aparece como uma possível jogada
	 */
	public void printPossivel () {
		if (posso) {
			ImageIcon possivel = Icone.possibilita ((ImageIcon) pronde.getBotao ().getIcon (), pronde.estaOcupada ());
			pronde.getBotao ().setIcon (possivel);
		}
	}
	/**
	 * Icone do rolê desfica colorido, assim volta ao normal as coisa
	 */
	public void unPrintPossivel () {
		if (posso)
			pronde.atualizaIcone ();
	}
	
	/**
	 * Formula e retorna a notação escrita da jogada
	 * 
	 * @note Aqui não estão incluídas as notações de xeque e mate
	 */
	public String notacaoEscrita () {
		Peca aux = pronde.getPeca ();
		// se a casa está ocupada, notação de 'tomou peça'
		if (aux != null) {
			notacao_extra = "x" + aux.getNotacao ();
		}

		String str = new String ();			// exemplo de notação
		str += donde.getPeca ().getNotacao ();			// D (peça)
		str += notacao_extra;
		str += (char) (pronde.getColuna () + 'a');	// g (coluna)
		str += 8 - pronde.getLinha ();		// 3 (linha)
		
		return str;
	}
	/**
	 * Adiciona o movimento no log de movimentos
	 */
	public void jogaNoLog () {
		Gui.getTela ().logMovimento (this);
	}
	
	/**
	 * Verifica, a partir da posição indicada (ou da casa), se a casa é a daquele movimento mesmo
	 */
	public boolean ehEsseMovimento (Point P) {
		Casa aux = Tabuleiro.getTabuleiro ().getCasa (P);
		return aux == pronde;
	}
	public boolean ehEsseMovimento (Casa aux) {
		return aux == pronde;
	}
	
	/* SETTERS */
	public void naoPosso () {
		posso = false;
	}
	public static void novoJogo () {
		num = 0;
	}
	/* GETTERS */
	public boolean getPosso () {
		return posso;
	}
	public static int getNumMovs () {
		return num;
	}
	public static void incNumMovs () {
		num++;
	}
	public Casa getDonde () {
		return donde;
	}
}
