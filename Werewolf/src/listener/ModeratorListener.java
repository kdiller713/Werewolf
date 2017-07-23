package listener;

public interface ModeratorListener {
    public void startNight();

    public void awakAtNight();

    public void eliminated();

    public void accuse();

    public void vote(String defense);

    public void defend();

    public void defenseBeingMade(String name);

    public void killed(String name);
    
    public void accussedResult(boolean b);
    
    public void winner();
    
    public String getName();
}
