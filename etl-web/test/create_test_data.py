#-*- coding: UTF8 -*-

import sqlite3
import os
from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String
from sqlalchemy.orm import sessionmaker

# os.environ['NLS_LANG'] = 'SIMPLIFIED_CHINESE_CHINA.UTF8'

root_dir = os.path.abspath('..')
conn = sqlite3.connect(root_dir + "\etl.db")
# engine = create_engine('sqlite:///'+root_dir + "\etl.db", echo=True)
engine = create_engine('oracle://amletl:aml@20.13.0.134:1521/orcl', echo=True)
Base = declarative_base()


class EtlSys(Base):
    __tablename__ = 'etl_sys'

    etl_system = Column(String, primary_key=True)
    description = Column(String)
    datakeepperiod = Column(Integer)
    logkeepperiod = Column(Integer)
    recordkeepperiod = Column(Integer)

    def __repr__(self):
        return "etl_system->%s" % (self.etl_system)


class EtlJob(Base):
    __tablename__ = 'etl_job'
    etl_system = Column(String, primary_key=True)
    etl_job = Column(String, primary_key=True)
    etl_server = Column(String)
    description = Column(String)
    frequency = Column(String)
    jobtype = Column(String)
    enable = Column(String)
    last_starttime = Column(String)
    last_endtime = Column(String)
    last_jobstatus = Column(String)
    last_txdate = Column(String)
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

class EtlJobDependency(Base):
    __tablename__ = 'etl_job_dependency'
    etl_system = Column(String, primary_key=True)
    etl_job = Column(String, primary_key=True)
    dependency_system = Column(String, primary_key=True)
    dependency_job = Column(String, primary_key=True)
    description = Column(String)
    enable = Column(String)


class EtlJobStream(Base):
    __tablename__ = 'etl_job_stream'
    etl_system = Column(String, primary_key=True)
    etl_job = Column(String, primary_key=True)
    stream_system = Column(String, primary_key=True)
    stream_job = Column(String, primary_key=True)
    description = Column(String)
    enable = Column(String)


def exec_sql(sql):
    cursor = conn.cursor()
    print sql
    cursor.execute(sql)
    conn.commit


def create_ddl():
    f = open(root_dir + "\migrations\etl-ddl.sql")
    sql = ''
    for line in f.readlines():
        sql = sql + line.strip()
        if line.strip().endswith(';'):
            exec_sql(sql[:-1])
            sql = ''


Session = sessionmaker(bind=engine)
session = Session()


def init_data():
    for i in range(10):
        etl_sys = EtlSys(etl_system='sys'+str(i), description='sys desc' + str(i))
        session.add(etl_sys)
        for j in range(10):
            etl_job = EtlJob(etl_system='sys'+str(i), etl_job='etl_job_'+str(i)+"_"+str(j))
            session.add(etl_job)
    session.commit()



def query():
    usr_info = session.query(EtlJob)
    print usr_info
    for usr in usr_info:
        print usr

if __name__ == "__main__":
    #create_ddl()
    #init_data()
    query()
