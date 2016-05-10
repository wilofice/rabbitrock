package com.rabbitrock.screens;


public class GameScreen extends AbstractGameScreen {
	 private static final String TAG = GameScreen.class.getName();
	 
	 private WorldController worldController;
	 private WorldRenderer worldRenderer;
	 
	 private boolean paused;
	 
	 public GameScreen (Game game) {   
		  super(game);  
	  }	 

@Override  public void render (float deltaTime) { 
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

 @Override 
	  public void show () {  
		  worldController = new WorldController();
		  worldRenderer = new WorldRenderer(worldController);
		  Gdx.input.setCatchBackKey(true);
}
	  public void hide () {  
		  worldRenderer.dispose();  
		  Gdx.input.setCatchBackKey(false); 
}}