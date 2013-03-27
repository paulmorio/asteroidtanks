package game;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class RenderableObject {
	public static ArrayList<RenderableObject> rendObjects;
	private static boolean init = true;
	public Image image;
	public Rectangle rectangle;
	private Vector2f velocity;
	public float angleSpeed = 0f;
	//public Image 
	static void Init(){
		if(init){
			rendObjects = new ArrayList<RenderableObject>();
			init = false;
		}
		
	}
	
	
	public RenderableObject(String file)throws SlickException {
		Init();
		rendObjects.add(this);
		image = new Image(file);
		rectangle = new Rectangle(0f,0f,image.getWidth()/(float)AstralTanks.screenWidth,image.getHeight()/(float)AstralTanks.screenHeight);
		velocity = new Vector2f(0f,0f);
	}
	
	public RenderableObject(String file, float scale)throws SlickException {
		Init();
		rendObjects.add(this);
		image = new Image(file);
		rectangle = new Rectangle(0f,0f,image.getWidth()/(float)AstralTanks.screenWidth*scale,image.getHeight()/(float)AstralTanks.screenHeight*scale);
		velocity = new Vector2f(0f,0f);
	}
	
	public void Update(float deltaTime){
		
		AddPosition(velocity.getX()*deltaTime, velocity.getY()*deltaTime);
		image.setRotation(image.getRotation()+angleSpeed*deltaTime);
	};
	float speed=0f;
	
	public void SetVelocity(Vector2f v){
		velocity = v;
		//velocity.set(v);
		speed = velocity.length();
	}
	
	public void SetVelocity(float x, float y){
		velocity.setX(x);
		velocity.setY(y);
		speed = velocity.length();
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
		
		image.draw(rectangle.getX()*AstralTanks.screenWidth, rectangle.getY()*AstralTanks.screenHeight,rectangle.getWidth()*AstralTanks.screenWidth,rectangle.getHeight()*AstralTanks.screenHeight);
	}
	public Vector2f GetPosition(){
		return new Vector2f(rectangle.getX(),rectangle.getY());
	}
	
}
