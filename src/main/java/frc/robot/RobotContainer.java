// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.Inputs.*;
import static frc.robot.Constants.Ports.*;

import java.util.Optional;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.ChargeLeveler;
import frc.robot.commands.ClawCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.TankDrive;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;

public class RobotContainer {
    // Subsystems
    Drivetrain drive = new Drivetrain();
    // Claw claw = new Claw();
    // Arm arm = new Arm();
    // Intake intake = new Intake();
    // Compressor pcmCompressor = new Compressor(COMPRESSOR_MODULE,
    // PneumaticsModuleType.CTREPCM);

    // Inputs
    CommandGenericHID leftStick = new CommandGenericHID(LEFT_JOYSTICK);
    CommandGenericHID rightStick = new CommandGenericHID(RIGHT_JOYSTICK);
    CommandGenericHID xbox = new CommandGenericHID(XBOX_CONTROLLER);

    // Drive Commands
    ArcadeDrive arcade = new ArcadeDrive(
            drive,
            () -> rightStick.getRawAxis(ARCADE_FORWARD_AXIS),
            () -> rightStick.getRawAxis(ARCADE_ROTATE_AXIS),
            () -> rightStick.getHID().getRawButton(BTN_SHIFT));
    TankDrive tank = new TankDrive(drive, () -> leftStick.getRawAxis(TANK_LEFT_AXIS),
            () -> rightStick.getRawAxis(TANK_RIGHT_AXIS), () -> rightStick.getHID().getRawButton(BTN_SHIFT));

    // Choosers
    SendableChooser<Command> drive_chooser = new SendableChooser<>();

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        // Setup Drivetrain
        drive_chooser.setDefaultOption("Arcade", arcade);
        drive_chooser.addOption("Tank", tank);
        SmartDashboard.putData("Drive Mode", drive_chooser);
        rightStick.button(BTN_LEVEL).onTrue(new ChargeLeveler(drive));
        drive.setDefaultCommand(drive_chooser.getSelected());
        // Setup Claw
        // claw.setDefaultCommand(
        // new ClawCommand(
        // claw,
        // () -> xbox.getHID().getRawButton(BTN_CONE),
        // () -> xbox.getHID().getRawButton(BTN_CUBE)));

        // // Setup Intake
        // intake.setDefaultCommand(
        // new IntakeCommand(
        // intake,
        // () -> xbox.getHID().getRawButton(BTN_INTAKE)));

        // Setup Compressor
        // pcmCompressor.enableDigital();
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}