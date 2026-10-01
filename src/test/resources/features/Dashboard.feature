@dashboard
Feature: ImmunoTrack Patient Dashboard / Home Page Verification and Navigation

  Background:
    Given the user is logged into the patient portal

  # =====================================================================
  # HOME / DASHBOARD HEADER & GREETING VERIFICATION
  # =====================================================================

  Scenario: Verify Home / Dashboard greeting header, subtitle and breadcrumbs
    Then the user should see the patient portal greeting header and monitoring subtitle

  # =====================================================================
  # YOUR MONITORING STATUS BADGE & COMPLIANCE LOGIC VERIFICATION
  # =====================================================================

  Scenario: Verify Your Monitoring card status badge rules and toggle collapse details
    Then the patient should see the monitoring status card
    And the monitoring status badge should match the condition for Goal Reached, At Risk, Behind, or On Track
    And the user can toggle the monitoring card details visibility

  # =====================================================================
  # TODAY'S SYMPTOMS SCORES, CLINICAL TITLES & SEVERITY THRESHOLDS
  # =====================================================================

  Scenario: Verify Today's Symptoms scores, clinical titles, overall risk, and severity levels
    Then the entered symptom scores should match correctly under Today's Symptoms
    And the symptom section clinical titles should match the score threshold rules
    And the overall risk score and severity cards should be verified under Today's Symptoms
    And the severity classification should match the overall risk level thresholds

  # =====================================================================
  # INSIGHTS & MEDICATIONS WIDGETS VERIFICATION
  # =====================================================================

  Scenario: Verify Insights and Medications side widgets content
    Then the patient should see the flare risk card
    And the Insights widget should display flare prediction or data logging guidance
    And the Medications widget should display tracked medications or fallback text

  # =====================================================================
  # SIDEBAR NAVIGATION VERIFICATION
  # =====================================================================

  Scenario Outline: Navigate to all portal sections via sidebar navigation menu
    When the patient clicks on "<menu_item>" in the sidebar menu
    Then the patient should be redirected to the <expected_page>

    Examples:
      | menu_item    | expected_page            |
      | Log Symptoms | Daily Health Log page    |
      | Medications  | Medications page         |
      | Lab Results  | Lab Results page         |
      | History      | History page             |
      | Insights     | Insights page            |

