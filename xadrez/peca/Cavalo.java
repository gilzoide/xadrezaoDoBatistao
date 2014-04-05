/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 05/04/2014
 */
package xadrez.peca;

import ui.Cor;
import ui.Icone;

import xadrez.Casa;
import xadrez.Tabuleiro;
import xadrez.Movimento;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Cavalo extends Peca {
	private ArrayList<Point> direcoes;


	public Cavalo (Cor nova_cor, Point P) {
		super (nova_cor, P);
		
		/// inicializa direções
		// L -> 2 prum lado e 1 pro outro
		direcoes = new ArrayList<> ();
		direcoes.add (new Point (1, 2));
		direcoes.add (new Point (2, 1));
		direcoes.add (new Point (-1, 2));
		direcoes.add (new Point (2, -1));
		direcoes.add (new Point (1, -2));
		direcoes.add (new Point (-2, 1));
		direcoes.add (new Point (-1, -2));
		direcoes.add (new Point (-2, -1));
	}
	
	public String toString () {
		return "C";
	}
	
	public ArrayList<Movimento> possiveisMovimentos () {
		ArrayList<Casa> casas = new ArrayList<> ();
		Tabuleiro tab = Tabuleiro.getTabuleiro ();

		// pra cada direção possível
		for (int count = 0; count < direcoes.size (); count++) {
			int i, j;
			i = (int) coord.getY () + (int) direcoes.get (count).getY ();
			j = (int) coord.getX () + (int) direcoes.get (count).getX ();
			
			Casa aux;	// auxiliar, pra testar à vontade pra por ou não em 'casas'
			aux = tab.getCasa (i, j);
				
			if (aux != null) {
				aux.addDominio (cor);
				if (!aux.estaOcupadaCor (cor))
					casas.add (aux);
			}
		}
		
		
		ArrayList<Movimento> movs = new ArrayList<> ();
		for (int i = 0; i < casas.size (); i++) {
			movs.add (new Movimento (getEssaCasa (), casas.get (i)));
		}
		
		return movs;
	}
	
	public void domina () {
		Tabuleiro tab = Tabuleiro.getTabuleiro ();		
		
		for (int count = 0; count < direcoes.size (); count++) {
			int i, j;
			i = (int) coord.getY () + (int) direcoes.get (count).getY ();
			j = (int) coord.getX () + (int) direcoes.get (count).getX ();

			Casa aux = tab.getCasa (i, j);
			if (aux != null)
				aux.addDominio (cor);
		}
	}
	
	/* GETTER */
	public ImageIcon getIcone () {
		return Icone.CAVALO.getImg (cor);
	}
}
