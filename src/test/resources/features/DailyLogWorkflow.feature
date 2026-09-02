Feature: ImmunoTrack Daily Health Log Workflow

  @run
  Scenario Outline: Complete end-to-end daily log submission for multiple patients via sidebar navigation
    Given the user navigates to the login page
    When the user enters a valid email "<email>"
    And the user enters a valid password "<password>"
    And the user clicks the Log In button
    Then the user should see the dashboard page or a login error if credentials are mock
    When the patient clicks on "Log Symptoms" in the sidebar menu
    Then the patient should be redirected to the Daily Health Log page
    And the user should see the header, date, monitoring banner, and symptom prompt
    And the user should see the SNOT-22 Nose and Sinus section
    And the user should see the ACQ-6 Asthma Control section
    And the user should see the POEM Skin Symptoms section
    When the user selects score ratings for all symptom questions in SNOT-22, ACQ-6, and POEM
    Then the answer counts and section score badges should update correctly
    And the user enters optional daily log notes "Feeling good today"
    And the user submits the daily health log
    When the patient navigates back to the dashboard page
    And the patient refreshes the dashboard page to update scores
    Then the entered symptom scores should match correctly under Today's Symptoms
    And the overall risk score and severity cards should be verified under Today's Symptoms
    When the user logs out of the patient portal
    Then the user should be redirected to the login page

    Examples:
      | email                             | password        |
      | immunotrack123@gmail.com          | Immunotrack@123 |
      | immunotrack456@gmail.com          | Testing@123     |
      | patient002@test.com               | Testing@123     |
      | patient003@test.com               | Testing@123     |
      | patient004@test.com               | Testing@123     |
      | patient005@test.com               | Testing@123     |
      | patient006@test.com               | Testing@123     |
      | patient007@test.com               | Testing@123     |
      | patient020@gmail.com              | Immunotrack@123 |
      | patientaccount1@gmail.com         | Immunotrack@123 |
      | durairaj.subramaniyan@piquota.com | Immunotrack@123 |
      | jokesapart003@gmail.com           | Immunotrack@123 |
      | lalitha@gmail.com                 | Kar@12345$      |
      | marcus@gmail.com                  | Kar@12345$      |
      | Alex@gmail.com                    | Kar@12345$      |
      | lavanya01@gmail.com               | Kar@12345$      |
      | bbb@gmail.com                     | Kar@12345$      |
      | ccc@gmail.com                     | Kar@12345$      |
      | duraiim@gmail.com                 | Vikki@52524     |





