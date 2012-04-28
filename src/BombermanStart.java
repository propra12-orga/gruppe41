import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.awt.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;




public class BombermanStart {

	public static void main(String[] args) {
		
		
		final JFrame StartMenu = new JFrame("bomber");
		StartMenu.setResizable(false);
		StartMenu.setMinimumSize(new Dimension(397, 347));
		StartMenu.setSize(new Dimension(386, 294));
		StartMenu.setLocation(new Point(450, 250));
		StartMenu.setForeground(Color.RED);
		StartMenu.setIconImage(Toolkit.getDefaultToolkit().getImage(BombermanStart.class.getResource("/bilder/maenchen1.jpg")));
		StartMenu.setTitle("Bomberman 0.1");
		StartMenu.setBackground(Color.WHITE);
		StartMenu.pack();
		
		JPanel Hintegrund = new JPanel();
		Hintegrund.setBackground(Color.RED);
		GroupLayout groupLayout = new GroupLayout(StartMenu.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(Hintegrund, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(Hintegrund, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JButton btnAufGehts = new JButton("Auf gehts!");
		btnAufGehts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bomberman bomber= new bomberman();
				
				//men� soll unsichtbar werde, wenn ich das spiel starte und wieder erscheinen, wenn ich dieses schlie�e
				//m�sste �ber listener funktionieren, ist aber zu fr�h am morgen
				//StartMenu.setVisible(false);
				
			}
		});
		btnAufGehts.setFont(new Font("Fighting Spirit TBS", Font.PLAIN, 11));
		btnAufGehts.setBackground(Color.RED);
		
		JButton btnOptionen = new JButton("Optionen");
		btnOptionen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				
				// soll neuen JFrame �ffnen, der die Optionen enth�lt
				
				
				
			}
		});
		btnOptionen.setFont(new Font("Fighting Spirit TBS", Font.PLAIN, 11));
		btnOptionen.setBackground(Color.LIGHT_GRAY);
		
		JButton btnHighscore = new JButton("Highscore");
		btnHighscore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				// soll neuen JFrame �ffnen, der den Highscore enth�lt
				
				
				
				
			}
		});
		btnHighscore.setForeground(Color.WHITE);
		btnHighscore.setFont(new Font("Fighting Spirit TBS", Font.PLAIN, 11));
		btnHighscore.setBackground(Color.BLACK);
		
		JButton btnIchMussLos = new JButton("Ich muss weg...");
		btnIchMussLos.addActionListener(new ActionListener() {
			
			
			//schlie�t das programm zus�tzlich zu dem "X"
			
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnIchMussLos.setFont(new Font("Fighting Spirit TBS", Font.PLAIN, 11));
		btnIchMussLos.setBackground(Color.YELLOW);
		
		JTextPane txtpnBomberman = new JTextPane();
		txtpnBomberman.setEditable(false);
		txtpnBomberman.setBackground(Color.RED);
		txtpnBomberman.setFont(new Font("Fighting Spirit TBS", Font.PLAIN, 46));
		txtpnBomberman.setText("Bomberman");
		GroupLayout gl_Hintegrund = new GroupLayout(Hintegrund);
		gl_Hintegrund.setHorizontalGroup(
			gl_Hintegrund.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_Hintegrund.createSequentialGroup()
					.addContainerGap(79, Short.MAX_VALUE)
					.addGroup(gl_Hintegrund.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnAufGehts, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnHighscore, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnIchMussLos, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnOptionen, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
					.addGap(137))
				.addGroup(Alignment.LEADING, gl_Hintegrund.createSequentialGroup()
					.addGap(53)
					.addComponent(txtpnBomberman, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(60, Short.MAX_VALUE))
		);
		gl_Hintegrund.setVerticalGroup(
			gl_Hintegrund.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Hintegrund.createSequentialGroup()
					.addGap(31)
					.addComponent(txtpnBomberman, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnAufGehts)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnOptionen)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnHighscore)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnIchMussLos)
					.addContainerGap(68, Short.MAX_VALUE))
		);
		gl_Hintegrund.setAutoCreateGaps(true);
		gl_Hintegrund.setAutoCreateContainerGaps(true);
		Hintegrund.setLayout(gl_Hintegrund);
		StartMenu.getContentPane().setLayout(groupLayout);
		StartMenu.setVisible(true);
		StartMenu.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}});

	}

}
