package config;

import entity.Bonus;
import entity.MovingTarget;
import entity.TypeCible;
import entity.bosses.*;
import entity.Cible;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import static entity.Bonus.TypeBonus.*;


/**
 * Gère les rounds dans le jeu, y compris les cibles pour chaque round.
 */
public class RoundManagement {
    private final List<Round> rounds; // Liste des rounds contenant des cibles pour chaque round
    private int currentRoundIndex; // Indice du round actuel
    private final Random random; // Pour générer des valeurs aléatoires
    private Queue<TypeCible> bossQueue;    // File principale pour les bosses
    private Queue<TypeCible> tempQueue;    // File temporaire pour stocker les bosses récemment utilisés
    private boolean isSolo;
    

    /**
     * Constructeur pour initialiser les rounds et leurs cibles.
     */
    public RoundManagement(boolean isSolo) {
        this.random = new Random();
        this.rounds = new ArrayList<>();
        this.currentRoundIndex = 0;
        this.isSolo = isSolo;
        initBossQueues();
        initRounds();
    }

    /**
     * Génère une position X aléatoire.
     *
     * @return Une position X aléatoire en dehors de la zone interdite.
     */
    private double getRandomPositionX() {
        if (isSolo) {
            double x;
            do {
                x = (random.nextDouble() * 60) - 30; //à regler selon la taille du jeu, cible en position x de -30 à 30
            } while (x > -7 && x < 7); // Évite la zone autour de x = 0
            return x;
        }
        else{
            double x;
            do {
                x = (random.nextDouble() * 60) - 30; //à regler selon la taille du jeu, cible en position x de -30 à 30
            } while ((x > -25 && x<-10)||( x < 25 && x>10)); // Évite la zone autour de x = 0
            return x;
        }
    }

    /**
     * Génère une position Y aléatoire.
     *
     * @return Une position Y aléatoire en dehors de la zone interdite.
     */
    private double getRandomPositionY() {
        double y;
        do {
            y = random.nextDouble() * 30; // génère un y entre 0 et 30
        } while (y >= 0 && y <= 15 && Math.abs(getRandomPositionX()) <= 7); // Évite la combinaison de y entre 0 et 15 avec x entre -7 et 7
        return y;
    }


    /**
     * Sélectionne un type de cible aléatoire pour les rounds normaux.
     *
     * @return Un type de cible aléatoire, à l'exception des boss.
     */
    private TypeCible getRandomTypeCible() {
        int randomNum = random.nextInt(3);
        return TypeCible.values()[randomNum];
    }

    /**
     * Sélectionne un type de boss aléatoire pour le round final, en évitant de répéter le dernier boss utilisé.
     *
     * @return Un type de boss aléatoire.
     */
    private TypeCible getRandomTypeBoss() {
        // S'assure que la queue principale a des éléments à offrir
        if (bossQueue.isEmpty()) {
            refillBossQueue();
        }

        TypeCible boss = bossQueue.poll();  // Retire le prochain boss de la file principale
        tempQueue.offer(boss);  // Ajoute ce boss à la file temporaire

        // Si la file temporaire atteint 3 éléments, réintégre le premier élément retiré à la file principale
        if (tempQueue.size() > 2) {
            bossQueue.offer(tempQueue.poll());
        }

        return boss;
    }

    /**
     * Génère un nombre aléatoire de cibles pour les rounds.
     *
     * @return Un nombre aléatoire de cibles, entre 4 et 10.
     */
    private int getRndIntTargetRounds() {
        return 4+ random.nextInt(3);
        //return 3;
    }

    /**
     * Retourne la liste des rounds.
     *
     * @return La liste des rounds.
     */
    public List<Round> getListeRounds() {
        return rounds;
    }

    /**
     * Retourne l'indice du round actuel.
     *
     * @return L'indice du round actuel.
     */
    public int getCurrentRoundIndex() {
        return currentRoundIndex;
    }

    /**
     * Définit l'indice du round actuel.
     *
     * @param a L'indice du round actuel à définir.
     */
    public void setCurrentRoundIndex(int a) {
        this.currentRoundIndex = a;
    }


    /**
     * Prépare les rounds en créant une liste de cibles pour chacun.
     */
    private void initRounds() {
        this.rounds.clear();
        for (int i = 0; i < 4; i++) {
            this.rounds.add(new Round());
        }
        populateRounds();
    }

    /**
     * Initialise les files de bosses avec des types mélangés.
     */
    private void initBossQueues() {
        List<TypeCible> bosses = Arrays.asList(
            TypeCible.CIBLE_BOSS1, 
            TypeCible.CIBLE_BOSS2, 
            TypeCible.CIBLE_BOSS3, 
            TypeCible.CIBLE_BOSS4  
        );
        Collections.shuffle(bosses);
        bossQueue = new LinkedList<>(bosses);
        tempQueue = new LinkedList<>();
    }

