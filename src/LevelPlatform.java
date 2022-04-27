import java.awt.Color;
import java.awt.Graphics2D;
/**
 * Class: LevelPlatform
 * @author Jackson Hajer, Siqi Wang, Hannah Woody
 * Purpose: Simple class that allows for the PlayerCharacter
 * 			and Enemies to land on the stage.
 * 
 */
public class LevelPlatform extends GameObject {

	public LevelPlatform(int width, int height, Color c, int x, int y) {
		super(x, y, width, height);
	}

	@Override
	public void drawOn(Graphics2D g2) {
	}

	@Override
	public void onRemove() {
	}

}
