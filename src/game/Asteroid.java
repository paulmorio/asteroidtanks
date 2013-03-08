package game;

// This is the class for Asteroids, it will include how they are created
// their size, shape, etc.

public class Asteroid {
	
	double r; // asteroid radius
	double x; // x position
	double y; // y position
	double dx; // change in x for movement
	double dy; // change in y for movement
	
	public Asteroid() {
		
		// radius of asteroid
		 r = Math.random() * 5;
		 
		 
		 // starting position of asteroid
		 if(Math.random() < 0.5) {
			 x = 0;
			 y = Math.random() * 600; //screen width set in AstralTanks setDisplayMode
		 }
		 else {
			 x = Math.random() * 800; //screen height set in AstralTanks setDisplayMode
			 y = 0;
		 }
		 
		 // dy and dx for movement. linear.
		 
		 dx = Math.random();
		 dy = Math.random();
		
	}
	
	

}
