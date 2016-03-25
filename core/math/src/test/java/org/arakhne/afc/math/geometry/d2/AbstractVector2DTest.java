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

import static org.arakhne.afc.math.MathConstants.PI;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.arakhne.afc.math.AbstractMathTestCase;
import org.arakhne.afc.math.MathConstants;
import org.arakhne.afc.math.geometry.coordinatesystem.CoordinateSystem2D;
import org.arakhne.afc.math.geometry.coordinatesystem.CoordinateSystem2DTestRule;
import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;

@SuppressWarnings("all")
public abstract class AbstractVector2DTest extends AbstractMathTestCase {

	@Rule
	public CoordinateSystem2DTestRule csTestRule = new CoordinateSystem2DTestRule();

	protected abstract Vector2D createVector(double x, double y);

	protected abstract Point2D createPoint(double x, double y);

	protected abstract boolean isIntCoordinates();
	
	@Test
	public void staticIsUnitVector() {
		assertTrue(Vector2D.isUnitVector(1., 0));
		assertFalse(Vector2D.isUnitVector(1.0001, 0));
		double length = Math.sqrt(5. * 5. + 18. * 18.);
		assertTrue(Vector2D.isUnitVector(5. / length, 18. / length));
	}

	@Test
	public void staticIsCollinearVectors() {
		assertTrue(Vector2D.isCollinearVectors(1, 0, 3, 0));
		assertTrue(Vector2D.isCollinearVectors(1, 0, -3, 0));
		assertFalse(Vector2D.isCollinearVectors(1, 0, 4, 4));
	}

	@Test
	public void staticPerpProduct() {
		assertEpsilonEquals(0, Vector2D.perpProduct(1, 0, 1, 0));
		assertEpsilonEquals(0, Vector2D.perpProduct(1, 0, 5, 0));
		assertEpsilonEquals(243, Vector2D.perpProduct(1, 45, -5, 18));
		assertEpsilonEquals(0, Vector2D.perpProduct(1, 2, 1, 2));
		assertEpsilonEquals(-2, Vector2D.perpProduct(1, 2, 3, 4));
		assertEpsilonEquals(-4, Vector2D.perpProduct(1, 2, 1, -2));
	}

	@Test
	public void staticDotProduct() {
		assertEpsilonEquals(1, Vector2D.dotProduct(1, 0, 1, 0));
		assertEpsilonEquals(5, Vector2D.dotProduct(1, 0, 5, 0));
		assertEpsilonEquals(805, Vector2D.dotProduct(1, 45, -5, 18));
		assertEpsilonEquals(5, Vector2D.dotProduct(1, 2, 1, 2));
		assertEpsilonEquals(11, Vector2D.dotProduct(1, 2, 3, 4));
		assertEpsilonEquals(-3, Vector2D.dotProduct(1, 2, 1, -2));
	}

	@Test
	public void statisSignedAngle() {
		assertEpsilonEquals(0, Vector2D.signedAngle(1, 0, 1, 0));
		assertEpsilonEquals(0, Vector2D.signedAngle(1, 0, 5, 0));
		assertEpsilonEquals(-MathConstants.DEMI_PI, Vector2D.signedAngle(2, 0, 0, -3));
		assertEpsilonEquals(Math.PI, Vector2D.signedAngle(1, 0, -1, 0));
		assertEpsilonEquals(0.29317, Vector2D.signedAngle(1, 45, -5, 18));
	}

	@Test
	public void staticAngleOfVectorDoubleDoubleDoubleDouble() {
		double v1x = this.random.nextDouble();
		double v1y = this.random.nextDouble();
		double v2x = this.random.nextDouble();
		double v2y = this.random.nextDouble();

		assertEpsilonEquals(
				0.,
				Vector2D.signedAngle(v1x, v1y, v1x, v1y));
		assertEpsilonEquals(
				0.,
				Vector2D.signedAngle(v2x, v2y, v2x, v2y));

		double sAngle1 = Vector2D.signedAngle(v1x, v1y, v2x, v2y);
		double sAngle2 = Vector2D.signedAngle(v2x, v2y, v1x, v1y);

		assertEpsilonEquals(-sAngle1, sAngle2);

		double sin = v1x * v2y - v1y * v2x;

		if (sin < 0) {
			assertTrue(sAngle1 <= 0);
			assertTrue(sAngle2 >= 0);
		} else {
			assertTrue(sAngle1 >= 0);
			assertTrue(sAngle2 <= 0);
		}
	}

	@Test
	public void staticAngleOfVectorDoubleDouble() {
		assertEpsilonEquals(Math.acos(1. / Math.sqrt(5)), Vector2D.angleOfVector(1, 2));
		assertEpsilonEquals(PI / 2. + Math.acos(1 / Math.sqrt(5)), Vector2D.angleOfVector(-2, 1));
		assertEpsilonEquals(PI / 4., Vector2D.angleOfVector(1, 1));
	}

