package config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * La classe Round gère les rounds du jeu, y compris les cibles pour chaque round et le boss final.
 * Elle permet d'initialiser les cibles pour chaque round, de passer au round suivant, et de réinitialiser les rounds.
 */
public class Round {
    private List<Integer> round1Targets;
    private List<Integer> round2Targets;
    private List<Integer> round3Targets;
    private List<Integer> bossTarget;
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
                    round1Targets.add(getRndIntTarget());
                }
                indexTargets = round1Targets.size();
                break;
            case 2:
                for (int i = 0; i < getRndIntTargetRounds(); i++) {
                    round2Targets.add(getRndIntTarget());
                }
                indexTargets = round2Targets.size();
                break;
            case 3:
                for (int i = 0; i < getRndIntTargetRounds(); i++) {
                    round3Targets.add(getRndIntTarget());
                }
                indexTargets = round3Targets.size();
                break;
            case 4:
                bossTarget.add(getRndIntBoss());
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
            //if(TargetsHit == true){ Condition si le joueur a bien touché la cible à implementer 
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
                    round1Targets.add(getRndIntTarget());
                }
                indexTargets = round1Targets.size();
                break;
            case 2:
                for (int i = 0; i < getRndIntTargetRounds(); i++) {
                    round2Targets.add(getRndIntTarget());
                }
                indexTargets = round2Targets.size();
                break;
            case 3:
                for (int i = 0; i < getRndIntTargetRounds(); i++) {
                    round3Targets.add(getRndIntTarget());
                }
                indexTargets = round3Targets.size();
                break;
            case 4:
                bossTarget.add(getRndIntBoss());
                indexTargets = bossTarget.size();
                break;
        }
    }

    /**
     * Efface les listes de cibles pour tous les rounds, en préparation pour le round suivant.
     */
    private void clearTargets() {
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
    private List<Integer> getCurrentTargets() {
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

    //renvoie un nombre entre 4 et 10 qui sont les chiffres des types de boss
    private int getRndIntBoss(){
        int randomNum = random.nextInt(7);
        return 4 + randomNum;
    }
    //renvoie un nombre entre 0 et 3 qui sont les chiffres des types de cible
    private int getRndIntTarget(){
        return random.nextInt(4);
    }

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
