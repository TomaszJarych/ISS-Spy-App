package tj.javadeveloper.issspyapp.commons.utils;


import tj.javadeveloper.issspyapp.domain.dto.LocationDto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocationUtils {
    private static final double EARTH_RADIUS_IN_KM = 6371;

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

    public static void main(String[] args) {
        LocationDto dto1 = LocationDto.builder()
                .latitude(40.366633)
                .longitude(74.640832)
                .build();
        LocationDto dto2 = LocationDto.builder()
                .latitude(42.443087)
                .longitude(76.488707)
                .build();

        Double result = distanceInKm(dto1, dto2) * 0.62;
        MathContext mathContext = new MathContext(6, RoundingMode.HALF_UP);
        System.out.println(BigDecimal.valueOf(result).round(mathContext));
    }
}
