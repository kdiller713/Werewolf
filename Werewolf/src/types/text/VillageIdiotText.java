package types.text;

import javax.swing.JOptionPane;

public class VillageIdiotText extends PlayerText {

    public VillageIdiotText(int n, int ind) {
        super(n, ind);

        System.out.println("You are a village idiot");
        JOptionPane.showMessageDialog(null, "You are a village idiot", "Role",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void awakAtNight() {
        pl.giveResponse(this, "n 0");
    }

    @Override
    public void winner() {
        System.out.println("You have survived the werewolf");
        JOptionPane.showMessageDialog(null, "You have survived the werewolf",
                "Winner", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void vote(String defense) {
        JOptionPane.showMessageDialog(null,
                "The Player defendes themselves with\n\"" + defense
                        + "\"\n You choose to kill them", "Execute",
                JOptionPane.INFORMATION_MESSAGE);
        pl.giveResponse(this, "v 1");

        System.out.println("You have voted, wait for others to vote");
    }

    @Override
    public void accuse() {
        String p = playerChoose("Would you like to accuse someone");

        if (p.equals("0")) {
            p = "" + ((int) (Math.random() * players.size()) + 1);
        }

        pl.giveResponse(this, "a " + p);
    }
}
