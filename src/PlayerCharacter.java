import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Class: PlayerCharacter
 * 
 * @author Jackson Hajer, Siqi Wang, Hannah Woody 
 * Purpose: This class is the player and is controlled 
 * 			by the user and can move, jump and shoot
 *          bubbles.
 * 
 */
public class PlayerCharacter extends Movable {

	private boolean isJumping;
	private boolean isFacingLeft = false;
	private Color color;
	private boolean invunerable = true;
	private int timeTicks = 0;

	public PlayerCharacter(LevelComponent levelComp, double x, double y, double dx, double dy, double width,
			double height) {
		super(levelComp, x, y, dx, dy, width, height);
		this.color = Color.YELLOW;
		yVelocity = 2;

	}

	public boolean isInvunerable() {
		return this.invunerable;
	}

	@Override
	public void drawOn(Graphics2D g) {
		g.setColor(this.color);
		Ellipse2D.Double character = new Ellipse2D.Double(x, y, width, height);
		g.fill(character);
	}

	public void update() {
		spawn();
		super.update();
	}

	public void spawn() {
		if (this.invunerable) {
			this.timeTicks++;
			if (timeTicks % 10 == 0)
				this.color = Color.YELLOW;
			else
				this.color = Color.BLACK;
		}
		if (this.timeTicks >= 100) {
			this.invunerable = false;
			this.color = Color.YELLOW;
		}
	}

	public void hit() {
		this.levelComp.reduceLives();
		this.markToRemove();
	}

	@Override
	public void onRemove() {
	}
}
