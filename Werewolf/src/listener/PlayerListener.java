package listener;

public interface PlayerListener {
    public void giveResponse(PlayerListener pl, String resp);
    
    public String getResponse(PlayerListener pl, String question);
}
