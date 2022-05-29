import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        HTML2JsonParser parser = new HTML2JsonParser("https://skillbox-java.github.io/");

        parser.parse();

        parser.makeJson("JSON", "jsonFile.json");
    }
}
