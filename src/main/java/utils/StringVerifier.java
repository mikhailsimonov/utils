package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Проверяет строки
 *
 */
public class StringVerifier {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringVerifier.class.getName());

    public static String checkIsNotEmpty(String parameter) {
        if (parameter != null && !parameter.isEmpty() && parameter != "") {
            LOGGER.info("success!message is not empty");
        } else {
            LOGGER.warn("PARAMETER IS EMPTY");
        }
        return parameter;
    }
    public static Boolean check(String line, String word) {
        return line.contains(word);
    }
}
