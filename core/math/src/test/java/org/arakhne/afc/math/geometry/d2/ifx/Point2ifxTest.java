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
package org.arakhne.afc.math.geometry.d2.ifx;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.arakhne.afc.math.geometry.d2.AbstractPoint2DTest;
import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.Shape2D;
import org.arakhne.afc.math.geometry.d2.Vector2D;
import org.junit.Test;

@SuppressWarnings("all")
public class Point2ifxTest extends AbstractPoint2DTest {

	@Override
	protected boolean isIntCoordinates() {
		return true;
	}
	
	@Override
	protected Vector2D createVector(double x, double y) {
		return new Vector2ifx(x, y);
	}

	@Override
	protected Point2D createPoint(double x, double y) {
		return new Point2ifx(x, y);
	}

	@Test
	public void testClone() {
		super.testClone();
		Point2ifx origin = (Point2ifx) createPoint(23, 45);
		Point2ifx clone = (Point2ifx) origin.clone();
		assertNotSame(origin.xProperty(), clone.xProperty());
		assertNotSame(origin.yProperty(), clone.yProperty());
	}
	
	@Override
	public void operator_andShape2D() {
		Shape2D shape = new Circle2ifx(5, 8, 5);
		assertFalse(createPoint(0,0).operator_and(shape));
		assertFalse(createPoint(11,10).operator_and(shape));
		assertFalse(createPoint(11,50).operator_and(shape));
		assertFalse(createPoint(9,12).operator_and(shape));
		assertTrue(createPoint(9,11).operator_and(shape));
		assertTrue(createPoint(8,12).operator_and(shape));
		assertTrue(createPoint(3,7).operator_and(shape));
		assertFalse(createPoint(10,11).operator_and(shape));
		assertTrue(createPoint(9,10).operator_and(shape));
	}
	
	@Override
	public void operator_upToShape2D() {
		Shape2D shape = new Circle2ifx(5, 8, 5);
		assertEpsilonEquals(0f, createPoint(5,8).operator_upTo(shape));
		assertEpsilonEquals(0f, createPoint(10,10).operator_upTo(shape));
		assertEpsilonEquals(0f, createPoint(4,8).operator_upTo(shape));
		assertEpsilonEquals(4.242640687f, createPoint(0,0).operator_upTo(shape));
		assertEpsilonEquals(1f, createPoint(5,14).operator_upTo(shape));
	}


}
