package entity;

public enum TypeCible {
    CIBLE_NORMALE,
    CIBLE_MOUVANTE,
    CIBLE_BONUS,
    CIBLE_BOSS1,
    CIBLE_BOSS2,
    CIBLE_BOSS3,
    CIBLE_BOSS4;


    public static TypeCible typecible = CIBLE_NORMALE;

    public static void setCible (TypeCible cible){
        typecible = cible;
    }
}