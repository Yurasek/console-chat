import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    static ArrayList<Socket> sockets = new ArrayList<>();

    public static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        try {
            ServerSocket s = new ServerSocket(8189);
            Socket sock;
            while (true) {
                sock = s.accept();
                User usr = new User(sock);
                users.add(usr);
                Runnable run = new UserHundler(users, users.get(users.indexOf(usr)));
                (new Thread(run)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

 /*private static OutputStream[] makeRecievers() throws IOException {
        OutputStream rec[] = new OutputStream[threadsClients.size()];
        for (int i = 0; i < threadsClients.size(); i++) {
            rec[i] = threadsClients.get(i).getOutputStream();
        }
        return rec;
    }*/