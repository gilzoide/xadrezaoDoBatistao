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
	public enum AcaoEspecial {
		ANDOU_DUAS,
		EN_PASSANT,
		PROMOCAO,
		NADA;
	};
	
	private AcaoEspecial acao;
	private Estado estado;
	
	/**
	 * Ctor
	 */
	public Peao (Cor nova_cor, byte linha, byte coluna) {
		super (nova_cor, linha, coluna);
		estado = Estado.PRIMEIRA;
		acao = AcaoEspecial.NADA;
	}
	
	public ArrayList<Movimento> possiveisMovimentos () {
		ArrayList<Casa> casas = new ArrayList<> ();
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		
		// dependendo da cor, vai pra frente ou pra trás
		int lado = (cor == Cor.BRANCO) ? -1 : 1;

		Casa aux, aux_en_passant;	// auxiliares, pra testar à vontade pra por ou não em 'casas'
		aux = tab.getCasa (linha + lado, coluna);
		if (!aux.estaOcupada ())
			casas.add (aux);
			
		if (estado == Estado.PRIMEIRA) {
			aux = tab.getCasa (linha + (2 * lado), coluna);
			if (!aux.estaOcupada ())
				casas.add (aux);
		}
		
		// pode tomar
		aux = tab.getCasa (linha + lado, coluna + 1);
		aux_en_passant = tab.getCasa (linha, coluna + 1);
		if (aux.estaOcupadaCor (cor.oposta ()) || (aux_en_passant.estaOcupadaCor (cor.oposta ()) && aux_en_passant.getPeca () instanceof Peao && ((Peao) aux_en_passant.getPeca ()).estado == Estado.EN_PASSANT && !aux.estaOcupada ()))
			casas.add (aux);
			
		aux = tab.getCasa (linha + lado, coluna - 1);
		aux_en_passant = tab.getCasa (linha, coluna - 1);
		if (aux.estaOcupadaCor (cor.oposta ()) || (aux_en_passant.estaOcupadaCor (cor.oposta ()) && aux_en_passant.getPeca () instanceof Peao && ((Peao) aux_en_passant.getPeca ()).estado == Estado.EN_PASSANT && !aux.estaOcupada ()))
			casas.add (aux);

		ArrayList<Movimento> movs = new ArrayList<> ();
		for (int i = 0; i < casas.size (); i++)
			movs.add (new Movimento (tab.getCasa (linha, coluna), casas.get (i)));
		
		return movs;
	}
	
	/**
	 * Se moveu, talvez mude o estado, talz...
	 */
	public AcaoEspecial update (boolean moveu) {
		if (moveu) {
			if (estado == Estado.PRIMEIRA) {
				if (linha == 3 || linha == 4)
					estado = Estado.EN_PASSANT;
			}
			else
				estado = Estado.NADA;
		}
		else if (estado == Estado.EN_PASSANT) {
			estado = Estado.NADA;
		}
		else if (linha == 0 || linha == 8) {
			return AcaoEspecial.PROMOCAO;
		}
		
		// default
		return AcaoEspecial.NADA;
	}
	
	/* GETTER */
	public ImageIcon getIcone () {
		return Icone.PEAO.getImg (cor);
	}
}
