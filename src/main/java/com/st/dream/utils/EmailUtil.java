package com.st.dream.utils;

import com.st.dream.exception.DreamException;
import com.st.dream.exception.ErrorCodes;
import com.st.dream.exception.GeneralErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class EmailUtil {

    private static Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    private static final String IPV4_REG = "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$";

    private static final String IPV6_REG = "^([\\da-fA-F]{1,4}:){7}[\\da-fA-F]{1,4}$";

    private EmailUtil() {

    }

    public static void validateEmail( String email) throws DreamException {
        if (StringUtils.isEmpty(email)) {
            throw new DreamException(GeneralErrorCodes.ERROR_400, "Email should not be empty!");
        }
        //Email addresses are 7-bit ascii; each character has a value from 1 to 127
        for (int i = 0; i < email.length(); i++) {
            int ascii = (int) email.charAt(i);
            if (ascii < 1 || ascii > 127) {
                throw new DreamException(GeneralErrorCodes.ERROR_400, "The email has invalid character.");
            }
        }
        //The email has a localpart on the left of an @, the domain on the right. Neither the localpart nor the domain may be empty.
        if (!email.contains("@")) {
            throw new DreamException(GeneralErrorCodes.ERROR_400, "The email must contain an @.");
        }
        String[] split = email.split("@", -1);
        if (split.length > 2) {
            throw new DreamException(GeneralErrorCodes.ERROR_400, "The email can only contain one @.");
        }
        if (StringUtils.isEmpty(split[0])) {
            throw new DreamException(GeneralErrorCodes.ERROR_400, "The left part of the @ cannot be empty.");
        }
        if (StringUtils.isEmpty(split[1])) {
            throw new DreamException(GeneralErrorCodes.ERROR_400, "The right part of the @ cannot be empty.");
        }

        //The localpart must be 64 characters or less.
        String localpart = split[0];
        if (localpart.length() > 64) {
            throw new DreamException(GeneralErrorCodes.ERROR_400, "The left part of the @ must be 64 characters or less.");
        }
        //The localpart can consist of labels separated by dots but there can not be two successive dots, nor can it start or end with a dot.
        if (localpart.contains("..")) {
            throw new DreamException(GeneralErrorCodes.ERROR_400, "The left part of the @ can not contains two successive dots.");
        }
        if (localpart.startsWith(".")) {
            throw new DreamException(GeneralErrorCodes.ERROR_400, "The left part of the @ cannot start with a dot.");
        }
        if (localpart.endsWith(".")) {
            throw new DreamException(GeneralErrorCodes.ERROR_400, "The left part of the @ cannot end with a dot.");
        }
        if (localpart.contains(".")) {
            String[] labels = localpart.split("\\.");
            for (String label : labels) {
                if (!label.contains("\"")) {
                    //Unquoted part must have at least one character.  --
                    if (!label.matches("[a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]+")) {
                        throw new DreamException(GeneralErrorCodes.ERROR_400, "Unquoted part has invalid characters.");
                    }
                } else {
                    if (!label.startsWith("\"") || !label.endsWith("\"")) {
                        throw new DreamException(GeneralErrorCodes.ERROR_400, "Quoted labels must have a \" at the beginning and end.");
                    }
                }
            }

        }
/**
 //Tab, CR, LF, ", [, \, ^ must not exist in the email address unescaped.
 if (email.contains("\\t") || email.contains("\\r") || email.contains("\\n") || email.contains("\\") || email.contains("^")) {
 throw new DreamException(GeneralErrorCodes.ERROR_400, "Tab, CR, LF, \", [, \\, ^ must not exist in the email address unescaped.");
 }
 //Only CR and LF can't be escaped with a \ before the character.
 if (email.contains("\\\\r") || email.contains("\\\\n")) {
 throw new DreamException(GeneralErrorCodes.ERROR_400, "CR, LF can not be secaped with a \\ before the character.");
 }
 */
        String domain = split[1];
        if (domain.matches(IPV4_REG) || domain.matches(IPV6_REG)) {
            return;
        }
        //The domain can be bracketed, unbracketed, or an IP address..
        if (!domain.contains("[")) {
            if (domain.length() >= 253) {
                throw new DreamException(GeneralErrorCodes.ERROR_400, "The right part of @ must be less than 253 characters.");
            }
            if (!domain.contains(".")) {
                throw new DreamException(GeneralErrorCodes.ERROR_400, "The right part of @ must contain at least one dot.");
            }
            if (domain.contains("..")) {
                throw new DreamException(GeneralErrorCodes.ERROR_400, "The right part of @ can not contains two successive dots.");
            }
            if (domain.startsWith(".")) {
                throw new DreamException(GeneralErrorCodes.ERROR_400, "The right part of @ cannot start with a dot.");
            }
            if (domain.endsWith(".")) {
                throw new DreamException(GeneralErrorCodes.ERROR_400, "The right part of @ cannot end with a dot.");
            }

        } else {
            //String s = StringEscapeUtils.escapeJava(domain);
            if (!domain.startsWith("[")) {
                throw new DreamException(GeneralErrorCodes.ERROR_400, "A bracketed right part of @ must start with [.");
            }
            if (!domain.endsWith("]")) {
                throw new DreamException(GeneralErrorCodes.ERROR_400, "A bracketed right part of @ must end with ].");
            }
            if (domain.length() >= 247) {
                throw new DreamException(GeneralErrorCodes.ERROR_400, "A bracketed right part of @ must be less than 247 characters.");
            }
            domain = domain.replace("[", "").replace("]", "");
            //CR, LF, {, }, |, and ^ are not allowed unescaped.
        }

        String[] labels = domain.split("\\.");
        for (int i = 0; i < labels.length; i++) {
            String label = labels[i];
            if (label.length() >= 63) {
                throw new DreamException(GeneralErrorCodes.ERROR_400, "The length of the right part of @ is excessive .");
            }
            if (!label.matches("[a-zA-Z0-9!#$%&'*+-/=?^_`{|}~]+")) {
                throw new DreamException(GeneralErrorCodes.ERROR_400, "The right part of @ contains invalid characters.");
            }
            if (label.contains("--")) {
                throw new DreamException(GeneralErrorCodes.ERROR_400, "The right part of @ can not contains two successive hyphens.");
            }
            if (label.startsWith("-")) {
                throw new DreamException(GeneralErrorCodes.ERROR_400, "The right part of @ is invalid.");
            }
            if (label.endsWith("-")) {
                throw new DreamException(GeneralErrorCodes.ERROR_400, "The right part of @ is invalid.");
            }
            if (i == labels.length - 1) {
                if (!label.matches("[a-zA-Z]+")) {
                    throw new DreamException(GeneralErrorCodes.ERROR_400, "The right part of @ contains invalid characters.");
                }
            }
        }
    }
}
