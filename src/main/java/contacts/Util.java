package contacts;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

        if (str == null || str.isEmpty()) {
            return false;
        }
        return pattern.matcher(str).matches();
    }

    public boolean isLocalDate(String str) {
        try {
            LocalDate.parse(str, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public LocalDate parseLocalDate(String str) {
        return LocalDate.parse(str, formatter);
    }

    public boolean validatePhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^\\+?(\\(\\w+\\)|\\w+[ -]\\(\\w{2,}\\)|\\w+)([ -]\\w{2,})*");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}

