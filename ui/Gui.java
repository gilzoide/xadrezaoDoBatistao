/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package ui;

import xadrez.Tabuleiro;
import xadrez.Casa;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


	final private int TAM_QUADRADO = 60;
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

	/**
	 * monta o tabuleiro em botões
	 */
	private void printTabuleiro () {
		// JPanel: tabuleiro
		JPanel panel = new JPanel ();
		getContentPane().add(panel);
		panel.setLayout (null);

		// cada casa é um botão
		for (byte i = 0; i < 8; i++) {
			for (byte j = 0; j < 8; j++) {
				Casa aux = Tabuleiro.getTabuleiro ().getCasa (i, j);
				JButton botao = new JButton ();
				botao.setBounds (j * TAM_QUADRADO, i * TAM_QUADRADO, TAM_QUADRADO, TAM_QUADRADO);
				// cor do fundo, preto ou branco
				Color cor = ((i + j) % 2 == 0) ? Color.WHITE : Color.BLACK;
				botao.setBackground (cor);
				//botao.setIcon (aux.getIcone ());
				
				aux.setBotao (botao);
				aux.atualizaIcone ();

				panel.add (botao);
			}
		}
	}
	/**
	 * monta o menu
	 */
	private void menu () {
		JMenuBar barra = new JMenuBar ();
		// menu Jogo
		JMenu Jogo = new JMenu ("Jogo");
		Jogo.setMnemonic (KeyEvent.VK_J);

		JMenuItem Novo = new JMenuItem ("Novo jogo");
		Novo.setMnemonic (KeyEvent.VK_N);
		Novo.setToolTipText ("Começa um novo jogo");
		Jogo.add (Novo);

		Novo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Reinicia ();
			}
		});

		JMenuItem Sair = new JMenuItem ("Sair");
		Sair.setMnemonic (KeyEvent.VK_S);
		Sair.setToolTipText ("Sai do jogo (você quer mesmo fazer isso?)");
		Jogo.add (Sair);

		Sair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});


		barra.add (Jogo);

		setJMenuBar (barra);
	}

	private void Reinicia () {
		tela = new Gui ();
	}
}
