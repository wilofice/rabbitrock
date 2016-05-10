package com.rabbitrock.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.rabbitrock.game.WorldController;
import com.rabbitrock.util.Constants;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class WorldRenderer implements Disposable{
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private WorldController worldController;
	
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
	private void renderGuiGameOverMessage (SpriteBatch batch) {
		  float x = cameraGUI.viewportWidth / 2;
		  float y = cameraGUI.viewportHeight / 2;
		  if (worldController.isGameOver()) {
		    BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
		    fontGameOver.setColor(1, 0.75f, 0.25f, 1);
		    fontGameOver.drawMultiLine(batch, "GAME OVER", x, y, 0,  
		BitmapFont.HAlignment.CENTER);
		    fontGameOver.setColor(1, 1, 1, 1);
		  }
		}
	private void renderGuiFeatherPowerup (SpriteBatch batch) {
		  float x = -15;
		  float y = 30;
		  float timeLeftFeatherPowerup =  
		 worldController.level.bunnyHead.timeLeftFeatherPowerup;
		  if (timeLeftFeatherPowerup > 0) {
		    // Start icon fade in/out if the left power-up time
		    // is less than 4 seconds. The fade interval is set
		    // to 5 changes per second.
		    if (timeLeftFeatherPowerup < 4) {
		      if (((int)(timeLeftFeatherPowerup * 5) % 2) != 0) {
		        batch.setColor(1, 1, 1, 0.5f);
		      }
		    }
		    batch.draw(Assets.instance.feather.feather,  
		x, y, 50, 50, 100, 100, 0.35f, -0.35f, 0);
		    batch.setColor(1, 1, 1, 1);
		    Assets.instance.fonts.defaultSmall.draw(batch,  
		"" + (int)timeLeftFeatherPowerup, x + 60, y + 57);
		  }
		}
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
	  renderGuiFpsCounter(batch);
	  // draw game over text
	  renderGuiGameOverMessage(batch);
	  batch.end();
	}
	private void renderGuiExtraLive (SpriteBatch batch) {
		float x = cameraGUI.viewportWidth – 50  
		Constants.LIVES_START * 50;
		float y = -15;
		for (int i = 0; i<Constants.LIVES_START; i++) {
		if (worldController.lives<= i)
		batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
		batch.draw(Assets.instance.bunny.head,  
		x + i * 50, y, 50, 50, 120, 100, 0.35f, -0.35f, 0);
		batch.setColor(1, 1, 1, 1);
		}
		if (worldController.lives>= 0  
		&&worldController.livesVisual>worldController.lives) {
		int i = worldController.lives;
		float alphaColor = Math.max(0, worldController.livesVisual  
		 - worldController.lives - 0.5f);
		float alphaScale = 0.35f * (2 + worldController.lives  
		 - worldController.livesVisual) * 2;
		float alphaRotate = -45 * alphaColor;
		batch.setColor(1.0f, 0.7f, 0.7f, alphaColor);
		batch.draw(Assets.instance.bunny.head,  
		 x + i * 50, y, 50, 50, 120, 100, alphaScale, -alphaScale,  
		alphaRotate);
		batch.setColor(1, 1, 1, 1);
		  }
		}
	private void renderGuiScore (SpriteBatch batch) {
		float x = -15;
		float y = -15;
		float offsetX = 50;
		float offsetY = 50;
		if (worldController.scoreVisual<worldController.score) {
		long shakeAlpha = System.currentTimeMillis() % 360;
		float shakeDist = 1.5f;
		offsetX += MathUtils.sinDeg(shakeAlpha * 2.2f) * shakeDist;
		offsetY += MathUtils.sinDeg(shakeAlpha * 2.9f) * shakeDist;
		  }
		batch.draw(Assets.instance.goldCoin.goldCoin, x, y, offsetX,  
		offsetY, 100, 100, 0.35f, -0.35f, 0);
		Assets.instance.fonts.defaultBig.draw(batch,  
		 "" + (int)worldController.scoreVisual,  
		 x + 75, y + 37);
		}
}
