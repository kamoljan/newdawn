package com.kenta.newdawn;

import android.app.Application;
import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.LoaderSettings;


public class NewDawnApplication extends Application {

    /**
     * It is possible to keep a static reference across the
     * application of the image loader.
     */
    private static ImageManager sImageManager;

    public static int sListId;

    @Override
    public void onCreate() {
        super.onCreate();
        LoaderSettings settings = new LoaderSettings.SettingsBuilder().withDisconnectOnEveryCall(
                true).build(this);
        sImageManager = new ImageManager(this, settings);
    }

    /**
     * Convenient method of access the imageLoader
     */
    public static final ImageManager getImageManager() {
        return sImageManager;
    }

    /**
     * Normal image manager settings
     */
    /*
    private void normalImageManagerSettings() {
        imageManager = new ImageManager(this, new SettingsBuilder()
                .withCacheManager(new LruBitmapCache(this))
                .build(this));
    }
    */
    /**
     * There are different settings that you can use to customize
     * the usage of the image loader for your application.
     */
    /*
    @SuppressWarnings("unused")
    private void verboseImageManagerSettings() {
        SettingsBuilder settingsBuilder = new SettingsBuilder();

        //You can force the urlConnection to disconnect after every call.
        settingsBuilder.withDisconnectOnEveryCall(true);

        //We have different types of cache, check cache package for more info
        settingsBuilder.withCacheManager(new LruBitmapCache(this));

        //You can set a specific read timeout
        settingsBuilder.withReadTimeout(30000);

        //You can set a specific connection timeout
        settingsBuilder.withConnectionTimeout(30000);

        //You can disable the multi-threading ability to download image
        settingsBuilder.withAsyncTasks(false);

        //You can set a specific directory for caching files on the sdcard
//        settingsBuilder.withCacheDir(new File("/something"));


        //Setting this to false means that file cache will use the url without the query part
        //for the generation of the hashname
        settingsBuilder.withEnableQueryInHashGeneration(false);

        LoaderSettings loaderSettings = settingsBuilder.build(this);
        imageManager = new ImageManager(this, loaderSettings);
    }
    */

}

