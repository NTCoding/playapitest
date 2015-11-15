use acceptancetests;

-- Test notifications
insert into notifications (id, room, username, message, timestamp)
values
    (UUID(), "test room1", "user 1", "This room is so amazing", "2015-01-01 00:00:00"),
    (UUID(), "test room2", "user 2", "This room is so amazing", "2015-01-01 00:00:00"),
    (UUID(), "test room3", "user 3", "This room is so amazing", "2015-01-01 00:00:00"),
    (UUID(), "test room4", "user 4", "This room is so amazing", "2015-01-01 00:00:00"),
    (UUID(), "test room5", "user 5", "This room is so amazing", "2015-01-01 00:00:00"),
    (UUID(), "test room6", "user 6", "This room is so amazing", "2015-01-01 00:00:00"),
    (UUID(), "test room7", "user 7", "This room is so amazing", "2015-01-01 00:00:00"),
    (UUID(), "test room8", "user 8", "This room is so amazing", "2015-01-01 00:00:00"),
    (UUID(), "test room9", "user 9", "This room is so amazing", "2015-01-01 00:00:00"),
    (UUID(), "test room10", "user 10", "This room is so amazing", "2015-01-01 00:00:00");