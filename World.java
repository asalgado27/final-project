import java.awt.Graphics;

public class World{
    int height;
    int width;
    int pointsLeft = 0;
    int pointsRight = 0;
    Person person = new Person();
    
    public World(int initWidth, int initHeight){
        width = initWidth;
        height = initHeight;
    }

    public void drawPerson(Graphics g){
        person.draw(g);
    }

    public void updatePerson(double time){
        person.update(this, time);
    }
}
