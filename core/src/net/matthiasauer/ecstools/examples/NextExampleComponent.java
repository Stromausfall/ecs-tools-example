package net.matthiasauer.ecstools.examples;

import com.badlogic.ashley.core.Component;

import com.badlogic.gdx.utils.Pool.Poolable;

public class NextExampleComponent implements Component, Poolable {
	public float secondsLeft = 0;

	@Override
	public void reset() {
		this.set(0);
	}
	
	public NextExampleComponent set(float secondsLeft) {
		this.secondsLeft = secondsLeft;
		
		return this;
	}
}
