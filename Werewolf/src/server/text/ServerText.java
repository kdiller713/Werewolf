package server.text;

import java.util.ArrayList;

import listener.ModeratorListener;
import listener.PlayerListener;

public class ServerText implements PlayerListener {
    private ArrayList<ModeratorListener> players;
    private ArrayList<Character> roles;
    private String[] inputs;
    private State game;

    private enum State {
        NIGHT, ACCUSE, VOTE
    }

    public ServerText(ArrayList<ModeratorListener> p, ArrayList<Character> r) {
        players = p;
        roles = r;
        inputs = new String[players.size()];

        System.out.println("Someone has been turned into a werewolf");
        System.out.println(roles);

        while (true) {
            night();

            if (winner()) {
                break;
            }

            accuse();

            if (winner()) {
                break;
            }
        }

        System.out.println("The game has ended");
    }

    private boolean winner() {
        int werewolfCount = 0;
        int villagerCount = 0;

        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i) == 'w')
                werewolfCount++;
            else if (roles.get(i) == 'v')
                villagerCount++;
        }

        if (villagerCount == 0 || werewolfCount == 0) {
            for (int i = 0; i < players.size(); i++) {
                players.get(i).winner();
            }
        }

        return werewolfCount == 0 || villagerCount == 0;
    }

    private void night() {
        System.out.println("Night has fallen");

        game = State.NIGHT;

        for (int i = 0; i < players.size(); i++) {
            players.get(i).startNight();
            players.get(i).awakAtNight();
            inputs[i] = null;
        }

        for (int i = 0; i < players.size(); i++) {
            while (inputs[i] == null)
                ;
        }
    }

    private void accuse() {

    }

    @Override
    public void giveResponse(ModeratorListener ml, String resp) {
        switch (game) {
        case NIGHT:
            if (resp.charAt(0) != 'n')
                return;
            break;
        case ACCUSE:
            if (resp.charAt(0) != 'a')
                return;
            break;
        case VOTE:
            if (resp.charAt(0) != 'v')
                return;
            break;
        }

        for (int i = 0; i < players.size(); i++) {
            if (ml == players.get(i))
                inputs[i] = resp.substring(2);
        }
    }

}
