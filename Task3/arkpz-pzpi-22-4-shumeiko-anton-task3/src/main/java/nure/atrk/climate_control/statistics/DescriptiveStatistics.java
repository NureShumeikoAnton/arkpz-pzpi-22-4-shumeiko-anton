package nure.atrk.climate_control.statistics;

import java.util.Arrays;

public class DescriptiveStatistics {
    private double[] data;
    private int size;

    public DescriptiveStatistics() {
        data = new double[0];
        size = 0;
    }

    public void addValue(double value) {
        data = Arrays.copyOf(data, size + 1);
        data[size++] = value;
    }

    public double getMean() {
        double sum = 0.0;
        for (double a : data) {
            sum += a;
        }
        return sum / size;
    }

    public double getMedian() {
        Arrays.sort(data);
        if (size % 2 == 0) {
            return (data[size / 2 - 1] + data[size / 2]) / 2.0;
        } else {
            return data[size / 2];
        }
    }

    public double getStandardDeviation() {
        double mean = getMean();
        double sum = 0.0;
        for (double a : data) {
            sum += Math.pow(a - mean, 2);
        }
        return Math.sqrt(sum / size);
    }

    public double getMin() {
        double min = data[0];
        for (double a : data) {
            if (a < min) {
                min = a;
            }
        }
        return min;
    }

    public double getMax() {
        double max = data[0];
        for (double a : data) {
            if (a > max) {
                max = a;
            }
        }
        return max;
    }

    public double getPercentile(double percentile) {
    if (data == null || data.length == 0) {
        throw new IllegalArgumentException("Data array cannot be null or empty");
    }

    // Sort the data in ascending order
    Arrays.sort(data);

    // Calculate the index of the percentile
    int n = data.length;
    double index = (percentile / 100) * (n - 1);

    // Check if the index is an integer
    int lowerIndex = (int) index;
    double diff = index - lowerIndex;

    // If it's an integer, no need to interpolate
    if (diff == 0) {
        return data[lowerIndex];
    }

    // Interpolate between the two nearest values
    double upperValue = data[lowerIndex + 1];
    double lowerValue = data[lowerIndex];
    return lowerValue + diff * (upperValue - lowerValue);
}
}