@imdb
Feature: imdb Test Feature

  Scenario: IMDb Senaryosu
    Given Imdb Sayfasına gidilir
    And 1929 Oscar sayfasına gidilir
    When Film bulunur "<filmName>"
    Then Film bilgileri ve resimleri dogrulanır "<filmName>"

    Examples:
      |filmName|
      |The Jazz Singer|
      |The Circus|
