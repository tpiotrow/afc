/**
 * 
 * fr.utbm.v3g.core.math.Tuple2dTest.java
 *
 * Copyright (c) 2008-10, Multiagent Team - Systems and Transportation Laboratory (SeT)
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of the Systems and Transportation Laboratory ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with the SeT.
 * 
 * http://www.multiagent.fr/
 *
 * Primary author : Olivier LAMOTTE (olivier.lamotte@utbm.fr) - 2015
 *
 */
package org.arakhne.afc.math.geometry.d2.i;

import org.arakhne.afc.math.geometry.d2.AbstractTuple2DTest;

@SuppressWarnings("all")
public class Tuple2iTest extends AbstractTuple2DTest<Tuple2i, Tuple2i> {

	@Override
	public boolean isIntCoordinates() {
		return true;
	}
	
	@Override
	public Tuple2i createTuple(double x, double y) {
		return new Tuple2i(x, y);
	}

}