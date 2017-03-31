rem correct 'filename' field in liquibase table
mysql -uroot -proot test_db -e "update databasechangelog set filename = replace(filename, '../liquibase', 'src/db/liquibase')"
rem create sql dump in test resources folder
mysqldump -uroot -proot test_db > ../../server/src/test/resources/test_dump.sql