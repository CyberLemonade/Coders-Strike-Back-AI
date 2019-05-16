import java.util.*;

class Map {
    static final double width = 40000.0;
    static  double height = 20000.0;
    static final double CPT_RADIUS = 1500.0;
    static final double POD_RADIUS = 250.0;
    static final double OBS_RADIUS = 1000.0;
    
    Unit[] cp;
    Unit[] obs;
    
    Map() {
        createCpt();
        createObs();
    }
    
    void createObs() {}
    
    void createCpt() {
        int num = 3 + (int)(Math.random()*4);
        cp = new Unit[num];
        for (int i = 0; i < num; i++) {
            cp[i] = getNewCp(i);
        }
    }
    
    Unit getNewCp(int i) {
        outer:
        while (true) {
            double x = CPT_RADIUS*6.0 + Math.random()*(width-CPT_RADIUS*10.0);
            double y = CPT_RADIUS*4.0 + Math.random()*(height-CPT_RADIUS*8.0);
            Unit u = new Unit(x,y,CPT_RADIUS);
            for (int j = 0; j < i; j++) {
                if (u.distance(cp[j]) < 4*CPT_RADIUS) continue outer; 
            }
            return u;
        }
    }
}