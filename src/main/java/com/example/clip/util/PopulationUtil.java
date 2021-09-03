package com.example.clip.util;

import java.util.Random;

public final class PopulationUtil {

    private PopulationUtil() {
    }

    public static final String MINIMUM_NUMBER_OF_USERS_MESSAGE = "The minimum number of new users to create must " +
            "be at least 1";
    public static final String MAXIMUM_NUMBER_OF_USERS_MESSAGE = "The maximum number of new users to create can " +
            "be maximum 50";
    public static final String MINIMUM_NUMBER_OF_PAYMENTS_MESSAGE = "The minimum number of payments per user to " +
            "create must be at least 1";
    public static final String MAXIMUM_NUMBER_OF_PAYMENTS_MESSAGE = "The maximum number of payments per user to " +
            "create can be maximum 50";
    public static final String MINIMUM_PAYMENT_AMOUNT_MESSAGE = "The minimum payment amount must be at least 1 pesos";
    public static final String MAXIMUM_PAYMENT_AMOUNT_MESSAGE = "The maximum payment amount can be maximum 100 pesos";

    public static int getIntegerValueFromParamOrFallback(Integer parameter, Integer fallbackValue) {
        if (parameter == null) {
            return fallbackValue;
        }
        return parameter;
    }

    public static Integer amountPerPayment(Integer maximumPaymentAmount) {
        Random randomPaymentAmount = new Random();
        int paymentAmount = randomPaymentAmount.nextInt(maximumPaymentAmount);
        if (paymentAmount == 0) {
            return 1;
        }
        return paymentAmount;
    }
}