	@Test
	public void addVector2DVector2D_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Vector2D vector = createVector(0, 0);
		Vector2D vector2 = createVector(-1, 0);
		Vector2D vector3 = createVector(1.2, 1.2);
		Vector2D vector4 = createVector(2.0, 1.5);
		Vector2D vector5 = createVector(0.0, 0.0);

		vector5.add(vector3,vector);
		assertFpVectorEquals(1.2, 1.2, vector5);

		vector5.add(vector4,vector2);
		assertFpVectorEquals(1.0, 1.5, vector5); 
	}

	@Test
	public void addVector2DVector2D_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Vector2D vector = createVector(0, 0);
		Vector2D vector2 = createVector(-1, 0);
		Vector2D vector3 = createVector(1.2, 1.2);
		Vector2D vector4 = createVector(2.0, 1.5);
		Vector2D vector5 = createVector(0.0, 0.0);

		vector5.add(vector3,vector);
		assertIntVectorEquals(1, 1, vector5);

		vector5.add(vector4,vector2);
		assertIntVectorEquals(1, 2, vector5); 
	}

	@Test
	public void addVector2D_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Vector2D vector = createVector(0,0);
		Vector2D vector2 = createVector(-1,0);
		Vector2D vector3 = createVector(1.2,1.2);
		Vector2D vector4 = createVector(2.0,1.5);

		vector.add(vector3);
		assertFpVectorEquals(1.2, 1.2, vector);

		vector2.add(vector4);
		assertFpVectorEquals(1., 1.5, vector2);
	}

	@Test
	public void addVector2D_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Vector2D vector = createVector(0,0);
		Vector2D vector2 = createVector(-1,0);
		Vector2D vector3 = createVector(1.2,1.2);
		Vector2D vector4 = createVector(2.0,1.5);

		vector.add(vector3);
		assertIntVectorEquals(1, 1, vector);

		vector2.add(vector4);
		assertIntVectorEquals(1, 2, vector2);
	}

	@Test
	public void scaleAddIntVector2DVector2D_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Vector2D vector = createVector(-1,0);
		Vector2D vector2 = createVector(1.0,1.2);
		Vector2D vector3 = createVector(0.0,0.0);

		vector3.scaleAdd(0,vector2,vector);
		assertFpVectorEquals(-1, 0, vector3);

		vector3.scaleAdd(1,vector2,vector);
		assertFpVectorEquals(0.0, 1.2, vector3);

		vector3.scaleAdd(-1,vector2,vector);
		assertFpVectorEquals(-2.0, -1.2, vector3);

		vector3.scaleAdd(10,vector2,vector);
		assertFpVectorEquals(9, 12, vector3);
	}

	@Test
	public void scaleAddIntVector2DVector2D_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Vector2D vector = createVector(-1,0);
		Vector2D vector2 = createVector(1.0,1.2);
		Vector2D vector3 = createVector(0.0,0.0);

		vector3.scaleAdd(0,vector2,vector);
		assertFpVectorEquals(-1, 0, vector3);

		vector3.scaleAdd(1,vector2,vector);
		assertFpVectorEquals(0, 1, vector3);

		vector3.scaleAdd(-1,vector2,vector);
		assertFpVectorEquals(-2, -1, vector3);

		vector3.scaleAdd(10,vector2,vector);
		assertFpVectorEquals(9, 10, vector3);
	}

	@Test
	public void scaleAddDoubleVector2DVector2D_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Vector2D point = createVector(1,0);
		Vector2D vector = createVector(-1,1);
		Vector2D newPoint = createVector(0.0,0.0);

		newPoint.scaleAdd(0.0, vector, point);
		assertFpVectorEquals(1, 0, newPoint);

		newPoint.scaleAdd(1.5,vector,point);
		assertFpVectorEquals(-0.5, 1.5, newPoint);

		newPoint.scaleAdd(-1.5,vector,point);
		assertFpVectorEquals(2.5, -1.5, newPoint);

		newPoint.scaleAdd(0.1,vector,point);
		assertFpVectorEquals(0.9, 0.1, newPoint);
	}

	@Test
	public void scaleAddDoubleVector2DVector2D_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Vector2D point = createVector(1,0);
		Vector2D vector = createVector(-1,1);
		Vector2D newPoint = createVector(0.0,0.0);

		newPoint.scaleAdd(0.0, vector, point);
		assertIntVectorEquals(1, 0, newPoint);

		newPoint.scaleAdd(1.5,vector,point);
		assertIntVectorEquals(0, 2, newPoint);

		newPoint.scaleAdd(-1.5,vector,point);
		assertIntVectorEquals(3, -1, newPoint);

		newPoint.scaleAdd(0.1,vector,point);
		assertIntVectorEquals(1, 0, newPoint);
	}

	@Test
	public void scaleAddIntVector2D() {
		Vector2D vector = createVector(1,0);
		Vector2D newPoint = createVector(0,0);

		newPoint.scaleAdd(0,vector);
		assertFpVectorEquals(1, 0, newPoint);

		newPoint.scaleAdd(1,vector);
		assertFpVectorEquals(2, 0, newPoint);

		newPoint.scaleAdd(-10,vector);
		assertFpVectorEquals(-19, 0, newPoint);
	}

	@Test
	public void scaleAddDoubleVector2D_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Vector2D vector = createVector(1,0);
		Vector2D newPoint = createVector(0.0,0.0);

		newPoint.scaleAdd(0.5,vector);
		assertFpVectorEquals(1, 0, newPoint);

		newPoint.scaleAdd(1.2,vector);
		assertFpVectorEquals(2.2, 0.0, newPoint);

		newPoint.scaleAdd(-10,vector);
		assertFpVectorEquals(-21, 0, newPoint);
	}

	@Test
	public void scaleAddDoubleVector2D_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Vector2D vector = createVector(1,0);
		Vector2D newPoint = createVector(0.0,0.0);

		newPoint.scaleAdd(0.5,vector);
		assertIntVectorEquals(1, 0, newPoint);

		newPoint.scaleAdd(1.2,vector);
		assertIntVectorEquals(2, 0, newPoint);

		newPoint.scaleAdd(-10,vector);
		assertIntVectorEquals(-19, 0, newPoint);
	}

	@Test
	public void subVector2DVector2D_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Vector2D point = createVector(0, 0);
		Vector2D point2 = createVector(1, 0);
		Vector2D vector = createVector(-1.2, -1.2);
		Vector2D vector2 = createVector(2.0, 1.5);
		Vector2D newPoint = createVector(0.0, 0.0);

		newPoint.sub(point,vector);
		assertFpVectorEquals(1.2, 1.2, newPoint);

		newPoint.sub(point2,vector2);
		assertFpVectorEquals(-1.0, -1.5, newPoint); 
	}

	@Test
	public void subVector2DVector2D_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Vector2D point = createVector(0, 0);
		Vector2D point2 = createVector(1, 0);
		Vector2D vector = createVector(-1.2, -1.2);
		Vector2D vector2 = createVector(2.0, 1.5);
		Vector2D newPoint = createVector(0.0, 0.0);

		newPoint.sub(point,vector);
		assertIntVectorEquals(1, 1, newPoint);

		newPoint.sub(point2,vector2);
		assertIntVectorEquals(-1, -2, newPoint); 
	}

	@Test
	public void subPoint2DPoint2D_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Point2D point = createPoint(0, 0);
		Point2D point2 = createPoint(1, 0);
		Point2D vector = createPoint(-1.2, -1.2);
		Point2D vector2 = createPoint(2.0, 1.5);
		Vector2D newPoint = createVector(0.0, 0.0);

		newPoint.sub(point,vector);
		assertFpVectorEquals(1.2, 1.2, newPoint);

		newPoint.sub(point2,vector2);
		assertFpVectorEquals(-1.0, -1.5, newPoint); 
	}

	@Test
	public void subPoint2DPoint2D_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Point2D point = createPoint(0, 0);
		Point2D point2 = createPoint(1, 0);
		Point2D vector = createPoint(-1.2, -1.2);
		Point2D vector2 = createPoint(2.0, 1.5);
		Vector2D newPoint = createVector(0.0, 0.0);

		newPoint.sub(point,vector);
		assertIntVectorEquals(1, 1, newPoint);

		newPoint.sub(point2,vector2);
		assertIntVectorEquals(-1, -2, newPoint); 
	}

	@Test
	public void subVector2D_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Vector2D point = createVector(0, 0);
		Vector2D point2 = createVector(-1, 0);
		Vector2D vector = createVector(-1.2, -1.2);
		Vector2D vector2 = createVector(-2.0, -1.5);

		point.sub(vector);
		assertFpVectorEquals(1.2, 1.2, point);

		point2.sub(vector2);
		assertFpVectorEquals(1.0, 1.5, point2);
	}

	@Test
	public void subVector2D_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Vector2D point = createVector(0, 0);
		Vector2D point2 = createVector(-1, 0);
		Vector2D vector = createVector(-1.2, -1.2);
		Vector2D vector2 = createVector(-2.0, -1.5);

		point.sub(vector);
		assertIntVectorEquals(1, 1, point);

		point2.sub(vector2);
		assertIntVectorEquals(1, 1, point2);
	}

	@Test
	public void dotVector2D() {
		Vector2D vector = createVector(1, 2);
		Vector2D vector2 = createVector(3, 4);
		Vector2D vector3 = createVector(1, -2);

		assertEpsilonEquals(5,vector.dot(vector));
		assertEpsilonEquals(11,vector.dot(vector2));
		assertEpsilonEquals(-3,vector.dot(vector3));
	}

	@Test
	public void perpVector2D() {
		Vector2D vector = createVector(1,2);
		Vector2D vector2 = createVector(3,4);
		Vector2D vector3 = createVector(1,-2);

		assertEpsilonEquals(0, vector.perp(vector));
		assertEpsilonEquals(-2, vector.perp(vector2));
		assertEpsilonEquals(-4, vector.perp(vector3));
	}

	@Test
	public void perpendicularize() {
		Vector2D vector = createVector(1,2);
		Vector2D vector2 = createVector(0,0);
		Vector2D vector3 = createVector(1,1);
		Vector2D vector4 = createVector(1,0);

		vector.perpendicularize();
		vector2.perpendicularize();
		vector3.perpendicularize();
		vector4.perpendicularize();

		assertFpVectorEquals(-2, 1, vector);
		assertFpVectorNotEquals(2, -1, vector);
		assertFpVectorEquals(0, 0, vector2);
		assertFpVectorEquals(-1, 1, vector3);
		assertFpVectorEquals(0, 1, vector4);
	}

	@Test
	public void length() {
		Vector2D vector = createVector(1, 2);
		Vector2D vector2 = createVector(0, 0);
		Vector2D vector3 = createVector(-1, 1);

		assertEpsilonEquals(Math.sqrt(5),vector.length());
		assertEpsilonEquals(0,vector2.length());
		assertEpsilonEquals(Math.sqrt(2),vector3.length());
	}

	@Test
	public void lengthSquared_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Vector2D vector = createVector(1, 2);
		Vector2D vector2 = createVector(0, 0);
		Vector2D vector3 = createVector(Math.sqrt(2.) / 2., Math.sqrt(2.) / 2.);

		assertEpsilonEquals(5,vector.lengthSquared());
		assertEpsilonEquals(0,vector2.lengthSquared());
		assertEpsilonEquals(1,vector3.lengthSquared());
	}

	@Test
	public void lengthSquared_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Vector2D vector = createVector(1, 2);
		Vector2D vector2 = createVector(0, 0);
		Vector2D vector3 = createVector(Math.sqrt(2.) / 2., Math.sqrt(2.) / 2.);

		assertEpsilonEquals(5,vector.lengthSquared());
		assertEpsilonEquals(0,vector2.lengthSquared());
		assertEpsilonEquals(2,vector3.lengthSquared());
	}

	@Test
	public void normalize_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Vector2D vector = createVector(1,2);
		Vector2D vector2 = createVector(0,0);
		Vector2D vector3 = createVector(-1,1);

		vector.normalize();
		vector2.normalize();
		vector3.normalize();

		assertFpVectorEquals(1/Math.sqrt(5),2/Math.sqrt(5), vector);
		assertZero(vector2.getX());
		assertZero(vector2.getY());
		assertFpVectorEquals(-1/Math.sqrt(2),1/Math.sqrt(2), vector3);
	}

	@Test  
	public void normalize_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Vector2D vector = createVector(1,2);
		Vector2D vector2 = createVector(0,0);
		Vector2D vector3 = createVector(-1,1);
		Vector2D vector4 = createVector(0,-5);

		vector.normalize();
		vector2.normalize();
		vector3.normalize();
		vector4.normalize();

		assertIntVectorEquals(0, 1, vector);
		assertIntVectorEquals(0, 0, vector2);
		assertIntVectorEquals(-1, 1, vector3);
		assertIntVectorEquals(0, -1, vector4);
	}

	@Test
	public void normalizeVector3D_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Vector2D vector = createVector(0,0);
		Vector2D vector2 = createVector(0,0);
		Vector2D vector3 = createVector(0,0);

		vector.normalize(createVector(1,2));
		vector2.normalize(createVector(0,0));
		vector3.normalize(createVector(-1,1));

		assertFpVectorEquals(1/Math.sqrt(5),2/Math.sqrt(5), vector);
		assertZero(vector2.getX());
		assertZero(vector2.getY());
		assertFpVectorEquals(-1/Math.sqrt(2),1/Math.sqrt(2), vector3);
	}

	@Test  
	public void normalizeVector3D_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Vector2D vector = createVector(0,0);
		Vector2D vector2 = createVector(0,0);
		Vector2D vector3 = createVector(0,0);
		Vector2D vector4 = createVector(0,0);

		vector.normalize(createVector(1,2));
		vector2.normalize(createVector(0,0));
		vector3.normalize(createVector(-1,1));
		vector4.normalize(createVector(0,-5));

		assertIntVectorEquals(0, 1, vector);
		assertIntVectorEquals(0, 0, vector2);
		assertIntVectorEquals(-1, 1, vector3);
		assertIntVectorEquals(0, -1, vector4);
	}

	@Test
	public void angleVector2D() {
		Vector2D vector = createVector(1, 2);
		Vector2D vector2 = createVector(-2, 1);
		Vector2D vector3 = createVector(1, 1);
		Vector2D vector4 = createVector(1, 0);

		assertEpsilonEquals(PI/2f,vector.angle(vector2));
		assertEpsilonEquals(PI/4f,vector4.angle(vector3));
		assertEpsilonEquals(Math.acos(1/Math.sqrt(5)),vector4.angle(vector));
		assertEpsilonEquals(PI/2f+Math.acos(1/Math.sqrt(5)),vector4.angle(vector2));
		assertEpsilonEquals(0,vector.angle(vector));
	}

	@Test
	public void signedAngleVector2D_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Vector2D v1 = createVector(this.random.nextDouble(), this.random.nextDouble());
		Vector2D v2 = createVector(this.random.nextDouble(), this.random.nextDouble());

		assertEpsilonEquals(
				0.f,
				v1.signedAngle(v1));
		assertEpsilonEquals(
				0.f,
				v2.signedAngle(v2));

		double sAngle1 = v1.signedAngle(v2);
		double sAngle2 = v2.signedAngle(v1);

		assertEpsilonEquals(-sAngle1, sAngle2);
		assertEpsilonEquals(v1.angle(v2), Math.abs(sAngle1));
		assertEpsilonEquals(v2.angle(v1), Math.abs(sAngle2));

		double sin = v1.getX() * v2.getY() - v1.getY() * v2.getX();

		if (sin < 0) {
			assertTrue(sAngle1 <= 0);
			assertTrue(sAngle2 >= 0);
		} else {
			assertTrue(sAngle1 >= 0);
			assertTrue(sAngle2 <= 0);
		}
	}

	@Test
	public void signedAngleVector2D_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Vector2D v1 = createVector(this.random.nextInt(48) + 2, this.random.nextInt(48) + 2);
		Vector2D v2 = createVector(this.random.nextInt(48) + 2, this.random.nextInt(48) + 2);

		assertEpsilonEquals(
				0,
				v1.signedAngle(v1));
		assertEpsilonEquals(
				0,
				v2.signedAngle(v2));

		double sAngle1 = v1.signedAngle(v2);
		double sAngle2 = v2.signedAngle(v1);

		assertEpsilonEquals(-sAngle1, sAngle2);
		assertEpsilonEquals(v1.angle(v2), Math.abs(sAngle1));
		assertEpsilonEquals(v2.angle(v1), Math.abs(sAngle2));

		double sin = v1.getX() * v2.getY() - v1.getY() * v2.getX();

		if (sin < 0) {
			assertTrue(sAngle1 <= 0);
			assertTrue(sAngle2 >= 0);
		} else {
			assertTrue(sAngle1 >= 0);
			assertTrue(sAngle2 <= 0);
		}
	}

	@Test
	public void turn_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Vector2D vector;
		
		vector = createVector(2, 0);
		vector.turn(MathConstants.DEMI_PI);
		assertFpVectorEquals(0, 2, vector); 

		vector.turn(MathConstants.DEMI_PI);
		assertFpVectorEquals(-2, 0, vector); 

		vector.turn(MathConstants.DEMI_PI);
		assertFpVectorEquals(0, -2, vector); 

		vector.turn(MathConstants.DEMI_PI);
		assertFpVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turn(-MathConstants.DEMI_PI);
		assertFpVectorEquals(0, -2, vector); 

		vector.turn(-MathConstants.DEMI_PI);
		assertFpVectorEquals(-2, 0, vector); 

		vector.turn(-MathConstants.DEMI_PI);
		assertFpVectorEquals(0, 2, vector); 

		vector.turn(-MathConstants.DEMI_PI);
		assertFpVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turn(-MathConstants.DEMI_PI/3);
		assertFpVectorEquals(1.732, -1, vector); 
	}

	@Test
	public void turn_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Vector2D vector;
		
		vector = createVector(2, 0);
		vector.turn(MathConstants.DEMI_PI);
		assertIntVectorEquals(0, 2, vector); 

		vector.turn(MathConstants.DEMI_PI);
		assertIntVectorEquals(-2, 0, vector); 

		vector.turn(MathConstants.DEMI_PI);
		assertIntVectorEquals(0, -2, vector); 

		vector.turn(MathConstants.DEMI_PI);
		assertIntVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turn(-MathConstants.DEMI_PI);
		assertIntVectorEquals(0, -2, vector); 

		vector.turn(-MathConstants.DEMI_PI);
		assertIntVectorEquals(-2, 0, vector); 

		vector.turn(-MathConstants.DEMI_PI);
		assertIntVectorEquals(0, 2, vector); 

		vector.turn(-MathConstants.DEMI_PI);
		assertIntVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turn(-MathConstants.DEMI_PI/3);
		assertIntVectorEquals(2, -1, vector); 
	}

	@Test
	public void turnLeft_iffp_rightHanded() {
		Assume.assumeFalse(isIntCoordinates());
		Assume.assumeTrue(CoordinateSystem2D.getDefaultCoordinateSystem().isRightHanded());
		Vector2D vector;
		
		vector = createVector(2, 0);
		vector.turnLeft(MathConstants.DEMI_PI);
		assertFpVectorEquals(0, 2, vector); 

		vector.turnLeft(MathConstants.DEMI_PI);
		assertFpVectorEquals(-2, 0, vector); 

		vector.turnLeft(MathConstants.DEMI_PI);
		assertFpVectorEquals(0, -2, vector); 

		vector.turnLeft(MathConstants.DEMI_PI);
		assertFpVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turnLeft(-MathConstants.DEMI_PI);
		assertFpVectorEquals(0, -2, vector); 

		vector.turnLeft(-MathConstants.DEMI_PI);
		assertFpVectorEquals(-2, 0, vector); 

		vector.turnLeft(-MathConstants.DEMI_PI);
		assertFpVectorEquals(0, 2, vector); 

		vector.turnLeft(-MathConstants.DEMI_PI);
		assertFpVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turnLeft(-MathConstants.DEMI_PI/3);
		assertFpVectorEquals(1.732, -1, vector); 
	}

	@Test
	public void turnLeft_iffp_leftHanded() {
		Assume.assumeFalse(isIntCoordinates());
		Assume.assumeTrue(CoordinateSystem2D.getDefaultCoordinateSystem().isLeftHanded());
		Vector2D vector;
		
		vector = createVector(2, 0);
		vector.turnLeft(MathConstants.DEMI_PI);
		assertFpVectorEquals(0, -2, vector); 

		vector.turnLeft(MathConstants.DEMI_PI);
		assertFpVectorEquals(-2, 0, vector); 

		vector.turnLeft(MathConstants.DEMI_PI);
		assertFpVectorEquals(0, 2, vector); 

		vector.turnLeft(MathConstants.DEMI_PI);
		assertFpVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turnLeft(-MathConstants.DEMI_PI);
		assertFpVectorEquals(0, 2, vector); 

		vector.turnLeft(-MathConstants.DEMI_PI);
		assertFpVectorEquals(-2, 0, vector); 

		vector.turnLeft(-MathConstants.DEMI_PI);
		assertFpVectorEquals(0, -2, vector); 

		vector.turnLeft(-MathConstants.DEMI_PI);
		assertFpVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turnLeft(-MathConstants.DEMI_PI/3);
		assertFpVectorEquals(1.732, 1, vector); 
	}

	@Test
	public void turnLeft_ifi_rightHanded() {
		Assume.assumeTrue(isIntCoordinates());
		Assume.assumeTrue(CoordinateSystem2D.getDefaultCoordinateSystem().isRightHanded());
		Vector2D vector;
		
		vector = createVector(2, 0);
		vector.turnLeft(MathConstants.DEMI_PI);
		assertIntVectorEquals(0, 2, vector); 

		vector.turnLeft(MathConstants.DEMI_PI);
		assertIntVectorEquals(-2, 0, vector); 

		vector.turnLeft(MathConstants.DEMI_PI);
		assertIntVectorEquals(0, -2, vector); 

		vector.turnLeft(MathConstants.DEMI_PI);
		assertIntVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turnLeft(-MathConstants.DEMI_PI);
		assertIntVectorEquals(0, -2, vector); 

		vector.turnLeft(-MathConstants.DEMI_PI);
		assertIntVectorEquals(-2, 0, vector); 

		vector.turnLeft(-MathConstants.DEMI_PI);
		assertIntVectorEquals(0, 2, vector); 

		vector.turnLeft(-MathConstants.DEMI_PI);
		assertIntVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turnLeft(-MathConstants.DEMI_PI/3);
		assertIntVectorEquals(2, -1, vector); 
	}

	@Test
	public void turnLeft_ifi_leftHanded() {
		Assume.assumeTrue(isIntCoordinates());
		Assume.assumeTrue(CoordinateSystem2D.getDefaultCoordinateSystem().isLeftHanded());
		Vector2D vector;
		
		vector = createVector(2, 0);
		vector.turnLeft(MathConstants.DEMI_PI);
		assertIntVectorEquals(0, -2, vector); 

		vector.turnLeft(MathConstants.DEMI_PI);
		assertIntVectorEquals(-2, 0, vector); 

		vector.turnLeft(MathConstants.DEMI_PI);
		assertIntVectorEquals(0, 2, vector); 

		vector.turnLeft(MathConstants.DEMI_PI);
		assertIntVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turnLeft(-MathConstants.DEMI_PI);
		assertIntVectorEquals(0, 2, vector); 

		vector.turnLeft(-MathConstants.DEMI_PI);
		assertIntVectorEquals(-2, 0, vector); 

		vector.turnLeft(-MathConstants.DEMI_PI);
		assertIntVectorEquals(0, -2, vector); 

		vector.turnLeft(-MathConstants.DEMI_PI);
		assertIntVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turnLeft(-MathConstants.DEMI_PI/3);
		assertIntVectorEquals(2, 1, vector); 
	}

	@Test
	public void turnRight_iffp_rightHanded() {
		Assume.assumeFalse(isIntCoordinates());
		Assume.assumeTrue(CoordinateSystem2D.getDefaultCoordinateSystem().isRightHanded());
		Vector2D vector;
		
		vector = createVector(2, 0);
		vector.turnRight(MathConstants.DEMI_PI);
		assertFpVectorEquals(0, -2, vector); 

		vector.turnRight(MathConstants.DEMI_PI);
		assertFpVectorEquals(-2, 0, vector); 

		vector.turnRight(MathConstants.DEMI_PI);
		assertFpVectorEquals(0, 2, vector); 

		vector.turnRight(MathConstants.DEMI_PI);
		assertFpVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turnRight(-MathConstants.DEMI_PI);
		assertFpVectorEquals(0, 2, vector); 

		vector.turnRight(-MathConstants.DEMI_PI);
		assertFpVectorEquals(-2, 0, vector); 

		vector.turnRight(-MathConstants.DEMI_PI);
		assertFpVectorEquals(0, -2, vector); 

		vector.turnRight(-MathConstants.DEMI_PI);
		assertFpVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turnRight(-MathConstants.DEMI_PI/3);
		assertFpVectorEquals(1.732, 1, vector); 
	}

	@Test
	public void turnRight_iffp_leftHanded() {
		Assume.assumeFalse(isIntCoordinates());
		Assume.assumeTrue(CoordinateSystem2D.getDefaultCoordinateSystem().isLeftHanded());
		Vector2D vector;
		
		vector = createVector(2, 0);
		vector.turnRight(MathConstants.DEMI_PI);
		assertFpVectorEquals(0, 2, vector); 

		vector.turnRight(MathConstants.DEMI_PI);
		assertFpVectorEquals(-2, 0, vector); 

		vector.turnRight(MathConstants.DEMI_PI);
		assertFpVectorEquals(0, -2, vector); 

		vector.turnRight(MathConstants.DEMI_PI);
		assertFpVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turnRight(-MathConstants.DEMI_PI);
		assertFpVectorEquals(0, -2, vector); 

		vector.turnRight(-MathConstants.DEMI_PI);
		assertFpVectorEquals(-2, 0, vector); 

		vector.turnRight(-MathConstants.DEMI_PI);
		assertFpVectorEquals(0, 2, vector); 

		vector.turnRight(-MathConstants.DEMI_PI);
		assertFpVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turnRight(-MathConstants.DEMI_PI/3);
		assertFpVectorEquals(1.732, -1, vector); 
	}

	@Test
	public void turnRight_ifi_rightHanded() {
		Assume.assumeTrue(isIntCoordinates());
		Assume.assumeTrue(CoordinateSystem2D.getDefaultCoordinateSystem().isRightHanded());
		Vector2D vector;
		
		vector = createVector(2, 0);
		vector.turnRight(MathConstants.DEMI_PI);
		assertIntVectorEquals(0, -2, vector); 

		vector.turnRight(MathConstants.DEMI_PI);
		assertIntVectorEquals(-2, 0, vector); 

		vector.turnRight(MathConstants.DEMI_PI);
		assertIntVectorEquals(0, 2, vector); 

		vector.turnRight(MathConstants.DEMI_PI);
		assertIntVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turnRight(-MathConstants.DEMI_PI);
		assertIntVectorEquals(0, 2, vector); 

		vector.turnRight(-MathConstants.DEMI_PI);
		assertIntVectorEquals(-2, 0, vector); 

		vector.turnRight(-MathConstants.DEMI_PI);
		assertIntVectorEquals(0, -2, vector); 

		vector.turnRight(-MathConstants.DEMI_PI);
		assertIntVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turnRight(-MathConstants.DEMI_PI/3);
		assertIntVectorEquals(2, 1, vector); 
	}

	@Test
	public void turnRight_ifi_leftHanded() {
		Assume.assumeTrue(isIntCoordinates());
		Assume.assumeTrue(CoordinateSystem2D.getDefaultCoordinateSystem().isLeftHanded());
		Vector2D vector;
		
		vector = createVector(2, 0);
		vector.turnRight(MathConstants.DEMI_PI);
		assertIntVectorEquals(0, 2, vector); 

		vector.turnRight(MathConstants.DEMI_PI);
		assertIntVectorEquals(-2, 0, vector); 

		vector.turnRight(MathConstants.DEMI_PI);
		assertIntVectorEquals(0, -2, vector); 

		vector.turnRight(MathConstants.DEMI_PI);
		assertIntVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turnRight(-MathConstants.DEMI_PI);
		assertIntVectorEquals(0, -2, vector); 

		vector.turnRight(-MathConstants.DEMI_PI);
		assertIntVectorEquals(-2, 0, vector); 

		vector.turnRight(-MathConstants.DEMI_PI);
		assertIntVectorEquals(0, 2, vector); 

		vector.turnRight(-MathConstants.DEMI_PI);
		assertIntVectorEquals(2, 0, vector); 

		vector = createVector(2, 0);
		vector.turnRight(-MathConstants.DEMI_PI/3);
		assertIntVectorEquals(2, -1, vector); 
	}

	@Test
	public abstract void staticGetOrientationAngle();

	@Test
	public void isUnitVector_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Vector2D vector = createVector(7.15161,6.7545);
		Vector2D vector2 = createVector(1,1);
		Vector2D vector3 = createVector(0,-1);

		vector.normalize();
		vector2.setLength(1.);

		assertTrue(vector.isUnitVector());
		assertTrue(vector2.isUnitVector());
		assertTrue(vector3.isUnitVector());
		assertTrue((createVector(Math.sqrt(2)/2,Math.sqrt(2)/2)).isUnitVector());
		assertTrue((createVector(1,0)).isUnitVector()); 
	}

	@Test
	public void isUnitVector_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Vector2D vector = createVector(7.15161,6.7545);
		Vector2D vector2 = createVector(1,1);
		Vector2D vector3 = createVector(0,-1);

		vector.normalize();
		vector2.setLength(1.);

		assertFalse(vector.isUnitVector());
		assertFalse(vector2.isUnitVector());
		assertTrue(vector3.isUnitVector());
		assertFalse((createVector(Math.sqrt(2)/2,Math.sqrt(2)/2)).isUnitVector());
		assertTrue((createVector(1,0)).isUnitVector()); 
	}

	@Test
	public void setLength_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		Vector2D vector = createVector(this.random.nextDouble(), this.random.nextDouble());
		Vector2D vector2 = createVector(0,0);
		Vector2D oldVector = vector.clone();
		
		double newLength = this.random.nextDouble();
		
		vector.setLength(newLength);
		vector2.setLength(newLength);
		
		assertEpsilonEquals(vector.angle(oldVector), 0);
		assertEpsilonEquals(vector.length()*oldVector.length()/newLength,oldVector.length());
		assertFpVectorEquals(newLength,0, vector2);
	}

	@Test
	public void setLength_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		Vector2D vector = createVector(0, 2);
		Vector2D vector2 = createVector(0, 0);
		
		int newLength = 5;
		
		vector.setLength(newLength);
		vector2.setLength(newLength);
		
		assertIntVectorEquals(0, newLength, vector);
		assertIntVectorEquals(newLength, 0, vector2);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void toUnmodifiable_exception() {
		Vector2D origin = createVector(2, 3);
		Vector2D immutable = origin.toUnmodifiable();
		assertNotSame(origin, immutable);
		assertEpsilonEquals(origin, immutable);
		immutable.add(1, 2);
	}

	@Test
	public void toUnmodifiable_changeInOrigin() {
		Vector2D origin = createVector(2, 3);
		Vector2D immutable = origin.toUnmodifiable();
		assertNotSame(origin, immutable);
		assertEpsilonEquals(origin, immutable);
		origin.add(1, 2);
		assertEpsilonEquals(origin, immutable);
	}

	@Test
	public void testClone() {
		Vector2D origin = createVector(23, 45);
		Vector2D clone = origin.clone();
		assertNotNull(clone);
		assertNotSame(origin, clone);
		assertEpsilonEquals(origin.getX(), clone.getX());
		assertEpsilonEquals(origin.getY(), clone.getY());
	}

}
