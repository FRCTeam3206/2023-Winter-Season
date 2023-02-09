package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADIS16448_IMU;

public class Gyro {
    public ADIS16448_IMU imu = new ADIS16448_IMU();

    public Gyro () {
        imu.reset();
    }

}
