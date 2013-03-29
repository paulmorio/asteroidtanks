package game;

import org.newdawn.slick.SlickException;

public class Tank extends CollidableRenderableObject{
	public Tank() throws SlickException{
		super("res/Sherman Tank Sprite.png",CollidableRenderableObject.Physics.Rectangular);
	}
	
	public void Move(float x){
		//SetVelocity()
		float hip = 0.4f * x;

		float rotation = GetRotation();

		float tankx = (float) (hip * Math.sin(Math.toRadians(rotation)));
		float tanky = (float) (hip * Math.cos(Math.toRadians(rotation)));
		SetVelocity(tankx,tanky);
	}
}
