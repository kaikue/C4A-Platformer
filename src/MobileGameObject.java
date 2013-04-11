import java.util.ArrayList;
import org.newdawn.slick.opengl.*;

public class MobileGameObject extends GameObject {
    
    private int speed;
    private ArrayList<GameObject> collisions;
    
    public MobileGameObject(Texture texture, int x, int y, int speed) {
        super(texture, x, y);
        this.speed = speed;
        collisions = new ArrayList<GameObject>();
    }
    
    public void checkCollisions(ArrayList<GameObject> objects) {
        collisions = new ArrayList<GameObject>();
        for(GameObject object : objects) {
            if(getBoundingBox().intersects(object.getBoundingBox()) && !object.equals(this)) {
                collisions.add(object);
            }
        }
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public ArrayList<GameObject> getCollisions() {
        return collisions;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public void setCollisions(ArrayList<GameObject> collisions) {
        this.collisions = collisions;
    }
}
