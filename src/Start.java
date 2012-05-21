import java.awt.Dimension;

import java.awt.EventQueue;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextPane;
import java.awt.Font;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.io.*;
import java.awt.Color;


public class Start extends JFrame {

	Map map0;														// das hier weg machen wenn wir das nicht so machen wollen:) ist halt gar nicht ausgereift.
	Bomberman bomber=new Bomberman(map0);
	
	/*Dieses Menu enthält viele Optionen, die zwar Änderungen annehmen und diese speichert, damit sie, beim Start des Spieles
	 *  als Paramter weitergegeben werden können, haben aber magels Implemetierung eines geeigneten Konstruktors noch keine
	 * Auswirkungen auf die tatsächliche Start des Spieles. 	
	 */
	
	
	private JPanel contentPane;
    public String schwierigkeit="einfach";
    public int anzahlbots=0;
    int mapdesign=0;
    // null für normal; eins für zusatz
    int bombermandesign=0;
    // null für normal; eins für zusatz
    
    /*man kann die folgenden daten auch als String-Array einlesen bzw. speicher momentan ist das so aber noch übersichtlicher
     * später für vergleich und einordnung in die bestenliste natürlich umformung 
     * von string in int durch "Integer.parseInt(ersterpunkt)" etc
     */
    
    String erster;
    String ersterpunkte;
    String zweiter;
    String zweiterpunkte;
    String dritter;
    String dritterpunkte;
    BufferedReader br = null;
    String Spielername;
	
    //Steuerung gleich Null bedeutet WASD-Steuerung, gleich Eins bedeutet Pfeil-Steuerung
     
    public int steuerung=0;
    

