create table clase_vehiculo(
    cla_id integer primary key autoincrement,
    cla_nombre text
);
insert into clase_vehiculo (cla_nombre) values  ('BUS'),
                                                ('MICROBUS'),
                                                ('BUSETA');

create table categoria(
    cat_id integer primary key autoincrement,
    cat_categoria text not null
);

insert into categoria values    (1,'A1'),
                                (2,'A2'),
                                (3,'B1'),
                                (4,'B2'),
                                (5,'B3'),
                                (6,'C1'),
                                (7,'C2'),
                                (8,'C3');

create table tipo_id(
    tip_id integer primary key autoincrement,
    tip_nombre text not null
);
insert into tipo_id (tip_nombre) values ('C.C.'),
                                        ('NIT'),
                                        ('T.I.');

create table departamento(
    dep_id tinyint primary key,
    dep_nombre text not null
);

create table ciudad(
    ciu_id integer primary key autoincrement,
    ciu_nombre text not null,
    dep_id tinyint,
    foreign key (dep_id) references departamento(dep_id)
);

insert into departamento(dep_nombre, dep_id) values ('Amazonas',1);
insert into ciudad (ciu_nombre,dep_id) values ('Leticia',1);
insert into departamento(dep_nombre, dep_id) values ('Antioquía',2);
insert into ciudad (ciu_nombre,dep_id) values ('Medellín',2);
insert into departamento(dep_nombre, dep_id) values ('Arauca',3);
insert into ciudad (ciu_nombre,dep_id) values ('Arauca',3);
insert into departamento(dep_nombre, dep_id) values ('Atlántico',4);
insert into ciudad (ciu_nombre,dep_id) values ('Barranquilla',4);
insert into departamento(dep_nombre, dep_id) values ('Bolívar',5);
insert into ciudad (ciu_nombre,dep_id) values ('Cartagena de Indias',5);
insert into departamento(dep_nombre, dep_id) values ('Boyacá',6);
insert into ciudad (ciu_nombre,dep_id) values ('Tunja',6);
insert into departamento(dep_nombre, dep_id) values ('Caldas',7);
insert into ciudad (ciu_nombre,dep_id) values ('Manizales',7);
insert into departamento(dep_nombre, dep_id) values ('Caquetá',8);
insert into ciudad (ciu_nombre,dep_id) values ('Florencia',8);
insert into departamento(dep_nombre, dep_id) values ('Casanare',9);
insert into ciudad (ciu_nombre,dep_id) values ('Yopal',9);
insert into departamento(dep_nombre, dep_id) values ('Cauca',10);
insert into ciudad (ciu_nombre,dep_id) values ('Popayán',10);
insert into departamento(dep_nombre, dep_id) values ('Cesar',11);
insert into ciudad (ciu_nombre,dep_id) values ('Valledupar',11);
insert into departamento(dep_nombre, dep_id) values ('Chocó',12);
insert into ciudad (ciu_nombre,dep_id) values ('Quibdó',12);
insert into departamento(dep_nombre, dep_id) values ('Córdoba',13);
insert into ciudad (ciu_nombre,dep_id) values ('Montería',13);
insert into departamento(dep_nombre, dep_id) values ('Cundinamarca',14);
insert into ciudad (ciu_nombre,dep_id) values ('Bogotá',14);
insert into departamento(dep_nombre, dep_id) values ('Guainía',15);
insert into ciudad (ciu_nombre,dep_id) values ('Inírida',15);
insert into departamento(dep_nombre, dep_id) values ('Guaviare',16);
insert into ciudad (ciu_nombre,dep_id) values ('San José del Guaviare',16);
insert into departamento(dep_nombre, dep_id) values ('Huila',17);
insert into ciudad (ciu_nombre,dep_id) values ('Neiva',17);
insert into departamento(dep_nombre, dep_id) values ('La Guajira',18);
insert into ciudad (ciu_nombre,dep_id) values ('Riohacha',18);
insert into departamento(dep_nombre, dep_id) values ('Magdalena',19);
insert into ciudad (ciu_nombre,dep_id) values ('Santa Marta',19);
insert into departamento(dep_nombre, dep_id) values ('Meta',20);
insert into ciudad (ciu_nombre,dep_id) values ('Villavicencio',20);
insert into ciudad (ciu_nombre, dep_id) values ('Acacias',20); 
insert into departamento(dep_nombre, dep_id) values ('Nariño',21);
insert into ciudad (ciu_nombre,dep_id) values ('San Juan de Pasto',21);
insert into departamento(dep_nombre, dep_id) values ('Norte de Santander',22);
insert into ciudad (ciu_nombre,dep_id) values ('San José de Cúcuta',22);
insert into departamento(dep_nombre, dep_id) values ('Putumayo',23);
insert into ciudad (ciu_nombre,dep_id) values ('Mocoa',23);
insert into departamento(dep_nombre, dep_id) values ('Quindío',24);
insert into ciudad (ciu_nombre,dep_id) values ('Armenia',24);
insert into departamento(dep_nombre, dep_id) values ('Risaralda',25);
insert into ciudad (ciu_nombre,dep_id) values ('Pereira',25);
insert into departamento(dep_nombre, dep_id) values ('San Andrés y Providencia',26);
insert into ciudad (ciu_nombre,dep_id) values ('San Andrés',26);
insert into departamento(dep_nombre, dep_id) values ('Santander',27);
insert into ciudad (ciu_nombre,dep_id) values ('Bucaramanga',27);
insert into departamento(dep_nombre, dep_id) values ('Sucre',28);
insert into ciudad (ciu_nombre,dep_id) values ('Sincelejo',28);
insert into departamento(dep_nombre, dep_id) values ('Tolima',29);
insert into ciudad (ciu_nombre,dep_id) values ('Ibagué',29);
insert into departamento(dep_nombre, dep_id) values ('Valle del Cauca',30);
insert into ciudad (ciu_nombre,dep_id) values ('Cali',30);
insert into departamento(dep_nombre, dep_id) values ('Vaupés',31);
insert into ciudad (ciu_nombre,dep_id) values ('Mitú',31);
insert into departamento(dep_nombre, dep_id) values ('Vichada',32);
insert into ciudad (ciu_nombre,dep_id) values ('Puerto Carreño',32);


