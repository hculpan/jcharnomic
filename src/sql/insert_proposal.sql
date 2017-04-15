use charnomic;

DELIMITER $$

drop procedure if exists insert_proposal;

create procedure insert_proposal()
begin
    insert into proposals (name, num, proposal, proposedby, proposeddate, status)
    values ('Night Shift', 23,
     '<p>A new rule will be added that says:</p>
              <p>The game will track turns using the cycle of Day and Night.  On the turn this rule is enacted, it will
              be Day and will alternate between the two cycles at the beginning of each subsequent turn.</p>',
      (select id from players where lastname = 'Thomason'),
      STR_TO_DATE('04/12/2017 2:35:00 PM', '%c/%e/%Y %r'),
      'voting');

end $$

delimiter ;

-- Execute the procedure
call insert_proposal();

-- Drop the procedure
drop procedure insert_proposal;
