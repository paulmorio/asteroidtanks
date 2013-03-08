package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// This is the class for Asteroids, it will include how they are created
// their size, shape, etc.

public class Asteroid {
	
	double r; // asteroid radius
	int x; // x position
	int y; // y position
	int dx; // change in x for movement
	int dy; // change in y for movement
	Image asteroid;
	public Asteroid() throws SlickException{
		
		// radius of asteroid
		 r = Math.random() * 5;
		 

		 /*if(Math.random() < 0.5) {

			 x = 0;
			 y = (int)(Math.random() * 600f); //screen width set in AstralTanks setDisplayMode
		 }
		 else {
			 x = (int)(Math.random() * 800f); //screen height set in AstralTanks setDisplayMode
			 y = 0;

		 }
		 
		 // dy and dx for movement. linear.
		 
		 dx = Math.random();
		 dy = Math.random();

		 }*/
		 x = (int)(Math.random() * 800f);
		 y = (int)(Math.random() * 600f);
		 dx = -1;
		 dy = -1;
		 
			 asteroid = new Image("Sherman Tank Sprite.png");
		 
	}
	
	public void Draw(){
		asteroid.draw(x, y);
	}
	
	public void Update(){
		x+=dx;
		y+=dy;
		Draw();
>>>>>>> ast
	}
	
	

}
