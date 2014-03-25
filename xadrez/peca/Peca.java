/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package xadrez.peca;

import ui.Cor;
import ui.Icone;
import xadrez.Movimento;

import java.util.ArrayList;
import javax.swing.ImageIcon;

public abstract class Peca {
	protected Cor cor;	/// cor da peça

	/**
	 * Ctor: ajusta a cor
	 */
	public Peca (Cor nova_cor) {
		this.cor = nova_cor;
	}

	/**
	 * Calcula os possíveis movimentos da peça, retornando um array de possibilidades de movimentos
	 *
	 * @param linha Linha atual da peça
	 * @param coluna Coluna atual da peça
	 *
	 * @return Lista de possíveis movimentos, cada um organizado em um vetor de 2 ints: linha e coluna
	 */
	abstract public ArrayList<Movimento> possiveisMovimentos (byte linha, byte coluna);
	
	/* GETTERS */
	public Cor getCor () {
		return this.cor;
	}
	public abstract ImageIcon getIcone ();
}
