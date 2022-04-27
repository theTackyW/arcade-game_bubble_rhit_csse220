import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
/**
 * Class:  Moveable
 * @author Jackson Hajer, Siqi Wang, Hannah Woody 
 * Purpose: This abstract class houses movement code for
 * 			all of enemies, players and fruit that obey
 * 			gravity.
 * 
 */
public abstract class Movable extends GameObject {

	boolean isFacingLeft;
	boolean isJumping;
	boolean collideSide;
	boolean collideTopBottom;
	boolean isEnemy = false;
	Color color;

	public Movable(LevelComponent levelComp, double x, double y, double dx, double dy, double width, double height) {
		super(levelComp, x, y, dx, dy, width, height);
	}

	public void update() {
		touchingEdge();
		touchingBottom();
		move(levelComp.getCurrentLevel().getPlatforms());
	}

	public void move(ArrayList<LevelPlatform> platforms) {
		y += yVelocity;
		yVelocity += 0.4;
		collideSide = false;
		collideTopBottom = false;
		for (LevelPlatform platform : platforms) {
			LevelPlatform next = platform;
			if (this.overlaps(next)) {
				collideTopBottom = true;
				y -= yVelocity;
				yVelocity = 0;
				isJumping = false;
			}
		}
		x += xVelocity;
		if (xVelocity < 0)
			isFacingLeft = true;
		else if (xVelocity > 0)
			isFacingLeft = false;
		for (LevelPlatform platform : platforms) {
			LevelPlatform next = platform;
			if (this.overlaps(next)) {
				collideSide = true;
				x -= xVelocity;
				if (isEnemy)
					xVelocity = -xVelocity;
			}
		}

	}

	public void touchingEdge() {
		collideSide = false;
		if (x + 40 >= this.levelComp.getWidth()) {
			x = this.levelComp.getWidth() - 40;
			if (isEnemy)
				xVelocity = -xVelocity;
		}
		if (x <= 0) {
			if (isEnemy)
				xVelocity = -xVelocity;
			x = 0;
		}
	}

	public void touchingBottom() {
		if (y >= this.levelComp.getHeight())
			y = 0;
	}

	public void setIsJumping(boolean b) {
		isJumping = b;
	}

	public boolean getIsJumping() {
		return isJumping;
	}

	public boolean getIsFacingLeft() {
		return isFacingLeft;
	}

	public Color getColor() {
		return color;
	}
}
