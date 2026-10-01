@admin @auth
Feature: Admin Portal Authentication

  Scenario: Successful login to Admin portal with Google Authenticator 2FA
    Given the user navigates to the admin login page
    When the admin enters credentials from config
    And the admin clicks the Log In button
    And the admin enters the Google Authenticator 2FA code
    And the admin clicks the Verify OTP button
    Then the admin should be successfully logged in to the admin dashboard
