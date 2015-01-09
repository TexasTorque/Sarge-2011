package org.TexasTorque.Sarge.feedback;

import edu.wpi.first.wpilibj.Timer;
import org.TexasTorque.Torquelib.component.TorquePotentiometer;
import org.TexasTorque.Torquelib.component.TorqueQuadrature;
import org.TexasTorque.Torquelib.util.MovingAverageFilter;

public class SensorFeedback extends FeedbackSystem {

    //Drivebase
    private TorqueQuadrature leftDriveEncoder;
    private TorqueQuadrature rightDriveEncoder;

    //Arm
    private TorquePotentiometer armPotentiometer;
    private double bottomVoltage = 2.32;
    private double upVoltage = 0.70;
    private double bottomAngle = -66.0;
    private int upAngle = 55;
    
    private double previousAngle;
    private double previousTime;
    
    MovingAverageFilter positionFilter;
    MovingAverageFilter velocityFilter;

    public SensorFeedback() {
        leftDriveEncoder = new TorqueQuadrature(4, 5, false);
        rightDriveEncoder = new TorqueQuadrature(2, 3, true);

        armPotentiometer = new TorquePotentiometer(1);
        armPotentiometer.setRange(bottomVoltage, upVoltage);
        
        positionFilter = new MovingAverageFilter(10);
        velocityFilter = new MovingAverageFilter(10);
    }

    public void run() {
        //DriveBase
        leftPosition = leftDriveEncoder.get();
        leftVelocity = leftDriveEncoder.getInstantRate();

        rightPosition = rightDriveEncoder.get();
        rightVelocity = rightDriveEncoder.getInstantRate();

        //Arm
        double currentAngle = (armPotentiometer.get() * (upAngle - bottomAngle) + bottomAngle);
        positionFilter.setInput(currentAngle);
        positionFilter.run();
        
        armAngle = positionFilter.getAverage();
        
        double currentTime = Timer.getFPGATimestamp();
        
        velocityFilter.setInput((armAngle - previousAngle) / (currentTime - previousTime));
        velocityFilter.run();
        
        armVelocity = velocityFilter.getAverage();
        
        previousAngle = armAngle;
        previousTime = currentTime;
    }
}
