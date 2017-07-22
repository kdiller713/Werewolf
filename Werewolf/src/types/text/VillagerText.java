package types.text;

import listener.PlayerListener;

public class VillagerText extends PlayerText {

    public VillagerText(PlayerListener p, int n, int ind) {
        super(p, n, ind);

        System.out.println("You are a villager");
    }

    @Override
    public void awakAtNight() {
        pl.giveResponse(this, "n null");
    }

    @Override
    public void winner(){
        System.out.println("You have survived the werewolf");
    }
}
