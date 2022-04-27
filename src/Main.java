import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * The main class for your arcade game.
 * 
 * You can design your game any way you like, but make the game start by running
 * main here.
 * 
 * Also don't forget to write javadocs for your classes and methods.
 * 
 * @author Jackson Hajer, Siqi Wang, Hannah Woody
 *
 *
 */

public class Main {

	public static final int DELAY = 10;

	public static void main(String[] args) {
		Main m = new Main();
		JFrame frame = new JFrame();
		ImageIcon icon = new ImageIcon(Main.class.getResource("Bubble_Bobble_Crystal_1200x1200.png"));
		Object[] options = { "Start Game", "Quit" };

		// Either you start the game or quit
		int startOrNot = JOptionPane.showOptionDialog(frame, null, "Welcome to Epileptic Bubble Bobble!",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);

		// If pressed quit, this executes
		if (startOrNot == 1) {
			System.exit(0);
		}

		frame.setSize(658, 687);
		frame.setTitle("Epileptic Bubble Bobble");

		LevelComponent comp = new LevelComponent();
		frame.add(comp, BorderLayout.CENTER);

		frame.addKeyListener(new LevelChangeListener(comp));
		frame.addKeyListener(new PlayerCharacterListener(comp));
		
		Timer timer = new Timer(DELAY, new GameAdvanceListener(comp));

		timer.start();

		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	

}
