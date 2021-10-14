package ru.progwards.java2.lessons.patterns;

public interface Speed {
    void parse(String name);                // парсинг gps-трекера в xml
    double distBetween(GPS gps1, GPS gps2); // расстояние между двумя точками в метрах
    double speed(GPS gps);                  // скорость между двумя точками
    void track(String name);                // обработка точек
}
