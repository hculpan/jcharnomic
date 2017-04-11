use charnomic;

DELIMITER $$

drop procedure if exists insert_proposal;

create procedure insert_proposal()
begin
    insert into proposals (name, num, proposal, proposedby, proposeddate, status)
    values ('Addition of Players', 22,
     '<ol type="a">
      <li>Any player may request permission to invite one or more specifically named persons (each individually
       referred to as a "Potential Player") to join the game.</li>
      <li>Current players have 24 hours to respond with an affirmative, negative, abstention or veto for each Potential Player.</li>
      <li>Current players not responding to the proposal of a Potential Player within 24 hours are determined to </li>
       have abstained for that Potential Player.</li>
      <li>Permission is granted with a simple majority of "Active Players" (affirmative responders / total
       non-abstaining responders), and no Vetoes.</li>
      <li>Potential Players who are granted permission to join, and choose to join, start with statistics equal to the
       those of the player with the lowest non-negative point total, and assume their turn position pursuant to the
       rules governing turn order as of the conclusion of the turn active at the time of their joining.</li>
      <li>Players who have their turn involuntarily skipped twice consecutively are removed from the game by the
       Game Monitor, and can no longer participate in any game mechanic covered by the Rules.</li>
      </ol>',
      (select id from players where lastname = 'Mele'),
      STR_TO_DATE('04/07/2017 6:19:00 PM', '%c/%e/%Y %r'),
      'proposed');

end $$

delimiter ;

-- Execute the procedure
call insert_proposal();

-- Drop the procedure
drop procedure insert_proposal;
