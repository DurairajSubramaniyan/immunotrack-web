@smoke
Feature: ImmunoTrack Full Application Comprehensive Smoke Test Suite

  Background:
    Given the patient logs in with valid credentials

  # =========================================================================
  # 1. DASHBOARD PAGE SMOKE
  # =========================================================================
  @smoke @dashboardSmoke
  Scenario: Smoke check - Dashboard page header, greeting, status cards, action buttons, and footer
    Then the patient should see the dashboard greeting header and portal title
    And the patient should see the monitoring status and flare risk cards with values
    And the patient should see the dashboard action buttons to log symptoms or start assessment
    And the page footer or bottom branding should be visible

  # =========================================================================
  # 2. LOG SYMPTOMS PAGE SMOKE
  # =========================================================================
  @smoke @logSymptomsSmoke
  Scenario: Smoke check - Log Symptoms page title, symptom assessment domains, and save button
    When the patient navigates to the Log Symptoms module
    Then the Log Symptoms page title and header should be visible
    And the clinical symptom assessment domains should be displayed
    And the save daily log action button should be visible
    And the page footer or bottom branding should be visible

  # =========================================================================
  # 3. MEDICATIONS PAGE SMOKE
  # =========================================================================
  @smoke @medicationsSmoke
  Scenario: Smoke check - Medications page title, Quick Add medication form, and Add to Plan button
    When the patient navigates to the Medications module
    Then the Medications page title and header should be visible
    And the Quick Add medication section and form fields should be displayed
    And the Add to Plan action button should be visible
    And the active medications tracking section should be displayed
    And the page footer or bottom branding should be visible

  # =========================================================================
  # 4. HISTORY PAGE SMOKE
  # =========================================================================
  @smoke @historySmoke
  Scenario: Smoke check - History page title, date filter buttons, and symptom entries
    When the patient navigates to the History module
    Then the Symptom History page title and header should be visible
    And the date filter section with quick range buttons should be displayed
    And the page footer or bottom branding should be visible

  # =========================================================================
  # 5. INSIGHTS PAGE SMOKE
  # =========================================================================
  @smoke @insightsSmoke
  Scenario: Smoke check - Insights page title, health analytics, and risk factors widgets
    When the patient navigates to the Insights module
    Then the Health Insights page title and header should be visible
    And the predictive insights and analytics widgets should be displayed
    And the page footer or bottom branding should be visible

  # =========================================================================
  # 6. LAB RESULTS PAGE SMOKE
  # =========================================================================
  @smoke @labResultsSmoke
  Scenario: Smoke check - Lab Results page title, biomarker readings, and result cards
    When the patient navigates to the Lab Results module
    Then the Lab Results page title and header should be visible
    And the laboratory biomarker cards and readings should be displayed
    And the page footer or bottom branding should be visible

  # =========================================================================
  # 7. PROFILE PAGE SMOKE
  # =========================================================================
  @smoke @profileSmoke
  Scenario: Smoke check - Profile page title, patient summary, contact information, and save button
    When the patient navigates to the Profile module
    Then the Profile page title and header should be visible
    And the patient account summary with condition and age should be displayed
    And the contact information section with email and phone should be displayed
    And the Save Profile Changes action button should be visible
    And the page footer or bottom branding should be visible

  # =========================================================================
  # 8. FULL END-TO-END APPLICATION TOUR
  # =========================================================================
  @smoke @fullAppTour
  Scenario: Smoke check - End-to-end full application navigation tour across all modules
    Then the patient should be on the healthy Dashboard module
    When the patient navigates to the Log Symptoms module
    Then the Log Symptoms module should load successfully
    When the patient navigates to the Medications module
    Then the Medications module should load successfully
    When the patient navigates to the History module
    Then the History module should load successfully
    When the patient navigates to the Insights module
    Then the Insights module should load successfully
    When the patient navigates to the Lab Results module
    Then the Lab Results module should load successfully
    When the patient navigates to the Profile module
    Then the Profile module should load successfully
    When the patient navigates back to the Dashboard module
    Then the patient should be on the healthy Dashboard module

  # =========================================================================
  # 9. SECURE LOGOUT SMOKE
  # =========================================================================
  @smoke @logoutSmoke
  Scenario: Smoke check - Verify patient can securely log out
    Then the patient should be on the healthy Dashboard module
    When the patient clicks the logout button
    Then the patient should be redirected to the Login page
