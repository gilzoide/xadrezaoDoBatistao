/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 30/03/2014
 */
package xadrez.peca;

import ui.Cor;
import ui.Icone;
import xadrez.Movimento;
import xadrez.Tabuleiro;
import xadrez.Casa;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public abstract class Peca {
	protected Cor cor;	/// cor da peça
	
	protected Point coord;

	/**
	 * Ctor: ajusta a cor e põe as coordenadas em 'coord'
	 */
	public Peca (Cor nova_cor, int linha, int coluna) {
		this.cor = nova_cor;
		coord = new Point (coluna, linha);
	}
	public Peca (Cor nova_cor, Point P) {
		this.cor = nova_cor;
		coord = new Point (P);
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
	public Casa getEssaCasa () {
		return Tabuleiro.getTabuleiro ().getCasa (coord);
	}
	/* SETTERS */
	public void setCoord (Point P) {
		coord.setLocation (P);
	}
	public void setCoord (int linha, int coluna) {
		coord.setLocation (coluna, linha);
	}
}
