def fn(self,name='world'):
	print 'Hello %s' % name

Hello = type('Hello',(object,),dict(hello=fn))
x = Hello()
print x.hello()

class ListMetaclass(type):
	def __new__(cls,name,bases,attrs):
		attrs['add'] = lambda self,value:self.append(value)
		return type.__new__(cls,name,bases,attrs)
class Mylist(list):
	__metaclass__ = ListMetaclass
L = Mylist()
L.add(1)
for x in L:
	print x