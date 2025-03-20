package contacts;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utility {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static Scanner scanner = new Scanner(System.in);

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        if (str == null || str.isEmpty()) {
            return false;
        }
        return pattern.matcher(str).matches();
    }

    public static boolean isLocalDate(String str) {
        try {
            LocalDate.parse(str, formatter);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String validateNumber(String number) {
        Pattern pattern = Pattern.compile("^\\+?(\\(\\w+\\)|\\w+[ -]\\(\\w{2,}\\)|\\w+)([ -]\\w{2,})*");
        Matcher matcher = pattern.matcher(number);
        if (number.isEmpty()) {
            System.out.println("Wrong number format!");
            return "[no number]";
        } else if (matcher.matches()) {
            return number;
        }
        System.out.println("Wrong number format!");
        return "[no number]";
    }
}

