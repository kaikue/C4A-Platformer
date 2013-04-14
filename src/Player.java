import org.newdawn.slick.opengl.*;

public class Player extends MobileGameObject {
    
    public Player(Texture texture, int x, int y, int speed) {
        super(texture, x, y, speed);
    }
    
    @Override
    public void update() {
        double[] vec = getVec();
        //if(!onGround) {
            vec[1] += 0.1; //I have no idea why this works properly, but it does
        //}
        if(vec[1] > Engine.MAX_VEL) {
            vec[1] = Engine.MAX_VEL;
        }
        setVec(vec);
        super.update();
    }
}
