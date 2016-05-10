package com.rabbitrock.game;

import com.badlogic.gdx.math.Rectangle;
import com.rabbitrock.game.objects.BunnyHead;
import com.rabbitrock.game.objects.BunnyHead 
.JUMP_STATE;
import com.rabbitrock.game.objects.Feather;
import com.rabbitrock.game.objects.GoldCoin;
import com.rabbitrock.game.objects.Rock;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.rabbitrock.util.CameraHelper;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class WorldController extends InputAdapter {
	 private static final String TAG = WorldController.class.getName();
	// Rectangles for collision detection
	 private Rectangle r1 = new Rectangle();
	 private Rectangle r2 = new Rectangle();
	 private void onCollisionBunnyHeadWithRock (Rock rock) {
		  BunnyHead bunnyHead = level.bunnyHead;
		  float heightDifference = Math.abs(bunnyHead.position.y  
		 - (  rock.position.y + rock.bounds.height));
		  if (heightDifference > 0.25f) {
		    boolean hitRightEdge = bunnyHead.position.x > (  
		rock.position.x + rock.bounds.width / 2.0f);
		    if (hitRightEdge) {
		      bunnyHead.position.x = rock.position.x + rock.bounds.width;
		    } else {
		      bunnyHead.position.x = rock.position.x -  
		bunnyHead.bounds.width;
		    }
		    return;
		  }
		  switch (bunnyHead.jumpState) {
		    case GROUNDED:
		      break;
		    case FALLING:
		    case JUMP_FALLING:
		      bunnyHead.position.y = rock.position.y +  
		bunnyHead.bounds.height  + bunnyHead.origin.y;
		      bunnyHead.jumpState = JUMP_STATE.GROUNDED;
		      break;
		    case JUMP_RISING:
		      bunnyHead.position.y = rock.position.y +  
		bunnyHead.bounds.height + bunnyHead.origin.y;
		    break;
		  }
		}
	 private void onCollisionBunnyWithGoldCoin (GoldCoin goldcoin) {
		  goldcoin.collected = true;
		  score += goldcoin.getScore();
		  Gdx.app.log(TAG, "Gold coin collected");
		}
	 private void onCollisionBunnyWithFeather (Feather feather) {
		  feather.collected = true;
		  score += feather.getScore();
		  level.bunnyHead.setFeatherPowerup(true);
		  Gdx.app.log(TAG, "Feather collected");
		}
	 private void testCollisions () {
	   r1.set(level.bunnyHead.position.x, level.bunnyHead.position.y,  
	 level.bunnyHead.bounds.width, level.bunnyHead.bounds.height);
	   // Test collision: Bunny Head <-> Rocks
	   for (Rock rock : level.rocks) {
	     r2.set(rock.position.x, rock.position.y, rock.bounds.width,  
	 rock.bounds.height);
	     if (!r1.overlaps(r2)) continue;
	     onCollisionBunnyHeadWithRock(rock);
	     // IMPORTANT: must do all collisions for valid
	     // edge testing on rocks.
	   }
	   // Test collision: Bunny Head <-> Gold Coins
	   for (GoldCoin goldcoin : level.goldcoins) {
	     if (goldcoin.collected) continue;
	     r2.set(goldcoin.position.x, goldcoin.position.y,  
	 goldcoin.bounds.width, goldcoin.bounds.height);
	     if (!r1.overlaps(r2)) continue;
	     onCollisionBunnyWithGoldCoin(goldcoin);
	     break;
	   }
	   // Test collision: Bunny Head <-> Feathers
	   for (Feather feather : level.feathers) {
	     if (feather.collected) continue;
	     r2.set(feather.position.x, feather.position.y,  
	 feather.bounds.width, feather.bounds.height);
	     if (!r1.overlaps(r2)) continue;
	     onCollisionBunnyWithFeather(feather);
	     break;
	   }
	 }

     public Sprite[] testSprites;
     public int selectedSprite;

     public CameraHelper cameraHelper;

     public WorldController () {
             init();
     }

     private void init () {
             Gdx.input.setInputProcessor(this);
             cameraHelper = new CameraHelper();
             initTestObjects();
     }

     private void initTestObjects () {
             // Create new array for 5 sprites
             testSprites = new Sprite[5];
             // Create a list of texture regions
             Array<TextureRegion> regions = new Array<TextureRegion>();
             regions.add(Assets.instance.bunny.head);
             regions.add(Assets.instance.feather.feather);
             regions.add(Assets.instance.goldCoin.goldCoin);
             // Create new sprites using a random texture region
             // Create new sprites using the just created texture
             for (int i = 0; i < testSprites.length; i++) {
                     Sprite spr = new Sprite(regions.random());
                     // Define sprite size to be 1m x 1m in game world
                     spr.setSize(1, 1);
                     // Set origin to spriteÕs center
                     spr.setOrigin(spr.getWidth() / 2.0f, spr.getHeight() / 2.0f);
                     // Calculate random position for sprite
                     float randomX = MathUtils.random(-2.0f, 2.0f);
                     float randomY = MathUtils.random(-2.0f, 2.0f);
                     spr.setPosition(randomX, randomY);
                     // Put new sprite into array
                     testSprites[i] = spr;
             }
             // Set first sprite as selected one
             selectedSprite = 0;
     }

     private Pixmap createProceduralPixmap (int width, int height) {
             Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
             // Fill square with red color at 50% opacity
             pixmap.setColor(1, 0, 0, 0.5f);
             pixmap.fill();
             // Draw a yellow-colored X shape on square
             pixmap.setColor(1, 1, 0, 1);
             pixmap.drawLine(0, 0, width, height);
             pixmap.drawLine(width, 0, 0, height);
             // Draw a cyan-colored border around square
             pixmap.setColor(0, 1, 1, 1);
             pixmap.drawRectangle(0, 0, width, height);
             return pixmap;
     }

     public void update (float deltaTime) {
    	  handleDebugInput(deltaTime);
    	  level.update(deltaTime);
    	  testCollisions();
    	  cameraHelper.update(deltaTime);
    	}

     private void updateTestObjects (float deltaTime) {
             // Get current rotation from selected sprite
             float rotation = testSprites[selectedSprite].getRotation();
             // Rotate sprite by 90 degrees per second
             rotation += 90 * deltaTime;
             // Wrap around at 360 degrees
             rotation %= 360;
             // Set new rotation value to selected sprite
             testSprites[selectedSprite].setRotation(rotation);
     }

     private void handleDebugInput (float deltaTime) {
             if (Gdx.app.getType() != ApplicationType.Desktop) return;

             // Selected Sprite Controls
             float sprMoveSpeed = 5 * deltaTime;
             if (Gdx.input.isKeyPressed(Keys.A)) moveSelectedSprite(-sprMoveSpeed, 0);
             if (Gdx.input.isKeyPressed(Keys.D)) moveSelectedSprite(sprMoveSpeed, 0);
             if (Gdx.input.isKeyPressed(Keys.W)) moveSelectedSprite(0, sprMoveSpeed);
             if (Gdx.input.isKeyPressed(Keys.S)) moveSelectedSprite(0, -sprMoveSpeed);

             // Camera Controls (move)
             float camMoveSpeed = 5 * deltaTime;
             float camMoveSpeedAccelerationFactor = 5;
             if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *= camMoveSpeedAccelerationFactor;
             if (Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed, 0);
             if (Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed, 0);
             if (Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
             if (Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0, -camMoveSpeed);
             if (Gdx.input.isKeyPressed(Keys.BACKSPACE)) cameraHelper.setPosition(0, 0);

             // Camera Controls (zoom)
             float camZoomSpeed = 1 * deltaTime;
             float camZoomSpeedAccelerationFactor = 5;
             if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *= camZoomSpeedAccelerationFactor;
             if (Gdx.input.isKeyPressed(Keys.COMMA)) cameraHelper.addZoom(camZoomSpeed);
             if (Gdx.input.isKeyPressed(Keys.PERIOD)) cameraHelper.addZoom(-camZoomSpeed);
             if (Gdx.input.isKeyPressed(Keys.SLASH)) cameraHelper.setZoom(1);
     }

     private void moveSelectedSprite (float x, float y) {
             testSprites[selectedSprite].translate(x, y);
     }
     private void moveCamera (float x, float y) {
         x += cameraHelper.getPosition().x;
         y += cameraHelper.getPosition().y;
         cameraHelper.setPosition(x, y);
 }

 @Override
 public boolean keyUp (int keycode) {
         // Reset game world
         if (keycode == Keys.R) {
                 init();
                 Gdx.app.debug(TAG, "Game world resetted");
         }
         // Select next sprite
         else if (keycode == Keys.SPACE) {
                 selectedSprite = (selectedSprite + 1) % testSprites.length;
                 // Update camera's target to follow the currently
                 // selected sprite
                 if (cameraHelper.hasTarget()) {
                         cameraHelper.setTarget(testSprites[selectedSprite]);
                 }
                 Gdx.app.debug(TAG, "Sprite #" + selectedSprite + " selected");
         }
         // Toggle camera follow
         else if (keycode == Keys.ENTER) {
                 cameraHelper.setTarget(cameraHelper.hasTarget() ? null : testSprites[selectedSprite]);
                 Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
         }
         return false;
 }

//Rectangles for collision detection
private Rectangle r1 = new Rectangle();

private Rectangle r2 = new Rectangle();

private void onCollisionBunnyHeadWithRock(Rock rock) {
	  BunnyHead bunnyHead = level.bunnyHead;
	  float heightDifference = Math.abs(bunnyHead.position.y  
	 - (  rock.position.y + rock.bounds.height));
	  if (heightDifference > 0.25f) {
	    boolean hitRightEdge = bunnyHead.position.x > (  
	rock.position.x + rock.bounds.width / 2.0f);
	    if (hitRightEdge) {
	      bunnyHead.position.x = rock.position.x + rock.bounds.width;
	    } else {
	      bunnyHead.position.x = rock.position.x -  
	bunnyHead.bounds.width;
	    }
	    return;
	  }
	  switch (bunnyHead.jumpState) {
	    case GROUNDED:
	      break;
	    case FALLING:
	    case JUMP_FALLING:
	      bunnyHead.position.y = rock.position.y +  
	bunnyHead.bounds.height  + bunnyHead.origin.y;
	      bunnyHead.jumpState = JUMP_STATE.GROUNDED;
	      break;
	    case JUMP_RISING:
	      bunnyHead.position.y = rock.position.y +  
	bunnyHead.bounds.height + bunnyHead.origin.y;
	    break;
	  }
};

private void onCollisionBunnyWithGoldCoin(GoldCoin goldcoin) {
	  goldcoin.collected = true;
	  score += goldcoin.getScore();
	  Gdx.app.log(TAG, "Gold coin collected");
};
private void onCollisionBunnyWithFeather(Feather feather) {
	  feather.collected = true;
	  score += feather.getScore();
	  level.bunnyHead.setFeatherPowerup(true);
	  Gdx.app.log(TAG, "Feather collected");
};
private void testCollisions () {
r1.set(level.bunnyHead.position.x, level.bunnyHead.position.y,  
level.bunnyHead.bounds.width, level.bunnyHead.bounds.height);
// Test collision: Bunny Head <-> Rocks
for (Rock rock : level.rocks) {
  r2.set(rock.position.x, rock.position.y, rock.bounds.width,  
rock.bounds.height);
  if (!r1.overlaps(r2)) continue;
  onCollisionBunnyHeadWithRock(rock);
  // IMPORTANT: must do all collisions for valid
  // edge testing on rocks.
}
// Test collision: Bunny Head <-> Gold Coins
for (GoldCoin goldcoin : level.goldcoins) {
  if (goldcoin.collected) continue;
  r2.set(goldcoin.position.x, goldcoin.position.y,  
goldcoin.bounds.width, goldcoin.bounds.height);
  if (!r1.overlaps(r2)) continue;
  onCollisionBunnyWithGoldCoin(goldcoin);
  break;
}
// Test collision: Bunny Head <-> Feathers
for (Feather feather : level.feathers) {
  if (feather.collected) continue;
  r2.set(feather.position.x, feather.position.y,  
feather.bounds.width, feather.bounds.height);
  if (!r1.overlaps(r2)) continue;
  onCollisionBunnyWithFeather(feather);
  break;
}
}
private void initLevel () {
	  score = 0;
	  level = new Level(Constants.LEVEL_01);
	  cameraHelper.setTarget(level.bunnyHead);
	}
}


