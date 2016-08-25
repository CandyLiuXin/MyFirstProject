# -*- coding: utf-8 -*-

from flask import render_template, request, jsonify, redirect
from . import main
from .dao import EtlJob, EtlSys, EtlJobDependency, EtlJobLog
import json
from ..mgutils import MgEncoder, TreeNode, sendSocketCmd


@main.route('/')
def index():
    return render_template('login.html')


@main.route('/login', methods=['POST'])
def login():
    print 'login'
    uname = request.form['i_username']
    uinfo = {"uname": uname}
    return render_template('index.html', uinfo=uinfo)


isExistsASV = False


def isExists(t1, t2):
    if t1.name == t2.parent:
        global isExistsASV
        if t2.name in ('ALL_01_ASV', 'ALL_01_TRANSACTION', 'ALL_01_AGREEMENT'):
            if isExistsASV:
                return True
            else:
                isExistsASV = True
        t1.add(t2)
        return True
    else:
        for c in t1.children:
            if isExists(c, t2):
                return


@main.route('/showloglist/<jobid>')
def showjoblist(jobid):
    loglists = EtlJobLog.query.filter_by(etl_job=jobid).all()
    return json.dumps({'logs':[log.as_dict() for log in loglists]}, cls=MgEncoder)


@main.route('/showlog/<logid>')
def showlog(logid):
    rst1 = sendSocketCmd('GETLOG ' + logid.replace('-', '') + "\r\n")
    return render_template('job-log-detail.html', detail=rst1.decode('gbk'))


@main.route('/showdep/<jobid>')
def showdep(jobid):
    rst = TreeNode(jobid, "null", 'Done')
    for row in EtlJobDependency.queryDeps(jobid):
        job1 = row[0]
        depjob1 = row[1]
        status = row[2]
        tn = TreeNode(depjob1, job1, status)
        isExists(rst, tn)
    return render_template('job-dep.html', deps=json.dumps(rst, cls=MgEncoder))


@main.route('/showjobs')
def showjobs():
    jobs = EtlJob.query.all()
    j = json.loads(json.dumps(jobs, encoding='gbk', cls=MgEncoder))
    return render_template('jobs.html', job=j)


@main.route('/refdata/<dtype>/<jobid>', methods=['GEt'])
def refdata(dtype, jobid):
    treeNode = {}
    tNodes = {'systems': [sys.etl_system for sys in EtlSys.query.all()]}
    if jobid == 'all':
        jobs = EtlJob.query.all()
        for job in jobs:
            if job.etl_system not in treeNode:
                treeNode[job.etl_system] = []
            treeNode[job.etl_system].append(
                {"etl_job": job.etl_job, "description": job.description}
            )
        tNodes['jobs'] = treeNode
        return json.dumps(tNodes, encoding='gbk', cls=MgEncoder)
    else:
        if dtype == 'job':
            job = EtlJob.query.filter_by(etl_job=jobid).first()
            return json.dumps(job.as_dict(), encoding='gbk', cls=MgEncoder)
        else:
            sys = EtlSys.query.filter_by(etl_system=jobid).first()
            return json.dumps(sys.as_dict(), encoding='gbk', cls=MgEncoder)
