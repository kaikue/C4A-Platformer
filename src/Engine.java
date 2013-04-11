import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import org.newdawn.slick.*;
import org.newdawn.slick.opengl.*;
import org.newdawn.slick.util.*;

public class Engine {
    
    //private boolean wPressed, aPressed, sPressed, dPressed;
    private ArrayList<GameObject> objects;
    private Player player;
    
    public void start() {
        initGL(800,600);
        init();
        
        while(true) {
            update();
            render();
            if (Display.isCloseRequested()) {
                Display.destroy();
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
        //wPressed = false;
        //aPressed = false;
        //sPressed = false;
        //dPressed = false;
        objects = new ArrayList<GameObject>();
        
        Texture texPlayer = null;
        try {
            texPlayer = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/image.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        player = new Player(texPlayer, 100, 100, 4);
        objects.add(player);
        
        Texture texFloor = null;
        try {
            texFloor = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/floor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        GameObject floor = new GameObject(texFloor, 100, 400);
        objects.add(floor);
    }
    
    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        for(GameObject obj : objects) {
            obj.draw();
        }
        
        Display.update();
        Display.sync(100);
    }
    
    public void update() {
        //move the player
        pollInput();
        double[] playerVec = new double[2];
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
            playerVec[1] = -2; //this resets when w is released...
        }
        //if(sPressed) {
        //    playerVec[1] = 1;
        //}
        if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
            playerVec[0] = -1;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
            playerVec[0] = 1;
        }
        player.move(playerVec);
        
        //update the objects
        for(GameObject object : objects) {
            if(object instanceof MobileGameObject) {
                MobileGameObject mobObject = (MobileGameObject)object;
                mobObject.checkCollisions(objects);
            }
            object.update();
        }
    }
    
    public void pollInput() {
        if (Mouse.isButtonDown(0)) {
            int x = Mouse.getX();
            int y = Mouse.getY();
     
            System.out.println("MOUSE DOWN @ X: " + x + " Y: " + y);
        }
     
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            System.out.println("SPACE KEY IS DOWN");
        }
        
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            System.exit(0);
        }
        /*
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_W) {
                    //System.out.println("W Key Pressed");
                    wPressed = true;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                    //System.out.println("A Key Pressed");
                    aPressed = true;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_S) {
                    //System.out.println("S Key Pressed");
                    sPressed = true;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                    //System.out.println("D Key Pressed");
                    dPressed = true;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                    System.exit(0);
                }
            } else {
                if (Keyboard.getEventKey() == Keyboard.KEY_W) {
                    //System.out.println("W Key Released");
                    wPressed = false;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                    //System.out.println("A Key Released");
                    aPressed = false;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_S) {
                    //System.out.println("S Key Released");
                    sPressed = false;
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                    //System.out.println("D Key Released");
                    dPressed = false;
                }
            }
        }
        */
    }
    
    public static void main(String[] argv) {
        Engine displayExample = new Engine();
        displayExample.start();
    }
}