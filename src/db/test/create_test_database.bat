mysql -uroot -proot -e "drop database if exists test_db; create database test_db;"
liquibase.bat --logLevel=info --changeLogFile=../liquibase/update.xml update 
