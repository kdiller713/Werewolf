package types.text;

import javax.swing.JOptionPane;

public class WerewolfText extends PlayerText {

    public WerewolfText(int n, int ind) {
        super(n, ind);

        JOptionPane.showMessageDialog(null, "You are a werewolf", "Role",
                JOptionPane.INFORMATION_MESSAGE);
        System.out.println("You are a werewolf");
    }

    @Override
    public void awakAtNight() {
        String p = playerChoose("Would you like to kill someone");
        pl.giveResponse(this, "n " + p);
        
        if(p.equals("0")){
            System.out.println("You ate nobody");
        }else{
            System.out.println("You ate " + players.get(Integer.parseInt(p) - 1));
        }
    }

    @Override
    public void winner() {
        JOptionPane.showMessageDialog(null,
                "All the villagers were eaten and you starved to death",
                "Winner", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("All the villagers were eaten and you starved to death");
    }
}
