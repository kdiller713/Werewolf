package types.text;

import javax.swing.JOptionPane;

public class SeerText extends PlayerText {

    public SeerText(int n, int ind) {
        super(n, ind);

        System.out.println("You are a seer");
        JOptionPane.showMessageDialog(null, "You are a seer", "Role",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void awakAtNight() {
        String p = playerChoose("Would you like to check someone");
        pl.giveResponse(this, "n " + p);

        if (p.equals("0")) {
            System.out.println("You select nobody");
        } else {
            System.out.println("You select "
                    + players.get(Integer.parseInt(p) - 1));
        }
    }

    @Override
    public void isTarget(boolean b) {
        if (b) {
            System.out.println("That player is a werewolf");
        } else {
            System.out.println("That player is not a werewolf");
        }
    }

    @Override
    public void winner() {
        System.out.println("You have survived the werewolf");
        JOptionPane.showMessageDialog(null, "You have survived the werewolf",
                "Winner", JOptionPane.INFORMATION_MESSAGE);
    }

}
