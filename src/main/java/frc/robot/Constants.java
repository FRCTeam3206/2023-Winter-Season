package frc.robot;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;

public class Constants {
    public static final int FILTER_WINDOW_SIZE = 5;
    public static final double CHARGE_LEVEL_K = 1.0 / 10.0 / 2.25;
    public static final double ENCODER_RATIO_K = 6. * Math.PI / 39.37 / 1024.;
    public static final double TRACK_WIDTH = 20.817 / 39.37;
    public static final double ACCEL_LIMIT_K = 100000;// In %motor/second(infinity is no accel limit, 0 is not moving)
    public static final double TRANSPORT_ENCODER_END_POS = 20 / Math.PI / 2 * 1024.;

    public static class Ports {
        public static final int INTAKE_MOTOR = 7;
        public static final int CLAW_MASTER_PISTON = 2;
        public static final int CLAW_SECOND_PISTON = 3;
        public static final int INTAKE_DEPLOY = 1;
        public static final int COMPRESSOR_MODULE = 0;
        public static final int SOLENOID_SHIFTER = 0;

        public static final int RIGHT_JOYSTICK = 0;
        public static final int LEFT_JOYSTICK = 1;
        public static final int XBOX_CONTROLLER = 2;
    }

    public static class Inputs {
        public static final int BTN_CONE = 4; // x
        public static final int BTN_CUBE = 1; // Y
        public static final int BTN_TRANS_DOWN = 2;// A
        public static final int BTN_INTAKE_CONE = 6; // RB
        public static final int BTN_REVERSE_INTAKE_CONE = 5; // LB
        public static final int BTN_INTAKE_CUBE = 8; // RT
        public static final int BTN_REVERSE_INTAKE_CUBE = 7; // LT
        public static final int BTN_DRIVE_FLIP = 2;

        public static final int ARCADE_FORWARD_AXIS = 1;
        public static final int ARCADE_ROTATE_AXIS = 2;

        public static final int TANK_LEFT_AXIS = 1;
        public static final int TANK_RIGHT_AXIS = 1;

        public static final int BTN_SHIFT = 1;
        public static final int BTN_LEVEL = 2;
    }

    public static class CANIDs {
        public static final int FLD = 4;
        public static final int FRD = 1;
        public static final int RLD = 3;
        public static final int RRD = 2;
        public static final int ARM_ELBOW = 5;
        public static final int ARM_TELE = 6;
        public static final int TRANSPORT = 11;
        public static final int RIGHT_ENCODER = 1;
        public static final int LEFT_ENCODER = 2;
    }

    public static class Encoders {
        public static final int RIGHT_A = 0;
        public static final int RIGHT_B = 1;
        public static final int LEFT_A = 2;
        public static final int LEFT_B = 3;
        public static final int ELBOW_A = 4;
        public static final int ELBOW_B = 5;
        public static final int TRANSPORT_A = 6;
        public static final int TRANSPORT_B = 7;
    }

    public static class ArmPID {
        public static final double elbowKp = 1.0 / 180;
        public static final double elbowKi = 0;
        public static final double elbowKd = 0;

        public static final double teleKp = 1.0 / 32;
        public static final double teleKi = 0;
        public static final double teleKd = 0;

        public static double ARM_SMALL = 37;
        public static double ARM_BIG = 52;
    }

    // Macros for the claw.grab() func
    public static class ClawBools {
        public static final boolean GRAB_CONE = true;
        public static final boolean GRAB_BLCK = false;
    }

    public static class Vision {
        // Change which camera you want to use with photonvision.
        public static final String photonvision_camera = "USB_GS_Camera";
        public static final Transform3d CAMERA_POS = new Transform3d(new Translation3d(-.229, 0, 0),
                new Rotation3d(0, 0, Math.PI));
    }
}
