import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * Class: LevelChangeListener
 * @author Jackson Hajer, Siqi Wang, Hannah Woody
 * Purpose: This listener handles the transition using the
 * 			u and d keys to go up and down the levels.
 * 
 */
public class LevelChangeListener implements KeyListener {
	private LevelComponent comp;

	public LevelChangeListener(LevelComponent comp) {
		this.comp = comp;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_U) {
			comp.levelSetUp(comp.getCurrLevelNumber() + 1);
			comp.repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			comp.levelSetUp(comp.getCurrLevelNumber() - 1);
			if (comp.getCurrLevelNumber() < 1)
				comp.setCurrLevel(1);
			comp.repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
