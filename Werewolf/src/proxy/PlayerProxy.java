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

    public PlayerProxy(Socket s, int n, int ind, char role) {
        sock = s;
        name = "Player " + ind;
        try {
            out = new PrintWriter(sock.getOutputStream());
            sc = new Scanner(sock.getInputStream());
            out.println(role + " " + n + " " + ind);
            out.flush();
            System.out.println(name + " " + role);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread t = new Thread(this);
        t.start();
    }
    
    @Override
    public void setPlayerListener(PlayerListener p){
        pl = p;
    }

    @Override
    public void startNight() {
        out.println("start");
        out.flush();
    }

    @Override
    public void awakAtNight() {
        out.println("awake");
        out.flush();
    }

    @Override
    public void eliminated() {
        out.println("elim");
        out.flush();
    }

    @Override
    public void accuse() {
        out.println("accuse");
        out.flush();
    }

    @Override
    public void vote(String defense) {
        out.println("vote " + defense);
        out.flush();
    }

    @Override
    public void defend() {
        out.println("defend");
        out.flush();
    }

    @Override
    public void defenseBeingMade(String name) {
        out.println("defense " + name);
        out.flush();
    }

    @Override
    public void noAccuse() {
        out.println("no");
        out.flush();
    }

    @Override
    public void killed(String name) {
        out.println("killed " + name);
        out.flush();
    }

    @Override
    public void accussedResult(boolean b) {
        out.println("result " + b);
        out.flush();
    }

    @Override
    public void winner() {
        out.println("winner");
        out.flush();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void noEat() {
        out.println("eat");
        out.flush();
    }

    @Override
    public void run() {
        try {
            while (sc.hasNextLine()){
                String s = sc.nextLine();
                pl.giveResponse(this, s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
