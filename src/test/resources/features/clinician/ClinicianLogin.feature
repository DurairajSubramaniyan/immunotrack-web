@clinician @auth
Feature: Clinician Portal Authentication

  Scenario: Successful login to Clinician portal with Google Authenticator 2FA
    Given the user navigates to the clinician login page
    When the clinician enters credentials from config
    And the clinician clicks the Log In button
    And the clinician enters the Google Authenticator 2FA code
    And the clinician clicks the Verify OTP button
    Then the clinician should be successfully logged in to the clinician dashboard
