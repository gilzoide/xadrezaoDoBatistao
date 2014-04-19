/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 18/04/2014
 */
package ui;

import xadrez.Xadrez;
import xadrez.tabuleiro.Casa;
import xadrez.tabuleiro.Tabuleiro;
import xadrez.movimento.Movimento;

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import javax.imageio.ImageIO;

import java.util.Random;

import javax.swing.KeyStroke;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import javax.swing.AbstractAction;

/**
 * Gui: padrão singleton em POO, já que a tela é única e sem frescura
 */
public class Gui extends JFrame {
	private static Gui tela = new Gui ();

	public static Gui getTela () {
		return tela;
	}

	/**
	 * Constantes
	 */
	final static int TAM_QUADRADO = 60;	/// tamanho do lado do quadrado (casa do tabuleiro)
	final private int TAM_TABULEIRO = TAM_QUADRADO * 8;	/// tamanho do lado do tabuleiro
	
	final private Point TAM_JANELA = new Point (800, 600);	/// tamanho total da janela
	final private Point INICIO_TABULEIRO = new Point (150, 50);	/// início do tabuleiro (pra n ficar hardcodando se quiser trocar)
	final private Point TAM_LOG = new Point ((int) INICIO_TABULEIRO.getX () - 10, (int) TAM_TABULEIRO);	/// tamanho do log de movimentos
	final private Point INICIO_LOG = new Point ((int) INICIO_TABULEIRO.getX () + TAM_TABULEIRO + 10, (int) INICIO_TABULEIRO.getY ());	/// início do log de movimentos #xupa hardcode
	
	
	private JLabel quem_joga;	// JLabel que escreve de quem é a vez
	private Log log;		// lugar pra escrever o log de jogadas
	
	/**
	 * Ctor: contrói a tela principal
	 */
	private Gui () {
		setTitle ("Xadrezão do Batistão");
		setSize ((int) TAM_JANELA.getX (), (int) TAM_JANELA.getY ());		// 800x600, é um tamanho bom
		setLocationRelativeTo(null);
		setDefaultCloseOperation (EXIT_ON_CLOSE);
	}
	/**
	 * Inicializa o tabuleiro, menu e log
	 */
	public void init (Xadrez motor) {
		// JPanel: tudão
		JPanel panel = new JPanel ();
		getContentPane ().add (panel);
		panel.setLayout (null);
		
		montaTabuleiro (panel, motor);
		montaLog (panel);
		menu ();
	}

