/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package xadrez;

import ui.Cor;
import xadrez.peca.Peca;

public class Movimento {
	private Peca ator;	// quem está movendo
	private byte linha, coluna;	// aonde está movendo
	
	private String notacao_extra;	// notação extra (que nem sempre ocorre): xeque, mate, roque, toma peça
	
	private boolean xeque;	// dá xeque?
	private boolean mate;	// esse xeque é mate? (lembra que aqui só existe se xeque tb for true)
	private boolean roque;	// vai ver foi um roque...
	
	/**
	 * Ctor: ator move pra 'linha'x'coluna', e se dá xeque
	 */
	public Movimento (Peca ator, byte linha, byte coluna, boolean xeque) {
		this.ator = ator;
		this.linha = linha;
		this.coluna = coluna;
		this.xeque = xeque;
		notacao_extra = "";
	}
	/**
	 * Ctor: igual o outro, mas com xeque constante = false
	 */
	public Movimento (Peca ator, byte linha, byte coluna) {
		this (ator, linha, coluna, false);
	}
	/**
	 * Quando escolher qual movimento realmente fazer, chame o método 'Mover' dele
	 */
	public void mover () {
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		Peca aux = tab.getCasa(linha, coluna).getPeca ();
		// se a casa está ocupada, notação de 'tomou peça'
		if (aux != null) {
			notacao_extra += "x" + aux;
		}
		tab.getCasa(linha, coluna).setPeca (ator);
		
	}
	
	public String notacaoEscrita () {
		String str = new String ();	// exemplo de notação
		str += ator;			// D (peça)
		str += 8 - linha;		// 3 (linha)
		str += 'a' + coluna;	// g (coluna)
		if (xeque) {
			str += '+';
			if (mate)
				str += '+';
		}
		
		return str;
	}
}
