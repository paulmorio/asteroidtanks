package game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

//this is the menu.
public class Menu extends BasicGameState {

	Image playNow;
	Image exitGame;

	public Menu(int State) {

	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		playNow = new Image("res/playNow.png");
		exitGame = new Image("res/exitGame.png");
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.drawString("Are you ready?", 390, 50);
		playNow.draw(100, 100);
		exitGame.draw(100, 200);

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		int xpos = Mouse.getX();
		int ypos = Mouse.getX();
		//playNow Button
		if ((xpos > 75 && xpos < 175) && (ypos > 160 && ypos < 260)) {
			if (Mouse.isButtonDown(0)) {
				sbg.enterState(1);
			}
		}
		//extitGame button
		if ((xpos>100 && xpos<311) && (ypos>109 && ypos<160)){
			if(Mouse.isButtonDown(0)){
				System.exit(0);
			}
		}
	}

	public int getID() {
		return 0;
	}

}
