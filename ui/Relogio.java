/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 20/04/2014
 */
package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * Reloginho cronômetro do xadrez!
 * 
 * É um JPanel com o relógio dentro. Como o swing.Timer
 * já roda em paralelo, nem pus o Relogio Runnable nem Thread.
 */
public class Relogio extends JLabel {
	private Timer timer;
	private double tempo;
	private String nome;
	
	/**
	 * Ctor
	 */
	public Relogio () {
		this ("");
	}
	public Relogio (String novo_nome) {
		setHorizontalAlignment (SwingConstants.CENTER);
		setTempo (0);
		this.nome = novo_nome;
		
		// conta a cada centésimo de segundo
		timer = new Timer (10, new ActionListener () {
			public void actionPerformed (ActionEvent evt) {
				tempo += 0.01;
				setText (String.format ("%s: %2d:%2d", nome, (int) tempo/60, (int) tempo % 60));
			}
		});
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
		setText (String.format ("%s: %2d:%2d", nome, (int) tempo/60, (int) tempo % 60));
	}
	public void setNome (String nome) {
		this.nome = nome;
		if (nome.equals ("cuzao"))
			setForeground (new Color (255, 0, 255));
		else
			setForeground (Color.BLACK);
	}
	
	@Override
	public String toString () {
		return Double.toString (tempo);
	}
}
