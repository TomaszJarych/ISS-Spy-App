package tj.javadeveloper.issspyapp.commons.utils;

public class Constants {

    public static final String REST_API_DEFAULT_PATH = "/api/v1";

    public static final String OPEN_NOTIFY_ISS_CURRENT_LOCATION_URL = "http://api.open-notify.org/iss-now.json";

    private static final String OPEN_NOTIFY_ISS_PREDICTED_PASSES = "http://api.open-notify.org/iss-pass.json?";

    private static final String ISS_PREDICTED_PASSES_LATITUDE = "lat=";

    private static final String ISS_PREDICTED_PASSES_LONGITUDE = "&lon=";

    public static String getISSPredictedPassesURL(String latitude, String longitude) {

        return OPEN_NOTIFY_ISS_PREDICTED_PASSES
                + ISS_PREDICTED_PASSES_LATITUDE + latitude
                + ISS_PREDICTED_PASSES_LONGITUDE + longitude;
    }

    public static final String LOCATION_FROM_IP_URL = "http://extreme-ip-lookup.com/json/";
}
