import java.io.IOException;

import org.lwjgl.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import org.newdawn.slick.*;
import org.newdawn.slick.opengl.*;
import org.newdawn.slick.util.*;

public class Engine {
    
    private Texture texture;
    private int bumbuuX, bumbuuY, bumbuuSpeed;
    private boolean wPressed, aPressed, sPressed, dPressed; 
    
    public void start() {
        initGL(800,600);
        init();
        
        while (true) {
            update();
            
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            render();
            Display.update();
            Display.sync(100);
            
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
        wPressed = false;
        aPressed = false;
        sPressed = false;
        dPressed = false;
        bumbuuX = 100;
        bumbuuY = 100;
        bumbuuSpeed = 4;
        
        try {
            //load texture from PNG file
            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/image.png"));
 
            System.out.println("Texture loaded: " + texture);
            System.out.println(">> Image width: " + texture.getImageWidth());
            System.out.println(">> Image height: " + texture.getImageHeight());
            System.out.println(">> Texture width: " + texture.getTextureWidth());
            System.out.println(">> Texture height: " + texture.getTextureHeight());
            System.out.println(">> Texture ID: " + texture.getTextureID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void render() {
        Color.white.bind(); //change white to different colors for a rainbow of Bumbuu bees!
        texture.bind(); // or GL11.glBind(texture.getTextureID());
 
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(bumbuuX, bumbuuY);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(bumbuuX + texture.getTextureWidth(), bumbuuY);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(bumbuuX + texture.getTextureWidth(), bumbuuY + texture.getTextureHeight());
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(bumbuuX, bumbuuY + texture.getTextureHeight());
        GL11.glEnd();
    }
    
    public void update() {
        pollInput();
        if(wPressed) {
            bumbuuY -= bumbuuSpeed;
            System.out.println("Buzz");
        }
        if(sPressed) {
            bumbuuY += bumbuuSpeed;
            System.out.println("Buzz");
        }
        if(aPressed) {
            bumbuuX -= bumbuuSpeed;
            System.out.println("Buzz");
        }
        if(dPressed) {
            bumbuuX += bumbuuSpeed;
            System.out.println("Buzz");
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
    }
    
    public static void main(String[] argv) {
        Engine displayExample = new Engine();
        displayExample.start();
    }
}