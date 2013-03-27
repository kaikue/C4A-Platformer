import org.lwjgl.*;
import org.lwjgl.opengl.*;

public class Engine {
    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(800,600));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        // init OpenGL here
        while (!Display.isCloseRequested()) {
            // render OpenGL here
            Display.update();
        }
        Display.destroy();
        //cleanup
        System.exit(0);
    }
    public static void main(String[] argv) {
        Engine displayExample = new Engine();
        displayExample.start();
    }
}