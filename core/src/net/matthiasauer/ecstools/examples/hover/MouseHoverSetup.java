package net.matthiasauer.ecstools.examples.hover;

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
import net.matthiasauer.ecstools.input.base.touch.InputTouchGeneratorSystem;
import net.matthiasauer.ecstools.input.base.touch.InputTouchTargetComponent;

public class MouseHoverSetup implements IDemoSetup {
	private static final String TEXTURE_FILE = "badlogic.jpg";
	private final PooledEngine engine;
	private final OrthographicCamera camera;
	private final AtlasRegion atlasRegion;
	private Entity hoverEntity1;
	private Entity hoverEntity2;
	private Entity hoverEntity3;
	
	public MouseHoverSetup(
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
		this.engine.addSystem(new InputTouchGeneratorSystem(inputMultiplexer, camera));
		
		this.engine.addSystem(new MouseHoverSystem());
	}
	
	private void createButton() {
		this.hoverEntity1 = this.engine.createEntity();
		this.hoverEntity2 = this.engine.createEntity();
		this.hoverEntity3 = this.engine.createEntity();
		
		this.hoverEntity1.add(
				this.engine.createComponent(RenderComponent.class).setSprite(
						-50,
						0,
						0,
						RenderPositionUnit.Percent,
						null,
						1,
						false,
						this.atlasRegion));
		this.hoverEntity2.add(
				this.engine.createComponent(RenderComponent.class).setText(
						0,
						0,
						0,
						RenderPositionUnit.Percent,
						null,
						1,
						false,
						"Dummy Text2",
						"IrishGrowler"));
		this.hoverEntity3.add(
				this.engine.createComponent(RenderComponent.class).setText(
						50,
						0,
						25,
						RenderPositionUnit.Percent,
						null,
						// give it higher priority for rendering AND touch detection !
						2,
						false,
						"Dummy Text",
						"IrishGrowler"));

		this.hoverEntity1.add(
				this.engine.createComponent(InputTouchTargetComponent.class));
		this.hoverEntity2.add(
				this.engine.createComponent(InputTouchTargetComponent.class));
		this.hoverEntity3.add(
				this.engine.createComponent(InputTouchTargetComponent.class));
		
		this.engine.addEntity(this.hoverEntity1);
		this.engine.addEntity(this.hoverEntity2);
		this.engine.addEntity(this.hoverEntity3);
	}
}
