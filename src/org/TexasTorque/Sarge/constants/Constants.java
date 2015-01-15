package org.TexasTorque.Sarge.constants;

import org.TexasTorque.Torquelib.util.Parameters.Constant;

public class Constants {
    //Arm PIV
    public static final Constant Arm_Kp = new Constant("R_ArmKp", 0.0);
    public static final Constant Arm_Kv = new Constant("R_ArmKv", 0.0);
    public static final Constant Arm_Kff = new Constant("R_ArmKff", 0.0);
    
    public static final Constant Arm_KffV = new Constant("R_ArmKffV", 0.0);
    public static final Constant Arm_KffA = new Constant("R_ArmKffA", 0.0);
    public static final Constant Arm_maxV = new Constant("R_ArmMaxV", 150.0);
    public static final Constant Arm_maxA = new Constant("R_ArmMaxA", 100.0);
    
    public static final Constant Arm_positionFF = new Constant("R_PositionFF", 0.0);
    
    public static final Constant Arm_DoneCycles = new Constant("R_DoneCycles", 10);
    public static final Constant Arm_VelocityDoneRange = new Constant("R_VelocityDoneRange", 3.0);
    public static final Constant Arm_PositionDoneRange = new Constant("R_PositionDoneRange", 2.0);
    
    public static final Constant tunedBatteryVoltage = new Constant("R_TunedBatteryVoltage", 12.0);
    
    public static final Constant angleDeadband = new Constant("R_AngleDeadband", 2.5);
    
    //Arm Setpoints
    public final static Constant FLOOR_ANGLE = new Constant("R_FloorAngle", -50.0);
    public final static Constant LOW_ANGLE = new Constant("R_LowAngle", -45.0);
    public final static Constant MIDDLE_ANGLE = new Constant("R_MiddleAngle", 10.0);
    public final static Constant HIGH_ANGLE = new Constant("R_HighAngle", 55.0);
    public final static Constant RETRACT_ANGLE = new Constant("R_RetractAngle", 55.0);
    
}
