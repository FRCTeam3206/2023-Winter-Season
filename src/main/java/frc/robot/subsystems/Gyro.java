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

    public Supplier<Float> pos_x = () -> raw_gyro.getRawGyroX();
    public Supplier<Float> pos_y = () -> raw_gyro.getRawGyroY();
    public Supplier<Float> pos_z = () -> raw_gyro.getRawGyroZ();

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
        return new Rotation2d(getGyroAngleZ());
    }

    public void periodic() {
    }
}
