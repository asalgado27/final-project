class Pair{
    public double x;
    public double y;
     
    public Pair(double x, double y){
    	this.x = x;
    	this.y = y;
    }
 
    public Pair add(Pair toAdd){
    	return new Pair(x + toAdd.x, y + toAdd.y);
    }

    public Pair subtract(Pair toSubtract){
    	return new Pair(x - toSubtract.x, y - toSubtract.y);
    }
 
    public Pair divide(double denom){
    	return new Pair(x / denom, y / denom);
    }
 
    public Pair times(double val){
    	return new Pair(x * val, y * val);
    }
 
    public void flipX(){
    	x = -x;
    }
     
    public void flipY(){
    	y = -y;
    }

    public void swap() {
        double xCopy = x;
        x = y;
        y = xCopy;
    }

    
    public String toString() {
        return "(" + x + ", " + y + ")";
    }


    // Returns new velocity value based on rebound
    public Pair rebound(double radian) {
        // Establish variables to keep track of proper signs
        int posNegX = 1;
        int posNegY = 1;

        // Change sign of x
        if (x > 0) {
            posNegX *= -1;
        }

        // Change sign of y
        if (radian < 0) {
            posNegY *= -1;
        }

        // keep track of initial speed
        double initSpeed = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

        double outputX = initSpeed * Math.cos(radian) * posNegX;
        double outputY = initSpeed * Math.sin(Math.abs(radian)) * posNegY;

        return new Pair(outputX, outputY);
    }

    // Make a copy of Pair object
    public Pair copy() {
        return new Pair(this.x, this.y);
    }
    
}