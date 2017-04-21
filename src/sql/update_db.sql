use charnomic;

DELIMITER $$

drop procedure if exists update_db;

create procedure update_db()
begin
    update players set active = 0, onleave = 0, leftgame = now() where lastname in ('Koehler', 'Boivin');

    commit;
end $$

delimiter ;

-- Execute the procedure
call update_db();

-- Drop the procedure
drop procedure update_db;
