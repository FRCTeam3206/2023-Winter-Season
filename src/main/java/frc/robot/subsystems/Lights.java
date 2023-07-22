package frc.robot.subsystems;


import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lights extends SubsystemBase {

  private AddressableLED m_led; 
  private AddressableLEDBuffer m_ledBuffer;
  private int m_rainbowFirstPixelHue = 0;
  private int count = 0;
  private int count2 = 0;
  private boolean count3 = false;
  /*
   * showColor 0 - Rainbow
   * showColor 1 - Solid color (blue)
   * showColor 2 - (slow theatre chase style alternation between r1,g1,b1 & r2,g2,b2
   * showColor 3 - rainbow for 1 sec, 
   */
  public int showColor = 0;

  /*
  //Three colors for ColorSwitch
  public int r1 = 0, g1 = 0, b1 = 200;
  public int r2 = 150, g2 = 150, b2 = 150;
   */


  public Lights() {
    // PWM port 0
    // Must be a PWM header, not MXP or DIO
    m_led = new AddressableLED(0);

    // Reuse buffer
    // Default to a length of 60, start empty output
    // Length is expensive to set, so only set it once, then just update data
    m_ledBuffer = new AddressableLEDBuffer(150);
    m_led.setLength(m_ledBuffer.getLength());

    // Set the data
    m_led.setData(m_ledBuffer);
    m_led.start();


    
  }

  @Override
  public void periodic() {
    //Chooses and uses a mode for the lights based on showColor
    if (showColor == 0){
        rainbow(m_rainbowFirstPixelHue);
        m_led.setData(m_ledBuffer);
    } else if (showColor == 1) {
    } else if (showColor == 2) {
      if (count == 13) {
        ColorSwitch();
        m_led.setData(m_ledBuffer);
        count = 0;
      }
      count ++;
    }else if (showColor == 3) {
      if (count2 < 50) {
        rainbow(m_rainbowFirstPixelHue);
        m_led.setData(m_ledBuffer);
      } else if (count2 < 100) {
        if (count == 13) {
          ColorSwitch();
          m_led.setData(m_ledBuffer);
          count = 0;
        }
        count ++;
      }
      count2++;
    }
  }

  /**
   * makes a fun rainbow pattern
   * 
   * Copied from the addressable LED library WPILib documentation
   */
  private void rainbow(int m_rainbowFirstPixelHue) {
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Calculate the hue - hue is easier for rainbows because the color
      // shape is a circle so only one value needs to precess
      final var hue = (m_rainbowFirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;
      // Set the value
      m_ledBuffer.setHSV(i, hue, 255, 128);
    }

    m_rainbowFirstPixelHue += 3;
    // Check bounds
    m_rainbowFirstPixelHue %= 180;

  }

  public void setRainbow() {
    showColor = 0;
  }

  /**
   * sets all LED's to the rgb color specified from the three aproprately named variables
   */
  public void setLightColor(int ColorRed,int ColorGreen,int ColorBlue) {
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
        // Sets the specified LED to the RGB values for red
        m_ledBuffer.setRGB(i, ColorRed, ColorGreen, ColorBlue);
     }
     showColor = 1;
     m_led.setData(m_ledBuffer);
  }

  public void setColorSwitch(int r1, int g1, int b1, int r2, int g2, int b2) {
    showColor = 2;
  }

  public void showOff() {
    showColor = 3;
  }

  /**
   * Alternates colors for each led, then switches them every time it's called
   */
  private void ColorSwitch(int r1, int g1, int b1, int r2, int g2, int b2) {

    if (count3 == false) {

      for (var i = 0; i < m_ledBuffer.getLength(); i+=2) {
        m_ledBuffer.setRGB(i, r1, g1, b1);
      }

      for (var i = 1; i < m_ledBuffer.getLength(); i+=2) {
        m_ledBuffer.setRGB(i, r2, g2, b2);
      }
      count3 = true;
    } else if (count3 == true) {

      for (var i = 1; i < m_ledBuffer.getLength(); i+=2) {
        m_ledBuffer.setRGB(i, r1, g1, b1);
      }

      for (var i = 0; i < m_ledBuffer.getLength(); i+=2) {
        m_ledBuffer.setRGB(i, r2, g2, b2);
      }
      count3 = false;
    }
    
  }

}

