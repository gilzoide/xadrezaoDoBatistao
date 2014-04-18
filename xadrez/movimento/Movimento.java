/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 18/04/2014
 */
package xadrez.movimento;

import ui.Jogador;
import ui.Cor;
import ui.Gui;
import ui.Icone;

import xadrez.tabuleiro.Casa;
import xadrez.tabuleiro.Tabuleiro;
import xadrez.peca.Peca;
import xadrez.peca.Peao;
import xadrez.peca.Rei;
import xadrez.peca.Torre;
import xadrez.peca.PromoveuException;

import java.awt.Point;

import javax.swing.ImageIcon;

public class Movimento {
	protected Casa donde, pronde;		// Casas: donde saiu, pronde vai
	protected boolean posso;	// Posso fazer esse movimento? Bom, só saberei depois de simular o movimento
	
	private String notacao_extra;	// notação extra (que nem sempre ocorre): toma peça
	
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
				Peca nova = Tabuleiro.getTabuleiro ().getCasa (aux.getCoord ()).getPeca ();
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
	
	/**
	 * Simula o movimento, pra ver se pode (se não deixa o rei em xeque)
	 */
	public void simula () {
		
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
	 * Formula e retorna a notação estrita da jogada
	 * 
	 * @note Aqui não estão incluídas as notações de xeque e mate
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