create table persona(
    per_id text primary key,
    tip_id integer,
    per_nombre text not null,
    per_celular text not null,
    ciu_id integer not null,
    per_direccion text not null,
    per_correo text,
    foreign key (tip_id) references tipo_id(tip_id),
    foreign key (ciu_id) references ciudad(ciu_id)
);

insert into persona values  ('1068972260',1,'Juan David Beltran Orjuela','3112342881',21,'Carrera 29B # 17 - 21', 'indefinido'),
                            ('80391277',1,'Javier Orlando Beltran Rodriguez','3144341010',21,'Carrera 29B # 17 - 21', 'indefinido'),
                            ('20384716',1,'Claudia Irene Orjuela Rincon','3132850641',21,'Carrera 29B # 17 - 21', 'indefinido'),
                            ('830085825',2,'Lineas Javarturs del Llano S.A.S.','3132526264',21,'Carrera 34A # 10B - 6', 'lineasjavarturssas@gmail.com');


create table tipo_empleado(
    tiem_id integer primary key autoincrement,
    tiem_nombre text not null
);

insert into tipo_empleado (tiem_nombre) values  ('Operativo'),
                                                ('Administrativo');

create table eps(
    eps_id integer primary key autoincrement,
    eps_nombre text not null
);

insert into eps (eps_nombre) values ('NUEVA EPS'),
                                    ('SALUD TOTAL EPS S.A.'),
                                    ('EPS SANITAS'),
                                    ('FAMISANAR'),
                                    ('ADRES');

create table pension(
    pen_id integer primary key autoincrement,
    pen_nombre text not null
);
insert into pension (pen_nombre) values ('Colpensiones');

