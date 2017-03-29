use charnomic;

DELIMITER $$

drop procedure if exists update_db;

create procedure update_db()
begin
    alter table players add email varchar(100);

    alter table players modify password varchar(65);

end $$

delimiter ;

-- Execute the procedure
call update_db();

-- Drop the procedure
drop procedure update_db;
