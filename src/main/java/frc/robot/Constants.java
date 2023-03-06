package frc.robot;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;

public class Constants {
    public static final int FILTER_WINDOW_SIZE = 5;
    public static final double CHARGE_LEVEL_K = 1.0 / 10.0 / 2.25;
    public static final double ENCODER_RATIO_K = 6. * Math.PI / 39.37 / 4090.;
    public static final double TRACK_WIDTH = 20.817 / 39.37;
    public static final double ALIGN_DAMPENER = 0.8;
    public static final double ALIGN_DEADZONE = .15;
    public static final double ALIGN_FLOOR = 0.2;

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
        public static final int BTN_LEVEL = 2;
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
        public static Translation3d[] aprilTags = new Translation3d[] {
                null,
                new Translation3d(15.513558, 1.071626, 0.462788),
                new Translation3d(15.513558, 2.748026, 0.462788),
                new Translation3d(15.513558, 4.424426, 0.462788),
                new Translation3d(16.178784, 6.749796, 0.695452),
                new Translation3d(0.36195, 6.749796, 0.695452),
                new Translation3d(1.02743, 4.424426, 0.462788),
                new Translation3d(1.02743, 2.748026, 0.462788),
                new Translation3d(1.02743, 1.071626, 0.462788),
        };

        /*
         * positions
         * (-1,1) (0,1) (1,1)
         * (-1,0) (0,0) (1,0)
         */
        public static Translation3d getScoreArea(int tagID, int x, int y) {
            Translation3d aprilTag = aprilTags[tagID];
            double multiplier = 1.0;
            if (tagID > 4)
                multiplier = -1.0;
            return new Translation3d(multiplier * (.226 + (y) * .433), -multiplier * (x) * .559,
                    .403 + (y) * .304)
                    .plus(aprilTag);
        }

        // Change which camera you want to use with photonvision.
        public static final String photonvision_camera = "USB_GS_Camera";
        public static final Transform3d CAMERA_POS = new Transform3d(new Translation3d(-.229, 0, 0),
                new Rotation3d(0, 0, Math.PI));
    }
}
