/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package xadrez.peca;

import ui.Cor;
import ui.Icone;
import xadrez.Tabuleiro;
import xadrez.Movimento;

import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Bispo extends Peca {

	public Bispo (Cor nova_cor) {
		super (nova_cor);
	}
	
	public String toString () {
		return "B";
	}
	
	public ArrayList<Movimento> possiveisMovimentos (byte linha, byte coluna) {
		ArrayList<Movimento> aux = null;
		int[] direcoes = {
			1, 1,	// diagonal principal, pra baixo
			1, -1,	// diagonal secundária, pra cima
			-1, 1,	// diagonal secundária, pra baixo
			-1, -1	// diagonal principal, pra cima
		};

		// pra cada direção possível
		for (int count = 0; count < 8; count += 2) {
			Integer i, j;
			// se ainda estiver no tabuleiro, é uma possibilidade
			for (i = linha + direcoes[count], j = coluna + direcoes[count + 1]; Tabuleiro.estaDentro (i.byteValue(), j.byteValue()); i += direcoes[count], j += direcoes[count + 1]) {
				aux.add (new Movimento (this, i.byteValue(), j.byteValue()));
				// e se tiver alguém ocupando a casa, ainda é uma possibilidade, mas a direção acaba por aí
				if (Tabuleiro.estaOcupadoPeloInimigo (i.byteValue(), j.byteValue(), this.cor))
					break;
			}
		}
		
		return aux;
	}
	
	/* GETTER */
	public ImageIcon getIcone () {
		return Icone.BISPO.getImg (cor);
	}
}
