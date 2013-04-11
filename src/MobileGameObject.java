//import java.util.ArrayList;
import org.newdawn.slick.opengl.*;

public class MobileGameObject extends GameObject {
    
    private int speed;
    private double[] vec;
    
    public MobileGameObject(Texture texture, int x, int y, int speed) {
        super(texture, x, y);
        this.speed = speed;
        vec = new double[2];
    }
    
    public void move() {
        //that should probably be a real vector?
        setX(getX() + (int)(getSpeed() * vec[0]));
        setY(getY() + (int)(getSpeed() * vec[1]));
    }
    
    @Override
    public void update() {
        move();
        super.update();
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
