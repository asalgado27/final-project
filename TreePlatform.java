import java.awt.Graphics;

public class TreePlatform extends Platform {
    boolean canDisappear;
    int counter = 0;
    // Stores the position value for when the tree platform has to return to its normal position
    Pair positionHolder;
    Pair positionGone;

    // Constructs the base platform of the Tree Biome that does not disappear
    public TreePlatform(World world, Pair position, Pair dimensions){
        super(world, position, dimensions);
        positionHolder = position;
        positionGone = new Pair(50000,50000);
        canDisappear = true;
    }

    // Constructs the disappearing platforms of the Tree Biome
    public TreePlatform(World world, Pair position, Pair dimensions, boolean canDisappear){
        super(world, position, dimensions);
        positionHolder = position;
        positionGone = new Pair(50000,50000);
        canDisappear = false;
    }

    @Override
    // Draws the Tree Platform
    public void draw(Graphics g) {
        // Increases counter if it is a tree platform, if it can disappear, and if the person is on the platform.
        if (canDisappear){
            if (personHere){
                counterPlus();
            }
            if (!personHere && visible == false){
                counterPlus();
            }
            if (counter >=80 && counter <= 249){
                setVisibleFalse();
            }
            if (counter >=250){
                setVisibleTrue();
            }
        }
        if (visible){
            super.draw(g);
        }
    
        
    }

    @Override
    // Counter should only increase if it is within a TreePlatform
    public void counterPlus(){
        counter++;
    }
    
    // Sets position far away from the screen so it is not visible
    public void setVisibleFalse(){
        visible = false;
        position = positionGone;
    }

    // Sets position back to where it once was
    public void setVisibleTrue(){
        visible = true;
        position = positionHolder;
        counter = 0;
    }
}
