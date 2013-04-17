import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.*;
import org.lwjgl.input.*;
import org.lwjgl.openal.*;
import org.lwjgl.opengl.*;
import org.newdawn.slick.openal.*;
import org.newdawn.slick.opengl.*;
import org.newdawn.slick.util.*;

public class Engine {
    
    private ArrayList<GameObject> objects;
    private Player player;
    public static final int STEP_HEIGHT = 20;
    public static final int MAX_VEL = 4;
    
    public void start() {
        initGL(800,600);
        init();
        
        while(true) {
            update();
            render();
            if (Display.isCloseRequested()) {
                Display.destroy();
                AL.destroy();
                System.exit(0);
            }
        }
    }
    
    private void initGL(int width, int height) {
        try {
            Display.setDisplayMode(new DisplayMode(width,height));
            Display.create();
            Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        GL11.glEnable(GL11.GL_TEXTURE_2D);               
 
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);          
 
        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
 
        GL11.glViewport(0,0,width,height);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
 
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
    
    public void init() {
        objects = new ArrayList<GameObject>();
        
        Texture texPlayer = null;
        try {
            texPlayer = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/image.png"));
            System.out.println("Texture loaded: "+texPlayer);
            System.out.println(">> Image width: "+texPlayer.getImageWidth());
            System.out.println(">> Image height: "+texPlayer.getImageHeight());
            System.out.println(">> Texture width: "+texPlayer.getTextureWidth());
            System.out.println(">> Texture height: "+texPlayer.getTextureHeight());
            System.out.println(">> Texture ID: "+texPlayer.getTextureID());
        } catch (IOException e) {
            e.printStackTrace();
        }
        player = new Player(texPlayer, 100, 250, 4);
        objects.add(player);
        
        Texture texFloor = null;
        try {
            texFloor = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/floor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StaticGameObject floor = new StaticGameObject(texFloor, 100, 100);
        objects.add(floor);
        //StaticGameObject ceiling = new StaticGameObject(texFloor, 100, 100);
        //objects.add(ceiling);
        HorizontalMovingPlatform p = new HorizontalMovingPlatform(texFloor, 100, 400, 4, 50);
        objects.add(p);
        
        Texture texWall = null;
        try {
            texWall = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/wall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StaticGameObject wall1 = new StaticGameObject(texWall, -50, 50);
        objects.add(wall1);
        StaticGameObject wall2 = new StaticGameObject(texWall, 550, 50, false);
        objects.add(wall2);
        
        /*
        Audio snd = null;
        try {
            snd = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/music.wav"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        snd.playAsSoundEffect(1.0f, 1.0f, true);
        */
    }
    
    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        for(GameObject obj : objects) {
            //sort these somehow?
            obj.draw();
        }
        
        Display.update();
        Display.sync(100);
    }
    
    public void update() {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            System.exit(0);
        }
        pollInput();
        
        //set the player's vector based on keyboard input
        double[] playerVec = player.getVec();
        playerVec[0] = 0;
        GameObject objBelowPlayer = objBelowPlayer();
        if(objBelowPlayer instanceof HorizontalMovingPlatform) {
            playerVec[0] = ((HorizontalMovingPlatform)objBelowPlayer).getVec()[0];
        }
        System.out.println("Before input: " + Arrays.toString(playerVec));
        if((Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) && objBelowPlayer != null) {
            playerVec[1] = -2.5;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            playerVec[0] += -1;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            playerVec[0] += 1;
        }
        System.out.println("After: " + Arrays.toString(playerVec));
        player.setVec(playerVec);
        
        //update the objects
        for(GameObject object : objects) {
            object.update();
            if(object instanceof MobileGameObject) {
                //move it, check for collisions, move it back if necessary
                MobileGameObject mObject = (MobileGameObject)object;
                //System.out.println("\nUpdating " + mObject + "...");
                int oldX = mObject.getX();
                //System.out.println("Old X = " + oldX);
                mObject.moveX();
                //System.out.println("New X = " + mObject.getX());
                if(checkCollisions(mObject)) {
                    mObject.setX(oldX);
                    //System.out.println("Moved " + mObject + " back to X=" + oldX);
                    double[] vec = mObject.getVec();
                    double[] newVec = {0, vec[1]};
                    mObject.setVec(newVec);
                }
                
                int oldY = mObject.getY();
                //System.out.println("Old Y = " + oldY);
                mObject.moveY();
                //System.out.println("New Y = " + mObject.getY());
                if(checkCollisions(mObject)) {
                    mObject.setY(oldY);
                    //System.out.println("Moved " + mObject + " back to Y=" + oldY);
                    double[] vec = mObject.getVec();
                    double[] newVec = {vec[0], 0};
                    mObject.setVec(newVec);
                }
            }
        }
    }
    
    public GameObject objBelowPlayer() {
        //checks if a line 1 pixel beneath the player intersects a solid object, returns the first one it finds
        for(GameObject o : objects) {
            if(o.isSolid() && o.getBoundingBox().intersectsLine(player.getX(), player.getY() + player.getHeight() + 1, player.getX() + player.getWidth(), player.getY() + player.getHeight() + 1) && !o.equals(player)) {
                return o;
            }
        }
        return null;
    }
    
    public void pollInput() {
        if (Mouse.isButtonDown(0)) {
            int x = Mouse.getX();
            int y = Mouse.getY();
     
            System.out.println("MOUSE DOWN @ X: " + x + " Y: " + y);
        }
        
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
                    System.out.println("Space Key Pressed");
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                    System.exit(0);
                }
            } else {
                if (Keyboard.getEventKey() == Keyboard.KEY_SPACE) {
                    System.out.println("Space Key Released");
                }
            }
        }
    }
    
    public boolean checkCollisions(GameObject a) {
        ArrayList<GameObject[]> collisions = new ArrayList<GameObject[]>();
        for(GameObject b : objects) {
            if(a.getBoundingBox().intersects(b.getBoundingBox()) && !a.equals(b) && b.isSolid()) {
                GameObject[] col = {a, b};
                collisions.add(col);
            }
        }
        for(GameObject[] pair : collisions) {
            collide(pair[0], pair[1]);
        }
        return collisions.size() > 0;
    }
    
    public void collide(GameObject a, GameObject b) {
        //used to make special events happen upon collisions.
        //example:
        /*
        if(a instanceof Player && b instanceof Pickup) {
            //collect the pickup
        }
        else if(b instanceof Player && a instanceof Pickup) {
            //collect the pickup
        }
        */
    }
    
    public static void main(String[] argv) {
        Engine displayExample = new Engine();
        displayExample.start();
    }
}

/*
LIST OF BUGS:
Audio doesn't work (because I'm just using shady incompatible .jars)
Textures wrap slightly (problem with wrapping vs. clamping?)
Player can slide through walls while on moving platforms
Player jumping while not on moving platforms should still be relative to the last platform stood on

TO DO:
Levels (code and design- use Tiled?)
    Scrolling view
Vertical moving platforms
Enemies
Projectiles
*/