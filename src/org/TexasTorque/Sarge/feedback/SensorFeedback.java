package org.TexasTorque.Sarge.feedback;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.TexasTorque.Torquelib.component.TorquePotentiometer;
import org.TexasTorque.Torquelib.component.TorqueQuadrature;

public class SensorFeedback extends FeedbackSystem {

    //Drivebase
    private TorqueQuadrature leftDriveEncoder;
    private TorqueQuadrature rightDriveEncoder;

    //Arm
    private TorquePotentiometer armPotentiometer;
    private double bottomVoltage = 2.32;
    private double upVoltage = 0.70;
    private double bottomAngle = -66.0;
    private double upAngle = 55.0;

    public SensorFeedback() {
        leftDriveEncoder = new TorqueQuadrature(4, 5, false);
        rightDriveEncoder = new TorqueQuadrature(2, 3, true);

        armPotentiometer = new TorquePotentiometer(1);
        armPotentiometer.setRange(bottomVoltage, upVoltage);
    }

    public void run() {
        //DriveBase
        leftPosition = leftDriveEncoder.get();
        leftVelocity = leftDriveEncoder.getInstantRate();

        rightPosition = rightDriveEncoder.get();
        rightVelocity = rightDriveEncoder.getInstantRate();

        //Arm
        armAngle = armPotentiometer.get() * (upAngle - bottomAngle) + bottomAngle;
    }
}
