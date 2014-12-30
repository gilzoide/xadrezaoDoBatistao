/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 29/06/2014
 */
package xadrez.movimento;

import ui.Icone;
import ui.Jogador;
import xadrez.tabuleiro.Casa;
import xadrez.tabuleiro.Tabuleiro;
import xadrez.peca.Peca;

import java.awt.Point;

import javax.swing.ImageIcon;

/**
 * Roque é um movimento especial, por esse motivo precisa de atenção especial xP
 * ps: ↑ que boiola
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
		
		@Override
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
		// o rei
		Peca rei = donde.getPeca ();
		donde.setPeca (null);
		donde.atualizaIcone ();
		// a torre em questão
		Peca torre = pronde.getPeca ();
		pronde.setPeca (null);
		pronde.atualizaIcone ();

		// pra que lado vai? (depende de se é maior ou menor)
		int lado = (tipo == roque_tipo.MENOR) ? 1 : -1;
		
		Point do_rei = new Point (donde.getCoord ());
		do_rei.translate (2 * lado, 0);
		
		Point da_torre = new Point (donde.getCoord ());
		da_torre.translate (lado, 0);
		
		// move a torre
		torre.setCoord (da_torre);
		tab.getCasa (da_torre).setPeca (torre);
		tab.getCasa (da_torre).atualizaIcone ();
		// move o rei
		rei.setCoord (do_rei);
		tab.getCasa (do_rei).setPeca (rei);
		tab.getCasa (do_rei).atualizaIcone ();
	}
	
	// Printa casa possível como movimento que não toma peça
	@Override
	public void printPossivel () {
		if (posso) {
			ImageIcon possivel = Icone.possibilita ((ImageIcon) pronde.getBotao ().getIcon (), false);
			pronde.getBotao ().setIcon (possivel);
		}
	}
	
	// Notação de roque é especial
	@Override
	public String notacaoEscrita () {
		return tipo.toString ();
	}
}
