use charnomic;

DELIMITER $$

drop procedure if exists update_rule;

create procedure update_rule()
begin
    update rules
    set rule='<ol type="a">
        <li>Each player is given a level starting at level 1.</li>
        <li>Each player will be given an initial account balance of 0 gold pieces.</li>
        <li>A player''s level will be calculated based on their current point total,
        increasing and decreasing according to the point total. The following formula
        will be used to calculate the level, rounded down to the nearest integer:
        1.725 * Math.sqrt(POINT_TOTAL) + 1</li>
      </ol>',
        num=20,
        proposalid=(select id from proposals where num = 20)
    where num = 16;

end $$

delimiter ;

-- Execute the procedure
call update_rule();

-- Drop the procedure
drop procedure update_rule;
