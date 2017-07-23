package proxy;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import listener.ModeratorListener;
import listener.PlayerListener;

public class PlayerProxy implements ModeratorListener, Runnable {
    private Socket sock;
    private PlayerListener pl;
    private PrintWriter out;
    private Scanner sc;
    private String name;

    public PlayerProxy(Socket s, PlayerListener l, int n, int ind, char role) {
        sock = s;
        name = "Player " + ind;
        pl = l;
        try {
            out = new PrintWriter(sock.getOutputStream());
            sc = new Scanner(sock.getInputStream());
            out.println(role + " " + n + " " + ind);
        } catch (Exception e) {
        }

        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void startNight() {
        out.println("start");
    }

    @Override
    public void awakAtNight() {
        out.println("awake");
    }

    @Override
    public void eliminated() {
        out.println("elim");
    }

    @Override
    public void accuse() {
        out.println("accuse");
    }

    @Override
    public void vote(String defense) {
        out.println("vote " + defense);
    }

    @Override
    public void defend() {
        out.println("defend");
    }

    @Override
    public void defenseBeingMade(String name) {
        out.println("defense " + name);
    }

    @Override
    public void noAccuse() {
        out.println("no");
    }

    @Override
    public void killed(String name) {
        out.println("killed " + name);
    }

    @Override
    public void accussedResult(boolean b) {
        out.println("result " + b);
    }

    @Override
    public void winner() {
        out.println("winner");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void noEat() {
        out.println("eat");
    }

    @Override
    public void run() {
        try {
            while (sc.hasNextLine())
                pl.giveResponse(this, sc.nextLine());
        } catch (Exception e) {

        }
    }
}
