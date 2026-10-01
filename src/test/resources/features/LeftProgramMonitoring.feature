@left_program
Feature: Left Program User Experience and Monitoring Suspended Verification

  Background:
    Given the user navigates to the login page
    When the user enters a valid email "duraiim@gmail.com"
    And the user enters a valid password "Vikki@52524"
    And the user clicks the Log In button
    Then the user should see the dashboard page or a login error if credentials are mock

  # =========================================================================
  # 1. HOME / DASHBOARD: BANNER, TITLE, CONTENT & 16-DAY BAR ABSENCE
  # =========================================================================
  @dashboard_left_program
  Scenario: Verify Monitoring Suspended banner and absence of 16-day progress bar on home page
    Then the user should see the Monitoring Suspended banner on the home page
    And the 16 days progress bar should not be displayed on the home page

  # =========================================================================
  # 2. DAILY HEALTH LOG: HEADER & 16-DAY BAR ABSENCE
  # =========================================================================
  @daily_log_left_program
  Scenario: Verify Daily Health Log page and absence of 16-day progress bar
    When the patient clicks on "Log Symptoms" in the sidebar menu
    Then the patient should be redirected to the Daily Health Log page
    And the 16 days progress bar should not be displayed on the Daily Health Log page
    When the patient clicks on "Home" in the sidebar menu
    Then the user should see the dashboard page or a login error if credentials are mock

  # =========================================================================
  # 3. PROFILE SCREEN: SETTINGS & PRIVACY AND LEFT PROGRAM BADGE
  # =========================================================================
  @profile_left_program
  Scenario: Verify Profile screen Settings and Privacy section and Left Program badge
    When the patient navigates to the Profile page from the top navigation
    Then the user should see the Settings and Privacy section with all configuration options
    And the Remote Monitoring item should display "Left Program" badge

  # =========================================================================
  # 4. REMOTE MONITORING OVERVIEW SCREEN: HEADERS, CARDS & ABOUT RTM
  # =========================================================================
  @remote_monitoring_overview
  Scenario: Verify Remote Monitoring details page content, headers, and about RTM breakdown
    When the patient navigates to the Profile page from the top navigation
    And the user clicks on Remote Monitoring in Settings and Privacy
    Then the user should be redirected to the Remote Monitoring overview page
    And the user should see the Remote Monitoring title, subtitle, and breadcrumb
    And the user should see the Left Monitoring status card with description and Re-join button
    And the user should see the ABOUT RTM section with all three informational cards
    When the user clicks the back arrow from Remote Monitoring overview
    Then the user should see the Settings and Privacy section

  # =========================================================================
  # 5. RE-JOIN MONITORING VIA DASHBOARD BANNER NAVIGATION
  # =========================================================================
  @rejoin_monitoring_navigation
  Scenario: Navigate to Re-join page via dashboard banner button and verify page title, header, and content
    When the user clicks on Re-join Monitoring button on the dashboard banner
    Then the user should be redirected to the Remote Monitoring overview page
    And the user should see the Remote Monitoring title, subtitle, and breadcrumb
    And the user should see the Left Monitoring status card with description and Re-join button
    And the user should see the ABOUT RTM section with all three informational cards

