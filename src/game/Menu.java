package game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

//this is the menu.
public class Menu extends BasicGameState {

	public Menu(int State) {

	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.fillOval(75,100,100,100);
		g.drawString("Are you ready?", 80, 70);

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = gc.getInput();
		int xpos = Mouse.getX();
		int ypos = Mouse.getX();
		if ((xpos > 75 && xpos < 175) && (ypos > 160 && ypos <260)){
			if (input.isMouseButtonDown(0)){
				sbg.enterState(1);
			}
		}
	}

	public int getID() {
		return 0;
	}

}
