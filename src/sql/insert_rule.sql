use charnomic;

DELIMITER $$

drop procedure if exists insert_rule;

create procedure insert_rule()
begin
  declare propNum int default 22;

  insert into rules (name, num, rule, proposalid)
  values ((select name from proposals where num = propNum),
          propNum,
          (select proposal from proposals where num = propNum),
          (select id from proposals where num = propNum)
    );

end $$

delimiter ;

-- Execute the procedure
call insert_rule();

-- Drop the procedure
drop procedure insert_rule;
