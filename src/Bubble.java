import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
/**
 * Class: Bubble
 * @author Jackson Hajer, Siqi Wang, Hannah Woody
 * Purpose: This class represents the bubbles that the player shoots.
 * 			It also handles the trapping and releasing of enemies
 * 			after a certain amount of time.
 * 
 */
public class Bubble extends GameObject {

	private double distanceTraveled = 0;
	private PlayerCharacter character;
	private boolean containsEnemy = false;
	private AbstractEnemy enemyInside = null;
	private int timeWithEnemy = 0;

	public Bubble(LevelComponent levelComp, PlayerCharacter character, boolean facingLeft) {
		super(levelComp, character.getX() + 5, character.getY() + 5, 3, -2, 30, 30);
		this.character = character;
		if (facingLeft) {
			xVelocity = xVelocity * -1;
		}
	}

	@Override
	public void onRemove() {
		if (containsEnemy && character.overlaps(this)) {
			levelComp.addScore(100);
		} else if (character.overlaps(this)){
			levelComp.addScore(10);
		}
	}

	@Override
	public void drawOn(Graphics2D g2) {
		if (containsEnemy) {
			drawWithEnemy(g2);
		} else {
			drawEmpty(g2);
		}
	}

	private void drawEmpty(Graphics2D g2) {
		g2.setColor(Color.BLUE);
		g2.fill(new Ellipse2D.Double(x, y, width, height));
	}

	private void drawWithEnemy(Graphics2D g2) {
		g2.setColor(Color.BLUE);
		g2.draw(new Ellipse2D.Double(x - 5, y - 5, width + 10, height + 10));
		g2.setColor(enemyInside.getColor());
		g2.fill(new Ellipse2D.Double(x - 2.5, y - 2.5, width + 5, height + 5));
	}

	public void update() {
		if (containsEnemy) {
			timeWithEnemy++;
			if (timeWithEnemy == 400) {
				this.markToRemove();
				Level level = levelComp.getCurrentLevel();
				if (enemyInside.getColor() == Color.CYAN)
					level.addEnemy(new Shooter(levelComp, x, y, Math.random() * .50 + .75, 0, 40, 40));
				else
					level.addEnemy(new Mover(levelComp, x, y, Math.random() * .50 + .75, 0, 40, 40));
			}
		}
		if ((distanceTraveled < 200 && xVelocity > 0) || (distanceTraveled > -200 && xVelocity < 0)) {
			x += xVelocity;
			distanceTraveled += xVelocity;
		} else {
			y += yVelocity;
			if (y <= 0) {
				yVelocity = 0;
				y = 0;
				if (!containsEnemy) {
					markToRemove();
				}
			}
		}
		if (containsEnemy) {
			if (x <= 0) {
				xVelocity = 0;
				x = 0;
			}
			if(x >= 600) {
				xVelocity = 0;
				x = 600;
			}
		}
	}

	public boolean shouldRemove() {
		return shouldRemove;
	}

	public void markToRemove() {
		this.shouldRemove = true;
	}

	public void trapEnemy(AbstractEnemy e) {
		this.containsEnemy = true;
		this.enemyInside = e;
	}

	public double getDistTraveled() {
		return distanceTraveled;
	}

	public boolean getContainsEnemy() {
		return containsEnemy;
	}
}
