package com.fei.upb;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import me.gosimple.nbvcxz.Nbvcxz;
import me.gosimple.nbvcxz.scoring.Result;

public class PasswordStrengthImpl implements PasswordStrength {

    private Zxcvbn tool = new Zxcvbn();
    private Nbvcxz anotherTool = new Nbvcxz();
    private Strength passwordStrength;
    private Result result;
    private String password;

    public PasswordStrengthImpl(String password) {
        passwordStrength = tool.measure(password);
        result = anotherTool.estimate(password);
        this.password = password;
    }

    @Override
    public boolean isSecure() {
        return passwordStrength.getScore() > 2 &&
                result.isMinimumEntropyMet() &&
                StaticDictionary.isPasswordSecure(password);
    }

    @Override
    public boolean isSecureByZxcvbn() {
        return passwordStrength.getScore() > 2;
    }

    @Override
    public String getZxcvbnFeedback() {
        StringBuilder sb = new StringBuilder();

        sb.append("Warning: ");
        sb.append(passwordStrength.getFeedback().getWarning());
        sb.append(System.lineSeparator());
        sb.append("Suggestions: ");
        passwordStrength.getFeedback().getSuggestions().forEach(suggestion -> {
            sb.append(suggestion);
            sb.append(System.lineSeparator());
        });
        sb.append(System.lineSeparator());
        sb.append("Minimal required security level: 3").append(System.lineSeparator());
        sb.append("This password security level: ").append(this.passwordStrength.getScore());

        return sb.toString();
    }

    @Override
    public boolean isSecureByNbvcxz() {
        return result.isMinimumEntropyMet();
    }

    @Override
    public String getNbvcxzFeedback() {
        StringBuilder sb = new StringBuilder();

        sb.append("Warning: ");

        if(result.getFeedback().getWarning() != null) {
            sb.append(result.getFeedback().getWarning());
        }

        sb.append(System.lineSeparator());
        sb.append("Suggestions: ");
        result.getFeedback().getSuggestion().forEach(suggestion -> {
            sb.append(suggestion);
            sb.append(System.lineSeparator());
        });
        sb.append(System.lineSeparator());
        sb.append("Minimal required entropy level: ").append(anotherTool.getConfiguration().getMinimumEntropy()).append(System.lineSeparator());
        sb.append("This password entropy level: ").append(this.getEntropyLevel());

        return sb.toString();
    }

    @Override
    public double getEntropyLevel() {
        return result.getEntropy();
    }

    @Override
    public boolean isVulnurableToDictionaryAttack() {
        return !StaticDictionary.isPasswordSecure(password);
    }

    @Override
    public String finalReport() {
        StringBuilder sb = new StringBuilder();

        sb.append("--------- Password Strength Report 5000 ---------").append(System.lineSeparator());
        sb.append("***********************************").append(System.lineSeparator());
        sb.append("Report for ** Zxcvbn ** algorithm: ").append(System.lineSeparator()).append(System.lineSeparator());
        sb.append(this.getZxcvbnFeedback());
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("***********************************").append(System.lineSeparator());
        sb.append("Report for ** Nbvcxz ** algorithm: ").append(System.lineSeparator()).append(System.lineSeparator());
        sb.append(getNbvcxzFeedback());
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("***********************************").append(System.lineSeparator());
        sb.append("Report for ** Dictionary attack ** algorithm: ").append(System.lineSeparator()).append(System.lineSeparator());
        sb.append("Password is in dictionary? ").append(this.isVulnurableToDictionaryAttack());
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        sb.append("***********************************").append(System.lineSeparator());
        sb.append("Is your password good enough? ").append(System.lineSeparator());
        sb.append(this.isSecure());

        return sb.toString();
    }
}
