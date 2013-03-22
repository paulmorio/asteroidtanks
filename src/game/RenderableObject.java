package game;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class RenderableObject {
	public static ArrayList<RenderableObject> rendObjects;
	public static boolean init = true;
	public Image image;
	public Rectangle rectangle;
	public Vector2f velocity;
	//public Image 
	public static void Init(){
		if(init){
			rendObjects = new ArrayList<RenderableObject>();
			init = false;
		}
		
	}
	public RenderableObject(String file)throws SlickException {
		Init();
		rendObjects.add(this);
		image = new Image(file);
		rectangle = new Rectangle(0f,0f,1f,1f);
		velocity = new Vector2f(0f,0f);
	}
	
	public void Update(float deltaTime){
		
		AddPosition(velocity.getX()*deltaTime, velocity.getY()*deltaTime);
	};
	
	public void setVelocity(float x, float y){
		velocity.setX(x);
		velocity.setY(y);
	}
	
	public void SetPosition(float x,float y){
		rectangle.setX(x);
		rectangle.setY(y);
	}
	public void AddPosition(float x,float y){
		rectangle.setX(rectangle.getX()+ x);
		rectangle.setY(rectangle.getY()+ y);
	}
	
	void Draw(){
		image.draw(rectangle.getX()*AstralTanks.screenWidth, rectangle.getY()*AstralTanks.screenHeight);
	}
	
	
}
