/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.dao;


public enum JoinType {
	NONE( -666 ),
	INNER_JOIN( 0 ),
	LEFT_OUTER_JOIN( 1 ),
	RIGHT_OUTER_JOIN( 2 ),
	FULL_JOIN( 4 );
	private int joinTypeValue;

	JoinType(int joinTypeValue) {
		this.joinTypeValue = joinTypeValue;
	}

	public int getJoinTypeValue() {
		return joinTypeValue;
	}

	public static JoinType parse(int joinType) {
		if ( joinType < 0 ) {
			return NONE;
		}
		switch ( joinType ) {
			case 0:
				return INNER_JOIN;
			case 1:
				return LEFT_OUTER_JOIN;
			case 2:
				return RIGHT_OUTER_JOIN;
			case 4:
				return FULL_JOIN;
			default:
				return NONE;
		}
	}
}