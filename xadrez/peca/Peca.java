/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 18/04/2014
 */
package xadrez.peca;

import ui.Cor;
import ui.Icone;
import xadrez.tabuleiro.Casa;
import xadrez.tabuleiro.Tabuleiro;
import xadrez.movimento.Movimento;

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
	 * Copia 2 peças, de 'src' a 'dest'
	 * 
	 * @note Essa função cria uma duplicata da peça, não simplesmente passando a referência das coisas
	 */
	public static void copia (Peca dest, Peca src) {
		if (src == null)
			dest = null;
		else {
			if (src instanceof Peao)
				dest = new Peao (src.cor, src.coord);
			else if (src instanceof Dama)
				dest = new Dama (src.cor, src.coord);
			else if (src instanceof Torre)
				dest = new Torre (src.cor, src.coord);
			else if (src instanceof Bispo)
				dest = new Bispo (src.cor, src.coord);
			else if (src instanceof Cavalo)
				dest = new Cavalo (src.cor, src.coord);
			else 	// Rei
				dest = new Rei (src.cor, src.coord);
		}
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
