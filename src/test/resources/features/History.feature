Feature: ImmunoTrack Patient Symptom History

  Background:
    Given the user is logged into the patient portal
    And the user navigates to the History page

  Scenario: View historical symptom log timeline
    Then the user should see historical symptom logs
