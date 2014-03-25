/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package xadrez.peca;

import ui.Cor;
import ui.Icone;

import xadrez.Casa;
import xadrez.Tabuleiro;
import xadrez.Movimento;

import java.util.ArrayList;

import java.awt.Point;

import javax.swing.ImageIcon;

public class Rei extends Peca {
	/* Esse rei pode fazer roque? */
	private boolean roque;
	
	public Rei (Cor nova_cor, byte linha, byte coluna) {
		super (nova_cor, linha, coluna);
		roque = true;
	}
	
	public ArrayList<Movimento> possiveisMovimentos () {
		ArrayList<Casa> casas = new ArrayList<>();
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		
		ArrayList<Point> direcoes = new ArrayList<> ();
		direcoes.add (new Point (1, 1));	// diagonal principal, pra baixo
		direcoes.add (new Point (1, -1));	// diagonal secundária, pra cima
		direcoes.add (new Point (-1, 1));	// diagonal secundária, pra baixo
		direcoes.add (new Point (-1, -1));	// diagonal principal, pra cima
		direcoes.add (new Point (1, 0));	// direita
		direcoes.add (new Point (0, 1));	// cima
		direcoes.add (new Point (-1, 0));	// esquerda
		direcoes.add (new Point (0, -1));	// baixo

		// pra cada direção possível
		for (int count = 0; count < direcoes.size (); count++) {
			int i, j;
			i = linha + (int) direcoes.get (count).getX ();
			j = coluna + (int) direcoes.get (count).getY ();
			
			Casa aux;	// auxiliar, pra testar à vontade pra por ou não em 'casas'
			if (Tabuleiro.estaDentro (i, j)) {
				aux = tab.getCasa (i, j);
				
				if (!aux.estaOcupadaCor (cor))
					casas.add (aux);
			}
		}
		
		ArrayList<Movimento> movs = new ArrayList<>();
		for (int i = 0; i < casas.size (); i++)
			movs.add (new Movimento (tab.getCasa (linha, coluna), casas.get (i)));
		
		return movs;
	}
	
	/* GETTER */
	public ImageIcon getIcone () {
		return Icone.REI.getImg (cor);
	}
}
