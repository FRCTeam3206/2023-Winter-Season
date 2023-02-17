// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import frc.robot.subsystems.Drivetrain;
import frc.robot.commands.ArcadeDrive;

public class RobotContainer {
    Drivetrain drive = new Drivetrain();
    CommandGenericHID leftStick = new CommandGenericHID(0);

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        drive.setDefaultCommand(new ArcadeDrive(drive, () -> leftStick.getRawAxis(1), () -> leftStick.getRawAxis(0)));
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}