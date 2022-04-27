import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Class: AbstractEnemy
 * @author Jackson Hajer, Siqi Wang, Hannah Woody 
 * Purpose: This is an abstract class that houses code universal to
 * 			all of the enemies in the game, like the random jump intervals.
 * 
 */
public abstract class AbstractEnemy extends Movable {

	private int numTicks = 0;
	private int randomJumpTime = (int) (Math.random() * 25) + 25;
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	public AbstractEnemy(LevelComponent levelComp, double x, double y, double dx, double dy, double width,
			double height) {
		super(levelComp, x, y, dx, dy, width, height);
		isEnemy = true;
	}

	public void move(ArrayList<LevelPlatform> platforms) {
		super.move(platforms);
		randomJump();
	}

	public void randomJump() {
		if (collideTopBottom == true) {
			numTicks++;
			if (numTicks == randomJumpTime) {
				yVelocity = -11.5;
				numTicks = 0;
				randomJumpTime = (int) (Math.random() * 25) + 25;
			}
		}
	}

	public abstract void drawOn(Graphics2D g);

	@Override
	public void onRemove() {
	}

	public ArrayList<Bullet> getBulletList() {
		return this.bullets;
	}
}
