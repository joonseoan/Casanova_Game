package game;

// in order to use the treeset, implements the Comparable interface
public class Casanova implements Comparable<Casanova> {

    private String id;
    private int time;

    public Casanova() {
        this.id = "";
        this.time = 0;
    }

    public Casanova(String id) {
        this.id = id;

    }

    public Casanova(String id, int time) {
        this.id = id;
        this.time = time;
    }

    // ascended by time to display the first recorded person to the game page
    public int compareTo(Casanova s) {
        return s.time - this.time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;

    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {

        return this.id + ", " + this.time;
    }
}
