/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 29/06/2014
 */
package xadrez.tabuleiro;

import ui.Cor;
import xadrez.peca.*;

import java.awt.Point;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.io.Serializable;

/**
 * Cada casa do tabuleiro: tem uma cor e possivelmente uma peça
 */
public class Casa implements Serializable {
	private Peca peca;
	private Point coord;
	/**
	 * Marca que jogador (pela cor) que tem domínio sobre a casa
	 * Serve pra checar xeque ;]
	 */
	private Cor dominio;
	
	private JButton botao;
	
	public Casa (int i, int j) {
		coord = new Point (j, i);
		dominio = Cor.LIVRE;
	}

	/**
	 * Constrói casas do novo jogo (ou seja, só seta as peças no lugar certo =P)
	 */
	void casaNovoJogo () {
		Cor nova_cor = Cor.BRANCO;
		PecaFactory fabrica = new PecaFactory ();
		
		switch ((int) coord.getY ()) {
			// primeira fila (torres, cavalos, bispos, dama e rei)
			case 0:
				nova_cor = Cor.PRETO;
			case 7:
				if (coord.getX () % 7 == 0)
					peca = fabrica.criaTorre ();
				else if (coord.getX () % 5 == 1)
					peca = fabrica.criaCavalo ();
				else if (coord.getX () % 3 == 2)
					peca = fabrica.criaBispo ();
				else if (coord.getX () == 4)
					peca = fabrica.criaRei ();
				else
					peca = fabrica.criaDama ();
				break;
			// segunda fila (peões)
			case 1:
				nova_cor = Cor.PRETO;
			case 6:
				peca = fabrica.criaPeao ();
				break;
				
			default:
				peca = null;
		}
		if (peca != null) {
			peca.setCoord (coord);
			peca.setCor (nova_cor);
		}
		
		atualizaIcone ();
	}
	
	/**
	 * Copia 2 casas, de 'src' a 'dest'
	 * 
	 * @note Essa função cria uma duplicata da casa, não simplesmente passando a referência
	 */
	public static void copia (Casa dest, Casa src) {
		// copia a peça lá dentro, que é só o que precisamos!
		dest.setPeca (Peca.copia (src.getPeca ()));
	}
	
	
	/**
	 * Verifica se casa está ocupada
	 */
	public boolean estaOcupada () {
		return peca != null;
	}
	/**
	 * Verifica se casa está ocupada por alguém da cor 'cor'
	 */
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
	public Cor getDominio () {
		return dominio;
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
	public void setDominio (Cor dominio) {
		this.dominio = dominio;
	}
	/**
	 * Adiciona à casa domínio da cor do jogador
	 */
	public void addDominio (Cor jogador) {
		dominio = dominio.dominioAdd (jogador);
	}
	/**
	 * Remove da casa domínio da cor do jogador
	 */
	public void removeDominio (Cor jogador) {
		dominio = dominio.dominioRemove (jogador);
	}
	
	@Override
	public String toString () {
		return ("casa " + coord.getX () + " " + coord.getY ());
	}
}
