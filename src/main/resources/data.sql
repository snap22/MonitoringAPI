INSERT INTO user_ (id, email, username, access_token)
SELECT 1, 'info@applifting.cz', 'Applifting', '93f39e2f-80de-4033-99ee-249d92736a25' WHERE NOT EXISTS (SELECT 1 FROM user_ WHERE id = 1);

INSERT INTO user_ (id, email, username, access_token)
SELECT 2, 'batman@example.com', 'Batman', 'dcb20f8a-5657-4f1b-9f7f-ce65739b359e' WHERE NOT EXISTS (SELECT 1 FROM user_ WHERE id = 2);
