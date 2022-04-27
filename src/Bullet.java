import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
/**
 * Class: Bullet
 * @author Jackson Hajer, Siqi Wang, Hannah Woody
 * Purpose: This is the bullet shot by the Shooter enemy.
 * 
 */
public class Bullet extends GameObject {

	public Shooter monster;

	public final double BULLET_XVELOCITY = 3.0;
	public final double BULLET_YVELOCITY = 2.0;
	public final double BULLET_DIAMETER = 5.0;

	public Bullet(LevelComponent levelComp, AbstractEnemy monster, boolean facingLeft) {
		super(levelComp, monster.getX() + 5, monster.getY() + 5, 3, -2, 5, 5);
		this.monster = (Shooter) monster;
		if (facingLeft) {
			xVelocity = xVelocity * -1;
		}
	}

	@Override
	public void onRemove() {
	}

	@Override
	public void drawOn(Graphics2D g2) {
		g2.setColor(Color.ORANGE);
		g2.fill(new Ellipse2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
	}

	public void update() {
		if (x >= 0 && x <= 640) {
			x += xVelocity;
		} 
		else {
			markToRemove();
		}
	}
	
	public boolean shouldRemove() {
		return shouldRemove;
	}

	public void markToRemove() {
		this.shouldRemove = true;
	}

	
}
