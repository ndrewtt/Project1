package bsu.edu.cs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

public class WikipediaRevisionParserTest {

    @Test
    public void testParseRevisions() throws Exception {
        WikipediaRevisionParser parser = new WikipediaRevisionParser();

        InputStream testDataStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("test.json");

        List<WikipediaRevision> revisions = parser.parseRevisions(testDataStream);

        WikipediaRevision firstRevision = revisions.getFirst();

        Assertions.assertEquals("2025-08-13T22:47:03Z", firstRevision.getUser());
        Assertions.assertEquals("Ernsanchez00", firstRevision.getTimestamp());
    }
    @Test
    public void testParseRevisions_emptyFile() throws Exception {
        WikipediaRevisionParser parser = new WikipediaRevisionParser();

        InputStream emptyStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("empty.json");
        Assertions.assertNotNull(emptyStream, "empty.json should exist");

        List<WikipediaRevision> revisions = parser.parseRevisions(emptyStream);
        Assertions.assertTrue(revisions.isEmpty(), "Revisions should be empty");
    }
    @Test
    public void testParseRevisions_under15() throws Exception {
        WikipediaRevisionParser parser = new WikipediaRevisionParser();

        InputStream emptyStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("test.json");
        Assertions.assertNotNull(emptyStream, "test.json should exist");

        List<WikipediaRevision> revisions = parser.parseRevisions(emptyStream);
        Assertions.assertTrue(revisions.size() < 15, "Should have fewer than 15 revisions");
    }
}
