class ModelMetaclass(type):
	def __new__(cla,name,bases,attrs):
		if name='Model':
			return type.__new__(cls,name,bases,attrs)
		mappings = dict()
		for k,v in attrs.iteritems():
			if isinstance(v,Field):
				print 'Found mapping:%s==>%s' % (k,v)
				mappings[k] = v
		for k in mappings.iterkeys():
			attrs.pop(k)
		attrs['__table__'] = name
		attrs['__mappings__'] mappings
		return type.__new__(cls,name,bases,attrs)