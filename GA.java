import java.util.*;

class GA {
    Map map;
    Pod[] pods = new Pod[500];
    int generation = 1;
    int maxMoves = 20;
    int cap = 60000;
    int cntBest = 0;

    GA() {
        map = new Map();
        for (int i = 0; i < pods.length; i++) {
            pods[i] = new Pod(map.cp[0].x, map.cp[0].y, Map.POD_RADIUS,map);
            pods[i].initMoves(maxMoves);
        }
    }

    void simulate() {
        cntBest = 0;
        boolean allDead = true;
        for (int i = 0; i < pods.length; i++) {
            if (pods[i].dead) continue;
            allDead = false;
            pods[i].cnt = pods[i].moves[pods[i].tick/10];;
            pods[i].simulate();
            if (pods[cntBest].score < pods[i].score) cntBest = i;
        }
        if (allDead) createNewGen();
    }

    void createNewGen() {
        generation++;
        if (generation % 5 == 0) {maxMoves += 10;}

        double score = 0.0;
        int index = 0;

        for (int i = 0; i < pods.length; i++) {
            //System.err.println("Pod "+i+" reached "+(pods[i].next-1)+" and is "+pods[i].distance(map.cp[pods[i].next])+"/"+(Map.POD_RADIUS + Map.CPT_RADIUS)+" from "+map.cp[pods[i].next]);
            score += Math.pow(pods[i].score,2.0);
            if (pods[i].score > pods[index].score) {
                index = i;
            }
            if (pods[i].finished) {cap = pods[i].tick;}
        }

        Pod[] baby = new Pod[pods.length];
        for (int i = 0; i < baby.length; i++) {
            baby[i] = new Pod(map.cp[0].x, map.cp[0].y, Map.POD_RADIUS, map);
            baby[i].initMoves(pods[naturalSelection(score)].moves,Math.min(maxMoves,cap),true);
        }        
        baby[0].initMoves(pods[index].moves,Math.min(maxMoves,cap),false);
        
        pods = baby;
    }

    int naturalSelection(double score) {
        double num = Math.random() * score;
        double sum = 0.0;

        for (int i = 0; i < pods.length; i++) {
            sum += Math.pow(pods[i].score,2.0);
            if (num <= sum) return i;
        }

        return 0;
    }
}