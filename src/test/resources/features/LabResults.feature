Feature: ImmunoTrack Patient Lab Results

  Background:
    Given the user is logged into the patient portal
    And the user navigates to the Lab Results page

  Scenario: View laboratory biomarker results
    Then the user should see lab results and biomarker readings
