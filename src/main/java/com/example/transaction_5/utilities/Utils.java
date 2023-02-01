package com.example.transaction_5.utilities;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class Utils {

    public static Boolean isPhoneValid(String phone){

        //possible phone number regex with validates only this format {+998901234567} not {+998 90 123 45 67}
        // this means without hyphen or space
        String patter = "^(\\+998)([0-9]{2})([0-9]{7})$";
        return phone.matches(patter);
    }

    public static Boolean isPasswordValid(String password){
        /**
         * have at least 8 characters
         * contain at least one letter
         * contain at least one digit
         * contain at least one special character (@, $, !, %, *, #, ?, &)
         * */
        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
        return password.matches(pattern);
    }
}
