import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
/**
 * Class: Mover
 * @author Jackson Hajer, Siqi Wang, Hannah Woody
 * Purpose: This is a very basic enemy which just moves.
 * 
 */
public class Mover extends AbstractEnemy {

	public Mover(LevelComponent levelComp, double x, double y, double dx, double dy, double width, double height) {
		super(levelComp, x, y, dx, dy, width, height);
		color = Color.GREEN;
	}

	@Override
	public void drawOn(Graphics2D g) {
		g.setColor(color);
		Ellipse2D.Double enemy = new Ellipse2D.Double(x, y, width, height);
		g.fill(enemy);
	}
}
