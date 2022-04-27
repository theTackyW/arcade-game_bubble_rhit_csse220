import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Class: Shooter
 * @author Jackson Hajer, Siqi Wang, Hannah Woody 
 * Purpose: This is an enemy that shoots bullets at
 * 			random intervals.
 * 
 */
public class Shooter extends AbstractEnemy {

	private int numTicks = 0;
	private int randomShootTime = (int) (Math.random() * 100) + 150;

	public Shooter(LevelComponent levelComp, double x, double y, double dx, double dy, double width, double height) {
		super(levelComp, x, y, dx, dy, width, height);
		color = Color.CYAN;
	}

	@Override
	public void drawOn(Graphics2D g) {
		for (Bullet b : bullets) {
			b.drawOn(g);
		}
		g.setColor(color);
		Ellipse2D.Double enemy = new Ellipse2D.Double(x, y, width, height);
		g.fill(enemy);
	}

	public void update() {
		super.update();
		shootBullet();
		updateBullets();
	}

	public void updateBullets() {
		for (Bullet b : bullets) {
			b.update();
		}
		List<Bullet> shouldRemove = new ArrayList<>();

		for (Bullet bullet : bullets) {
			if (bullet.shouldRemove()) {
				shouldRemove.add(bullet);
			}
		}

		for (Bullet bullet : shouldRemove) {
			bullets.remove(bullet);
			bullet.onRemove();
		}
	}

	public void shootBullet() {
		numTicks++;
		if (numTicks == randomShootTime) {
			Bullet bullet = new Bullet(this.levelComp, this, getIsFacingLeft());
			bullets.add(bullet);
			numTicks = 0;
			randomShootTime = (int) (Math.random() * 100) + 150;
		}
	}

}
