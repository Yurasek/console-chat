import java.net.Socket;

class User {

    private int ID = 0;

    private String name;

    private int age = 0;

    private String secName;

    private static int count = 0;

    private Socket socket;

    public User(Socket sock) {
        this.socket = sock;
        setName();
        setHashThread();
    }

    public String getName() {
        return this.name;
    }

    public void setName() {
        User.count += 1;
        this.name = "Anon " + User.count;
    }

    public String getSecName() {
        return this.secName;
    }

    public int getAge() {
        return this.age;
    }

    public int getID() {
        return this.ID;
    }

    public Socket getSocket() {
        return this.socket;
    }

    private void setHashThread() {
        this.ID = Thread.currentThread().hashCode();
    }
}
