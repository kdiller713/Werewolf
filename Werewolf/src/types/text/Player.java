package types.text;

import java.util.ArrayList;
import java.util.Scanner;

import listener.ModeratorListener;
import listener.PlayerListener;

public abstract class Player implements ModeratorListener {
    protected Scanner sc;
    protected ArrayList<String> players;
    protected PlayerListener pl;

    public Player(PlayerListener p) {
        sc = new Scanner(System.in);
        players = new ArrayList<String>();
        pl = p;
    }

    @Override
    public void startNight() {
        System.out.println("Night has fallen in the village");
        System.out.println("People are making their decisions for the night");
    }

    @Override
    public void eliminated() {
        System.out.println("You have been killed");
    }

    @Override
    public void accuse() {
        System.out.println("Would you like to accuse someone");

        System.out.println("0. Nobody");

        for (int i = 0; i < players.size(); i++) {
            System.out.println((i + 1) + ". " + players.get(i));
        }

        String p = sc.nextLine();
        pl.giveResponse(this, p);
    }

    @Override
    public void accussedResult(boolean pass) {
        if (pass) {
            System.out.println("The vote passed and the player was killed");
        } else {
            System.out
                    .println("The vote did not pass and the player was spared");
        }
    }

    @Override
    public void vote(String defense) {
        System.out.println("The player denfends themselves with");
        System.out.println("\"" + defense + "\"");
        System.out.println("Execute them or spare");
        System.out.println("0. Spare");
        System.out.println("1. Execute");
        String vote = sc.nextLine();
        pl.giveResponse(this, vote);

        System.out.println("You have voted, wait for others to vote");
    }

    @Override
    public void defend() {
        System.out.println("You have been accused, what is your defence");
        String def = sc.nextLine();
        pl.giveResponse(this, def);
        System.out.println("You make your defence, it's up to a vote");
    }

    @Override
    public void defenseBeingMade(String name) {
        System.out.println(name + " is accused and is making their defence");
    }

    @Override
    public void killed(String name) {
        System.out.println(name + " has been killed");
        players.remove(name);
    }

    @Override
    public void numPlayers(int n, int ind) {
        System.out.println("You are Player " + ind);
        System.out.println("You are 1 of " + n + " players");

        for (int i = 1; i <= n; i++) {
            players.add("Player " + i);
        }
    }

}
