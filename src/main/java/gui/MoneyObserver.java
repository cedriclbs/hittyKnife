package gui;

/**
 * Cette interface définit la méthode permettant de mettre à jour l'affichage du montant d'argent dans la boutique.
 */
public interface MoneyObserver {

    /**
     * Met à jour l'affichage du montant d'argent.
     */
    void updateMoneyLabel();
}
