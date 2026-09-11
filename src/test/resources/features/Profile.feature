@profile
Feature: Patient Profile Management

  Background:
    Given the user navigates to the login page
    When the user enters a valid email "immunotrack123@gmail.com"
    And the user enters a valid password "Immunotrack@123"
    And the user clicks the Log In button
    Then the user should see the dashboard page or a login error if credentials are mock
    And the user is on the Profile page

  Scenario: View profile page sections
    Then the user should see the My Profile header and description
    And the user should see the account name, condition tag, and age
    And the user should see the Contact Information section with email, phone number, birthday, and gender
    And the user should see the Medical Information section with assigned clinician and practice name
    And the user should see the Settings and Privacy section

  Scenario: Toggle medication reminders
    When the user toggles the Medication Reminders switch
    Then the Medication Reminders status should update accordingly

  Scenario: Toggle push notifications
    When the user toggles the Push Notifications switch
    Then the Push Notifications status should update accordingly

  Scenario: Update phone number and save changes
    When the user updates the phone number to "9876543210"
    And the user clicks the Save Profile Changes button
    Then a profile update confirmation should be displayed

  Scenario: Verify remote monitoring and privacy notice statuses, then return to dashboard
   Then the Remote Monitoring section should show "Left Program" status
    And the Notice of Privacy Practices should show "Acknowledged" status
    When the patient navigates back to the dashboard using Back to Home

  Scenario: Initiate change password flow
    When the user clicks on "Change Password"
    Then the change password flow should be initiated
    When the user closes the Change Password dialog

  Scenario: View Notice of Privacy Practices page
    When the user clicks on "Notice of Privacy Practices"
    Then the user should be navigated to the Notice of Privacy Practices page
    And the current notice version and effective date should be displayed
    And the acknowledgement status should show "Acknowledged"

  Scenario: Download a copy of the privacy notice
    When the user clicks on "Notice of Privacy Practices"
    And the user clicks the Download a copy button on the privacy notice
    Then the download action should be triggered

  Scenario: View previous versions on NPP page
    When the user clicks on "Notice of Privacy Practices"
    Then the Previous versions section should be displayed
    When the user navigates back to the profile page from the privacy notice

  Scenario: View full privacy notice document
    When the user clicks on "Notice of Privacy Practices"
    And the user clicks on the Read full Privacy Notice link
    Then the full privacy notice document should be displayed
    And the Download PDF button should be displayed on the document viewer
    When the user closes the full privacy notice document

  Scenario: View Cookie Policy page
    When the user clicks on "Cookie Policy"
    Then the user should be navigated to the Cookie Policy page
    And the What Are Cookies section should be displayed
    And the Cookies We Use section should be displayed
    When the user clicks back from the Cookie Policy page