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
public class ListAd {

    public int filtered;
    public List<ParcelableAd> ads;
    
}
