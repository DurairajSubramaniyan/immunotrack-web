Feature: ImmunoTrack SNOT-22 Clinical Health Assessment Workflows

  @snot22 @snot22-intake
  Scenario: Verify Intake SNOT-22 Assessment landing page, question flow, and score calculation
    Given the user navigates to the login page
    When the user enters a valid email "newpatient__01@gmail.com"
    And the user enters a valid password "Immunotrack@123"
    And the user clicks the Log In button
    Then the user should see the SNOT-22 "Intake" landing page with correct header and description
    And the "Remind Me Later" button should not be present on the SNOT-22 landing page
    When the user begins the SNOT-22 check-in
    And the user answers all 22 SNOT-22 questions with rating 1 across all domains
    Then the SNOT-22 completion screen should display the correct calculated score and severity

  @snot22 @snot22-monthly
  Scenario: Verify Monthly SNOT-22 Assessment landing page, question flow, and score calculation
    Given the user navigates to the login page
    When the user enters a valid email "durairaj10@gmail.com"
    And the user enters a valid password "Immunotrack@123"
    And the user clicks the Log In button
    Then the user should see the SNOT-22 "Monthly" landing page with correct header and description
    And the "Remind Me Later" button should not be present on the SNOT-22 landing page
    When the user begins the SNOT-22 check-in
    And the user answers all 22 SNOT-22 questions with rating 0 across all domains
    Then the SNOT-22 completion screen should display the correct calculated score and severity

  @snot22 @snot22-clinician-remind
  Scenario: Verify Clinician Request SNOT-22 Assessment Remind Me Later deferral flow
    Given the user navigates to the login page
    When the user enters a valid email "immunotrack456@gmail.com"
    And the user enters a valid password "Immunotrack@123"
    And the user clicks the Log In button
    Then the user should see the SNOT-22 "Clinician" landing page with correct header and description
    And the "Remind Me Later" button should be present on the SNOT-22 landing page
    When the user clicks the Remind Me Later button
    Then the user should be dismissed to the dashboard page

  @snot22 @snot22-clinician-complete
  Scenario: Verify Clinician Request SNOT-22 Assessment full completion and score calculation
    Given the user navigates to the login page
    When the user enters a valid email "immunotrack456@gmail.com"
    And the user enters a valid password "Immunotrack@123"
    And the user clicks the Log In button
    Then the user should see the SNOT-22 "Clinician" landing page with correct header and description
    And the "Remind Me Later" button should be present on the SNOT-22 landing page
    When the user begins the SNOT-22 check-in
    And the user answers all 22 SNOT-22 questions with rating 1 across all domains
    Then the SNOT-22 completion screen should display the correct calculated score and severity
