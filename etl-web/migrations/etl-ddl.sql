create table etl_sys
(
  etl_system       varchar(3) not null,
  description      varchar(50),
  datakeepperiod   int,
  logkeepperiod    int,
  recordkeepperiod int
);

CREATE TABLE etl_job
(
  etl_system      varchar(3) not null,
  etl_job         varchar(50) not null,
  etl_server      varchar(10),
  description     varchar(50),
  frequency       varchar(30),
  jobtype         varchar(1),
  enable          varchar(1),
  last_starttime  varchar(19),
  last_endtime    varchar(19),
  last_jobstatus  varchar(20),
  last_txdate     varchar(6),
  last_filecnt    int,
  last_cubestatus varchar(20),
  cubeflag        varchar(1),
  checkflag       varchar(1),
  autooff         varchar(1),
  checkcalendar   varchar(1),
  calendarbu      varchar(15),
  runningscript   varchar(50),
  jobsessionid    int,
  expectedrecord  int,
  checklaststatus varchar(1),
  timetrigger     varchar(1)
);

CREATE TABLE etl_job_stream
(
  etl_system    varchar(3) not null,
  etl_job       varchar(50) not null,
  stream_system varchar(3) not null,
  stream_job    varchar(50) not null,
  description   varchar(50),
  enable        varchar(1)
);

CREATE TABLE etl_job_dependency
(
  etl_system        varchar(3) not null,
  etl_job           varchar(50) not null,
  dependency_system varchar(3) not null,
  dependency_job    varchar(50) not null,
  description       varchar(50),
  enable            varchar(1)
);

CREATE TABLE etl_job_log
(
  etl_system   varchar(3) not null,
  etl_job      varchar(50) not null,
  jobsessionid int not null,
  scriptfile   varchar(60) not null,
  txdate       varchar(6) not null,
  starttime    varchar(19),
  endtime      varchar(19),
  returncode   int,
  seconds      int
);
