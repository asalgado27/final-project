// basic structure. Will work out the details when we need to create objects.

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
        main.uncollectedItems.add(this);
        visible = true;
    }

    public void draw(Graphics g) {}

    public void checkAndCollect() {
        person.inventory.add(this);
        if (this instanceof Key && person.keyInventory.contains(this.index) == false){
            person.keyInventory.add(this.index);
        }
        main.uncollectedItems.remove(this);
        visible = false;
    }
	// item disappears (or pops, or does some motion) when the person collects it

    public void use() {
        person.inventory.remove(this);
        if (this instanceof Key){
            person.keyInventory.remove(this.index);
        }
        
    } 
	// unleash powers of the item
	// remove from inventory if item is inventory; or, if item is immediate-use, immediately use
}
