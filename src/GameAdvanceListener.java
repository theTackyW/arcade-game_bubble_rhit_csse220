import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Class: GameAdvanceListener
 * @author Jackson Hajer, Siqi Wang, Hannah Woody
 * Purpose: This listener advances the game one tick.
 * 
 */
public class GameAdvanceListener implements ActionListener {

	private LevelComponent levelComp;

	public GameAdvanceListener(LevelComponent levelComp) {
		this.levelComp = levelComp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		advanceOneTick();
	}

	public void advanceOneTick() {
		this.levelComp.updateState();
		this.levelComp.drawScreen();
	}
}
