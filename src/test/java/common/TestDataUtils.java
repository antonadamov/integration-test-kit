package common;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataUtils {

    public static String getRandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static int getRandomNumber() {
        return getRandomNumber(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static int getRandomNumber(int start, int end) {
        return ThreadLocalRandom.current().nextInt(start, end);
    }

    public static Map<String, Object> getUserVariables() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("firstName", TestDataUtils.getRandomString(7));
        variables.put("lastName", TestDataUtils.getRandomString(7));
        variables.put("age", TestDataUtils.getRandomNumber(20, 99));
        return variables;
    }


}
