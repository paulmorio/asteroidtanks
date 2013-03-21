package game;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

//Testing this as a different sate
public class Play extends BasicGameState {

	Image plane = null;
	RenderableObject land = null;
	float x = 400;
	float y = 300;
	float scale = 1f;
	int numberOfAsteroids = 10;
	Asteroid[] asts;
	Asteroid[] ast = new Asteroid[20];

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
