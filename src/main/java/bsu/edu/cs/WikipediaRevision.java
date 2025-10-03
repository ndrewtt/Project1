package bsu.edu.cs;

public class WikipediaRevision {
    private final String timestamp;
    private final String user;

    public WikipediaRevision(String timestamp, String user) {
        this.timestamp = timestamp;
        this.user = user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }
}
