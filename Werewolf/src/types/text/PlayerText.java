package types.text;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import listener.ModeratorListener;
import listener.PlayerListener;

public abstract class PlayerText implements ModeratorListener {
    protected Scanner sc;
    protected ArrayList<String> players;
    protected PlayerListener pl;
    protected String name;

    public PlayerText(int n, int ind) {
        System.out.println("Welcome to the village");
        System.out.println("You are Player " + ind);
        System.out.println("You are 1 of " + n + " players");
        name = "Player " + ind;

        players = new ArrayList<String>();

        for (int i = 1; i <= n; i++) {
            players.add("Player " + i);
        }

        sc = new Scanner(System.in);
    }

    @Override
    public void setPlayerListener(PlayerListener p) {
        pl = p;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void startNight() {
        System.out.println("Night has fallen in the village");
        System.out.println("People are making their decisions for the night");
    }

    @Override
    public void eliminated() {
        System.out.println("You have been killed");
        JOptionPane.showMessageDialog(null, "You have been killed", "Death",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void accuse() {
        String p = playerChoose("Would you like to accuse someone");
        pl.giveResponse(this, "a " + p);
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
        int choice = JOptionPane.showConfirmDialog(null,
                "The Player defendes themselves with\n\"" + defense
                        + "\"\nDo you want to kill them", "Execute",
                JOptionPane.YES_NO_OPTION);
        pl.giveResponse(this, "v " + (choice == JOptionPane.YES_OPTION ? 1 : 0));

        System.out.println("You have voted, wait for others to vote");
    }

    @Override
    public void defend() {
        String def = JOptionPane.showInputDialog(null,
                "You have been accused\nWhat is your defence", "Accused",
                JOptionPane.OK_CANCEL_OPTION);
        pl.giveResponse(this, "d " + def);
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
    public void noAccuse() {
        System.out.println("No one has been accused");
    }

    @Override
    public void noEat() {
        System.out.println("No one was eaten");
    }

    protected String playerChoose(String msg) {
        String[] pl = new String[players.size() + 1];

        pl[0] = "No one";

        for (int i = 0; i < players.size(); i++) {
            pl[i + 1] = players.get(i);
        }

        JList<String> list = new JList<String>(pl);
        list.setSelectedIndex(0);

        Object[] disp = { msg, new JScrollPane(list) };
        System.out.println("Select a player");

        JOptionPane.showConfirmDialog(null, disp, "Pick a Player",
                JOptionPane.OK_CANCEL_OPTION);

        return list.getSelectedIndex() + "";
    }
    
    @Override
    public void isTarget(boolean b){
    }
}
