package net.matthiasauer.ecstools.examples.hover;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;

import net.matthiasauer.ecstools.graphics.RenderComponent;
import net.matthiasauer.ecstools.input.base.touch.InputTouchEventComponent;

public class MouseHoverSystem extends IteratingSystem {
	@SuppressWarnings("unchecked")
	private static final Family family =
			Family.all(InputTouchEventComponent.class).get();
	@SuppressWarnings("unchecked")
	private static final Family renderEntityFamily =
			Family.all(RenderComponent.class).get();
	private final ComponentMapper<InputTouchEventComponent> inputTouchEventComponentMapper =
			ComponentMapper.getFor(InputTouchEventComponent.class);
	private final ComponentMapper<RenderComponent> renderComponentMapper =
			ComponentMapper.getFor(RenderComponent.class);
	private PooledEngine pooledEngine;
	private ImmutableArray<Entity> renderEntities;

	public MouseHoverSystem() {
		super(family);
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		
		this.pooledEngine = (PooledEngine) engine;
		this.renderEntities =
				this.pooledEngine.getEntitiesFor(renderEntityFamily);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		InputTouchEventComponent component =
				this.inputTouchEventComponentMapper.get(entity);
		Entity targetEntity = component.target;
		
		// first remove the color from all entities (restore their state to default)
		for (Entity markedEntity : this.renderEntities) {
			RenderComponent renderComponent =
					this.renderComponentMapper.get(markedEntity);
			
			// restore original color !
			renderComponent.tint = null;
		}
		
		if (targetEntity == null) {
			// no target
			return;
		}
		
		RenderComponent renderComponent =
				this.renderComponentMapper.get(targetEntity);
		
		// make the graphic to render RED - because the cursor is positoned over it
		renderComponent.tint = Color.RED;
	}
}
