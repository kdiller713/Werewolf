package runner;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import listener.ModeratorListener;
import proxy.ServerProxy;
import types.text.VillagerText;
import types.text.WerewolfText;

public class PlayerTextRunner {
    public static void main(String[] args) throws Exception {
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        System.out.print("IP: ");
        String ip = sc.nextLine();
        System.out.print("Port: ");
        int port = sc.nextInt();
        sc.nextLine();

        Socket sock = new Socket();
        sock.connect(new InetSocketAddress(ip, port));

        System.out
                .println("You have been connected, waiting for other players");

        Scanner scan = new Scanner(sock.getInputStream());
        String line = sc.nextLine();
        String[] split = line.split(" ");

        int n = Integer.parseInt(split[1]);
        int ind = Integer.parseInt(split[2]);
        ModeratorListener ml = null;
        ServerProxy sp = new ServerProxy(sock, scan);

        switch (split[0]) {
        case "w":
            ml = new WerewolfText(sp, n, ind);
            break;
        case "v":
            ml = new VillagerText(sp, n, ind);
            break;
        }

        sp.setModeratorListener(ml);
    }
}
