package ua.epam.payments.payments.model.util;


import java.util.concurrent.ThreadLocalRandom;



public class CardGeneration {

    public static String generateCardNumber() {
        long randomNum = ThreadLocalRandom.current().nextLong(1,1_0000_0000_0000L);

        if (String.valueOf(randomNum).length() < 12) {
            return String.format("1234%012d", randomNum);
        }

        return "1234" + randomNum;
    }
}
