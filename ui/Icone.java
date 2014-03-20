/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package ui;

import javax.swing.GrayFilter;
import javax.swing.ImageIcon;

public enum Icone {
	BISPO ("bispo"),
	CAVALO ("cavalo"),
	DAMA ("dama"),
	PEAO ("peao"),
	REI ("rei"),
	TORRE ("torre");

	ImageIcon img_branca;
	ImageIcon img_preta;
	String nome;

	private Icone (String arq) {
		img_branca = new ImageIcon (getClass ().getResource ("img/" + arq + ".png"));
		img_preta = new ImageIcon (getClass ().getResource ("img/!" + arq + ".png"));
		nome = arq;
	}
	
	public String toString () {
		return nome;
	}

	/* GETTER */
	public ImageIcon getImg (Cor cor) {
		return (cor == Cor.BRANCO) ? img_branca : img_preta;
	}
}
