package strategies;

import buildings.EnvironmentalProtectionDataCenter;
import graph.Graph;
import transport.Car;
import transport.CarStorage;

import java.util.List;

public class JsonStrategy implements Strategy {


    public String getOverviewOfSituationInTown(EnvironmentalProtectionDataCenter environmentalProtectionDataCenter) {
        List<Car> cars = environmentalProtectionDataCenter.getCarStorage().getCars();
        StringBuilder jsonString = new StringBuilder();

        jsonString.append("{pollution: {");
        jsonString.append(environmentalProtectionDataCenter.getAmountOfAirPollution());
        jsonString.append("}, ");

        jsonString.append("cars: {");
        for (Car car : cars) {
            jsonString.append(car);
            jsonString.append(", ");
        }
        jsonString.replace(jsonString.length() - 2, jsonString.length(), "}"); // Only way I can think of to remove the unnecessary space

        return jsonString.toString();
    }
}
