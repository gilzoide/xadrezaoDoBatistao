/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 20/04/2014
 */
package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Reloginho cronômetro do xadrez!
 * 
 * É um JPanel com o relógio dentro, runnable pra ser executado concorrentemente.
 */
public class Relogio extends JLabel implements Runnable {
	private Timer timer;
	private double tempo;
	
	/**
	 * Ctor
	 */
	public Relogio (double novo_tempo) {
		setHorizontalAlignment (SwingConstants.CENTER);
		tempo = novo_tempo;
		
		// conta a cada centésimo de segundo
		timer = new Timer (10, new ActionListener () {
			public void actionPerformed (ActionEvent evt) {
				tempo += 0.01;
				setText (String.format ("%2d:%2d", (int) tempo/60, (int) tempo%60));
			}
		});
	}
	public Relogio () {
		this (0);
	}
	
	@Override
	public void run () {
		start ();
	}
	/**
	 * Começa ou para o timer
	 */
	public void start () {
		timer.start ();
	}
	public void stop () {
		timer.stop ();
	}
	
	/* GETTERS */
	public double getTempo () {
		return tempo;
	}
	/* SETTERS */
	public void setTempo (double tempo) {
		this.tempo = tempo;
	}
	
	@Override
	public String toString () {
		return Double.toString (tempo);
	}
}
