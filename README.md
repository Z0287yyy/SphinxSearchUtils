# SphinxSearchUtils
Java utils of Sphinx

1. Must be use "mysql-connector-java-5.1.49.jar", must greater than 5.1.12.
2. Sphinx Must open listen "mysql41", and mysql_version_string must "5.0.37"
3. If use spring boot, exclude "DataSourceAutoConfiguration", "DataSourceTransactionManagerAutoConfiguration" and "HibernateJpaAutoConfiguration"

4. OR is not supported yet but will be in the future.
5. MATCH('query') is supported and maps to fulltext query
