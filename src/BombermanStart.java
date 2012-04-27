import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;




public class BombermanStart {

	public static void main(String[] args) {
		
		
		JFrame StartMenu = new JFrame("bomber");
		StartMenu.pack();
		StartMenu.setResizable(false);
		
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
		btnAufGehts.setFont(new Font("Fighting Spirit TBS", Font.PLAIN, 11));
		btnAufGehts.setBackground(Color.RED);
		
		JButton btnOptionen = new JButton("Optionen");
		btnOptionen.setFont(new Font("Fighting Spirit TBS", Font.PLAIN, 11));
		btnOptionen.setBackground(Color.RED);
		
		JButton btnHighscore = new JButton("Highscore");
		btnHighscore.setFont(new Font("Fighting Spirit TBS", Font.PLAIN, 11));
		btnHighscore.setBackground(Color.RED);
		
		JButton btnIchMussLos = new JButton("Ich muss los...");
		btnIchMussLos.setFont(new Font("Fighting Spirit TBS", Font.PLAIN, 11));
		btnIchMussLos.setBackground(Color.RED);
		
		JTextPane txtpnBomberman = new JTextPane();
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
