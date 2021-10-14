package ru.progwards.java2.lessons.patterns;

import java.util.ArrayList;

public class SpeedGps implements Speed {
    public ArrayList<GPS> gpspoints;
    public GPS lastGPS;            // последняя точка gps

    @Override
    public void parse(String name) {
        gpspoints = SaxParseGpxXml.parsing(name);
    }

    @Override
    public double distBetween(GPS gps1, GPS gps2) {
        double dLat = Math.toRadians(gps2.lat - gps1.lat);
        double dLon = Math.toRadians(gps2.lon - gps1.lon);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(gps1.lat))
                * Math.cos(Math.toRadians(gps2.lat)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return c * 6371000; // радиус земли в метрах
    }

    @Override
    public double speed(GPS gps) {
        double speedNow = (lastGPS != null) ? distBetween(gps, lastGPS)/(gps.time - lastGPS.time) : 0;
        lastGPS = gps;
        return speedNow;
    }

    @Override
    public void track(String name) {
        parse(name);
        for (GPS point : gpspoints) {
            double pointSpeed = speed(point);
            System.out.printf("Текущая скорость - %.2f м/с\n", pointSpeed);
        }
    }

    public static void main(String[] args) {
        SpeedGps speedGPS = new SpeedGps();
        speedGPS.track("geoObjects.gpx");
    }
}