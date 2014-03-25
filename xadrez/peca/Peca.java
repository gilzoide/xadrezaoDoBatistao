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
	
	protected byte linha, coluna;	/// posição da peça

	/**
	 * Ctor: ajusta a cor
	 */
	public Peca (Cor nova_cor, byte linha, byte coluna) {
		this.cor = nova_cor;
		this.linha = linha;
		this.coluna = coluna;
	}

	/**
	 * Calcula os possíveis movimentos da peça, retornando um array de possibilidades de movimentos
	 *
	 * @return Lista de possíveis movimentos, cada um organizado em um vetor de 2 ints: linha e coluna
	 */
	public abstract ArrayList<Movimento> possiveisMovimentos ();
	
	/* GETTERS */
	public abstract ImageIcon getIcone ();
	
	public Cor getCor () {
		return this.cor;
	}
	/* SETTERS */
	public void setLinha (byte linha) {
		this.linha = linha;
	}
	public void setColuna (byte coluna) {
		this.coluna = coluna;
	}
}
