package sample;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageParser {
    private String url = "";
    private ArrayList<String> subLinks = new ArrayList<>();
    private LocalDate dateFrom;
    private LocalDate dateTo;

    PageParser(LocalDate dateFrom, LocalDate dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    ArrayList<String> getLinks() throws Exception {
        prepareSubLinks();
        ArrayList<String> links = new ArrayList<>();
        for (String query : subLinks) {
            URLConnection connection = new URL(this.url + query).openConnection();
            connection.setRequestProperty("Cache-Control", "no-cache");
            InputStream response = connection.getInputStream();

            try (Scanner scanner = new Scanner(response)) {
                String responseBody = scanner.useDelimiter("\\A").next();
                links.addAll(parsePage(responseBody));
            }
        }

        return links;
    }

    private void prepareSubLinks()
    {
        subLinks.add("2");
        subLinks.add("11");
    }

    private ArrayList<String> parsePage(String pageData)
    {
        Document doc = Jsoup.parse(pageData);
        Elements links = doc.getElementsByTag("a");
        ArrayList<String> preparedLinks = new ArrayList<>();

        for (Element link : links) {
            String linkHref = link.attr("href");
            if (linkHref.contains("")) {
                LocalDate date = LocalDate.parse(linkHref.split("stamp=")[1].split("&")[0]);
                if (isDateValid(date)) {
                    preparedLinks.add("" + linkHref);
                }
            }
        }

        return preparedLinks;
    }

    private boolean isDateValid(LocalDate date)
    {
        boolean valid = true;
        if (dateFrom != null) {
            valid = dateFrom.isBefore(date);
        }
        if (dateTo != null) {
            valid = dateTo.isAfter(date);
        }
        if (dateFrom != null && dateTo != null) {
            valid = dateFrom.isBefore(date) && dateTo.isAfter(date);
        }

        return valid;
    }
}
