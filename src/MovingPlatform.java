import org.newdawn.slick.opengl.*;


public class MovingPlatform extends MobileGameObject {
    
    private int pathLength;
    private int currentStep;
    private boolean forward;
    private double[] direction;
    
    public MovingPlatform(Texture texture, int x, int y, int speed, int pathLength, double[] direction) {
        super(texture, x, y, speed, true);
        this.pathLength = pathLength;
        currentStep = 0;
        forward = true;
        this.direction = direction;
        double[] vec = direction;
        setVec(vec);
    }
    
    @Override
    public void update() {
        if(currentStep >= pathLength) {
            forward = false;
            double[] vec = {-direction[0], -direction[1]};
            setVec(vec);
        }
        else if(currentStep <= 0) {
            forward = true;
            double[] vec = {direction[0], direction[1]};
            setVec(vec);
        }
        if(forward) {
            currentStep++;
        }
        else {
            currentStep--;
        }
        super.update();
    }
    
    public int getPathLength() {
        return this.pathLength;
    }
    
    public void getPathLength(int pathLength) {
        this.pathLength = pathLength;
    }

}
