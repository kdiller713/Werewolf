package types.text;

import javax.swing.JOptionPane;

public class PacifistText extends PlayerText {

    public PacifistText(int n, int ind) {
        super(n, ind);

        System.out.println("You are a pacifist");
        JOptionPane.showMessageDialog(null, "You are a pacifist", "Role",
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
                        + "\"\n You choose to spare them", "Execute",
                JOptionPane.INFORMATION_MESSAGE);
        pl.giveResponse(this, "v 0");

        System.out.println("You have voted, wait for others to vote");
    }

    @Override
    public void accuse() {
        JOptionPane.showMessageDialog(null, "People will be accused\nYou can't accuse people",
                "Accusations", JOptionPane.INFORMATION_MESSAGE);
        pl.giveResponse(this, "a 0");
    }
}
