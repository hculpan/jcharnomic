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

    update players
        set email='wboivin@gmail.com',
        password='5my/nNfyiPXPskXq23UzFyaqZxdcUnxDf7w7/HpeGdE5581sMs5cuSImgvqrF+HM',
        passwordexpired=1
        where lastname = 'Boivin';

    update players
        set email='duignan_chris@yahoo.com',
        password='uP3alt0wrAu9G+iDOrEE6a4RZvol8ZVskmkdjrfWe/h4DUlaWvDrxFOwIDQ+tARb',
        passwordexpired=1
        where lastname = 'Duignan';

    update players
        set email='stvkoehler@gmail.com',
        password='DONwtcUmHD5SXaBqXyaD6a1l/mUAtrI2ug7NG4FIP8/tHNkKnSEPpgA6XfpEKNpN',
        passwordexpired=1
        where lastname = 'Koehler';

    update players
        set email='amele@carolina.rr.com',
        password='p9XGXVV+Q1RCYFEgwQ27TfwKzyBq2GLbsGVFxAYFZLldVXASMCU18uQcNMxHe3xY',
        passwordexpired=1
        where lastname = 'Mele';

    update players
        set email='michael.thomason68@gmail.com',
        password='AKySfl9HAhjbum8oVVSiNS1GHkdD+xSJuHSaEjnqEe226m8UoH308DKxngOzy6O5',
        passwordexpired=1
        where lastname = 'Thomason';

    update players
        set email='paul@tesseract.org',
        password='1ql3E2JqsChy33qvxo5L4Uvi2qsF6wBlTrdfky4ggsy3ERsVkKxkR6ZNm1Gt8xvK',
        passwordexpired=1
        where lastname = 'Wiegand';

end $$

delimiter ;

-- Execute the procedure
call update_db();

-- Drop the procedure
drop procedure update_db;
