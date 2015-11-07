package mkr.mesh;

/**
 * Simple class point
 */
public class Point {
    private double X;
    private double Y;

    public Point(double X , double Y){
        this.X = X;
        this.Y = Y;
    }

    public double X(){return X;}
    public double Y(){return Y;}
    public double distance(Point point){
        return Math.sqrt(Math.pow(X - point.X() , 2) + Math.pow(Y - point.Y() , 2));
    }
}
