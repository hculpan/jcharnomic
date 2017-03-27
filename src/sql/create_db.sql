use charnomic;

DELIMITER $$

drop procedure if exists create_db;

create procedure create_db()
begin
	drop table if exists judgments;
    drop table if exists rules;
    drop table if exists proposals;
    drop table if exists players;

    create table players (
        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        password VARCHAR(20),
        firstname VARCHAR(30),
        lastname VARCHAR(50),
        joined DATETIME DEFAULT CURRENT_TIMESTAMP,
        turn BOOLEAN DEFAULT 0,
        onleave BOOLEAN DEFAULT 0,
        active BOOLEAN DEFAULT 1,
        points INT DEFAULT 0,
        level INT DEFAULT 1,
        gold INT DEFAULT 0,
        vetoes INT DEFAULT 3,
        leftgame DATETIME NULL,
        monitor BOOLEAN DEFAULT 0
    );

    create table proposals (
        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        proposal TEXT,
        name VARCHAR(255),
        num INT,
        proposedby INT,
        proposeddate DATETIME,
        voteopened DATETIME,
        voteclosed DATETIME,
        status varchar(10),

        FOREIGN KEY (proposedby)
              REFERENCES players(id)
              ON DELETE CASCADE
    );

    create table rules (
        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        rule TEXT,
        name VARCHAR(255),
        num INT,
        proposalid INT,

        FOREIGN KEY (proposalid)
              REFERENCES proposals(id)
              ON DELETE CASCADE
    );
    
    create table judgments (
        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        request TEXT,
        judgment TEXT,
        requestedby INT,
        requesteddate DATETIME,
        judgedby INT,
        judgeddate DATETIME,
        overruled BOOLEAN DEFAULT 0,

        FOREIGN KEY (requestedby)
              REFERENCES players(id)
              ON DELETE CASCADE,
              
        FOREIGN KEY (judgedby)
              REFERENCES players(id)
              ON DELETE CASCADE
    );

end $$

delimiter ;

-- Execute the procedure
call create_db();

-- Drop the procedure
drop procedure create_db;
