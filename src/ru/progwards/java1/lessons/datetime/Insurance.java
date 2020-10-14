package ru.progwards.java1.lessons.datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Insurance {
    public static enum FormatStyle {SHORT, LONG, FULL} // стиль формата даты-времени
    private ZonedDateTime start; // дата-время начала действия страховки
    private Duration duration = null; // продолжительность действия

    public Insurance(ZonedDateTime start) {
        this.start = start;
    }

    public Insurance(String strStart, FormatStyle style) {
        ZoneId defaultZone = ZoneId.of("Europe/Moscow");
        DateTimeFormatter dtf;
        switch (style) {
            case SHORT:
                dtf = DateTimeFormatter.ISO_LOCAL_DATE;
                LocalDate ldate = LocalDate.parse(strStart, dtf);
                LocalTime ltime = LocalTime.of(0,0,0);
                LocalDateTime temp1 = LocalDateTime.of(ldate, ltime);
                this.start = temp1.atZone(defaultZone);
                break;
            case LONG:
                dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                LocalDateTime temp2 = LocalDateTime.parse(strStart, dtf);
                this.start = temp2.atZone(defaultZone);
                break;
            case FULL:
                dtf = DateTimeFormatter.ISO_ZONED_DATE_TIME;
                this.start = ZonedDateTime.parse(strStart, dtf);
                break;
            default:
                System.out.println("Формат даты задан не верно");
        }
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setDuration(ZonedDateTime expiration) {
        duration = Duration.between(start, expiration);
    }

    public void setDuration(int months, int days, int hours) {
        duration = Duration.ofHours(hours).plusDays(days).plusDays(months*30);
    }

    public void setDuration(String strDuration, FormatStyle style) {
        switch (style) {
            case SHORT:
                long millis = Long.parseLong(strDuration);
                duration = Duration.ofMillis(millis);
                break;
            case LONG:
                int years = Integer.parseInt(strDuration.substring(0,4));
                int months = Integer.parseInt(strDuration.substring(5,7));
                int days = Integer.parseInt(strDuration.substring(8,10));
                int hours = Integer.parseInt(strDuration.substring(11,13));
                int minutes = Integer.parseInt(strDuration.substring(14,16));
                int seconds = Integer.parseInt(strDuration.substring(17,19));
                duration = Duration.ofSeconds(seconds).plusMinutes(minutes).plusHours(hours);
                duration = duration.plusDays(days).plusDays(months*30).plusDays(years*365);
                break;
            case FULL:
                duration = Duration.parse(strDuration);
                break;
            default:
                System.out.println("Формат задан не верно");
        }
    }

    public boolean checkValid(ZonedDateTime dateTime) {
        if (dateTime.isBefore(start)) {
            return false;
        }
        if (duration == null || dateTime.isEqual(start)) {
            return true;
        }
        if (dateTime.isAfter(start)) {
            long seconds = duration.getSeconds();
            ZonedDateTime end = start.plusSeconds(seconds);
            return dateTime.isBefore(end);
        }
        return false;
    }

    @Override
    public String toString() {
        String validStr;
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.of("Europe/Moscow"));
        if (checkValid(zdt)) {
            validStr = " is valid";
        } else {
            validStr = " is not valid";
        }
        return "Insurance issued on " + start + validStr;
    }

    public static void main(String[] args) {
        LocalDateTime ldt = LocalDateTime.of(2020, 1,1,10,10,10);
        Insurance t = new Insurance(ZonedDateTime.parse("2021-01-22T21:42:14.548223+03:00[Europe/Moscow]"));
        //Insurance t = new Insurance("2020-02-03T02:03:04", FormatStyle.LONG);
        //t.setDuration(12,5,5);
        ZonedDateTime zdt = ZonedDateTime.parse("2020-10-24T21:42:14.548249+03:00[Europe/Moscow]");
        System.out.println(t.checkValid(zdt));
        System.out.println(t.toString());
    }
}
