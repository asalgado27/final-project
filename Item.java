
import java.awt.Color;
import java.awt.Graphics;

// This class was originally created because we wanted to have many types of Items, such as powerups as well as Keys.
// Unfortunately, we only had time to fully implement Key, which is the sole child class of Item.

public abstract class Item {
    Pair position;
    Color color;
    Main main;
    Person person;
    boolean visible;

    int index;

    // Constructs the Item object
    public Item(Main main, Person person) {
        this.main = main;
        this.person = person;
        visible = true;
    }

    // Requires that all classes that extends Item have a draw method
    public void draw(Graphics g) {}

    // Requires that all classes that extends Item have a checkAndCollect method
    public void checkAndCollect() {}
	
    // Item disappears (or pops, or does some motion) when the person collects it
    public void use() {
        if (this instanceof Key){
            person.keyInventory.remove(this.index);
        }
        
    } 
}
