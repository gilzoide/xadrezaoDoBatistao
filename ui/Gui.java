/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 29/06/2014
 */
package ui;

import xadrez.Xadrez;
import xadrez.ObServer;
import xadrez.Partida;
import xadrez.SessionManager;
import xadrez.tabuleiro.Casa;
import xadrez.tabuleiro.Tabuleiro;
import xadrez.movimento.Movimento;
import xadrez.movimento.IllegalChessMovement;

import java.awt.SplashScreen;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Random;

import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;



/**
 * Gui: padrão singleton em POO, já que a tela é única e sem frescura
 * 
 * Implementa a interface Runnable pra rodar em uma Thread separada,
 * pra não ser bloqueado pela conexão (veja ObServer).
 */
public class Gui extends JFrame {
	// Single...
	private static Gui tela = new Gui ();
	public static Gui getTela () {
		return tela;
	}
	// ...ton
	
	private SessionManager sessao;
	private Xadrez motor;

	/**
	 * Constantes
	 */
	final static int TAM_QUADRADO = 60;	/// tamanho do lado do quadrado (casa do tabuleiro)
	final private int TAM_TABULEIRO = TAM_QUADRADO * 8;	/// tamanho do lado do tabuleiro
	
	final private Point TAM_JANELA = new Point (800, 600);	/// tamanho total da janela
	final private Point INICIO_TABULEIRO = new Point (10, 37);	/// início do tabuleiro (pra n ficar hardcodando se quiser trocar)
	final private Point TAM_LOG = new Point (180, (int) TAM_TABULEIRO);	/// tamanho do log de movimentos
	final private Point INICIO_LOG = new Point (0, 0);	/// início do log de movimentos #xupa hardcode
	
	private JLabel quem_joga;	// JLabel que escreve de quem é a vez
	private Log log;		// lugar pra escrever o log de jogadas
	// Relógios
	private JPanel rel;		// painel dos relógios
	private JLabel J1, total, J2;
	
	/**
	 * Ctor: contrói a tela principal
	 */
	private Gui () {
		setTitle ("Xadrezão do Batistão");
		setSize ((int) TAM_JANELA.getX (), (int) TAM_JANELA.getY ());		// 800x600, é um tamanho bom; clááááássico SVGA
		setLocationRelativeTo(null);
		setDefaultCloseOperation (EXIT_ON_CLOSE);
	}

	/**
	 * Inicializa o tabuleiro, menu e log
	 */
	public void init (Xadrez motor) {
		this.motor = motor;

		// abre splash screen
		SplashScreen window = SplashScreen.getSplashScreen ();
		if (window == null) {
			System.out.println ("SplashScreen não especificada. Para mostrá-la, rode o programa com a opção '-splash:ui/img/splash.png'");
		}

		// constrói resto dos trem; enquanto isso, a splashscreen ainda tá lá
		JPanel tab = new JPanel ();
		tab.setLayout (null);
		JPanel log = new JPanel ();
		rel = new JPanel ();
		rel.setLayout (new BorderLayout ());
		getContentPane ().add (tab, BorderLayout.CENTER);
		getContentPane ().add (log, BorderLayout.LINE_END);
		getContentPane ().add (rel, BorderLayout.PAGE_START);
		
		total = new JLabel ("", SwingConstants.CENTER);
		J1 = new JLabel ("", SwingConstants.CENTER);
		J2 = new JLabel ("", SwingConstants.CENTER);
		
		montaQuemJoga (tab);
		montaTabuleiro (tab);
		montaLog (log);

		// fecha splash screen
		if (window != null) {
			window.close ();
		}

		// mostra janela do jogo
		setVisible (true);
		
		// Pergunta se quer jogar na rede
		perguntaConexao ();
		
		// Monta menu (algumas opções são desativadas se estiver em rede, inclusive save/auto)
		if (!ObServer.estaEmRede ())
			sessao = new SessionManager (motor);
		menu ();
		
		novoJogo ();
		
	}

