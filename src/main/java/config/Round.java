package config;

import java.util.ArrayList;
import java.util.List;
import entity.Cible.TypeCible;
import java.util.Random;

/**
 * La classe Round gère les rounds du jeu, y compris les cibles pour chaque round et le boss final.
 * Elle permet d'initialiser les cibles pour chaque round, de passer au round suivant, et de réinitialiser les rounds.
 */
public class Round {
    private List<TypeCible> round1Targets;
    private List<TypeCible> round2Targets;
    private List<TypeCible> round3Targets;
    private List<TypeCible> bossTarget;
    private int currentRound;
    private int indexTargets;
    private Random random;

    public Round() {
        this.random = new Random();
        round1Targets = new ArrayList<>();
        round2Targets = new ArrayList<>();
        round3Targets = new ArrayList<>();
        bossTarget = new ArrayList<>();
        currentRound = 1; // si round en trop à l'affichage mettre 0

        initTargets();
    }

    /**
     * Initialise les cibles pour le round actuel.
     */
    private void initTargets() {

        switch (currentRound) {
            case 1:
                for (int i = 0; i < getRndIntTargetRounds(); i++) {
                    round1Targets.add(getRandomTypeCible());
                }
                indexTargets = round1Targets.size();
                break;
            case 2:
                for (int i = 0; i < getRndIntTargetRounds(); i++) {
                    round2Targets.add(getRandomTypeCible());
                }
                indexTargets = round2Targets.size();
                break;
            case 3:
                for (int i = 0; i < getRndIntTargetRounds(); i++) {
                    round3Targets.add(getRandomTypeCible());
                }
                indexTargets = round3Targets.size();
                break;
            case 4:
                bossTarget.add(getRandomTypeBoss());
                indexTargets = bossTarget.size();
                break;
        }
    }

    public void reset(){
        currentRound = 1;
        indexTargets = 0;
    }
    
    /**
     * Décrémente le nombre de cible restant dans le round actuel, et passe au round suivant si toutes les cibles sont atteintes.
     */
    public void nextTarget() {
        if (indexTargets > 0) {
            //if(TargetsHit == true){ TODO Condition si le joueur a bien touché la cible à implementer 
                indexTargets--;
            //}
        } else {
            currentRound++;
            initNextRoundTargets(); 
        }
    }

    /**
     * Initialise les cibles pour le round suivant.
     */
    private void initNextRoundTargets() {
       
        clearTargets();

        switch (currentRound) {
            case 1:
                for (int i = 0; i < getRndIntTargetRounds(); i++) {
                    round1Targets.add(getRandomTypeCible());
                }
                indexTargets = round1Targets.size();
                break;
            case 2:
                for (int i = 0; i < getRndIntTargetRounds(); i++) {
                    round2Targets.add(getRandomTypeCible());
                }
                indexTargets = round2Targets.size();
                break;
            case 3:
                for (int i = 0; i < getRndIntTargetRounds(); i++) {
                    round3Targets.add(getRandomTypeCible());
                }
                indexTargets = round3Targets.size();
                break;
            case 4:
                bossTarget.add(getRandomTypeBoss());
                indexTargets = bossTarget.size();
                break;
        }
    }

    /**
     * Efface les listes de cibles pour tous les rounds, en préparation pour le round suivant.
     */
    protected void clearTargets() {
        round1Targets.clear();
        round2Targets.clear();
        round3Targets.clear();
        bossTarget.clear();
    }

    /**
     * Retourne la liste des cibles pour le round actuel.
     *
     * @return La liste des cibles pour le round actuel.
     */
    private List<TypeCible> getCurrentTargets() {
        switch (currentRound) {
            case 1:
                return round1Targets;
            case 2:
                return round2Targets;
            case 3:
                return round3Targets;
            default:
                return bossTarget;
        }
    }

    // Sélectionne parmi les deux premiers types de cibles
    private TypeCible getRandomTypeCible() {
        int randomNum = random.nextInt(2); // Sélectionne parmi les deux premiers types
        return TypeCible.values()[randomNum];
    }

    //Sélectionne parmi les trois types de boss
    private TypeCible getRandomTypeBoss() {
        int randomNum = random.nextInt(3); 
        return TypeCible.values()[TypeCible.values().length - 1 - randomNum];
    }

    //un nombre de cible aléatoire
    private int getRndIntTargetRounds(){
        int randomNum = random.nextInt(7);
        return 4 + randomNum;
    }

    public int getIndexTargets(){
        return indexTargets;
    }

    public void resetIndexTargets() {
        indexTargets= 0;
    }

}
