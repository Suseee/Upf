package flysystem;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Date implements Comparable<Date> {

    private int day;

    private int month;

    private int year;

    private int secound;
    
    private int minute;

    private int hour;

    public Date(int secound, int minute, int hour, int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.secound = secound;
        this.minute = minute;
        this.hour = hour;
    }

    /**
     * Função que calcula diferença de anos
     *
     * @param d
     * @return diferença
     */
    public int differenceYears(Date d) {
        int difference = this.getYear() - d.getYear();
        return Math.abs(difference);
    }

    /**
     * Função que calcula diferença de meses
     *
     * @param d
     * @return diferença
     */
    public int differenceMonths(Date d) {
        int difference = this.getMonth() - d.getMonth() + (this.getYear() - d.getYear()) * 12;
        return Math.abs(difference);
    }

    /**
     * Função que calcula diferença de dias
     *
     * @param d
     * @return diferença
     */
    public int differenceDays(Date d) {
        int difference = this.getDay() - d.getDay() + (this.getMonth() - d.getMonth()) * 31 + (this.getYear() - d.getYear()) * 365;
        return Math.abs(difference);
    }

    /**
     * Função que verifica se a data é antes
     *
     * @param d
     * @return true or false
     */
    public boolean beforeDate(Date d) {
        if (this.year < d.year) {
            return true;
        } else if (this.year == d.year) {
            if (this.month < d.month) {
                return true;
            } else if (this.month == d.month) {
                if (this.day < d.day) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Função que veifica se é antes da data ou não
     *
     * @param d
     * @return true or false
     */
    public boolean afterDate(Date d) {
        if (this.year == d.year && this.month == d.month && this.day == d.day) {
            return false;
        }
        return !this.beforeDate(d);
    }

    /**
     * Função que verifica se um ano é bissexto
     *
     * @return true or false
     */
    public boolean isLeapYear() {
        return ((this.year % 4 == 0) && ((this.year % 100 != 0) || (this.year % 400 == 0)));
    }

    /**
     * Metodo estatico que verifica se um ano é bissexto
     *
     * @return true or false
     */
    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0)));
    }

    /**
     * Função que calcula o numero de dias de um mês
     *
     * @param month
     * @param year
     * @return numero de dias
     */
    public static int daysMonth(int month, int year) {
        if (month < 1 || month > 12) {
            System.out.println("Mês invalido!");
            return -1;
        }
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (isLeapYear(year)) {
                    return 29;
                }
                return 28;

            default:
                return 31;

        }
    }

//    @Override
//    public String toString() {
//        return this.day + "/" + this.month + "/" + this.year;
//    }

    /**
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int compareTo(Date d) {
        if (this.year == d.getYear() && this.month == d.getMonth() && this.day == d.getDay()) {
            return 0;
        }
        return (this.beforeDate(d) ? -1 : 1);
    }

    /**
     * @return the minute
     */
    public int getMinute() {
        return minute;
    }

    /**
     * @param minute the minute to set
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * @return the secound
     */
    public int getSecound() {
        return secound;
    }

    /**
     * @param secound the secound to set
     */
    public void setSecound(int secound) {
        this.secound = secound;
    }

    /**
     * @return the hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * @param hour the hour to set
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return this.secound + "-" + this.minute + "-" + this.hour + "-" + this.day + "-" + this.month + "-" +  this.year;
    }

}
