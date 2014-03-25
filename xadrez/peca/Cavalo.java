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

public class Cavalo extends Peca {
	
	public Cavalo (Cor nova_cor, byte linha, byte coluna) {
		super (nova_cor, linha, coluna);
	}
	
	public ArrayList<Movimento> possiveisMovimentos () {
		ArrayList<Casa> casas = new ArrayList<> ();
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		
		// L -> 2 prum lado e 1 pro outro
		ArrayList<Point> direcoes = new ArrayList<> ();
		direcoes.add (new Point (1, 2));
		direcoes.add (new Point (2, 1));
		direcoes.add (new Point (-1, 2));
		direcoes.add (new Point (2, -1));
		direcoes.add (new Point (1, -2));
		direcoes.add (new Point (-2, 1));
		direcoes.add (new Point (-1, -2));
		direcoes.add (new Point (-2, -1));

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
		for (int i = 0; i < casas.size (); i++) {
			movs.add (new Movimento (tab.getCasa (linha, coluna), casas.get (i)));
		}
		
		return movs;
	}
	
	/* GETTER */
	public ImageIcon getIcone () {
		return Icone.CAVALO.getImg (cor);
	}
}
