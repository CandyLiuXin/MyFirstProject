# -*- coding: utf-8 -*-

from .. import Base, db_session, engine
from sqlalchemy import Column, Integer, String, Date, Time


class EtlSys(Base):
    __tablename__ = 'etl_sys'

    etl_system = Column(String, primary_key=True)
    description = Column(String)
    datakeepperiod = Column(Integer)
    logkeepperiod = Column(Integer)
    recordkeepperiod = Column(Integer)

    def __init__(self):
        print 'init sys'

    def __repr__(self):
        return "etl_system->%s" % (self.etl_system)

    def as_dict(self):
        return {c.name: getattr(self, c.name) for c in self.__table__.columns}


class EtlJob(Base):
    __tablename__ = 'etl_job'
    etl_system = Column(String, primary_key=True)
    etl_job = Column(String, primary_key=True)
    etl_server = Column(String)
    description = Column(String)
    frequency = Column(String)
    jobtype = Column(String)
    enable = Column(String)
    last_starttime = Column(Time)
    last_endtime = Column(Time)
    last_jobstatus = Column(String)
    last_txdate = Column(Date)
    last_filecnt = Column(String)
    last_cubestatus = Column(String)
    cubeflag = Column(String)
    checkflag = Column(String)
    autooff = Column(String)
    checkcalendar = Column(String)
    calendarbu = Column(String)
    runningscript = Column(String)
    jobsessionid = Column(String)
    expectedrecord = Column(String)
    checklaststatus = Column(String)
    timetrigger = Column(String)

    def __repr__(self):
        return "%s->%s->%s" % (self.etl_system, self.etl_job, self.description)

    def as_dict(self):
       return {c.name: getattr(self, c.name) for c in self.__table__.columns}


class EtlJobDependency(Base):
    __tablename__ = 'etl_job_dependency'
    etl_system = Column(String, primary_key=True)
    etl_job = Column(String, primary_key=True)
    dependency_system = Column(String, primary_key=True)
    dependency_job = Column(String, primary_key=True)
    description = Column(String)
    enable = Column(String)

    @staticmethod
    def queryDeps(jobid):
        curs = engine.execute("select a.etl_job,dependency_job,B.LAST_JOBSTATUS from (" +
            "select DISTINCT T.*,LEVEL levl from v_etl_job_dependency T " +
            "start with etl_job = :1 connect by etl_job = prior dependency_job order by level) a, ETL_JOB B"+
            " where levl<4 AND A.ETL_JOB = B.ETL_JOB", [jobid])
        return curs


class EtlJobStream(Base):
    __tablename__ = 'etl_job_stream'
    etl_system = Column(String, primary_key=True)
    etl_job = Column(String, primary_key=True)
    stream_system = Column(String, primary_key=True)
    stream_job = Column(String, primary_key=True)
    description = Column(String)
    enable = Column(String)


class EtlJobLog(Base):
    __tablename__ = 'etl_job_log'
    etl_system = Column(String, primary_key=True)
    etl_job = Column(String, primary_key=True)
    jobsessionid = Column(Integer, primary_key=True)
    scriptfile = Column(String, primary_key=True)
    txdate = Column(Date, primary_key=True)
    starttime = Column(Time)
    endtime = Column(Time)
    returncode = Column(Integer)
    seconds = Column(Integer)

    def as_dict(self):
       return {c.name: getattr(self, c.name) for c in self.__table__.columns}
