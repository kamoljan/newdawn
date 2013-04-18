package com.kenta.newdawn.model.json;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ad {

    public String image;
    /*
    private int filtered;

    public int getFiltered() {
        return filtered;
    }

    public void setFiltered(int filtered) {
        this.filtered = filtered;
    }
    */

    /*
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
     */
}
