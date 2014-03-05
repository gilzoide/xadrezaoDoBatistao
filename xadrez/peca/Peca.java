/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 05/03/2014
 */
package xadrez.peca;

import ui.Cor;
import xadrez.Movimento;
import java.util.ArrayList;

public abstract class Peca {
	private Cor cor;	// cor da peça
	private char letra;	// letra da notação da peça: depende do tipo
	
	/**
	 * Ctor: ajusta a cor
	 */
	public Peca (Cor nova_cor, char nova_letra) {
		cor = nova_cor;
		letra = nova_letra;
	}
	
	/**
	 * Calcula os possíveis movimentos da peça, retornando um array de possibilidades de movimentos
	 * 
	 * @return Lista de possíveis movimentos, cada um organizado em um vetor de 2 ints: linha e coluna
	 */
	public Movimento[] possiveisMovimentos (byte linha, byte coluna) {
		return null;
	}
	
	public char getNotacao () {
		return letra;
	}
}
