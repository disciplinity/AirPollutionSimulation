package utility;

import buildings.EnvironmentalProtectionDataCenter;

/**
 * Created by Daiy on 02.01.2018.
 */
public class Flag {

    private volatile boolean timerIsOff = true;
    private EnvironmentalProtectionDataCenter environmentalProtectionDataCenter;

    public Flag(EnvironmentalProtectionDataCenter environmentalProtectionDataCenter) {
        this.environmentalProtectionDataCenter = environmentalProtectionDataCenter;
    }

    public void turnTimerOn() {
        timerIsOff = false;
    }

    public synchronized void turnTimerOff() {
        timerIsOff = true;
        environmentalProtectionDataCenter.allowCarsToDrive();
    }

    public boolean timerIsOff() {
        return timerIsOff;
    }
}
