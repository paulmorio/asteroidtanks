package game;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class RenderableObject {
	public static ArrayList<RenderableObject> rendObjects;
	public static boolean init = true;
	public Vector2f position;
	public Image image;
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
		position = new Vector2f(0f,0f);
	}
	
	public void Update(float deltaTime){};
	
	void Draw(){
		image.draw(position.x, position.y);
		
	}
}
