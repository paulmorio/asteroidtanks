package game;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

// This is the main class that handles game flow, and contains the game loop (our main class). 
public class AstralTanks extends StateBasedGame {
	public static final int menu = 0;
	public static final int play = 1;
	public static final int hs = 2;
	
/*
	Image plane = null;
	RenderableObject land = null;
	float x = 400;
	float y = 300;
	float scale = 1f;
	int numberOfAsteroids = 10;
	Asteroid[] asts;
	Asteroid[] ast = new Asteroid[20];
*/
	
	public AstralTanks() { // constructor
		super("Asteroids and Astral Tanks");
		this.addState(new Menu(menu));
		this.addState(new Play(play));
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(menu).init(gc, this);
		this.getState(play).init(gc, this);
		this.enterState(menu);

	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app; 
		try {
		 app = new AppGameContainer(new AstralTanks());
		 app.setDisplayMode(800, 600, false); // leaving it false keeps the game as a window
		 app.start();
		}catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
