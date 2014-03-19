/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public enum Icone {
	BISPO ("bispo"),
	CAVALO ("cavalo"),
	DAMA ("dama"),
	PEAO ("peao"),
	REI ("rei"),
	TORRE ("torre");

	ImageIcon img;
	String nome;

	private Icone (String arq) {
		img = new ImageIcon (getClass ().getResource ("img/" + arq + ".png"));
		nome = arq;
	}
	
	public String toString () {
		return nome;
	}

	/* GETTER */
	public ImageIcon getImg () {
		return img;
	}
}
