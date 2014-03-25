/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package ui;

import xadrez.Xadrez;
import xadrez.Tabuleiro;
import xadrez.Casa;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import javax.imageio.ImageIO;

import javax.swing.KeyStroke;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.AbstractAction;

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

	final private int JANELA_LARGURA = 800;
	final private int JANELA_ALTURA = 600;
	final private int TAM_QUADRADO = 60;
	/**
	 * Ctor: contrói a tela principal
	 */
	private Gui () {
		setTitle ("Xadrezão do Batistão");
		setSize (JANELA_LARGURA, JANELA_ALTURA);		// 800x600, é um tamanho bom
		setLocationRelativeTo(null);
		setDefaultCloseOperation (EXIT_ON_CLOSE);
	}
	
	public void init (Xadrez motor) {
		montaTabuleiro (motor);
		menu ();
	}

	/**
	 * monta o tabuleiro em botões
	 */
	private void montaTabuleiro (final Xadrez motor) {
		// JPanel: tabuleiro
		JPanel panel = new JPanel ();
		getContentPane ().add (panel);
		panel.setLayout (null);

		// cada casa é um botão
		for (byte i = 0; i < 8; i++) {
			for (byte j = 0; j < 8; j++) {
				final JButton botao = new JButton ();
				botao.setBounds (j * TAM_QUADRADO, i * TAM_QUADRADO, TAM_QUADRADO, TAM_QUADRADO);
				botao.setName (Integer.toString(i) + Integer.toString(j));
				// cor do fundo, preto ou branco
				Color cor = ((i + j) % 2 == 0) ? Color.WHITE : Color.GRAY;
				botao.setBackground (cor);
				
				// o que o botao faz (escreve linha x coluna, e motor processa a casa clicada)
				botao.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed (ActionEvent event) {
						int linha = Integer.parseInt (botao.getName ().substring (0, 1));
						int coluna = Integer.parseInt (botao.getName ().substring (1, 2));
						//System.out.println (linha + " " + coluna);
						motor.cliquei (linha, coluna);
					}
				});
				
				// botão tá dentro da Casa agora!
				Casa aux = Tabuleiro.getTabuleiro ().getCasa (i, j);
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
		// MENU 'jogo'
		JMenu Jogo = new JMenu ("Jogo");
		Jogo.setMnemonic (KeyEvent.VK_J);
		
		// Item 'novo jogo'
		JMenuItem Novo = new JMenuItem ("Novo jogo");
		Novo.setMnemonic (KeyEvent.VK_N);
		Novo.setToolTipText ("Começa um novo jogo");
		Jogo.add (Novo);
		//ctrlN sai do jogo
		Novo.getInputMap (JComponent.WHEN_IN_FOCUSED_WINDOW).put (KeyStroke.getKeyStroke (KeyEvent.VK_N, InputEvent.CTRL_MASK), "novo");
		Novo.getActionMap ().put ("novo", new AbstractAction () {
			@Override
			public void actionPerformed (ActionEvent event) {
				Reinicia ();
			}
		});

		Novo.addActionListener (new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Reinicia ();
			}
		});
		
		// Item 'sair'
		JMenuItem Sair = new JMenuItem ("Sair");
		Sair.setMnemonic (KeyEvent.VK_S);
		Sair.setToolTipText ("Sai do jogo (você quer mesmo fazer isso?)");
		//ctrlQ sai do jogo
		Sair.getInputMap (JComponent.WHEN_IN_FOCUSED_WINDOW).put (KeyStroke.getKeyStroke (KeyEvent.VK_Q, InputEvent.CTRL_MASK), "sair");
		Sair.getActionMap ().put ("sair", new AbstractAction () {
			@Override
			public void actionPerformed (ActionEvent event) {
				System.exit (0);
			}
		});
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

	/**
	 * Pediu novo jogo
	 * 
	 * Se tem que recomeçar um novo jogo (seja por fim do de antes, ou pediu no menu),
	 * repõe as peças no lugar certo =]
	 */
	private void Reinicia () {
		Tabuleiro.getTabuleiro ().novoJogo ();
	}
}