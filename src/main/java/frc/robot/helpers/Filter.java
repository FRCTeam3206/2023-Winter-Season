package frc.robot.helpers;

import java.util.function.Supplier;

import edu.wpi.first.math.filter.MedianFilter;

public class Filter {
    MedianFilter filter;
    Supplier<Double> source;
    Double store;

    public Filter(Supplier<Double> source_value, int window_size) {
        this.source = source_value;
        this.filter = new MedianFilter(window_size);
    }

    public void periodic() {
        this.store = this.filter.calculate(this.source.get());
    }
}
