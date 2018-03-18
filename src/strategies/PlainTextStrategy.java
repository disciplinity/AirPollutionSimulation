package strategies;

import buildings.EnvironmentalProtectionDataCenter;
import transport.Car;

import java.util.List;

public class PlainTextStrategy implements Strategy{

    public String getOverviewOfSituationInTown(EnvironmentalProtectionDataCenter environmentalProtectionDataCenter) {
        List<Car> cars = environmentalProtectionDataCenter.getCarStorage().getCars();
        StringBuilder plainText = new StringBuilder();

        plainText.append("Pollution: ");
        plainText.append(environmentalProtectionDataCenter.getAmountOfAirPollution());
        plainText.append("\n");

        for (Car car : cars) {
            plainText.append(car);
            plainText.append("\n");
        }
        return plainText.toString();
    }

}
