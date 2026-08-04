Feature: ImmunoTrack Clinician Login

  Background:
    Given the user navigates to the login page

  Scenario: Successful login with valid credentials
    When the user enters a valid email "patient002@test.com"
    And the user enters a valid password "Testing@123"
    And the user clicks the Log In button
    Then the user should see the dashboard page or a login error if credentials are mock

# Scenario Outline: Login failure with invalid or missing credentials
#   When the user enters email "<email>" and password "<password>"
#   And the user clicks the Log In button
#   Then the user should see an error notification containing "<expected_error>"

#   Examples:
#     | email                      | password      | expected_error             |
#     |                            | password123   | Please enter your email address |
#     | test@example.com           |               | Please enter your password |
#     | invalidemailformat         | password123   | Please include an '@' in the email address |
#     | wrong.clinician@gmail.com  | WrongPassword | Validation failed. Please check your inputs. |

# Scenario: Navigation to Forgot Password page
#   When the user clicks the Forgot Password link
#   Then the user should see the Forgot Password recovery page
