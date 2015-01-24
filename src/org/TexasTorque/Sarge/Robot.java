package org.TexasTorque.Sarge;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.TexasTorque.Sarge.feedback.FeedbackSystem;
import org.TexasTorque.Sarge.feedback.SensorFeedback;
import org.TexasTorque.Sarge.input.DriverInput;
import org.TexasTorque.Sarge.input.InputSystem;
import org.TexasTorque.Sarge.subsystem.Arm;
import org.TexasTorque.Sarge.subsystem.Drivebase;
import org.TexasTorque.Torquelib.util.Parameters;

public class Robot extends TorqueIterative {

    InputSystem input;
    DriverInput driverInput;

    FeedbackSystem feedback;
    SensorFeedback sensorFeedback;

    Drivebase drivebase;
    Arm arm;
    Compressor compressor;

    Parameters params;

    int NumCycles = 0;

    public void robotInit() {
        params = new Parameters();
        params.load();

        driverInput = new DriverInput();

        sensorFeedback = new SensorFeedback();

        drivebase = new Drivebase();
        arm = new Arm();
        arm.setFeedbackSystem(sensorFeedback);
        arm.setInputSystem(driverInput);
        arm.updateGains();

        compressor = new Compressor(13, 1);
        compressor.start();
    }

    public void teleopInit() {
        params.load();
        arm.updateGains();

        input = driverInput;
        feedback = sensorFeedback;
        input.setFeedBackSystem(feedback);

        drivebase.setInputSystem(input);
        drivebase.setFeedbackSystem(feedback);
        drivebase.enableOutput(true);

        arm.setInputSystem(input);
        arm.setFeedbackSystem(feedback);
        arm.enableOutput(true);

        NumCycles = 0;
    }

    public void teleopPeriodic() {

        drivebase.update();
        drivebase.pushToDashboard();

    }

    public void teleopContinuous() {
        SmartDashboard.putNumber("NumCycles", NumCycles++);
        input.run();
        feedback.run();
        arm.update();
        arm.pushToDashboard();
    }

    public void disabledInit() {
        params.load();
        arm.updateGains();

        feedback = sensorFeedback;
        input = driverInput;
        input.setFeedBackSystem(feedback);
    }

    public void disabledPeriodic() {
        feedback.run();
        input.run();
    }

}
