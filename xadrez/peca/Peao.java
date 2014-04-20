/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 20/04/2014
 */
package xadrez.peca;

import ui.Cor;
import ui.Icone;

import xadrez.tabuleiro.Casa;
import xadrez.tabuleiro.Tabuleiro;
import xadrez.tabuleiro.Simulador;
import xadrez.movimento.Movimento;
import xadrez.movimento.EnPassant;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Peao extends Peca {
	/**
	 * Estado do peão (muda uns trem na jogabilidade):
	 * 		PRIMEIRA: primeira jogada desse peão: pode andar duas casas
	 * 		EN_PASSANT: acabou de dar a primeira jogada: pode ser tomado por 'en passant'
	 * 		NADA: se passou a chance de 'en passant', já era, tem mais nada não
	 */
	private enum Estado {
		PRIMEIRA,	// primeira jogada: posso mover duas
		MOVI_DUAS,	// movi duas casas: tenho que poder ser tomado por en passant
		EN_PASSANT,	// posso ser tomado por en passant
		NADA;	// acabaram os estados especiais
	};
	
	private Estado estado;
	private int lado;
	
	/**
	 * Ctor
	 */
	public Peao (Cor nova_cor, Point P) {
		super (nova_cor, P);
		estado = Estado.PRIMEIRA;		
		// dependendo da cor, vai pra frente ou pra trás
		lado = (cor == Cor.BRANCO) ? -1 : 1;
	}
	
	public String toString () {
		return "";
	}
	
	public ArrayList<Movimento> possiveisMovimentos () {
		ArrayList<Movimento> movs = new ArrayList<> ();
		ArrayList<Casa> casas = new ArrayList<> ();
		Tabuleiro tab = Tabuleiro.getTabuleiro ();

		Casa aux, aux_en_passant;	// auxiliares, pra testar à vontade pra por ou não em 'casas'
		aux = tab.getCasa ((int) coord.getY () + lado, (int) coord.getX ());
		if (!aux.estaOcupada ()) {
			casas.add (aux);
			// se for a primeira jogada, e ninguém tá na frente, pode andar 2 casas
			if (estado == Estado.PRIMEIRA) {
				aux = tab.getCasa ((int) coord.getY () + (2 * lado), (int) coord.getX ());
				if (!aux.estaOcupada ())
					casas.add (aux);
			}
		}
		// pode tomar
		aux = tab.getCasa ((int) coord.getY () + lado, (int) coord.getX () + 1);
		aux_en_passant = tab.getCasa ((int) coord.getY (), (int) coord.getX () + 1);
		if (aux != null) {
			aux.addDominio (cor);
			if (aux.estaOcupadaCor (cor.oposta ()))
				casas.add (aux);
			else if (!aux.estaOcupada () && aux_en_passant != null && aux_en_passant.estaOcupadaCor (cor.oposta ()) && aux_en_passant.getPeca () instanceof Peao && ((Peao) aux_en_passant.getPeca ()).estado == Estado.EN_PASSANT)
				movs.add (new EnPassant (getEssaCasa (), aux, aux_en_passant));
		}
			
		aux = tab.getCasa ((int) coord.getY () + lado, (int) coord.getX () - 1);
		aux_en_passant = tab.getCasa ((int) coord.getY (), (int) coord.getX () - 1);
		if (aux != null) {
			aux.addDominio (cor);
			if (aux.estaOcupadaCor (cor.oposta ()))
				casas.add (aux);
			else if (!aux.estaOcupada () && aux_en_passant != null && aux_en_passant.estaOcupadaCor (cor.oposta ()) && aux_en_passant.getPeca () instanceof Peao && ((Peao) aux_en_passant.getPeca ()).estado == Estado.EN_PASSANT)
				movs.add (new EnPassant (getEssaCasa (), aux, aux_en_passant));
		}

		for (int i = 0; i < casas.size (); i++)
			movs.add (new Movimento (getEssaCasa (), casas.get (i)));
		
		return movs;
	}
	
	public void domina (Simulador sim) {
		Casa aux;
		aux = sim.getCasa ((int) coord.getY () + lado, (int) coord.getX () + 1);
		if (aux != null)
			aux.addDominio (cor);
		aux = sim.getCasa ((int) coord.getY () + lado, (int) coord.getX () - 1);
		if (aux != null)
			aux.addDominio (cor);
	}
	
	/**
	 * Se moveu, talvez mude o estado, talz...
	 */
	public void update (boolean moveu) throws PromoveuException {
		if (moveu) {
			if (estado == Estado.PRIMEIRA && ((int) coord.getY () == 3 || (int) coord.getY () == 4))
					estado = Estado.MOVI_DUAS;
			else
				estado = Estado.NADA;
		}
		// não moveu - update de cada vez: do updatePiaums
		else {
			// movi duas, então posso ser tomado por en passant
			if (estado == Estado.MOVI_DUAS) {
				estado = Estado.EN_PASSANT;
			}
			else if (estado == Estado.EN_PASSANT) {
				estado = Estado.NADA;
			}
			// promoção!
			else if ((int) coord.getY () == 0 || (int) coord.getY () == 7) {
				// não tem mais eu
				morre ();
				Object[] opcoes = { "Bispo", "Cavalo", "Dama", "Torre" };		// opções pra promoção
				Object selecionado = JOptionPane.showInputDialog (null, "Promove pra que peça?", "Promoção do Peão! Só aqui, no Xadrezão do Batistão!", JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);
				Peca nova;
				if (selecionado.toString () == "Bispo")
					nova = new Bispo (cor, coord);
				else if (selecionado.toString () == "Cavalo")
					nova = new Cavalo (cor, coord);
				else if (selecionado.toString () == "Dama")
					nova = new Dama (cor, coord);
				else
					nova = new Torre (cor, coord);

				Tabuleiro.getTabuleiro ().getCasa (coord).setPeca (nova);
				Tabuleiro.getTabuleiro ().getCasa (coord).atualizaIcone ();

				throw new PromoveuException (nova);
			}
		}
	}
	
	/* GETTER */
	public ImageIcon getIcone () {
		return Icone.PEAO.getImg (cor);
	}
}
