import java.util.*;

class Pod extends Unit{
    final double drag = 0.98;
    final double angularDrag = 0.95;
    //final double power = 8.0;
    //final double turnSpeed = 0.0035;
    final double power = 15.0;
    final double turnSpeed = 0.01;
    final double mutate = 0.07;

    double vx, vy;
    double angularVelocity;
    double angle;

    boolean up, down, left, right;
    Move cnt;
    Map map;

    Move[] moves;
    int tick;
    int sinceLast;

    int next = 1;
    boolean dead = false;
    boolean finished = false;
    double score = 0.0;

    Pod(double x,double y,double r) {
        super(x,y,r);
        vx = vy = angularVelocity = 0.0;
        angle = Math.PI*0.5;
    }
    
    Pod(double x,double y,double r,Map map) {
        super(x,y,r);
        this.map = map;
        vx = vy = angularVelocity = 0.0;
        angle = Math.PI*0.5;
    }

    void initMoves(int maxMoves) {
        moves = new Move[maxMoves];
        for (int i = 0; i < maxMoves; i++) {
            moves[i] = new Move();
        }
    }

    void initMoves(Move[] parent,int maxMoves,boolean change) {
        moves = new Move[maxMoves];
        for (int i = 0; i < maxMoves; i++) {
            //if (i >= parent.length || (i > parent.length-10 && (Math.random()*Math.pow(maxMoves/(i+1),2.0)) <= mutate)) moves[i] = new Move();
            if (i >= parent.length || (change && i > parent.length-parent.length && (Math.random() <= mutate))) {moves[i] = new Move();}
            //if (i >= parent.length || (Math.random() <= mutate)) moves[i] = new Move();
            else moves[i] = new Move(parent[i]);
        }
    }

    void simulate() {
        //cnt = new Move(up,down,left,right);
        //cnt = moves[tick/5];
        accelerate();
        steer();
        move();
        if (map != null) checkDeath();
        tick++;
        sinceLast++;
        score += 500000.0/(distance2(map.cp[next])+50.0)*(next>0?next:map.cp.length);
    }

    void accelerate() {
        vx += cnt.acc * Math.cos(angle) * power;
        vy += cnt.acc * Math.sin(angle) * power;
    }

    void steer() {
        angularVelocity += cnt.turn * turnSpeed;
    }

    void move() {
        x += vx;
        y -= vy;
        angle += angularVelocity;
        vx *= drag;
        vy *= drag;
        angularVelocity *= angularDrag;
    }

    void checkDeath() {
        if (collides(map.cp[next])) {
            if (next == 0) die(0);
            next ++;
            score += 100000.0*next/sinceLast;
            next %= map.cp.length;
            sinceLast = 0;
        } else if (x<0 || y<0 || x>Map.width || y>Map.height) {die(6);
        } else if (tick >= moves.length) {die(1);}
        /*else {
        for (int i = 0; i < map.obs.length; i++) {
        if (collides(map.obs[i])) {die(1);}
        }
        } */
    }

    void die(int type) {
        dead = true;
        if (type == 0) {
            finished = true;
            score = 1000000.0*next;
        } else if (type == 1) {
            //double totalDist = map.cp[next-1].distance(map.cp[next]); 
            //double dist = (totalDist - distance(map.cp[next]))/totalDist;
            //score += 5000.0/distance(map.cp[next]);
        } else if (type == 6) {
            score -= 50.0;
        }
    }
}