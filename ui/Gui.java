/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 09/03/2014
 */
package ui;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Gui: padrão singleton em POO, já que a tela é única e sem frescura
 */
public class Gui extends JFrame {	
	private static Gui tela = new Gui ();
	
	public static Gui getTela () {
		return tela;
	}
	
	
	final private int TAM_QUADRADO = 50;
	/**
	 * Ctor: contrói a tela principal
	 */
	private Gui () {
		printTabuleiro ();
		menu ();
		
		setTitle ("Xadrezão do Batistão");
		setSize (800, 600);		// 800x600, é um tamanho bom
		setLocationRelativeTo(null);
		setDefaultCloseOperation (EXIT_ON_CLOSE);
		
	}
	
	private void printTabuleiro () {
		// JPanel: tabuleiro
		JPanel panel = new JPanel ();
		getContentPane().add(panel);
		panel.setLayout (null);
		
		// cada casa é um botão
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				JButton button = new JButton (Integer.toString (i*8 + j));
				button.setBounds (j * TAM_QUADRADO, i * TAM_QUADRADO, TAM_QUADRADO, TAM_QUADRADO);
				// cor do fundo, preto ou branco
				Color cor = ((i + j) % 2 == 0) ? Color.WHITE : Color.BLACK;
				button.setBackground (cor);
				
				panel.add (button);
			}
		}
	}
	
	private void menu () {
		JMenuBar barra = new JMenuBar ();
		// menu Jogo
		JMenu Jogo = new JMenu ("Jogo");
		Jogo.setMnemonic (KeyEvent.VK_J);
		
		JMenuItem Novo = new JMenuItem ("Novo jogo");
		Novo.setMnemonic (KeyEvent.VK_N);
		Novo.setToolTipText ("Começa um novo jogo");
		
		Jogo.add (Novo);
		
		barra.add (Jogo);
		
		setJMenuBar (barra);
	}
}
