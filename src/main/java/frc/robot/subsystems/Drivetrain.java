package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
    CANSparkMax frontLeftDrive = new CANSparkMax(1, MotorType.kBrushless);
    CANSparkMax frontRightDrive = new CANSparkMax(2, MotorType.kBrushless);
    CANSparkMax rearLeftDrive = new CANSparkMax(3, MotorType.kBrushless);
    CANSparkMax rearRightDrive = new CANSparkMax(4, MotorType.kBrushless);

    RelativeEncoder leftEncoder = frontLeftDrive.getEncoder();
    RelativeEncoder rightEncoder = frontRightDrive.getEncoder();

    DifferentialDrive drive = new DifferentialDrive(frontLeftDrive, frontRightDrive);
    boolean flipped = false;

    Gyro gyro = new Gyro();

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

    public void reset_position() {
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
    }

    @Override
    public void periodic() {
        gyro.periodic();
    }
}
