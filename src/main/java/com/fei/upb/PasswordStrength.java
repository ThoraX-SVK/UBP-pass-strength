package com.fei.upb;

public interface PasswordStrength {

    int getPasswordStrengthLevel();

    boolean isSecure();

    double getOfflineCrackingTime();

    double getOnlineCrackingTime();

    String getOfflineCrackingTimeString();

    String getOnlineCrackingTimeString();
}
