// basic structure. Will work out the details when we need to create objects.

public abstract class Item() {
	Pair position = new Pair();
	Color color = new Color();

	public Item() {}

	public void collect() {}
		// item disappears (or pops, or does some motion) when the person collects it

	public void reset() {}
		// put back in the place it should be

	public void use() {} 
		// unleash powers of the item
		// remove from inventory if item is inventory; or, if item is immediate-use, immediately use
}