package server.text;

import java.util.ArrayList;

import listener.ModeratorListener;
import listener.PlayerListener;

public class ServerText implements PlayerListener {
    private ArrayList<ModeratorListener> players;
    private ArrayList<Character> roles;
    private String[] inputs;
    private int[] max;
    private int maxInd;
    private State game;

    private enum State {
        NIGHT, ACCUSE, VOTE, DEFEND
    }

    public ServerText() {
        players = new ArrayList<ModeratorListener>();
    }

    public synchronized void addModeratorListener(ModeratorListener ml) {
        players.add(ml);
    }

    public synchronized void begin(ArrayList<Character> r) {
        roles = r;
        inputs = new String[players.size()];
        max = new int[players.size()];

        System.out.println(players.size() + " players");
        System.out.println("Someone has been turned into a werewolf");

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

    private synchronized boolean winner() {
        int werewolfCount = 0;
        int villagerCount = 0;

        for (int i = 0; i < roles.size(); i++) {
            switch (roles.get(i)) {
            case 'w':
                werewolfCount++;
                break;
            case 'v':
            case 'i':
            case 'p':
            case 's':
                villagerCount++;
            }
        }

        if (villagerCount == 0 || werewolfCount == 0) {
            for (int i = 0; i < players.size(); i++) {
                players.get(i).winner();
            }
        }

        return werewolfCount == 0 || villagerCount == 0;
    }

    private synchronized void night() {
        System.out.println("Night has fallen");

        game = State.NIGHT;

        for (int i = 0; i < players.size(); i++) {
            inputs[i] = null;
            players.get(i).startNight();
            players.get(i).awakAtNight();
        }

        System.out.println("Waiting for player choices");

        for (int i = 0; i < players.size(); i++) {
            while (inputs[i] == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            
            System.out.println(players.get(i).getName() + " has given choice");
        }

        System.out.println("Got player choices");

        ArrayList<ModeratorListener> killed = new ArrayList<ModeratorListener>();

        for (int i = 0; i < players.size(); i++) {
            if (roles.get(i) == 'w') {
                int k = Integer.parseInt(inputs[i]);
                if (k > 0)
                    killed.add(players.get(k - 1));
            } else if (roles.get(i) == 's') {
                int k = Integer.parseInt(inputs[i]);
                System.out.println("Seer checks "
                        + players.get(k - 1).getName());
                if (k > 0)
                    players.get(i).isTarget(roles.get(k - 1) == 'w');
            }
        }

        for (int i = 0; i < players.size(); i++) {
            if (killed.size() > 0) {
                for (int j = 0; j < killed.size(); j++) {
                    players.get(i).killed(killed.get(j).getName());
                }
            } else {
                players.get(i).noEat();
            }
        }

        for (int i = 0; i < killed.size(); i++) {
            killed.get(i).eliminated();
            int ind = players.indexOf(killed.get(i));
            players.remove(ind);
            roles.remove(ind);
        }
    }

    private synchronized void accuse() {
        System.out.println("Accusations take place");

        game = State.ACCUSE;

        for (int i = 0; i < players.size(); i++) {
            inputs[i] = null;
            max[i] = 0;
            players.get(i).accuse();
        }

        for (int i = 0; i < players.size(); i++) {
            while (inputs[i] == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            
            System.out.println(players.get(i).getName() + " has given choice");

            try {
                int ind = Integer.parseInt(inputs[i]);
                if (ind > 0)
                    max[ind - 1]++;
            } catch (Exception e) {

            }
        }

        int maxAcc = max[0];
        maxInd = 0;

        for (int i = 1; i < players.size(); i++) {
            if (maxAcc < max[i]) {
                maxInd = i;
                maxAcc = max[i];
            }
        }

        if (maxAcc == 0) {
            for (int i = 0; i < players.size(); i++) {
                players.get(i).noAccuse();
            }

            return;
        }

        game = State.DEFEND;

        for (int i = 0; i < players.size(); i++) {
            inputs[i] = null;
            players.get(i).defenseBeingMade(players.get(maxInd).getName());
        }

        inputs[maxInd] = null;
        players.get(maxInd).defend();

        while (inputs[maxInd] == null) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        game = State.VOTE;
        String def = inputs[maxInd];
        int votes = 0;
        
        System.out.println("Voting to begin");

        for (int i = 0; i < players.size(); i++) {
            inputs[i] = null;
            players.get(i).vote(def);
        }

        for (int i = 0; i < players.size(); i++) {
            while (inputs[i] == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }

            
            System.out.println(players.get(i).getName() + " has given choice");

            try {
                int ind = Integer.parseInt(inputs[i]);
                votes += ind;
            } catch (Exception e) {

            }
        }

        boolean good = votes > players.size() / 2;

        for (int i = 0; i < players.size(); i++) {
            players.get(i).accussedResult(good);
            if (good)
                players.get(i).killed(players.get(maxInd).getName());
        }

        if (good) {
            players.get(maxInd).eliminated();
            players.remove(maxInd);
            roles.remove(maxInd);
        }
    }

    @Override
    public synchronized void giveResponse(ModeratorListener ml, String resp) {
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
        case DEFEND:
            if (resp.charAt(0) != 'd')
                return;

            if (ml != players.get(maxInd))
                return;
        }

        for (int i = 0; i < players.size(); i++) {
            if (ml == players.get(i))
                inputs[i] = resp.substring(2);
        }

        notifyAll();
    }

}
