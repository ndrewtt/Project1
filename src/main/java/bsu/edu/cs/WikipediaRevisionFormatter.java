package bsu.edu.cs;

public class WikipediaRevisionFormatter {
    public String format(WikipediaRevision revision, int lineNumber) {
        return String.format("%d  %s  %s", lineNumber, revision.getTimestamp(), revision.getUser());
    }
}
