
/**
 * Author:  sofia
 * Created: 30 sep. 2023
 */
create table usuarioss(
    id bigint not null auto_increment,
    login varchar(100) not null,
    clave varchar(300) not null,

    primary key(id)
);
