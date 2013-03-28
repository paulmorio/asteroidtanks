package game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.util.FastTrig;

public class Missile {

	private Image image;
	private float speed;
	private float x;
	private float y;
	private boolean completed;
	private float vecX;
	private float vecY;
	private float distance;
	private BaseParticle explosionParticle;
	private String explosionSoundName;
	private float startX;
	private float startY;
	private float expX;
	private float expY;
	private float centerXAdd;
	private float centerYAdd;

	/*
	 * public Missile(Image image, float speed, int particleLifeTime, String
	 * emitterName, String soundName, float x, float y, float distance, float
	 * angle) { this(image, speed, hitScore, particleLifeTime, emitterName,
	 * soundName, x, y, distance, angle); }
	 */

	public Missile(Image image, float speed, int particleLifeTime,
			String emitterName, String soundName, float x, float y,
			float distance, float angle, int range) {
		this.image = image;
		this.speed = speed;
		this.x = x;
		this.y = y;
		this.startX = x;
		this.startY = y;
		this.angle = angle;
		this.distance = distance;
		this.particleLifeTime = particleLifeTime;
		this.emitterName = emitterName;
		this.explosionSoundName = soundName;
		this.range = range;

	}

}
