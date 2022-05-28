/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package demo;


import chris.sphinxsearch.SphinxQL.entity.SphinxBaseEntity;
import chris.sphinxsearch.SphinxQL.entity.SphinxField;
import chris.sphinxsearch.SphinxQL.utils.GsonUtils;

public class Transactions extends SphinxBaseEntity {
	
	public String _id;
	
	@SphinxField(isRTField = true)
	public String address;
	
	public Double _count;
	
	@SphinxField(isIndex = true)
	public Long txtime;
	
	public Long block;
	
	@SphinxField(isRTField = true)
	public String txid;
	
	public Boolean side;
	
	
	@Override
	public String toString() {
		return GsonUtils.toJson(this);
	}
}
