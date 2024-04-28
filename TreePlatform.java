import java.awt.Color;
import java.awt.Graphics;

public class TreePlatform extends Platform {
    boolean visible = true;
    int counter = 0;
    public TreePlatform(World world, Pair position, Pair dimensions){
        super(world, position, dimensions);
    }

    @Override
    public void draw(Graphics g) {
        if (counter >=20){
            visible = false;
        }
        if (counter >=40){
            visible = false;
            counter = 0;
        }
        if (visible){
            super.draw(g);
        }
    }
    @Override
    public void counterPlus(){
        counter++;
        System.out.println(counter);
    }
}
