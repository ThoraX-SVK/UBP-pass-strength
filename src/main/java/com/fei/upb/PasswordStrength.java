package com.fei.upb;

public interface PasswordStrength {

    boolean isSecure();

    boolean isSecureByZxcvbn();

    String getZxcvbnFeedback();

    boolean isSecureByNbvcxz();

    String getNbvcxzFeedback();

    double getEntropyLevel();

    boolean isVulnurableToDictionaryAttack();

    String finalReport();
}
