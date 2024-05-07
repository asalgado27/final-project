
import java.awt.Color;
import java.awt.Graphics;

// This class was originally created because we wanted to have many types of Items, such as powerups as well as Keys.
// Unfortunately, we only had time to fully implement Key, which is the sole child class of Item.

public interface Item {

    // Requires that all classes that extends Item have a draw method
    default void draw(Graphics g) {}

    // Requires that all classes that extends Item have a checkAndCollect method
    default void checkAndCollect() {}
	
    // Item disappears (or pops, or does some motion) when the person collects it
    default void use() {} 
}
