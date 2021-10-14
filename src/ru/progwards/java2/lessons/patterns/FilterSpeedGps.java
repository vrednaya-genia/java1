package ru.progwards.java2.lessons.patterns;

public class FilterSpeedGps implements Speed {
    private SpeedGps speedGPS;
    double mathExp;                          // мат ожидание
    double sigma;                            // среднеквадратичное отклонение
    double[] speeds;                         // 50 скоростей для вычисления 3 сигм

    FilterSpeedGps(SpeedGps speedGPS) {
        this.speedGPS = speedGPS;
        speeds = new double[50];
    }

    @Override
    public void parse(String name) {
        speedGPS.parse(name);
    }

    @Override
    public double distBetween(GPS gps1, GPS gps2) {
        return speedGPS.distBetween(gps1, gps2);
    }

    @Override
    public double speed(GPS gps) {
        return speedGPS.speed(gps);
    }

    @Override
    public void track(String name) {
        parse(name);
        // первые 50 точек для статистики
        for (int i=0; i < 50; i++) {
            double pointSpeed = speed(speedGPS.gpspoints.get(i));
            speeds[i] = pointSpeed;
        }
        calcSigma();

        for (int i = 50; i < speedGPS.gpspoints.size()-1; i++) {
            double pointSpeed = speed(speedGPS.gpspoints.get(i));
            if ((pointSpeed > mathExp - 3*sigma) && (pointSpeed < mathExp + 3*sigma)) {
                System.out.printf("Текущая скорость - %.2f м/с\n", pointSpeed);
            } else {
                System.out.printf("Выход за пределы 3 сигм - %.2f м/с\n", pointSpeed);
            }
        }
    }

    public void calcSigma() {
        // мат ожидание
        double[] speedsTemp = speeds;
        double sum = 0;
        for (int i = 0; i < 50; i++) {
            sum += speedsTemp[i];
        }
        mathExp = sum / 50;
        System.out.printf("Мат ожидание " + "%.3f" + " м/с\n", mathExp);

        // дисперсия
        speedsTemp = speeds;
        sum = 0;
        for (int i = 0; i < 50; i++) {
            double diff = mathExp - speedsTemp[i];
            sum += diff * diff;
        }
        double dispersion = sum / 50;
        System.out.printf("Дисперсия " + "%.3f" + " м/с\n", dispersion);

        // среднеквадратичное отклонение
        sigma = Math.sqrt(dispersion);
        System.out.printf("Отклонение " + "%.3f" + " м/с\n" + "3 сигма " + "%.3f" + " м/с\n", sigma, 3 * sigma);
    }
}
