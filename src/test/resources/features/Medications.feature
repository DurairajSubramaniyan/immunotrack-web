@medications
Feature: Medications Page - Quick Add Medication and Tracking

  Background:
    Given the user navigates to the login page
    When the user enters a valid email "immunotrack123@gmail.com"
    And the user enters a valid password "Immunotrack@123"
    And the user clicks the Log In button
    Then the user should see the dashboard page or a login error if credentials are mock
    And the user navigates to the Medications page

  # =====================================================================
  # PAGE LOAD & UI VERIFICATION
  # =====================================================================

  Scenario: Verify Medications page loads successfully
    Then the Medications page title should be displayed
    And the subtitle "Track your daily medication adherence" should be visible
    And the "QUICK ADD MEDICATION" section should be visible
    And the "ADD TO PLAN" button should be visible

  Scenario: Verify header elements are displayed
    Then the "Patient Portal" breadcrumb should be visible
    And the "Medications" breadcrumb should be visible
    And the theme toggle button should be visible
    And the notification bell button should be visible
    And the user avatar "PS" should be visible
    And the username "pavithra" should be visible
    And the user role "Patient" should be visible

  Scenario: Verify sidebar navigation items are displayed
    Then the sidebar should show "Home" menu item
    And the sidebar should show "Log Symptoms" menu item
    And the sidebar should show "Medications" menu item as active
    And the sidebar should show "Lab Results" menu item
    And the sidebar should show "History" menu item
    And the sidebar should show "Insights" menu item
    And the sidebar should show "Log Out" menu item

  Scenario: Verify Quick Add Medication form fields are displayed
    Then the "CATEGORY" dropdown should be visible
    And the "MEDICATION" dropdown should be visible
    And the "DOSAGE" input field should be visible
    And the "FREQUENCY" dropdown should be visible
    And the "START DATE" field should be visible
    And the "NOTES (OPTIONAL)" field should be visible

  Scenario: Verify Start Date defaults to today's date
    Then the Start Date field should display today's date

  Scenario: Verify Medications and Reminders tabs are displayed
    Then the "Medications" tab should be visible with a count badge
    And the "Reminders" tab should be visible with a count badge

  Scenario: Verify filter tabs are displayed under Medications tab
    When the user clicks the "Medications" tab
    Then the "Today" filter tab should be visible
    And the "Future" filter tab should be visible
    And the "Missed" filter tab should be visible
    And the "Discontinued" filter tab should be visible
    And the "Yesterday" filter tab should be visible
    And the "Missed Logs" filter tab should be visible

  Scenario: Verify filter tabs are displayed under Reminders tab
    When the user clicks the "Reminders" tab
    Then the "Pending" filter tab should be visible
    And the "Completed" filter tab should be visible

  Scenario: Verify Weekly Adherence section is displayed
    Then the "Weekly Adherence" section should be visible
    And the adherence percentage should be displayed
    And the "Last 7 days" label should be visible
    And the "Doses Taken" count should be displayed
    And the "Doses Missed" count should be displayed

  # =====================================================================
  # CATEGORY DROPDOWN VERIFICATION
  # =====================================================================

  Scenario: Verify Category dropdown options
    When the user clicks the Category dropdown
    Then the Category dropdown should contain the following options:
      | Antihistamine          |
      | Biologic               |
      | Epinephrine            |
      | ICS/LABA Combo         |
      | Immunotherapy          |
      | Inhaled Corticosteroid |
      | LABA                   |
      | Leukotriene Modifier   |
      | Mast Cell Stabilizer   |
      | Nasal Spray            |
      | Nasal Spray / Oral     |
      | Oral                   |
      | SABA                   |

  # =====================================================================
  # MEDICATION DROPDOWN - DYNAMIC BASED ON CATEGORY
  # =====================================================================

  Scenario: Verify Medication dropdown updates when Antihistamine is selected
    When the user selects "Antihistamine" from the Category dropdown
    And the user clicks the Medication dropdown
    Then the Medication dropdown should contain:
      | Chlorpheniramine |
      | Hydroxyzine      |
      | Bilastine        |
      | Rupatadine       |
      | Loratadine       |
      | Fexofenadine     |
      | Desloratadine    |
      | Levocetirizine   |
      | Diphenhydramine  |
      | Cetirizine       |

  Scenario: Verify Medication dropdown updates when Epinephrine is selected
    When the user selects "Epinephrine" from the Category dropdown
    And the user clicks the Medication dropdown
    Then the Medication dropdown should contain:
      | Epinephrine Nasal Spray  |
      | Epinephrine Auto-Injector|

  Scenario: Verify Medication dropdown updates when SABA is selected
    When the user selects "SABA" from the Category dropdown
    And the user clicks the Medication dropdown
    Then the Medication dropdown should contain:
      | Albuterol (Salbutamol) |
      | Levalbuterol           |
      | Pirbuterol             |

  Scenario: Verify Medication dropdown updates when Leukotriene Modifier is selected
    When the user selects "Leukotriene Modifier" from the Category dropdown
    And the user clicks the Medication dropdown
    Then the Medication dropdown should contain:
      | Zafirlukast |
      | Zileuton    |
      | Montelukast |

  # =====================================================================
  # FREQUENCY & REMINDER SETTINGS
  # =====================================================================

  Scenario: Verify PRN reminder message for As needed frequency
    When the user selects "Epinephrine" from the Category dropdown
    And the user selects "Epinephrine Nasal Spray" from the Medication dropdown
    And the user selects "As needed (PRN)" from the Frequency dropdown
    Then the reminder message should show "This medication is taken as needed. No daily reminders will be scheduled. You can log it manually when taken."

  Scenario: Verify Preferred Time field appears for scheduled frequency
    When the user selects "Inhaled Corticosteroid" from the Category dropdown
    And the user selects "Budesonide Inhaler" from the Medication dropdown
    And the user selects "Once daily" from the Frequency dropdown
    Then the "Preferred Time 1" field should be visible in Reminder Settings
    And the default time should be "08:00 AM"

  Scenario: Verify unit dropdown is disabled for Biologic category
    When the user selects "Biologic" from the Category dropdown
    Then the unit dropdown should be disabled

  Scenario: Verify unit dropdown is enabled for Antihistamine category
    When the user selects "Antihistamine" from the Category dropdown
    And the user selects "Chlorpheniramine" from the Medication dropdown
    Then the unit dropdown should be enabled
    And the unit should show "mg"

  # =====================================================================
  # ADD MEDICATION - POSITIVE SCENARIOS
  # =====================================================================

  Scenario: Successfully add Epinephrine medication with PRN frequency
    When the user selects "Epinephrine" from the Category dropdown
    And the user selects "Epinephrine Nasal Spray" from the Medication dropdown
    And the user enters "2" in the Dosage field
    And the user selects "As needed (PRN)" from the Frequency dropdown
    And the user clicks the "ADD TO PLAN" button
    Then the medication should be added successfully

  Scenario: Successfully add Antihistamine medication with notes
    When the user selects "Antihistamine" from the Category dropdown
    And the user selects "Cetirizine" from the Medication dropdown
    And the user enters "10" in the Dosage field
    And the user selects "Once daily" from the Frequency dropdown
    And the user enters "Take with food" in the Notes field
    And the user clicks the "ADD TO PLAN" button
    Then the medication should be added successfully

  Scenario: Successfully add Inhaled Corticosteroid with scheduled time
    When the user selects "Inhaled Corticosteroid" from the Category dropdown
    And the user selects "Budesonide Inhaler" from the Medication dropdown
    And the user enters "40" in the Dosage field
    And the user selects "Once daily" from the Frequency dropdown
    And the user sets the preferred time to "08:00 AM"
    And the user clicks the "ADD TO PLAN" button
    Then the medication should be added successfully

  Scenario: Successfully add SABA medication
    When the user selects "SABA" from the Category dropdown
    And the user selects "Albuterol (Salbutamol)" from the Medication dropdown
    And the user enters "2" in the Dosage field
    And the user selects "As needed (PRN)" from the Frequency dropdown
    And the user clicks the "ADD TO PLAN" button
    Then the medication should be added successfully

  # =====================================================================
  # ADD MEDICATION - NEGATIVE SCENARIOS
  # =====================================================================

  Scenario: Submit form without selecting Category
    When the user clicks the "ADD TO PLAN" button without filling any fields
    Then a validation error should be displayed for the Category field

  Scenario: Submit form without selecting Medication
    When the user selects "Epinephrine" from the Category dropdown
    And the user clicks the "ADD TO PLAN" button without selecting Medication
    Then a validation error should be displayed for the Medication field

  Scenario: Submit form without entering Dosage
    When the user selects "Epinephrine" from the Category dropdown
    And the user selects "Epinephrine Nasal Spray" from the Medication dropdown
    And the user clicks the "ADD TO PLAN" button without entering Dosage
    Then a validation error should be displayed for the Dosage field

  Scenario: Enter invalid dosage value
    When the user selects "Antihistamine" from the Category dropdown
    And the user selects "Cetirizine" from the Medication dropdown
    And the user enters "-5" in the Dosage field
    And the user clicks the "ADD TO PLAN" button
    Then a validation error should be displayed for invalid dosage

  # =====================================================================
  # FILTER TABS FUNCTIONALITY
  # =====================================================================

  Scenario: Click Today filter tab and verify medications displayed
    When the user clicks the "Medications" tab
    And the user clicks the "Today" filter tab
    Then medications scheduled for today should be displayed

  Scenario: Click Missed filter tab and verify missed medications displayed
    When the user clicks the "Medications" tab
    And the user clicks the "Missed" filter tab
    Then missed medications should be displayed
    And each missed medication card should show the reason

  Scenario: Click Yesterday filter tab and verify yesterday medications
    When the user clicks the "Medications" tab
    And the user clicks the "Yesterday" filter tab
    Then medications from yesterday should be displayed

  Scenario: Click Discontinued filter tab
    When the user clicks the "Medications" tab
    And the user clicks the "Discontinued" filter tab
    Then discontinued medications count should match the tab badge

  Scenario: Mark a medication as taken
    When the user clicks the "Medications" tab
    And the user clicks the "Missed" filter tab
    And the user clicks the "Mark as Taken" button for the first medication
    Then the medication should be marked as taken

  Scenario: Verify confirmation dialog appears before marking as taken
    When the user clicks the "Medications" tab
    And the user clicks the "Missed" filter tab
    And the user clicks the "Mark as Taken" button for the first medication without confirming
    Then the "Did you take this?" confirmation dialog should be displayed
    And the "Yes, I took it" button should be visible in the dialog
    And the "Cancel" button should be visible in the dialog

  Scenario: Cancel the Mark as Taken confirmation dialog
    When the user clicks the "Medications" tab
    And the user clicks the "Missed" filter tab
    And the user clicks the "Mark as Taken" button for the first medication without confirming
    And the user clicks "Cancel" on the confirmation dialog
    Then the medication should remain in the Missed list

  # =====================================================================
  # THEME TOGGLE
  # =====================================================================

  Scenario: Toggle dark and light mode
    When the user clicks the theme toggle button
    Then the page theme should switch accordingly

  # =====================================================================
  # NAVIGATION
  # =====================================================================

  Scenario: Navigate to Home from sidebar
    When the user clicks "Home" in the sidebar
    Then the user should be redirected to the Home page

  Scenario: Navigate to Log Symptoms from sidebar
    When the user clicks "Log Symptoms" in the sidebar
    Then the user should be redirected to the Log Symptoms page

  Scenario: Navigate to Lab Results from sidebar
    When the user clicks "Lab Results" in the sidebar
    Then the user should be redirected to the Lab Results page

  Scenario: Navigate to History from sidebar
    When the user clicks "History" in the sidebar
    Then the user should be redirected to the History page

  Scenario: Navigate to Insights from sidebar
    When the user clicks "Insights" in the sidebar
    Then the user should be redirected to the Insights page

  Scenario: Log out from Medications page
    When the user clicks "Log Out" in the sidebar
    Then the user should be redirected to the Login page