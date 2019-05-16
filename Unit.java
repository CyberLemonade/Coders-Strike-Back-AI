import java.util.*;

class Unit {
    double x, y, r;
    
    Unit(double x,double y,double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
    
    boolean collides(Unit u) {
        return distance2(u) < (r + u.r) * (r + u.r);
    }
    
    double distance2(Unit u) {
        return (x - u.x) * (x - u.x) + (y - u.y) * (y - u.y);
    }
    
    double distance(Unit u) {
        return Math.sqrt(distance2(u));
    }
}