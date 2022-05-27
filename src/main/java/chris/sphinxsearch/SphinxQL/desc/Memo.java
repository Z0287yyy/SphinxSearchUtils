/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.desc;

public class Memo {
	
	
// sphinx 配置::
	
	
/*

source main_src{工具

       type                = mysql    //  (mysql, pgsql, mssql, xmlpipe, xmlpipe2, odbc)  // http://sphinxsearch.com/docs/manual-2.3.2.html#sql

       sql_host            = localhost    // 测试

       sql_user            = yourusername

       sql_pass            = yourpassword

       sql_db              = test   //你所用的数据库

       sql_port            = 3306 //所用端口，默认是3306

       sql_query_pre       = SET NAMES utf8

       sql_query_pre       = SET SESSION query_cache_type=OFF       #下面的语句是更新sph_counter表中的 max_doc_id。       sql_query_pre = REPLACE INTO sph_counter SELECT 1, MAX(id) FROM documents

       sql_query = SELECT id, group_id, UNIX_TIMESTAMP(date_added) AS date_added, title,\

                 content FROM documents \

               WHERE id<=( SELECT max_doc_id FROM sph_counter WHERE counter_id=1 ) 

}

// 注意：delta_src 中的sql_query_pre的个数需和main_src 对应，不然可能搜索不出相应结果
 
source delta_src: main_src{

         sql_ranged_throttle = 100

         sql_query_pre       = SET NAMES utf8

         sql_query_pre       = SET SESSION query_cache_type=OFF

         sql_query      = SELECT id, group_id, UNIX_TIMESTAMP(date_added) AS date_added, title, content FROM documents\

            WHERE id>( SELECT max_doc_id FROM sph_counter WHERE counter_id=1 )

}



 */

/*
 * 
 index testrt    // 表名
{
    type            = rt    // 类型
    rt_mem_limit    = 128M    // 内存限制

    path            = /Volumes/Base/sphinx/data/testrt    // 保存路径

    rt_field        = title    // 字段
    rt_field        = content    // 字段
    rt_attr_uint    = gid    // 字段
    
    stored_fields = title, content    // 保存字段
    
    rt_attr_uint = gid # regular attribute
    rt_attr_float_array = vec1[5] # 5D array of floats
    rt_attr_int8_array = vec2[3] # 3D array of small 8-bit integers
}

 */
	

	
/*

Sphinx without using an auto_increment id

 
source products {

  # Use a variable to store a throwaway ID value
  sql_query_pre = SELECT @id := 0 

  # Keep incrementing the throwaway ID.
  # "code" is present twice because Sphinx does not full-text index attributes
  sql_query = SELECT @id := @id + 1, code AS code_attr, code, description FROM products

  # Return the code so that your app will know which records were matched
  # this will only work in Sphinx 0.9.10 and higher!
  sql_attr_string = code_attr  
}
 */
	
	
	
	
/*
 
searchd
{
    listen          = 9312
    listen          = 9306:mysql41    // mysql版本4
    log             = /Volumes/Base/sphinx/log/searchd.log
    query_log       = /Volumes/Base/sphinx/log/query.log
    read_timeout    = 5
    max_children    = 30
    pid_file        = /Volumes/Base/sphinx/log/searchd.pid
    seamless_rotate = 1
    preopen_indexes = 1
    unlink_old      = 1
    workers         = threads # for RT to work
    binlog_path     = /Volumes/Base/sphinx/data
    mysql_version_string = 5.0.37    // 能被mysql jdbc驱动识别
}
 
 
 * */
	
	
	
// 查询
// http://sphinxsearch.com/docs/manual-2.3.2.html#sphinxql-select

	
// create TABLE transactions ( _id String, address field, _count FLOAT, txttime BIGINT, block BIGINT, txid String, side INTEGER)
// SHOW TABLES LIKE '%test%'
// SHOW TABLES
// 	
	
	
//
	
	
}
