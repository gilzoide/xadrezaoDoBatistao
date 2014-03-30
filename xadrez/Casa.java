/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 30/03/2014
 */
package xadrez;

import ui.Cor;
import ui.Icone;
import xadrez.peca.*;

import java.awt.Point;

import javax.swing.JButton;
import javax.swing.ImageIcon;

/**
 * Cada casa do tabuleiro: tem uma cor e possivelmente uma peça
 */
public class Casa {
	private Peca peca;
	private Point coord;
	
	private JButton botao;
	
	public Casa (int i, int j) {
		coord = new Point (j, i);
	}

	/**
	 * Constrói casas do novo jogo (ou seja, só seta as peças no lugar certo =P)
	 */
	void casaNovoJogo () {
		
		switch ((int) coord.getY ()) {
			// Peças PRETAS: primeira fila (torres, cavalos, bispos, dama e rei)
			case 0:
				if (coord.getX () % 7 == 0)
					peca = new Torre (Cor.PRETO, coord);
				else if (coord.getX () % 5 == 1)
					peca = new Cavalo (Cor.PRETO, coord);
				else if (coord.getX () % 3 == 2)
					peca = new Bispo (Cor.PRETO, coord);
				else if (coord.getX () == 4)
					peca = new Dama (Cor.PRETO, coord);
				else
					peca = new Rei (Cor.PRETO, coord);
				break;
			// segunda fila (peões)
			case 1:
				peca = new Peao (Cor.PRETO, coord);
				break;

			// Peças BRANCAS: segunda fila (peões)
			case 6:
				peca = new Peao (Cor.BRANCO, coord);
				break;
			// primeira fila ((torres, cavalos, bispos, dama e rei)
			case 7:
				if (coord.getX () % 7 == 0)
					peca = new Torre (Cor.BRANCO, coord);
				else if (coord.getX () % 5 == 1)
					peca = new Cavalo (Cor.BRANCO, coord);
				else if (coord.getX () % 3 == 2)
					peca = new Bispo (Cor.BRANCO, coord);
				else if (coord.getX () == 4)
					peca = new Dama (Cor.BRANCO, coord);
				else
					peca = new Rei (Cor.BRANCO, coord);
				break;

			default:
				peca = null;
		}
		
		atualizaIcone ();
	}
	
	/**
	 * Ve se casa está ocupada
	 */
	public boolean estaOcupada () {
		return peca != null;
	}
	public boolean estaOcupadaCor (Cor cor) {
		if (estaOcupada ())
			return peca.getCor () == cor;
		else
			return false;
	}

	/* GETTERS */
	public Peca getPeca () {
		return peca;
	}
	public ImageIcon getIcone () {
		return (peca != null) ? peca.getIcone () : null;
	}
	public JButton getBotao () {
		return botao;
	}
	public Point getCoord () {
		return coord;
	}
	public int getLinha () {
		return (int) coord.getY ();
	}
	public int getColuna () {
		return (int) coord.getX ();
	}
	/* SETTERS */
	public void setPeca (Peca nova_peca) {
		peca = nova_peca;
	}
	public void setBotao (JButton novo_botao) {
		this.botao = novo_botao;
	}
	public void atualizaIcone () {
		if (peca != null)
			botao.setIcon (peca.getIcone ());
		else
			botao.setIcon (null);
	}
	
	public String toString () {
		return ("casa " + coord.getY () + " " + coord.getX ());
	}
}
