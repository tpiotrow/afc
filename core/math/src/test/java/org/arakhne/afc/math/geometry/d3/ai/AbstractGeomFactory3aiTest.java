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

package org.arakhne.afc.math.geometry.d3.ai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import org.arakhne.afc.math.AbstractMathTestCase;
import org.arakhne.afc.math.geometry.PathElementType;
import org.arakhne.afc.math.geometry.PathWindingRule;
import org.arakhne.afc.math.geometry.coordinatesystem.CoordinateSystem3DTestRule;
import org.arakhne.afc.math.geometry.d3.Point3D;
import org.arakhne.afc.math.geometry.d3.Point3DStub;
import org.arakhne.afc.math.geometry.d3.Vector3D;
import org.arakhne.afc.math.geometry.d3.Vector3DStub;

@SuppressWarnings("all")
public abstract class AbstractGeomFactory3aiTest extends AbstractMathTestCase {

	private GeomFactory3ai<?, ?, ?, ?> factory;
	
	@Rule
	public CoordinateSystem3DTestRule csTestRule = new CoordinateSystem3DTestRule();

	protected abstract GeomFactory3ai<?, ?, ?, ?> createFactory();
	
	protected abstract Point3D createPoint(int x, int y, int z);

	protected abstract Vector3D createVector(int x, int y, int z);

	@Before
	public void setUp() throws Exception {
		this.factory = createFactory();
	}
	
	@After
	public void tearDown() throws Exception {
		this.factory = null;
	}
	
	@Test
	public void convertToPointPoint3D_expectedPointType() {
		Point3D p = createPoint(45, 56, 72);
		Point3D p2 = this.factory.convertToPoint(p);
		assertSame(p, p2);
	}
	
	@Test
	public void convertToPointPoint3D_notExpectedPointType() {
		Point3D p = new Point3DStub(45, 56, 72);
		Point3D p2 = this.factory.convertToPoint(p);
		assertNotSame(p, p2);
		assertEquals(p, p2);
	}

	@Test
	public void convertToVectorPoint3D() {
		Point3D p = new Point3DStub(45, 56, 72);
		Vector3D v = this.factory.convertToVector(p);
		assertNotSame(p, v);
		assertEquals(p, v);
	}

	@Test
	public void convertToPointVector2D() {
		Vector3D v = new Vector3DStub(45, 56, 72);
		Point3D p = this.factory.convertToPoint(v);
		assertNotSame(v, p);
		assertEquals(v, p);
	}

	@Test
	public void convertToVectorVector2D_expectedVectorType() {
		Vector3D v = createVector(45, 56, 72);
		Vector3D v2 = this.factory.convertToVector(v);
		assertSame(v, v2);
	}
	
	@Test
	public void convertToVectorVector2D_notExpectedVectorType() {
		Vector3D v = new Vector3DStub(45, 56, 72);
		Vector3D v2 = this.factory.convertToVector(v);
		assertNotSame(v, v2);
		assertEquals(v, v2);
	}

	@Test
	public void newPoint() {
		Point3D p = this.factory.newPoint();
		assertNotNull(p);
		assertEquals(0, p.ix());
		assertEquals(0, p.iy());
		assertEquals(0, p.iz());
		Point3D ref = createPoint(0, 0, 0);
		assertEquals(ref.getClass(), p.getClass());
	}

	@Test
	public void newPointIntIntInt() {
		Point3D p = this.factory.newPoint(15, 48, 6);
		assertNotNull(p);
		assertEquals(15, p.ix());
		assertEquals(48, p.iy());
		assertEquals(6, p.iz());
		Point3D ref = createPoint(0, 0, 0);
		assertEquals(ref.getClass(), p.getClass());
	}

	@Test
	public void newPointDoubleDouble() {
		Point3D p = this.factory.newPoint(15.56, 48.32, 6.42);
		assertNotNull(p);
		assertEpsilonEquals(16, p.getX());
		assertEpsilonEquals(48, p.getY());
		assertEpsilonEquals(6, p.getZ());
		Point3D ref = createPoint(0, 0, 0);
		assertEquals(ref.getClass(), p.getClass());
	}

	@Test
	public void newVector() {
		Vector3D p = this.factory.newVector();
		assertNotNull(p);
		assertEquals(0, p.ix());
		assertEquals(0, p.iy());
		assertEquals(0, p.iz());
		Vector3D ref = createVector(0, 0, 0);
		assertEquals(ref.getClass(), p.getClass());
	}

	@Test
	public void newVectorIntIntInt() {
		Vector3D p = this.factory.newVector(15, 48, 6);
		assertNotNull(p);
		assertEquals(15, p.ix());
		assertEquals(48, p.iy());
		assertEquals(6, p.iz());
		Vector3D ref = createVector(0, 0, 0);
		assertEquals(ref.getClass(), p.getClass());
	}

	@Test
	public void newVectorDoubleDoubleDouble() {
		Vector3D p = this.factory.newVector(15.45, 48.87, 6.42);
		assertNotNull(p);
		assertEpsilonEquals(15, p.getX());
		assertEpsilonEquals(49, p.getY());
		assertEpsilonEquals(6, p.getZ());
		Vector3D ref = createVector(0, 0, 0);
		assertEquals(ref.getClass(), p.getClass());
	}

	@Test
	public void newPath_NONZERO() {
		Path3ai<?, ?, ?, ?, ?, ?> path = this.factory.newPath(PathWindingRule.NON_ZERO);
		assertNotNull(path);
		assertSame(PathWindingRule.NON_ZERO, path.getWindingRule());
		assertEquals(0, path.size());
	}
	
