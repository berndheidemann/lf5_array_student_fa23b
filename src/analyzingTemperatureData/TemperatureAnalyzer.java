package analyzingTemperatureData;


public class TemperatureAnalyzer {

    public int getMaxPeriod(double[] temperatureList, double minimumValue) {
        int counter = 0;
        int maxCounter = 0;

        for (int i = 0; i < temperatureList.length; i++) {
            if (temperatureList[i] >= minimumValue) {
                counter++;
            } else {
                if (counter > maxCounter) {
                    maxCounter = counter;
                }
                counter = 0;
            }
        }
        if (counter > maxCounter) {
            maxCounter = counter;
        }

        return maxCounter;
    }
}