	/**
	 * monta o tabuleiro em botões
	 */
	private void montaTabuleiro (JPanel panel, final Xadrez motor) {
		// cada casa é um botão
		for (byte i = 0; i < 8; i++) {
			// escreve o número da linha
			JLabel numero = new JLabel (Integer.toString (8 - i), SwingConstants.CENTER);
			numero.setBounds ((int) INICIO_TABULEIRO.getX () - 10, (int) INICIO_TABULEIRO.getY () + i * TAM_QUADRADO, 10, TAM_QUADRADO);
			panel.add (numero);
			
			for (byte j = 0; j < 8; j++) {
				final JButton botao = new JButton ();
				botao.setBounds ((int) INICIO_TABULEIRO.getX () + j * TAM_QUADRADO, (int) INICIO_TABULEIRO.getY () + i * TAM_QUADRADO, TAM_QUADRADO, TAM_QUADRADO);
				botao.setName (Integer.toString(i) + Integer.toString(j));
				// cor do fundo, preto ou branco
				Color cor = ((i + j) % 2 == 0) ? Color.WHITE : Color.GRAY;
				botao.setBackground (cor);
				
				// o que o botao faz (escreve linha x coluna, e motor processa a casa clicada)
				botao.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed (ActionEvent event) {
						Point P = new Point (Integer.parseInt (botao.getName ().substring (1, 2)), Integer.parseInt (botao.getName ().substring (0, 1)));
						//System.out.println (linha + " " + coluna);
						motor.cliquei (P);
					}
				});
				
				// botão tá dentro da Casa agora!
				Casa aux = Tabuleiro.getTabuleiro ().getCasa (i, j);
				aux.setBotao (botao);
				aux.atualizaIcone ();

				panel.add (botao);
			}
		}
		// escreve a letra da coluna
		for (byte j = 0; j < 8; j++) {
			JLabel letra = new JLabel (String.valueOf ((char) (j + 'a')), SwingConstants.CENTER);
			letra.setBounds ((int) INICIO_TABULEIRO.getX () + j * TAM_QUADRADO, (int) INICIO_TABULEIRO.getY () + TAM_TABULEIRO, TAM_QUADRADO, 15);
			panel.add (letra);
		}

		// Xadrez.novoJogo()
		motor.novoJogo ();
		
		montaQuemJoga (panel);
	}
	/**
	 * escreve em cima do tabuleiro de quem é a vez
	 */
	private void montaQuemJoga (JPanel panel) {		
		quem_joga = new JLabel ("Jogador BRANCO, comece o jogo!", SwingConstants.CENTER);
		quem_joga.setBackground (Color.WHITE);
		quem_joga.setBounds ((int) INICIO_TABULEIRO.getX (), (int) INICIO_TABULEIRO.getY () - 40, TAM_TABULEIRO, 40);
		panel.add (quem_joga);
	}
	/**
	 * Monta Log de movimentos
	 */
	private void montaLog (JPanel panel) {
		log = new Log (20, 1);
		JScrollPane scroller = new JScrollPane (log.getTextArea ());
		scroller.setBounds ((int) INICIO_LOG.getX (), (int) INICIO_LOG.getY (), (int) TAM_LOG.getX (), (int) TAM_LOG.getY ());
		
		panel.add (scroller);
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
				novoJogo ();
			}
		});

		Novo.addActionListener (new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				novoJogo ();
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
		
		// MENU 'jogadas'
		

		setJMenuBar (barra);
	}

	/**
	 * Pediu novo jogo
	 * 
	 * Se tem que recomeçar um novo jogo (seja por fim do de antes, ou pediu no menu),
	 * repõe as peças no lugar certo =]
	 */
	private void novoJogo () {
		Xadrez.novoJogo ();
		log.novoJogo ();
		quem_joga.setForeground (Color.BLACK);
		quem_joga.setText ("Jogador BRANCO, comece o jogo!");
	}
	
	/**
	 * Escreve de quem é a vez (pra ser chamado depois de trocar jogador atual)
	 */
	public void trocaJogador (Jogador J) {
		Random rand = new Random ();
		String [] str = {
			"sua vez",
			"não me desaponte!",
			"faça aqueeeeeeela jogada!",
			"manda ver!",
			"vai aí",
			"pense com carinho e jogue!",
			"destrói esse palhaço!",
			"vai com fé, irmão!",
			"vai, não desista!",
			"bola pra frente",
			"relaxa e joga",
			"fique sempre atento ao rei, valeu?",
			"cuidado aí, rapaz!",
			"vai, vai, vai, tchananã,nananãã",
			"é sua vez, sô",
			"vai que é tua!"
		};
		
		quem_joga.setForeground (Color.BLACK);
		quem_joga.setText (J + ", " + str[rand.nextInt (16)]);
	}
	/**
	 * Escreve na tela se deu xeque
	 */
	public void xeque (Jogador J) {
		quem_joga.setForeground (Color.RED);
		quem_joga.setText (J + " está em xeque, faça alguma coisa! =S");
		log.addXeque ();
	}
	/**
	 * Escreve na tela que deu mate! (e já 
	 */
	public void mate (Jogador J) {
		quem_joga.setForeground (Color.RED);
		quem_joga.setText ("Xeque mate! " + J + ", seu rei já era! VWAHAHAHAHA");
		log.addXeque ();	// mais um '+', que quer dizer mate
		// para a tela e pergunta se quer começar novo jogo, ou quitar
		
	}
	
	/**
	 * Escreve o movimento no log
	 */
	public void logMovimento (Movimento mov) {
		log.addMovimento (mov);
	}
}
