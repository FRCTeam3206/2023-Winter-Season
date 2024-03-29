// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;

public class Robot extends TimedRobot {
    private Command m_autonomousCommand;
    private RobotContainer m_robotContainer;

    @Override
    public void robotInit() {
        m_robotContainer = new RobotContainer();
        m_robotContainer.claw.open();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        m_robotContainer.claw.open();
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void disabledExit() {
    }

    @Override
    public void autonomousInit() {
        m_autonomousCommand = m_robotContainer.getAutonomousCommand();

        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void autonomousExit() {
    }

    @Override
    public void teleopInit() {
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }
        m_robotContainer.drive.setDefaultCommand(m_robotContainer.drive_chooser.getSelected());

    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void teleopExit() {
        m_robotContainer.drive_chooser.getSelected().cancel();
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testExit() {
    }

    GenericHID xbox = new GenericHID(Constants.Ports.XBOX_CONTROLLER);

    @Override
    public void testPeriodic() {
        if (xbox.getRawAxis(1) > .5) {
            m_robotContainer.intake.overrideTransport(-.5);
        } else if (xbox.getRawAxis(1) < -.5) {
            m_robotContainer.intake.overrideTransport(.5);
        } else {
            m_robotContainer.intake.overrideTransport(0);
        }
    }
}
