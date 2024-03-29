package org.TexasTorque.Torquelib.component;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;

/**
 * Class for a FRC encoder.
 *
 * @author Matthew
 */
public class TorqueEncoder extends Encoder {

    private double averageRate;
    private double acceleration;
    private double previousTime;
    private double previousPosition;
    private double previousRate;

    /**
     * Create a new encoder.
     *
     * @param aChannel Channel a port.
     * @param bChannel Channel b port.
     * @param indexChannel The index channel digital input channel.
     * @param reverseDirection Whether or not the encoder is reversed.
     */
    public TorqueEncoder(int aChannel, int bChannel, int indexChannel, boolean reverseDirection) {
        super(aChannel, bChannel, indexChannel, reverseDirection);
    }

    /**
     * Create a new encoder.
     *
     * @param aChannel Channel a port.
     * @param bChannel Channel b port.
     * @param reverseDireciton Whether or not the encoder is revered.
     * @param encodingType What type of encoding the encoder is using.
     */
    public TorqueEncoder(int aChannel, int bChannel, boolean reverseDireciton, CounterBase.EncodingType encodingType) {
        super(aChannel, bChannel, reverseDireciton, encodingType);
    }

    /**
     * Calculate the values for the encoder.
     */
    public void calc() {
        double currentTime = Timer.getFPGATimestamp();
        double currentPosition = super.get();

        averageRate = (currentPosition - previousPosition) / (currentTime - previousTime);
        acceleration = (averageRate - previousRate) / (currentTime - previousTime);

        previousTime = currentTime;
        previousPosition = currentPosition;
        previousRate = averageRate;
    }

    /**
     * Get the average rate at which encoder position changes over time. This
     * rate is calculated in the dx/dt method rather than 1 / period method.
     *
     * @return The rate.
     */
    public double getAverageRate() {
        return 1 / super.getPeriod();
    }

    /**
     * Get the average rate at which rate changes over time.
     *
     * @return The rate.
     */
    public double getAcceleration() {
        return acceleration;
    }
}
