class StringField(Field):
	def __init__(self,name):
		super(StringField,self).__init__(name,'varchar(100)')
class IntegerField(Field):
	def __init__(self,name):
		super(IntegerField,self).__init__(name,'bigint')