package entity;

/**
 * Énumération représentant les différents types de cibles dans le jeu.
 */
public enum TypeCible {
    CIBLE_NORMALE,  // Cible normale
    CIBLE_MOUVANTE, // Cible mouvante
    CIBLE_BONUS,    // Cible bonus
    CIBLE_BOSS1,    // Boss de niveau 1
    CIBLE_BOSS2,    // Boss de niveau 2
    CIBLE_BOSS3,    // Boss de niveau 3
    CIBLE_BOSS4;    // Boss de niveau 4

    // Variable statique pour stocker le type de cible actuel
    public static TypeCible typecible = CIBLE_NORMALE;

    /**
     * Méthode statique pour définir le type de cible actuel.
     *
     * @param cible Le type de cible à définir comme actuel.
     */
    public static void setCible(TypeCible cible) {
        typecible = cible;
    }
}
