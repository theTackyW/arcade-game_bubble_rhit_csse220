import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class: PlayerCharacterListener
 * @author Jackson Hajer, Siqi Wang, Hannah Woody 
 * Purpose: This listener handles the jumping,moving and bubble shooting of the
 * 			player via keystrokes left, right, up and space.
 * 
 */
public class PlayerCharacterListener implements KeyListener {
	private PlayerCharacter character;
	private LevelComponent comp;
	private Level level;

	public PlayerCharacterListener(LevelComponent comp) {
		this.comp = comp;
		this.level = comp.getCurrentLevel();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		this.level = comp.getCurrentLevel();
		if (character != comp.getPlayer())
			character = comp.getPlayer();
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			character.setXVelocity(-3);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			character.setXVelocity(3);
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (!character.getIsJumping()) {
				character.setYVelocity(-11.5);
				character.setIsJumping(true);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			level.addBubble(new Bubble(comp, character, character.getIsFacingLeft()));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			character.setXVelocity(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			character.setXVelocity(0);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
