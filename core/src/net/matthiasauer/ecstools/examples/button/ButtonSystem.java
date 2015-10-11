package net.matthiasauer.ecstools.examples.button;

import java.util.Random;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;

import net.matthiasauer.ecstools.graphics.RenderComponent;
import net.matthiasauer.ecstools.input.click.ClickedComponent;

public class ButtonSystem extends IteratingSystem {
	@SuppressWarnings("unchecked")
	private static final Family family =
			Family.all(ButtonComponent.class, ClickedComponent.class).get();
	private static final Random random = new Random();
	private final ComponentMapper<RenderComponent> renderComponentMapper;
	
	public ButtonSystem() {
		super(family);
		
		this.renderComponentMapper =
				ComponentMapper.getFor(RenderComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		RenderComponent renderComponent =
				this.renderComponentMapper.get(entity);
		
		renderComponent.tint = choice(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
		
		System.err.println("a button (entity #" + entity.getId() + ") was clicked !");
	}

	private <T> T choice(T ... elements) {
		int randomIndex = random.nextInt(elements.length);
		return
				elements[randomIndex];
	}
}