	public static void main(String[] args) {
				
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start frame = new Start();
					frame.setResizable(false);
					frame.setLocation(new Point(500, 250));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});};
		
	
	public Start() {
		setTitle("Bomberman 0.2");
		setSize(new Dimension(450, 400));
		
		// folgende methode liest die daten aus der textdatei ein und speichert diese in den variablen
		
		 try {
		        br = new BufferedReader(new FileReader(new File("besten.txt")));
		        String line = null;
		        while((line = br.readLine()) != null) {               
		            String []parts= line.split(";");
		            erster=parts[0];
		            ersterpunkte=parts[1];
		            zweiter=parts[2];
		            zweiterpunkte=parts[3];
		            dritter=parts[4];
		            dritterpunkte=parts[5];
		            Spielername=parts[6];
		           
		        }
		    } catch(FileNotFoundException f) {
		        f.printStackTrace();
		    } catch(IOException f) {
		        f.printStackTrace();
		    } finally {
		        if(br != null) {
		            try {
		                br.close();
		            } catch(IOException f) {
		                f.printStackTrace();
		            }
		        }
		    }
		 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 364);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		// cardlayout gewählt, damit einfach mehrere panels in einem frame "verarbeitet" werden können 
		
		final CardLayout c=new CardLayout(0,0);
		contentPane.setLayout(c);
		
		final JPanel Start = new JPanel();
		Start.setSize(new Dimension(450, 400));
		contentPane.add(Start, "1");
		
		
		JButton NeuesSpiel = new JButton("Neues Spiel");
		NeuesSpiel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/* dieses "aufrufschema" wird im folgenden code öfters erscheinen.
				* jedes panel hat einen namen hier z.b. " card="7"". dieser name ist vorher bei der erstellung des panels
				* schon gewählt. mit dem aufruf wird dann das jeweilige adressiert panel geöffnet, da immer nur ein panel im 
				* vordergrund sein kann. 
				*/
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				String card = "7";
				cl.show(contentPane, card);
				
				
				
			}
		});
		
		JButton Optionen = new JButton("Optionen");
			
		JButton Highscore = new JButton("Highscore");
		Highscore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			      
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				String card = "3";
				cl.show(contentPane, card);
				
				
				//nur eine testfunktion
				//System.out.println(erster+ersterpunkte);
				
			}
		});
		
		JButton Beenden= new JButton("Beenden");
		Beenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				System.exit(0);
			}
		});
		
		final JTextPane Titel = new JTextPane();
		Titel.setEditable(false);
		Titel.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		Titel.setText("Bomberman");
		Titel.setBackground(Start.getBackground());
		GroupLayout gl_Start = new GroupLayout(Start);
		gl_Start.setHorizontalGroup(
			gl_Start.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Start.createSequentialGroup()
					.addGroup(gl_Start.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_Start.createSequentialGroup()
							.addGap(159)
							.addGroup(gl_Start.createParallelGroup(Alignment.LEADING, false)
								.addComponent(NeuesSpiel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(Optionen, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(Highscore, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(Beenden, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(gl_Start.createSequentialGroup()
							.addGap(151)
							.addComponent(Titel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(152, Short.MAX_VALUE))
		);
		gl_Start.setVerticalGroup(
			gl_Start.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_Start.createSequentialGroup()
					.addContainerGap(77, Short.MAX_VALUE)
					.addComponent(Titel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(NeuesSpiel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(Optionen)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(Highscore)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(Beenden)
					.addGap(78))
		);
		Start.setLayout(gl_Start);
		
		final JPanel Option = new JPanel();
		Option.setSize(new Dimension(450, 400));
		contentPane.add(Option, "2");
		
		final JButton schliessen2 = new JButton("");
		schliessen2.setForeground(Color.BLACK);
		schliessen2.setBackground(Color.WHITE);
		schliessen2.setLocation(12, 275);
		schliessen2.setSize(55, 43);
		schliessen2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				String card = "1";
				cl.show(contentPane, card);
			}
		});
		schliessen2.setIcon(new ImageIcon(Start.class.getResource("/bilder/zurueck.jpg")));
		
		JButton Steuerung = new JButton("Steuerung");
		Steuerung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				String card = "5";
				cl.show(contentPane, card);
			}
		});
		
		JButton Grafik = new JButton("Grafik");
		Grafik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				String card = "4";
				cl.show(contentPane, card);
			}
		});
		
		JButton Credits = new JButton("Credits");
		Credits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				String card = "6";
				cl.show(contentPane, card);
			}
		});
		GroupLayout gl_Option = new GroupLayout(Option);
		gl_Option.setHorizontalGroup(
			gl_Option.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Option.createSequentialGroup()
					.addContainerGap(307, Short.MAX_VALUE)
					.addComponent(Credits)
					.addGap(40))
				.addGroup(gl_Option.createSequentialGroup()
					.addGap(149)
					.addGroup(gl_Option.createParallelGroup(Alignment.LEADING, false)
						.addComponent(Grafik, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(Steuerung, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(182, Short.MAX_VALUE))
				.addGroup(gl_Option.createSequentialGroup()
					.addContainerGap()
					.addComponent(schliessen2, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(353, Short.MAX_VALUE))
		);
		gl_Option.setVerticalGroup(
			gl_Option.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_Option.createSequentialGroup()
					.addGap(98)
					.addComponent(Steuerung)
					.addGap(18)
					.addComponent(Grafik)
					.addPreferredGap(ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
					.addComponent(Credits)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(schliessen2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		Option.setLayout(gl_Option);
		
		final JPanel High = new JPanel();
		High.setSize(new Dimension(450, 400));
		contentPane.add(High, "3");
		
		JLabel Bestenliste = new JLabel("Bestenliste:");
		
		JLabel Erster = new JLabel("Erster:");
		
		JLabel Zweiter = new JLabel("Zweiter:");
		
		JLabel Dritter = new JLabel("Dritter:");
		
		JLabel Name = new JLabel("Name");
		
		JLabel Zeit = new JLabel("Zeit");
		
		final JButton schliessen = new JButton("");
		schliessen.setForeground(Color.BLACK);
		schliessen.setBackground(Color.WHITE);
		schliessen.setLocation(12, 275);
		schliessen.setSize(55, 43);
		schliessen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				String card = "1";
				cl.show(contentPane, card);
			}
		});
		schliessen.setIcon(new ImageIcon(Start.class.getResource("/bilder/zurueck.jpg")));
		
		JLabel ersterTextFeld = new JLabel(erster);
		JLabel ersterZeit = new JLabel(ersterpunkte);
		JLabel zweiterTextFeld = new JLabel(zweiter);
		JLabel zweiterZeit = new JLabel(zweiterpunkte);
		JLabel dritterTextFeld = new JLabel(dritter);
		JLabel dritterZeit = new JLabel(dritterpunkte);
		
		GroupLayout gl_High = new GroupLayout(High);
		gl_High.setHorizontalGroup(
			gl_High.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_High.createSequentialGroup()
					.addGap(72)
					.addGroup(gl_High.createParallelGroup(Alignment.TRAILING)
						.addComponent(Bestenliste, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_High.createParallelGroup(Alignment.LEADING)
							.addComponent(Zweiter)
							.addComponent(Erster)
							.addComponent(Dritter)))
					.addPreferredGap(ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
					.addGroup(gl_High.createParallelGroup(Alignment.LEADING)
						.addComponent(ersterTextFeld)
						.addComponent(Name)
						.addComponent(zweiterTextFeld)
						.addComponent(dritterTextFeld))
					.addGap(31)
					.addGroup(gl_High.createParallelGroup(Alignment.LEADING)
						.addComponent(zweiterZeit)
						.addComponent(ersterZeit)
						.addComponent(Zeit)
						.addComponent(dritterZeit))
					.addGap(48))
				.addGroup(gl_High.createSequentialGroup()
					.addContainerGap()
					.addComponent(schliessen, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(355, Short.MAX_VALUE))
		);
		gl_High.setVerticalGroup(
			gl_High.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_High.createSequentialGroup()
					.addGroup(gl_High.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_High.createSequentialGroup()
							.addGap(47)
							.addComponent(Bestenliste, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
							.addGap(26)
							.addComponent(Erster)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Zweiter)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Dritter))
						.addGroup(gl_High.createSequentialGroup()
							.addGap(83)
							.addGroup(gl_High.createParallelGroup(Alignment.BASELINE)
								.addComponent(Name)
								.addComponent(Zeit))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_High.createParallelGroup(Alignment.BASELINE)
								.addComponent(ersterTextFeld)
								.addComponent(ersterZeit))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_High.createParallelGroup(Alignment.BASELINE)
								.addComponent(zweiterTextFeld)
								.addComponent(zweiterZeit))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_High.createParallelGroup(Alignment.BASELINE)
								.addComponent(dritterTextFeld)
								.addComponent(dritterZeit))))
					.addPreferredGap(ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
					.addComponent(schliessen, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		High.setLayout(gl_High);
		
		JPanel GrafikPanel = new JPanel();
		GrafikPanel.setSize(new Dimension(450, 400));
		contentPane.add(GrafikPanel, "4");
		
		final JRadioButton SFNormal = new JRadioButton("Normal");
		SFNormal.setSelected(true);
		SFNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bombermandesign=0;
			}
		});
		
		
		final JRadioButton SFZusatz = new JRadioButton("Zusatz");
		SFZusatz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				bombermandesign=1;
			}
		});
		
		/* fasse die JRadioButton zu gruppen zusammen damit jeweils nur eins der jeweiligen wahlmethoden
		 * ausgewählt werden kann.
		 */
		ButtonGroup SFGruppe= new ButtonGroup();
		SFGruppe.add(SFNormal); 
		SFGruppe.add(SFZusatz);
		

		final JRadioButton KLNormal = new JRadioButton("Normal");
		KLNormal.setSelected(true);
		KLNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapdesign=0;
			}
		});
	
		final JRadioButton KLZusatz = new JRadioButton("Zusatz");
		KLZusatz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mapdesign=1;
				
			}
		});
		
		/* fasse die JRadioButton zu gruppen zusammen damit jeweils nur eins der jeweiligen wahlmethoden
		 * ausgewählt werden kann.
		 */
		ButtonGroup KLGruppe= new ButtonGroup();
		KLGruppe.add(KLNormal); 
		KLGruppe.add(KLZusatz);
		
		
		
		JButton schliessen1 = new JButton("");
		schliessen1.setForeground(Color.BLACK);
		schliessen1.setBackground(Color.WHITE);
		schliessen1.setLocation(12, 275);
		schliessen1.setSize(55, 43);
		schliessen1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				String card = "2";
				cl.show(contentPane, card);
			}
		});
		schliessen1.setIcon(new ImageIcon(Start.class.getResource("/bilder/zurueck.jpg")));
		
		
		JLabel Spielfigur = new JLabel("Spielfigur");
		
		JLabel lblKartenlayout = new JLabel("Kartenlayout");
		GroupLayout gl_GrafikPanel = new GroupLayout(GrafikPanel);
		gl_GrafikPanel.setHorizontalGroup(
			gl_GrafikPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_GrafikPanel.createSequentialGroup()
					.addContainerGap(73, Short.MAX_VALUE)
					.addGroup(gl_GrafikPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(SFNormal)
						.addComponent(KLNormal))
					.addPreferredGap(ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
					.addGroup(gl_GrafikPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(KLZusatz)
						.addComponent(SFZusatz))
					.addGap(76))
				.addGroup(gl_GrafikPanel.createSequentialGroup()
					.addContainerGap(174, Short.MAX_VALUE)
					.addGroup(gl_GrafikPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblKartenlayout)
						.addComponent(Spielfigur))
					.addGap(178))
				.addGroup(gl_GrafikPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(schliessen1, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(353, Short.MAX_VALUE))
		);
		gl_GrafikPanel.setVerticalGroup(
			gl_GrafikPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_GrafikPanel.createSequentialGroup()
					.addContainerGap(59, Short.MAX_VALUE)
					.addComponent(Spielfigur)
					.addGap(18)
					.addGroup(gl_GrafikPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(SFNormal)
						.addComponent(SFZusatz))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblKartenlayout)
					.addGap(18)
					.addGroup(gl_GrafikPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(KLNormal)
						.addComponent(KLZusatz))
					.addGap(96)
					.addComponent(schliessen1, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		GrafikPanel.setLayout(gl_GrafikPanel);
		
		JPanel SteuerungPanel = new JPanel();
		SteuerungPanel.setSize(new Dimension(450, 400));
		contentPane.add(SteuerungPanel, "5");
		
		JButton schliessen3 = new JButton("");
		schliessen3.setForeground(Color.BLACK);
		schliessen3.setBackground(Color.WHITE);
		schliessen3.setLocation(12, 275);
		schliessen3.setSize(55, 43);
		schliessen3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				String card = "2";
				cl.show(contentPane, card);
			}
		});
		schliessen3.setIcon(new ImageIcon(Start.class.getResource("/bilder/zurueck.jpg")));
		
		JButton WASD_Steuerung = new JButton("");
		WASD_Steuerung.setIcon(new ImageIcon(Start.class.getResource("/bilder/wasd1.jpg")));
		WASD_Steuerung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				steuerung=0;
			}
		});
		
		JButton Pfeil_Steuerung = new JButton("");
		Pfeil_Steuerung.setIcon(new ImageIcon(Start.class.getResource("/bilder/steuerpfeil1.jpg")));
		Pfeil_Steuerung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				steuerung=1;
			}
		});
		GroupLayout gl_SteuerungPanel = new GroupLayout(SteuerungPanel);
		gl_SteuerungPanel.setHorizontalGroup(
			gl_SteuerungPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_SteuerungPanel.createSequentialGroup()
					.addGroup(gl_SteuerungPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_SteuerungPanel.createSequentialGroup()
							.addGap(69)
							.addComponent(WASD_Steuerung, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addGap(40)
							.addComponent(Pfeil_Steuerung, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_SteuerungPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(schliessen3, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(65, Short.MAX_VALUE))
		);
		gl_SteuerungPanel.setVerticalGroup(
			gl_SteuerungPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_SteuerungPanel.createSequentialGroup()
					.addGap(113)
					.addGroup(gl_SteuerungPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(Pfeil_Steuerung, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
						.addComponent(WASD_Steuerung, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
					.addComponent(schliessen3, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		SteuerungPanel.setLayout(gl_SteuerungPanel);
		
		JPanel CreditsPanel = new JPanel();
		CreditsPanel.setSize(new Dimension(450, 400));
		contentPane.add(CreditsPanel, "6");
		
		JButton schliessen4 = new JButton("");
		schliessen4.setForeground(Color.BLACK);
		schliessen4.setBackground(Color.WHITE);
		schliessen4.setLocation(12, 275);
		schliessen4.setSize(55, 43);
		schliessen4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				String card = "2";
				cl.show(contentPane, card);
			}
		});
		schliessen4.setIcon(new ImageIcon(Start.class.getResource("/bilder/zurueck.jpg")));
		
		JTextArea txtrDiesIstDas = new JTextArea();
		txtrDiesIstDas.setBackground(CreditsPanel.getBackground());
		txtrDiesIstDas.setText("Dies ist das Produkt von:\r\n\r\nVenus\r\nArian\r\nPhilipp\r\nTimo\r\nKevin\r\n");
		GroupLayout gl_CreditsPanel = new GroupLayout(CreditsPanel);
		gl_CreditsPanel.setHorizontalGroup(
			gl_CreditsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_CreditsPanel.createSequentialGroup()
					.addContainerGap(101, Short.MAX_VALUE)
					.addComponent(txtrDiesIstDas, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
					.addGap(98))
				.addGroup(gl_CreditsPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(schliessen4, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(353, Short.MAX_VALUE))
		);
		gl_CreditsPanel.setVerticalGroup(
			gl_CreditsPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_CreditsPanel.createSequentialGroup()
					.addContainerGap(71, Short.MAX_VALUE)
					.addComponent(txtrDiesIstDas, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addGap(68)
					.addComponent(schliessen4, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		CreditsPanel.setLayout(gl_CreditsPanel);
		
		JPanel Auswahl = new JPanel();
		Auswahl.setSize(new Dimension(450, 400));
		contentPane.add(Auswahl, "7");
		
		JButton schliessen5 = new JButton("");
		schliessen5.setForeground(Color.BLACK);
		schliessen5.setBackground(Color.WHITE);
		schliessen5.setLocation(12, 275);
		schliessen5.setSize(55, 43);
		schliessen5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				String card = "1";
				cl.show(contentPane, card);
			}
		});
		schliessen5.setIcon(new ImageIcon(Start.class.getResource("/bilder/zurueck.jpg")));
		
		JButton Einzel = new JButton("SingelPlayer");
		Einzel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bomber.spiele(10);											// hier starten es beim Singelplayer-Modus 
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				String card = "8";
				cl.show(contentPane, card);
			}
		});
		
		JButton Multiplayer = new JButton("Multiplayer");
		Multiplayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				String card = "9";
				cl.show(contentPane, card);
			}
		});
		
		final JTextField SpielerNameFeld = new JTextField(Spielername);
		SpielerNameFeld.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Spielername=SpielerNameFeld.getText();
				
				
				
				/* zum speicher des spielernames auch wenn das spiel geschlossen ist
				 * kann "recycelt" werden für die speicherung der highscoreliste
				 */
				FileWriter writer;
				  File file;
				  
				  
				    // File anlegen
				     file = new File("besten.txt");
				     try {
				      
				       
				      
				       // überschreibt die alte datei
				       writer = new FileWriter(file);
				       
				       // sammelt strings die in datei geschrieben werden sollen
				       writer.write(erster+";"+ersterpunkte+";");
				       writer.write(zweiter+";"+zweiterpunkte+";");
				       writer.write(dritter+";"+dritterpunkte+";");
				       writer.write(Spielername);
				       
				       // leert den stream
				     
				       writer.flush();
				       
				       // schließt den Stream
				       writer.close();
				    } catch (IOException e) {
				      e.printStackTrace();
				    }
			}
		});
		SpielerNameFeld.setColumns(10);
		
		JLabel SpielerNameLabel = new JLabel("Spielername:");
		
		JLabel lblNewLabel = new JLabel("(Neueingabe bitte mit Enter best\u00E4tigen)");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
		GroupLayout gl_Auswahl = new GroupLayout(Auswahl);
		gl_Auswahl.setHorizontalGroup(
			gl_Auswahl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Auswahl.createSequentialGroup()
					.addGap(87)
					.addComponent(Einzel)
					.addGap(63)
					.addComponent(Multiplayer, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
					.addGap(82))
				.addGroup(gl_Auswahl.createSequentialGroup()
					.addGap(176)
					.addComponent(SpielerNameLabel)
					.addContainerGap(176, Short.MAX_VALUE))
				.addGroup(gl_Auswahl.createSequentialGroup()
					.addGap(164)
					.addComponent(SpielerNameFeld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(164, Short.MAX_VALUE))
				.addGroup(gl_Auswahl.createSequentialGroup()
					.addContainerGap()
					.addComponent(schliessen5, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(353, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_Auswahl.createSequentialGroup()
					.addContainerGap(138, Short.MAX_VALUE)
					.addComponent(lblNewLabel)
					.addGap(114))
		);
		gl_Auswahl.setVerticalGroup(
			gl_Auswahl.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_Auswahl.createSequentialGroup()
					.addGap(91)
					.addComponent(SpielerNameLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(SpielerNameFeld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(lblNewLabel)
					.addGap(18)
					.addGroup(gl_Auswahl.createParallelGroup(Alignment.BASELINE)
						.addComponent(Einzel)
						.addComponent(Multiplayer))
					.addGap(83)
					.addComponent(schliessen5, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		Auswahl.setLayout(gl_Auswahl);
		
		JPanel SingelPanel = new JPanel();
		SingelPanel.setSize(new Dimension(450, 400));
		contentPane.add(SingelPanel, "8");
		
		JButton schliessen6 = new JButton("");
		schliessen6.setForeground(Color.BLACK);
		schliessen6.setBackground(Color.WHITE);
		schliessen6.setLocation(12, 275);
		schliessen6.setSize(55, 43);
		schliessen6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				String card = "7";
				cl.show(contentPane, card);
			}
		});
		schliessen6.setIcon(new ImageIcon(Start.class.getResource("/bilder/zurueck.jpg")));
		
		JButton SpielStart = new JButton("Start");
		SpielStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//versuch bomberman zu starten, flackert aber nur und gibt dann endbild aus
				//bomberman a=new bomberman();
				//a.main(null);				
				
				//test
				
				System.out.println(" Mapedesign: "+mapdesign+"\n "+"Bombermandesign :"+bombermandesign+"\n "+ "Schwierigkeit: "+schwierigkeit+"\n "+"Anzahl der Bots: "+anzahlbots+"\n "+"Steuerung: "+steuerung+"\n "+"Spielername: "+Spielername);
				
				
				
				
				
				
			}
		});
		
		final JComboBox Schwierigkeit = new JComboBox();
		Schwierigkeit.setModel(new DefaultComboBoxModel(new String[] {"Einfach", "Normal", "Schwer"}));
		Schwierigkeit.setMaximumRowCount(3);
		Schwierigkeit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Schwierigkeit.getSelectedIndex()==0){
					schwierigkeit="einfach";
				}
				if(Schwierigkeit.getSelectedIndex()==1){
					schwierigkeit="normal";
				}
				if(Schwierigkeit.getSelectedIndex()==2){
					schwierigkeit="schwer";
				}
					
			}
		});
		
		final JComboBox Anzahl = new JComboBox();
		Anzahl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Anzahl.getSelectedIndex()==0){
					anzahlbots=0;
				}
				if(Anzahl.getSelectedIndex()==1){
					anzahlbots=1;
				}
				if(Anzahl.getSelectedIndex()==2){
					anzahlbots=2;
				}
				if(Anzahl.getSelectedIndex()==3){
					anzahlbots=3;
				}
				
					
			}
		});
		Anzahl.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3"}));
		Anzahl.setMaximumRowCount(4);
		
		JLabel SchwierigkeitDrop = new JLabel("Schwierigkeit:");
		
		JLabel Gegneranzahl = new JLabel("Gegneranzahl:");
		GroupLayout gl_SingelPanel = new GroupLayout(SingelPanel);
		gl_SingelPanel.setHorizontalGroup(
			gl_SingelPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_SingelPanel.createSequentialGroup()
					.addGroup(gl_SingelPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_SingelPanel.createSequentialGroup()
							.addGap(95)
							.addGroup(gl_SingelPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(Schwierigkeit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(SchwierigkeitDrop))
							.addGap(76)
							.addGroup(gl_SingelPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(Gegneranzahl)
								.addComponent(Anzahl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_SingelPanel.createSequentialGroup()
							.addGap(173)
							.addComponent(SpielStart))
						.addGroup(gl_SingelPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(schliessen6, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(107, Short.MAX_VALUE))
		);
		gl_SingelPanel.setVerticalGroup(
			gl_SingelPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_SingelPanel.createSequentialGroup()
					.addGap(87)
					.addComponent(SpielStart)
					.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
					.addGroup(gl_SingelPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(SchwierigkeitDrop)
						.addComponent(Gegneranzahl))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_SingelPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(Schwierigkeit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(Anzahl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(88)
					.addComponent(schliessen6, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		SingelPanel.setLayout(gl_SingelPanel);
		
		JPanel MultiPanel = new JPanel();
		MultiPanel.setSize(new Dimension(450, 400));
		contentPane.add(MultiPanel, "9");
		
		JButton schliessen7 = new JButton("");
		schliessen7.setForeground(Color.BLACK);
		schliessen7.setBackground(Color.WHITE);
		schliessen7.setLocation(12, 275);
		schliessen7.setSize(55, 43);
		schliessen7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				String card = "7";
				cl.show(contentPane, card);
				
				
			}
		});
		schliessen7.setIcon(new ImageIcon(Start.class.getResource("/bilder/zurueck.jpg")));
		
		JLabel lblDiesesFeaturIst = new JLabel("Dieses Featur ist noch nicht implementiert.");
		GroupLayout gl_MultiPanel = new GroupLayout(MultiPanel);
		gl_MultiPanel.setHorizontalGroup(
			gl_MultiPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_MultiPanel.createSequentialGroup()
					.addGroup(gl_MultiPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_MultiPanel.createSequentialGroup()
							.addGap(101)
							.addComponent(lblDiesesFeaturIst))
						.addGroup(gl_MultiPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(schliessen7, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(110, Short.MAX_VALUE))
		);
		gl_MultiPanel.setVerticalGroup(
			gl_MultiPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_MultiPanel.createSequentialGroup()
					.addContainerGap(137, Short.MAX_VALUE)
					.addComponent(lblDiesesFeaturIst)
					.addGap(121)
					.addComponent(schliessen7, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		MultiPanel.setLayout(gl_MultiPanel);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{Start, NeuesSpiel, Optionen, Highscore, Beenden, Titel}));
		Optionen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CardLayout cl = (CardLayout) (contentPane.getLayout());
				String card = "2";
				cl.show(contentPane, card);
				
				
			}
		});
		
	}
}
