import java.awt.Color;
import java.awt.Graphics;

public class TreePlatform extends Platform {
    int counter = 0;
    //to store the position value for when the tree platform has to return to its normal position
    Pair positionHolder;
    Pair positionGone;

    public TreePlatform(World world, Pair position, Pair dimensions){
        super(world, position, dimensions);
        positionHolder = position;
        positionGone = new Pair(50000,50000);
    }

    @Override
    public void draw(Graphics g) {
        //increases counter if it is a tree platform and if the person is on the platform.
        System.out.println(personHere);
        if (personHere){
            counterPlus();
        }
        if (counter >=80 && counter <=249){
            setVisibleFalse();
        }
        if (counter >=250){
            setVisibleTrue();
        }
        if (visible){
            super.draw(g);
        }
    }
    @Override
    public void counterPlus(){
        counter++;
        System.out.println("in override " + counter);
    }

    public void setVisibleFalse(){
        visible = false;
        position = positionGone;
    }

    public void setVisibleTrue(){
        visible = true;
        position = positionHolder;
        counter = 0;
    }
}
