import org.newdawn.slick.opengl.*;

public class Player extends MobileGameObject {
    
    private double gravity;
    private boolean onGround;
    
    public Player(Texture texture, int x, int y, int speed) {
        super(texture, x, y, speed);
        onGround = false;
    }
    
    public void move(double[] vec) {
        //that should probably be a real vector?
        setX(getX() + (int)(getSpeed() * vec[0]));
        setY(getY() + (int)(getSpeed() * vec[1]));
    }
    
    @Override
    public void update() {
        if(getCollisions().size() > 0) {
            onGround = true; //probably not but this can be fixed later
        }
        else {
            onGround = false;
        }
        if(onGround) {
            gravity = 0;
        }
        else {
            gravity += 0.1;
        }
        double[] vec = {0, gravity};
        move(vec);
        
        super.update();
    }
    
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
