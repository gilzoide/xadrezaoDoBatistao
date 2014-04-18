/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 05/04/2014
 */
package ui;

import xadrez.movimento.Movimento;

import javax.swing.JTextArea;

public class Log {
	private JTextArea log;
	
	/**
	 * Ctor - constrói o log
	 */
	public Log (int lin, int col) {
		log = new JTextArea (lin, col);
		log.setEditable (false);
		novoJogo ();
	}
	
	public void novoJogo () {
		log.setText ("");
		log.append ("Log de Jogadas\n");
	}
	
	/**
	 * Adiciona uma linha no log, sempre terminada em '\n'
	 */
	public void addLinha (String str) {
		log.append ("\n" + str);
	}
	
	public void addXeque () {
		log.append ("+");
	}
	
	/**
	 * Adiciona ao log a notação escrita do movimento 'mov'
	 */
	public void addMovimento (Movimento mov) {
		addLinha (mov.notacaoEscrita ());
	}
	
	/* GETTERS */
	public JTextArea getTextArea () {
		return log;
	}
}
