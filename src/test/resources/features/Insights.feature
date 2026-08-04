Feature: ImmunoTrack Patient Health Insights

  Background:
    Given the user is logged into the patient portal
    And the user navigates to the Insights page

  Scenario: View predictive health insights and analytics
    Then the user should see health insights analytics
