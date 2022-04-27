import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
/**
 * Class: GameObject
 * @author Jackson Hajer, Siqi Wang, Hannah Woody
 * Purpose: This abstract class houses code universal to all
 * 			objects on the game screen.
 * 
 */
public abstract class GameObject {
	double x, y;
	double yVelocity;
	double xVelocity;
	boolean shouldRemove;
	double width;
	double height;
	LevelComponent levelComp;

	public GameObject(LevelComponent levelComp, double x, double y, double dx, double dy, double width, double height) {
		this.x = x;
		this.y = y;
		this.xVelocity = dx;
		this.yVelocity = dy;
		this.levelComp = levelComp;
		this.width = width;
		this.height = height;
	}

	public GameObject(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public abstract void onRemove();

	public abstract void drawOn(Graphics2D g2);

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return this.width;
	}

	public double getHeight() {
		return this.height;
	}

	public void setXVelocity(double xVel) {
		xVelocity = xVel;
	}

	public void setYVelocity(double yVel) {
		yVelocity = yVel;
	}

	public double getXVelocity() {
		return xVelocity;
	}

	public double getYVelocity() {
		return yVelocity;
	}
	
	public boolean shouldRemove() {
		return this.shouldRemove;
	}

	public void markToRemove() {
		this.shouldRemove = true;
	}

	public Rectangle2D.Double getBoundingBox() {
		return new Rectangle2D.Double(this.x, this.y, getWidth(), getHeight());
	}

	public boolean overlaps(GameObject other) {
		return getBoundingBox().intersects(other.getBoundingBox());
	}

	public void shootBullet() {
	}

}
