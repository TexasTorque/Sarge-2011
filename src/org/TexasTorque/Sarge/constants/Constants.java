package org.TexasTorque.Sarge.constants;

import org.TexasTorque.Torquelib.util.Parameters.Constant;

public class Constants {
    
    //Arm PIV
    public static final Constant Arm_Kp = new Constant("R_ArmKp", 0.0);
    public static final Constant Arm_Ki = new Constant("R_ArmKi", 0.0);
    public static final Constant Arm_Kd = new Constant("R_ArmKd", 0.0);
    public static final Constant Arm_Kff = new Constant("R_ArmKff", 0.0);
    
    public static final Constant Arm_KffV = new Constant("R_ArmKffV", 0.0);
    public static final Constant Arm_KffA = new Constant("R_ArmKffA", 0.0);
    public static final Constant Arm_maxV = new Constant("R_ArmMaxV", 150.0);
    public static final Constant Arm_maxA = new Constant("R_ArmaxA", 100.0);
    
    //Arm Setpoints
    public final static Constant FLOOR_ANGLE = new Constant("R_FloorAngle", -50.0);
    public final static Constant LOW_ANGLE = new Constant("R_LowAngle", -45.0);
    public final static Constant MIDDLE_ANGLE = new Constant("R_MiddleAngle", 10.0);
    public final static Constant HIGH_ANGLE = new Constant("R_HighAngle", 55.0);
    public final static Constant RETRACT_ANGLE = new Constant("R_RetractAngle", 55.0);
    
}
