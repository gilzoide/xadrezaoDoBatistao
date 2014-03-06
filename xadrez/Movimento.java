/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 05/03/2014
 */
package xadrez;

import ui.Cor;
import xadrez.peca.Peca;

public class Movimento {
	private Peca ator;	// quem está movendo
	private byte linha, coluna;	// aonde está movendo
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
	}
	/**
	 * Ctor: igual o outro, mas com xeque constante = false
	 */
	public Movimento (Peca ator, byte linha, byte coluna) {
		this (ator, linha, coluna, false);
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
