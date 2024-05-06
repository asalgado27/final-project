import java.util.Random;

public class SkyPlatform extends Platform {
    // extra Fields indicating velocity and acceleration of the platform
    Pair velocity = new Pair(0, 0); // initial upward/downward velocity is 0
    Pair acceleration = new Pair(0, 0); // constant acceleration
    Random rand = new Random();

    // Constructs a Sky Platform
    public SkyPlatform(World world, Pair position, Pair dimensions) {
        super(world, position, dimensions);
    }

    @Override
    // Updates the Sky Platform every frame
    public void update(double time){
        position = position.add(velocity.times(time));
    
        // Continually update sky-platform's position
        double newRandomFloat = rand.nextFloat();
        if (newRandomFloat < 0.01) {
            setVelocityY(rand.nextInt(25) - 50);
            acceleration = new Pair(0, -50);
        }
        else if (newRandomFloat > 0.99) {
            setVelocityY(rand.nextInt(25) + 50);
            acceleration = new Pair(0, 50);
        }

        // Check if platform is within the y-bounds of its movement
        if (position.y <= 600 && position.y >= 150){
            velocity = velocity.add(acceleration.times(time));
        }

        else if (position.y < 150) {
            setVelocityY(0);
            this.setPosition(new Pair(position.x, 150));
        }

        else if (position.y > 600){
            setVelocityY(0);
            this.setPosition(new Pair(position.x, 600));
        }
    }

    public void setPosition(Pair position){
        this.position = position;
    }
    public void setVelocityX(int newXVelocity){
        this.velocity = new Pair(newXVelocity, this.velocity.y);
    }
    public void setVelocityY(int newYVelocity){
        this.velocity = new Pair(velocity.x, newYVelocity);
    }
    public void setAcceleration(int newAcceler) {
        this.acceleration = new Pair(this.acceleration.x, newAcceler);
    }
}
