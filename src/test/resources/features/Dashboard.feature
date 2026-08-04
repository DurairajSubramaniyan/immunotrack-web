Feature: ImmunoTrack Patient Dashboard Navigation and Widgets

  Background:
    Given the user is logged into the patient portal

  Scenario: Patient Dashboard renders essential widgets
    Then the patient should see the monitoring status card
    And the patient should see the flare risk card

  Scenario: Navigate to Log Symptoms page from sidebar
    When the patient clicks on "Log Symptoms" in the sidebar menu
    Then the patient should be redirected to the Daily Health Log page

  Scenario: Navigate to Medications page from sidebar
    When the patient clicks on "Medications" in the sidebar menu
    Then the patient should be redirected to the Medications page

  Scenario: Navigate to Lab Results page from sidebar
    When the patient clicks on "Lab Results" in the sidebar menu
    Then the patient should be redirected to the Lab Results page

  Scenario: Navigate to History page from sidebar
    When the patient clicks on "History" in the sidebar menu
    Then the patient should be redirected to the History page

  Scenario: Navigate to Insights page from sidebar
    When the patient clicks on "Insights" in the sidebar menu
    Then the patient should be redirected to the Insights page