create table cesantias(
    ces_id integer primary key autoincrement,
    ces_nombre text not null
);
insert into cesantias (ces_nombre) values ('Proteccion');

create table banco(
    ban_id integer primary key autoincrement,
    ban_nombre text not null
);

insert into banco(ban_nombre) values    ('BANCO DAVIVIENDA'),
                                        ('BANCOLOMBIA'),
                                        ('NEQUI'),
                                        ('DAVIPLATA'),
                                        ('RAPYPAY'),
                                        ('BANCO BBVA'),
                                        ('BANCO POPULAR');

create table empleado(
    per_id text primary key,
    tiem_id integer not null,
    eps_id integer not null,
    pen_id tinyint null,
    ces_id tinyint null,
    emp_fecha_ingreso date not null,
    emp_fecha_retiro date null,
    emp_activo boolean not null default 1,
    emp_salario real not null,

    foreign key (per_id) references persona(per_id),
    foreign key (tiem_id) references tipo_empleado(tiem_id),
    foreign key (eps_id) references eps(eps_id),
    foreign key (pen_id) references pension(pen_id),
    foreign key (ces_id) references cesantias(ces_id)
);

insert into empleado values ('1068972260',2,1,1,1,'2023-10-1',null,1,1300000,1,'2112342881');

create table cuenta_bancaria(
    per_id text not null,
    ban_id integer not null,
    cue_numero = text not null,

    primary key (per_id, ban_id, cue_numero),
    foreign key (per_id) references persona (per_id),
    foreign key (ban_id) references banco (ban_id)
);

create table tipo_novedad(
    tin_id integer primary key autoincrement,
    tin_nombre text not null,
    tin_nombre2 text null
);

create table novedad(
   per_id text,
   tin_id tinyint,
   nov_dias tinyint not null,
   nov_estado boolean not null default 0,
   nov_fecha_inicial date,
   nov_fecha_final date,
   primary key(per_id,tin_id,nov_fecha_inicial,nov_fecha_final),
   foreign key(per_id) references empleado (per_id),
   foreign key(tin_id) references tipo_novedad (tin_id)
);

create table servicio(
    ser_id integer primary key autoincrement,
    ser_nombre text not null
);

insert into servicio (ser_nombre) values    ('PARTICULAR'), 
                                            ('PÚBLICO');

create table vehiculo(
    veh_placa text primary key,
    cla_id integer not null,
    veh_modelo integer not null,
    veh_marca text not null,
    veh_linea text not null,
    veh_cilindrada integer not null,
    veh_color text not null,
    ser_id integer not null,
    veh_combustible text not null,
    veh_tipo_carroceria text not null,
    veh_numero_motor text not null,
    veh_numero_chasis text not null,
    veh_cantidad integer not null,
    veh_propietario text not null,
    veh_parque_automotor boolean not null,

    foreign key (veh_propietario) references persona (per_id),
    foreign key (cla_id) references clase_vehiculo (cla_id),
    foreign key (ser_id) references servicio(ser_id)
);

create table vehiculo_has_conductor(
    veh_placa text,
    per_id text,

    primary key (veh_placa, per_id),
    foreign key (veh_placa) references vehiculo(veh_placa),
    foreign key (per_id) references persona (per_id)
);

create table documento(
    veh_placa text primary key,
    doc_interno integer not null unique,
    doc_fecha_soat date not null,
    doc_fecha_rtm date not null,
    doc_fecha_rcc date not null,
    doc_fecha_rce date not null,
    doc_top integer not null unique,
    doc_fecha_top date not null,

    foreign key (veh_placa) references vehiculo (veh_placa)
);

create table licencia(
    per_id text primary key,
    cat_id integer,
    lic_fecha date,

    foreign key (per_id) references persona(per_id)
    foreign key (cat_id) references categoria(cat_id)
);
insert into licencia values ('1068972260',6,'2023-12-2');


create table contratante(
    con_contratante text primary key,
    con_responsable text not null,

    foreign key(con_contratante) references persona(per_id),
    foreign key(con_responsable) references persona(per_id)
);

