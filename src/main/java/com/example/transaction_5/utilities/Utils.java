package com.example.transaction_5.utilities;

public class Utils {

    public static boolean isPhoneValid(String phone) {
        String pattern = "^\\+998[0-9]{2}[0-9]{7}$";
        if (phone == null || !phone.matches(pattern)) {
            return false;
        }
        return true;
    }

    public static Boolean isPasswordValid(String password) {
        /**
         * have at least 8 characters
         * contain at least one letter
         * contain at least one digit
         * contain at least one special character (@, $, !, %, *, #, ?, &)
         * */
        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
        return password.matches(pattern);
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public static Boolean isVisa(String cardNumber) {
        if (cardNumber.substring(0, 4).equals("4200"))
            return true;
        else
            return false;
    }

    public static Boolean   isHumo(String cardNumber) {
        if (cardNumber.substring(0, 4).equals("9860"))
            return true;
        else
            return false;
    }

}
