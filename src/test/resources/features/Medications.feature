Feature: ImmunoTrack Patient Medications Management

  Background:
    Given the user is logged into the patient portal
    And the user navigates to the Medications page

  Scenario: View active prescriptions
    Then the user should see active medication prescriptions
