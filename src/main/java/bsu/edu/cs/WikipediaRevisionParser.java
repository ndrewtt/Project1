package bsu.edu.cs;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WikipediaRevisionParser {

    public List<WikipediaRevision> parseRevisions(InputStream inputStream) throws IOException {
        String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        JSONArray timestamps = JsonPath.read(json, "$..revisions[*].timestamp");
        JSONArray users = JsonPath.read(json, "$..revisions[*].user");

        List<WikipediaRevision> revisions = new ArrayList<>();

        for (int i = 0; i < timestamps.size(); i++) {
            String timestamp = timestamps.get(i).toString();
            String username = users.get(i).toString();
            revisions.add(new WikipediaRevision(timestamp, username));
        }

        return revisions;
    }
}
