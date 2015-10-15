package net.matthiasauer.ecstools.examples.bitmap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import net.matthiasauer.ecstools.examples.IDemoSetup;
import net.matthiasauer.ecstools.graphics.RenderComponent;
import net.matthiasauer.ecstools.graphics.RenderPositionUnit;
import net.matthiasauer.ecstools.graphics.RenderSystem;

public class BitmapDemoSetup implements IDemoSetup {
	private static final String TEXTURE_FILE = "badlogic.jpg";
	private final PooledEngine engine;
	private final OrthographicCamera camera;
	private final AtlasRegion atlasRegion;
	private Entity buttonEntity;
	
	public BitmapDemoSetup(
			PooledEngine engine,
			OrthographicCamera camera) {
		this.engine = engine;
		this.camera = camera;

		Texture texture = new Texture(TEXTURE_FILE);
		this.atlasRegion = new AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
	}
	
	public void setup() {
		this.createSystems();
		this.createButton();
	}
	
	private void createSystems() {
		// start the render system
		this.engine.addSystem(new RenderSystem(this.camera));
	}
	
	private void createButton() {
		this.buttonEntity = this.engine.createEntity();
		
		buttonEntity.add(
				this.engine.createComponent(RenderComponent.class).setSprite(
						0,
						0,
						45,
						RenderPositionUnit.Pixels,
						this.atlasRegion,
						0,
						false,
						null));
		
		this.engine.addEntity(buttonEntity);
	}
}
