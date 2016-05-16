package com.rabbitrock.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.rabbitrock.game.WorldController;
import com.rabbitrock.util.Constants;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.rabbitrock.util.GamePreferences;

public class WorldRenderer implements Disposable{
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private WorldController worldController;
	
	private void renderGui (SpriteBatch batch) {  
		batch.setProjectionMatrix(cameraGUI.combined);  
		batch.begin();
	  // draw collected gold coins icon + text 
		// (anchored to top left edge) 
		renderGuiScore(batch); 
		// draw collected feather icon (anchored to top left edge)
		renderGuiFeatherPowerup(batch); 
		// draw extra lives icon + text (anchored to top right edge)
		renderGuiExtraLive(batch); 
		// draw FPS text (anchored to bottom right edge) 
		if (GamePreferences.instance.showFpsCounter) 
			renderGuiFpsCounter(batch);  
		// draw game over text 
		renderGuiGameOverMessage(batch);
	  batch.end();
	  }
	public WorldRenderer(WorldController worldController) {
		this.worldController = worldController;
		init();
	}
	private void init() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
	}
	
	private void renderTestObjects() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(Sprite sprite : worldController.testSprites) {
		sprite.draw(batch);
		}
		batch.end();
	}
	
	public void render(){
		renderTestObjects();
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
	}
	
	@Override public void dispose(){
		batch.dispose();
	}
}
