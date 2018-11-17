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

    public PasswordStrengthImpl(String password) {
        passwordStrength = tool.measure(password);
        result = anotherTool.estimate(password);
    }

    @Override
    public int getPasswordStrengthLevel() {
        return passwordStrength.getScore();
    }

    @Override
    public boolean isSecure() {
        return passwordStrength.getScore() > 2;
    }

    @Override
    public double getOfflineCrackingTime() {
        return passwordStrength.getCrackTimeSeconds().getOfflineSlowHashing1e4perSecond();
    }

    @Override
    public double getOnlineCrackingTime() {
        return passwordStrength.getCrackTimeSeconds().getOnlineNoThrottling10perSecond();
    }

    @Override
    public String getOfflineCrackingTimeString() {
        return passwordStrength.getCrackTimesDisplay().getOfflineSlowHashing1e4perSecond();
    }

    @Override
    public String getOnlineCrackingTimeString() {
        return passwordStrength.getCrackTimesDisplay().getOnlineNoThrottling10perSecond();
    }
}
