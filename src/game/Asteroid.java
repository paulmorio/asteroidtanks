package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// This is the class for Asteroids, it will include how they are created
// their size, shape, etc.

public class Asteroid {
	
	double astradius; // asteroid radius
	double x; // x position
	double y; // y position
	double dx; // change in x for movement
	double dy; // change in y for movement
	Image asteroid;
	public Asteroid() throws SlickException{
		
		// radius of asteroid
		 astradius = Math.random() * 5;
		 

		 if(Math.random() < 0.5) {

			 x = 0.1;
			 y = Math.random() * 600f; //screen width set in AstralTanks setDisplayMode
		 }
		 else {
			 x = Math.random() * 800f; //screen height set in AstralTanks setDisplayMode
			 y = 0.1;

		 }
		 
		 dx = Math.random()*50f;
		 dy = Math.random()*50f;

	
		 
		asteroid = new Image("res/asteroid.jpg");
		 
	}
	
	public void Draw(){
		asteroid.draw((int)x, (int)y);
	}
	
	public void Update(double del){
		if (x == 0 | x == 800 | y == 0 | y == 600) {
			x= -dx*del;
			y= -dy*del;	
		}
		else {
			 x += dx*del;
			 y += dy*del; 
		 }

		//Draw();

	}
	
	

}
