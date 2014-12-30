/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 29/06/2014
 */
package xadrez.peca;

import ui.Cor;
import ui.Icone;

import xadrez.tabuleiro.Casa;
import xadrez.tabuleiro.Tabuleiro;
import xadrez.tabuleiro.Simulador;
import xadrez.movimento.Movimento;
import xadrez.movimento.Roque;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Rei extends Peca {
	
	private ArrayList<Point> direcoes;
	
	public Rei (Cor nova_cor, Point P) {
		super (nova_cor, P);
		
		// inicializa direções possíveis
		direcoes = new ArrayList<> ();
		direcoes.add (new Point (1, 1));	// diagonal principal, pra baixo
		direcoes.add (new Point (1, -1));	// diagonal secundária, pra cima
		direcoes.add (new Point (-1, 1));	// diagonal secundária, pra baixo
		direcoes.add (new Point (-1, -1));	// diagonal principal, pra cima
		direcoes.add (new Point (1, 0));	// direita
		direcoes.add (new Point (0, 1));	// cima
		direcoes.add (new Point (-1, 0));	// esquerda
		direcoes.add (new Point (0, -1));	// baixo
	}
	public Rei (Cor nova_cor, int linha, int coluna) {
		this (nova_cor, new Point (coluna, linha));
	}
	
	
	@Override
	public String toString () {
		return "R";
	}
	
	@Override
	public ArrayList<Movimento> possiveisMovimentos () {
		ArrayList<Casa> casas = new ArrayList<> ();
		Tabuleiro tab = Tabuleiro.getTabuleiro ();		

		// pra cada direção possível
		for (int count = 0; count < direcoes.size (); count++) {
			int i, j;
			i = (int) coord.getY () + (int) direcoes.get (count).getY ();
			j = (int) coord.getX () + (int) direcoes.get (count).getX ();
			
			Casa aux = tab.getCasa (i, j);;	// auxiliar, pra testar à vontade pra por ou não em 'casas'
				
			if (aux != null) {
				aux.addDominio (cor);
				if (!aux.getDominio ().ameaca (cor) && !aux.estaOcupadaCor (cor))
					casas.add (aux);
			}
		}
		
		ArrayList<Movimento> movs = new ArrayList<> ();
		for (int i = 0; i < casas.size (); i++)
			movs.add (new Movimento (getEssaCasa (), casas.get (i)));
		
		return movs;
	}
	
	public ArrayList<Movimento> Roques (boolean maior, boolean menor) {
		ArrayList<Movimento> movs = new ArrayList<> ();
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		
		int linha = (cor == Cor.BRANCO) ? 7 : 0;
		Casa do_rei = tab.getCasa (linha, 4);
		
		if (maior) {
			// casa da torre
			int i, coluna = 0;
			Casa da_torre = Tabuleiro.getTabuleiro ().getCasa (linha, coluna);
			
			for (i = 3; i > coluna; i--) {
				Casa aux = tab.getCasa (linha, i);
				if (aux.estaOcupada () || aux.getDominio ().ameaca (cor))
					break;
			}

			if (i == coluna)
				movs.add (new Roque (do_rei, da_torre, Roque.roque_tipo.MAIOR));
		}
		if (menor) {
			// casa da torre
			int i, coluna = 7;
			Casa da_torre = Tabuleiro.getTabuleiro ().getCasa (linha, coluna);
			
			for (i = 5; i < coluna; i++) {
				Casa aux = tab.getCasa (linha, i);
				if (aux.estaOcupada () || aux.getDominio ().ameaca (cor))
					break;
			}

			if (i == coluna)
				movs.add (new Roque (do_rei, da_torre, Roque.roque_tipo.MENOR));
		}

		return movs;
	}

	@Override
	public void domina (Simulador sim) {
		for (int count = 0; count < direcoes.size (); count++) {
			int i, j;
			i = (int) coord.getY () + (int) direcoes.get (count).getY ();
			j = (int) coord.getX () + (int) direcoes.get (count).getX ();

			Casa aux = sim.getCasa (i, j);
			if (aux != null)
				aux.addDominio (cor);
		}
	}

	/* GETTERS */
	@Override
	public ImageIcon getIcone () {
		return Icone.REI.getImg (cor);
	}
	
	@Override
	public byte getMask () {
		byte aux = super.getMask ();
		return (byte) (10 + aux);
	}
}
