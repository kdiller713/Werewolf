package runner;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import listener.ModeratorListener;
import proxy.PlayerProxy;
import server.text.ServerText;

public class ServerTextRunner {
    public static void main(String[] args) throws Exception {
        String ip = InetAddress.getLocalHost().getHostAddress();
        Scanner sc = new Scanner(System.in);

        System.out.print("Port: ");
        int port = sc.nextInt();
        sc.nextLine();

        System.out.println("Server Running on " + ip + " at port " + port);

        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(ip, port));

        ArrayList<Socket> conn = new ArrayList<Socket>();
        boolean[] good = new boolean[1];

        Thread t = new Thread(() -> {
            while (!good[0]) {
                try {
                    Socket s = ss.accept();
                    conn.add(s);
                    System.out.println("Player joined");
                } catch (Exception e) {
                }
            }
        });
        t.start();

        System.out.println("Hit Enter When All Players Have Joined");
        while (!sc.hasNextLine())
            ;
        sc.nextLine();
        good[0] = true;

        Socket s = new Socket();
        s.connect(new InetSocketAddress(ip, port));

        Thread.sleep(1000);

        conn.remove(conn.size() - 1);
        s.close();

        ArrayList<Character> roles = new ArrayList<Character>();
        int wolf = (int) (Math.random() * conn.size());
        ServerText st = new ServerText();

        ArrayList<ModeratorListener> players = new ArrayList<ModeratorListener>();

        for (int i = 0; i < conn.size(); i++) {
            if (i == wolf) {
                roles.add('w');
            } else {
                roles.add('v');
            }

            players.add(new PlayerProxy(conn.get(i), st, conn.size(), i + 1,
                    roles.get(i)));
        }

        st.begin(players, roles);
        sc.close();
        ss.close();
    }
}
