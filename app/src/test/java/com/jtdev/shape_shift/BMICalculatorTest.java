package com.jtdev.shape_shift;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class BMICalculatorTest {

    private class_bmiCalculator calculator;

    @Before
    public void setUp() {
        calculator = new class_bmiCalculator();
    }

    @Test
    public void testBMICalculation() {
        float heightInCm = 175f;
        float weightInKg = 70f;
        float expectedBMI = 22.86f;

        float bmi = calculator.calculateBMI(heightInCm, weightInKg);

        assertEquals(expectedBMI, bmi, 0.01);
    }

    @Test
    public void testUnderweightProgram() {
        float bmi = 17.5f;
        String expectedProgram = "Gain Weight";

        String program = calculator.getProgram(bmi);

        assertEquals(expectedProgram, program);
    }

    @Test
    public void testNormalWeightProgram() {
        float bmi = 22f;
        String expectedProgram = "Maintain Healthy";

        String program = calculator.getProgram(bmi);

        assertEquals(expectedProgram, program);
    }

    @Test
    public void testOverweightProgram() {
        float bmi = 27.5f;
        String expectedProgram = "Lose Weight";

        String program = calculator.getProgram(bmi);

        assertEquals(expectedProgram, program);
    }

    @Test
    public void testObeseProgram() {
        float bmi = 32f;
        String expectedProgram = "Significant Weight Loss";

        String program = calculator.getProgram(bmi);

        assertEquals(expectedProgram, program);
    }

}
