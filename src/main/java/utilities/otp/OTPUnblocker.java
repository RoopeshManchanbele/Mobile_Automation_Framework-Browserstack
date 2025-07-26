package utilities.otp;

public class OTPUnblocker {

    //    private static final String OTPUNBLOCK = "/public/support/otp-unblock";
//    private static final String ACCOUNT = "/utility";
//    private static final String VERSION = "/v1";

    private static final String VAGATOR_API = "api-t2.fyers.in";
    private static final String VAGATOR_OTPUNBLOCK = "/otp_unblock";
    private static final String VAGATOR_ACCOUNT = "/vagator";
    private static final String VAGATOR_VERSION = "/v2";

    private static final String OTPUNBLOCK = "/private/qa/otp-unblock";
    private static final String ACCOUNT = "/utility";
    private static final String VERSION = "/v1";

    private static final String SEND_LOGIN_OTP = "/send_login_otp";
    private static final String RESEND_OTP = "/resend_otp";
    private static final String VERIFY_OTP = "/verify_otp";
    private static final String VERIFY_PIN = "/verify_pin";
    private static final String FUNDS = "/funds";


    public static String OTPUnblock() {
        return ACCOUNT + VERSION + OTPUNBLOCK + "?sub=otp_unblock&fy_id=";
    }

    public static String otpLimitationRemover() {
        return VAGATOR_API + VAGATOR_ACCOUNT + VAGATOR_VERSION + VAGATOR_OTPUNBLOCK + "?sub=otp_unblock&fy_id=";
    }

    public static String SendLoxginOTP() {
        return SEND_LOGIN_OTP;
    }

    public static String ReSendOTP() {
        return RESEND_OTP;
    }

    public static String VerifyOTP() {
        return VERIFY_OTP;
    }

    public static String VerifyPIN() {
        return VERIFY_PIN;
    }

    public static String Funds() {
        return FUNDS;
    }

}