    /**
     * Remplit chaque round avec un ensemble déterminé de cibles.
     */
    private synchronized void populateRounds() {
        int lastIndex = rounds.size() - 1; // Index du dernier round

        for (int i = 0; i < rounds.size(); i++) {
            Round round = rounds.get(i);
            int targetsCount = i < lastIndex ? getRndIntTargetRounds() : 1; // Plusieurs cibles pour les rounds normaux, une pour le boss

            for (int j = 0; j < targetsCount; j++) {
                TypeCible typeCible;
                double x, y;

                if (i == lastIndex) {  // S'assure que c'est le dernier round
                    typeCible = getRandomTypeBoss();
                    x = -60;
                    y = -60;
                    Cible cible = createCibleWithType(typeCible, x, y);
                    round.addCible(cible);
                    continue;  // S'assure que seul un boss est ajouté dans le dernier round
                }

                // Sélection du type de cible pour les rounds autres que le dernier
                typeCible = TypeCible.CIBLE_NORMALE ;
                do {
                    x = getRandomPositionX();
                    y = getRandomPositionY();
                } while (EstTropProche(x, y, round.getListeCibles()) || (x > -7 && x < 7 && y >= 0 && y <= 15)); // Réessaye tant que la cible est trop proche des autres

                Cible cible;
                if (j == targetsCount - 2) {
                    int randomNum = random.nextInt(2);
                    if (randomNum == 0) {
                        cible = createCibleWithType(TypeCible.CIBLE_MOUVANTE, x, y);
                    } else {
                        cible = createCibleWithType(typeCible, x, y);
                    }
                } else if (j == targetsCount - 1) {
                    cible = createCibleWithType(TypeCible.CIBLE_BONUS, x, y);
                } else {
                    cible = createCibleWithType(typeCible, x, y);
                }
                round.addCible(cible);
            }
        }
    }

    /**
     * Crée une cible avec le type spécifié et les coordonnées données.
     *
     * @param typeCible Le type de la cible à créer.
     * @param x La position X de la cible.
     * @param y La position Y de la cible.
     * @return La cible créée.
     */
    private Cible createCibleWithType(TypeCible typeCible, double x, double y) {
        switch (typeCible) {
            case CIBLE_NORMALE:
                // Créer une cible normale
                return new Cible(typeCible,x, y);
            case CIBLE_MOUVANTE:
                // Créer une cible mouvante
                return new MovingTarget(x, y);
            case CIBLE_BONUS:
                int randomNum = random.nextInt(5);
                Bonus.TypeBonus typeBonus = switch (randomNum) {
                    case 0 -> BONUS_XP;
                    case 1 -> BONUS_GOLD;
                    case 2 -> BONUS_GEL;
                    case 3 -> BONUS_POWER;
                    default -> BONUS_TNT;
                };
                return new Bonus(x, y,typeBonus);
    
            case CIBLE_BOSS1:
                // Créer un BossType1
                return new BossType1(x, y);
    
            case CIBLE_BOSS2:
                // Créer un BossType2
                return new BossType2(x,y);

            case CIBLE_BOSS3:
                // Créer un BossType3
                return new BossType3(x, y);

            case CIBLE_BOSS4:
            // Créer un BossType4
            return new BossType4(x, y);
    
            default:
                return new Cible(x, y);
        }
    }

    /**
     * Vérifie si une nouvelle cible est trop proche d'une cible existante.
     *
     * @param x La position X de la nouvelle cible.
     * @param y La position Y de la nouvelle cible.
     * @param cibles La liste des cibles existantes
     *
     **/
    private boolean EstTropProche(double x, double y, List<Cible> cibles) {
        double minDistance = 8; 
        for (Cible cible : cibles) {
            double dx = x - cible.getX();
            double dy = y - cible.getY();
            if (Math.sqrt(dx * dx + dy * dy) < minDistance) {
                return true; 
            }
        }
        return false;
    }

    /**
     * Re-remplit la file des bosses si elle est vide.
     * Transfère les éléments de la file temporaire à la file principale et les mélange si nécessaire.
     */
    private void refillBossQueue() {
        if (bossQueue.isEmpty() && tempQueue.size() <= 2) {
            while (!tempQueue.isEmpty()) {
                bossQueue.offer(tempQueue.poll());
            }
            Collections.shuffle((LinkedList<TypeCible>) bossQueue);
        }
    }
    // méthode pour vérifier si tous les rounds sont complétés
    public boolean isAllRoundsCompleted() {
        return currentRoundIndex == rounds.size();
    }

    /**
     * Réinitialise les rounds à leur état initial.
     * Efface tous les rounds existants et réinitialise l'indice du round actuel à 0.
     */
    public void resetRounds() {
        rounds.clear();
        currentRoundIndex = 0;
        initRounds();
    }
}