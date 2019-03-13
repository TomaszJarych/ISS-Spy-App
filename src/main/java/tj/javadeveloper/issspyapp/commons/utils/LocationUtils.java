package tj.javadeveloper.issspyapp.commons.utils;


import tj.javadeveloper.issspyapp.domain.dto.LocationDto;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class LocationUtils {
    private static final double EARTH_RADIUS_IN_KM = 6371;
    private static final int KM_TO_METERS = 1000;
    private static final double METERS_PER_SECUND_TO_KM_PER_HOUR = 3.6;

    public static LocalDateTime toLocalDateTimeFromTimestamp(Long timestamp) {
        LocalDateTime dateTime =
                LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
        return dateTime;
    }

    public static Double distanceInKm(LocationDto location1, LocationDto location2) {

        double latitudeFrom = Math.toRadians(location1.getLatitude());
        double latitudeTo = Math.toRadians(location2.getLatitude());
        double longitudeFrom = Math.toRadians(location1.getLongitude());
        double longitudeTo = Math.toRadians(location2.getLongitude());

        double distance = Math.acos(Math.sin(latitudeFrom) * Math.sin(latitudeTo)
                + Math.cos(latitudeFrom) * Math.cos(latitudeTo) * Math.cos(longitudeTo - longitudeFrom))
                * EARTH_RADIUS_IN_KM;

        return distance;
    }

    public static Double calculateTotalDistanceInKm(List<LocationDto> list) {
        if (list.size() <= 1) {
            return 0.0;
        }
        double result = 0.0;
        for (int i = 1; i < list.size(); i++) {
            result += distanceInKm(list.get(i - 1), list.get(i));
        }
        return result;
    }

    public static double calculateCurrentSpeedInKmPerHour(LocationDto location1, LocationDto location2) {
        if (location1.equals(location2)) {
            return 0.0;
        }

        double distanceInKm = distanceInKm(location1, location2);
        double distanceInMeters = distanceInKm * KM_TO_METERS;

        long calculateTimeInSeconds = location2.getTime() - location1.getTime();
        double calculateTotalSpeedInMetersPerSecond = distanceInMeters / calculateTimeInSeconds;

        BigDecimal result = BigDecimal.valueOf(calculateTotalSpeedInMetersPerSecond * METERS_PER_SECUND_TO_KM_PER_HOUR)
                .round(new MathContext(7, RoundingMode.HALF_UP));

        return result.doubleValue();
    }

    public static String getIPFromRequest(HttpServletRequest servletRequest) {
        String remoteAddr = "";
        if (servletRequest != null) {
            remoteAddr = servletRequest.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = servletRequest.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
}
