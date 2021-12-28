package io.projects.Covidtracker.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CoronaVirusDataService {
    public static String url="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/01-01-2021.csv";
    @PostConstruct
    @Scheduled(cron = "* * * * * *")
   public void FetchData() throws IOException, InterruptedException {
       HttpClient client = HttpClient.newHttpClient();
       HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
       HttpResponse <String> res= client.send(request, HttpResponse.BodyHandlers.ofString());
       System.out.println(res.body());
        StringReader in= new StringReader(res.body());
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withHeader().parse(in);
        for (CSVRecord record : records) {
            String state = record.get("Province_State");
            System.out.println(state);
        }

    }
}
