@echo off
  title Database - rosinkas
  MODE CON: COLS=120 LINES=9999
liquibase.bat --logLevel=info generateChangeLog > change-log.sql