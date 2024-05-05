
import java.awt.Color;
import java.awt.Graphics;

public abstract class Item {
    Pair position;
    Color color;
    Main main;
    Person person;
    boolean visible;

    int index;

    public Item(Main main, Person person) {
        this.main = main;
        this.person = person;
        visible = true;
    }

    public void draw(Graphics g) {}

    public void checkAndCollect() {
        visible = false;
    }
	
    // Item disappears (or pops, or does some motion) when the person collects it
    public void use() {
        if (this instanceof Key){
            person.keyInventory.remove(this.index);
        }
        
    } 
}
