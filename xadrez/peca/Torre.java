/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 05/04/2014
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

public class Torre extends Peca {
	private ArrayList<Point> direcoes;

	public Torre (Cor nova_cor, Point P) {
		super (nova_cor, P);
		
		// inicializa direções
		direcoes = new ArrayList<> ();
		direcoes.add (new Point (1, 0));	// direita
		direcoes.add (new Point (0, 1));	// cima
		direcoes.add (new Point (-1, 0));	// esquerda
		direcoes.add (new Point (0, -1));	// baixo
	}
	
	public String toString () {
		return "T";
	}
	
	public ArrayList<Movimento> possiveisMovimentos () {
		ArrayList<Casa> casas = new ArrayList<> ();
		Tabuleiro tab = Tabuleiro.getTabuleiro ();

		// pra cada direção possível
		for (int count = 0; count < direcoes.size (); count++) {
			int i, j;
			Casa aux;	// auxiliar, pra testar à vontade pra por ou não em 'casas'
			// se ainda estiver no tabuleiro, é uma possibilidade
			for (i = (int) coord.getY () + (int) direcoes.get (count).getY (), j = (int) coord.getX () + (int) direcoes.get (count).getX (); Tabuleiro.estaDentro (i, j); i += (int) direcoes.get (count).getY (), j += (int) direcoes.get (count).getX ()) {
				aux = tab.getCasa (i, j);
				
				aux.addDominio (cor);
				
				// não tá ocupada por uma peça da mesma cor
				if (!aux.estaOcupadaCor (cor)) {
					casas.add (aux);
					// mas talvez uma do inimigo, então só essa e cabou
					if (aux.estaOcupada ())
						break;
				}
				else
					break;
			}
		}
		
		ArrayList<Movimento> movs = new ArrayList<>();
		for (int i = 0; i < casas.size (); i++)
			movs.add (new Movimento (getEssaCasa (), casas.get (i)));
		
		return movs;
	}
	
	public void domina () {
		Tabuleiro tab = Tabuleiro.getTabuleiro ();		
		
		for (int count = 0; count < direcoes.size (); count++) {
			int i, j;
			Casa aux;	// auxiliar, pra testar à vontade pra por ou não em 'casas'
			// se ainda estiver no tabuleiro, é uma possibilidade
			for (i = (int) coord.getY () + (int) direcoes.get (count).getY (), j = (int) coord.getX () + (int) direcoes.get (count).getX (); Tabuleiro.estaDentro (i, j); i += (int) direcoes.get (count).getY (), j += (int) direcoes.get (count).getX ()) {
				aux = tab.getCasa (i, j);
				
				aux.addDominio (cor);
				
				// se tiver ocupada, ainda domina mais cabou a graça
				if (aux.estaOcupada ())
					break;
			}
		}
	}
	
	/* GETTER */
	public ImageIcon getIcone () {
		return Icone.TORRE.getImg (cor);
	}
}
