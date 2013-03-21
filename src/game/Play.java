package game;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Play extends BasicGameState {

	Image plane = null;
	RenderableObject land = null;
	float x = 400;
	float y = 300;
	float scale = 1f;
	int numberOfAsteroids = 10;
	Asteroid[] asts;
	Asteroid[] ast = new Asteroid[20];
	
	boolean quit = false;

	public Play(int State) {

	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

		plane = new Image("res/Sherman Tank Sprite.png");
		land = new RenderableObject("res/messier81_800x600.jpg");
		land.position = new Vector2f(2, 5);

		for (int i = 0; i < ast.length; i++) {
			ast[i] = new Asteroid();
		}
		asts = new Asteroid[numberOfAsteroids];
		for (int i = 0; i < numberOfAsteroids; i++) {
			asts[i] = new Asteroid();
		}

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		for(RenderableObject ro : RenderableObject.rendObjects){
			ro.Draw();
		}
		
		// gameMusic.loop();
		for (int i = 0; i < 10; i++)
			asts[i].Draw();
		plane.draw(x, y, scale);
		
		//for when the player presses escape
		if (quit == true){
			g.drawString("Resume (R)", 390,100);
			g.drawString("Main Menu (M)", 390, 150);
			g.drawString("Quit Game (Q)", 390, 200);
			if (quit == false){
				g.clear();
			}
		}

	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		Input input = gc.getInput();
		double del = (double) delta / 1000f;
		if (input.isKeyDown(Input.KEY_A)) {
			plane.rotate(-0.2f * delta);
		}

		if (input.isKeyDown(Input.KEY_D)) {
			plane.rotate(0.2f * delta);
		}

		if (input.isKeyDown(Input.KEY_W)) {
			float hip = 0.4f * delta;

			float rotation = plane.getRotation();

			x += hip * Math.sin(Math.toRadians(rotation));
			y -= hip * Math.cos(Math.toRadians(rotation));
		}

		if (input.isKeyDown(Input.KEY_S)) {
			float hip = 0.4f * delta;

			float rotation = plane.getRotation();

			x -= hip * Math.sin(Math.toRadians(rotation));
			y += hip * Math.cos(Math.toRadians(rotation));
		}

		if (input.isKeyDown(Input.KEY_2)) {
			scale += (scale >= 5.0f) ? 0 : 0.1f;
			plane.setCenterOfRotation(plane.getWidth() / 2.0f * scale,
					plane.getHeight() / 2.0f * scale);
		}
		if (input.isKeyDown(Input.KEY_1)) {
			scale -= (scale <= 1.0f) ? 0 : 0.1f;
			plane.setCenterOfRotation(plane.getWidth() / 2.0f * scale,
					plane.getHeight() / 2.0f * scale);
		}
		
		//escape to open ingame menu
		if(input.isKeyDown(Input.KEY_ESCAPE)){
			quit = false;
		}
		
		//when the player hits escape
		if (quit == true) {
			if (input.isKeyDown(Input.KEY_R)){
				quit = false;
			}
			
			if (input.isKeyDown(Input.KEY_M)){
				sbg.enterState(0);
				try{
					Thread.sleep(250);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			}
			
			if (input.isKeyDown(Input.KEY_Q)){
				System.exit(0);
			}
			
		}

		// as game runs, moves asteroid across screen
		// change += 1 to something to do with dy, dx
		for (int i = 0; i < ast.length; i++) {
			ast[i].x += 1;
			ast[i].y += 1;
		}
		for (int i = 0; i < 10; i++) {
			asts[i].Update(del);
		}

	}

	public int getID() {
		return 1;
	}

}
