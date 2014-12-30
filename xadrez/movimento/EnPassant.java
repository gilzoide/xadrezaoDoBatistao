/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 29/06/2014
 */
package xadrez.movimento;

import ui.Icone;
import ui.Jogador;
import xadrez.tabuleiro.Casa;
import xadrez.peca.Peca;


import javax.swing.ImageIcon;

public class EnPassant extends Movimento {
	private int lado;
	private Casa do_inimigo;
	
	public EnPassant (Casa donde, Casa pronde, Casa do_inimigo) {
		super (donde, pronde);
		this.do_inimigo = do_inimigo;
	}
	
	@Override
	public void mover (Jogador ator) {
		// morreu inimigo!
		do_inimigo.getPeca ().morre ();
		do_inimigo.setPeca (null);
		do_inimigo.atualizaIcone ();
		
		// move o peão em questão
		Peca aux = donde.getPeca ();
		aux.setCoord (pronde.getCoord ());
		pronde.setPeca (aux);
		pronde.atualizaIcone ();
		donde.setPeca (null);
		donde.atualizaIcone ();
	}
		
	// Printa casa possível sempre como pra tomar peça; afinal, é o que fazemos aqui!
	@Override
	public void printPossivel () {
		if (posso) {
			ImageIcon possivel = Icone.possibilita ((ImageIcon) pronde.getBotao ().getIcon (), true);
			pronde.getBotao ().setIcon (possivel);
		}
	}
	
	@Override
	public String notacaoEscrita () {
		Peca aux = do_inimigo.getPeca ();
		notacao_extra = "x" + aux;
		
		String str = new String ();			// exemplo de notação
		str += donde.getPeca ();			// D (peça)
		str += notacao_extra;
		str += (char) (pronde.getColuna () + 'a');	// g (coluna)
		str += 8 - pronde.getLinha ();		// 3 (linha)
		str += "e.p.";
		
		return str;
	}
}
