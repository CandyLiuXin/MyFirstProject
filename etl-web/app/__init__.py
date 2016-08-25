#-*- coding: utf-8 -*-

from flask import Flask
from flask_bootstrap import Bootstrap
from config import config, basedir
from sqlalchemy import create_engine
from sqlalchemy.orm import scoped_session, sessionmaker
from sqlalchemy.ext.declarative import declarative_base

# db = SQLAlchemy()
# db_url = 'oracle://amletl:aml@20.13.0.134:1521/orcl'
db_url = 'oracle://etl:etl@localhost:1521/orcl'
# db_url = 'sqlite:///' + basedir + '/etl.db1'
engine = create_engine(db_url, encoding='utf-8')
# engine = create_engine('sqlite:///' + basedir + '/etl.db1', encoding='utf-8')
db_session = scoped_session(sessionmaker(autocommit=False,
                                         autoflush=False,
                                         bind=engine))
Base = declarative_base()
Base.query = db_session.query_property()


def create_app(config_name="development"):
    app = Flask(__name__)
    app.config.from_object(config[config_name])
    config[config_name].init_app(app)

    from .main import main as main_blueprint
    app.register_blueprint(main_blueprint)

    # from .main.dao import EtlSys, EtlJob

    Bootstrap(app)
    # db.init_app(app)
    Base.metadata.create_all(bind=engine)

    return app
