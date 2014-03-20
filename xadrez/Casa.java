/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package xadrez;

import ui.Cor;
import ui.Icone;
import xadrez.peca.*;

import javax.swing.JButton;
import javax.swing.ImageIcon;

/**
 * Cada casa do tabuleiro: tem uma cor e possivelmente uma peça
 */
public class Casa {
	private Peca peca;
	
	private JButton botao;

	/**
	 * Ctor: depende da posição no tabuleiro
	 */
	Casa (byte i, byte j) {
		// Únicas posições possíveis são de 0~7
		if (!Tabuleiro.estaDentro (i, j)) {
			System.err.println ("Cê tá loko? Xadrez é só 8x8, irmão!");
			System.exit (-1);
		}

		switch (i) {
			// Peças PRETAS: primeira fila (torres, cavalos, bispos, dama e rei)
			case 0:
				if (j % 7 == 0)
					peca = new Torre (Cor.PRETO);
				else if (j % 5 == 1)
					peca = new Cavalo (Cor.PRETO);
				else if (j % 3 == 2)
					peca = new Bispo (Cor.PRETO);
				else if (j == 4)
					peca = new Dama (Cor.PRETO);
				else
					peca = new Rei (Cor.PRETO);
				break;
			// segunda fila (peões)
			case 1:
				peca = new Peao (Cor.PRETO);
				break;

			// Peças BRANCAS: segunda fila (peões)
			case 6:
				peca = new Peao (Cor.BRANCO);
				break;
			// primeira fila ((torres, cavalos, bispos, dama e rei)
			case 7:
				if (j % 7 == 0)
					peca = new Torre (Cor.BRANCO);
				else if (j % 5 == 1)
					peca = new Cavalo (Cor.BRANCO);
				else if (j % 3 == 2)
					peca = new Bispo (Cor.BRANCO);
				else if (j == 4)
					peca = new Dama (Cor.BRANCO);
				else
					peca = new Rei (Cor.BRANCO);
				break;

			default:
				peca = null;
		}
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
}
