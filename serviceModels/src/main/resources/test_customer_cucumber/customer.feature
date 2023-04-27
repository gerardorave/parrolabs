Feature: does Customer exists ? (finding by id)

  Scenario: Customer id int he response is null or 57ba5331-dee1-4803-86c3-0dd3511097bf
    Given Customer Id is "<id>" "<param>"
    When Searching for CustomerId
    Then Return "<value>"

  Examples:
    |                   id                   |   param  |                value                  |
    |  be38e115-c220-4050-829c-7d694b01437a  |    id    |                null                   |
    |  57ba5331-dee1-4803-86c3-0dd3511097bf  |    id    | 57ba5331-dee1-4803-86c3-0dd3511097bf  |
    |  57ba5331-dee1-4803-86c3-0dd3511097bf  |    name  |             Customer name             |