create table contrato_mensual(
    con_id integer primary key not null,
    con_contratante text not null,

    foreign key(con_contratante) references contratante(con_contratante)
);


create table contrato_ocasional(
    con_id integer primary key,
    con_contratante text not null,
    con_fecha_inicial date not null,
    con_fecha_final date not null,
    con_origen integer not null,
    con_destino integer not null,
    con_valor decimal not null,
    tc_id integer not null,
    
    foreign key(con_contratante) references contratante(con_contratante)
    foreign key(con_origen) references ciudad(ciu_id),
    foreign key(con_destino) references ciudad(ciu_id),
    foreign key(tc_id) references tipo_contrato(tc_id)

);

-- tabla para los vehiculos externos
-- o que nohacen parte del parque automotor de
-- la empresa

drop table vehiculos_externos;
create table vehiculo_externo(
    veh_placa text primary key,
    per_id text not null,
    
    foreign key(per_id) references persona(per_id)
);


create table extracto_mensual(
    veh_placa text not null,
    ext_consecutivo integer not null,
    con_id integer not null,
    ext_fecha_inicial date not null,
    ext_fecha_final date not null,
    ext_origen integer not null,
    ext_destino integer not null,

    unique(veh_placa, ext_consecutivo),
    primary key(veh_placa, ext_consecutivo),
    foreign key(con_id) references contrato_mensual(con_id),
    foreign key(veh_placa) references vehiculo(veh_placa),
    foreign key(ext_origen) references ciudad(ciu_id),
    foreign key(ext_destino) references ciudad(ciu_id)
    );

create table extracto_ocasional(
    veh_placa text not null,
    ext_consecutivo integer not null,
    con_id integer not null,
    
    primary key(veh_placa, ext_consecutivo),
    foreign key(veh_placa) references vehiculo(veh_placa),
    foreign key(con_id) references contrato_ocasional(con_id)
    
);

-- Esta tabla es para definir el tipo
-- de contrato del personal que se va a cargar
create table tipo_contrato(

    tc_id integer primary key,
    tc_nombre text not null
);

insert into tipo_contrato values (1, 'ESTUDIANTIL');
insert into tipo_contrato values (2, 'PARTICULAR');
insert into tipo_contrato values (3, 'EMPRESARIAL');
insert into tipo_contrato values (4, 'PERSONALIZADO');

-- Esta tabla es auxiliar para tener el ultimo numero consecutivo
-- un extracto mensual
create table consecutivo_extracto_mensual(
    con_placa text not null primary key,
    con_numero integer not null
);

-- Esta tabla es auxiliar, y sirve para registrar el numero consecutivo
-- de un vehiculo
create table consecutivo_extracto_ocasional(
    con_placa text primary key,
    con_numero integer not null);



-- Seccion de las vistas de las diferentes tablas
create view vw_vehiculo as 
    select veh_placa, cla_nombre, veh_modelo, 
    veh_marca, veh_linea, veh_cilindrada, veh_color,ser_nombre, 
    veh_combustible, veh_tipo_carroceria, veh_numero_motor, 
    veh_numero_chasis,veh_cantidad,tip_nombre,per_id, per_nombre 
    from vehiculo join persona on per_id = veh_propietario natural join clase_vehiculo natural join tipo_id natural join servicio
        order by veh_placa asc;

create view vw_vehiculo_sin_documento as
    select * 
    from vw_vehiculo 
    where veh_placa not in (select veh_placa from documento);

create view vw_persona as 
    select per_id, tip_nombre,per_nombre, per_celular,ciu_nombre,dep_nombre,per_direccion, per_correo
    from persona natural join tipo_id natural join ciudad natural join departamento order by per_nombre asc;

create view vw_persona_natural as
    select per_id, tip_nombre,per_nombre, per_celular,ciu_nombre,dep_nombre,per_direccion,per_correo
    from persona natural join tipo_id natural join ciudad natural join departamento
    where tip_id = 1 or tip_id = 3;

