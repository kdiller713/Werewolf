package types.text;

public class WerewolfText extends PlayerText {

    public WerewolfText(int n, int ind) {
        super(n, ind);

        System.out.println("You are the werewolf");
    }

    @Override
    public void awakAtNight() {
        String p = playerChoose("Would you like to kill someone");
        pl.giveResponse(this, "n " + p);
        System.out.println(p);
    }

    @Override
    public void winner() {
        System.out
                .println("All the villagers were eaten and you starved to death");
    }
}
