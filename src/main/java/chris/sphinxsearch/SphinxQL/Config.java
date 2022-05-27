/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL;

public class Config {

	public static final String JDBCDriverName = "com.mysql.jdbc.Driver";
	public static final String UserName = "";
	public static final String Password = "";
	
	private static final String SphinxUrlTemplate = "jdbc:mysql://%s:%d?characterEncoding=utf8&maxAllowedPacket=512000";
	
	private static final String SphinxDomain = "127.0.0.1";
	private static final long SphinxPort = 9306;
	
	
	public static String getSphinxUrl(String domain, long port) {
		return String.format(SphinxUrlTemplate, domain, port);
	}
	
	public static String getDefaultSphinxUrl() {
		return String.format(SphinxUrlTemplate, SphinxDomain, SphinxPort);
	}
}
