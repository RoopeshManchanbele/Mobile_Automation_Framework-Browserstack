package utilities.dataUtility;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DataUtil {

    /**
     * Increase the given price by two percent.
     *
     * @param currentValue value to increase
     * @return the increased value, or null if the given value is null
     */
    public static Double increasePriceByTwoPercent(Double currentValue) {
        if (currentValue == null) {
            return null;
        }
        BigDecimal decimal = BigDecimal.valueOf(currentValue);
        BigDecimal increasedValue = decimal.add(decimal.multiply(BigDecimal.valueOf(0.02)));
        return increasedValue.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }


    /**
     * Increase the given price by the given percentage.
     *
     * @param currentValue value to increase
     * @param percentage   percentage to increase by
     * @return the increased value, or null if the given value is null
     */
    public static Double increasePrice(Double currentValue, int percentage) {
        if (currentValue == null) {
            return null;
        }
        BigDecimal decimal = BigDecimal.valueOf(currentValue);
        BigDecimal increasedValue = decimal.add(decimal.multiply(BigDecimal.valueOf((double) percentage / 100)));
        return increasedValue.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }


    /**
     * Decrease the given price by the given percentage.
     *
     * @param currentValue value to decrease
     * @param percentage   percentage to decrease by
     * @return the decreased value, or null if the given value is null
     */
    public static Double decreasePrice(Double currentValue, int percentage) {
        if (currentValue == null) {
            return null;
        }
        BigDecimal decimal = BigDecimal.valueOf(currentValue);
        BigDecimal increasedValue = decimal.subtract(decimal.multiply(BigDecimal.valueOf((double) percentage / 100)));
        return increasedValue.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Increase the given price by five percent.
     *
     * @param currentValue value to increase
     * @return the increased value, or null if the given value is null
     */
    public static Double increasePriceByFivePercent(Double currentValue) {
        if (currentValue == null) {
            return null;
        }
        BigDecimal decimal = BigDecimal.valueOf(currentValue);
        BigDecimal increasedValue = decimal.add(decimal.multiply(BigDecimal.valueOf(0.05)));
        return increasedValue.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Increase the given price by ten percent.
     *
     * @param currentValue value to increase
     * @return the increased value, or null if the given value is null
     */
    public static Double increasePriceByTenPercent(Double currentValue) {
        if (currentValue == null) {
            return null;
        }
        BigDecimal decimal = BigDecimal.valueOf(currentValue);
        BigDecimal increasedValue = decimal.add(decimal.multiply(BigDecimal.valueOf(0.1)));
        return increasedValue.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Decrease the given price by ten percent.
     *
     * @param currentValue value to decrease
     * @return the decreased value, or null if the given value is null
     */
    public static Double decreasePriceByTenPercent(Double currentValue) {
        if (currentValue == null) {
            return null;
        }
        BigDecimal decimal = BigDecimal.valueOf(currentValue);
        BigDecimal decreasedValue = decimal.subtract(decimal.multiply(BigDecimal.valueOf(0.1)));
        return decreasedValue.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Decrease the given price by five percent.
     *
     * @param currentValue value to decrease
     * @return the decreased value, or null if the given value is null
     */
    public static Double decreasePriceByFivePercent(Double currentValue) {
        if (currentValue == null) {
            return null;
        }
        BigDecimal decimal = BigDecimal.valueOf(currentValue);
        BigDecimal decreasedValue = decimal.subtract(decimal.multiply(BigDecimal.valueOf(0.05)));
        return decreasedValue.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Decrease the given price by two percent.
     *
     * @param currentValue value to decrease
     * @return the decreased value, or null if the given value is null
     */
    public static Double decreasePriceByTwoPercent(Double currentValue) {
        if (currentValue == null) {
            return null;
        }
        BigDecimal decimal = BigDecimal.valueOf(currentValue);
        BigDecimal decreasedValue = decimal.subtract(decimal.multiply(BigDecimal.valueOf(0.02)));
        return decreasedValue.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
