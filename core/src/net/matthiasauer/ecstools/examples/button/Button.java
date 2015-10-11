package net.matthiasauer.ecstools.examples.button;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import net.matthiasauer.ecstools.examples.IDemoSetup;
import net.matthiasauer.ecstools.graphics.RenderComponent;
import net.matthiasauer.ecstools.graphics.RenderPositionUnit;
import net.matthiasauer.ecstools.graphics.RenderSystem;
import net.matthiasauer.ecstools.graphics.texture.archive.RenderTextureArchiveSystem;
import net.matthiasauer.ecstools.input.base.gestures.InputGestureEventGenerator;
import net.matthiasauer.ecstools.input.base.touch.InputTouchGeneratorSystem;
import net.matthiasauer.ecstools.input.base.touch.InputTouchTargetComponent;
import net.matthiasauer.ecstools.input.click.ClickGeneratorSystem;
import net.matthiasauer.ecstools.input.click.ClickableComponent;

public class Button implements IDemoSetup {
	private static final String TEXTURE_FILE = "badlogic.jpg";
	private final PooledEngine engine;
	private final OrthographicCamera camera;
	private final AtlasRegion atlasRegion;
	private Entity buttonEntity;
	
	public Button(
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
		OrthographicCamera camera = new OrthographicCamera(800, 600);
		InputMultiplexer inputMultiplexer = new InputMultiplexer();		
		Gdx.input.setInputProcessor(inputMultiplexer);
		
		// start the render system
		this.engine.addSystem(new RenderSystem(this.camera));
		this.engine.addSystem(new RenderTextureArchiveSystem());
		this.engine.addSystem(new InputGestureEventGenerator(inputMultiplexer));
		this.engine.addSystem(new InputTouchGeneratorSystem(inputMultiplexer, camera));
		this.engine.addSystem(new ClickGeneratorSystem());
		this.engine.addSystem(new ButtonSystem());
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
		
		// register whether the mouse touches the rendered entity
		buttonEntity.add(
				this.engine.createComponent(InputTouchTargetComponent.class));
		
		// makes the entity clickable		
		buttonEntity.add(
				this.engine.createComponent(ClickableComponent.class));
		
		// note that this is now a button !
		buttonEntity.add(
				this.engine.createComponent(ButtonComponent.class));
		
		this.engine.addEntity(buttonEntity);
	}
}
