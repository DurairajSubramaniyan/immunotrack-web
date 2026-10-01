package utils;

import org.jboss.aerogear.security.otp.Totp;

public class TotpUtil {

    /**
     * Generates a 6-digit TOTP code for Google Authenticator based on the base32 secret key.
     *
     * @param secretKey The secret seed key provided during 2FA setup (spaces will be stripped).
     * @return The 6-digit code as a String.
     */
    public static String generateCurrentOtp(String secretKey) {
        if (secretKey == null || secretKey.trim().isEmpty()) {
            throw new IllegalArgumentException("TOTP Secret key cannot be null or empty");
        }
        // Remove any spaces or dashes that users might paste
        String cleanedSecret = secretKey.replace(" ", "").replace("-", "").trim();
        Totp totp = new Totp(cleanedSecret);
        return totp.now();
    }
}
