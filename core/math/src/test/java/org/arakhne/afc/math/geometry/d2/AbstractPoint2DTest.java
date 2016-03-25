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
package org.arakhne.afc.math.geometry.d2;

import static org.junit.Assert.*;

import org.arakhne.afc.math.AbstractMathTestCase;
import org.arakhne.afc.math.geometry.coordinatesystem.CoordinateSystem2DTestRule;
import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;

@SuppressWarnings("all")
public abstract class AbstractPoint2DTest extends AbstractMathTestCase {
	
	@Rule
	public CoordinateSystem2DTestRule csTestRule = new CoordinateSystem2DTestRule();

	protected abstract Point2D createPoint(double x, double y);

	protected abstract Vector2D createVector(double x, double y);

	protected abstract boolean isIntCoordinates();
	
	@Test
	public void staticIsCollinearPoints() {
		assertTrue(Point2D.isCollinearPoints(0, 0, 0, 0, 0, 0));
		assertTrue(Point2D.isCollinearPoints(-6, -4, -1, 3, 4, 10));
		assertFalse(Point2D.isCollinearPoints(0, 0, 1, 1, 1, -5));
	}

	@Test
	public void staticGetDistancePointPoint() {
		assertEpsilonEquals(0, Point2D.getDistancePointPoint(0, 0, 0, 0));
		assertEpsilonEquals(Math.sqrt(5), Point2D.getDistancePointPoint(0, 0, 1, 2));
		assertEpsilonEquals(Math.sqrt(2), Point2D.getDistancePointPoint(0, 0, 1, 1));
	}

	@Test
	public void staticGetDistanceSquaredPointPoint() {
		assertEpsilonEquals(0, Point2D.getDistanceSquaredPointPoint(0, 0, 0, 0));
		assertEpsilonEquals(5, Point2D.getDistanceSquaredPointPoint(0, 0, 1, 2));
		assertEpsilonEquals(2, Point2D.getDistanceSquaredPointPoint(0, 0, 1, 1));
	}

	@Test
	public void staticGetDistanceL1PointPoint() {
		assertEpsilonEquals(4, Point2D.getDistanceL1PointPoint(1.0, 2.0, 3.0, 0));
		assertEpsilonEquals(0, Point2D.getDistanceL1PointPoint(1.0, 2.0, 1 ,2));
		assertEpsilonEquals(0, Point2D.getDistanceL1PointPoint(1, 2, 1.0, 2.0));
		assertEpsilonEquals(4, Point2D.getDistanceL1PointPoint(1.0, 2.0, -1, 0));
	}

	@Test
	public void staticGetDistanceLinfPointPoint() {
		assertEpsilonEquals(2, Point2D.getDistanceLinfPointPoint(1.0,2.0,3.0,0));
		assertEpsilonEquals(0, Point2D.getDistanceLinfPointPoint(1.0,2.0,1,2));
		assertEpsilonEquals(0, Point2D.getDistanceLinfPointPoint(1,2,1.0,2.0));
		assertEpsilonEquals(2, Point2D.getDistanceLinfPointPoint(1.0,2.0,-1,0));
	}

	@Test
	public void getDistanceSquaredPoint2D() {
		Point2D point = createPoint(0, 0);
		Point2D point2 = createPoint(0, 0);
		Point2D point3 = createPoint(1, 2);
		Point2D point4 = createPoint(1, 1);
		assertEpsilonEquals(0, point.getDistanceSquared(point2));
		assertEpsilonEquals(5, point.getDistanceSquared(point3));
		assertEpsilonEquals(2, point.getDistanceSquared(point4));
	}
	
	@Test
	public void getDistancePoint2D() {
		Point2D point = createPoint(0, 0);
		Point2D point2 = createPoint(0, 0);
		Point2D point3 = createPoint(1, 2);
		Point2D point4 = createPoint(1, 1);
		assertEpsilonEquals(0, point.getDistance(point2));
		assertEpsilonEquals(Math.sqrt(5), point.getDistance(point3));
		assertEpsilonEquals(Math.sqrt(2), point.getDistance(point4));
	}

	@Test
	public void getDistanceL1Point2D() {
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Point2D point3 = createPoint(1, 2);
		Point2D point4 = createPoint(-1, 0);
		assertEpsilonEquals(4, point.getDistanceL1(point2));
		assertEpsilonEquals(0, point.getDistanceL1(point));
		assertEpsilonEquals(0, point.getDistanceL1(point3));
		assertEpsilonEquals(4, point.getDistanceL1(point4));
	}

