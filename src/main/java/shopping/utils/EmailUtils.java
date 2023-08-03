package shopping.utils;

import java.util.regex.Pattern;

public class EmailUtils {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private EmailUtils() {}

    public static boolean isValidEmail(String email) {
        return pattern.matcher(email).matches();
    }
}
