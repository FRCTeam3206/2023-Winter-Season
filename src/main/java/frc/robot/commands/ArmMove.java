package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class ArmMove extends CommandBase {

    private Arm m_arm;

    public ArmMove(Arm arm, Supplier<Double> power) {
        this.m_arm = arm;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        this.m_arm.setArmPower();
    }

    @Override
    public void end(boolean interrupted) {
    }

}