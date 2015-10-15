package net.matthiasauer.ecstools.examples;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.matthiasauer.ecstools.examples.bitmap.BitmapDemoSetup;

public class EntryPoint extends ApplicationAdapter {
	private PooledEngine engine;
	private OrthographicCamera camera;
	private InputMultiplexer inputMultiplexer;
	private Viewport viewport;
	private long lastTimestep = System.currentTimeMillis();
	
	@Override
	public void create () {
		this.engine = new PooledEngine();
		this.camera = new OrthographicCamera(800, 600);
		this.viewport = new ScreenViewport(this.camera);
		this.inputMultiplexer = new InputMultiplexer();

		Gdx.input.setInputProcessor(this.inputMultiplexer);
		
		this.engine.addSystem(
				new NextExampleSystem(BitmapDemoSetup.class, this.engine, this.camera));
	}
	
	@Override
	public void resize(int width, int height) {
		this.viewport.update(width, height);
	}
	
	@Override
	public void render() {
		long current = System.currentTimeMillis();
		long difference = current - lastTimestep;
		lastTimestep = current;
		
		this.engine.update( difference / 1000f );
	}
}
