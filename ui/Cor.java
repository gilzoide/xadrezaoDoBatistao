/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 29/06/2014
 */
package ui;

import java.io.Serializable;

public enum Cor implements Serializable {
	BRANCO,
	PRETO,
	// específicos de domínio na casa
	AMBOS,
	LIVRE;
	
	public Cor oposta () {
		if (this == BRANCO)
			return PRETO;
		else
			return BRANCO;
	}
	
	/**
	 * Pra domínio [na casa], adiciona nova cor
	 */
	public Cor dominioAdd (Cor nova) {
		if (nova == AMBOS || this == nova.oposta ())
			return AMBOS;
		else if (this == LIVRE)
			return nova;
		else
			return this;
	}
	/**
	 * Pra domínio [na casa], retira uma cor
	 */
	public Cor dominioRemove (Cor a_tirar) {
		if (this == AMBOS)
			return a_tirar.oposta ();
		else if (a_tirar == AMBOS || this == a_tirar)
			return LIVRE;
		else
			return this;
	}
	/**
	 * verifica se peça da cor 'sua_cor' é ameaçada pelo domínio (pro rei não mover aonde não pode);
	 */
	public boolean ameaca (Cor sua_cor) {
		if (sua_cor == BRANCO) {
			if (this == BRANCO || this == LIVRE)
				return false;
			else
				return true;
		}
		
		else {
			if (this == PRETO || this == LIVRE)
				return false;
			else
				return true;
		}
	}
}
