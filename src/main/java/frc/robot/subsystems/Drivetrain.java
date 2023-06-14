package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANIDs;

import static frc.robot.Constants.*;

public class Drivetrain extends SubsystemBase {
    CANSparkMax frontLeftDrive = new CANSparkMax(CANIDs.FLD, MotorType.kBrushless);
    CANSparkMax frontRightDrive = new CANSparkMax(CANIDs.FRD, MotorType.kBrushless);
    CANSparkMax rearLeftDrive = new CANSparkMax(CANIDs.RLD, MotorType.kBrushless);
    CANSparkMax rearRightDrive = new CANSparkMax(CANIDs.RRD, MotorType.kBrushless);
    Encoder leftEncoder = new Encoder(Encoders.LEFT_A, Encoders.LEFT_B);
    Encoder rightEncoder = new Encoder(Encoders.RIGHT_A, Encoders.RIGHT_B);
    DifferentialDrive drive = new DifferentialDrive(frontLeftDrive, frontRightDrive);
    boolean flipped = false;
    Gyro gyro = new Gyro();
    IntegratedOdometry odometry = new IntegratedOdometry(gyro, rightEncoder, leftEncoder);
    Pose2d pose = new Pose2d();
    Solenoid shifter = new Solenoid(PneumaticsModuleType.REVPH, Ports.SOLENOID_SHIFTER);

    SlewRateLimiter rightLimiter = new SlewRateLimiter(ACCEL_LIMIT_K, -ACCEL_LIMIT_K, 0);
    SlewRateLimiter leftLimiter = new SlewRateLimiter(ACCEL_LIMIT_K, -ACCEL_LIMIT_K, 0);

    public Drivetrain() {
        frontLeftDrive.restoreFactoryDefaults();
        frontRightDrive.restoreFactoryDefaults();
        rearLeftDrive.restoreFactoryDefaults();
        rearRightDrive.restoreFactoryDefaults();
        rightEncoder.setDistancePerPulse(ENCODER_RATIO_K);
        leftEncoder.setDistancePerPulse(ENCODER_RATIO_K);
        frontRightDrive.setInverted(true);
        rearLeftDrive.follow(frontLeftDrive);
        rearRightDrive.follow(frontRightDrive);
    }

    public void fixReverseDrive() {
        frontRightDrive.setInverted(true);
    }

    public void resetEncoders() {
        rightEncoder.reset();
        leftEncoder.reset();
    }

    public double getRawEncoderDistance() {
        SmartDashboard.putNumber("Encoder Distance", (rightEncoder.getDistance() - leftEncoder.getDistance()) / 2);
        return (rightEncoder.getDistance() - leftEncoder.getDistance()) / 2;
    }

    public void calibrateGyro() {
        gyro.raw_gyro.calibrate();
    }

    public void arcadeDrive(double xaxisSpeed, double zaxisRotate) {
        drive.arcadeDrive(xaxisSpeed, zaxisRotate);
    }

    public void tankDrive(double left_speed, double right_speed) {
        SmartDashboard.putNumber("Left Speed", left_speed);
        SmartDashboard.putNumber("Right Speed", right_speed);
        drive.tankDrive(leftLimiter.calculate(left_speed), rightLimiter.calculate(right_speed));
    }

    public void flip() {
        flipped = !flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public void setShift(boolean shift) {
        shifter.set(shift);
    }

    public void reset_position() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    public double pitch() {
        return gyro.getRawGyroAngleY();
    }

    public Pose2d getPose() {
        return pose;
    }

    @Override
    public void periodic() {
        fixReverseDrive();
        gyro.periodic();
        pose = odometry.update();
        getRawEncoderDistance();
        SmartDashboard.putData(leftEncoder);
        SmartDashboard.putData(rightEncoder);
        SmartDashboard.putNumber("Gryo", gyro.getRawGyroAngleY());
    }
}
