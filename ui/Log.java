/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 20/04/2014
 */
package ui;

import xadrez.movimento.Movimento;

import javax.swing.JTextArea;

public class Log {
	private JTextArea log;
	private String anterior;
	Cor cor;
	
	/**
	 * Ctor - constrói o log
	 */
	public Log (int lin, int col) {
		log = new JTextArea (lin, col);
		log.setEditable (false);
		novoJogo ();
	}
	
	public void novoJogo () {
		cor = Cor.BRANCO;
		log.setText ("");
		log.append ("         Log de Jogadas\n");
		log.append ("Branco\tPreto\n");
	}
	
	/**
	 * Adiciona uma linha no log, sempre terminada em '\n'
	 */
	public void addLinha (String str) {
		if (cor == Cor.BRANCO)
			log.append ("\n" + str);
		else
			log.append ("\t" + str);
	}
	
	public void addXeque () {
		log.append ("+");
	}
	
	public void addMate () {
		log.append ("++");
	}
	
	/**
	 * Adiciona ao log a notação escrita do movimento 'mov'
	 */
	public void addMovimento (Movimento mov) {
		anterior = mov.notacaoEscrita ();
		addLinha (anterior);
		cor = cor.oposta ();
	}
	
	/**
	 * Se desfez o movimento, retira do log
	 */
	public void removeMovimento () {
		String aux = log.getText ();
		for (int i = 0; i <= anterior.length (); i++)
			log.append ("\r");
	}
	
	/* GETTERS */
	public JTextArea getTextArea () {
		return log;
	}
}
