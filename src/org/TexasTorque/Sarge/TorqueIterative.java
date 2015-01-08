package org.TexasTorque.Sarge;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.communication.FRCControl;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import java.util.TimerTask;

/**
 * @Author TexasTorque
 *
 * A modified version of the WPILIBJ IterativeRobot template that uses two
 * threads.
 *
 * CPU usage on the roboRio will be higher than usual, but should not be a
 * problem.
 */
public abstract class TorqueIterative extends RobotBase {

    public void startCompetition() {
        robotInit();

        Thread periodicThread = new Thread(new Periodic());
        periodicThread.start();

        java.util.Timer continuousTimer = new java.util.Timer();
        continuousTimer.scheduleAtFixedRate(new Continuous(), 0, 10);

        System.err.println("UNEXPECTED RETURN FROM startCompetition() IS NORMAL");
    }

    private boolean m_autonomousInitialized;
    private boolean m_teleopInitialized;
    private boolean m_disabledInitialized;
    private boolean m_testInitialized;

    /**
     * This class provides the default IterativeRobot functionality.
     *
     * It is synchronized to inputs coming from the driver station.
     */
    private class Periodic implements Runnable {

        boolean didDisabledPeriodic = false;
        boolean didAutonomousPeriodic = false;
        boolean didTeleopPeriodic = false;
        boolean didTestPeriodic = false;

        public void run() {
            while (true) {
                if (isDisabled()) {
                    if (!m_disabledInitialized) {
                        LiveWindow.setEnabled(false);
                        disabledInit();
                        m_disabledInitialized = true;
                        m_autonomousInitialized = false;
                        m_teleopInitialized = false;
                        m_testInitialized = false;
                    }
                    if (nextPeriodReady()) {
                        FRCControl.observeUserProgramDisabled();
                        disabledPeriodic();
                        didDisabledPeriodic = true;
                    }
                } else if (isAutonomous()) {
                    if (!m_autonomousInitialized) {
                        LiveWindow.setEnabled(false);
                        autonomousInit();
                        m_autonomousInitialized = true;
                        m_testInitialized = false;
                        m_teleopInitialized = false;
                        m_disabledInitialized = false;
                    }
                    if (nextPeriodReady()) {
                        getWatchdog().feed();
                        FRCControl.observeUserProgramAutonomous();
                        autonomousPeriodic();
                        didAutonomousPeriodic = true;
                    }
                } else if (isTest()) {
                    if (!m_testInitialized) {
                        LiveWindow.setEnabled(true);
                        teleopInit();
                        m_teleopInitialized = false;
                        m_testInitialized = true;
                        m_autonomousInitialized = false;
                        m_disabledInitialized = false;
                    }
                    if (nextPeriodReady()) {
                        getWatchdog().feed();
                        FRCControl.observeUserProgramTeleop();
                        testPeriodic();
                        didTestPeriodic = true;
                    }
                } else {
                    if (!m_teleopInitialized) {
                        LiveWindow.setEnabled(false);
                        teleopInit();
                        m_teleopInitialized = true;
                        m_testInitialized = false;
                        m_autonomousInitialized = false;
                        m_disabledInitialized = false;
                    }
                    if (nextPeriodReady()) {
                        getWatchdog().feed();
                        FRCControl.observeUserProgramTeleop();
                        teleopPeriodic();
                        didTeleopPeriodic = true;
                    }
                }
                m_ds.waitForData();
            }
        }

        private boolean nextPeriodReady() {
            return m_ds.isNewControlData();
        }
    }

    /**
     * This class provides an extra execution thread to take advantage of the
     * two cores of the roboRIO.
     *
     * It runs at a constant frequency of around 250 Hz at all times.
     */
    private class Continuous extends TimerTask {

        public void run() {
            while (true) {
                if (isAutonomous() && m_autonomousInitialized) {
                    autonomousContinuous();
                } else if (isOperatorControl() && m_teleopInitialized) {
                    teleopContinuous();
                } else if (isDisabled() && m_disabledInitialized) {
                    disabledContinuous();
                }
                try {
                    Thread.sleep(4);
                } catch (InterruptedException ex) {
                }
            }
        }
    }

    /* ----------- Overridable Initialization code -----------------*/
    public void robotInit() {
        System.out.println("Default TorqueIterativeRobot.robotInit()!");
    }

    public void disabledInit() {
        System.out.println("Default TorqueIterativeRobot.disabledInit() method!");
    }

    public void autonomousInit() {
        System.out.println("Default TorqueIterativeRobot.autonomousInit() method!");
    }

    public void teleopInit() {
        System.out.println("Default TorqueIterativeRobot.teleopInit() method!");
    }

    public void testInit() {
        System.out.println("Default TorqueIterativeRobot.testInit() method!");
    }

    /* ----------- Overridable continuous code -----------------*/
    private boolean tpcFirstRun = true;

    public void teleopContinuous() {
        if (tpcFirstRun) {
            System.out.println("Default TorqueIterativeRobot.teleopContinuous() method!");
            tpcFirstRun = false;
        }
    }

    private boolean apcFirstRun = true;

    public void autonomousContinuous() {
        if (apcFirstRun) {
            System.out.println("Default TorqueIterativeRobot.autonomousContinuous() method!");
            apcFirstRun = false;
        }
    }

    private boolean dcFirstRun = true;

    public void disabledContinuous() {
        if (dcFirstRun) {
            System.out.println("Default TorqueIterativeRobot.disabledContinuous() method!");
            dcFirstRun = false;
        }
    }

    /* ----------- Overridable periodic code -----------------*/
    private boolean dpFirstRun = true;

    public void disabledPeriodic() {
        if (dpFirstRun) {
            System.out.println("Default TorqueIterativeRobot.disabledPeriodic() method!");
            dpFirstRun = false;
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
        }
    }
    private boolean apFirstRun = true;

    public void autonomousPeriodic() {
        if (apFirstRun) {
            System.out.println("Default TorqueIterativeRobot.autonomousPeriodic() method!");
            apFirstRun = false;
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
        }
    }
    private boolean tpFirstRun = true;

    public void teleopPeriodic() {
        if (tpFirstRun) {
            System.out.println("Default TorqueIterativeRobot.teleopPeriodic() method!");
            tpFirstRun = false;
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
        }
    }
    private boolean testFirstRun = true;

    public void testPeriodic() {
        if (testFirstRun) {
            System.out.println("Default TorqueIterativeRobot.testPeriodic() method!");
            testFirstRun = false;
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
        }
    }
}
