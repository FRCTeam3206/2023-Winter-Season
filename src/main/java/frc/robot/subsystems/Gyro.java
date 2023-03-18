package frc.robot.subsystems;

import java.util.function.Supplier;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gyro {
    public AHRS raw_gyro = new AHRS(SPI.Port.kMXP);

    public Gyro() {
        raw_gyro.calibrate();
        raw_gyro.reset();
    }

    public double getRawGyroAngleX() {
        double value = raw_gyro.getRawGyroX();
        SmartDashboard.putNumber("Raw Pos X", value);
        return value;
    }

    public double getRawGyroAngleY() {
        double value = raw_gyro.getRawGyroY();
        SmartDashboard.putNumber("Raw Pos Y", value);
        return value;
    }

    public double getRawGyroAngleZ() {
        double value = raw_gyro.getRawGyroZ();
        SmartDashboard.putNumber("Raw Pos Z", value);
        return value;
    }

    public Supplier<Double> pos_x = () -> this.getRawGyroAngleX();
    public Supplier<Double> pos_y = () -> this.getRawGyroAngleY();
    public Supplier<Double> pos_z = () -> this.getRawGyroAngleZ();

    public double getGyroAngleX() {
        double value = pos_x.get();
        SmartDashboard.putNumber("Pos X", value);
        return value;
    }

    public double getGyroAngleY() {
        double value = pos_y.get();
        SmartDashboard.putNumber("Pos Y", value);
        return value;
    }

    public double getGyroAngleZ() {
        double value = pos_z.get();
        SmartDashboard.putNumber("Pos Z", value);
        return value;
    }

    public Rotation2d yaw() {
        return new Rotation2d(getRawGyroAngleZ());
    }

    public void periodic() {
        getGyroAngleX();
        getGyroAngleY();
        getGyroAngleZ();
    }
}
