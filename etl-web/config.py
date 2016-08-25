#-*- coding: utf-8 -*-
import os


basedir = os.path.abspath(os.path.dirname(__file__))


class Config:
    DB_URL = ""
    DB_USERNAME = "amletl"
    DB_PASSWORD = "aml"
    SQLALCHEMY_NATIVE_UNICODE = False
    # SQLALCHEMY_DATABASE_URI = "sqlite:///" + basedir + "/etl.db"
    # SQLALCHEMY_DATABASE_URI = "oracle://amletl:aml@20.13.0.134:1521/orcl"

    @staticmethod
    def init_app(app):
        pass


class DevelopmentConfig(Config):
    DEBUG = True
    DB_URL = ""


class ProductionConfig(Config):
    DB_URL = ""


config = {
    'development': DevelopmentConfig,
    'production': ProductionConfig,
    'default': DevelopmentConfig
}
