/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 29/06/2014
 */
package xadrez.peca;

import ui.Cor;

/**
 * Fábrica de peças!
 * 
 * Implementa o padrão de projeto Factory.
 */
public class PecaFactory {
	/**
	 * Cria uma peça a partir do nome da tal.
	 * 
	 * @note Nomes de peças são com a primeira letra maiúscula
	 * e as outras minúsculas. Caso um nome inválido é encontrado,
	 * essa função retorna um _null_.
	 */
	public Peca cria (String peca) {
		if (peca == "Bispo")
			return criaBispo ();
		else if (peca == "Cavalo")
			return criaCavalo ();
		else if (peca == "Dama")
			return criaDama ();
		else if (peca == "Rei")
			return criaRei ();
		else if (peca == "Torre")
			return criaTorre ();
		else if (peca == "Peao")
			return criaPeao ();
		else
			return null;
	}
	
	public Bispo criaBispo () {
		return new Bispo (Cor.BRANCO, 0, 0);
	}
	
	public Cavalo criaCavalo () {
		return new Cavalo (Cor.BRANCO, 0, 0);
	}
	
	public Dama criaDama () {
		return new Dama (Cor.BRANCO, 0, 0);
	}
	
	public Rei criaRei () {
		return new Rei (Cor.BRANCO, 0, 0);
	}
	
	public Peao criaPeao () {
		return new Peao (Cor.BRANCO, 0, 0);
	}
	
	public Torre criaTorre () {
		return new Torre (Cor.BRANCO, 0, 0);
	}
}
