package game;

import org.newdawn.slick.SlickException;

public class Tank extends CollidableRenderableObject{
	public Tank() throws SlickException{
		super("res/Sherman Tank Sprite.png",CollidableRenderableObject.Physics.Rectangular);
		resolve = false;
	}
	
	public void MoveTank(float x){
		//SetVelocity()
		//float hip = 0.4f * x;

		float rotation = (float) Math.toRadians(GetRotation());
		
		float tankx = (float) (x * Math.sin(rotation));
		float tanky = (float) (x * Math.cos(rotation));
		SetVelocity(tankx,-tanky);
	}
	@Override
	public void Update(float deltaTime){
		super.Update(deltaTime);
		SetVelocity(0f,0f);
		angleSpeed =0f;
		
	}
	public void RotateTank(float r){
		angleSpeed = r;
	}
}
