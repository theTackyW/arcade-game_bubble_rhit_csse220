import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * Class: Fruit
 * @author Jackson Hajer, Siqi Wang, Hannah Woody 
 * Purpose: This gives player points when picked up. These
 * 			spawn when the player pops a Bubble.
 * 
 */
public class Fruit extends Movable {

	private double distTraveled = 0;

	public Fruit(LevelComponent levelComp, Bubble b) {
		super(levelComp, b.getX(), b.getY(), Math.random() * 4 - 2, Math.random() * 4 - 2, 40, 40);
	}

	public void onRemove() {
		levelComp.addScore(250);
	}

	public void move(ArrayList<LevelPlatform> platforms) {
		super.move(platforms);
		if (collideTopBottom) {
			xVelocity = 0;
		}
	}

	public void drawOn(Graphics2D g2) {
		g2.setColor(Color.PINK);
		g2.fill(new Ellipse2D.Double(x, y, width, height));
	}

	public boolean getKillable() {
		if (distTraveled > 100 || (xVelocity == 0 && yVelocity == 0))
			return true;
		return false;
	}
}
