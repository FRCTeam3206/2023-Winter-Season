// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import frc.robot.subsystems.Drivetrain;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.ClawCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.subsystems.*;
import static frc.robot.Constants.Inputs.*;

public class RobotContainer {
    Drivetrain drive = new Drivetrain();
    CommandGenericHID leftStick = new CommandGenericHID(0);
    CommandGenericHID rightStick = new CommandGenericHID(2);
    CommandGenericHID xbox = new CommandGenericHID(1);
    Claw claw;
    Arm arm;
    Intake intake;
    Compressor pcmCompressor = new Compressor(0, PneumaticsModuleType.CTREPCM);

    public RobotContainer() {
        configureBindings();
        this.claw = new Claw();
        this.intake = new Intake();
        pcmCompressor.enableDigital();
    }

    private void configureBindings() {
        drive.setDefaultCommand(new ArcadeDrive(drive, () -> rightStick.getRawAxis(0), () -> rightStick.getRawAxis(1)));
        claw.setDefaultCommand(
                new ClawCommand(claw, () -> xbox.getHID().getRawButton(BTN_CONE),
                        () -> xbox.getHID().getRawButton(BTN_CUBE)));
        intake.setDefaultCommand(new IntakeCommand(intake, () -> xbox.getHID().getRawButton(BTN_INTAKE)));

    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
