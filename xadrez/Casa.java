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
	private byte linha, coluna;
	
	private JButton botao;
	
	public Casa (byte i, byte j) {
		linha = i;
		coluna = j;
	}

	/**
	 * Constrói casas do novo jogo (ou seja, só seta as peças no lugar certo =P)
	 */
	void casaNovoJogo () {
		
		switch (linha) {
			// Peças PRETAS: primeira fila (torres, cavalos, bispos, dama e rei)
			case 0:
				if (coluna % 7 == 0)
					peca = new Torre (Cor.PRETO);
				else if (coluna % 5 == 1)
					peca = new Cavalo (Cor.PRETO);
				else if (coluna % 3 == 2)
					peca = new Bispo (Cor.PRETO);
				else if (coluna == 4)
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
				if (coluna % 7 == 0)
					peca = new Torre (Cor.BRANCO);
				else if (coluna % 5 == 1)
					peca = new Cavalo (Cor.BRANCO);
				else if (coluna % 3 == 2)
					peca = new Bispo (Cor.BRANCO);
				else if (coluna == 4)
					peca = new Dama (Cor.BRANCO);
				else
					peca = new Rei (Cor.BRANCO);
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
	public byte getLinha () {
		return linha;
	}
	public byte getColuna () {
		return coluna;
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
