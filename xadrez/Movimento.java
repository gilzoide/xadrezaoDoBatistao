/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 30/03/2014
 */
package xadrez;

import ui.Cor;
import ui.Gui;
import ui.Icone;
import ui.Jogador;

import xadrez.peca.Peca;
import xadrez.peca.Peao;

import java.awt.Point;

import javax.swing.ImageIcon;

public class Movimento {
	private Casa donde, pronde;		// Casas: donde saiu, pronde vai
	
	private String notacao_extra;	// notação extra (que nem sempre ocorre): xeque, mate, roque, toma peça
	
	private boolean roque;	// vai ver foi um roque...
	private boolean pode;
	
	private Casa[][] simulador;
	/**
	 * Ctor: ator move de 'donde' pra 'pronde'
	 */
	public Movimento (Casa donde, Casa pronde) {
		this.donde = donde;
		this.pronde = pronde;
		notacao_extra = "";
		pode = true;
		simulador = new Casa[8][8];
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				simulador[i][j] = new Casa (i, j);
		}
	}
	/**
	 * Quando escolher qual movimento realmente fazer, chame o método 'Mover' dele
	 */
	public void mover () {
		// se tinha alguém lá, morreu =P
		Peca aux = pronde.getPeca ();
		if (aux != null)
			aux.morre ();

		// pega a peça a ser movida e atualiza sua posição
		aux = donde.getPeca ();
		aux.setCoord (pronde.getCoord ());
		// se for um peão, podem acontecer coisas muito loucas
		if (aux instanceof Peao) {
			((Peao)aux).update (true);
		}
				
		pronde.setPeca (aux);
		pronde.atualizaIcone ();
		donde.setPeca (null);
		donde.atualizaIcone ();
	}
	/**
	 * Função que simula o movimento e checa se este movimento deixa o próprio rei em xeque
	 * 
	 * @return retorna 'false' se puder fazer o movimento e 'true' se não puder (não se pode deixar o rei em xeque, né =P)
	 */
	public boolean simulaMovimentoEstaXeque (Jogador ator, Jogador outro) {
		// simula a jogada (muda a peça de lugar)
		Peca aux = donde.getPeca ();
		Peca temp = pronde.getPeca ();
		if (temp != null)
			temp.morre ();
		pronde.setPeca (aux);
		donde.setPeca (null);
		
		ator.simulaDominios (simulador);
		outro.simulaDominios (simulador);
		
		boolean retorno = ator.estaXeque (simulador);
		
		// volta peça à posição original
		donde.setPeca (aux);
		pronde.setPeca (temp);
		if (temp != null)
			temp.desmorre ();

		return retorno;
	}
	
	/**
	 * Icone do rolê fica colorido, assim aparece como uma possível jogada
	 */
	public void printPossivel () {
		ImageIcon possivel = Icone.possibilita ((ImageIcon) pronde.getBotao ().getIcon (), pronde.estaOcupada ());
		
		pronde.getBotao ().setIcon (possivel);
	}
	/**
	 * Icone do rolê desfica colorido, assim volta ao normal as coisa
	 */
	public void unPrintPossivel () {
		pronde.atualizaIcone ();
	}
	
	/**
	 * Formula e retorna a notação estrita da jogada
	 * 
	 * @note Aqui não estão incluídas as notações de xeque e mate
	 */
	public String notacaoEscrita () {
		Peca aux = pronde.getPeca ();
		// se a casa está ocupada, notação de 'tomou peça'
		if (aux != null) {
			notacao_extra = "x" + aux;
		}
		
		String str = new String ();			// exemplo de notação
		str += donde.getPeca ();			// D (peça)
		str += notacao_extra;
		str += 8 - pronde.getLinha ();		// 3 (linha)
		str += (char) (pronde.getColuna () + 'a');	// g (coluna)
		
		return str;
	}
	
	public void jogaNoLog () {
		Gui.getTela ().logMovimento (this);
	}
	
	/**
	 * Verifica, a partir da posição indicada (ou da casa), se a casa é a daquele movimento mesmo
	 */
	public boolean ehEsseMovimento (Point P) {
		Casa aux = Tabuleiro.getTabuleiro ().getCasa (P);
		return aux == pronde;
	}
	public boolean ehEsseMovimento (Casa aux) {
		return aux == pronde;
	}
	
	/* GETTERS */
	public boolean podeSerRealizado () {
		return pode;
	}
	/* SETTER */
	public void inutiliza () {
		pode = false;
	}
}
