package runner;

import java.util.ArrayList;

import listener.ModeratorListener;
import server.text.ServerText;
import types.text.PacifistText;
import types.text.SeerText;
import types.text.VillageIdiotText;
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
        roles.add('i');
        roles.add('p');
        roles.add('s');

        ServerText st = new ServerText();

        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i) == 'v') {
                players.add(new VillagerText(roles.size(), i + 1));
            } else if (roles.get(i) == 'i') {
                players.add(new VillageIdiotText(roles.size(), i + 1));
            } else if (roles.get(i) == 'p') {
                players.add(new PacifistText(roles.size(), i + 1));
            } else if (roles.get(i) == 's') {
                players.add(new SeerText(roles.size(), i + 1));
            } else {
                players.add(new WerewolfText(roles.size(), i + 1));
            }

            players.get(i).setPlayerListener(st);
            st.addModeratorListener(players.get(i));
        }

        st.begin(roles);
    }
}
