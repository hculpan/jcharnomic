use charnomic;

DELIMITER $$

drop procedure if exists update_proposal;

create procedure update_proposal()
begin
    declare propNum int default 23;

    update proposals
    set voteopened = STR_TO_DATE('04/12/2017 2:35:00 PM', '%c/%e/%Y %r'),
        voteclosed = STR_TO_DATE('04/10/2017 9:24:00 AM', '%c/%e/%Y %r'),
        votesinfavor = 4,
        votesagainst = 0,
        votesabstained = 0,
        votesveto = 0,
        status='passed'
    where num = propNum;

/*    insert into votes (vote, voterid, proposalid)
        values ('Yes',
                (select id from players where lastname = 'Mele'),
                (select id from proposals where num = propNum)
        );

    insert into votes (vote, voterid, proposalid)
    values ('Yes',
            (select id from players where lastname = 'Culpan'),
            (select id from proposals where num = propNum)
    );

    insert into votes (vote, voterid, proposalid)
    values ('Yes',
            (select id from players where lastname = 'Thomason'),
            (select id from proposals where num = propNum)
    );
*/

    insert into votes (vote, voterid, proposalid)
    values ('Yes',
            (select id from players where lastname = 'Wiegand'),
            (select id from proposals where num = propNum)
    );
/*
    insert into votes (vote, voterid, proposalid)
    values ('Yes',
            (select id from players where lastname = 'Duignan'),
            (select id from proposals where num = propNum)
    );
*/
end $$

delimiter ;

-- Execute the procedure
call update_proposal();

-- Drop the procedure
drop procedure update_proposal;
