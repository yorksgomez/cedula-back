package eci.escuelaing.cedula2.controller;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class UtilController {
    public static Stream<Character> getRandomSpecialChars(int count) {
        Random random = new SecureRandom();
        IntStream specialChars = random.ints(count, 65, 90);
        return specialChars.mapToObj(data -> (char) data);
    }

    public static String generateSecureRandomPassword() {
        Stream<Character> pwdStream = Stream.concat(UtilController.getRandomSpecialChars(10), UtilController.getRandomSpecialChars(2));
        List<Character> charList = pwdStream.collect(Collectors.toList());
        Collections.shuffle(charList);
        String password = charList.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }
}
