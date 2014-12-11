package org.TexasTorque.Torquelib.component;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;

public class TorqueQuadrature extends TorqueEncoder
{
    public static final CounterBase.EncodingType k1X = CounterBase.EncodingType.k1X;
    public static final CounterBase.EncodingType k2X = CounterBase.EncodingType.k2X;
    public static final CounterBase.EncodingType k4X = CounterBase.EncodingType.k4X;
    
    public TorqueQuadrature(int aChannel, int bChannel, boolean reverseDirection)
    {
        encoder = new Encoder(aChannel, bChannel, reverseDirection);
    }
    
    public TorqueQuadrature(int aChannel, int bChannel, boolean reverseDireciton, CounterBase.EncodingType encodingType)
    {
        encoder = new Encoder(aChannel, bChannel, reverseDireciton, encodingType);
    }
    
    public void calc()
    {
        double currentTime = Timer.getFPGATimestamp();
        currentPosition = encoder.get();
        
        secantRate = (currentPosition - previousPosition) / (currentTime - previousTime);
        instantRate = 1.0 / encoder.getPeriod();
        acceleration = (secantRate - previousRate) / (currentTime - previousTime);
        
        previousTime = currentTime;
        previousPosition = currentPosition;
        previousRate = secantRate;
    }
}
