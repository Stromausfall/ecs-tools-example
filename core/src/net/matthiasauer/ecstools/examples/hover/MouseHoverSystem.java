package net.matthiasauer.ecstools.examples.hover;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;

import net.matthiasauer.ecstools.graphics.RenderComponent;
import net.matthiasauer.ecstools.input.base.touch.InputTouchEventComponent;

public class MouseHoverSystem extends EntitySystem {
	@SuppressWarnings("unchecked")
	private static final Family renderEntityFamily =
			Family.all(RenderComponent.class).get();
	@SuppressWarnings("unchecked")
	private static final Family inputTouchEventContainerEntityFamily =
			Family.all(InputTouchEventComponent.class).get();
	private final ComponentMapper<InputTouchEventComponent> inputTouchEventComponentMapper =
			ComponentMapper.getFor(InputTouchEventComponent.class);
	private final ComponentMapper<RenderComponent> renderComponentMapper =
			ComponentMapper.getFor(RenderComponent.class);
	private PooledEngine pooledEngine;
	private ImmutableArray<Entity> renderEntities;
	private ImmutableArray<Entity> inputTouchEventContainerEntities;

	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		
		this.pooledEngine = (PooledEngine) engine;
		this.renderEntities =
				this.pooledEngine.getEntitiesFor(renderEntityFamily);
		this.inputTouchEventContainerEntities =
				this.pooledEngine.getEntitiesFor(inputTouchEventContainerEntityFamily);
	}
	
	@Override
	public void update(float deltaTime) {
		List<Entity> hoveredEntities =
				new LinkedList<Entity>();
		
		for (Entity containerEntity : this.inputTouchEventContainerEntities) {
			InputTouchEventComponent component =
					this.inputTouchEventComponentMapper.get(containerEntity);
			
			if (component.target != null) {
				hoveredEntities.add(component.target);
			}
		}

		if (this.inputTouchEventContainerEntities.size() != 0) {
			// first remove the color from all entities (restore their state to default)
			for (Entity markedEntity : this.renderEntities) {
				RenderComponent renderComponent =
						this.renderComponentMapper.get(markedEntity);
				
				// restore original color !
				renderComponent.tint = null;
			}
		}
		
		for (Entity targetEntity : hoveredEntities) {		
			RenderComponent renderComponent =
					this.renderComponentMapper.get(targetEntity);
			
			// make the graphic to render RED - because the cursor is positoned over it
			renderComponent.tint = Color.RED;
		}
	}
}
