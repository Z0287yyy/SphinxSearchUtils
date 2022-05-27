/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.desc;

public enum DocStore {

	store_fields("store indexed fields"),
	stored_only_fields("store unindexed fields"),
	hl_fields("store precomputed data to speedup snippets"),  // can accelerate highlighting where possible, sometimes making snippets times faster. If your documents are big enough (as in, a little bigger than tweets), try it! Without hl_fields, SNIPPET() function will have to reparse the document contents every time. With it, the parsed representation is compressed and stored into the index upfront, trading off a not-insignificant amount of CPU work for more disk space, and a few extra disk reads.
	
	
//  docstore_type = vblock_solid, (default) groups small documents into a single compressed block, upto a given limit: better compression, slower access
//	docstore_type = vblock, stores every document separately: worse compression, faster access
//	docstore_block = 16k, (default) lets you tweak the block size limit
//	docstore_comp = lz4hc, (default) uses LZ4HC algorithm for compression: better compression, but slower
//	docstore_comp = lz4, uses LZ4 algorithm: worse compression, but faster
//	docstore_comp = none, disables compression
	docstore_type("be fine-tuned a little"),  
	docstore_comp("be fine-tuned a little"),
	docstore_block("be fine-tuned a little"),
	
	rt_attr_string("creates an attribute, uncompressed, and stored in RAM. Attributes are supposed to be small, and suitable for filtering (WHERE), sorting (ORDER BY), and other operations like that, by the millions. So if you really need to run queries like … WHERE title=‘abc’, or in case you want to update those strings on the fly, you will still need attributes."),
	;
	
	
	public final String desc;
	
	private DocStore(String desc) {
		this.desc = desc;
	}
	
}