	/**
	 * monta o tabuleiro em botões
	 */
	private void montaTabuleiro (JPanel panel) {
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
		//~ scroller.setBounds ((int) INICIO_LOG.getX (), (int) INICIO_LOG.getY (), (int) TAM_LOG.getX (), (int) TAM_LOG.getY ());
		scroller.setPreferredSize (new Dimension ((int) TAM_LOG.getX (), (int) TAM_LOG.getY ()));
		
		panel.add (scroller, BorderLayout.CENTER);
	}
	/**
	 * 
	 */
	private void perguntaConexao () {
		String[] opcoes = { "Offline", "Servidor", "Cliente" };
		String escolhido = (String) JOptionPane.showInputDialog (null, "Tipo de jogo", "Selecione o tipo de jogo", JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);
		if (escolhido == "Servidor") {
			motor.conecta (ObServer.Lado.SERVIDOR);
			System.out.println ("Servidor mode!");
		}
		else if (escolhido == "Cliente") {
			motor.conecta (ObServer.Lado.CLIENTE);
			System.out.println ("Cliente mode!");
		}
		else
			System.out.println ("offline mode!");
	}
	/**
	 * monta o menu
	 */
	private void menu () {
		JMenuBar barra = new JMenuBar ();
		// MENU 'jogo'
		JMenu Jogo = new JMenu ("Jogo");
		Jogo.setMnemonic (KeyEvent.VK_J);
		
		if (!ObServer.estaEmRede ()) {
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
			
			// Item 'carregar jogo'
			JMenuItem Carregar = new JMenuItem ("Carregar jogo		^O");
			Carregar.setMnemonic (KeyEvent.VK_C);
			Carregar.setToolTipText ("Carrega um jogo salvo");
			Jogo.add (Carregar);
			//ctrlO Carrega jogo salvo
			Carregar.getInputMap (JComponent.WHEN_IN_FOCUSED_WINDOW).put (KeyStroke.getKeyStroke (KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK), "carregar");
			Carregar.getActionMap ().put ("carregar", new AbstractAction () {
				@Override
				public void actionPerformed (ActionEvent event) {
					carregarJogo ();
				}
			});

			Carregar.addActionListener (new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					carregarJogo ();
				}
			});

