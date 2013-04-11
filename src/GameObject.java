import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.*;

public abstract class GameObject {
    
    private int x, y, width, height;
    private Rectangle boundingBox;
    private Texture texture;
    
    public GameObject(Texture texture, int x, int y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        width = texture.getImageWidth();
        height = texture.getImageHeight();
        boundingBox = new Rectangle(x, y, width + 1, height + 1);
    }
    
    public void update() {
        boundingBox.x = x;
        boundingBox.y = y;
    }
    
    public void draw() {
        Color.white.bind(); //change white to different colors to tint the image
        texture.bind(); // or GL11.glBind(texture.getTextureID());
        //GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(x + width, y);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(x + width, y + height);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(x, y + height);
        GL11.glEnd();
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public Rectangle getBoundingBox() {
        return boundingBox;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }
}
