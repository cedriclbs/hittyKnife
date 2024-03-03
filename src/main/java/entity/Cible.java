package entity;

public class Cible {


    private int x,y;

    Cible(int x,int y){
        this.x=x;
        this.y=y;
    }


    public int getY(){
        return y;
    }
    public int getX() {
        return x;
    }
    public void setX(int x){
        this.x=x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
