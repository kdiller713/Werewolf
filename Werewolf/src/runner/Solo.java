package runner;

import java.util.ArrayList;

import listener.ModeratorListener;
import server.text.ServerText;
import types.text.VillagerText;
import types.text.WerewolfText;

public class Solo {
    public static void main(String[] args) {
        ArrayList<ModeratorListener> players = new ArrayList<ModeratorListener>();
        ArrayList<Character> roles = new ArrayList<Character>();

        roles.add('v');
        roles.add('v');
        roles.add('v');
        roles.add('w');

        ServerText st = new ServerText();

        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i) == 'v') {
                players.add(new VillagerText(roles.size(), i + 1));
            } else {
                players.add(new WerewolfText(roles.size(), i + 1));
            }
            
            players.get(i).setPlayerListener(st);
            st.addModeratorListener(players.get(i));
        }

        st.begin(roles);
    }
}
