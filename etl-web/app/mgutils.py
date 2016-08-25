import json
from datetime import date, datetime
from .main.dao import EtlJob
import socket


def sendSocketCmd(cmd):
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.connect(('20.13.0.136', 6346))
    header = sock.recv(1024) + sock.recv(1024)
    print 'header--->\n' + header
    sock.send(cmd)
    cmdrst = sock.recv(100)
    print 'cmdrst--->\n' + cmdrst
    if cmdrst.find('OK') >= 0:
        rst = sock.recv(1024)
        line = None
        rst1 = ''
        for s1 in rst:
            rst1 = rst1 + s1
            if s1 == '\r' and line is None:
                line = rst1
                rst1 = ''

        for i in range(int(line)):
            s1 = sock.recv(1024)
            if s1 == '\n':
                pass
            else:
                rst1 += s1
    else:
        rst1 = cmdrst
    # AML 20160229 aml_monitor_cust0001.pl.1439.log
    sock.close()
    return rst1


class MgEncoder(json.JSONEncoder):
    def default(self, obj):
        # if isinstance(obj, datetime.datetime):
        #     return int(mktime(obj.timetuple()))
        if isinstance(obj, datetime):
            return obj.strftime('%Y-%m-%d %H:%M:%S')
        elif isinstance(obj, date):
            return obj.strftime('%Y-%m-%d')
        elif isinstance(obj, TreeNode):
            return obj.toDict()
        elif isinstance(obj, EtlJob):
            return obj.as_dict()
        else:
            return json.JSONEncoder.default(self, obj)


class TreeNode():
    def __init__(self, name, parent, status):
        self.name = name
        self.parent = parent
        self.value = 10
        if status == 'Done':
            self.level = "green"
            self.type = "green"
        else:
            self.level = "red"
            self.type = "red"
        self.cat = "suit"
        self.children = []

    def add(self, treeNode):
        isadd = 1
        if len(self.children) > 0:
            for c in self.children:
                if c.name == treeNode.name:
                    isadd = 0
            if isadd == 1:
                self.children.append(treeNode)
        else:
            self.children.append(treeNode)

    def toDict(self):
        return {"name": self.name, "parent": self.parent,
                "value": 10, "type": "black", "level": "level",
                "cat": "suit", "children": self.children}

    def __repr__(self):
        return '%s@%s' % (self.name, self.parent)



# print json.dumps(dataMap, cls=MyEncoder)
