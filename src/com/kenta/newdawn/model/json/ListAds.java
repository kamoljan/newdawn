package com.kenta.newdawn.model.json;

import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * {
 *    filtered: "66",
 *    ads: [
 *        {
 *           image: "http://54.251.249.255:20001/thumbs/04/0477901857.jpg",
 *           subject: "Asdfasdfasdfasdfasdfasdf adsfasdfasdf",
 *           category: "3040",
 *           company_ad: "0",
 *           list_id: "8000002",
 *           price: "",
 *           date: "Hari ini 02:56",
 *           name: "naomi"
 *       },
 *       ..
 *    ]
 * }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListAds {

    public int filtered;
    public List<Ad> ads;

    /*
    public int getFiltered() {
        return filtered;
    }

    public void setFiltered(int filtered) {
        this.filtered = filtered;
    }

    public List<Ad> getResults() {
        return results;
    }

    public void setResults(List<Ad> results) {
        this.results = results;
    }
    */
}
