package uk.co.edgeorgedev.streamernetwork.twitch;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import uk.co.edgeorgedev.streamernetwork.BuildConfig;
import uk.co.edgeorgedev.streamernetwork.twitch.api.API;
import uk.co.edgeorgedev.streamernetwork.twitch.api.WebAPI;

/**
 * Created by edgeorge on 26/07/15.
 */
public class TwitchAPI {

    private static API api;
    private static WebAPI webApi;

    public static API getAPI(){

        if(api == null){
            api = getRestAdapter("https://api.twitch.tv/kraken").create(API.class);
        }
        return api;
    }

    public static WebAPI getWebAPI(){

        if(webApi == null){
            webApi = getRestAdapter("http://api.twitch.tv/api").create(WebAPI.class);
        }

        return webApi;
    }

    private static RestAdapter getRestAdapter(String url){
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept", "application/vnd.twitchtv.v3+json");
                request.addHeader("Client-ID", BuildConfig.APPLICATION_ID);
            }
        };

        return new RestAdapter.Builder()
                .setEndpoint(url)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(requestInterceptor).build();
    }

}
