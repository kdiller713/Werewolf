package types.text;

import listener.PlayerListener;

public class VillagerText extends PlayerText {

    public VillagerText(PlayerListener p) {
        super(p);

        System.out.println("You are a villager");
    }

    @Override
    public void awakAtNight() {
        pl.giveResponse(this, null);
    }

}