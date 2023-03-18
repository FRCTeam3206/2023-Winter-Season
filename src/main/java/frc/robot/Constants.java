package frc.robot;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;

public class Constants {
    public static final int FILTER_WINDOW_SIZE = 5;
    public static final double CHARGE_LEVEL_K = 1.0 / 10.0 / 2.25;
    public static final double ENCODER_RATIO_K = 6. * Math.PI / 39.37 / 4090.;
    public static final double TRACK_WIDTH = 20.817 / 39.37;

    public static class Ports {
        public static final int INTAKE_MOTOR = 7;
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
        // Controls Constants (Do not Change)
        public static final int JOY_BTN_TRIGGER = 1; // Trigger
        public static final int JOY_BTN_THUMB = 2; // Thumb
        public static final int JOY_BTN_3 = 3; // Top of stick
        public static final int JOY_BTN_4 = 4; // Top of stick
        public static final int JOY_BTN_5 = 5; // top of stick
        public static final int JOY_BTN_6 = 6; // Top of stick
        public static final int JOY_BTN_7 = 7; // Base
        public static final int JOY_BTN_8 = 8; // Base
        public static final int JOY_BTN_9 = 9; // Base
        public static final int JOY_BTN_10 = 10; // Base
        public static final int JOY_BTN_11 = 11; // Base
        public static final int JOY_BTN_12 = 12; // Base
        public static final int JOY_UP_DOWN_AXIS = 1;
        public static final int JOY_LEFT_RIGHT_AXIS = 2;
        public static final int JOY_TWIST_AXIS = 3;
        public static final int JOY_THRUST_AXIS = 4;
        public static final int HAT_UP_DOWN_AXIS = 5;
        public static final int HAT_LEFT_RIGHT_AXIS = 6;

        // Gamepad Constants for LogitechF310 (Do not change)
        public static final int PAD_LEFTSTICK_UP_DOWN_AXIS = 0;
        public static final int PAD_LEFTSTICK_LEFT_RIGHT_AXIS = 1;
        public static final int PAD_RIGHTSTICK_UP_DOWN_AXIS = 2;
        public static final int PAD_RIGHTSTICK_LEFT_RIGHT_AXIS = 3;
        public static final int PAD_BTN_X_BLUE = 0;
        public static final int PAD_BTN_A_GREEN = 1;
        public static final int PAD_BTN_B_RED = 2;
        public static final int PAD_BTN_Y_YELLOW = 3;
        public static final int PAD_LEFT_BUMPER = 4;
        public static final int PAD_RIGHT_BUMPER = 5;
        public static final int PAD_LEFT_TRIGGER = 6;
        public static final int PAD_RIGHT_TRIGGER = 7;
        public static final int PAD_BTN_BACK = 8;
        public static final int PAD_BTN_START = 9;
        public static final int PAD_BTN_LEFT_STICK = 10;
        public static final int PAD_BTN_RIGHT_STICK = 11;

        // Driver chosenConstants
        public static final int BTN_CONE = PAD_BTN_X_BLUE;
        public static final int BTN_CUBE = PAD_BTN_Y_YELLOW;
        public static final int BTN_INTAKE = PAD_LEFT_TRIGGER;
        public static final int BTN_DRIVE_FLIP = PAD_BTN_B_RED;

        public static final int ARCADE_FORWARD_AXIS = JOY_UP_DOWN_AXIS;
        public static final int ARCADE_ROTATE_AXIS = JOY_LEFT_RIGHT_AXIS;

        public static final int TANK_LEFT_AXIS = JOY_UP_DOWN_AXIS;
        public static final int TANK_RIGHT_AXIS = JOY_UP_DOWN_AXIS;

        public static final int BTN_SHIFT = JOY_BTN_TRIGGER;
        public static final int BTN_LEVEL = JOY_BTN_THUMB;
    }

    public static class CANIDs {
        public static final int FLD = 4;
        public static final int FRD = 1;
        public static final int RLD = 3;
        public static final int RRD = 2;

        public static final int RIGHT_ENCODER = 1;
        public static final int LEFT_ENCODER = 2;
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
