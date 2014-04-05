/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 05/04/2014
 */
package xadrez;

import ui.Cor;
import ui.Jogador;
import xadrez.Casa;
import xadrez.peca.Peca;

import java.awt.Point;

/**
 * Roque é um movimento especial, por esse motivo precisa de atenção especial xP
 * ps: que boiola
 */
public class Roque extends Movimento {
	public enum roque_tipo {
		MAIOR ("O-O-O"),
		MENOR ("O-O");
	
		/* Notação do roque */
		private String notacao;
		private roque_tipo (String notacao) {
			this.notacao = notacao;
		}
		
		public String toString () {
			return notacao;
		}
	}
	
	private roque_tipo tipo;
	
	public Roque (Casa do_rei, Casa da_torre, roque_tipo tipo) {
		super (do_rei, da_torre);
		this.tipo = tipo;
	}
	
	
	@Override
	public void mover (Jogador ator) {
		// não posso mais fazer roques
		ator.setRoques (false, false);
		
		Tabuleiro tab = Tabuleiro.getTabuleiro ();
		Point do_rei = donde.getCoord ();
		// o rei
		Peca rei = donde.getPeca ();
		donde.setPeca (null);
		// a torre em questão
		Peca torre = pronde.getPeca ();
		pronde.setPeca (null);

		// pra que lado vai? (depende de se é maior ou menor)
		int lado = (tipo == roque_tipo.MENOR) ? 1 : -1;
		// move a torre
		do_rei.translate (lado, 0);
		torre.setCoord (do_rei);
		tab.getCasa (do_rei).setPeca (torre);
		tab.getCasa (do_rei).atualizaIcone ();
		pronde.atualizaIcone ();
		// move o rei
		do_rei.translate (lado, 0);
		rei.setCoord (do_rei);
		tab.getCasa (do_rei).setPeca (rei);
		tab.getCasa (do_rei).atualizaIcone ();
		donde.atualizaIcone ();
	}
	
	@Override
	public String notacaoEscrita () {
		return tipo.toString ();
	}
}
