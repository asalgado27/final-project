// basic structure. Will work out the details when we need to create objects.

import java.awt.Color;
import java.awt.Graphics;

public abstract class Item {
    Pair position;
    Color color;
    Main main;
    Person person;
    boolean visible;

    public Item(/*Main main, Person person*/) {
        // this.main = main;
        //this.person = person;
        // main.uncollectedItems.add(this);
        visible = true;
    }

    public void draw(Graphics g) {}

    public void checkAndCollect() {
        person.inventory.add(this);
        main.uncollectedItems.remove(this);
        visible = false;
    }
	// item disappears (or pops, or does some motion) when the person collects it

    public void use() {
        person.inventory.remove(this);
        
    } 
	// unleash powers of the item
	// remove from inventory if item is inventory; or, if item is immediate-use, immediately use
}
