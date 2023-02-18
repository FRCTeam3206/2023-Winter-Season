// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.Inputs.ARCADE_FORWARD_AXIS;
import static frc.robot.Constants.Inputs.ARCADE_ROTATE_AXIS;
import static frc.robot.Constants.Inputs.BTN_CONE;
import static frc.robot.Constants.Inputs.BTN_CUBE;
import static frc.robot.Constants.Inputs.BTN_INTAKE;
import static frc.robot.Constants.Inputs.TANK_LEFT_AXIS;
import static frc.robot.Constants.Inputs.TANK_RIGHT_AXIS;
import static frc.robot.Constants.Ports.COMPRESSOR_MODULE;
import static frc.robot.Constants.Ports.LEFT_JOYSTICK;
import static frc.robot.Constants.Ports.RIGHT_JOYSTICK;
import static frc.robot.Constants.Ports.XBOX_CONTROLLER;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.ArcadeDrive;
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
    Claw claw = new Claw();
    Arm arm = new Arm();
    Intake intake = new Intake();;
    Compressor pcmCompressor = new Compressor(COMPRESSOR_MODULE, PneumaticsModuleType.CTREPCM);

    // Inputs
    CommandGenericHID leftStick = new CommandGenericHID(LEFT_JOYSTICK);
    CommandGenericHID rightStick = new CommandGenericHID(RIGHT_JOYSTICK);
    CommandGenericHID xbox = new CommandGenericHID(XBOX_CONTROLLER);

    // Drive Commands
    ArcadeDrive arcade = new ArcadeDrive(
            drive,
            () -> rightStick.getRawAxis(ARCADE_FORWARD_AXIS),
            () -> rightStick.getRawAxis(ARCADE_ROTATE_AXIS));
    TankDrive tank = new TankDrive(
            drive,
            () -> leftStick.getRawAxis(TANK_LEFT_AXIS),
            () -> rightStick.getRawAxis(TANK_RIGHT_AXIS));

    SendableChooser<Boolean> drive_chooser = new SendableChooser<>();
    Trigger is_arcade = new Trigger(() -> drive_chooser.getSelected());

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        // Setup Drivetrain
        drive_chooser.setDefaultOption("arcade (if you choose this you are based)", true);
        drive_chooser.addOption("tank (armor iddd for wimds L rizz)", false);
        is_arcade
                .onFalse(Commands.run(() -> drive.setDefaultCommand(tank)))
                .onTrue(Commands.run(() -> drive.setDefaultCommand(arcade)));

        // Setup Claw
        claw.setDefaultCommand(
                new ClawCommand(
                        claw,
                        () -> xbox.getHID().getRawButton(BTN_CONE),
                        () -> xbox.getHID().getRawButton(BTN_CUBE)));

        // Setup Intake
        intake.setDefaultCommand(
                new IntakeCommand(
                        intake,
                        () -> xbox.getHID().getRawButton(BTN_INTAKE)));

        // Setup Compressor
        pcmCompressor.enableDigital();
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}