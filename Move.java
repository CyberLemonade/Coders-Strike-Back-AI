class Move {
    int acc = 0;
    int turn = 0;

    Move() {
        //acc = (int)(Math.random()*3)-1;
        acc = (int)(Math.random()*2);
        //acc = 1;
        //turn = ((int)(Math.random()*5)-2)/2;
        //turn = (int)(Math.random()*2);
        turn = (int)(Math.random()*3)-1;
    }

    Move(boolean up,boolean down,boolean left,boolean right) {
        acc = (up?1:0) + (down?-1:0);
        turn = (left?1:0) + (right?-1:0);
    }

    Move(Move parent) {
        acc = parent.acc;
        turn = parent.turn;
    }
}