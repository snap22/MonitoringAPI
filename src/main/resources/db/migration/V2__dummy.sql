INSERT INTO user_ (id, email, username, access_token) VALUES
  (1, 'info@applifting.cz', 'Applifting', '93f39e2f-80de-4033-99ee-249d92736a25'),
  (2, 'batman@example.com', 'Batman', 'dcb20f8a-5657-4f1b-9f7f-ce65739b359e')
;


INSERT INTO endpoint (id, user_id, name, url, check_interval) VALUES
    ( 1, 1, 'google', 'https://www.google.sk', 5 ),
    ( 2, 1, 'movies', 'https://freetestapi.com/api/v1/movies', 10 ),
    ( 3, 2, 'dogs', 'https://freetestapi.com/api/v1/dogs?limit=5', 8 ),
    ( 4, 2, 'book', 'https://freetestapi.com/api/v1/books/1', 6 )
;