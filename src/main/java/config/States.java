package config;

public enum States {
     
    HOMEMENU, 
    SOLOMODE,
    VERSUSMODE,
    GAMEOVER;
    

    public static States states = HOMEMENU;

    public static void setStates (States state){
        states = state;
    }

}
