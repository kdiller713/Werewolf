package proxy;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import listener.ModeratorListener;
import listener.PlayerListener;

public class ServerProxy implements PlayerListener, Runnable {
    private ModeratorListener ml;
    private Socket sock;
    private PrintWriter out;
    private Scanner sc;

    public ServerProxy(Socket s, Scanner scan) {
        sock = s;
        sc = scan;
        try {
            out = new PrintWriter(sock.getOutputStream());
        } catch (Exception e) {
        }

        Thread t = new Thread(this);
        t.start();
    }

    public void setModeratorListener(ModeratorListener m) {
        ml = m;
    }

    @Override
    public void giveResponse(ModeratorListener ml, String resp) {
        out.println(resp);
        out.flush();
    }

    @Override
    public void run() {
        try {
            String line;

            while (ml == null)
                ;

            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String[] choices = line.split(" ");

                switch (choices[0]) {
                case "start":
                    ml.startNight();
                    break;
                case "awake":
                    ml.awakAtNight();
                    break;
                case "elim":
                    ml.eliminated();
                    sock.close();
                    break;
                case "accuse":
                    ml.accuse();
                    break;
                case "vote":
                    ml.vote(line.substring(5));
                    break;
                case "defend":
                    ml.defend();
                    break;
                case "defense":
                    ml.defenseBeingMade(line.substring(8));
                    break;
                case "no":
                    ml.noAccuse();
                    break;
                case "killed":
                    ml.killed(line.substring(7));
                    break;
                case "result":
                    ml.accussedResult(Boolean.parseBoolean(choices[1]));
                    break;
                case "winner":
                    ml.winner();
                    break;
                case "eat":
                    ml.noEat();
                    break;
                }
            }
        } catch (Exception e) {
        }
    }
}
