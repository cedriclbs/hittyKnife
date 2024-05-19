package gui;

/**
 * L'interface LibraryObserver définit la méthode updateInventaire(),
 * qui est utilisée pour informer les observateurs de bibliothèque d'un changement dans l'inventaire.
 * Les classes qui implémentent cette interface peuvent agir en tant qu'observateurs de l'inventaire
 * et réagir aux mises à jour de l'inventaire en conséquence.
 */

public interface LibraryObserver {
    /**
     * Méthode appelée lorsqu'un changement est détecté dans l'inventaire.
     * Les observateurs de bibliothèque doivent implémenter cette méthode pour réagir
     * aux mises à jour de l'inventaire.
     */
    void updateInventaire();

}
