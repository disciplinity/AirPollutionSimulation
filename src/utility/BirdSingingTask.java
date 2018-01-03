package utility;

import buildings.EnvironmentalProtectionDataCenter;

/**
 * Created by Daiy on 03.01.2018.
 */
public class BirdSingingTask implements Runnable {

    private EnvironmentalProtectionDataCenter environmentalProtectionDataCenter;

    public BirdSingingTask(EnvironmentalProtectionDataCenter environmentalProtectionDataCenter) {
        this.environmentalProtectionDataCenter = environmentalProtectionDataCenter;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (environmentalProtectionDataCenter.getAmountOfAirPollution() <= 400) {
                    System.out.println("Puhas õhk on puhas õhk on rõõmus linnu elu!");
                } else {
                    System.out.println("Inimene tark, inimene tark – saastet täis on linnapark");
                }
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
