package bsu.edu.cs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class WikipediaRevisionParserTest {

    @Test
    public void testParseRevisions() throws IOException {
        WikipediaRevisionParser parser = new WikipediaRevisionParser();
        InputStream testDataStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.json");

        List<WikipediaRevision> revisions = parser.parseRevisions(testDataStream);

        Assertions.assertFalse(revisions.isEmpty(), "No revisions!");

        WikipediaRevision firstRevision = revisions.get(0);
        Assertions.assertEquals("2025-08-13T22:47:03Z", firstRevision.getTimestamp());

        Assertions.assertEquals("Ernsanchez00", firstRevision.getUser());
    }
}
