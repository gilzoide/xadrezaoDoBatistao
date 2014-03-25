/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package xadrez.peca;

import ui.Cor;
import ui.Icone;

import xadrez.Movimento;
import xadrez.Casa;
import xadrez.Tabuleiro;

import java.util.ArrayList;

import java.awt.Point;

import javax.swing.ImageIcon;

public class Peao extends Peca {
	/**
	 * Estado do peão (muda uns trem na jogabilidade):
	 * 		PRIMEIRA: primeira jogada desse peão: pode andar duas casas
	 * 		EN_PASSANT: acabou de dar a primeira jogada: pode ser tomado por 'en passant'
	 * 		NADA: se passou a chance de 'en passant', já era, tem mais nada não
	 */
	private enum Estado {
		PRIMEIRA,
		EN_PASSANT,
		NADA;
	};
	private Estado estado;
	
	/**
	 * Ctor
	 */
	public Peao (Cor nova_cor, byte linha, byte coluna) {
		super (nova_cor, linha, coluna);
		estado = Estado.PRIMEIRA;
	}
	
	public ArrayList<Movimento> possiveisMovimentos () {
		ArrayList<Casa> casas = new ArrayList<>();
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		
		// dependendo da cor, vai pra frente ou pra trás
		int lado = (cor == Cor.BRANCO) ? -1 : 1;

		Casa aux;	// auxiliar, pra testar à vontade pra por ou não em 'casas'
		aux = tab.getCasa (linha + lado, coluna);
		if (!aux.estaOcupadaCor (cor))
			casas.add (aux);
			
		if (estado == Estado.PRIMEIRA) {
			aux = tab.getCasa (linha + (2 * lado), coluna);
			if (!aux.estaOcupadaCor (cor))
				casas.add (aux);
		}
		
		ArrayList<Movimento> movs = new ArrayList<>();
		for (int i = 0; i < casas.size (); i++)
			movs.add (new Movimento (tab.getCasa (linha, coluna), casas.get (i)));
		
		return movs;
	}
	
	/**
	 * Se moveu, talvez mude o estado, talz...
	 */
	public void update (boolean moveu) {
		if (moveu) {
			if (estado == Estado.PRIMEIRA)
				estado = Estado.EN_PASSANT;
		}
		else if (estado == Estado.EN_PASSANT)
			estado = Estado.NADA;
		
	}
	
	/* GETTER */
	public ImageIcon getIcone () {
		return Icone.PEAO.getImg (cor);
	}
}
