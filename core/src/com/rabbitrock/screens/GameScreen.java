package com.rabbitrock.screens;
<<<<<<< Updated upstream
<<<<<<< Updated upstream

||||||| merged common ancestors

import com.badlogic.gdx.Game;
=======
||||||| merged common ancestors

=======
>>>>>>> Stashed changes
import com.badlogic.gdx.Game;
>>>>>>> Stashed changes
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.rabbitrock.game.WorldController; 
import com.rabbitrock.game.WorldRenderer;
import com.rabbitrock.util.GamePreferences;

public class GameScreen extends AbstractGameScreen {
<<<<<<< Updated upstream
||||||| merged common ancestors
	 private static final String TAG = GameScreen.class.getName();
	 
	 private WorldController worldController;
	 private WorldRenderer worldRenderer;
	 
	 private boolean paused;
	 
	 public GameScreen (Game game) {   
		  super(game);  
	  }	 

@Override 
public void render (float deltaTime) { 
	// Do not update game world when paused. 
	if (!paused) {   
		// Update game world by the time that has passed    
		// since last rendered frame.    
		worldController.update(deltaTime);  
		}  
	// Sets the clear screen color to: Cornflower Blue  
	Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 
255.0f, 0xff / 255.0f);  
	// Clears the screen 
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);  
	// Render game world to screen    
	worldRenderer.render(); 
	}  
@Override 
	public void resize (int width, int height) {  
		worldRenderer.resize(width, height); 
		
}
=======
	 private static final String TAG = GameScreen.class.getName();
	 
	 private WorldController worldController;
	 private WorldRenderer worldRenderer;
	 
	 private boolean paused;
	 
	 public GameScreen (Game game) {   
		  super(game);  
	  }	 

@Override 
public void render (float deltaTime) { 
	// Do not update game world when paused. 
	if (!paused) {   
		// Update game world by the time that has passed    
		// since last rendered frame.    
		worldController.update(deltaTime);  
		}  
	// Sets the clear screen color to: Cornflower Blue  
	Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 
255.0f, 0xff / 255.0f);  
	// Clears the screen 
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);  
	// Render game world to screen    
	worldRenderer.render(); 
	}  
@Override 
	public void resize (int width, int height) {  
		worldRenderer.resize(width, height); 
		
}
>>>>>>> Stashed changes

<<<<<<< Updated upstream
	private static final String TAG = GameScreen.class.getName();

	private WorldController worldController;
	private WorldRenderer worldRenderer;

	private boolean paused;

	public GameScreen (DirectedGame game) {
		super(game);
	}

	@Override
	public void render (float deltaTime) {
		// Do not update game world when paused.
		if (!paused) {
			// Update game world by the time that has passed
			// since last rendered frame.
			worldController.update(deltaTime);
		}
		// Sets the clear screen color to: Cornflower Blue
		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
		// Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render game world to screen
		worldRenderer.render();
	}

	@Override
	public void resize (int width, int height) {
		worldRenderer.resize(width, height);
	}
||||||| merged common ancestors
 @Override 
	  public void show () {  
	 	  //GamePreferences.instance.load(); 
		  worldController = new WorldController(game);
		  worldRenderer = new WorldRenderer(worldController);
		  Gdx.input.setCatchBackKey(true);
}
	  public void hide () {  
		  worldRenderer.dispose();  
		  Gdx.input.setCatchBackKey(false); 
}
=======
 @Override 
	  public void show () {  
	 	  //GamePreferences.instance.load(); 
		  worldController = new WorldController(game);
		  worldRenderer = new WorldRenderer(worldController);
		  Gdx.input.setCatchBackKey(true);
}
	  public void hide () {  
		  worldRenderer.dispose();  
		  Gdx.input.setCatchBackKey(false); 
}
>>>>>>> Stashed changes

<<<<<<< Updated upstream
	@Override
	public void show () {
		GamePreferences.instance.load();
		worldController = new WorldController(game);
		worldRenderer = new WorldRenderer(worldController);
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void hide () {
		worldRenderer.dispose();
		Gdx.input.setCatchBackKey(false);
	}

	@Override
	public void pause () {
		paused = true;
	}

<<<<<<< Updated upstream
	@Override
	public void resume () {
		super.resume();
		// Only called on Android!
		paused = false;
	}

	@Override
	public InputProcessor getInputProcessor () {
		return worldController;
	}

}
||||||| merged common ancestors
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}}
=======
@Override  public void pause () {   
	paused = true;
}

@Override  public void resume () { 
	super.resume();  
	// Only called on Android!   
	paused = false;  }
} 
>>>>>>> Stashed changes
||||||| merged common ancestors
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}}
=======
@Override  public void pause () {   
	paused = true;
}

@Override  public void resume () { 
	super.resume();  
	// Only called on Android!   
	paused = false;  }
} 
>>>>>>> Stashed changes
