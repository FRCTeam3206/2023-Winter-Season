// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Gyro;

public class RobotContainer {
    Drivetrain drive = new Drivetrain();
    Gyro gyro = new Gyro();

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {}
}
