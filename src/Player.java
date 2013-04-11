import org.newdawn.slick.opengl.*;

public class Player extends MobileGameObject {
    
    private boolean onGround;
    
    public Player(Texture texture, int x, int y, int speed) {
        super(texture, x, y, speed);
        onGround = false;
    }
    
    @Override
    public void update() {
        double[] vec = getVec();
        if(!onGround) {
            vec[1] += 0.1;
        }
        if(vec[1] > Engine.MAX_VEL) {
            vec[1] = Engine.MAX_VEL;
        }
        setVec(vec);
        super.update();
    }
    
    public boolean getOnGround() {
        return onGround;
    }
    
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
