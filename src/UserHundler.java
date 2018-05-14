import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

class UserHundler implements Runnable {
    private static ArrayList<User> users;
    private Socket sock;
    private User user;

    public UserHundler(ArrayList<User> users, User user) {

        this.users = users;
        this.user = user;
        this.sock = user.getSocket();

    }

    @Override
    public void run() {
        //users.add(new User(sock));
        try {
            InputStream inForm = sock.getInputStream();
            Scanner scan = new Scanner(inForm, "UTF-8");
            doResponse(scan, user);
            users.remove(users.indexOf(user));
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doResponse(Scanner scan, User user) throws IOException {
        PrintWriter writer;
        boolean bye = false;
        String line;
        while (!bye && (line = scan.nextLine()) != null) {
            int ind = users.indexOf(user);
            if (!line.trim().equals("BYE")) {
                for (int i = 0; i < users.size(); i++) {

                    writer = new PrintWriter(new OutputStreamWriter(
                            users.get(i).getSocket().getOutputStream(), "UTF-8"), true);

                    if (users.get(i).getID() == Thread.currentThread().hashCode())
                        writer.println("Your message: " + line + " ");
                    else
                        writer.println(users.get(ind).getName() + ": - " + line);
                }
            } else {
                for (int i = 0; i < users.size(); i++) {

                    writer = new PrintWriter(new OutputStreamWriter(
                            users.get(i).getSocket().getOutputStream(), "UTF-8"), true);
                    if (users.get(i).getID() != Thread.currentThread().hashCode())
                        writer.println("User - \"" + users.get(ind).getName() + "\"  has been disconnect.");

                }
                bye = true;
            }

        }
    }
}
