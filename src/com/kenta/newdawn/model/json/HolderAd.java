package com.kenta.newdawn.model.json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import android.os.Parcelable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HolderAd {

    //public String image;
    //public String list_id;
    
    public String list_id;
    public String image;
    public String subject;
    public String body;
    //public String category;
    //public String company_ad;
    public String price;
    public String date;
    public String name;
    public String phone;

}
