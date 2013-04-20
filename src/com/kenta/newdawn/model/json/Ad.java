package com.kenta.newdawn.model.json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ad {

    public String image;
    public String list_id;

}
