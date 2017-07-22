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

    /**
     * Tells the player how many players there are and the number they are
     * 
     * @param n
     *            is the number of players
     * @param ind
     *            is their number
     */
    public void numPlayers(int n, int ind);
}
