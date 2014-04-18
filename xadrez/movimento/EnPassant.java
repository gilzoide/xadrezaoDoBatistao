/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 18/04/2014
 */
package xadrez.movimento;

import ui.Cor;
import ui.Icone;
import ui.Jogador;
import xadrez.tabuleiro.Casa;
import xadrez.tabuleiro.Tabuleiro;
import xadrez.peca.Peca;

import java.awt.Point;

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
	
	@Override
	public void printPossivel () {
		if (posso) {
			ImageIcon possivel = Icone.possibilita ((ImageIcon) pronde.getBotao ().getIcon (), true);
			pronde.getBotao ().setIcon (possivel);
		}
	}
}
