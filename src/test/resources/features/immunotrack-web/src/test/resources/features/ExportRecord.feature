Feature: ImmunoTrack Export My Record

  Background:
    Given the user navigates to the login page
    When the user enters a valid email "pavithrasaravanakumar29@gmail.com"
    And the user enters a valid password "Pavithra@29#2006pavi"
    And the user clicks the Log In button
    Then the user should see the dashboard page or a login error if credentials are mock

  @profile @exportRecord
  Scenario: Verify user can export their full health record
    Given the user is on the Profile page
    When the user clicks on "Export My Record"
    Then the user should be navigated to the Export My Record page
    When the user enters the account password "Pavithra@29#2006pavi" for identity verification
    And the user confirms the password
    Then the identity should be verified successfully
    When the user selects the "Full Record" export scope
    And the user requests the export
    Then an export queued confirmation should be displayed

  @profile @exportRecord
  Scenario: Verify user can return to profile after export queued
    Given the user is on the Profile page
    When the user clicks on "Export My Record"
    And the user enters the account password "Pavithra@29#2006pavi" for identity verification
    And the user confirms the password
    And the user selects the "Full Record" export scope
    And the user requests the export
    Then an export queued confirmation should be displayed
    When the user clicks "Return to Profile"
    Then the user should be navigated to the profile page