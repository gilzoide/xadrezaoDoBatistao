/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 30/03/2014
 */
package xadrez;

import ui.Cor;
import ui.Gui;
import ui.Icone;

import xadrez.Casa;
import xadrez.peca.Peca;
import xadrez.peca.Peao;

import java.awt.Point;

import javax.swing.ImageIcon;

public class Movimento {
	private Casa donde, pronde;		// Casas: donde saiu, pronde vai
	
	private String notacao_extra;	// notação extra (que nem sempre ocorre): xeque, mate, roque, toma peça
	
	private boolean xeque;	// dá xeque?
	private boolean mate;	// esse xeque é mate? (lembra que aqui só existe se xeque tb for true)
	private boolean roque;	// vai ver foi um roque...
	
	/**
	 * Ctor: ator move pra 'linha'x'coluna', e se dá xeque
	 */
	public Movimento (Casa donde, Casa pronde, boolean xeque) {
		this.donde = donde;
		this.pronde = pronde;
		this.xeque = xeque;
		notacao_extra = "";
	}
	/**
	 * Ctor: igual o outro, mas com xeque constante = false
	 */
	public Movimento (Casa donde, Casa pronde) {
		this (donde, pronde, false);
	}
	/**
	 * Quando escolher qual movimento realmente fazer, chame o método 'Mover' dele
	 */
	public void mover () {
		// pega a peça a ser movida e atualiza sua posição
		Peca aux = donde.getPeca ();
		aux.setCoord (pronde.getCoord ());
		// se for um peão, podem acontecer coisas muito loucas
		if (aux instanceof Peao) {
			((Peao)aux).update (true);
		}
		
		pronde.setPeca (donde.getPeca ());
		pronde.atualizaIcone ();
		donde.setPeca (null);
		donde.atualizaIcone ();
	}
	
	/**
	 * Icone do rolê fica colorido, assim aparece como uma possível jogada
	 */
	public void printPossivel () {
		ImageIcon possivel = Icone.possibilita ((ImageIcon) pronde.getBotao ().getIcon (), pronde.estaOcupada ());
		
		pronde.getBotao ().setIcon (possivel);
	}
	/**
	 * Icone do rolê desfica colorido, assim volta ao normal as coisa
	 */
	public void unPrintPossivel () {
		pronde.atualizaIcone ();
	}
	
	/**
	 * Formula e retorna a notação estrita da jogada
	 */
	public String notacaoEscrita () {
		Peca aux = pronde.getPeca ();
		// se a casa está ocupada, notação de 'tomou peça'
		if (aux != null) {
			notacao_extra = "x" + aux;
		}
		
		String str = new String ();			// exemplo de notação
		str += donde.getPeca ();			// D (peça)
		str += notacao_extra;
		str += 8 - pronde.getLinha ();		// 3 (linha)
		str += (char) (pronde.getColuna () + 'a');	// g (coluna)
		if (xeque) {
			str += '+';
			if (mate)
				str += '+';
		}
		
		return str;
	}
	
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
}
