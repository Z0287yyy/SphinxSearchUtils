/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.desc;

public enum SphinxQLMainConcepts {

	Index("Table"),
	
	Document("Row"),
	RowID("Internal Sphinx row number"), rowid("Internal Sphinx row number"),
	
	FieldOrAttribute("Column and/or a FULLTEXT index"),
	IndexedField("Just a FULLTEXT index on a text column"),
	StoredField("Text column and a FULLTEXT index on it"),
	Attribute("Column"),
	MVA("Column with an INT_SET type"),
	JSONAttribute("Column with a JSON type"),
	AttributeIndex("Index"),
	DocumentID("Column called “id”, with a BIGINT type"), docid("Column called “id”, with a BIGINT type"),
	
	;
	
	
	public final String closestSQLEquivalent;
	
	private SphinxQLMainConcepts(String closestSQLEquivalent) {
		this.closestSQLEquivalent = closestSQLEquivalent;
	}
	
}
