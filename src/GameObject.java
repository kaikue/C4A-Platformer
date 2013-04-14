import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.*;

public abstract class GameObject {
    
    private int x, y, width, height, texWidth, texHeight;
    private Rectangle boundingBox;
    private Texture texture;
    private boolean solid;
    
    public GameObject(Texture texture, int x, int y, boolean solid) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.solid = solid;
        width = texture.getImageWidth();
        height = texture.getImageHeight();
        texWidth = texture.getTextureWidth();
        texHeight = texture.getTextureHeight();
        boundingBox = new Rectangle(x, y, width + 1, height + 1);
    }
    
    public void update() {
    }
    
    public void draw() {
        Color.white.bind(); //change white to different colors to tint the image
        texture.bind(); // or GL11.glBind(texture.getTextureID());
        //GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(x, y);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(x + texWidth, y);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(x + texWidth, y + texHeight);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(x, y + texHeight);
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
    
    public boolean isSolid() {
        return solid;
    }
    
    public void setX(int x) {
        this.x = x;
        boundingBox.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
        boundingBox.y = y;
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
    
    public void setSolid(boolean solid) {
        this.solid = solid;
    }
}