	@Test
	public void getDistanceLinfPoint2D() {
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Point2D point3 = createPoint(1, 2);
		Point2D point4 = createPoint(-1, 0);
		assertEpsilonEquals(2, point.getDistanceLinf(point2));
		assertEpsilonEquals(0, point.getDistanceLinf(point));
		assertEpsilonEquals(0, point.getDistanceLinf(point3));
		assertEpsilonEquals(2, point.getDistanceLinf(point4));
	}

	@Test
	public void getIdistanceL1Point2D() {
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Point2D point3 = createPoint(1, 2);
		Point2D point4 = createPoint(-1, 0);
		assertEquals(4, point.getIdistanceL1(point2));
		assertEquals(0, point.getIdistanceL1(point));
		assertEquals(0, point.getIdistanceL1(point3));
		assertEquals(4, point.getIdistanceL1(point4));
	}

	@Test
	public void getIdistanceLinfPoint2D() {
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Point2D point3 = createPoint(1, 2);
		Point2D point4 = createPoint(-1, 0);
		assertEquals(2, point.getIdistanceLinf(point2));
		assertEquals(0, point.getIdistanceLinf(point));
		assertEquals(0, point.getIdistanceLinf(point3));
		assertEquals(2, point.getIdistanceLinf(point4));
	}

	@Test
	public void addPoint2DVector2D() {
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Vector2D vector1 = createVector(0, 0);
		Vector2D vector2 = createVector(1, 2);
		Vector2D vector3 = createVector(1, -5);
		
		point.add(point, vector1);
		assertFpPointEquals(1, 2, point);

		point.add(point, vector2);
		assertFpPointEquals(2, 4, point);

		point.add(point, vector3);
		assertFpPointEquals(3, -1, point);

		point.add(point2, vector1);
		assertFpPointEquals(3, 0, point);

		point.add(point2, vector2);
		assertFpPointEquals(4, 2, point);

		point.add(point2, vector3);
		assertFpPointEquals(4, -5, point);
	}

	@Test
	public void addVector2DPoint2D() {
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Vector2D vector1 = createVector(0, 0);
		Vector2D vector2 = createVector(1, 2);
		Vector2D vector3 = createVector(1, -5);
		
		point.add(vector1, point);
		assertFpPointEquals(1, 2, point);

		point.add(vector2, point);
		assertFpPointEquals(2, 4, point);

		point.add(vector3, point);
		assertFpPointEquals(3, -1, point);

		point.add(vector1, point2);
		assertFpPointEquals(3, 0, point);

		point.add(vector2, point2);
		assertFpPointEquals(4, 2, point);

		point.add(vector3, point2);
		assertFpPointEquals(4, -5, point);
	}

	@Test
	public void addVector2D() {
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Vector2D vector1 = createVector(0, 0);
		Vector2D vector2 = createVector(1, 2);
		Vector2D vector3 = createVector(1, -5);
		
		point.add(vector1);
		assertFpPointEquals(1, 2, point);

		point.add(vector2);
		assertFpPointEquals(2, 4, point);

		point.add(vector3);
		assertFpPointEquals(3, -1, point);

		point.add(vector1);
		assertFpPointEquals(3, -1, point);

		point.add(vector2);
		assertFpPointEquals(4, 1, point);

		point.add(vector3);
		assertFpPointEquals(5, -4, point);
	}

	@Test
	public void scaleAddDoubleVector2DPoint2D_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Vector2D vector1 = createVector(0, 0);
		Vector2D vector2 = createVector(1, 2);
		Vector2D vector3 = createVector(1, -5);
		
		point.scaleAdd(2.5, vector1, point);
		assertFpPointEquals(1, 2, point);

		point.scaleAdd(-2.5, vector2, point);
		assertFpPointEquals(-1.5, -3, point);

		point.scaleAdd(2.5, vector3, point);
		assertFpPointEquals(1, -15.5, point);

		point.scaleAdd(-2.5, vector1, point2);
		assertFpPointEquals(3, 0, point);

		point.scaleAdd(2.5, vector2, point2);
		assertFpPointEquals(5.5, 5, point);