create view vw_novedad as 
    select per_id, per_nombre,tin_nombre, tin_nombre2, nov_dias, nov_estado, nov_fecha_inicial, nov_fecha_final 
    from empleado natural join persona natural join novedad natural join tipo_novedad;

create view vw_vehiculo_has_conductor as 
    select veh_placa, tip_nombre, per_id,per_nombre,cat_categoria,lic_fecha 
    from vehiculo_has_conductor natural join persona natural join licencia natural join tipo_id natural join categoria 
    where cat_id in (6,7,8);

create view vw_licencia as
    select per_id, per_nombre,cat_categoria, lic_fecha 
    from licencia natural join categoria natural join persona;

create view vw_contratante as
select con_contratante,
    tip_nombre as con_tipo_id,
    per_nombre as con_nombre,
    con_responsable,
    res_nombre,
    res_celular,
    res_direccion
        from (contratante 
            join persona on (per_id = con_contratante))
            natural join tipo_id natural join 
                (select con_responsable, 
                per_nombre as res_nombre, 
                per_celular as res_celular, 
                per_direccion as res_direccion
                    from contratante 
                        join persona on (per_id = con_responsable) group by con_responsable);

create view vw_contrato_mensual as
    select con_id,
    con_tipo_id,
    con_contratante,
    con_nombre,
    con_responsable,
    res_nombre,
    res_celular,
    res_direccion,
    tc_nombre
    from contrato_mensual 
        natural join vw_contratante 
        natural join tipo_contrato;

create view vw_extracto_mensual as
    select veh_placa, ext_consecutivo, con_id, con_tipo_id, 
    con_contratante, con_nombre, 
    ext_fecha_inicial, ext_fecha_final, 
    ciu_nombre as ciudad_origen, 
    dep_nombre as departamento_origen, 
    ciudad_destino, departamento_destino
        from extracto_mensual 
        join ciudad on (ext_origen = ciu_id) 
        natural join departamento 
        natural join contrato_mensual 
        natural join vw_contratante 
        join (select ciu_id as ciudad_destino_id, ciu_nombre as ciudad_destino, dep_nombre as departamento_destino 
            from ciudad 
            natural join departamento) on (ciudad_destino_id = ext_destino);

create view vw_vehiculo_extracto as
    select veh_placa, veh_modelo, veh_marca, cla_nombre, doc_interno, doc_top  
        from vehiculo 
        natural join documento 
        natural join clase_vehiculo;

create view vw_no_contratante as
    select * from vw_persona 
        where per_id 
            not in(select con_contratante from contratante);

create view vw_contrato_ocasional as
select con_id,
    con_contratante,
    con_tipo_id,
    con_nombre,
    con_fecha_inicial,
    con_fecha_final,
    con_ciu_origen,
    con_dep_origen,
    con_ciu_destino,
    con_dep_destino,
    con_valor,
    tc_nombre
    from contrato_ocasional 
        natural join vw_contratante
        join (select ciu_id, ciu_nombre as con_ciu_origen, dep_nombre as con_dep_origen from ciudad natural join departamento) 
            on (ciu_id = con_origen)
        join (select ciu_id as ciudad_id, ciu_nombre as con_ciu_destino, dep_nombre as con_dep_destino from ciudad natural join departamento) 
            on (ciudad_id = con_destino)
        natural join tipo_contrato
        order by con_id desc;


create view vw_extracto_ocasional as
    select veh_placa, ext_consecutivo, con_id, 
    con_tipo_id, con_contratante, con_nombre, 
    con_fecha_inicial, con_fecha_final, con_ciu_origen, 
    con_dep_origen, con_ciu_destino, con_dep_destino, tc_nombre
        from extracto_ocasional natural join vw_contrato_ocasional
        order by con_id desc;

CREATE VIEW VW_VEHICULO_EXTERNO AS
SELECT VEH_PLACA, PER_ID, PER_NOMBRE 
    FROM VEHICULO_EXTERNO NATURAL JOIN PERSONA;