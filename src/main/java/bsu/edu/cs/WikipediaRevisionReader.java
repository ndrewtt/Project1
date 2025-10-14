package bsu.edu.cs;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class WikipediaRevisionReader {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type the Wikipedia article you wish to know more about: ");
        String article = scanner.nextLine().trim();

        if (article.isEmpty()) {
            System.err.println("You didn't type any article");
            return;
        }

        try {
            WikipediaRevisionReader reader = new WikipediaRevisionReader();
            List<WikipediaRevision> revisions = reader.getRevisions(article);

            if (revisions.isEmpty()) {
                System.err.println("Sorry, we couldn't find anything for \"" + article + "\".");
                return;
            }

            WikipediaRevisionFormatter formatter = new WikipediaRevisionFormatter();
            for (int i = 0; i < revisions.size(); i++) {
                System.out.println(formatter.format(revisions.get(i), i + 1));
            }

        } catch (IOException e) {
            System.err.println("Network error: " + e.getMessage());
        }
    }

    protected List<WikipediaRevision> getRevisions(String articleTitle) throws IOException {
        String encodedTitle = URLEncoder.encode(articleTitle, StandardCharsets.UTF_8);
        String urlString = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions" +
                "&titles=" + encodedTitle +
                "&rvprop=timestamp|user&rvlimit=15&redirects";

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent",
                "WikipediaRevisionReader/0.1 (me@bsu.edu)");

        try (InputStream in = conn.getInputStream()) {
            WikipediaRevisionParser parser = new WikipediaRevisionParser();
            return parser.parseRevisions(in);
        }
    }
}
