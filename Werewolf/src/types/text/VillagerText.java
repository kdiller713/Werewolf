package types.text;

import javax.swing.JOptionPane;

public class VillagerText extends PlayerText {

    public VillagerText(int n, int ind) {
        super(n, ind);

        System.out.println("You are a villager");
        JOptionPane.showMessageDialog(null, "You are a villager", "Role",
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
}
