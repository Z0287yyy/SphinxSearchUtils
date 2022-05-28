/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package demo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import chris.sphinxsearch.SphinxQL.dao.BasicDao;
import chris.sphinxsearch.SphinxQL.desc.SphinxQLAttributes;
import chris.sphinxsearch.SphinxQL.entity.PageBean;
import chris.sphinxsearch.SphinxQL.jdbc.JDBCExecutor;
import chris.sphinxsearch.SphinxQL.sql.SphinxQLUtils;

public class SphixQLTest {

	
	public static void main(String[] args) {
		System.err.println(SphinxQLAttributes.getAttributeByType(Timestamp.class));
		
		BasicDao<Transactions> dao = new BasicDao<>(Transactions.class);
		
		dao.createTableIfNotExist();
		
//		Transactions t = new Transactions();
//		t.id = 1;
//		t._id = "61cd2a978c184842c853136e";
//		t.address = "12BPEHE6rHK9be4JZ2PsfS7PE79P8o5o4e";
//		t._count = 50.5;
//		t.txtime = 1265403586l;
//		t.block = 38516l;
//		t.txid = "49a392ae4e8a7a458d9aae9572219e5419cf8c8d9e88209bf56ac03ee8589233";
//		t.side = true;
//		
//		dao.insert(t);
		
//		System.err.println(DBExecute.getDBExecute().findList("select * from transactions"));
		
		
		List<Transactions> trans = new ArrayList<>();
		
//		for (int i = 0; i < 7; i++) {
//			Transactions t = new Transactions();
//			t._id = "_id_" + i;
//			t.address = "address_" + i;
//			t._count = i + 0.5;
//			t.txtime = 123 * 1l;
//			t.block = i * 1l;
//			t.txid = "txid_" + i;
//			t.side = true;
//			
//			trans.add(t);
//		}
//		
//		dao.batchInsert(trans);
		
		
		// query
		trans = dao.find(PageBean.newInstance(10, 10));
		System.err.println(trans.size());
		System.err.println(trans.get(0));
		System.err.println(trans.get(trans.size() - 1));
		
		
		trans = dao.findByField("address", "address_1");
		System.err.println(trans.size());
		System.err.println(trans.get(0));
		System.err.println(trans.get(trans.size() - 1));
		
		
	}
}
