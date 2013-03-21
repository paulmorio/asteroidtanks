package game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

//this is the menu.
public class Menu extends BasicGameState {

	public String mouse = "No Input Yet!";

	public Menu(int State) {

	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.drawString("Is your body ready for this?", 50, 50);
		g.drawString(mouse, 100, 100);

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		mouse = "Mouse position x: " + xpos + " y: " + ypos;

	}

	public int getID() {
		return 0;
	}

}
