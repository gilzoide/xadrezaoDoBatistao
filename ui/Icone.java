/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 29/06/2014
 */
package ui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

public enum Icone {
	BISPO ("bispo"),
	CAVALO ("cavalo"),
	DAMA ("dama"),
	PEAO ("peao"),
	REI ("rei"),
	TORRE ("torre"),
	POSSIVEL ("possivel");

	ImageIcon img_branca;
	ImageIcon img_preta;
	String nome;

	private Icone (String arq) {
		img_branca = new ImageIcon (getClass ().getResource ("img/" + arq + ".png"));
		img_preta = new ImageIcon (getClass ().getResource ("img/!" + arq + ".png"));
		nome = arq;
	}
	
	@Override
	public String toString () {
		return nome;
	}

	/* GETTER */
	public ImageIcon getImg (Cor cor) {
		return (cor == Cor.BRANCO) ? img_branca : img_preta;
	}
	
	/**
	 * Faz o ícone receber aquele quadradinho de possível
	 */
	public static ImageIcon possibilita (ImageIcon img, boolean marcado_pra_morrer) {
		BufferedImage junto = new BufferedImage (Gui.TAM_QUADRADO, Gui.TAM_QUADRADO, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = junto.createGraphics ();
		
		// img que já estava
		if (img != null) {
			Image normal = img.getImage ();
			g2.drawImage (normal, 0, 0, null);
		}
		// imagem de possibilidade
		Image possivel;
		if (marcado_pra_morrer)
			possivel = Icone.POSSIVEL.getImg (Cor.PRETO).getImage ();
		else
			possivel = Icone.POSSIVEL.getImg (Cor.BRANCO).getImage ();

		g2.drawImage (possivel, 0, 0, null);
		g2.dispose ();
		
		return new ImageIcon (junto);
	}
}
