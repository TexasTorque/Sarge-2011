package org.TexasTorque.Sarge;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotBase extends IterativeRobot implements Runnable {
    
    Watchdog watchdog;
    
    double previousTime;
    int numCycles;
    
    public void robotInit() {
        watchdog = getWatchdog();
        watchdog.setEnabled(true);
        watchdog.setExpiration(0.5);
    }

    public void autonomousPeriodic() {

    }
    
    public void teleopPeriodic() {
        
    }
    
    public void testPeriodic() {
    
    }

    public void run() {
        previousTime = Timer.getFPGATimestamp();

        while (true) {
            watchdog.feed();
            if (isAutonomous() && isEnabled()) {
                autonomousContinuous();
                Timer.delay(0.004);
            } else if (isOperatorControl() && isEnabled()) {
                teleopContinuous();
                Timer.delay(0.004);
            } else if (isDisabled()) {
                disabledContinuous();
                Timer.delay(0.05);
            }

            numCycles++;
            SmartDashboard.putNumber("NumCycles", numCycles);
        }
    }

    private void autonomousContinuous() {
    }

    private void teleopContinuous() {
    }

    private void disabledContinuous() {
    }
    
}