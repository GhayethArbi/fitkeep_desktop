package services;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OTPService {
    private static final long OTP_EXPIRATION_DURATION = 10 * 60 * 1000; // 10 minutes in milliseconds

    private static Map<String, OTPDetails> otpMap = new HashMap<>();

    // Method to generate a random OTP of given length
    public static String generateOTP(String userId, int length) {
        String otp = generateRandomOTP(length);
        otpMap.put(userId, new OTPDetails(otp));
        return otp;
    }

    // Method to send OTP via a specified medium (e.g., email, SMS)
    public static void sendOTP(String recipient, String otp) {
        // Logic to send OTP via the specified medium (e.g., email, SMS) goes here
        System.out.println("Sending OTP " + otp + " to " + recipient + "...");
    }

    // Method to validate the OTP entered by the user
    public static boolean validateOTP(String userId, String userOTP) {
        OTPDetails otpDetails = otpMap.get(userId);
        if (otpDetails != null && System.currentTimeMillis() - otpDetails.getTimestamp() <= OTP_EXPIRATION_DURATION) {
            return userOTP.equals(otpDetails.getOtp());
        }
        return false;
    }

    // Method to generate a random OTP
    private static String generateRandomOTP(int length) {
        StringBuilder otp = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10)); // Append a random digit (0-9)
        }
        return otp.toString();
    }

    // Inner class to store OTP details along with timestamp
    private static class OTPDetails {
        private String otp;
        private long timestamp;

        public OTPDetails(String otp) {
            this.otp = otp;
            this.timestamp = System.currentTimeMillis();
        }

        public String getOtp() {
            return otp;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}