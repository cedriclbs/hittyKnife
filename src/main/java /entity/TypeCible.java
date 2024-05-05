package entity;

public enum TypeCible {
    CIBLE_NORMALE,
    CIBLE_ARGENT,
    CIBLE_BONUSGEL,
    CIBLE_BOSS1,
    CIBLE_BOSS2,
    CIBLE_BOSS3;


    public static TypeCible typecible = CIBLE_NORMALE;

    public static void setCible (TypeCible cible){
        typecible = cible;
    }
}