/*
 * $Id$
 * This file is a part of the Arakhne Foundation Classes, http://www.arakhne.org/afc
 *
 * Copyright (c) 2000-2012 Stephane GALLAND.
 * Copyright (c) 2005-10, Multiagent Team, Laboratoire Systemes et Transports,
 *                        Universite de Technologie de Belfort-Montbeliard.
 * Copyright (c) 2013-2016 The original authors, and other authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.arakhne.afc.math.geometry.d2.ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.arakhne.afc.math.AbstractMathTestCase;
import org.arakhne.afc.math.geometry.PathElementType;
import org.arakhne.afc.math.geometry.PathWindingRule;
import org.arakhne.afc.math.geometry.coordinatesystem.CoordinateSystem2DTestRule;
import org.arakhne.afc.math.geometry.d2.Point2DStub;
import org.arakhne.afc.math.geometry.d2.Vector2DStub;
import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.Vector2D;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@SuppressWarnings("all")
public abstract class AbstractGeomFactory2aiTest extends AbstractMathTestCase {

	private GeomFactory2ai<?, ?, ?, ?> factory;
	
	@Rule
	public CoordinateSystem2DTestRule csTestRule = new CoordinateSystem2DTestRule();

	protected abstract GeomFactory2ai<?, ?, ?, ?> createFactory();
	
	protected abstract Point2D createPoint(int x, int y);

	protected abstract Vector2D createVector(int x, int y);

	@Before
	public void setUp() throws Exception {
		this.factory = createFactory();
	}
	
	@After
	public void tearDown() throws Exception {
		this.factory = null;
	}
	
	@Test
	public void convertToPointPoint2D_expectedPointType() {
		Point2D p = createPoint(45, 56);
		Point2D p2 = this.factory.convertToPoint(p);
		assertSame(p, p2);
	}
	
	@Test
	public void convertToPointPoint2D_notExpectedPointType() {
		Point2D p = new Point2DStub(45, 56);
		Point2D p2 = this.factory.convertToPoint(p);
		assertNotSame(p, p2);
		assertEquals(p, p2);
	}

	@Test
	public void convertToVectorPoint2D() {
		Point2D p = new Point2DStub(45, 56);
		Vector2D v = this.factory.convertToVector(p);
		assertNotSame(p, v);
		assertEquals(p, v);
	}

	@Test
	public void convertToPointVector2D() {
		Vector2D v = new Vector2DStub(45, 56);
		Point2D p = this.factory.convertToPoint(v);
		assertNotSame(v, p);
		assertEquals(v, p);
	}

	@Test
	public void convertToVectorVector2D_expectedVectorType() {
		Vector2D v = createVector(45, 56);
		Vector2D v2 = this.factory.convertToVector(v);
		assertSame(v, v2);
	}
	
	@Test
	public void convertToVectorVector2D_notExpectedVectorType() {
		Vector2D v = new Vector2DStub(45, 56);
		Vector2D v2 = this.factory.convertToVector(v);
		assertNotSame(v, v2);
		assertEquals(v, v2);
	}

	@Test
	public void newPoint() {
		Point2D p = this.factory.newPoint();
		assertNotNull(p);
		assertEquals(0, p.ix());
		assertEquals(0, p.iy());
		Point2D ref = createPoint(0, 0);
		assertEquals(ref.getClass(), p.getClass());
	}

	@Test
	public void newPointIntInt() {
		Point2D p = this.factory.newPoint(15, 48);
		assertNotNull(p);
		assertEquals(15, p.ix());
		assertEquals(48, p.iy());
		Point2D ref = createPoint(0, 0);
		assertEquals(ref.getClass(), p.getClass());
	}

	@Test
	public void newPointDoubleDouble() {
		Point2D p = this.factory.newPoint(15.56, 48.32);
		assertNotNull(p);
		assertEpsilonEquals(16, p.getX());
		assertEpsilonEquals(48, p.getY());
		Point2D ref = createPoint(0, 0);
		assertEquals(ref.getClass(), p.getClass());
	}

	@Test
	public void newVector() {
		Vector2D p = this.factory.newVector();
		assertNotNull(p);
		assertEquals(0, p.ix());
		assertEquals(0, p.iy());
		Vector2D ref = createVector(0, 0);
		assertEquals(ref.getClass(), p.getClass());
	}

	@Test
	public void newVectorIntInt() {
		Vector2D p = this.factory.newVector(15, 48);
		assertNotNull(p);
		assertEquals(15, p.ix());
		assertEquals(48, p.iy());
		Vector2D ref = createVector(0, 0);
		assertEquals(ref.getClass(), p.getClass());
	}

	@Test
	public void newVectorDoubleDouble() {
		Vector2D p = this.factory.newVector(15.45, 48.87);
		assertNotNull(p);
		assertEpsilonEquals(15, p.getX());
		assertEpsilonEquals(49, p.getY());
		Vector2D ref = createVector(0, 0);
		assertEquals(ref.getClass(), p.getClass());
	}

	@Test
	public void newPath_NONZERO() {
		Path2ai<?, ?, ?, ?, ?, ?> path = this.factory.newPath(PathWindingRule.NON_ZERO);
		assertNotNull(path);
		assertSame(PathWindingRule.NON_ZERO, path.getWindingRule());
		assertEquals(0, path.size());
	}
	
	@Test
	public void newPath_EVENODD() {
		Path2ai<?, ?, ?, ?, ?, ?> path = this.factory.newPath(PathWindingRule.EVEN_ODD);
		assertNotNull(path);
		assertSame(PathWindingRule.EVEN_ODD, path.getWindingRule());
		assertEquals(0, path.size());
	}

	@Test
	public void newSegment() {
		Segment2ai<?, ?, ?, ?, ?, ?> s = this.factory.newSegment(1,  2,  3,  4);
		assertNotNull(s);
		assertEquals(1, s.getX1());
		assertEquals(2, s.getY1());
		assertEquals(3, s.getX2());
		assertEquals(4, s.getY2());
	}
	
	@Test
	public void newBox() {
		Rectangle2ai<?, ?, ?, ?, ?, ?> r = this.factory.newBox();
		assertNotNull(r);
		assertEquals(0, r.getMinX());
		assertEquals(0, r.getMinY());
		assertEquals(0, r.getMaxX());
		assertEquals(0, r.getMaxY());
	}

	@Test
	public void newBoxIntIntIntInt() {
		Rectangle2ai<?, ?, ?, ?, ?, ?> r = this.factory.newBox(1, 2, 3, 4);
		assertNotNull(r);
		assertEquals(1, r.getMinX());
		assertEquals(2, r.getMinY());
		assertEquals(4, r.getMaxX());
		assertEquals(6, r.getMaxY());
	}

	@Test
	public void newMovePathElement() {
		PathElement2ai element = this.factory.newMovePathElement(1, 2);
		assertNotNull(element);
		assertSame(PathElementType.MOVE_TO, element.getType());
		assertEquals(0, element.getFromX());
		assertEquals(0, element.getFromY());
		assertEquals(0, element.getCtrlX1());
		assertEquals(0, element.getCtrlY1());
		assertEquals(0, element.getCtrlX2());
		assertEquals(0, element.getCtrlY2());
		assertEquals(1, element.getToX());
		assertEquals(2, element.getToY());
	}
	
	@Test
	public void newLinePathElement() {
		PathElement2ai element = this.factory.newLinePathElement(1, 2, 3, 4);
		assertNotNull(element);
		assertSame(PathElementType.LINE_TO, element.getType());
		assertEquals(1, element.getFromX());
		assertEquals(2, element.getFromY());
		assertEquals(0, element.getCtrlX1());
		assertEquals(0, element.getCtrlY1());
		assertEquals(0, element.getCtrlX2());
		assertEquals(0, element.getCtrlY2());
		assertEquals(3, element.getToX());
		assertEquals(4, element.getToY());
	}

	@Test
	public void newClosePathElement() {
		PathElement2ai element = this.factory.newClosePathElement(1, 2, 3, 4);
		assertNotNull(element);
		assertSame(PathElementType.CLOSE, element.getType());
		assertEquals(1, element.getFromX());
		assertEquals(2, element.getFromY());
		assertEquals(0, element.getCtrlX1());
		assertEquals(0, element.getCtrlY1());
		assertEquals(0, element.getCtrlX2());
		assertEquals(0, element.getCtrlY2());
		assertEquals(3, element.getToX());
		assertEquals(4, element.getToY());
	}

	@Test
	public void newCurvePathElement_quad() {
		PathElement2ai element = this.factory.newCurvePathElement(1, 2, 3, 4, 5, 6);
		assertNotNull(element);
		assertSame(PathElementType.QUAD_TO, element.getType());
		assertEquals(1, element.getFromX());
		assertEquals(2, element.getFromY());
		assertEquals(3, element.getCtrlX1());
		assertEquals(4, element.getCtrlY1());
		assertEquals(0, element.getCtrlX2());
		assertEquals(0, element.getCtrlY2());
		assertEquals(5, element.getToX());
		assertEquals(6, element.getToY());
	}

	@Test
	public void newCurvePathElement_curve() {
		PathElement2ai element = this.factory.newCurvePathElement(1, 2, 3, 4, 5, 6, 7, 8);
		assertNotNull(element);
		assertSame(PathElementType.CURVE_TO, element.getType());
		assertEquals(1, element.getFromX());
		assertEquals(2, element.getFromY());
		assertEquals(3, element.getCtrlX1());
		assertEquals(4, element.getCtrlY1());
		assertEquals(5, element.getCtrlX2());
		assertEquals(6, element.getCtrlY2());
		assertEquals(7, element.getToX());
		assertEquals(8, element.getToY());
	}

	@Test
	public void newArcPathElement() {
		PathElement2ai element = this.factory.newArcPathElement(1, 2, 3, 4, 5, 6, 7, true, false);
		assertNotNull(element);
		assertSame(PathElementType.ARC_TO, element.getType());
		assertEquals(1, element.getFromX());
		assertEquals(2, element.getFromY());
		assertEquals(3, element.getToX());
		assertEquals(4, element.getToY());
		assertEquals(5, element.getRadiusX());
		assertEquals(6, element.getRadiusY());
		assertEpsilonEquals(7, element.getRotationX());
		assertTrue(element.getLargeArcFlag());
		assertFalse(element.getSweepFlag());
	}
	
}