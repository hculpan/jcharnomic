use charnomic;

DELIMITER $$

drop procedure if exists insert_data;

create procedure insert_data()
begin
    delete from judgments;
    delete from rules;
    delete from proposals;
    delete from players;
    
	-- Insert players
    insert into players (lastname, firstname, points, onleave) values ('Boivin', 'Bill', 10, 1);
    insert into players (lastname, firstname, points, monitor) values ('Culpan', 'Harry', 18, 1);
    insert into players (lastname, firstname, points) values ('Duignan', 'Chris', 17);
    insert into players (lastname, firstname, points, onleave) values ('Koehler', 'Steve', 20, 1);
    insert into players (lastname, firstname, points) values ('Mele', 'Al', 19);
    insert into players (lastname, firstname, points, turn) values ('Thomason', 'Mike', 19, 1);
    insert into players (lastname, firstname, points) values ('Wiegand', 'Paul', 19);

    commit;
    
    -- Insert proposals
    insert into proposals (proposal, name, num, status, proposedby,
        proposeddate, voteopened, voteclosed)
      values ('Be it proposed that the first line of rule 6 will be changed to:
        "A rule-change is adopted if and only if the proposal receives votes in
        favor from a majority of players in the game."

        A rule-change is adopted if and only if the proposal receives votes in
        favor from a majority of players in the game. Any player may choose to
        abstain from the vote. If a player fails to vote within 3 days of the
        vote being called, their vote will be assumed to be an abstention.
        The voting must be completed within 7 days of the proposal’s initial
        introduction or the proposal will be rejected. The proposing player may
        call for a vote earlier than that, and he or she determines the final
        form of the proposal to be voted on prior to any votes being cast.',
        null, 12, 'failed', (select id from players where lastname = 'Culpan'),
        '2017-02-25 17:00:00', '2017-02-27 20:00:00', '2017-03-28 08:00:00');

    insert into proposals (proposal, name, num, status, proposedby,
        proposeddate, voteopened, voteclosed)
      values ('A new rule shall be added that says:
        A player may take temporary leave of the game by notifying the other
        players that they will not be playing and the dates of their
        leave. During this period, they will automatically, voluntarily
        pass their turn pursuant to Rule 3 as soon as their turn commences and
        Abstain from all voting. The Player on Leave may not vote or make
        proposals while on Leave. The Player on Leave may rejoin the game
        early by notifying the other players and then resuming play.', 'Player on Leave',
        13, 'passed', (select id from players where lastname = 'Duignan'),
        '2017-02-28 12:41:00', '2017-03-02 08:47:00', '2017-03-05 20:30:00');

    insert into proposals (proposal, name, num, status, proposedby,
        proposeddate, voteopened, voteclosed)
      values ('A new Rule shall be enacted:

        No Proposal may be enacted which directly, specifically and negatively
        affect any player or players currently on Leave as that term is defined
        in Rule 13. A proposal "directly, specifically and negatively" affects
        a player or players currently on Leave if, in the opinion of the Judge,
        (a) the proposal is intended to or in application will effect the player
        such that he would definitely have voted "No" to the proposal had he
        not been on Leave, and (b) the proposal effects any players then on
        Leave more than those not on Leave.', 'The "No Targeting of Players
        on Leave" Act', 14, 'passed', (select id from players where lastname = 'Koehler'),
        '2017-03-07 09:12:00', '2017-03-08 06:45:00', '2017-03-08 12:09:00');

    insert into proposals (proposal, name, num, status, proposedby,
        proposeddate, voteopened, voteclosed)
      values ('I propose the following rule-change, being the enactment of rule 15 with the following content:

        Any player may request permission to invite one or more specifically named persons (each
        individually referred to as a "Potential Player") to join the game. Current players have
        24 hours to respond with an affirmative, negative, abstention or veto for each
        Potential Player. Current players not responding to the proposal of a Potential Player
        within 24 hours are determined to have abstained for that Potential Player.
        Permission is granted with a simple majority of "Active Responders" (affirmative responders / total
        non-abstaining responders), and no vetos. Each Player is allowed three lifetime vetos which
        may be used to forbid a new player from joining the game, regardless of the voting results
        from other players. Potential Players who are granted permission to join, and choose to join,
        start with a number of points equal to the lowest non-negative point total of active players,
        and assume their turn position pursuant to Rule 3 as of the conclusion of the turn active at the
        time of their joining. The inviting player ("Sponsor") earns 3 points per new player who joins
        under this rule and makes at least one proposal. The total maximum points over the course of
        the game which may be awarded to a Sponsor under this rule is 15. Players who have their turn
        involuntarily skipped twice consecutively are removed from the game by the Game Monitor.',
        null, 15, 'failed', (select id from players where lastname = 'Mele'),
        '2017-03-08 19:21:00', '2017-03-09 18:58:00', '2017-03-09 21:59:00');

    insert into proposals (proposal, name, num, status, proposedby,
        proposeddate, voteopened, voteclosed)
      values ('I propose a new rule be added that states:

        Each player is given a level starting at level 1.
        Each player will be given an initial account balance of 0 gold pieces.', null, 16, 'passed', (select id from players where lastname = 'Thomason'),
        '2017-03-09 19:01:00', '2017-03-14 11:38:00', '2017-03-15 21:00:00');

    commit;
    
    -- Insert rules
    insert into rules (rule, name, num)
      values ('All players must always abide by all the rules then in effect, in
        the form in which they are then in effect. The rules in the Initial Set
        are in effect when the game begins. The Initial Set consists of Rules
        1-11. Players start the game with zero points.', null, 1);

    insert into rules (rule, name, num)
      values ('A rule-change is the enactment, repeal, or amendment of a rule.', null, 2);

    insert into rules (rule, name, num)
      values ('Players shall alternate in alphabetical order by surname,
        taking one turn apiece. A player may voluntarily pass his or her turn,
        and any player who does not initiate their turn within 3 days of the
        turn starting shall have their turn involuntarily skipped and lose 10
        points.', null, 3);

    insert into rules (rule, name, num)
      values ('Each player on their turn proposes one rule-change and has
        it voted on. Each proposal will be given a number, starting with 12,
        and each proposal shall receive the next successive integer whether or
        not it is adopted. The player will receive a number of points equal to
        the number of the proposed rule-change multiplied by the fraction of
        votes in favor, rounding down.', null, 4);

    insert into rules (rule, name, num)
      values ('The winner is the first player to achieve 200 (positive) points.', null, 5);

    insert into rules (rule, name, num)
      values ('A rule-change is adopted if and only if the proposal receives a
        majority of votes in favor and no votes against. Any player may choose
        to abstain from the vote. If a player fails to vote within 3 days of the
        vote being called, their vote will be assumed to be an abstention. The
        voting must be completed within 7 days of the proposal’s initial
        introduction or the proposal will be rejected. The proposing player may
        call for a vote earlier than that, and he or she determines the final
        form of the proposal to be voted on prior to any votes being cast.', null, 6);

    insert into rules (rule, name, num)
      values ('An adopted rule-change takes full effect immediately at the
        completion of the vote that adopted it. New rules will be assigned the
        number of the proposal, and amended rules will have their number changed
        to match the proposal''s number.', null, 7);

    insert into rules (rule, name, num)
      values ('Each player always has exactly one vote.', null, 8);

    insert into rules (rule, name, num)
      values ('If two or more rules conflict with one another, then the rule
        with the lowest ordinal number takes precedence.', null, 9);

    insert into rules (rule, name, num)
      values ('If players disagree about the legality of a move or the
        interpretation or application of a rule, then the player preceding the
        one moving is to be the Judge and decide the question. Disagreement for
        the purposes of this rule may be created by the insistence of any
        player. This process is called invoking Judgment. The previous acting
        player becomes the Judge, and the Judge gives a decision. The Judge''s
        Judgment may be overruled only by a majority of favorable votes with
        no votes against by the other players taken before the next turn is begun.
        If a Judge''s Judgment is overruled, the player prior to the first Judge
        becomes a new Judge and gives a decision, and do as same as above until
        Judgement is not overruled. All Judgments, whether overruled or not,
        shall be recorded, but future judges are not bound by these decisions.', null, 10);

    insert into rules (rule, name, num)
      values ('The state of the game will be managed by a player called the
        Game Monitor, hereafter referred to as the GM. This individual will be
        responsible for preserving the current state of the Rules, maintaining
        records of any Judgments, the score of each player, and the order of
        turns. In addition, the GM will maintain any other game state information
        that may become necessary as the rules change. This information will
        be maintained in a location that all the players may access freely.
        The GM is Harry Culpan.', null, 11);

    insert into rules (rule, name, num, proposalid)
      values ('A player may take temporary leave of the game by notifying the
        other players that they will not be playing and the dates of their
        leave. During this period, they will automatically, voluntarily pass
        their turn pursuant to Rule 3 as soon as their turn commences and
        Abstain from all voting. The Player on Leave may not vote or make
        proposals while on Leave. The Player on Leave may rejoin the game
        early by notifying the other players and then resuming play.', 'Player on Leave', 13, 
        (select id from proposals where num=13));

    insert into rules (rule, name, num, proposalid)
      values ('No Proposal may be enacted which directly, specifically and
        negatively affect any player or players currently on Leave as that
        term is defined in Rule 13. A proposal "directly, specifically and
        negatively" affects a player or players currently on Leave if, in the
        opinion of the Judge, (a) the proposal is intended to or in application
        will effect the player such that he would definitely have voted "No" to
        the proposal had he not been on Leave, and (b) the proposal effects any
        players then on Leave more than those not on Leave.', 'The "No
        Targeting of Players on Leave" Act', 14, (select id from proposals where num=14));

    insert into rules (rule, name, num, proposalid)
      values ('<ol type="a">
          <li>Each player is given a level starting at level 1.</li>
          <li>Each player will be given an initial account balance of 0 gold pieces.</li>
        </ol>', null, 16, (select id from proposals where num=16));

    commit;
    
    insert into judgments(request, judgment, requestedby, requesteddate, judgedby, judgeddate)
	  values ('<p>I want to invoke Judgment on Harry''s Points. I don''t know if he has correctly 
      applied Rule 4: The player will receive a number of points equal to the number of the 
      proposed rule-change multiplied by the fraction of votes in favor, rounding down.</p>
	  <p>If I understand this right, then if the two other players had voted No, then he would 
      have gotten 7 points, right? Frankly, I am just confused by this verbiage and want to 
      discuss it a bit.</p>', '<p>Rule 4 says, "The player will receive a number of points equal to 
      the number of the proposed rule-change multiplied by the fraction of votes in favor, rounding 
      down." The intent of this rule is to use the ratio of the players voting in favor to the total 
      players in the game, regardless of whether they participated in the voting process for the 
      proposal.</p><p>In this case, 5 players voted in favor, 1 voted against, and 1 did not vote
      because the voting was closed by the player, but it would be assumed to be an abstention. So 
      that was the ratio of 5 in favor to 7 total, which is .7143. This is multiplied by the proposal 
      number, 12, for a total on 8.5, which rounded down is 8.</p><p>So I judge that 8 was the proper 
      number of points to be awarded.</p>', 
      (select id from players where lastname = 'Koehler'), STR_TO_DATE('02/28/2017 06:05:00 PM', '%c/%e/%Y %r'),
      (select id from players where lastname = 'Culpan'), STR_TO_DATE('02/28/2017 08:42:00 PM', '%c/%e/%Y %r'));
      
	commit;

end $$

delimiter ;

-- Execute the procedure
call insert_data();

-- Drop the procedure
drop procedure insert_data;
