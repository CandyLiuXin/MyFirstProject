import ModelMetaclass
class Model(dict):
	__metaclass__ = ModelMetaclass
	def __init__(self,**kw):
		super(Model,self).__init__(**kw)
	def __getattr__(self,key):
		try:
			return self[key]
