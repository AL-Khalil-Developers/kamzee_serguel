package com.kamzee.app.app;


public class Config {

    // Parse Server
    public static final String SERVER_URL = "https://parseapi.back4app.com/";
    public static final String LIVE_QUERY_URL = "wss://kamzeelive.b4a.io";
    static final String SERVER_APP_ID = "GwrYH5UkwmwAdEvKcpSLb5GwZE7zoZ6BBjyPBR5g";
    static final String SERVER_CLIENT_KEY = "LzlvBAVsDQpLcZJZ6FW813mZSFFClJgc8z6HtNDI";

    // FlutterWave
    public static final String FLUTTERWAYE_PUBLISH_KEY = "FLWPUBK-b3aa7d507e19a3d68ecfadbace19b569-X";
    public static final String FLUTTERWAYE_ENCRYPTION_KEY = "92a7ebb56ffcd02c727dc313";

    // PayPal
    public static final String PAYPAL_CLIENT_ID = "AdNkriWaVp6rFJeZJiL52S93QTHhSK1FABa9QaL4Y3CNW6sxIdTwHns7xqDCZ0EA3-QlQfhFWDjrj1ps";

    // Enable or disable logins system
    public static final boolean EMAIL_LOGIN = true;
    public static final boolean PHONE_LOGIN = true;
    public static final boolean GOOGLE_LOGIN = true;
    public static final boolean FACEBOOK_LOGIN = true;
    // Enable or Disable Payment Systems
    public static final boolean PAYPAL_ENABLED = true;
    public static final boolean FLUTTER_WAVE_ENABLED = false;
    // Extra features to add in payment methods
    public static final boolean PAYPAL_CREDIT_CARD_ENABLED = true;
    // Exchange your tokens
    public static final int MinTokenExchange = 200; // Minimum amount of diamonds allowed to exchange
    public static final int TokenExchangeRate = 10; // 10 diamonds equal to 100 Coins
    // Withdraw tokens to PayPal
    public static final int MinTokenWithdraw = 40000; // Minimum amount of diamonds allowed to exchange
    public static final int WithdrawExchangeRate = 400; // 400 diamonds equal to 1 USD

    // Enable or Disable paid messages.
    public static final boolean isPaidMessagesActivated = false;

    // Push notifications
    public static final String CHANNEL = "global";

    // Agora API
    public static final String AGORA_APP_ID = "4bc850ffe2f0499f8327afdd8012c9c1";

    // Instagram API
    public static final String INSTAGRAM_APP_ID = "139259244375848";
    public static final String INSTAGRAM_APP_SECRET = "e20f55995675436b8cb54135f35ccfb3";
    public static final String INSTAGRAM_REDIRECT_URI = "https://www.angopapo.com/";

    // Google Admob
    static final String HOME_BANNER_ADS = "ca-app-pub-5394730926722848/8594612640";
    static final String REWARDED_ADS= "ca-app-pub-5394730926722848/4522741352";
    static final String NEARBY_NATIVE_ADS = "ca-app-pub-8614234010137830/4913013508";
    static final String ENCOUNTERS_NATIVE_ADS = "ca-app-pub-8614234010137830/4274912773";

    // Encounters crush
    public static final boolean isCrushCreditNeeded = true;
    public static final boolean isCrushAdsEnabled = false;
    public static final int CrushAdsLimitPerDay = 5;

    // Credits needed to activate features
    public static final int CRUSH_CREDIT_NEEDED = 50;
    public static int TYPE_RISE_UP = 50;
    public static int TYPE_GET_MORE_VISITS = 100;
    public static int TYPE_ADD_EXTRA_SHOWS = 100;
    public static int TYPE_SHOW_IM_ONLINE = 100;
    public static int TYPE_3X_POPULAR = 200;

    // Amount of days to activate features
    public static int DAYS_TO_ACTIVATE_FEATURES = 7;

    // Google Play In-app Purchases IDs
    public static final String CREDIT_5000 = "kamzee.5000.coin";
    public static final String CREDIT_20000 = "kamzee.20000.coin";
    public static final String CREDIT_50000 = "kamzee.50000.coin";
    public static final String CREDIT_150000 = "kamzee.150000.coin";
    public static final String CREDIT_400000 = "kamzee.400000.coin";
    public static final String CREDIT_800000 = "kamzee.800000.coin";
    public static final String CREDIT_1600000 = "kamzee.1600000.coins";
    public static final String CREDIT_1200000 = "kamzee.1200000.coins";

    public static final String PAY_LIFETIME = "kamzee.pay.lifetime";

    //Google Play In-app Subscription IDs
    public static final String SUBS_3_MONTHS = "kamzee.3.months";
    public static final String SUBS_1_WEEK = "kamzee.1.week";
    public static final String SUBS_1_MONTH = "kamzee.1.month";
    public static final String SUBS_6_MONTHS = "kamzee.6.months";

    // Web links for help, privacy policy and terms of use.
    public static final String HELP_CENTER = "https://kamzeelive.com/helppage2.html";
    public static final String PRIVACY_POLICY = "https://kamzeelive.com/privacy_policy.html";
    public static final String TERMS_OF_USE = "https://kamzeelive.com/terms_and_conditions.html";
    public static final String TERMS_OF_USE_IN_APP = "https://kamzeelive.com/terms_and_conditions.html";

    // Enable or Disable Ads and Premium.
    public static final boolean isAdsActivated = false;
    public static final boolean isPremiumEnabled = true;
    public static final boolean isNearByNativeAdsActivated = false;
    public static final boolean isEncountersNativeAdsActivated = true;

    // Application setup
    public static final String bio = "Hey! i'm using kamzee!";
    public static final int WelcomeCoin = 10;
    public static final int WelcomeCredit = 10;
    public static final int MinimumAgeToRegister = 18;
    public static final int MaximumAgeToRegister = 80;
    public static final int MaxUsersNearToShow = 100;
    public static final int DistanceForRealBadge = 1;
    public static final int DistanceForRealKm = 20;
    public static final int MinDistanceBetweenUsers = 2;
    public static final int MaxDistanceBetweenUsers = 1000;
    public static final double DistanceBetweenUsersLive = 1000;
    public static final boolean ShowBlockedUsersOnQuery = true;
    public static final boolean isVideoCallEnabled = true;
    public static final boolean isVoiceCallEnabled = true;
    public static final int ShowNearbyNativeAdsAfter = 9;
    public static final int ShowEncountersNativeAdsAfter = 2;
    public static final int freeMessagesInTotal = 10;
    public static final int freeMessagesPerDay = 5;

    // Enable or Disable Fake messages.
    public static final boolean isFakeMessagesActivated = false;
    public static final String defaultFakeMessage = "Hello, how are you ?";
}