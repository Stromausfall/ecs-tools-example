package net.matthiasauer.ecstools.examples.text;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;

import net.matthiasauer.ecstools.examples.IDemoSetup;
import net.matthiasauer.ecstools.graphics.RenderComponent;
import net.matthiasauer.ecstools.graphics.RenderPositionUnit;
import net.matthiasauer.ecstools.graphics.RenderSystem;

public class TextDemoSetup implements IDemoSetup {
	private final PooledEngine engine;
	private final OrthographicCamera camera;
	private Entity textEntity1;
	private Entity textEntity2;
	
	public TextDemoSetup(
			PooledEngine engine,
			OrthographicCamera camera) {
		this.engine = engine;
		this.camera = camera;
	}
	
	public void setup() {
		this.createSystems();
		this.createText();
	}
	
	private void createSystems() {
		// start the render system
		this.engine.addSystem(new RenderSystem(this.camera));
	}
	
	private void createText() {
		this.textEntity1 = this.engine.createEntity();
		this.textEntity2 = this.engine.createEntity();
		
		this.textEntity1.add(
				engine.createComponent(RenderComponent.class).setText(
						0, 0, 0, RenderPositionUnit.Pixels, 0, true, "foo foo 1", "IrishGrowler", Color.YELLOW));
		this.textEntity2.add(
				engine.createComponent(RenderComponent.class).setText(
						0, 0, 180, RenderPositionUnit.Pixels, 0, true, "foo foo 2", null, null));
		
		this.engine.addEntity(this.textEntity1);
		this.engine.addEntity(this.textEntity2);
	}
}
