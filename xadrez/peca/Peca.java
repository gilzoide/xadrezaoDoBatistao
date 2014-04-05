/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 05/04/2014
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
	
	protected Point coord;	/// coordenada da peça
	protected boolean morreu;	/// se a peça tá em jogo ainda ou não

	protected int indice_comeco;	/// índice dos seus possíveis movimentos dentro de todos os possíveis movimentos
	protected int indice_fim;	/// indice fim dos seus possíveis movimentos dentro de todos os possíveis movimentos
	/**
	 * Ctor: ajusta a cor e põe as coordenadas em 'coord'
	 */
	public Peca (Cor nova_cor, int linha, int coluna) {
		this.cor = nova_cor;
		coord = new Point (coluna, linha);
		morreu = false;
	}
	public Peca (Cor nova_cor, Point P) {
		this.cor = nova_cor;
		coord = new Point (P);
		morreu = false;
	}

	/**
	 * Calcula os possíveis movimentos da peça, retornando um array de possibilidades de movimentos;
	 * Seta o domínio do jogador no tabuleiro
	 *
	 * @return Lista de possíveis movimentos
	 */
	public abstract ArrayList<Movimento> possiveisMovimentos ();

	/**
	 * Peça domina as casas que ela pode fazê-lo
	 */
	public abstract void domina ();

	/* GETTERS */
	public abstract ImageIcon getIcone ();
	
	public Cor getCor () {
		return this.cor;
	}
	public Casa getEssaCasa () {
		return Tabuleiro.getTabuleiro ().getCasa (coord);
	}
	public boolean estaMorto () {
		return morreu;
	}
	public Point getCoord () {
		return coord;
	}
	public int getIndiceComeco () {
		return this.indice_comeco;
	}
	public int getIndiceFim () {
		return this.indice_fim;
	}
	/* SETTERS */
	public void setCoord (Point P) {
		coord.setLocation (P);
	}
	public void setCoord (int linha, int coluna) {
		coord.setLocation (coluna, linha);
	}
	public void morre () {
		morreu = true;
	}
	public void setIndiceComeco (int indice) {
		this.indice_comeco = indice;
	}
	public void setIndiceFim (int indice) {
		this.indice_fim = indice;
	}
}
