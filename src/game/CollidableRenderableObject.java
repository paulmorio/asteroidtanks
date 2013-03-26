package game;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;

import com.sun.xml.internal.rngom.parse.host.Base;

public class CollidableRenderableObject extends RenderableObject {
	public enum Physics{
		Circular,
		Rectangular
	}
	public boolean invert = false;
	public boolean resolve = true;
	public static ArrayList<CollidableRenderableObject> collRendObjects;
	static boolean init2 = true;
	static void Init2(){
		if(init2){
			collRendObjects = new ArrayList<CollidableRenderableObject>();
			init2 = false;
		}
	}
	static int SizeColls(){
		if(collRendObjects== null)
			return 0;
		else
			return collRendObjects.size();
	}
	public static void CheckCollisions(){
		//System.out.print(SizeColls());
		for(int i = 0;i<SizeColls();i++){
			switch(collRendObjects.get(i).physics){
			case Circular:
				((Circle)collRendObjects.get(i).physicsShape).setCenterX(collRendObjects.get(i).rectangle.getX());
				((Circle)collRendObjects.get(i).physicsShape).setCenterY(collRendObjects.get(i).rectangle.getY());
				break;
			case Rectangular:
				((Rectangle)collRendObjects.get(i).physicsShape).setX(collRendObjects.get(i).rectangle.getX());
				((Rectangle)collRendObjects.get(i).physicsShape).setY(collRendObjects.get(i).rectangle.getY());
				break;
			}
			
		}
		for(int i = 0;i<SizeColls();i++){
			for(int j = i+1;j<SizeColls();j++){
				boolean in = collRendObjects.get(i).CheckIntersection(collRendObjects.get(j));
				boolean inv = collRendObjects.get(i).invert || collRendObjects.get(j).invert;
				if(inv ? !in : in){

					collRendObjects.get(i).Colleded(collRendObjects.get(j));
					collRendObjects.get(j).Colleded(collRendObjects.get(i));
				}
			}
		}
	}
	
	
	public void Colleded(CollidableRenderableObject cro){
		if(!resolve)
			return;
		
		Vector2f deltaPos;
		if(cro.invert){
			deltaPos = Vector2f.sub(cro.GetPosition(),this.GetPosition(),null);
		}else{
			deltaPos = Vector2f.sub(this.GetPosition(), cro.GetPosition(),null);
		}
		deltaPos.normalise(deltaPos);
		deltaPos.scale(speed);
		//System.out.print(speed+", ");
		SetVelocity(deltaPos);
		//= Vector2f.sub(left, right, dest) this.GetPosition().;
	}
	
	public Physics physics;
	public Shape physicsShape;
	public void SetPhysics(Physics newPhysics, float scale){
		physics = newPhysics;
		switch(physics){
		case Circular:
			physicsShape = new Circle(rectangle.getX(),rectangle.getY(), rectangle.getHeight()*scale);
			break;
		case Rectangular:
			physicsShape = new Rectangle(rectangle.getX(),rectangle.getY(), rectangle.getWidth()*scale,rectangle.getHeight()*scale);
		}
	}

	public boolean CheckIntersection(CollidableRenderableObject other){
		return physicsShape.intersects(other.physicsShape);
	}
	
	public CollidableRenderableObject(String file, Physics physics)throws SlickException {
		
		super(file);
		//Test1();
		Init2();
		SetPhysics(physics,1f);
		collRendObjects.add(this);
		
	}
	static int noi = 0;
	void Test1(){
		noi++;
		System.out.print(noi+" ");
	}
}
