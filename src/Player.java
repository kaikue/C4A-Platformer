import org.newdawn.slick.opengl.*;

public class Player extends MobileGameObject {
    
    public Player(Texture texture, int x, int y, int speed) {
        super(texture, x, y, speed, false);
    }
    
    @Override
    public void update() {
        double[] vec = getVec();
        vec[1] += 0.1;
        if(vec[1] > Engine.MAX_VEL) {
            vec[1] = Engine.MAX_VEL;
        }
        setVec(vec);
        super.update();
    }
}
