package net.matthiasauer.ecstools.examples;

import java.util.Collection;
import java.util.LinkedList;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.OrthographicCamera;

import net.matthiasauer.ecstools.examples.bitmap.BitmapDemoSetup;
import net.matthiasauer.ecstools.examples.click.ClickDemoSetup;
import net.matthiasauer.ecstools.examples.hover.MouseHoverSetup;
import net.matthiasauer.ecstools.examples.text.TextDemoSetup;
import net.matthiasauer.ecstools.graphics.RenderComponent;
import net.matthiasauer.ecstools.graphics.RenderPositionUnit;

public class NextExampleSystem extends EntitySystem {
	private final ComponentMapper<NextExampleComponent> nextExampleComponentMapper =
			ComponentMapper.getFor(NextExampleComponent.class);
	private static final int TIME_UNTIL_NEXT_DEMO = 15;
	private final Class<? extends IDemoSetup> currentExample;
	private final PooledEngine engine;
	private final OrthographicCamera camera;
	private Class<? extends IDemoSetup> nextDemoExample;
	private Entity entity;

	public NextExampleSystem(
			final Class<? extends IDemoSetup> currentExample,
			final PooledEngine engine,
			final OrthographicCamera camera) {
		this.engine = engine;
		this.camera = camera;
		this.currentExample = currentExample;
		this.entity = null;
	}
	
	private void initialize() {/*
		if (this.currentExample == BitmapDemoSetup.class) {
			IDemoSetup setup = new BitmapDemoSetup(engine, camera);
			setup.setup();
			this.nextDemoExample = ClickDemoSetup.class;
		}
		if (this.currentExample == ClickDemoSetup.class) {
			IDemoSetup setup = new ClickDemoSetup(engine, camera);
			setup.setup();
			this.nextDemoExample = TextDemoSetup.class;
		}
		if (this.currentExample == TextDemoSetup.class) {
			IDemoSetup setup = new TextDemoSetup(engine, camera);
			setup.setup();
			this.nextDemoExample = MouseHoverSetup.class;
		}
		if (this.currentExample == MouseHoverSetup.class) {*/
			IDemoSetup setup = new MouseHoverSetup(engine, camera);
			setup.setup();
			this.nextDemoExample = BitmapDemoSetup.class;
		//}
		
		this.entity = this.engine.createEntity();
		this.entity.add(
				this.engine.createComponent(NextExampleComponent.class).set(TIME_UNTIL_NEXT_DEMO));

		this.engine.addEntity(this.entity);
	}
	
	@Override
	public void update(float deltaTime) {		
		if (entity == null) {
			this.initialize();
			return;
		}

		NextExampleComponent component =
				this.nextExampleComponentMapper.get(this.entity);
		
		component.secondsLeft -= deltaTime;
		
		entity.add(
				engine.createComponent(RenderComponent.class).setText(
						-100,
						-80,
						0,
						RenderPositionUnit.Percent,
						null,
						0,
						true,
						"time left in " + this.currentExample.getSimpleName() + " : " + component.secondsLeft,
						"IrishGrowler"));
		
		
		if (component.secondsLeft < 0) {
			this.engine.removeAllEntities();
			this.removeAllSystems();
			
			// after cleaning the whole system, setup the new one !
			//this.getEngine().addSystem(
			this.engine.addSystem(
					new NextExampleSystem(
							this.nextDemoExample,
							this.engine,
							this.camera));
		}
	}
	
	private void removeAllSystems() {
		Collection<EntitySystem> allSystems =
				new LinkedList<EntitySystem>();
		
		for (EntitySystem system : this.engine.getSystems()) {
			allSystems.add(system);
		}
		
		for (EntitySystem system : allSystems) {
			this.engine.removeSystem(system);
		}	
	}
}
