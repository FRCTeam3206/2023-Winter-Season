package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
    CANSparkMax frontLeftDrive = new CANSparkMax(Constants.CANIDs.FLD, MotorType.kBrushless);
    CANSparkMax frontRightDrive = new CANSparkMax(Constants.CANIDs.FRD, MotorType.kBrushless);
    CANSparkMax rearLeftDrive = new CANSparkMax(Constants.CANIDs.RLD, MotorType.kBrushless);
    CANSparkMax rearRightDrive = new CANSparkMax(Constants.CANIDs.RRD, MotorType.kBrushless);
    RelativeEncoder leftEncoder = frontLeftDrive.getEncoder();
    RelativeEncoder rightEncoder = frontRightDrive.getEncoder();

    DifferentialDrive drive = new DifferentialDrive(frontLeftDrive, frontRightDrive);
    boolean flipped = false;
    // Gyro gyro = new Gyro();
    Solenoid shifter = new Solenoid(PneumaticsModuleType.REVPH, Constants.Ports.SOLENOID_SHIFTER);

    public Drivetrain() {
        frontLeftDrive.restoreFactoryDefaults();
        frontRightDrive.restoreFactoryDefaults();
        rearLeftDrive.restoreFactoryDefaults();
        rearRightDrive.restoreFactoryDefaults();

        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);

        frontRightDrive.setInverted(true);
        rearLeftDrive.follow(frontLeftDrive);
        rearRightDrive.follow(frontRightDrive);
    }

    public void arcadeDrive(double xaxisSpeed, double zaxisRotate) {
        drive.arcadeDrive(xaxisSpeed, zaxisRotate);
    }

    public void tankDrive(double left_speed, double right_speed) {
        drive.tankDrive(left_speed, right_speed);
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
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
    }

    @Override
    public void periodic() {
        // gyro.periodic();
    }
}
