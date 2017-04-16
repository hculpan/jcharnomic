use charnomic;

DELIMITER $$

drop procedure if exists update_db;

create procedure update_db()
begin

    drop table if exists sessions;

    alter table players drop uuid;

    create table game_state (
        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        turn int default 1,
        day boolean DEFAULT 1
    );

    insert into game_state (turn, day)
        values (5, 1);

    create table sessions (
        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        playerid INT,
        uuid char(36),
        info char(255),
        login_datetime datetime default now(),

        FOREIGN KEY (playerid)
        REFERENCES players(id)
            ON DELETE CASCADE

    );

    create unique index sessions_idx1 on sessions(uuid);

    insert into sessions (playerid, uuid, login_datetime)
        values (
            (
                select id, uuid, now()
                from players
                where uuid is not null
            )
        );
end $$

delimiter ;

-- Execute the procedure
call update_db();

-- Drop the procedure
drop procedure update_db;
