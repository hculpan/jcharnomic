use charnomic;

DELIMITER $$

drop procedure if exists update_db;

create procedure update_db()
begin
    alter table players add email varchar(100);

    alter table players modify password varchar(65);

    alter table players add uuid char(36);

    update players
        set email='harry@culpan.org',
            password='Fd0YPm9DpymTbMRbn9gRs6mN+lFd4pHGZhSlxPV2ViH7ABokg5ptbQoDDUse8Byk',
            passwordexpired=0
        where lastname = 'Culpan';

    create table gamelog (
        id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        event VARCHAR(255),
        eventdate DATETIME DEFAULT CURRENT_TIMESTAMP
    );

end $$

delimiter ;

-- Execute the procedure
call update_db();

-- Drop the procedure
drop procedure update_db;
