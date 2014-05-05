/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 20/04/2014
 */
package ui;

import xadrez.movimento.Movimento;

import javax.swing.JTextArea;

public class Log {
	private JTextArea log;
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
	 * Adiciona uma linha no log
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
		addLinha (mov.notacaoEscrita ());
		cor = cor.oposta ();
	}
	
	/**
	 * Refaz o log
	 * 
	 * @param log string a ser escrita no log
	 */
	public void setLogText (String log) {
		this.log.setText (log);
		cor = cor.oposta ();
	}
	
	/* GETTERS */
	public JTextArea getTextArea () {
		return log;
	}
}
