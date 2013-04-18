package com.kenta.newdawn;

public class Config {

    // APY config
    public static final String APY_BASE_URL = "http://54.251.249.255:54321/api/v1";
    public static final String APY_API_KEY = "107d769b4e9c97c96433d85761c37834be6afdce";
    public static final String GET_APY_REGIONS_URL = APY_BASE_URL + "/regions.json" + "?key=" + APY_API_KEY;
    public static final String GET_APY_LIST_URL = APY_BASE_URL + "/list.json" + "?key=" + APY_API_KEY;
}
