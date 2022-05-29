import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class HTML2JsonParser {

    private final String url;
    private Document document;
    private ArrayList<Line> lineList = new ArrayList<>();
    private ArrayList<Station> stationList = new ArrayList<>();

    public HTML2JsonParser(String url) {
        this.url = url;
    }

    public void parse() {

        parseURL2Document(url);
        stationList = getStationList();
        lineList = getLineList();
    }

    private void parseURL2Document(String url) {
        try {
            document = Jsoup.connect(url).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Line> getLineList() {

        ArrayList<Line> lines = new ArrayList<>();

        Elements elements = document.select("span");
        for (Element element : elements) {
            if(!element.getElementsByAttribute("data-line").isEmpty()) {
                lines.add(new Line(element.getElementsByAttribute("data-line").text(),
                        element.attr("data-line")));
            }
        }

        return lines;

    }

    private ArrayList<Station> getStationList() {

        ArrayList<Station> stations = new ArrayList<>();

        Elements elements1 = document.getElementsByAttributeValue
                ("class", "js-metro-stations t-metrostation-list-table");

        for(Element element : elements1) {
            String numberLine = element.attr("data-line");
            Elements elements2 = element.select("p");

            for (Element element1 : elements2) {
                String name = element1.attr("class", "name").text();
                stations.add(new Station(parseNameStation(name), numberLine));
            }

        }

        return stations;
    }

    private String parseNameStation(String name) {
        int start = name.indexOf(".");
        name = name.substring(++start).trim();
        return name;
    }

    public void makeJson(String path, String name) throws IOException {

        JSONObject json = new JSONObject();
        JSONArray lines = new JSONArray();
        JSONObject stations = new JSONObject();

        for (Line line : lineList) {

            JSONObject lineO = new JSONObject();
            lineO.put("name", line.getNameLine());
            lineO.put("number", line.getNumberLine());
            lines.add(lineO);
        }

        for (Line line : lineList) {
            JSONArray names = new JSONArray();

            for (Station station : stationList) {
                if (station.getNumberLine().equals(line.getNumberLine())){
                    names.add(station.getNameStation());
                }
            }

            stations.put(line.getNumberLine(), names);
        }

        json.put("stations", stations);

        json.put("lines", lines);

        if(!new File(path).exists()) {
            new File(path).mkdir();
        }

        Files.write(Paths.get(path + "/" + name), json.toJSONString().getBytes());
    }
}
