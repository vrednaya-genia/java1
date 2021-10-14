package ru.progwards.java2.lessons.patterns;

public class Client3 {
    public static void main(String[] args) {
        SpeedGps speedGPS = new SpeedGps();
        FilterSpeedGps filterSpeedGPS = new FilterSpeedGps(speedGPS);
        filterSpeedGPS.track("geoObjects.gpx");
    }
}
