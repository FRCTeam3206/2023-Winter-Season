package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class ArmMove extends CommandBase {

    private Arm m_arm;
    Supplier<Double> power;

    public ArmMove(Arm arm, Supplier<Double> power) {
        this.m_arm = arm;
        this.power = power;
        addRequirements(arm);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        this.m_arm.setArmPower(power.get());
        SmartDashboard.putNumber("Arm Power", power.get());

    }

    @Override
    public void end(boolean interrupted) {
    }

}