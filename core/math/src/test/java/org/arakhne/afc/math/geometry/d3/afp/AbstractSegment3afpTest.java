/* 
 * $Id$
 * 
 * Copyright (C) 2011 Janus Core Developers
 * Copyright (C) 2012-13 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * This program is free software; you can redistribute it and/or modify
 */
package org.arakhne.afc.math.geometry.d3.afp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.arakhne.afc.math.MathConstants;
import org.arakhne.afc.math.geometry.PathElementType;
import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.Shape2D;
import org.arakhne.afc.math.geometry.d2.Transform2D;
import org.arakhne.afc.math.geometry.d2.afp.Segment2afp.UncertainIntersection;
import org.arakhne.afc.math.geometry.d2.ai.PathIterator2ai;
import org.junit.Test;

@SuppressWarnings("all")
public abstract class AbstractSegment3afpTest<T extends Segment3afp<?, T, ?, ?, ?, B>,
B extends RectangularPrism3afp<?, ?, ?, ?, ?, B>> extends AbstractShape3afpTest<T, B> {

	@Override
	protected final T createShape() {
		return (T) createSegment(0, 0, 1, 1);
	}

	@Test
	public void staticCcw() {
		double x1 = -100;
		double y1 = -100;
		double x2 = 100;
		double y2 = 100;

		assertEquals(0, Segment2afp.ccw(x1, y1, x2, y2, x1, y1, 0));
		assertEquals(0, Segment2afp.ccw(x2, y2, x1, y1, x1, y1, 0));

		assertEquals(0, Segment2afp.ccw(x1, y1, x2, y2, x2, y2, 0));
		assertEquals(0, Segment2afp.ccw(x2, y2, x1, y1, x2, y2, 0));

		assertEquals(0, Segment2afp.ccw(x1, y1, x2, y2, 0, 0, 0));
		assertEquals(0, Segment2afp.ccw(x2, y2, x1, y1, 0, 0, 0));

		assertEquals(-1, Segment2afp.ccw(x1, y1, x2, y2, -200, -200, 0));
		assertEquals(1, Segment2afp.ccw(x2, y2, x1, y1, -200, -200, 0));

		assertEquals(1, Segment2afp.ccw(x1, y1, x2, y2, 200, 200, 0));
		assertEquals(-1, Segment2afp.ccw(x2, y2, x1, y1, 200, 200, 0));

		assertEquals(-1, Segment2afp.ccw(x1, y1, x2, y2, -200, 200, 0));
		assertEquals(1, Segment2afp.ccw(x2, y2, x1, y1, -200, 200, 0));

		assertEquals(1, Segment2afp.ccw(x1, y1, x2, y2, 200, -200, 0));
		assertEquals(-1, Segment2afp.ccw(x2, y2, x1, y1, 200, -200, 0));
	}

	@Test
	public void staticComputeClosestPointTo() {
		Point2D result;

		result = createPoint(Double.NaN, Double.NaN);
		Segment2afp.computeClosestPointTo(0, 0, 1, 1, 0, 0, result);
		assertEpsilonEquals(0, result.getX());
		assertEpsilonEquals(0, result.getY());

		result = createPoint(Double.NaN, Double.NaN);
		Segment2afp.computeClosestPointTo(0, 0, 1, 1, .75, .75, result);
		assertEpsilonEquals(.75, result.getX());
		assertEpsilonEquals(.75, result.getY());

		result = createPoint(Double.NaN, Double.NaN);
		Segment2afp.computeClosestPointTo(0, 0, 1, 1, -10, -50, result);
		assertEpsilonEquals(0, result.getX());
		assertEpsilonEquals(0, result.getY());

		result = createPoint(Double.NaN, Double.NaN);
		Segment2afp.computeClosestPointTo(0, 0, 1, 1, 200, -50, result);
		assertEpsilonEquals(1, result.getX());
		assertEpsilonEquals(1, result.getY());

		result = createPoint(Double.NaN, Double.NaN);
		Segment2afp.computeClosestPointTo(0, 0, 1, 1, 0, 1, result);
		assertEpsilonEquals(.5, result.getX());
		assertEpsilonEquals(.5, result.getY());
	}

	@Test
	public void staticComputeCrossingsFromCircle() {
		assertEquals(0, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				5, -4, -1, -5));
		assertEquals(0, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				-7, -3, -5, -1));
		assertEquals(0, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				11, -2, 10, -1));
		assertEquals(0, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				3, 5, 1, 6));
		assertEquals(-1, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				5, .5, 6, -1));
		assertEquals(-2, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				5, 2, 6, -1));
		assertEquals(-1, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				5, 2, 6, .5));
		assertEquals(MathConstants.SHAPE_INTERSECTS, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				.5, .5, 3, 0));
		assertEquals(MathConstants.SHAPE_INTERSECTS, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				0, 2, 3, 0));
		assertEquals(MathConstants.SHAPE_INTERSECTS, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				.5, 4, .5, -1));
		assertEquals(-2, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				1, 3, 3, 0));

		assertEquals(0, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				-1, -5, 5, -4));
		assertEquals(0, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				-5, -1, -7, 3));
		assertEquals(0, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				10, -1, 11, -2));
		assertEquals(0, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				1, 6, 3, 5));
		assertEquals(1, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				6, -1, 5, .5));
		assertEquals(2, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				6, -1, 5, 2));
		assertEquals(1, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				6, .5, 5, 2));
		assertEquals(MathConstants.SHAPE_INTERSECTS, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				3, 0, .5, .5));
		assertEquals(MathConstants.SHAPE_INTERSECTS, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				3, 0, 0, 2));
		assertEquals(MathConstants.SHAPE_INTERSECTS, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				.5, -1, .5, 4));
		assertEquals(2, Segment2afp.computeCrossingsFromCircle(
				0,
				1, 1, 1,
				3, 0, 1, 3));
	}

	@Test
	public void staticComputeCrossingsFromEllipse() {
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						5, -4, -1, -5));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						-7, 3, -5, -1));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						11, -2, 10, -1));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						3, 5, 1, 6));
		assertEquals(
				-1,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						5, .5, 6, -1));
		assertEquals(
				-2,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						5, 2, 6, -1));
		assertEquals(
				-1,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						5, 2, 6, .5));
		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						.5, .5, 3, 0));
		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						0, 1, 3, 0));
		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						0, 1, 3, 0));
		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						.5, 2, .5, -1));
		assertEquals(
				-2,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 1, 1,
						1, 1, 7, -5));
		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						4, -3, 1, 2,
						1, 1, 7, -5));
		assertEquals(
				-2,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						4, -3, 1, 2,
						4.2, 0, 7, -5));

		assertEquals(
				0,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						-1, -5, 5, -4));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						-5, -1, -7, 3));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						10, -1, 11, -2));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						1, 6, 3, 5));
		assertEquals(
				1,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						6, -1, 5, .5));
		assertEquals(
				2,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						6, -1, 5, 2));
		assertEquals(
				1,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						6, .5, 5, 2));
		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						3, 0, .5, .5));
		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						3, 0, 0, 1));
		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						3, 0, 0, 1));
		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 2, 1,
						.5, -1, .5, 2));
		assertEquals(
				2,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						0, 0, 1, 1,
						7, -5, 1, 1));
		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						4, -3, 1, 2,
						7, -5, 1, 1));
		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						4, -3, 1, 2,
						7, -5, 4, 0));
		assertEquals(
				2,
				Segment2afp.computeCrossingsFromEllipse(
						0,
						4, -3, 1, 2,
						7, -5, 4.2, 0));
	}

	@Test
	public void staticComputeCrossingsFromPoint() {
		assertEquals(
				1,
				Segment2afp.computeCrossingsFromPoint(
						0, 0,
						10, -1, 10, 1));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromPoint(
						0, 0,
						10, -1, 10, -.5));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromPoint(
						0, 0,
						-10, -1, -10, 1));
	}

	@Test
	public void staticComputeCrossingsFromPointWithoutEquality() {
		assertEquals(
				1,
				Segment2afp.computeCrossingsFromPointWithoutEquality(
						0, 0,
						10, -1, 10, 1));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromPointWithoutEquality(
						0, 0,
						10, -1, 10, -.5));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromPointWithoutEquality(
						0, 0,
						-10, -1, -10, 1));
	}

	@Test
	public void staticComputeCrossingsFromRect() {
		assertEquals(
				2,
				Segment2afp.computeCrossingsFromRect(
						0,
						0, 0, 1, 1,
						10, -5, 10, 5));
		assertEquals(
				1,
				Segment2afp.computeCrossingsFromRect(
						0,
						0, 0, 1, 1,
						10, -5, 10, .5));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromRect(
						0,
						0, 0, 1, 1,
						10, -5, 10, -1));
	}

	@Test
	public void staticComputeCrossingsFromTriangle() {
		assertEquals(
				1,
				Segment2afp.computeCrossingsFromTriangle(
						0,
						5, 8, -10, 1, -1, -2,
						10, -5, 10, 5));
		assertEquals(
				-1,
				Segment2afp.computeCrossingsFromTriangle(
						0,
						5, 8, -10, 1, -1, -2,
						10, 5, 10, -5));
		assertEquals(
				-1,
				Segment2afp.computeCrossingsFromTriangle(
						0,
						5, 8, -10, 1, -1, -2,
						10, 5, 0, -4));
		assertEquals(
				1,
				Segment2afp.computeCrossingsFromTriangle(
						0,
						5, 8, -10, 1, -1, -2,
						0, -4, 10, 5));
		assertEquals(
				2,
				Segment2afp.computeCrossingsFromTriangle(
						0,
						5, 8, -10, 1, -1, -2,
						0, -4, 8, 10));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromTriangle(
						0,
						5, 8, -10, 1, -1, -2,
						-20, 0, 8, 10));
		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromTriangle(
						0,
						5, 8, -10, 1, -1, -2,
						8, 10, -8, 0));
	}

	@Test
	public void staticComputeCrossingsFromRoundRect() {
		assertEquals(
				2,
				Segment2afp.computeCrossingsFromRoundRect(
						0,
						0, 0, 1, 1, .1, .2,
						10, -5, 10, 5));
		assertEquals(
				1,
				Segment2afp.computeCrossingsFromRoundRect(
						0,
						0, 0, 1, 1, .1, .2,
						10, -5, 10, .5));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromRoundRect(
						0,
						0, 0, 1, 1, .1, .2,
						10, -5, 10, -1));
	}

	@Test
	public void staticComputeCrossingsFromSegment() {
		// 0011
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 0, 1, 1,
						10, -5, 10, -4));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 0, 1, 1,
						10, 5, 10, 4));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 0, 1, 1,
						-5, .5, 0, .6));

		assertEquals(
				1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 0, 1, 1,
						10, -1, 11, .6));
		assertEquals(
				2,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 0, 1, 1,
						10, -1, 11, 2));
		assertEquals(
				1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 0, 1, 1,
						10, .5, 11, 2));

		assertEquals(
				-1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 0, 1, 1,
						10, 2, 11, .6));
		assertEquals(
				-2,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 0, 1, 1,
						10, 2, 11, -1));
		assertEquals(
				-1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 0, 1, 1,
						10, .6, 11, -1));

		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 0, 1, 1,
						0, .5, .25, .5));

		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 0, 1, 1,
						.75, .5, 1, .5));

		assertEquals(
				1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 0, 1, 1,
						5, -5, .75, .5));

		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 0, 1, 1,
						5, -5, 0, 1));

		assertEquals(
				2,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 0, 1, 1,
						5, -5, 1, 1));

		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 0, 1, 1,
						-2, 1, 5, -5));

		// 1100

		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 1, 0, 0,
						10, -5, 10, -4));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 1, 0, 0,
						10, 5, 10, 4));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 1, 0, 0,
						-5, .5, 0, .6));

		assertEquals(
				1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 1, 0, 0,
						10, -1, 11, .6));
		assertEquals(
				2,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 1, 0, 0,
						10, -1, 11, 2));
		assertEquals(
				1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 1, 0, 0,
						10, .5, 11, 2));

		assertEquals(
				-1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 1, 0, 0,
						10, 2, 11, .6));
		assertEquals(
				-2,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 1, 0, 0,
						10, 2, 11, -1));
		assertEquals(
				-1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 1, 0, 0,
						10, .6, 11, -1));

		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 1, 0, 0,
						0, .5, .25, .5));

		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 1, 0, 0,
						.75, .5, 1, .5));

		assertEquals(
				1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 1, 0, 0,
						5, -5, .75, .5));

		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 1, 0, 0,
						5, -5, 0, 1));

		assertEquals(
				2,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 1, 0, 0,
						5, -5, 1, 1));

		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 1, 0, 0,
						-2, 1, 5, -5));

		// 0110

		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 1, 1, 0,
						10, -5, 10, -4));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 1, 1, 0,
						10, 5, 10, 4));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 1, 1, 0,
						-5, .5, 0, .6));

		assertEquals(
				1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 1, 1, 0,
						10, -1, 11, .6));
		assertEquals(
				2,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 1, 1, 0,
						10, -1, 11, 2));
		assertEquals(
				1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 1, 1, 0,
						10, .5, 11, 2));

		assertEquals(
				-1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 1, 1, 0,
						10, 2, 11, .6));
		assertEquals(
				-2,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 1, 1, 0,
						10, 2, 11, -1));
		assertEquals(
				-1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 1, 1, 0,
						10, .6, 11, -1));

		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 1, 1, 0,
						0, .5, .25, .5));

		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 1, 1, 0,
						.75, .5, 1, .5));

		assertEquals(
				1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 1, 1, 0,
						5, -.01, .75, .5));

		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 1, 1, 0,
						20, -5, -1, 1));

		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromSegment(
						0,
						0, 1, 1, 0,
						5, 10, .25, .5));

		// 1001

		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 0, 0, 1,
						10, -5, 10, -4));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 0, 0, 1,
						10, 5, 10, 4));
		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 0, 0, 1,
						-5, .5, 0, .6));

		assertEquals(
				1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 0, 0, 1,
						10, -1, 11, .6));
		assertEquals(
				2,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 0, 0, 1,
						10, -1, 11, 2));
		assertEquals(
				1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 0, 0, 1,
						10, .5, 11, 2));

		assertEquals(
				-1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 0, 0, 1,
						10, 2, 11, .6));
		assertEquals(
				-2,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 0, 0, 1,
						10, 2, 11, -1));
		assertEquals(
				-1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 0, 0, 1,
						10, .6, 11, -1));

		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 0, 0, 1,
						0, .5, .25, .5));

		assertEquals(
				0,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 0, 0, 1,
						.75, .5, 1, .5));

		assertEquals(
				1,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 0, 0, 1,
						20, -5, .75, .5));

		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 0, 0, 1,
						20, -5, 0, 1));

		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 0, 0, 1,
						5, 10, .25, .5));

		// Others

		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromSegment(
						0,
						7, -5, 1, 1,
						4, -3, 1, 1));
		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromSegment(
						0,
						4, -3, 1, 1,
						7, -5, 1, 1));
		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 1, 4, -3,
						7, -5, 1, 1));
		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromSegment(
						0,
						4, -3, 1, 1,
						1, 1, 7, -5));
		assertEquals(
				MathConstants.SHAPE_INTERSECTS,
				Segment2afp.computeCrossingsFromSegment(
						0,
						1, 1, 4, -3,
						1, 1, 7, -5));
	}

	@Test
	public void staticComputeFarthestPointTo() {
		Point2D p;

		p = createPoint(Double.NaN, Double.NaN);
		Segment2afp.computeFarthestPointTo(0, 0, 1, 1, 0, 0, p);
		assertEpsilonEquals(1, p.getX());
		assertEpsilonEquals(1, p.getY());

		p = createPoint(Double.NaN, Double.NaN);
		Segment2afp.computeFarthestPointTo(0, 0, 1, 1, .5, .5, p);
		assertEpsilonEquals(0, p.getX());
		assertEpsilonEquals(0, p.getY());

		p = createPoint(Double.NaN, Double.NaN);
		Segment2afp.computeFarthestPointTo(0, 0, 1, 1, 1, 1, p);
		assertEpsilonEquals(0, p.getX());
		assertEpsilonEquals(0, p.getY());

		p = createPoint(Double.NaN, Double.NaN);
		Segment2afp.computeFarthestPointTo(0, 0, 1, 1, 2, 2, p);
		assertEpsilonEquals(0, p.getX());
		assertEpsilonEquals(0, p.getY());

		p = createPoint(Double.NaN, Double.NaN);
		Segment2afp.computeFarthestPointTo(0, 0, 1, 1, -2, 2, p);
		assertEpsilonEquals(1, p.getX());
		assertEpsilonEquals(1, p.getY());

		p = createPoint(Double.NaN, Double.NaN);
		Segment2afp.computeFarthestPointTo(0, 0, 1, 1, 0.1, 1.2, p);
		assertEpsilonEquals(0, p.getX());
		assertEpsilonEquals(0, p.getY());

		p = createPoint(Double.NaN, Double.NaN);
		Segment2afp.computeFarthestPointTo(0, 0, 1, 1, 10.1, -.2, p);
		assertEpsilonEquals(0, p.getX());
		assertEpsilonEquals(0, p.getY());
	}

	@Test
	public void staticComputeLineLineIntersection() {
		Point2D result;

		result = createPoint(Double.NaN, Double.NaN);
		assertTrue(Segment2afp.computeLineLineIntersection(
				1000, 1.5325000286102295, 2500, 1.5325000286102295,
				1184.001080023255, 1.6651813832907332, 1200.7014393876193, 1.372326130924099,
				result));
		assertEpsilonEquals(1191.567365026541, result.getX());
		assertEpsilonEquals(1.532500028610229, result.getY());

		result = createPoint(Double.NaN, Double.NaN);
		assertTrue(Segment2afp.computeLineLineIntersection(
				100, 50, 100, 60,
				90, 55, 2000, 55,
				result));
		assertEpsilonEquals(100, result.getX());
		assertEpsilonEquals(55, result.getY());

		result = createPoint(Double.NaN, Double.NaN);
		assertFalse(Segment2afp.computeLineLineIntersection(
				100, 50, 100, 60,
				200, 0, 200, 10,
				result));
		assertNaN(result.getX());
		assertNaN(result.getY());

		result = createPoint(Double.NaN, Double.NaN);
		assertTrue(Segment2afp.computeLineLineIntersection(
				100, -50, 100, -60,
				90, 55, 2000, 55,
				result));
		assertEpsilonEquals(100, result.getX());
		assertEpsilonEquals(55, result.getY());
	}

	@Test
	public void staticComputeLineLineIntersectionFactor() {
		assertEpsilonEquals(.1277115766843605, Segment2afp.computeLineLineIntersectionFactor(
				1000, 1.5325000286102295, 2500, 1.5325000286102295,
				1184.001080023255, 1.6651813832907332, 1200.7014393876193, 1.372326130924099));

		assertEpsilonEquals(.5, Segment2afp.computeLineLineIntersectionFactor(
				100, 50, 100, 60,
				90, 55, 2000, 55));

		assertNaN(Segment2afp.computeLineLineIntersectionFactor(
				100, 50, 100, 60,
				200, 0, 200, 10));

		assertEpsilonEquals(-10.5, Segment2afp.computeLineLineIntersectionFactor(
				100, -50, 100, -60,
				90, 55, 2000, 55));
	}

	@Test
	public void staticComputeProjectedPointOnLine() {
		assertEpsilonEquals(.3076923076923077, Segment2afp.computeProjectedPointOnLine(
				2, 1,
				0, 0, 3, -2));

		assertEpsilonEquals(.6666666666666666, Segment2afp.computeProjectedPointOnLine(
				2, 1,
				0, 0, 3, 0));

		assertEpsilonEquals(-.7, Segment2afp.computeProjectedPointOnLine(
				2, -1,
				0, 0, -3, 1));

		assertEpsilonEquals(14.4, Segment2afp.computeProjectedPointOnLine(
				2, 150,
				0, 0, -3, 1));

		assertEpsilonEquals(.5, Segment2afp.computeProjectedPointOnLine(
				.5, .5,
				0, 0, 1, 1));
	}

	@Test
	public void staticComputeRelativeDistanceLinePoint() {
		assertEpsilonEquals(-1.941450686788302, Segment2afp.computeRelativeDistanceLinePoint(
				0, 0, 3, -2,
				2, 1));
		assertEpsilonEquals(1.941450686788302, Segment2afp.computeRelativeDistanceLinePoint(
				3, -2, 0, 0,
				2, 1));

		assertEpsilonEquals(-1, Segment2afp.computeRelativeDistanceLinePoint(
				0, 0, 3, 0,
				2, 1));
		assertEpsilonEquals(1, Segment2afp.computeRelativeDistanceLinePoint(
				3, 0, 0, 0,
				2, 1));

		assertEpsilonEquals(-.3162277660168379, Segment2afp.computeRelativeDistanceLinePoint(
				0, 0, -3, 1,
				2, -1));
		assertEpsilonEquals(.3162277660168379, Segment2afp.computeRelativeDistanceLinePoint(
				-3, 1, 0, 0,
				2, -1));

		assertEpsilonEquals(142.9349502396107, Segment2afp.computeRelativeDistanceLinePoint(
				0, 0, -3, 1,
				2, 150));
		assertEpsilonEquals(-142.9349502396107, Segment2afp.computeRelativeDistanceLinePoint(
				-3, 1, 0, 0,
				2, 150));

		assertEpsilonEquals(0, Segment2afp.computeRelativeDistanceLinePoint(
				0, 0, 1, 1,
				.5, .5));
		assertEpsilonEquals(0, Segment2afp.computeRelativeDistanceLinePoint(
				1, 1, 0, 0,
				.5, .5));
	}

	@Test
	public void staticComputeSegmentSegmentIntersection() {
		Point2D result;

		result = createPoint(Double.NaN, Double.NaN);
		assertTrue(Segment2afp.computeSegmentSegmentIntersection(
				1000, 1.5325000286102295, 2500, 1.5325000286102295,
				1184.001080023255, 1.6651813832907332, 1200.7014393876193, 1.372326130924099,
				result));
		assertEpsilonEquals(1191.567365026541, result.getX());
		assertEpsilonEquals(1.532500028610229, result.getY());

		result = createPoint(Double.NaN, Double.NaN);
		assertTrue(Segment2afp.computeSegmentSegmentIntersection(
				100, 50, 100, 60,
				90, 55, 2000, 55,
				result));
		assertEpsilonEquals(100, result.getX());
		assertEpsilonEquals(55, result.getY());

		result = createPoint(Double.NaN, Double.NaN);
		assertFalse(Segment2afp.computeSegmentSegmentIntersection(
				100, 50, 100, 60,
				200, 0, 200, 10,
				result));
		assertNaN(result.getX());
		assertNaN(result.getY());

		result = createPoint(Double.NaN, Double.NaN);
		assertFalse(Segment2afp.computeSegmentSegmentIntersection(
				-100, 50, -100, 60,
				90, 55, 2000, 55,
				result));
		assertNaN(result.getX());
		assertNaN(result.getY());
	}

	@Test
	public void staticComputeSegmentSegmentIntersectionFactor() {
		assertEpsilonEquals(.1277115766843605, Segment2afp.computeSegmentSegmentIntersectionFactor(
				1000, 1.5325000286102295, 2500, 1.5325000286102295,
				1184.001080023255, 1.6651813832907332, 1200.7014393876193, 1.372326130924099));

		assertEpsilonEquals(.5, Segment2afp.computeSegmentSegmentIntersectionFactor(
				100, 50, 100, 60,
				90, 55, 2000, 55));

		assertNaN(Segment2afp.computeSegmentSegmentIntersectionFactor(
				100, 50, 100, 60,
				200, 0, 200, 10));

		assertNaN(Segment2afp.computeSegmentSegmentIntersectionFactor(
				100, -50, 100, -60,
				90, 55, 2000, 55));
	}

	@Test
	public void staticComputeSideLinePoint() {
		assertEquals(0, Segment2afp.computeSideLinePoint(0, 0, 1, 1, 0, 0, 0.1));
		assertEquals(0, Segment2afp.computeSideLinePoint(0, 0, 1, 1, 1, 1, 0.1));
		assertEquals(0, Segment2afp.computeSideLinePoint(0, 0, 1, 1, .25, .25, 0.1));
		assertEquals(1, Segment2afp.computeSideLinePoint(0, 0, 1, 1, 0.2, 0, 0.1));
		assertEquals(1, Segment2afp.computeSideLinePoint(0, 0, 1, 1, 120, 0, 0.1));
		assertEquals(0, Segment2afp.computeSideLinePoint(0, 0, 1, 1, -20.05, -20, 0.1));
		assertEquals(-1, Segment2afp.computeSideLinePoint(0, 0, 1, 1, 0, 0.2, 0.1));
		assertEquals(-1, Segment2afp.computeSideLinePoint(0, 0, 1, 1, 0, 120, 0.1));
	}

	@Test
	public void staticGetNoSegmentSegmentWithEndsIntersection() {
		assertSame(UncertainIntersection.PERHAPS, Segment2afp.getNoSegmentSegmentWithEndsIntersection(
				0, 0, 1, 1,
				0, .5, 1, .5));
		assertSame(UncertainIntersection.PERHAPS, Segment2afp.getNoSegmentSegmentWithEndsIntersection(
				0, 0, 1, 1,
				0, 0, 1, 1));
		assertSame(UncertainIntersection.PERHAPS, Segment2afp.getNoSegmentSegmentWithEndsIntersection(
				0, 0, 1, 1,
				0, 0, 2, 2));
		assertSame(UncertainIntersection.PERHAPS, Segment2afp.getNoSegmentSegmentWithEndsIntersection(
				0, 0, 1, 1,
				0, 0, .5, .5));
		assertSame(UncertainIntersection.PERHAPS, Segment2afp.getNoSegmentSegmentWithEndsIntersection(
				0, 0, 1, 1,
				-3, -3, .5, .5));
		assertSame(UncertainIntersection.PERHAPS, Segment2afp.getNoSegmentSegmentWithEndsIntersection(
				0, 0, 1, 1,
				-3, -3, 0, 0));
		assertSame(UncertainIntersection.NO, Segment2afp.getNoSegmentSegmentWithEndsIntersection(
				0, 0, 1, 1,
				-3, -3, -1, -1));

		assertSame(UncertainIntersection.PERHAPS, Segment2afp.getNoSegmentSegmentWithEndsIntersection(
				0, 0, 1, 1,
				-3, 0, 4, 0));

		assertSame(UncertainIntersection.PERHAPS, Segment2afp.getNoSegmentSegmentWithEndsIntersection(
				0, 0, 1, 1,
				-3, -1, 4, -1));

		assertSame(UncertainIntersection.NO, Segment2afp.getNoSegmentSegmentWithEndsIntersection(
				0, 0, 1, 1,
				-3, -1, -1, -1));

		assertSame(UncertainIntersection.NO, Segment2afp.getNoSegmentSegmentWithEndsIntersection(
				0, 0, 1, 1,
				-3, 0, -2, 1));

		assertSame(UncertainIntersection.NO, Segment2afp.getNoSegmentSegmentWithEndsIntersection(
				0, 0, 1, 1,
				10, 0, 9, -1));

		assertSame(UncertainIntersection.PERHAPS, Segment2afp.getNoSegmentSegmentWithEndsIntersection(
				7, -5, 1, 1,
				4, -3, 1, 1));
	}

	@Test
	public void staticGetNoSegmentSegmentWithoutEndsIntersection() {
		assertSame(UncertainIntersection.PERHAPS, Segment2afp.getNoSegmentSegmentWithoutEndsIntersection(
				0, 0, 1, 1,
				0, .5, 1, .5));
		assertSame(UncertainIntersection.PERHAPS, Segment2afp.getNoSegmentSegmentWithoutEndsIntersection(
				0, 0, 1, 1,
				0, 0, 1, 1));
		assertSame(UncertainIntersection.PERHAPS, Segment2afp.getNoSegmentSegmentWithEndsIntersection(
				0, 0, 1, 1,
				0, 0, 2, 2));
		assertSame(UncertainIntersection.PERHAPS, Segment2afp.getNoSegmentSegmentWithoutEndsIntersection(
				0, 0, 1, 1,
				0, 0, .5, .5));
		assertSame(UncertainIntersection.PERHAPS, Segment2afp.getNoSegmentSegmentWithoutEndsIntersection(
				0, 0, 1, 1,
				-3, -3, .5, .5));
		assertSame(UncertainIntersection.NO, Segment2afp.getNoSegmentSegmentWithoutEndsIntersection(
				0, 0, 1, 1,
				-3, -3, 0, 0));
		assertSame(UncertainIntersection.NO, Segment2afp.getNoSegmentSegmentWithoutEndsIntersection(
				0, 0, 1, 1,
				-3, -3, -1, -1));

		assertSame(UncertainIntersection.PERHAPS, Segment2afp.getNoSegmentSegmentWithoutEndsIntersection(
				0, 0, 1, 1,
				-3, 0, 4, 0));

		assertSame(UncertainIntersection.PERHAPS, Segment2afp.getNoSegmentSegmentWithoutEndsIntersection(
				0, 0, 1, 1,
				-3, -1, 4, -1));

		assertSame(UncertainIntersection.NO, Segment2afp.getNoSegmentSegmentWithoutEndsIntersection(
				0, 0, 1, 1,
				-3, -1, -1, -1));

		assertSame(UncertainIntersection.NO, Segment2afp.getNoSegmentSegmentWithoutEndsIntersection(
				0, 0, 1, 1,
				-3, 0, -2, 1));

		assertSame(UncertainIntersection.NO, Segment2afp.getNoSegmentSegmentWithoutEndsIntersection(
				0, 0, 1, 1,
				10, 0, 9, -1));

		assertSame(UncertainIntersection.NO, Segment2afp.getNoSegmentSegmentWithoutEndsIntersection(
				7, -5, 1, 1,
				4, -3, 1, 1));
	}

	@Test
	public void staticComputeDistanceLinePoint() {
		assertEpsilonEquals(1.941450686788302, Segment2afp.computeDistanceLinePoint(
				0, 0, 3, -2,
				2, 1));
		assertEpsilonEquals(1.941450686788302, Segment2afp.computeDistanceLinePoint(
				3, -2, 0, 0,
				2, 1));

		assertEpsilonEquals(1, Segment2afp.computeDistanceLinePoint(
				0, 0, 3, 0,
				2, 1));
		assertEpsilonEquals(1, Segment2afp.computeDistanceLinePoint(
				3, 0, 0, 0,
				2, 1));

		assertEpsilonEquals(.3162277660168379, Segment2afp.computeDistanceLinePoint(
				0, 0, -3, 1,
				2, -1));
		assertEpsilonEquals(.3162277660168379, Segment2afp.computeDistanceLinePoint(
				-3, 1, 0, 0,
				2, -1));

		assertEpsilonEquals(142.9349502396107, Segment2afp.computeDistanceLinePoint(
				0, 0, -3, 1,
				2, 150));
		assertEpsilonEquals(142.9349502396107, Segment2afp.computeDistanceLinePoint(
				-3, 1, 0, 0,
				2, 150));

		assertEpsilonEquals(0, Segment2afp.computeDistanceLinePoint(
				0, 0, 1, 1,
				.5, .5));
		assertEpsilonEquals(0, Segment2afp.computeDistanceLinePoint(
				1, 1, 0, 0,
				.5, .5));
	}

	@Test
	public void staticComputeDistanceSegmentPoint() {
		assertEpsilonEquals(1.941450686788302, Segment2afp.computeDistanceSegmentPoint(
				0, 0, 3, -2,
				2, 1));
		assertEpsilonEquals(1.941450686788302, Segment2afp.computeDistanceSegmentPoint(
				3, -2, 0, 0,
				2, 1));

		assertEpsilonEquals(1, Segment2afp.computeDistanceSegmentPoint(
				0, 0, 3, 0,
				2, 1));
		assertEpsilonEquals(1, Segment2afp.computeDistanceSegmentPoint(
				3, 0, 0, 0,
				2, 1));

		assertEpsilonEquals(2.23606797749979, Segment2afp.computeDistanceSegmentPoint(
				0, 0, -3, 1,
				2, -1));
		assertEpsilonEquals(2.23606797749979, Segment2afp.computeDistanceSegmentPoint(
				-3, 1, 0, 0,
				2, -1));

		assertEpsilonEquals(149.0838690133845, Segment2afp.computeDistanceSegmentPoint(
				0, 0, -3, 1,
				2, 150));
		assertEpsilonEquals(149.0838690133845, Segment2afp.computeDistanceSegmentPoint(
				-3, 1, 0, 0,
				2, 150));

		assertEpsilonEquals(0, Segment2afp.computeDistanceSegmentPoint(
				0, 0, 1, 1,
				.5, .5));
		assertEpsilonEquals(0, Segment2afp.computeDistanceSegmentPoint(
				1, 1, 0, 0,
				.5, .5));
	}

	@Test
	public void staticComputeDistanceSquaredLinePoint() {
		assertEpsilonEquals(3.769230769230769, Segment2afp.computeDistanceSquaredLinePoint(
				0, 0, 3, -2,
				2, 1));
		assertEpsilonEquals(3.769230769230769, Segment2afp.computeDistanceSquaredLinePoint(
				3, -2, 0, 0,
				2, 1));

		assertEpsilonEquals(1, Segment2afp.computeDistanceSquaredLinePoint(
				0, 0, 3, 0,
				2, 1));
		assertEpsilonEquals(1, Segment2afp.computeDistanceSquaredLinePoint(
				3, 0, 0, 0,
				2, 1));

		assertEpsilonEquals(.09999999999999996, Segment2afp.computeDistanceSquaredLinePoint(
				0, 0, -3, 1,
				2, -1));
		assertEpsilonEquals(.09999999999999996, Segment2afp.computeDistanceSquaredLinePoint(
				-3, 1, 0, 0,
				2, -1));

		assertEpsilonEquals(20430.39999999979, Segment2afp.computeDistanceSquaredLinePoint(
				0, 0, -3, 1,
				2, 150));
		assertEpsilonEquals(20430.39999999979, Segment2afp.computeDistanceSquaredLinePoint(
				-3, 1, 0, 0,
				2, 150));

		assertEpsilonEquals(0, Segment2afp.computeDistanceSquaredLinePoint(
				0, 0, 1, 1,
				.5, .5));
		assertEpsilonEquals(0, Segment2afp.computeDistanceSquaredLinePoint(
				1, 1, 0, 0,
				.5, .5));
	}

	@Test
	public void staticComputeDistanceSquaredSegmentPoint() {
		assertEpsilonEquals(3.769230769230769, Segment2afp.computeDistanceSquaredSegmentPoint(
				0, 0, 3, -2,
				2, 1));
		assertEpsilonEquals(3.769230769230769, Segment2afp.computeDistanceSquaredSegmentPoint(
				3, -2, 0, 0,
				2, 1));

		assertEpsilonEquals(1, Segment2afp.computeDistanceSquaredSegmentPoint(
				0, 0, 3, 0,
				2, 1));
		assertEpsilonEquals(1, Segment2afp.computeDistanceSquaredSegmentPoint(
				3, 0, 0, 0,
				2, 1));

		assertEpsilonEquals(5, Segment2afp.computeDistanceSquaredSegmentPoint(
				0, 0, -3, 1,
				2, -1));
		assertEpsilonEquals(5, Segment2afp.computeDistanceSquaredSegmentPoint(
				-3, 1, 0, 0,
				2, -1));

		assertEpsilonEquals(22225.99999999998, Segment2afp.computeDistanceSquaredSegmentPoint(
				0, 0, -3, 1,
				2, 150));
		assertEpsilonEquals(22225.99999999998, Segment2afp.computeDistanceSquaredSegmentPoint(
				-3, 1, 0, 0,
				2, 150));

		assertEpsilonEquals(0, Segment2afp.computeDistanceSquaredSegmentPoint(
				0, 0, 1, 1,
				.5, .5));
		assertEpsilonEquals(0, Segment2afp.computeDistanceSquaredSegmentPoint(
				1, 1, 0, 0,
				.5, .5));
	}

	@Test
	public void staticInterpolate() {
		Point2D result;

		result = createPoint(Double.NaN, Double.NaN);
		Segment2afp.interpolate(1., 2., 3., 4., 0., result);
		assertEpsilonEquals(1, result.getX());
		assertEpsilonEquals(2, result.getY());

		result = createPoint(Double.NaN, Double.NaN);
		Segment2afp.interpolate(1., 2., 3., 4., .25, result);
		assertEpsilonEquals(1.5, result.getX());
		assertEpsilonEquals(2.5, result.getY());

		result = createPoint(Double.NaN, Double.NaN);
		Segment2afp.interpolate(1., 2., 3., 4., .5, result);
		assertEpsilonEquals(2, result.getX());
		assertEpsilonEquals(3., result.getY());

		result = createPoint(Double.NaN, Double.NaN);
		Segment2afp.interpolate(1., 2., 3., 4., .75, result);
		assertEpsilonEquals(2.5, result.getX());
		assertEpsilonEquals(3.5, result.getY());

		result = createPoint(Double.NaN, Double.NaN);
		Segment2afp.interpolate(1., 2., 3., 4., 1., result);
		assertEpsilonEquals(3, result.getX());
		assertEpsilonEquals(4, result.getY());
	}

	@Test
	public void staticIntersectsLineLine() {
		assertTrue(Segment2afp.intersectsLineLine(
				0, 0, 1, 1,
				0, 0, 1, 1));
		assertTrue(Segment2afp.intersectsLineLine(
				0, 0, 1, 1,
				0, 0, 2, 2));
		assertTrue(Segment2afp.intersectsLineLine(
				0, 0, 1, 1,
				0, 0, .5, .5));
		assertTrue(Segment2afp.intersectsLineLine(
				0, 0, 1, 1,
				-3, -3, .5, .5));
		assertTrue(Segment2afp.intersectsLineLine(
				0, 0, 1, 1,
				-3, -3, 0, 0));
		assertTrue(Segment2afp.intersectsLineLine(
				0, 0, 1, 1,
				-3, -3, -1, -1));

		assertTrue(Segment2afp.intersectsLineLine(
				0, 0, 1, 1,
				-3, 0, 4, 0));

		assertFalse(Segment2afp.intersectsLineLine(
				0, 0, 1, 1,
				-3, 0, -2, 1));

		assertFalse(Segment2afp.intersectsLineLine(
				0, 0, 1, 1,
				10, 0, 9, -1));
	}

	@Test
	public void staticIntersectsSegmentLine() {
		assertTrue(Segment2afp.intersectsSegmentLine(
				0, 0, 1, 1,
				0, 0, 1, 1));
		assertTrue(Segment2afp.intersectsSegmentLine(
				0, 0, 1, 1,
				0, 0, 2, 2));
		assertTrue(Segment2afp.intersectsSegmentLine(
				0, 0, 1, 1,
				0, 0, .5, .5));
		assertTrue(Segment2afp.intersectsSegmentLine(
				0, 0, 1, 1,
				-3, -3, .5, .5));
		assertTrue(Segment2afp.intersectsSegmentLine(
				0, 0, 1, 1,
				-3, -3, 0, 0));
		assertTrue(Segment2afp.intersectsSegmentLine(
				0, 0, 1, 1,
				-3, -3, -1, -1));

		assertTrue(Segment2afp.intersectsSegmentLine(
				0, 0, 1, 1,
				-3, 0, 4, 0));

		assertFalse(Segment2afp.intersectsSegmentLine(
				0, 0, 1, 1,
				-3, 0, -2, 1));

		assertFalse(Segment2afp.intersectsSegmentLine(
				0, 0, 1, 1,
				10, 0, 9, -1));
	}

	@Test
	public void staticIntersectsSegmentSegmentWithEnds() {
		assertTrue(Segment2afp.intersectsSegmentSegmentWithEnds(
				0, 0, 1, 1,
				0, .5, 1, .5));
		assertTrue(Segment2afp.intersectsSegmentSegmentWithEnds(
				0, 0, 1, 1,
				0, 0, 1, 1));
		assertTrue(Segment2afp.intersectsSegmentSegmentWithEnds(
				0, 0, 1, 1,
				0, 0, 2, 2));
		assertTrue(Segment2afp.intersectsSegmentSegmentWithEnds(
				0, 0, 1, 1,
				0, 0, .5, .5));
		assertTrue(Segment2afp.intersectsSegmentSegmentWithEnds(
				0, 0, 1, 1,
				-3, -3, .5, .5));
		assertTrue(Segment2afp.intersectsSegmentSegmentWithEnds(
				0, 0, 1, 1,
				-3, -3, 0, 0));
		assertFalse(Segment2afp.intersectsSegmentSegmentWithEnds(
				0, 0, 1, 1,
				-3, -3, -1, -1));

		assertTrue(Segment2afp.intersectsSegmentSegmentWithEnds(
				0, 0, 1, 1,
				-3, 0, 4, 0));

		assertFalse(Segment2afp.intersectsSegmentSegmentWithEnds(
				0, 0, 1, 1,
				-3, -1, 4, -1));

		assertFalse(Segment2afp.intersectsSegmentSegmentWithEnds(
				0, 0, 1, 1,
				-3, -1, -1, -1));

		assertFalse(Segment2afp.intersectsSegmentSegmentWithEnds(
				0, 0, 1, 1,
				-3, 0, -2, 1));

		assertFalse(Segment2afp.intersectsSegmentSegmentWithEnds(
				0, 0, 1, 1,
				10, 0, 9, -1));

		assertTrue(
				Segment2afp.intersectsSegmentSegmentWithEnds(
						7, -5, 1, 1,
						4, -3, 1, 1));
	}

	@Test
	public void staticIntersectsSegmentSegmentWithoutEnds() {
		assertTrue(Segment2afp.intersectsSegmentSegmentWithoutEnds(
				0, 0, 1, 1,
				0, .5, 1, .5));
		assertTrue(Segment2afp.intersectsSegmentSegmentWithoutEnds(
				0, 0, 1, 1,
				0, 0, 1, 1));
		assertTrue(Segment2afp.intersectsSegmentSegmentWithoutEnds(
				0, 0, 1, 1,
				0, 0, 2, 2));
		assertTrue(Segment2afp.intersectsSegmentSegmentWithoutEnds(
				0, 0, 1, 1,
				0, 0, .5, .5));
		assertTrue(Segment2afp.intersectsSegmentSegmentWithoutEnds(
				0, 0, 1, 1,
				-3, -3, .5, .5));
		assertFalse(Segment2afp.intersectsSegmentSegmentWithoutEnds(
				0, 0, 1, 1,
				-3, -3, 0, 0));
		assertFalse(Segment2afp.intersectsSegmentSegmentWithoutEnds(
				0, 0, 1, 1,
				-3, -3, -1, -1));

		assertFalse(Segment2afp.intersectsSegmentSegmentWithoutEnds(
				0, 0, 1, 1,
				-3, 0, 4, 0));

		assertFalse(Segment2afp.intersectsSegmentSegmentWithoutEnds(
				0, 0, 1, 1,
				-3, -1, 4, -1));

		assertFalse(Segment2afp.intersectsSegmentSegmentWithoutEnds(
				0, 0, 1, 1,
				-3, -1, -1, -1));

		assertFalse(Segment2afp.intersectsSegmentSegmentWithoutEnds(
				0, 0, 1, 1,
				-3, 0, -2, 1));

		assertFalse(Segment2afp.intersectsSegmentSegmentWithoutEnds(
				0, 0, 1, 1,
				10, 0, 9, -1));

		assertFalse(
				Segment2afp.intersectsSegmentSegmentWithoutEnds(
						7, -5, 1, 1,
						4, -3, 1, 1));
	}

	@Test
	public void staticIsCollinearLines() {
		assertTrue(Segment2afp.isCollinearLines(0, 0, 1, 1, 0, 0, 1, 1));
		assertTrue(Segment2afp.isCollinearLines(0, 0, 1, 1, 1, 1, 0, 0));
		assertTrue(Segment2afp.isCollinearLines(0, 0, 1, 1, 0, 0, -1, -1));
		assertTrue(Segment2afp.isCollinearLines(0, 0, 1, 1, -2, -2, -3, -3));
		assertFalse(Segment2afp.isCollinearLines(0, 0, 1, 1, 5, 0, 6, 1));
		assertFalse(Segment2afp.isCollinearLines(0, 0, 1, 1, 154, -124, -2, 457));
	}

	@Test
	public void staticIsParallelLines() {
		assertTrue(Segment2afp.isParallelLines(0, 0, 1, 1, 0, 0, 1, 1));
		assertTrue(Segment2afp.isParallelLines(0, 0, 1, 1, 1, 1, 0, 0));
		assertTrue(Segment2afp.isParallelLines(0, 0, 1, 1, 0, 0, -1, -1));
		assertTrue(Segment2afp.isParallelLines(0, 0, 1, 1, -2, -2, -3, -3));
		assertTrue(Segment2afp.isParallelLines(0, 0, 1, 1, 5, 0, 6, 1));
		assertFalse(Segment2afp.isParallelLines(0, 0, 1, 1, 154, -124, -2, 457));
	}

	@Test
	public void staticIsPointClosedToLine() {
		assertTrue(Segment2afp.isPointClosedToLine(0, 0, 1, 1, 0, 0, 0.1));
		assertTrue(Segment2afp.isPointClosedToLine(0, 0, 1, 1, 1, 1, 0.1));
		assertTrue(Segment2afp.isPointClosedToLine(0, 0, 1, 1, .25, .25, 0.1));
		assertFalse(Segment2afp.isPointClosedToLine(0, 0, 1, 1, 0.2, 0, 0.1));
		assertFalse(Segment2afp.isPointClosedToLine(0, 0, 1, 1, 120, 0, 0.1));
		assertTrue(Segment2afp.isPointClosedToLine(0, 0, 1, 1, -20.05, -20, 0.1));
	}

	@Test
	public void staticIsPointClosedToSegment() {
		assertTrue(Segment2afp.isPointClosedToSegment(0, 0, 1, 1, 0, 0, 0.1));
		assertTrue(Segment2afp.isPointClosedToSegment(0, 0, 1, 1, 1, 1, 0.1));
		assertTrue(Segment2afp.isPointClosedToSegment(0, 0, 1, 1, .25, .25, 0.1));
		assertFalse(Segment2afp.isPointClosedToSegment(0, 0, 1, 1, 0.2, 0, 0.1));
		assertFalse(Segment2afp.isPointClosedToSegment(0, 0, 1, 1, 120, 0, 0.1));
		assertFalse(Segment2afp.isPointClosedToSegment(0, 0, 1, 1, -20.05, -20, 0.1));
	}

	@Test
	@Override
	public void testClone() {
		T clone = this.shape.clone();
		assertNotNull(clone);
		assertNotSame(this.shape, clone);
		assertEquals(this.shape.getClass(), clone.getClass());
		assertEpsilonEquals(0, clone.getX1());
		assertEpsilonEquals(0, clone.getY1());
		assertEpsilonEquals(1, clone.getX2());
		assertEpsilonEquals(1, clone.getY2());
	}

	@Test
	@Override
	public void equalsObject() {
		assertFalse(this.shape.equals(null));
		assertFalse(this.shape.equals(new Object()));
		assertFalse(this.shape.equals(createSegment(0, 0, 5, 0)));
		assertFalse(this.shape.equals(createSegment(0, 0, 2, 2)));
		assertFalse(this.shape.equals(createCircle(5, 8, 6)));
		assertTrue(this.shape.equals(this.shape));
		assertTrue(this.shape.equals(createSegment(0, 0, 1, 1)));
	}

	@Test
	@Override
	public void equalsObject_withPathIterator() {
		assertFalse(this.shape.equals(createSegment(0, 0, 5, 0).getPathIterator()));
		assertFalse(this.shape.equals(createSegment(0, 0, 2, 2).getPathIterator()));
		assertFalse(this.shape.equals(createCircle(5, 8, 6).getPathIterator()));
		assertTrue(this.shape.equals(this.shape.getPathIterator()));
		assertTrue(this.shape.equals(createSegment(0, 0, 1, 1).getPathIterator()));
	}

	@Test
	@Override
	public void equalsToPathIterator() {
		assertFalse(this.shape.equalsToPathIterator((PathIterator2ai) null));
		assertFalse(this.shape.equalsToPathIterator(createSegment(0, 0, 5, 0).getPathIterator()));
		assertFalse(this.shape.equalsToPathIterator(createSegment(0, 0, 2, 2).getPathIterator()));
		assertFalse(this.shape.equalsToPathIterator(createCircle(5, 8, 6).getPathIterator()));
		assertTrue(this.shape.equalsToPathIterator(this.shape.getPathIterator()));
		assertTrue(this.shape.equalsToPathIterator(createSegment(0, 0, 1, 1).getPathIterator()));
	}

	@Test
	@Override
	public void equalsToShape() {
		assertFalse(this.shape.equalsToShape(null));
		assertFalse(this.shape.equalsToShape((T) createSegment(0, 0, 5, 0)));
		assertFalse(this.shape.equalsToShape((T) createSegment(0, 0, 2, 2)));
		assertTrue(this.shape.equalsToShape(this.shape));
		assertTrue(this.shape.equalsToShape((T) createSegment(0, 0, 1, 1)));
	}

	@Test
	@Override
	public void isEmpty() {
		assertFalse(this.shape.isEmpty());
		this.shape.clear();
		assertTrue(this.shape.isEmpty());
	}

	@Test
	@Override
	public void clear() {
		this.shape.clear();
		assertEpsilonEquals(0, this.shape.getX1());
		assertEpsilonEquals(0, this.shape.getY1());
		assertEpsilonEquals(0, this.shape.getX2());
		assertEpsilonEquals(0, this.shape.getY2());
	}

	@Test
	public void getP1() {
		Point2D p = this.shape.getP1();
		assertNotNull(p);
		assertEpsilonEquals(0, p.getX());
		assertEpsilonEquals(0, p.getY());
	}

	@Test
	public void getP2() {
		Point2D p = this.shape.getP2();
		assertNotNull(p);
		assertEpsilonEquals(1, p.getX());
		assertEpsilonEquals(1, p.getY());
	}

	@Test
	public void setP1DoubleDouble() {
		this.shape.setP1(123.456, -789.159);
		assertEpsilonEquals(123.456, this.shape.getX1());
		assertEpsilonEquals(-789.159, this.shape.getY1());
		assertEpsilonEquals(1, this.shape.getX2());
		assertEpsilonEquals(1, this.shape.getY2());
	}

	@Test
	public void setP1Point2D() {
		this.shape.setP1(createPoint(123.456, -789.159));
		assertEpsilonEquals(123.456, this.shape.getX1());
		assertEpsilonEquals(-789.159, this.shape.getY1());
		assertEpsilonEquals(1, this.shape.getX2());
		assertEpsilonEquals(1, this.shape.getY2());
	}

	@Test
	public void setP2DoubleDouble() {
		this.shape.setP2(123.456, -789.159);
		assertEpsilonEquals(0, this.shape.getX1());
		assertEpsilonEquals(0, this.shape.getY1());
		assertEpsilonEquals(123.456, this.shape.getX2());
		assertEpsilonEquals(-789.159, this.shape.getY2());
	}

	@Test
	public void setP2Point2D() {
		this.shape.setP2(createPoint(123.456, -789.159));
		assertEpsilonEquals(0, this.shape.getX1());
		assertEpsilonEquals(0, this.shape.getY1());
		assertEpsilonEquals(123.456, this.shape.getX2());
		assertEpsilonEquals(-789.159, this.shape.getY2());
	}

	@Test
	public void getX1() {
		assertEpsilonEquals(0, this.shape.getX1());
	}

	@Test
	public void getX2() {
		assertEpsilonEquals(1, this.shape.getX2());
	}

	@Test
	public void getY1() {
		assertEpsilonEquals(0, this.shape.getY1());
	}

	@Test
	public void getY2() {
		assertEpsilonEquals(1, this.shape.getY2());
	}

	@Test
	public void length() {
		assertEpsilonEquals(Math.sqrt(2), this.shape.getLength());
	}

	@Test
	public void lengthSquared() {
		assertEpsilonEquals(2, this.shape.getLengthSquared());
	}

	@Test
	public void setDoubleDoubleDoubleDouble() {
		this.shape.set(123.456, 456.789, 789.123, 159.753);
		assertEpsilonEquals(123.456, this.shape.getX1());
		assertEpsilonEquals(456.789, this.shape.getY1());
		assertEpsilonEquals(789.123, this.shape.getX2());
		assertEpsilonEquals(159.753, this.shape.getY2());
	}

	@Test
	public void setPoint2DPoint2D() {
		this.shape.set(createPoint(123.456, 456.789), createPoint(789.123, 159.753));
		assertEpsilonEquals(123.456, this.shape.getX1());
		assertEpsilonEquals(456.789, this.shape.getY1());
		assertEpsilonEquals(789.123, this.shape.getX2());
		assertEpsilonEquals(159.753, this.shape.getY2());
	}

	@Test
	public void setX1() {
		this.shape.setX1(123.456);
		assertEpsilonEquals(123.456, this.shape.getX1());
		assertEpsilonEquals(0, this.shape.getY1());
		assertEpsilonEquals(1, this.shape.getX2());
		assertEpsilonEquals(1, this.shape.getY2());
	}

	@Test
	public void setX2() {
		this.shape.setX2(123.456);
		assertEpsilonEquals(0, this.shape.getX1());
		assertEpsilonEquals(0, this.shape.getY1());
		assertEpsilonEquals(123.456, this.shape.getX2());
		assertEpsilonEquals(1, this.shape.getY2());
	}

	@Test
	public void setY1() {
		this.shape.setY1(123.456);
		assertEpsilonEquals(0, this.shape.getX1());
		assertEpsilonEquals(123.456, this.shape.getY1());
		assertEpsilonEquals(1, this.shape.getX2());
		assertEpsilonEquals(1, this.shape.getY2());
	}

	@Test
	public void setY2() {
		this.shape.setY2(123.456);
		assertEpsilonEquals(0, this.shape.getX1());
		assertEpsilonEquals(0, this.shape.getY1());
		assertEpsilonEquals(1, this.shape.getX2());
		assertEpsilonEquals(123.456, this.shape.getY2());
	}

	@Test
	public void transformTransform2D() {
		Segment2afp s;
		Transform2D tr;

		tr = new Transform2D();
		s = this.shape.clone();
		s.transform(tr);
		assertEpsilonEquals(0, s.getX1());
		assertEpsilonEquals(0, s.getY1());
		assertEpsilonEquals(1, s.getX2());
		assertEpsilonEquals(1, s.getY2());

		tr = new Transform2D();
		tr.setTranslation(3.4, 4.5);
		s = this.shape.clone();
		s.transform(tr);
		assertEpsilonEquals(3.4, s.getX1());
		assertEpsilonEquals(4.5, s.getY1());
		assertEpsilonEquals(4.4, s.getX2());
		assertEpsilonEquals(5.5, s.getY2());

		tr = new Transform2D();
		tr.setRotation(MathConstants.PI);
		s = this.shape.clone();
		s.transform(tr);
		assertEpsilonEquals(0, s.getX1());
		assertEpsilonEquals(0, s.getY1());
		assertEpsilonEquals(-1, s.getX2());
		assertEpsilonEquals(-1, s.getY2());

		tr = new Transform2D();
		tr.setRotation(MathConstants.QUARTER_PI);
		s = this.shape.clone();
		s.transform(tr);
		assertEpsilonEquals(0, s.getX1());
		assertEpsilonEquals(0, s.getX2());
		assertEpsilonEquals(1.414213562, s.getY2());
	}

	@Test
	public void clipToRectangle() {
		this.shape.set(20, 45, 43, 15);
		assertTrue(this.shape.clipToRectangle(10, 12, 50, 49));
		assertEpsilonEquals(20, this.shape.getX1());
		assertEpsilonEquals(45, this.shape.getY1());
		assertEpsilonEquals(43, this.shape.getX2());
		assertEpsilonEquals(15, this.shape.getY2());

		this.shape.set(20, 55, 43, 15);
		assertTrue(this.shape.clipToRectangle(10, 12, 50, 49));
		assertEpsilonEquals(23.45, this.shape.getX1());
		assertEpsilonEquals(49.0, this.shape.getY1());
		assertEpsilonEquals(43, this.shape.getX2());
		assertEpsilonEquals(15, this.shape.getY2());

		this.shape.set(20, 0, 43, 15);
		assertTrue(this.shape.clipToRectangle(10, 12, 50, 49));
		assertEpsilonEquals(38.4, this.shape.getX1());
		assertEpsilonEquals(12.0, this.shape.getY1());
		assertEpsilonEquals(43, this.shape.getX2());
		assertEpsilonEquals(15, this.shape.getY2());

		this.shape.set(0, 45, 43, 15);
		assertTrue(this.shape.clipToRectangle(10, 12, 50, 49));
		assertEpsilonEquals(10, this.shape.getX1());
		assertEpsilonEquals(38.02326, this.shape.getY1());
		assertEpsilonEquals(43, this.shape.getX2());
		assertEpsilonEquals(15, this.shape.getY2());

		this.shape.set(20, 45, 60, 15);
		assertTrue(this.shape.clipToRectangle(10, 12, 50, 49));
		assertEpsilonEquals(20, this.shape.getX1());
		assertEpsilonEquals(45, this.shape.getY1());
		assertEpsilonEquals(50, this.shape.getX2());
		assertEpsilonEquals(22.5, this.shape.getY2());

		this.shape.set(5, 45, 30, 55);
		assertTrue(this.shape.clipToRectangle(10, 12, 50, 49));
		assertEpsilonEquals(10, this.shape.getX1());
		assertEpsilonEquals(47, this.shape.getY1());
		assertEpsilonEquals(15, this.shape.getX2());
		assertEpsilonEquals(49, this.shape.getY2());

		this.shape.set(40, 55, 60, 15);
		assertTrue(this.shape.clipToRectangle(10, 12, 50, 49));
		assertEpsilonEquals(43, this.shape.getX1());
		assertEpsilonEquals(49, this.shape.getY1());
		assertEpsilonEquals(50, this.shape.getX2());
		assertEpsilonEquals(35, this.shape.getY2());

		this.shape.set(40, 0, 60, 40);
		assertTrue(this.shape.clipToRectangle(10, 12, 50, 49));
		assertEpsilonEquals(46, this.shape.getX1());
		assertEpsilonEquals(12, this.shape.getY1());
		assertEpsilonEquals(50, this.shape.getX2());
		assertEpsilonEquals(20, this.shape.getY2());

		this.shape.set(0, 40, 20, 0);
		assertTrue(this.shape.clipToRectangle(10, 12, 50, 49));
		assertEpsilonEquals(10, this.shape.getX1());
		assertEpsilonEquals(20, this.shape.getY1());
		assertEpsilonEquals(14, this.shape.getX2());
		assertEpsilonEquals(12, this.shape.getY2());

		this.shape.set(0, 45, 100, 15);
		assertTrue(this.shape.clipToRectangle(10, 12, 50, 49));
		assertEpsilonEquals(10, this.shape.getX1());
		assertEpsilonEquals(42, this.shape.getY1());
		assertEpsilonEquals(50, this.shape.getX2());
		assertEpsilonEquals(30, this.shape.getY2());

		this.shape.set(20, 100, 43, 0);
		assertTrue(this.shape.clipToRectangle(10, 12, 50, 49));
		assertEpsilonEquals(31.73, this.shape.getX1());
		assertEpsilonEquals(49, this.shape.getY1());
		assertEpsilonEquals(40.24, this.shape.getX2());
		assertEpsilonEquals(12, this.shape.getY2());

		this.shape.set(20, 100, 43, 101);
		assertFalse(this.shape.clipToRectangle(10, 12, 50, 49));
		assertEpsilonEquals(20, this.shape.getX1());
		assertEpsilonEquals(100, this.shape.getY1());
		assertEpsilonEquals(43, this.shape.getX2());
		assertEpsilonEquals(101, this.shape.getY2());

		this.shape.set(100, 45, 102, 15);
		assertFalse(this.shape.clipToRectangle(10, 12, 50, 49));
		assertEpsilonEquals(100, this.shape.getX1());
		assertEpsilonEquals(45, this.shape.getY1());
		assertEpsilonEquals(102, this.shape.getX2());
		assertEpsilonEquals(15, this.shape.getY2());

		this.shape.set(20, 0, 43, -2);
		assertFalse(this.shape.clipToRectangle(10, 12, 50, 49));
		assertEpsilonEquals(20, this.shape.getX1());
		assertEpsilonEquals(0, this.shape.getY1());
		assertEpsilonEquals(43, this.shape.getX2());
		assertEpsilonEquals(-2, this.shape.getY2());

		this.shape.set(-100, 45, -48, 15);
		assertFalse(this.shape.clipToRectangle(10, 12, 50, 49));
		assertEpsilonEquals(-100, this.shape.getX1());
		assertEpsilonEquals(45, this.shape.getY1());
		assertEpsilonEquals(-48, this.shape.getX2());
		assertEpsilonEquals(15, this.shape.getY2());

		this.shape.set(-100, 60, -98, 61);
		assertFalse(this.shape.clipToRectangle(10, 12, 50, 49));
		assertEpsilonEquals(-100, this.shape.getX1());
		assertEpsilonEquals(60, this.shape.getY1());
		assertEpsilonEquals(-98, this.shape.getX2());
		assertEpsilonEquals(61, this.shape.getY2());
	}

	@Override
	public void containsDoubleDouble() {
		assertTrue(this.shape.contains(0, 0));
		assertTrue(this.shape.contains(.5, .5));
		assertTrue(this.shape.contains(1, 1));
		assertFalse(this.shape.contains(2.3, 4.5));
		assertFalse(this.shape.contains(2, 2));
	}

	@Override
	public void containsPoint2D() {
		assertTrue(this.shape.contains(createPoint(0, 0)));
		assertTrue(this.shape.contains(createPoint(.5, .5)));
		assertTrue(this.shape.contains(createPoint(1, 1)));
		assertFalse(this.shape.contains(createPoint(2.3, 4.5)));
		assertFalse(this.shape.contains(createPoint(2, 2)));
	}

	@Override
	public void getClosestPointTo() {
		Point2D p;

		p = this.shape.getClosestPointTo(createPoint(0, 0));
		assertEpsilonEquals(0, p.getX());
		assertEpsilonEquals(0, p.getY());

		p = this.shape.getClosestPointTo(createPoint(.5, .5));
		assertEpsilonEquals(.5, p.getX());
		assertEpsilonEquals(.5, p.getY());

		p = this.shape.getClosestPointTo(createPoint(1, 1));
		assertEpsilonEquals(1, p.getX());
		assertEpsilonEquals(1, p.getY());

		p = this.shape.getClosestPointTo(createPoint(2, 2));
		assertEpsilonEquals(1, p.getX());
		assertEpsilonEquals(1, p.getY());

		p = this.shape.getClosestPointTo(createPoint(-2, 2));
		assertEpsilonEquals(0, p.getX());
		assertEpsilonEquals(0, p.getY());

		p = this.shape.getClosestPointTo(createPoint(0.1, 1.2));
		assertEpsilonEquals(0.65, p.getX());
		assertEpsilonEquals(0.65, p.getY());

		p = this.shape.getClosestPointTo(createPoint(10.1, -.2));
		assertEpsilonEquals(1, p.getX());
		assertEpsilonEquals(1, p.getY());
	}

	@Override
	public void getFarthestPointTo() {
		Point2D p;

		p = this.shape.getFarthestPointTo(createPoint(0, 0));
		assertEpsilonEquals(1, p.getX());
		assertEpsilonEquals(1, p.getY());

		p = this.shape.getFarthestPointTo(createPoint(.5, .5));
		assertEpsilonEquals(0, p.getX());
		assertEpsilonEquals(0, p.getY());

		p = this.shape.getFarthestPointTo(createPoint(1, 1));
		assertEpsilonEquals(0, p.getX());
		assertEpsilonEquals(0, p.getY());

		p = this.shape.getFarthestPointTo(createPoint(2, 2));
		assertEpsilonEquals(0, p.getX());
		assertEpsilonEquals(0, p.getY());

		p = this.shape.getFarthestPointTo(createPoint(-2, 2));
		assertEpsilonEquals(1, p.getX());
		assertEpsilonEquals(1, p.getY());

		p = this.shape.getFarthestPointTo(createPoint(0.1, 1.2));
		assertEpsilonEquals(0, p.getX());
		assertEpsilonEquals(0, p.getY());

		p = this.shape.getFarthestPointTo(createPoint(10.1, -.2));
		assertEpsilonEquals(0, p.getX());
		assertEpsilonEquals(0, p.getY());
	}

	@Override
	public void getDistance() {
		assertEpsilonEquals(0, this.shape.getDistance(createPoint(0, 0)));
		assertEpsilonEquals(0, this.shape.getDistance(createPoint(.5, .5)));
		assertEpsilonEquals(0, this.shape.getDistance(createPoint(1, 1)));

		assertEpsilonEquals(3.733630941, this.shape.getDistance(createPoint(2.3, 4.5)));

		assertEpsilonEquals(1.414213562, this.shape.getDistance(createPoint(2, 2)));
	}

	@Override
	public void getDistanceSquared() {
		assertEpsilonEquals(0, this.shape.getDistanceSquared(createPoint(0, 0)));
		assertEpsilonEquals(0, this.shape.getDistanceSquared(createPoint(.5, .5)));
		assertEpsilonEquals(0, this.shape.getDistanceSquared(createPoint(1, 1)));

		assertEpsilonEquals(13.94, this.shape.getDistanceSquared(createPoint(2.3, 4.5)));

		assertEpsilonEquals(2, this.shape.getDistanceSquared(createPoint(2, 2)));
	}

	@Override
	public void getDistanceL1() {
		assertEpsilonEquals(0, this.shape.getDistanceL1(createPoint(0, 0)));
		assertEpsilonEquals(0, this.shape.getDistanceL1(createPoint(.5, .5)));
		assertEpsilonEquals(0, this.shape.getDistanceL1(createPoint(1, 1)));

		assertEpsilonEquals(4.8, this.shape.getDistanceL1(createPoint(2.3, 4.5)));

		assertEpsilonEquals(2, this.shape.getDistanceL1(createPoint(2, 2)));
	}

	@Override
	public void getDistanceLinf() {
		assertEpsilonEquals(0, this.shape.getDistanceLinf(createPoint(0, 0)));
		assertEpsilonEquals(0, this.shape.getDistanceLinf(createPoint(.5, .5)));
		assertEpsilonEquals(0, this.shape.getDistanceLinf(createPoint(1, 1)));

		assertEpsilonEquals(3.5, this.shape.getDistanceLinf(createPoint(2.3, 4.5)));

		assertEpsilonEquals(1, this.shape.getDistanceLinf(createPoint(2, 2)));
	}

	@Override
	public void setIT() {
		this.shape.set((T) createSegment(123.456, 456.789, 789.123, 159.753));
		assertEpsilonEquals(123.456, this.shape.getX1());
		assertEpsilonEquals(456.789, this.shape.getY1());
		assertEpsilonEquals(789.123, this.shape.getX2());
		assertEpsilonEquals(159.753, this.shape.getY2());
	}

	@Override
	public void getPathIterator() {
		PathIterator2afp pi = this.shape.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 0,0);
		assertElement(pi, PathElementType.LINE_TO, 1,1);
		assertNoElement(pi);
	}

	@Override
	public void getPathIteratorTransform2D() {
		Transform2D tr;
		PathIterator2afp pi;

		tr = new Transform2D();
		pi = this.shape.getPathIterator(tr);
		assertElement(pi, PathElementType.MOVE_TO, 0,0);
		assertElement(pi, PathElementType.LINE_TO, 1,1);
		assertNoElement(pi);

		tr = new Transform2D();
		tr.makeTranslationMatrix(3.4, 4.5);
		pi = this.shape.getPathIterator(tr);
		assertElement(pi, PathElementType.MOVE_TO, 3.4, 4.5);
		assertElement(pi, PathElementType.LINE_TO, 4.4, 5.5);
		assertNoElement(pi);

		tr = new Transform2D();
		tr.makeRotationMatrix(MathConstants.QUARTER_PI);
		pi = this.shape.getPathIterator(tr);
		assertElement(pi, PathElementType.MOVE_TO, 0, 0);
		assertElement(pi, PathElementType.LINE_TO, 0, 1.414213562);
		assertNoElement(pi);
	}

	@Override
	public void createTransformedShape() {
		Segment2afp s;
		Transform2D tr;

		tr = new Transform2D();    	
		s = (Segment2afp) this.shape.createTransformedShape(tr);
		assertEpsilonEquals(0, s.getX1());
		assertEpsilonEquals(0, s.getY1());
		assertEpsilonEquals(1, s.getX2());
		assertEpsilonEquals(1, s.getY2());

		tr = new Transform2D();
		tr.setTranslation(3.4, 4.5);
		s = (Segment2afp) this.shape.createTransformedShape(tr);
		assertEpsilonEquals(3.4, s.getX1());
		assertEpsilonEquals(4.5, s.getY1());
		assertEpsilonEquals(4.4, s.getX2());
		assertEpsilonEquals(5.5, s.getY2());

		tr = new Transform2D();
		tr.setRotation(MathConstants.PI);
		s = (Segment2afp) this.shape.createTransformedShape(tr);
		assertEpsilonEquals(0, s.getX1());
		assertEpsilonEquals(0, s.getY1());
		assertEpsilonEquals(-1, s.getX2());
		assertEpsilonEquals(-1, s.getY2());

		tr = new Transform2D();
		tr.setRotation(MathConstants.QUARTER_PI);
		s = (Segment2afp) this.shape.createTransformedShape(tr);
		assertEpsilonEquals(0, s.getX1());
		assertEpsilonEquals(0, s.getY1());
		assertEpsilonEquals(0, s.getX2());
		assertEpsilonEquals(1.414213562, s.getY2());
	}

	@Override
	public void translateDoubleDouble() {
		this.shape.translate(3.4, 4.5);
		assertEpsilonEquals(3.4, this.shape.getX1());
		assertEpsilonEquals(4.5, this.shape.getY1());
		assertEpsilonEquals(4.4, this.shape.getX2());
		assertEpsilonEquals(5.5, this.shape.getY2());
	}

	@Override
	public void translateVector2D() {
		this.shape.translate(createVector(3.4, 4.5));
		assertEpsilonEquals(3.4, this.shape.getX1());
		assertEpsilonEquals(4.5, this.shape.getY1());
		assertEpsilonEquals(4.4, this.shape.getX2());
		assertEpsilonEquals(5.5, this.shape.getY2());
	}

	@Override
	public void toBoundingBox() {
		B bb = this.shape.toBoundingBox();
		assertEpsilonEquals(0, bb.getMinX());
		assertEpsilonEquals(0, bb.getMinY());
		assertEpsilonEquals(1, bb.getMaxX());
		assertEpsilonEquals(1, bb.getMaxY());
	}

	@Override
	public void toBoundingBoxB() {
		B bb = createRectangle(0, 0, 0, 0);
		this.shape.toBoundingBox(bb);
		assertEpsilonEquals(0, bb.getMinX());
		assertEpsilonEquals(0, bb.getMinY());
		assertEpsilonEquals(1, bb.getMaxX());
		assertEpsilonEquals(1, bb.getMaxY());
	}

	@Override
	public void containsRectangle2afp() {
		assertFalse(this.shape.contains(createRectangle(0, 0, 1, 1)));
		assertFalse(this.shape.contains(createRectangle(0, 0, 0, 0)));
		assertFalse(this.shape.contains(createRectangle(10, 10, 1, 1)));

		this.shape.set(10, 15, 10, 18);
		assertTrue(this.shape.contains(createRectangle(10, 16, 0, 1)));
	}

	@Override
	public void intersectsRectangle2afp() {
		this.shape.set(20, 45, 43, 15);
		assertTrue(this.shape.intersects(createRectangle(10, 12, 40, 37)));

		this.shape.set(20, 55, 43, 15);
		assertTrue(this.shape.intersects(createRectangle(10, 12, 40, 37)));

		this.shape.set(20, 0, 43, 15);
		assertTrue(this.shape.intersects(createRectangle(10, 12, 40, 37)));

		this.shape.set(0, 45, 43, 15);
		assertTrue(this.shape.intersects(createRectangle(10, 12, 40, 37)));

		this.shape.set(20, 45, 60, 15);
		assertTrue(this.shape.intersects(createRectangle(10, 12, 40, 37)));

		this.shape.set(5, 45, 30, 55);
		assertTrue(this.shape.intersects(createRectangle(10, 12, 40, 37)));

		this.shape.set(40, 55, 60, 15);
		assertTrue(this.shape.intersects(createRectangle(10, 12, 40, 37)));

		this.shape.set(40, 0, 60, 40);
		assertTrue(this.shape.intersects(createRectangle(10, 12, 40, 37)));

		this.shape.set(0, 40, 20, 0);
		assertTrue(this.shape.intersects(createRectangle(10, 12, 40, 37)));

		this.shape.set(0, 45, 100, 15);
		assertTrue(this.shape.intersects(createRectangle(10, 12, 40, 37)));

		this.shape.set(20, 100, 43, 0);
		assertTrue(this.shape.intersects(createRectangle(10, 12, 40, 37)));

		this.shape.set(20, 100, 43, 101);
		assertFalse(this.shape.intersects(createRectangle(10, 12, 40, 37)));

		this.shape.set(100, 45, 102, 15);
		assertFalse(this.shape.intersects(createRectangle(10, 12, 40, 37)));

		this.shape.set(20, 0, 43, -2);
		assertFalse(this.shape.intersects(createRectangle(10, 12, 40, 37)));

		this.shape.set(-100, 45, -48, 15);
		assertFalse(this.shape.intersects(createRectangle(10, 12, 50, 49)));

		this.shape.set(-100, 60, -98, 61);
		assertFalse(this.shape.intersects(createRectangle(10, 12, 40, 37)));
	}

	@Override
	public void intersectsCircle2afp() {
		assertFalse(this.shape.intersects(createCircle(10, 10, 1)));
		assertTrue(this.shape.intersects(createCircle(0, 0, 1)));
		assertTrue(this.shape.intersects(createCircle(0, .5, 1)));
		assertTrue(this.shape.intersects(createCircle(.5, 0, 1)));
		assertTrue(this.shape.intersects(createCircle(.5, .5, 1)));
		assertFalse(this.shape.intersects(createCircle(2, 0, 1)));
		assertFalse(this.shape.intersects(createCircle(12, 8, 2)));
		assertFalse(this.shape.intersects(createCircle(12, 8, 2.1)));
		assertFalse(this.shape.intersects(createCircle(2, 1, 1)));
		assertTrue(this.shape.intersects(createCircle(2, 1, 1.1)));
		this.shape.set(0, 0, 3, 0);
		assertFalse(this.shape.intersects(createCircle(2, 1, 1)));
		assertTrue(this.shape.intersects(createCircle(2, 1, 1.1)));
	}

	@Override
	public void intersectsEllipse2afp() {
		this.shape.set(0, 0, 2, 1);
		assertFalse(this.shape.intersects(createEllipseFromCorners(5, -4, -1, -5)));
		assertFalse(this.shape.intersects(createEllipseFromCorners(-7, 3, -5, -1)));
		assertFalse(this.shape.intersects(createEllipseFromCorners(11, -2, 10, -1)));
		assertFalse(this.shape.intersects(createEllipseFromCorners(3, 5, 1, 6)));
		assertFalse(this.shape.intersects(createEllipseFromCorners(5, .5, 6, -1)));
		assertFalse(this.shape.intersects(createEllipseFromCorners(5, 2, 6, -1)));
		assertFalse(this.shape.intersects(createEllipseFromCorners(5, 2, 6, .5)));
		assertTrue(this.shape.intersects(createEllipseFromCorners(.5, .5, 3, 0)));
		assertTrue(this.shape.intersects(createEllipseFromCorners(0, 1, 3, 0)));
		assertTrue(this.shape.intersects(createEllipseFromCorners(0, 1, 3, 0)));
		assertFalse(this.shape.intersects(createEllipseFromCorners(.5, 2, .5, -1)));
		assertFalse(this.shape.intersects(createEllipseFromCorners(-1, -5, 5, -4)));
		assertFalse(this.shape.intersects(createEllipseFromCorners(-5, -1, -7, 3)));
		assertFalse(this.shape.intersects(createEllipseFromCorners(10, -1, 11, -2)));
		assertFalse(this.shape.intersects(createEllipseFromCorners(1, 6, 3, 5)));
		assertFalse(this.shape.intersects(createEllipseFromCorners(6, -1, 5, .5)));
		assertFalse(this.shape.intersects(createEllipseFromCorners(6, -1, 5, 2)));
		assertFalse(this.shape.intersects(createEllipseFromCorners(6, .5, 5, 2)));
		assertTrue(this.shape.intersects(createEllipseFromCorners(3, 0, .5, .5)));
		assertTrue(this.shape.intersects(createEllipseFromCorners(3, 0, 0, 1)));
		assertTrue(this.shape.intersects(createEllipseFromCorners(3, 0, 0, 1)));
		assertFalse(this.shape.intersects(createEllipseFromCorners(.5, -1, .5, 2)));
	}

	@Override
	public void intersectsSegment2afp() {
		assertFalse(this.shape.intersects(createSegment(10, -5, 10, -4)));
		assertFalse(this.shape.intersects(createSegment(10, 5, 10, 4)));
		assertFalse(this.shape.intersects(createSegment(-5, .5, 0, .6)));
		assertFalse(this.shape.intersects(createSegment(10, -1, 11, .6)));
		assertFalse(this.shape.intersects(createSegment(10, -1, 11, 2)));
		assertFalse(this.shape.intersects(createSegment(10, .5, 11, 2)));
		assertFalse(this.shape.intersects(createSegment(10, 2, 11, .6)));
		assertFalse(this.shape.intersects(createSegment(10, 2, 11, -1)));
		assertFalse(this.shape.intersects(createSegment(10, .6, 11, -1)));
		assertFalse(this.shape.intersects(createSegment(0, .5, .25, .5)));
		assertFalse(this.shape.intersects(createSegment(.75, .5, 1, .5)));
		assertFalse(this.shape.intersects(createSegment(5, -5, .75, .5)));
		assertTrue(this.shape.intersects(createSegment(5, -5, 0, 1)));
		assertTrue(this.shape.intersects(createSegment(5, -5, 1, 1)));
		assertFalse(this.shape.intersects(createSegment(-2, 1, 5, -5)));
	}

	@Override
	public void intersectsTriangle2afp() {
		Triangle2afp triangle = createTriangle(5, 8, -10, 1, -1, -2);
		assertTrue(createSegment(-6, -2, -4, 0).intersects(triangle));
		assertTrue(createSegment(-6, -2, 2, 0).intersects(triangle));
		assertFalse(createSegment(-6, -2, 14, -4).intersects(triangle));
		assertTrue(createSegment(-2, 2, 4, 0).intersects(triangle));
		assertTrue(createSegment(-2, 2, 0, 0).intersects(triangle));
		assertTrue(createSegment(-4, -2, -6, 6).intersects(triangle));
		assertTrue(createSegment(6, 7, -6, 6).intersects(triangle));
		assertTrue(createSegment(0, 5, -6, 6).intersects(triangle));
		assertFalse(createSegment(-5, 5, -6, 6).intersects(triangle));

		assertFalse(createSegment(-4, -2, 2, -2).intersects(triangle));
		assertFalse(createSegment(-1, -2, 5, 8).intersects(triangle));
	}

	@Override
	public void intersectsPath2afp() {
		Path2afp p;

		p = createPath();
		p.moveTo(-2, -2);
		p.lineTo(-2, 2);
		p.lineTo(2, 2);
		p.lineTo(2, -2);
		assertFalse(this.shape.intersects(p));
		p.closePath();
		assertTrue(this.shape.intersects(p));

		p = createPath();
		p.moveTo(-2, -2);
		p.lineTo(0, 0);
		p.lineTo(-2, 2);
		assertFalse(this.shape.intersects(p));
		p.closePath();
		assertFalse(this.shape.intersects(p));

		p = createPath();
		p.moveTo(-2, -2);
		p.lineTo(2, 2);
		p.lineTo(-2, 2);
		assertTrue(this.shape.intersects(p));
		p.closePath();
		assertTrue(this.shape.intersects(p));

		p = createPath();
		p.moveTo(-2, -2);
		p.lineTo(-2, 2);
		p.lineTo(2, -2);
		assertTrue(this.shape.intersects(p));
		p.closePath();
		assertTrue(this.shape.intersects(p));

		p = createPath();
		p.moveTo(-2, 2);
		p.lineTo(1, 0);
		p.lineTo(2, 1);
		assertTrue(this.shape.intersects(p));
		p.closePath();
		assertTrue(this.shape.intersects(p));

		p = createPath();
		p.moveTo(-2, 2);
		p.lineTo(2, 1);
		p.lineTo(1, 0);
		assertFalse(this.shape.intersects(p));
		p.closePath();
		assertTrue(this.shape.intersects(p));
	}

	@Override
	public void intersectsPathIterator2afp() {
		Path2afp<?, ?, ?, ?, ?, B> p;

		p = createPath();
		p.moveTo(-2, -2);
		p.lineTo(-2, 2);
		p.lineTo(2, 2);
		p.lineTo(2, -2);
		assertFalse(this.shape.intersects(p.getPathIterator()));
		p.closePath();
		assertTrue(this.shape.intersects(p.getPathIterator()));

		p = createPath();
		p.moveTo(-2, -2);
		p.lineTo(0, 0);
		p.lineTo(-2, 2);
		assertFalse(this.shape.intersects(p.getPathIterator()));
		p.closePath();
		assertFalse(this.shape.intersects(p.getPathIterator()));

		p = createPath();
		p.moveTo(-2, -2);
		p.lineTo(2, 2);
		p.lineTo(-2, 2);
		assertTrue(this.shape.intersects(p.getPathIterator()));
		p.closePath();
		assertTrue(this.shape.intersects(p.getPathIterator()));

		p = createPath();
		p.moveTo(-2, -2);
		p.lineTo(-2, 2);
		p.lineTo(2, -2);
		assertTrue(this.shape.intersects(p.getPathIterator()));
		p.closePath();
		assertTrue(this.shape.intersects(p.getPathIterator()));

		p = createPath();
		p.moveTo(-2, 2);
		p.lineTo(1, 0);
		p.lineTo(2, 1);
		assertTrue(this.shape.intersects(p.getPathIterator()));
		p.closePath();
		assertTrue(this.shape.intersects(p.getPathIterator()));

		p = createPath();
		p.moveTo(-2, 2);
		p.lineTo(2, 1);
		p.lineTo(1, 0);
		assertFalse(this.shape.intersects(p.getPathIterator()));
		p.closePath();
		assertTrue(this.shape.intersects(p.getPathIterator()));
	}

	@Override
	public void intersectsOrientedRectangle2afp() {
		assertFalse(this.shape.intersects(createOrientedRectangle(
				7, 3.5,
				-0.99839, -0.05671,
				8.06684, 1.81085)));
		assertFalse(this.shape.intersects(createOrientedRectangle(
				7, 3.5,
				-0.7471, -0.66471,
				9.43417, 1.81085)));
		assertTrue(this.shape.intersects(createOrientedRectangle(
				7, 3.5,
				-0.81997, -0.57241,
				6.94784, 1.81085)));
		assertTrue(this.shape.intersects(createOrientedRectangle(
				7, 3.5,
				-0.84335, -0.53736,
				8.80456, 1.81085)));
	}

	@Test
	@Override
	public void intersectsParallelogram2afp() {
		Parallelogram2afp para = createParallelogram(
				6, 9,
				2.425356250363330e-01, 9.701425001453320e-01, 9.219544457292887,
				-7.071067811865475e-01, 7.071067811865475e-01, 1.264911064067352e+01);
		assertFalse(createSegment(0, 0, 1, 1).intersects(para));
		assertTrue(createSegment(5, 5, 4, 6).intersects(para));
		assertTrue(createSegment(2, -2, 5, 0).intersects(para));
		assertFalse(createSegment(-20, -5, -10, 6).intersects(para));
		assertFalse(createSegment(-5, 0, -10, 16).intersects(para));
		assertTrue(createSegment(-10, 1, 10, 20).intersects(para));
	}

	@Override
	public void intersectsRoundRectangle2afp() {
		RoundRectangle2afp<?, ?, ?, ?, ?, B> box = createRoundRectangle(0, 0, 1, 1, .2, .4);
		assertTrue(this.shape.intersects(box));
		assertTrue(createSegment(-5, -5, 1, 1).intersects(box));
		assertTrue(createSegment(.5, .5, 5, 5).intersects(box));
		assertTrue(createSegment(.5, .5, 5, .6).intersects(box));
		assertTrue(createSegment(-5, -5, 5, 5).intersects(box));
		assertFalse(createSegment(-5, -5, -4, -4).intersects(box));
		assertFalse(createSegment(-5, -5, 4, -4).intersects(box));
		assertFalse(createSegment(5, -5, 6, 5).intersects(box));
		assertFalse(createSegment(5, -5, 5, 5).intersects(box));
		assertFalse(createSegment(-1, -1, 0.01, 0.01).intersects(box));
		assertTrue(createSegment(-1, -1, 0.1, 0.2).intersects(box));
	}

	@Override
	public void intersectsShape2D() {
		assertTrue(this.shape.intersects((Shape2D) createCircle(0, 0, 1)));
		assertFalse(this.shape.intersects((Shape2D) createEllipseFromCorners(5, 2, 6, .5)));
	}

	@Override
	public void operator_addVector2D() {
		this.shape.operator_add(createVector(3.4, 4.5));
		assertEpsilonEquals(3.4, this.shape.getX1());
		assertEpsilonEquals(4.5, this.shape.getY1());
		assertEpsilonEquals(4.4, this.shape.getX2());
		assertEpsilonEquals(5.5, this.shape.getY2());
	}

	@Override
	public void operator_plusVector2D() {
		T shape = this.shape.operator_plus(createVector(3.4, 4.5));
		assertNotSame(shape, this.shape);
		assertEpsilonEquals(3.4, shape.getX1());
		assertEpsilonEquals(4.5, shape.getY1());
		assertEpsilonEquals(4.4, shape.getX2());
		assertEpsilonEquals(5.5, shape.getY2());
	}

	@Override
	public void operator_removeVector2D() {
		this.shape.operator_remove(createVector(3.4, 4.5));
		assertEpsilonEquals(-3.4, this.shape.getX1());
		assertEpsilonEquals(-4.5, this.shape.getY1());
		assertEpsilonEquals(-2.4, this.shape.getX2());
		assertEpsilonEquals(-3.5, this.shape.getY2());
	}

	@Override
	public void operator_minusVector2D() {
		T shape = this.shape.operator_minus(createVector(3.4, 4.5));
		assertEpsilonEquals(-3.4, shape.getX1());
		assertEpsilonEquals(-4.5, shape.getY1());
		assertEpsilonEquals(-2.4, shape.getX2());
		assertEpsilonEquals(-3.5, shape.getY2());
	}

	@Override
	public void operator_multiplyTransform2D() {
		Segment2afp s;
		Transform2D tr;

		tr = new Transform2D();    	
		s = (Segment2afp) this.shape.operator_multiply(tr);
		assertEpsilonEquals(0, s.getX1());
		assertEpsilonEquals(0, s.getY1());
		assertEpsilonEquals(1, s.getX2());
		assertEpsilonEquals(1, s.getY2());

		tr = new Transform2D();
		tr.setTranslation(3.4, 4.5);
		s = (Segment2afp) this.shape.operator_multiply(tr);
		assertEpsilonEquals(3.4, s.getX1());
		assertEpsilonEquals(4.5, s.getY1());
		assertEpsilonEquals(4.4, s.getX2());
		assertEpsilonEquals(5.5, s.getY2());

		tr = new Transform2D();
		tr.setRotation(MathConstants.PI);
		s = (Segment2afp) this.shape.operator_multiply(tr);
		assertEpsilonEquals(0, s.getX1());
		assertEpsilonEquals(0, s.getY1());
		assertEpsilonEquals(-1, s.getX2());
		assertEpsilonEquals(-1, s.getY2());

		tr = new Transform2D();
		tr.setRotation(MathConstants.QUARTER_PI);
		s = (Segment2afp) this.shape.operator_multiply(tr);
		assertEpsilonEquals(0, s.getX1());
		assertEpsilonEquals(0, s.getY1());
		assertEpsilonEquals(0, s.getX2());
		assertEpsilonEquals(1.414213562, s.getY2());
	}

	@Override
	public void operator_andPoint2D() {
		assertTrue(this.shape.operator_and(createPoint(0, 0)));
		assertTrue(this.shape.operator_and(createPoint(.5, .5)));
		assertTrue(this.shape.operator_and(createPoint(1, 1)));
		assertFalse(this.shape.operator_and(createPoint(2.3, 4.5)));
		assertFalse(this.shape.operator_and(createPoint(2, 2)));
	}

	@Override
	public void operator_andShape2D() {
		assertTrue(this.shape.operator_and(createCircle(0, 0, 1)));
		assertFalse(this.shape.operator_and(createEllipseFromCorners(5, 2, 6, .5)));
	}

	@Override
	public void operator_upToPoint2D() {
		assertEpsilonEquals(0, this.shape.operator_upTo(createPoint(0, 0)));
		assertEpsilonEquals(0, this.shape.operator_upTo(createPoint(.5, .5)));
		assertEpsilonEquals(0, this.shape.operator_upTo(createPoint(1, 1)));

		assertEpsilonEquals(3.733630941, this.shape.operator_upTo(createPoint(2.3, 4.5)));

		assertEpsilonEquals(1.414213562, this.shape.operator_upTo(createPoint(2, 2)));
	}

	@Test
	public void issue15() {
		Segment2afp segment = createSegment(-20, -20, 20, 20);
		Path2afp path = createPath();
		path.moveTo(5, 5);
		path.lineTo(5, -5);
		path.lineTo(-5, -5);
		path.lineTo(-5, 5);
		path.lineTo(5, 5);
		assertTrue(path.intersects(segment));
	}

}