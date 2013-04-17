import org.newdawn.slick.opengl.*;


public class HorizontalMovingPlatform extends MobileGameObject {
    
    private int pathLength;
    private int currentStep;
    private boolean right;
    
    public HorizontalMovingPlatform(Texture texture, int x, int y, int speed, int pathLength) {
        super(texture, x, y, speed, true);
        this.pathLength = pathLength;
        currentStep = 0;
        right = true; //starts going right
        double[] vec = {1, 0};
        setVec(vec);
    }
    
    @Override
    public void update() {
        if(currentStep >= pathLength) {
            right = false;
            double[] vec = {-1, 0};
            setVec(vec);
        }
        else if(currentStep <= 0) {
            right = true;
            double[] vec = {1, 0};
            setVec(vec);
        }
        if(right) {
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
