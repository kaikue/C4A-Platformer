import org.newdawn.slick.opengl.Texture;


public class StaticGameObject extends GameObject {

    public StaticGameObject(Texture texture, int x, int y, boolean solid) {
        super(texture, x, y, solid);
    }
    public StaticGameObject(Texture texture, int x, int y) {
        this(texture, x, y, true);
    }
    

}
