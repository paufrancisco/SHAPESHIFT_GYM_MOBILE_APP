package com.jtdev.shape_shift;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.jtdev.shape_shift.fragments.Login;
import com.jtdev.shape_shift.fragments.SignUp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;


public class ExampleUnitTest {

    private Login loginFrag;

    @Mock
    private FirebaseAuth mockFirebaseAuth;

    @Mock
    private Context mockContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        loginFrag = new Login();
    }

    @Test
    public void validSignIn() throws InterruptedException {
        // Mock successful sign-in
        when(mockFirebaseAuth.signInWithEmailAndPassword("1@gmail.com", "123123"))
                .thenReturn(mock(Task.class));

        loginFrag.checkUser("1@gmail.com", "123123", mockFirebaseAuth, mockContext);

        Thread.sleep(1000);

        verify(mockFirebaseAuth).signInWithEmailAndPassword("1@gmail.com", "123123");
    }

    @Test
    public void notValidSignIn() {

        String invalidEmail = "2@gmail.com";
        String invalidPass = "invalid";

        Task<AuthResult> failedTask = Tasks.forException(new FirebaseAuthException("123", "Wrong Credentials"));
        when(mockFirebaseAuth.signInWithEmailAndPassword(eq(invalidEmail), eq(invalidPass)))
                .thenReturn(failedTask);

        loginFrag.checkUser(invalidEmail, invalidPass, mockFirebaseAuth, mockContext);

        verify(mockFirebaseAuth).signInWithEmailAndPassword(invalidEmail, invalidPass);
    }

    @Test
    public void signInWithEmptyCredentials() {
        String invalidEmail = "";
        String invalidPass = "";

        Task<AuthResult> failedTask = Tasks.forException(new FirebaseAuthException("123", "Please enter your email and password"));
        when(mockFirebaseAuth.signInWithEmailAndPassword(eq(invalidEmail), eq(invalidPass)))
                .thenReturn(failedTask);

        loginFrag.checkUser(invalidEmail, invalidPass, mockFirebaseAuth, mockContext);

        verify(mockFirebaseAuth).signInWithEmailAndPassword(invalidEmail, invalidPass);
    }

    @Test
    public void signInWithNullCredentials() {
        String invalidEmail = null;
        String invalidPass = null;

        Task<AuthResult> failedTask = Tasks.forException(new FirebaseAuthException("123", "Complete all fields"));
        when(mockFirebaseAuth.signInWithEmailAndPassword(eq(invalidEmail), eq(invalidPass)))
                .thenReturn(failedTask);

        loginFrag.checkUser(invalidEmail, invalidPass, mockFirebaseAuth, mockContext);

        verify(mockFirebaseAuth).signInWithEmailAndPassword(invalidEmail, invalidPass);
    }

    @Test
    public void signInWithNullEmail() {

        String invalidEmail = null;
        String invalidPass = "123123";

        Task<AuthResult> failedTask = Tasks.forException(new FirebaseAuthException("123", "Please enter your email"));
        when(mockFirebaseAuth.signInWithEmailAndPassword(eq(invalidEmail), eq(invalidPass)))
                .thenReturn(failedTask);

        loginFrag.checkUser(invalidEmail, invalidPass, mockFirebaseAuth, mockContext);

        verify(mockFirebaseAuth).signInWithEmailAndPassword(invalidEmail, invalidPass);

    }

    @Test
    public void signInWithNullPassword() {
        String invalidEmail = "1@gmail.com";
        String invalidPass = null;

        Task<AuthResult> failedTask = Tasks.forException(new FirebaseAuthException("123", "Please enter your password"));
        when(mockFirebaseAuth.signInWithEmailAndPassword(eq(invalidEmail), eq(invalidPass)))
                .thenReturn(failedTask);

        loginFrag.checkUser(invalidEmail, invalidPass, mockFirebaseAuth, mockContext);

        verify(mockFirebaseAuth).signInWithEmailAndPassword(invalidEmail, invalidPass);
    }

    @Test
    public void testGetUnderweightRecommendation() {
        class_bmiCalculator bmiCalculator = new class_bmiCalculator();
        // Given
        float underHeight = 155f; //height in cm
        float underWeight = 40f; //weight in kg
        String expectedRecommendation = "     You are UNDERWEIGHT. It's important to maintain a balanced diet. " +
                "We suggest you to try gain weight with proper guidance using our workout plan.";
        // When
        float bmi = bmiCalculator.calculateBMI(underHeight, underWeight);
        String recommendation = bmiCalculator.getRecommendation(bmi);

        // Then
        assertEquals(expectedRecommendation, recommendation);
    }

    @Test
    public void testGetNormalWeightRecommendation() {
        class_bmiCalculator bmiCalculator = new class_bmiCalculator();
        // Given
        float normalHeight = 175f; //height in cm
        float normalWeight = 70f; //weight in kg
        String expectedRecommendation = "     Your weight is within a HEALTHY range. Keep up the good work and make sure you get frequent exercise and a healthy diet. " +
                "Try our training regimen to keep a regular body.";
        // When
        float bmi = bmiCalculator.calculateBMI(normalHeight, normalWeight);
        String recommendation = bmiCalculator.getRecommendation(bmi);

        // Then
        assertEquals(expectedRecommendation, recommendation);
    }

    @Test
    public void testGetOverweightRecommendation() {
        class_bmiCalculator bmiCalculator = new class_bmiCalculator();
        // Given
        float overweightHeight = 175f; //height in cm
        float overweightWeight = 85f; //weight in kg
        String expectedRecommendation = "     You are OVERWEIGHT. To get a healthy weight, " +
                "think about changing your lifestyle by consuming healthier food and engaging in more physical activity. " +
                "Try our exercise program to start your new lifestyle.";
        // When
        float bmi = bmiCalculator.calculateBMI(overweightHeight, overweightWeight);
        String recommendation = bmiCalculator.getRecommendation(bmi);

        // Then
        assertEquals(expectedRecommendation, recommendation);
    }

    @Test
    public void testGetObesityRecommendation() {
        class_bmiCalculator bmiCalculator = new class_bmiCalculator();
        // Given
        float obeseHeight = 175f; //height in cm
        float obeseWeight = 100f; //weight in kg
        String expectedRecommendation = "     You are OBESE. It's critical to put your health first by making lifestyle adjustments. " +
                "Using our exercise program can help you lower the health risks linked to obesity.";
        // When
        float bmi = bmiCalculator.calculateBMI(obeseHeight, obeseWeight);
        String recommendation = bmiCalculator.getRecommendation(bmi);

        // Then
        assertEquals(expectedRecommendation, recommendation);
    }

    @Test
    // Sign-Up Test
    public void signup_isCorrect() {
        SignUp service = new SignUp();

        // Testing Sign-Up Valid Credentials
        assertTrue(service.isPasswordValid("StrongPassword123!"));
        // valid

        // Testing password requirements
        assertFalse(service.isPasswordValid("weak"));           // Password too short
        assertFalse(service.isPasswordValid("weakpassword"));   // No uppercase letters
        assertFalse(service.isPasswordValid("PASSWORD123"));    // No lowercase letters
        assertFalse(service.isPasswordValid("password"));       // No numbers
        assertFalse(service.isPasswordValid("WeakPassword"));   // No special characters

        // Sign up are invalid if one of the requirement did not meet the Password requirements.
    }

    // Forgot Password Tests
    @Test
    public void forgotPassword_isCorrect() {
        // Mocking the FirebaseAuth instance
        FirebaseAuth mockAuth = mock(FirebaseAuth.class);
        Task<Void> mockTask = mock(Task.class);

        // Mock the sendPasswordResetEmail method to return the mocked task
        when(mockAuth.sendPasswordResetEmail(eq("valid@example.com")))
                .thenReturn(mockTask);

        when(mockTask.isSuccessful()).thenReturn(true);

        AccountService accountService = new AccountService();
        assertTrue(accountService.forgotPassword("valid@example.com", mockAuth));
    }

    @Test
    public void forgotPassword_invalidEmailFormat() {
        FirebaseAuth mockAuth = mock(FirebaseAuth.class);

        AccountService accountService = new AccountService();
        assertFalse(accountService.forgotPassword("invalid-email", mockAuth));
    }

    @Test
    public void forgotPassword_nonexistentEmail() {

        FirebaseAuth mockAuth = mock(FirebaseAuth.class);
        Task<Void> mockTask = mock(Task.class);


        when(mockAuth.sendPasswordResetEmail(eq("nonexistent@example.com")))
                .thenReturn(mockTask);

        when(mockTask.isSuccessful()).thenReturn(false);

        AccountService accountService = new AccountService();
        assertFalse(accountService.forgotPassword("nonexistent@example.com", mockAuth));
    }
}

  
