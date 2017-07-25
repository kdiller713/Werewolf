package types.text;

public class VillagerText extends PlayerText {

    public VillagerText(int n, int ind) {
        super(n, ind);

        System.out.println("You are a villager");
    }

    @Override
    public void awakAtNight() {
        pl.giveResponse(this, "n 0");
    }

    @Override
    public void winner() {
        System.out.println("You have survived the werewolf");
    }
}