		point.scaleAdd(-2.5, vector3, point2);
		assertFpPointEquals(0.5, 12.5, point);
	}

	@Test
	public void scaleAddDoubleVector2DPoint2D_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Vector2D vector1 = createVector(0, 0);
		Vector2D vector2 = createVector(1, 2);
		Vector2D vector3 = createVector(1, -5);
		
		point.scaleAdd(2.5, vector1, point);
		assertIntPointEquals(1, 2, point);

		point.scaleAdd(-2.5, vector2, point);
		assertIntPointEquals(-1, -3, point);

		point.scaleAdd(2.5, vector3, point);
		assertIntPointEquals(2, -15, point);

		point.scaleAdd(-2.5, vector1, point2);
		assertIntPointEquals(3, 0, point);

		point.scaleAdd(2.5, vector2, point2);
		assertIntPointEquals(6, 5, point);

		point.scaleAdd(-2.5, vector3, point2);
		assertIntPointEquals(1, 13, point);
	}

	@Test
	public void scaleAddIntVector2DPoint2D() {
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Vector2D vector1 = createVector(0, 0);
		Vector2D vector2 = createVector(1, 2);
		Vector2D vector3 = createVector(1, -5);
		
		point.scaleAdd(2, vector1, point);
		assertFpPointEquals(1, 2, point);

		point.scaleAdd(-2, vector2, point);
		assertFpPointEquals(-1, -2, point);

		point.scaleAdd(2, vector3, point);
		assertFpPointEquals(1, -12, point);

		point.scaleAdd(-2, vector1, point2);
		assertFpPointEquals(3, 0, point);

		point.scaleAdd(2, vector2, point2);
		assertFpPointEquals(5, 4, point);

		point.scaleAdd(-2, vector3, point2);
		assertFpPointEquals(1, 10, point);
	}

	@Test
	public void scaleAddIntPoint2DVector2D() {
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Vector2D vector1 = createVector(0, 0);
		Vector2D vector2 = createVector(1, 2);
		Vector2D vector3 = createVector(1, -5);
		
		point.scaleAdd(2, point, vector1);
		assertFpPointEquals(2, 4, point);

		point.scaleAdd(-2, point, vector2);
		assertFpPointEquals(-3, -6, point);

		point.scaleAdd(2, point, vector3);
		assertFpPointEquals(-5, -17, point);

		point.scaleAdd(-2, point2, vector1);
		assertFpPointEquals(-6, 0, point);

		point.scaleAdd(2, point2, vector2);
		assertFpPointEquals(7, 2, point);

		point.scaleAdd(-2, point2, vector3);
		assertFpPointEquals(-5, -5, point);
	}

	@Test
	public void scaleAddDoublePoint2DVector2D_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Vector2D vector1 = createVector(0, 0);
		Vector2D vector2 = createVector(1, 2);
		Vector2D vector3 = createVector(1, -5);
		
		point.scaleAdd(2.5, point, vector1);
		assertFpPointEquals(2.5, 5, point);

		point.scaleAdd(-2.5, point, vector2);
		assertFpPointEquals(-5.25, -10.5, point);

		point.scaleAdd(2.5, point, vector3);
		assertFpPointEquals(-12.125, -31.25, point);

		point.scaleAdd(-2.5, point2, vector1);
		assertFpPointEquals(-7.5, 0, point);

		point.scaleAdd(2.5, point2, vector2);
		assertFpPointEquals(8.5, 2, point);

		point.scaleAdd(-2.5, point2, vector3);
		assertFpPointEquals(-6.5, -5, point);
	}

	@Test
	public void scaleAddDoublePoint2DVector2D_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Vector2D vector1 = createVector(0, 0);
		Vector2D vector2 = createVector(1, 2);
		Vector2D vector3 = createVector(1, -5);
		
		point.scaleAdd(2.5, point, vector1);
		assertIntPointEquals(3, 5, point);

		point.scaleAdd(-2.5, point, vector2);
		assertIntPointEquals(-6, -10, point);

		point.scaleAdd(2.5, point, vector3);
		assertIntPointEquals(-14, -30, point);

		point.scaleAdd(-2.5, point2, vector1);
		assertIntPointEquals(-7, 0, point);

		point.scaleAdd(2.5, point2, vector2);
		assertIntPointEquals(9, 2, point);

		point.scaleAdd(-2.5, point2, vector3);
		assertIntPointEquals(-6, -5, point);
	}

	@Test
	public void scaleAddIntVector2D() {
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Vector2D vector1 = createVector(0, 0);
		Vector2D vector2 = createVector(1, 2);
		Vector2D vector3 = createVector(1, -5);
		
		point.scaleAdd(2, vector1);
		assertFpPointEquals(2, 4, point);

		point.scaleAdd(-2, vector2);
		assertFpPointEquals(-3, -6, point);

		point.scaleAdd(2, vector3);
		assertFpPointEquals(-5, -17, point);

		point.scaleAdd(-2, vector1);
		assertFpPointEquals(10, 34, point);

		point.scaleAdd(2, vector2);
		assertFpPointEquals(21, 70, point);

		point.scaleAdd(-2, vector3);
		assertFpPointEquals(-41, -145, point);
	}

	@Test
	public void scaleAddDoubleVector2D_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Vector2D vector1 = createVector(0, 0);
		Vector2D vector2 = createVector(1, 2);
		Vector2D vector3 = createVector(1, -5);
		
		point.scaleAdd(2.5, vector1);
		assertFpPointEquals(2.5, 5, point);

		point.scaleAdd(-2.5, vector2);
		assertFpPointEquals(-5.25, -10.5, point);

		point.scaleAdd(2.5, vector3);
		assertFpPointEquals(-12.125, -31.25, point);

		point.scaleAdd(-2.5, vector1);
		assertFpPointEquals(30.312, 78.125, point);

		point.scaleAdd(2.5, vector2);
		assertFpPointEquals(76.781, 197.312, point);

		point.scaleAdd(-2.5, vector3);
		assertFpPointEquals(-190.95, -498.28, point);
	}

	@Test
	public void scaleAddDoubleVector2D_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Vector2D vector1 = createVector(0, 0);
		Vector2D vector2 = createVector(1, 2);
		Vector2D vector3 = createVector(1, -5);
		
		point.scaleAdd(2.5, vector1);
		assertIntPointEquals(3, 5, point);

		point.scaleAdd(-2.5, vector2);
		assertIntPointEquals(-6, -10, point);

		point.scaleAdd(2.5, vector3);
		assertIntPointEquals(-14, -30, point);

		point.scaleAdd(-2.5, vector1);
		assertIntPointEquals(35, 75, point);

		point.scaleAdd(2.5, vector2);
		assertIntPointEquals(89, 190, point);

		point.scaleAdd(-2.5, vector3);
		assertIntPointEquals(-221, -480, point);
	}

	@Test
	public void subPoint2DVector2D() {
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Vector2D vector1 = createVector(0, 0);
		Vector2D vector2 = createVector(1, 2);
		Vector2D vector3 = createVector(1, -5);
		
		point.sub(point, vector1);
		assertFpPointEquals(1, 2, point);

		point.sub(point, vector2);
		assertFpPointEquals(0, 0, point);

		point.sub(point, vector3);
		assertFpPointEquals(-1, 5, point);

		point.sub(point2, vector1);
		assertFpPointEquals(3, 0, point);

		point.sub(point2, vector2);
		assertFpPointEquals(2, -2, point);

		point.sub(point2, vector3);
		assertFpPointEquals(2, 5, point);
	}

	@Test
	public void subVector2D() {
		Point2D point = createPoint(1, 2);
		Point2D point2 = createPoint(3, 0);
		Vector2D vector1 = createVector(0, 0);
		Vector2D vector2 = createVector(1, 2);
		Vector2D vector3 = createVector(1, -5);
		
		point.sub(vector1);
		assertFpPointEquals(1, 2, point);

		point.sub(vector2);
		assertFpPointEquals(0, 0, point);

		point.sub(vector3);
		assertFpPointEquals(-1, 5, point);

		point.sub(vector1);
		assertFpPointEquals(-1, 5, point);

		point.sub(vector2);
		assertFpPointEquals(-2, 3, point);

		point.sub(vector3);
		assertFpPointEquals(-3, 8, point);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void toUnmodifiable_exception() {
		Point2D origin = createPoint(2, 3);
		Point2D immutable = origin.toUnmodifiable();
		assertNotSame(origin, immutable);
		assertEpsilonEquals(origin, immutable);
		immutable.add(1, 2);
	}

	@Test
	public void toUnmodifiable_changeInOrigin() {
		Point2D origin = createPoint(2, 3);
		Point2D immutable = origin.toUnmodifiable();
		assertNotSame(origin, immutable);
		assertEpsilonEquals(origin, immutable);
		origin.add(1, 2);
		assertEpsilonEquals(origin, immutable);
	}

	@Test
	public void testClone() {
		Point2D origin = createPoint(23, 45);
		Point2D clone = origin.clone();
		assertNotNull(clone);
		assertNotSame(origin, clone);
		assertEpsilonEquals(origin.getX(), clone.getX());
		assertEpsilonEquals(origin.getY(), clone.getY());
	}

}
