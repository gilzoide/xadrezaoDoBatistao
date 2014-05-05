/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 20/04/2014
 */
package ui;

import xadrez.Xadrez;
import xadrez.tabuleiro.Casa;
import xadrez.tabuleiro.Tabuleiro;
import xadrez.movimento.Movimento;

import java.awt.SplashScreen;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Random;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

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
import javax.swing.JOptionPane;
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
	final private Point INICIO_TABULEIRO = new Point (110, 50);	/// início do tabuleiro (pra n ficar hardcodando se quiser trocar)
	final private Point TAM_LOG = new Point (180, (int) TAM_TABULEIRO);	/// tamanho do log de movimentos
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
		// splash screen
		try {
			SplashScreen window = SplashScreen.getSplashScreen ();
			if (window == null) {
				System.out.println ("SplashScreen não especificada. Por favor, rode o programa com a opção '-splash:ui/img/splash.png'");
			}
			else {
				try {
					AudioClip entrada = Applet.newAudioClip (new File ("audio/entrada.wav").toURI ().toURL ());
					entrada.loop ();
				}
				catch (MalformedURLException e) {
					System.out.println ("Audio não encontrado!");
				}
				
				try {
					Thread.sleep (1000);
				}
				catch(InterruptedException e) {
					System.out.println ("mataram a splash screen");
					System.exit (0);
				}
				window.close ();
			}
		}
		catch (UnsupportedOperationException e) {
			System.out.println ("SplashScreen não suportado");
		}

		// resto dos trem
		JPanel panel = new JPanel ();
		getContentPane ().add (panel);
		panel.setLayout (null);
		
		montaQuemJoga (panel);
		montaTabuleiro (panel, motor);
		montaLog (panel);
		menu (panel, motor);
		
		novoJogo ();
		
		setVisible (true);
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
	private void menu (JPanel panel, final Xadrez motor) {
		JMenuBar barra = new JMenuBar ();
		// MENU 'jogo'
		JMenu Jogo = new JMenu ("Jogo");
		Jogo.setMnemonic (KeyEvent.VK_J);
		
		// Item 'novo jogo'
		JMenuItem Novo = new JMenuItem ("Novo jogo   ^N");
		Novo.setMnemonic (KeyEvent.VK_N);
		Novo.setToolTipText ("Começa um novo jogo");
		Jogo.add (Novo);
		//ctrlN começa novo jogo
		Novo.getInputMap (JComponent.WHEN_IN_FOCUSED_WINDOW).put (KeyStroke.getKeyStroke (KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), "novo");
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
		
		// Item 'desfazer movimento'
		JMenuItem Desfazer = new JMenuItem ("Desfazer Movimento   ^Z");
		Desfazer.setMnemonic (KeyEvent.VK_D);
		Desfazer.setToolTipText ("Desfaz último movimento");
		Jogo.add (Desfazer);
		//ctrlZ desfaz movimento
		Desfazer.getInputMap (JComponent.WHEN_IN_FOCUSED_WINDOW).put (KeyStroke.getKeyStroke (KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "desfazer");
		Desfazer.getActionMap ().put ("desfazer", new AbstractAction () {
			@Override
			public void actionPerformed (ActionEvent event) {
				motor.desfazerMovimento ();
			}
		});

		Desfazer.addActionListener (new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				motor.desfazerMovimento ();
			}
		});
		
		// Item 'refazer movimento'
		JMenuItem Refazer = new JMenuItem ("Refazer Movimento   ^R");
		Refazer.setMnemonic (KeyEvent.VK_R);
		Refazer.setToolTipText ("Refaz último movimento");
		Jogo.add (Refazer);
		//ctrlR refaz movimento
		Refazer.getInputMap (JComponent.WHEN_IN_FOCUSED_WINDOW).put (KeyStroke.getKeyStroke (KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK), "refazer");
		Refazer.getActionMap ().put ("refazer", new AbstractAction () {
			@Override
			public void actionPerformed (ActionEvent event) {
				motor.refazerMovimento ();
			}
		});

		Desfazer.addActionListener (new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				motor.refazerMovimento ();
			}
		});
		
		// Item 'sair'
		JMenuItem Sair = new JMenuItem ("Sair   ^Q");
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

		Sair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit (0);
			}
		});
		Jogo.add (Sair);
		
		// MENU 'jogadas'
		JMenu jogador = new JMenu ("Jogador");
		jogador.setMnemonic (KeyEvent.VK_O);
		
		// Item 'renomear'
		JMenuItem Renomear = new JMenuItem ("Renomear jogador");
		Renomear.setMnemonic (KeyEvent.VK_R);
		Renomear.setToolTipText ("Conte-nos seu nome, jovem guerreiro");
		Renomear.addActionListener (new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent event) {
				Jogador j = Xadrez.getDaVez ();
				
				String novo_nome = JOptionPane.showInputDialog ("Novo nome pr@ " + j);
				
				j.setNome (novo_nome);
				trocaJogador (j);	// escreve lá novo nome
			}
		});
		jogador.add (Renomear);
		
		// Item 'empatar'
		JMenuItem Empatar = new JMenuItem ("Empatar jogo");
		Empatar.setMnemonic (KeyEvent.VK_E);
		Empatar.setToolTipText ("Propõe um empate ao outro jogador");
		Empatar.addActionListener (new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent event) {
				// para a tela e pergunta se quer começar novo jogo, ou quitar
				int n = JOptionPane.showConfirmDialog (Gui.getTela (), Xadrez.getDaVez () + " está propondo um empate. Aceitas?", "Proposta de empate", JOptionPane.YES_NO_OPTION);
				
				if (n == JOptionPane.YES_OPTION) {	// quer empatar
					empata ();
				}
			}
		});
		jogador.add (Empatar);
		
		// Item 'desistir'
		JMenuItem Desistir = new JMenuItem ("Desistir do jogo");
		Desistir.setMnemonic (KeyEvent.VK_D);
		Desistir.setToolTipText ("Dá um peteleco no rei e desiste da partida");
		Desistir.addActionListener (new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent event) {
				// para a tela e pergunta se quer começar novo jogo, ou quitar
				int n = JOptionPane.showConfirmDialog (Gui.getTela (), Xadrez.getDaVez () + ", vai mesmo desistir?", "¿Fim de jogo?", JOptionPane.YES_NO_OPTION);
				
				if (n == JOptionPane.YES_OPTION) {	// quer desistir
					quem_joga.setText (Xadrez.getDaVez () + " jogou a toalha!");
					Xadrez.acabaPartida ();
				}
			}
		});
		jogador.add (Desistir);
		
		// povoa a barra de menu
		barra.add (Jogo);
		barra.add (jogador);

		setJMenuBar (barra);
	}

	/**
	 * Pediu novo jogo
	 * 
	 * Recomeça um novo jogo, repõe as peças no lugar certo
	 */
	private void novoJogo () {
		Xadrez.novoJogo ();
		Movimento.novoJogo ();
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
	 * Escreve na tela que empatou, e acaba o jogo
	 */
	public void empata () {
		quem_joga.setText ("Jogo empatado!");
		Xadrez.acabaPartida ();
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
	 * Escreve na tela que deu mate
	 */
	public void mate (Jogador J) {
		quem_joga.setForeground (Color.RED);
		quem_joga.setText ("Xeque mate! " + J + ", seu rei já era! VWAHAHAHAHA");
		log.addMate ();	// um '++', é mate!
		
		// quantas rodadas durou o jogo
		int rodadas = (int) Math.ceil (Movimento.getNumMovs () / 2.0);
		// se foi 4, é xeque do pastor xP
		String pastor = "";
		if (rodadas == 4)
			pastor = "Po, " + J + ", de xeque do pastor... Xupa essa!\n";
			
		// para a tela e pergunta se quer começar novo jogo, ou quitar
		int n = JOptionPane.showConfirmDialog (this, "Você perdeu em " + rodadas + " rodadas.\n" + pastor + "Que tal um novo jogo?", "Fim de jogo!", JOptionPane.YES_NO_OPTION);
		switch (n) {
			case JOptionPane.YES_OPTION:
				novoJogo ();
				break;
			
			case JOptionPane.NO_OPTION: case JOptionPane.CLOSED_OPTION:
				System.exit (0);
		}
	}
	
	/**
	 * Escreve o movimento no log
	 */
	public void logMovimento (Movimento mov) {
		log.addMovimento (mov);
	}
	public void setLog (String log) {
		this.log.setLogText (log);
	}
	public String getLog () {
		return log.getTextArea ().getText ();
	}
}
