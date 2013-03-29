package game;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Play extends BasicGameState {

	private static final int EMITTER_BOOM = 0;
	private static final int EMITTER_ASTEROID = 0;
	private static final int NUM_EMITTERS = 15;
	private static final int FIRING_DELAY = 120;
	private boolean soundEnabled = true;
	private int id;
	// private Movable player = new Movable(400.0F, 300.0F, new Rectangle());

	float tankx = 400;
	float tanky = 300;
	float scale = 1f;

	// asteroids
	int numberOfAsteroids = 10;
	private CollidableRenderableObject[] asts;
	private Asteroid[] ast = new Asteroid[20];
	boolean quit = false;

	// Images to be used.
	private Image tank;
	private CollidableRenderableObject land;
	private CollidableRenderableObject bullet;

	// Sounds to be used
	private Sound soundHit1;
	private Sound soundHit2;
	private Sound soundShoot;
	private Sound soundExplosion;
	// When we actually have a winning situation
	// private Sound soundWin;

	// Particles
	private BaseParticle[] boom = new BaseParticle[15];
	private BaseParticle[] blood = new BaseParticle[15];
	private BaseParticle[] barrelboom = new BaseParticle[7];

	public Play(int State) {

	}

	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

		tank = new Image("res/Sherman Tank Sprite.png");
		land = new CollidableRenderableObject("res/messier81_800x600.jpg",
				CollidableRenderableObject.Physics.Rectangular);
		land.invert = true;
		land.resolve = false;
		land.SetPosition(0f, 0f);
		// land.position = new Vector2f(2, 5);

		for (int i = 0; i < ast.length; i++) {
			ast[i] = new Asteroid();
		}
		asts = new CollidableRenderableObject[numberOfAsteroids];
		for (int i = 0; i < numberOfAsteroids; i++) {
			asts[i] = new CollidableRenderableObject("res/asteroid.png",
					CollidableRenderableObject.Physics.Rectangular, 0.8f);
			asts[i].SetPosition((float) Math.random(), (float) Math.random());
			asts[i].SetVelocity(RandMinus1To1() * 0.3f, RandMinus1To1() * 0.1f);
			asts[i].image.rotate(RandMinus1To1() * 180f);
			// asts[i].Explode();
		}

	}

	public static float RandMinus1To1() {
		return (float) ((Math.random() - 0.5) * 2f);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		if (RenderableObject.rendObjects != null)
			for (RenderableObject ro : RenderableObject.rendObjects) {
				ro.Draw();
			}

		// for (int i = 0; i < 10; i++) {asts[i].Draw();}
		// plane.draw(tankx, tanky, scale);

		// for when the player presses escape
		if (quit == true) {
			g.drawString("Resume (R)", 390, 100);
			g.drawString("Main Menu (M)", 390, 150);
			g.drawString("Quit Game (Q)", 390, 200);
			if (quit == false) {
				g.clear();
			}
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// if(quit)
		// return;

		Input input = gc.getInput();
		float del = (float) delta / 1000f;
		if (input.isKeyDown(Input.KEY_A)) {
			tank.rotate(-0.2f * delta);
		}

		if (input.isKeyDown(Input.KEY_D)) {
			tank.rotate(0.2f * delta);
		}

		if (input.isKeyDown(Input.KEY_W)) {
			float hip = 0.4f * delta;

			float rotation = tank.getRotation();

			tankx += hip * Math.sin(Math.toRadians(rotation));
			tanky -= hip * Math.cos(Math.toRadians(rotation));
		}

		if (input.isKeyDown(Input.KEY_S)) {
			float hip = 0.4f * delta;

			float rotation = tank.getRotation();

			tankx -= hip * Math.sin(Math.toRadians(rotation));
			tanky += hip * Math.cos(Math.toRadians(rotation));
		}

		if (input.isKeyDown(Input.KEY_2)) {
			scale += (scale >= 5.0f) ? 0 : 0.1f;
			tank.setCenterOfRotation(tank.getWidth() / 2.0f * scale,
					tank.getHeight() / 2.0f * scale);
		}
		if (input.isKeyDown(Input.KEY_1)) {
			scale -= (scale <= 1.0f) ? 0 : 0.1f;
			tank.setCenterOfRotation(tank.getWidth() / 2.0f * scale,
					tank.getHeight() / 2.0f * scale);
		}

		// escape to open ingame menu
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			quit = true;
		}

		// when the player hits escape
		if (quit == true) {
			if (input.isKeyDown(Input.KEY_R)) {
				quit = false;
			}

			if (input.isKeyDown(Input.KEY_M)) {
				sbg.enterState(0);
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if (input.isKeyDown(Input.KEY_Q)) {
				System.exit(0);
			}

		}

		// as game runs, moves asteroid across screen
		// change += 1 to something to do with dy, dx
		for (int i = 0; i < ast.length; i++) {
			ast[i].x += 1;
			ast[i].y += 1;
		}

		if (RenderableObject.rendObjects != null)
			for (RenderableObject ro : RenderableObject.rendObjects) {
				ro.Update(del);
			}

		CollidableRenderableObject.CheckCollisions();

	}

	public int getID() {
		return 1;
	}

}