	@Test
	public void newPath_EVENODD() {
		Path3ai<?, ?, ?, ?, ?, ?> path = this.factory.newPath(PathWindingRule.EVEN_ODD);
		assertNotNull(path);
		assertSame(PathWindingRule.EVEN_ODD, path.getWindingRule());
		assertEquals(0, path.size());
	}

	@Test
	public void newSegment() {
		Segment3ai<?, ?, ?, ?, ?, ?> s = this.factory.newSegment(1, 2, 3, 4, 5, 6);
		assertNotNull(s);
		assertEquals(1, s.getX1());
		assertEquals(2, s.getY1());
		assertEquals(3, s.getZ1());
		assertEquals(4, s.getX2());
		assertEquals(5, s.getY2());
		assertEquals(6, s.getZ2());
	}
	
	@Test
	public void newBox() {
		RectangularPrism3ai<?, ?, ?, ?, ?, ?> r = this.factory.newBox();
		assertNotNull(r);
		assertEquals(0, r.getMinX());
		assertEquals(0, r.getMinY());
		assertEquals(0, r.getMinZ());
		assertEquals(0, r.getMaxX());
		assertEquals(0, r.getMaxY());
		assertEquals(0, r.getMaxZ());
	}

	@Test
	public void newBoxIntIntIntIntIntInt() {
		RectangularPrism3ai<?, ?, ?, ?, ?, ?> r = this.factory.newBox(1, 2, 3, 4, 5, 6);
		assertNotNull(r);
		assertEquals(1, r.getMinX());
		assertEquals(2, r.getMinY());
		assertEquals(3, r.getMinZ());
		assertEquals(5, r.getMaxX());
		assertEquals(7, r.getMaxY());
		assertEquals(9, r.getMaxZ());
	}

	@Test
	public void newMovePathElement() {
		PathElement3ai element = this.factory.newMovePathElement(1, 2, 3);
		assertNotNull(element);
		assertSame(PathElementType.MOVE_TO, element.getType());
		assertEquals(0, element.getFromX());
		assertEquals(0, element.getFromY());
		assertEquals(0, element.getFromZ());
		assertEquals(0, element.getCtrlX1());
		assertEquals(0, element.getCtrlY1());
		assertEquals(0, element.getCtrlZ1());
		assertEquals(0, element.getCtrlX2());
		assertEquals(0, element.getCtrlY2());
		assertEquals(0, element.getCtrlZ2());
		assertEquals(1, element.getToX());
		assertEquals(2, element.getToY());
		assertEquals(3, element.getToZ());
	}
	
	@Test
	public void newLinePathElement() {
		PathElement3ai element = this.factory.newLinePathElement(1, 2, 3, 4, 5, 6);
		assertNotNull(element);
		assertSame(PathElementType.LINE_TO, element.getType());
		assertEquals(1, element.getFromX());
		assertEquals(2, element.getFromY());
		assertEquals(3, element.getFromZ());
		assertEquals(0, element.getCtrlX1());
		assertEquals(0, element.getCtrlY1());
		assertEquals(0, element.getCtrlZ1());
		assertEquals(0, element.getCtrlX2());
		assertEquals(0, element.getCtrlY2());
		assertEquals(0, element.getCtrlZ2());
		assertEquals(4, element.getToX());
		assertEquals(5, element.getToY());
		assertEquals(6, element.getToZ());
	}

	@Test
	public void newClosePathElement() {
		PathElement3ai element = this.factory.newClosePathElement(1, 2, 3, 4, 5, 6);
		assertNotNull(element);
		assertSame(PathElementType.CLOSE, element.getType());
		assertEquals(1, element.getFromX());
		assertEquals(2, element.getFromY());
		assertEquals(3, element.getFromZ());
		assertEquals(0, element.getCtrlX1());
		assertEquals(0, element.getCtrlY1());
		assertEquals(0, element.getCtrlZ1());
		assertEquals(0, element.getCtrlX2());
		assertEquals(0, element.getCtrlY2());
		assertEquals(0, element.getCtrlZ2());
		assertEquals(4, element.getToX());
		assertEquals(5, element.getToY());
		assertEquals(6, element.getToZ());
	}

	@Test
	@Ignore
	public void newCurvePathElement_quad() {
		PathElement3ai element = this.factory.newCurvePathElement(1, 2, 3, 4, 5, 6, 7, 8, 9);
		assertNotNull(element);
		assertSame(PathElementType.QUAD_TO, element.getType());
		assertEquals(1, element.getFromX());
		assertEquals(2, element.getFromY());
		assertEquals(3, element.getFromZ());
		assertEquals(4, element.getCtrlX1());
		assertEquals(5, element.getCtrlY1());
		assertEquals(6, element.getCtrlZ1());
		assertEquals(0, element.getCtrlX2());
		assertEquals(0, element.getCtrlY2());
		assertEquals(0, element.getCtrlZ2());
		assertEquals(7, element.getToX());
		assertEquals(8, element.getToY());
		assertEquals(9, element.getToY());
	}

	@Test
	public void newCurvePathElement_curve() {
		PathElement3ai element = this.factory.newCurvePathElement(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		assertNotNull(element);
		assertSame(PathElementType.CURVE_TO, element.getType());
		assertEquals(1, element.getFromX());
		assertEquals(2, element.getFromY());
		assertEquals(3, element.getFromZ());
		assertEquals(4, element.getCtrlX1());
		assertEquals(5, element.getCtrlY1());
		assertEquals(6, element.getCtrlZ1());
		assertEquals(7, element.getCtrlX2());
		assertEquals(8, element.getCtrlY2());
		assertEquals(9, element.getCtrlZ2());
		assertEquals(10, element.getToX());
		assertEquals(11, element.getToY());
		assertEquals(12, element.getToZ());
	}
	
}
