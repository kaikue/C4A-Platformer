//import java.util.ArrayList;
import org.newdawn.slick.opengl.*;

public class MobileGameObject extends GameObject {
    
    private int speed;
    private double[] vec; //that should probably be a real vector?
    
    public MobileGameObject(Texture texture, int x, int y, int speed, boolean solid) {
        super(texture, x, y, solid);
        this.speed = speed;
        vec = new double[2];
    }
    
    public MobileGameObject(Texture texture, int x, int y, int speed) {
        this(texture, x, y, speed, true);
    }
    
    public void moveX() {
        setX(getX() + (int)vec[0]);
    }
    
    public void moveY() {
        setY(getY() + (int)vec[1]);
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public double[] getVec() {
        return vec;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public void setVec(double[] vec) {
        this.vec = vec;
    }
}
