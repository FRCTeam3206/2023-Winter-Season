package frc.robot;

public class Constants {
    public static class Ports {
        public static final int INTAKE_MOTOR = 5;
        public static final int CLAW_MASTER_PISTON = 0;
        public static final int CLAW_SECOND_PISTON = 1;
        public static final int ARM_MOTOR = 6;
        public static final int INTAKE_DEPLOY = 2;
    }

    public static class Inputs {
        public static final int BTN_CONE = 4;
        public static final int BTN_CUBE = 1;
        public static final int BTN_INTAKE = 6;
        public static final int BTN_DRIVE_FLIP = 2;
    }

    public static class CANIDs {
        public static final int FLD = 1;
        public static final int FRD = 2;
        public static final int RLD = 3;
        public static final int RRD = 4;
    }

    // Macros for the claw.grab() func
    public static class ClawBools {
        public static final boolean GRAB_CONE = true;
        public static final boolean GRAB_BLCK = false;
    }
}
