/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 29/06/2014
 */
package xadrez.peca;

import ui.Cor;
import xadrez.tabuleiro.Casa;
import xadrez.tabuleiro.Tabuleiro;
import xadrez.tabuleiro.Simulador;
import xadrez.movimento.Movimento;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import java.io.Serializable;

/**
 * Peças do jogo de xadrez
 * 
 * É abstrata, pois não faz sentido se ter uma instancia de peça
 */
public abstract class Peca implements Serializable {
	protected Cor cor;	/// cor da peça
	
	protected Point coord;	/// coordenada da peça
	protected boolean morreu;	/// se a peça está em jogo ainda ou não

	protected int indice_comeco;	/// índice dos seus possíveis movimentos, dentro de todos os possíveis movimentos
	protected int indice_fim;	/// indice fim dos seus possíveis movimentos, dentro de todos os possíveis movimentos
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
	 * @note Essa função cria uma duplicata da peça, não simplesmente passando a referência
	 */
	public static Peca copia (final Peca src) {
		if (src == null)
			return null;
		else {
			Peca aux = null;
			if (src instanceof Peao)
				aux = new Peao (src.cor, src.coord);
			else if (src instanceof Dama)
				aux = new Dama (src.cor, src.coord);
			else if (src instanceof Torre)
				aux = new Torre (src.cor, src.coord);
			else if (src instanceof Bispo)
				aux = new Bispo (src.cor, src.coord);
			else if (src instanceof Cavalo)
				aux = new Cavalo (src.cor, src.coord);
			else if (src instanceof Rei)
				aux = new Rei (src.cor, src.coord);
			
			return aux;
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
	 * Peça domina as casas no simulador
	 */
	public abstract void domina (Simulador sim);

	/* GETTERS */
	public abstract ImageIcon getIcone ();
	
	/**
	 * @return byte de máscara da peça
	 */
	public byte getMask () {
		return (byte) ((cor == Cor.BRANCO) ? 1 : 0);
	}
	
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
	public void setCor (Cor cor) {
		this.cor = cor;
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
	/**
	 * Retorna a notação específica de xadrez
	 */
	public String getNotacao () {
		return toString ();
	}
}