			// Item 'salvar jogo'
			JMenuItem Salvar = new JMenuItem ("Salvar jogo		^S");
			Salvar.setMnemonic (KeyEvent.VK_V);
			Salvar.setToolTipText ("Salva a partida atual, no estado atual");
			Jogo.add (Salvar);
			//ctrlO Carrega jogo salvo
			Salvar.getInputMap (JComponent.WHEN_IN_FOCUSED_WINDOW).put (KeyStroke.getKeyStroke (KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "salvar");
			Salvar.getActionMap ().put ("salvar", new AbstractAction () {
				@Override
				public void actionPerformed (ActionEvent event) {
					salvarJogo ();
				}
			});

			Salvar.addActionListener (new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					salvarJogo ();
				}
			});
			
			// Item 'autosave'
			JMenuItem Autosave = new JMenuItem ("Autosave");
			Autosave.setMnemonic (KeyEvent.VK_A);
			Autosave.setToolTipText ("Ativa ou desativa o autosave");
			Jogo.add (Autosave);
			//ctrlO Carrega jogo salvo
			Autosave.getActionMap ().put ("autosave", new AbstractAction () {
				@Override
				public void actionPerformed (ActionEvent event) {
					autosave ();
				}
			});

			Autosave.addActionListener (new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					autosave ();
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
		}
		
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
				
				j.getRelogio ().setNome (novo_nome);
				j.setNome (novo_nome);
				trocaJogador (j);	// escreve lá novo nome
			}
		});
		jogador.add (Renomear);
		
		if (!ObServer.estaEmRede ()) {
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
		}
		
		// povoa a barra de menu
		barra.add (Jogo);
		barra.add (jogador);

		setJMenuBar (barra);
	}
	
	/**
	 * Monta os relógios
	 */
	private void montaRelojs (JPanel panel) {
		Relogio R = motor.getRelogio ();
		R.setLabel (total);
		R.start ();
		panel.add (total, BorderLayout.CENTER);
		
		R = motor.getPartida ().getJ1 ().getRelogio ();
		R.setLabel (J1);
		R.setNome (motor.getPartida ().getJ1 ().toString ());
		R.start ();
		panel.add (J1, BorderLayout.LINE_START);
		
		R = motor.getPartida ().getJ2 ().getRelogio ();
		R.setLabel (J2);
		R.setNome (motor.getPartida ().getJ2 ().toString ());
		R.start ();
		panel.add (J2, BorderLayout.LINE_END);
		
		// relógio doutro jogador tá parado
		Xadrez.outroJogador ().getRelogio ().stop ();
	}
	public void updateRelojs (Partida partida) {
		Relogio R = motor.getRelogio ();
		R.setLabel (total);
		R.start ();
		
		R = partida.getJ1 ().getRelogio ();
		R.setLabel (J1);
		R.setNome (partida.getJ1 ().toString ());
		R.start ();
		
		R = partida.getJ2 ().getRelogio ();
		R.setLabel (J2);
		R.setNome (partida.getJ2 ().toString ());
		R.start ();
		
		// relógio doutro jogador tá parado
		Xadrez.outroJogador ().getRelogio ().stop ();
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
		
		// se está conectado e é cliente, manda esperar
		if (!motor.possoJogar ())
			quem_joga.setText ("O servidor começa o jogo, aguarde");
		else
			quem_joga.setText ("Jogador BRANCO, comece o jogo!");

		montaRelojs (rel);
	}

	/**
	 * Salva a partida atual
	 */
	private void salvarJogo () {
		try {
			// uma janelinha pra escolher o arquivo
			JFileChooser browser = new JFileChooser (".");
			FileNameExtensionFilter filtro = new FileNameExtensionFilter ("Jogos salvos", "sav");
			browser.addChoosableFileFilter (filtro);
			browser.setFileFilter (filtro);
			
			// Aceitou: salva lá!
			if (browser.showSaveDialog (this) == JFileChooser.APPROVE_OPTION) {
				File file = browser.getSelectedFile ();
				// extensão .sav (se tiver ou não colocado)
				if (!file.toString ().endsWith (".sav"))
					file = new File (file + ".sav");
				
				sessao.salvaPartida (file);
				System.out.println ("Partida salva!");
			}			
		}
		catch (IOException ex) {
			JOptionPane.showMessageDialog (this, "Tentativa de salvar partida: erro de IO\n" + ex.getCause ());
			ex.printStackTrace();
		}
	}
	/**
	 * Carrega a partida salva
	 */
	private void carregarJogo () {
		try {
			// uma janelinha pra escolher o arquivo
			JFileChooser browser = new JFileChooser (".");
			FileNameExtensionFilter filtro = new FileNameExtensionFilter ("Jogos salvos", "sav");
			browser.addChoosableFileFilter (filtro);
			browser.setFileFilter (filtro);
			
			// Aceitou: carrega lá!
			if (browser.showOpenDialog (this) == JFileChooser.APPROVE_OPTION) {
				File file = browser.getSelectedFile ();
				// extensão .sav (se tiver ou não colocado)
				if (!file.toString ().endsWith (".sav"))
					file = new File (file + ".sav");
				
				sessao.carregaPartida (file);
				motor.refreshSnap ();
				updateRelojs (sessao.getPartida ());
				System.out.println ("Partida carregada!");
			}
		}
		catch (FileNotFoundException | ObjectStreamException ex) {
			JOptionPane.showMessageDialog (this, "Tentativa de carregar partida: falha no stream do objeto\n" + ex.getCause ());
			System.out.println ("Tentativa de carregar partida: falha no stream do objeto\n" + ex.getCause ());
		}
		catch (IOException ex) {
			JOptionPane.showMessageDialog (this, "Tentativa de carregar partida: falha na abertura de arquivo\n" + ex.getCause ());
			System.out.println ("Tentativa de carregar partida: falha na abertura de arquivo\n" + ex.getCause ());
		}
		catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog (this, "Tentativa de carregar partida: classe não encontrada\n" + ex.getCause ());
			System.out.println ("Tentativa de carregar partida: classe não encontrada\n" + ex.getCause ());			
		}
	}
	/**
	 * Ativa ou desativa o autosave
	 */
	private void autosave () {
		int auto = JOptionPane.showConfirmDialog (this, "Deixar o autosave rodando?", "Autosave", JOptionPane.YES_NO_OPTION);
		
		if (auto == JOptionPane.YES_OPTION)
			sessao.setAutosave (true);
		else if (auto == JOptionPane.NO_OPTION)
			sessao.setAutosave (false);
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
		if (motor.possoJogar ())
			quem_joga.setText (J + ", " + str[rand.nextInt (16)]);
		else
			quem_joga.setText ("Esperando jogada pela rede, aguarde!");
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
	public void xeque (Jogador J, boolean refresh) {
		quem_joga.setForeground (Color.RED);
		if (motor.possoJogar ())	// é offline ou online e minha vez
			quem_joga.setText (J + " está em xeque, faça alguma coisa! =S");
		else
			quem_joga.setText (J + " está em xeque, vamos esperar a sequência!");
		
		// só põe '+' se não tiver refreshando =P
		if (!refresh) {
			log.addXeque ();
		}
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
	 * Pega do usuário o endereço do servidor
	 * a conectar. Endereço padrão é 'localhost'.
	 */
	public String getEndereco () {
		return JOptionPane.showInputDialog ("Endereço do servidor", "localhost");
	}
	/**
	 * Conexão falhou =/
	 * fecha o trem
	 */
	public void falhaComunicacao () {
		JOptionPane.showConfirmDialog (this, "A conexão falhou, e a culpa não é do programa =P\nAcabou o jogo...", "Conexão perdida",  JOptionPane.OK_CANCEL_OPTION);
		System.exit (0);
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
