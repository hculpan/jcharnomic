use charnomic;

DELIMITER $$

drop procedure if exists insert_data;

create procedure insert_data()
begin
    delete from judgments;
    delete from rules;
    delete from votes;
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
        proposeddate, voteopened, voteclosed, votesinfavor, votesagainst, votesabstained, votesveto)
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
        '2017-02-25 17:00:00', '2017-02-27 20:00:00', '2017-03-28 08:00:00', 5, 0, 0, 1);
	
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Harry'), (select id from proposals where num = 12));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Chris'), (select id from proposals where num = 12));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Bill'), (select id from proposals where num = 12));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Mike'), (select id from proposals where num = 12));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Al'), (select id from proposals where num = 12));
    insert into votes (vote, voterid, proposalid)
		values ('Veto', (select id from players where firstname = 'Steve'), (select id from proposals where num = 12));

    insert into proposals (proposal, name, num, status, proposedby,
        proposeddate, voteopened, voteclosed, votesinfavor, votesagainst, votesabstained, votesveto)
      values ('A new rule shall be added that says:
        A player may take temporary leave of the game by notifying the other
        players that they will not be playing and the dates of their
        leave. During this period, they will automatically, voluntarily
        pass their turn pursuant to Rule 3 as soon as their turn commences and
        Abstain from all voting. The Player on Leave may not vote or make
        proposals while on Leave. The Player on Leave may rejoin the game
        early by notifying the other players and then resuming play.', 'Player on Leave',
        13, 'passed', (select id from players where lastname = 'Duignan'),
        '2017-02-28 12:41:00', '2017-03-02 08:47:00', '2017-03-05 20:30:00', 4, 0, 3, 0);

    insert into votes (vote, voterid, proposalid)
		values ('Abstain', (select id from players where firstname = 'Harry'), (select id from proposals where num = 13));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Chris'), (select id from proposals where num = 13));
    insert into votes (vote, voterid, proposalid)
		values ('Abstain', (select id from players where firstname = 'Bill'), (select id from proposals where num = 13));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Mike'), (select id from proposals where num = 13));
    insert into votes (vote, voterid, proposalid)
		values ('Abstain', (select id from players where firstname = 'Al'), (select id from proposals where num = 13));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Steve'), (select id from proposals where num = 13));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Paul'), (select id from proposals where num = 13));

    insert into proposals (proposal, name, num, status, proposedby,
        proposeddate, voteopened, voteclosed, votesinfavor, votesagainst, votesabstained, votesveto)
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
        '2017-03-07 09:12:00', '2017-03-08 06:45:00', '2017-03-08 12:09:00', 5, 0, 2, 0);

    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Harry'), (select id from proposals where num = 14));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Chris'), (select id from proposals where num = 14));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Bill'), (select id from proposals where num = 14));
    insert into votes (vote, voterid, proposalid)
		values ('Abstain', (select id from players where firstname = 'Mike'), (select id from proposals where num = 14));
    insert into votes (vote, voterid, proposalid)
		values ('Abstain', (select id from players where firstname = 'Al'), (select id from proposals where num = 14));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Steve'), (select id from proposals where num = 14));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Paul'), (select id from proposals where num = 14));

    insert into proposals (proposal, name, num, status, proposedby,
        proposeddate, voteopened, voteclosed, votesinfavor, votesagainst, votesabstained, votesveto)
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
        '2017-03-08 19:21:00', '2017-03-09 18:58:00', '2017-03-09 21:59:00', 2, 0, 2, 1);

    insert into votes (vote, voterid, proposalid)
		values ('Abstain', (select id from players where firstname = 'Harry'), (select id from proposals where num = 15));
    insert into votes (vote, voterid, proposalid)
		values ('Veto', (select id from players where firstname = 'Chris'), (select id from proposals where num = 15));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Al'), (select id from proposals where num = 15));
    insert into votes (vote, voterid, proposalid)
		values ('Abstain', (select id from players where firstname = 'Steve'), (select id from proposals where num = 15));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Paul'), (select id from proposals where num = 15));

    insert into proposals (proposal, name, num, status, proposedby,
        proposeddate, voteopened, voteclosed, votesinfavor, votesagainst, votesabstained, votesveto)
      values ('I propose a new rule be added that states:

        Each player is given a level starting at level 1.
        Each player will be given an initial account balance of 0 gold pieces.', null, 16, 'passed', (select id from players where lastname = 'Thomason'),
        '2017-03-09 19:01:00', '2017-03-14 11:38:00', '2017-03-15 21:00:00', 4, 0, 1, 0);

    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Harry'), (select id from proposals where num = 16));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Mike'), (select id from proposals where num = 16));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Al'), (select id from proposals where num = 16));
    insert into votes (vote, voterid, proposalid)
		values ('Abstain', (select id from players where firstname = 'Steve'), (select id from proposals where num = 16));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Paul'), (select id from proposals where num = 16));

    insert into proposals (proposal, name, num, status, proposedby,
        proposeddate, voteopened, voteclosed, votesinfavor, votesagainst, votesabstained, votesveto)
      values ('Players wishing to perform actions that require an election must formally and explicitly 
		call for a vote on that specific action. The time period following that call is referred to as 
        the ''Voting Period'', and the player initiating it is referred to as the ''Legislator''. A Voting 
        Period continues until any one of the following occurs: the measure is formally withdrawn by the 
        Legislator, all players not on leave have cast a vote, or any calendar rules governing the measure 
        are obtained. After this, the Voting Period for that measure ends. Votes cast before or after the 
        Voting Period of an action will be ignored and not considered for any purpose within the rules. A 
        player may change his vote within the Voting Period, but the Voting Period will not be extended solely 
        to permit time for players to change their vote. Actions that require a vote include, but may not be 
        limited to, the adoption of a proposal.', 'Clarifying Voting', 17, 'passed', (select id from players where lastname = 'Wiegand'),
        '2017-03-19 08:50:00', '2017-03-19 08:50:00', '2017-03-22 08:53:00', 4, 0, 1, 0);

    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Harry'), (select id from proposals where num = 17));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Mike'), (select id from proposals where num = 17));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Al'), (select id from proposals where num = 17));
    insert into votes (vote, voterid, proposalid)
		values ('Abstain', (select id from players where firstname = 'Steve'), (select id from proposals where num = 17));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Paul'), (select id from proposals where num = 17));

    insert into proposals (proposal, name, num, status, proposedby,
        proposeddate, voteopened, voteclosed, votesinfavor, votesagainst, votesabstained, votesveto)
      values ('<p>Rule 4 will be altered to read in its entirety:</p><p>Each player on their turn proposes one
      rule-change and has it voted on. Each new proposal will receive the next successive integer
      than the last proposal, whether or not it is adopted. The player whose turn it is, hereafter referred
      to as Legislator, shall receive 5 points if they call for a vote on a proposal within 48 hours of the
      start of their turn, regardless of the outcome of that vote. Any player (including the Legislator) who
      votes in favor or against the proposal, but does not abstain, shall receive 5 points only if that
      proposal is adopted. Players on Leave shall receive 5 points for every proposal that is accepted during
      their period of absence.</p>', 'Proposals and Scoring', 18, 'passed', (select id from players where lastname = 'Culpan'),
        '2017-03-22 08:57:00', '2017-03-24 20:20:00', '2017-03-25 01:14:00', 4, 0, 0, 0);

    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Harry'), (select id from proposals where num = 18));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Mike'), (select id from proposals where num = 18));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Al'), (select id from proposals where num = 18));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Paul'), (select id from proposals where num = 18));

    insert into proposals (proposal, name, num, status, proposedby,
        proposeddate, voteopened, voteclosed, votesinfavor, votesagainst, votesabstained, votesveto)
      values ('<p>I propose updating the text of what is currently Rule 6 as follows,
      and I call for a vote on this proposal:</p>
      <ol type="a">
        <li>A rule-change is adopted if and only if the proposal receives a majority (counting only
        Active Players) of votes in favor and no Vetoes.</li>
        <li>Any player may choose to abstain from the vote. If a player fails to vote within 3 days
        of the vote being called, their vote will be assumed to be an abstention. For the sake
        of clarity and explanation, votes may be any one of: yes, no, abstain (explicit),
        abstain (implied by non-voting), or veto.</li>
        <li>The voting must be completed within 7 days of the start of the proposing player''s turn
        or the proposal will be rejected. The proposing player may call for a vote earlier than
        that, and he or she determines the final form of the proposal to be voted on prior to
        any votes being cast.</li>
        An "Active Player" is any player who has not declared themselves on leave for the start
        of the Voting Period, and who has not involuntarily passed their most recent turn.</li>
        <li>As of the adoption of Proposal 19, each player is awarded 3 Vetoes.</li>
        <li>A player may acquire more Vetoes by paying X gold, where X is the number of Vetoes
        they have ever possessed.</li>
        <li>When a veto is used to defeat a proposal, the player''s total number of vetoes is
        reduced by one. The player cannot veto a proposal unless their total vetoes are greater
        than zero.</li>
      </ol>', 'Passing Rule Changes', 19, 'passed', (select id from players where lastname = 'Mele'),
        '2017-03-25 08:00:00', '2017-03-25 19:11:00', '2017-03-26 12:52:00', 4, 0, 0, 0);

    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Harry'), (select id from proposals where num = 19));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Mike'), (select id from proposals where num = 19));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Al'), (select id from proposals where num = 19));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Paul'), (select id from proposals where num = 19));

    insert into proposals (proposal, name, num, status, proposedby,
        proposeddate, voteopened, voteclosed, votesinfavor, votesagainst, votesabstained, votesveto)
      values ('<p>I propose that rule 16 be amended, adding bullet point #3 as follows:</p>
      <p>A player''s level will be calculated based on their current point total, increasing and
      decreasing according to the point total.  The following formula will be used to calculate
      the level, rounded down to the nearest integer: 1.725 * Math.sqrt(POINT_TOTAL) + 1</p>',
      null, 20, 'passed', (select id from players where lastname = 'Thomason'),
        '2017-03-25 08:00:00', '2017-03-25 19:11:00', '2017-03-26 12:52:00', 4, 0, 0, 0);

    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Harry'), (select id from proposals where num = 20));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Mike'), (select id from proposals where num = 20));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Al'), (select id from proposals where num = 20));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Chris'), (select id from proposals where num = 20));
    insert into votes (vote, voterid, proposalid)
		values ('Yes', (select id from players where firstname = 'Paul'), (select id from proposals where num = 20));

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
      values ('Players wishing to perform actions that require an election must formally and explicitly call
        for a vote on that specific action. The time period following that call is referred to as the ''Voting Period'',
        and the player initiating it is referred to as the ''Legislator’. A Voting Period continues until any one of the
        following occurs: the measure is formally withdrawn by the Legislator, all players not on leave have cast a vote,
        or any calendar rules governing the measure are obtained. After this, the Voting Period for that measure ends. Votes
        cast before or after the Voting Period of an action will be ignored and not considered for any purpose within the rules.
        A player may change his vote within the Voting Period, but the Voting Period will not be extended solely to permit
        time for players to change their vote. Actions that require a vote include, but may not be limited to, the adoption
        of a proposal.', 'Clarifying Voting', 17, (select id from proposals where num=17));

    insert into rules (rule, name, num, proposalid)
      values ('Each player on their turn proposes one rule-change and has it voted on. Each new proposal will receive
        the next successive integer than the last proposal, whether or not it is adopted. The player whose turn it is,
        hereafter referred to as Legislator, shall receive 5 points if they call for a vote on a proposal within 48 hours
        of the start of their turn, regardless of the outcome of that vote. Any player (including the Legislator) who
        votes in favor or against the proposal, but does not abstain, shall receive 5 points only if that proposal is
        adopted. Players on Leave shall receive 5 points for every proposal that is accepted during their period of
        absence.', 'Proposals and Scoring', 18, (select id from proposals where num=18));

    insert into rules (rule, name, num, proposalid)
      values ('<ol type="a">
          <li>A rule-change is adopted if and only if the proposal receives a majority (counting only Active Players) of
          votes in favor and no Vetoes.</li>
          <li>Any player may choose to abstain from the vote. If a player fails to vote within 3 days of the vote being
          called, their vote will be assumed to be an abstention. For the sake of clarity and explanation, votes may be
          any one of: yes, no, abstain (explicit), abstain (implied by non-voting), or veto.</li>
          <li>The voting must be completed within 7 days of the start of the proposing player''s turn or the proposal
          will be rejected. The proposing player may call for a vote earlier than that, and he or she determines the
          final form of the proposal to be voted on prior to any votes being cast.</li>
          <li>An "Active Player" is any player who has not declared themselves on leave for the start of the Voting
          Period, and who has not involuntarily passed their most recent turn.</li>
          <li>As of the adoption of Proposal 19, each player is awarded 3 Vetoes.</li>
          <li>A player may acquire more Vetoes by paying X gold, where X is the number of Vetoes they have ever
          possessed.</li>
          <li>When a veto is used to defeat a proposal, the player''s total number of vetoes is reduced by one. The
          player cannot veto a proposal unless their total vetoes are greater than zero.</li>
        </ol>', 'Passing Rule Changes', 19, (select id from proposals where num=19));

  insert into rules (rule, name, num, proposalid)
      values ('<ol type="a">
          <li>Each player is given a level starting at level 1.</li>
          <li>Each player will be given an initial account balance of 0 gold pieces.</li>
          <li>A player''s level will be calculated based on their current point total, increasing
            and decreasing according to the point total. The following formula will be used to
            calculate the level, rounded down to the nearest integer: 1.725 * Math.sqrt(POINT_TOTAL) + 1</li>
        </ol>', null, 20, (select id from proposals where num=20));

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
