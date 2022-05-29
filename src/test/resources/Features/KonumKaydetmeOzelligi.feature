Feature: Konum Kaydetme

  Scenario: Listeleme sayfasinda Yarin Kapinda alaninda gelen il bilgisi ile anasayfada secilen il bilgisinin
  ayni olmalidir
    Given Uygulamayi actim
    When Konum alanina tikladim
    And il sectim
    And ilce sectim
    And mahallle sectim
    And Kaydet butonuna tikladim
    Then Popupta "Konumuz Kaydedildi" yi gordum
    Given Tab bar uzerinden kategoriler tabına tikladim
    When Bir kategori sectim
    And Yarin Kapinda alanindaki il bilgisini kaydettim
    Then Anasayfada secilen il bilgisi ile Yarin Kapinda alanindaki il bilgisinin ayni oldugunu dogruladim