package listener;

public interface ModeratorListener {
    public void startNight();
    
    public void awakAtNight();
    
    public void eliminated();
    
    public void accuse();
    
    public void vote();
    
    public void defend();
    
    public void defenseBeingMade();
}
