package frc.robot;

public class Constants {
    public static final int FILTER_WINDOW_SIZE = 5;

    public static class Ports {
        public static final int INTAKE_MOTOR = 5;
        public static final int CLAW_MASTER_PISTON = 0;
        public static final int CLAW_SECOND_PISTON = 1;
        public static final int ARM_MOTOR = 6;
        public static final int INTAKE_DEPLOY = 2;
        public static final int COMPRESSOR_MODULE = 0;
        public static final int SOLENOID_SHIFTER = 1;

        public static final int RIGHT_JOYSTICK = 0;
        public static final int LEFT_JOYSTICK = 1;
        public static final int XBOX_CONTROLLER = 2;
    }

    public static class Inputs {
        public static final int BTN_CONE = 4;
        public static final int BTN_CUBE = 1;
        public static final int BTN_INTAKE = 6;
        public static final int BTN_DRIVE_FLIP = 2;

        public static final int ARCADE_FORWARD_AXIS = 1;
        public static final int ARCADE_ROTATE_AXIS = 2;

        public static final int TANK_LEFT_AXIS = 1;
        public static final int TANK_RIGHT_AXIS = 1;

        public static final int BTN_SHIFT = 1;

    }

    public static class CANIDs {
        public static final int FLD = 4;
        public static final int FRD = 1;
        public static final int RLD = 3;
        public static final int RRD = 2;
    }

    // Macros for the claw.grab() func
    public static class ClawBools {
        public static final boolean GRAB_CONE = true;
        public static final boolean GRAB_BLCK = false;
    }
}
