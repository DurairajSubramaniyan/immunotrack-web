Feature: ImmunoTrack Daily Health Log Symptoms

  Background:
    Given the user is logged into the patient portal
    And the user navigates to the Log Symptoms page

  Scenario: Daily Health Log page displays clinical assessment domains
    Then the user should see the ACQ-6 Asthma Control section
    And the user should see the SNOT-22 Nose and Sinus section
    And the user should see the POEM Skin Symptoms section

  Scenario: Submitting daily health log form
    When the user selects score ratings for symptoms
    And the user submits the daily health log
    Then the log should be successfully saved
