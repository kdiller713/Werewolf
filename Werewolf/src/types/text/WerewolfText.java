package types.text;

import listener.PlayerListener;

public class WerewolfText extends PlayerText {

    public WerewolfText(PlayerListener p, int n, int ind) {
        super(p, n, ind);

        System.out.println("You are the werewolf");
    }

    @Override
    public void awakAtNight() {
        System.out.println("Would you like to eat someone");

        System.out.println("0. Nobody");

        for (int i = 0; i < players.size(); i++) {
            System.out.println((i + 1) + ". " + players.get(i));
        }

        String p = sc.nextLine();
        pl.giveResponse(this, "n " + p);
    }

    @Override
    public void winner() {
        System.out
                .println("All the villagers were eaten and you starved to death");
    }
}
