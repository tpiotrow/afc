/* 
 * $Id$
 * 
 * Copyright (C) 2010-2013 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package org.arakhne.afc.math.geometry.d3.ifx;

import org.arakhne.afc.math.geometry.PathWindingRule;
import org.arakhne.afc.math.geometry.d3.Point3D;
import org.arakhne.afc.math.geometry.d3.Quaternion;
import org.arakhne.afc.math.geometry.d3.Vector3D;
import org.arakhne.afc.math.geometry.d3.ai.GeomFactory3ai;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/** Factory of geometrical elements.
 * 
 * @author $Author: sgalland$
 * @author $Author: tpiotrow$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public class GeomFactory3ifx implements GeomFactory3ai<PathElement3ifx, Point3ifx, Vector3ifx, RectangularPrism3ifx> {

	/** The singleton of the factory.
	 */
	public static final GeomFactory3ifx SINGLETON = new GeomFactory3ifx();
	
	@Override
	public Point3ifx convertToPoint(Point3D<?, ?> point) {
		assert (point != null) : "Point must be not null"; //$NON-NLS-1$
		try {
			return (Point3ifx) point;
		} catch (Throwable exception) {
			return new Point3ifx(point);
		}
	}
	
	@Override
	public Vector3ifx convertToVector(Point3D<?, ?> point) {
		assert (point != null) : "Point must be not null"; //$NON-NLS-1$
		return new Vector3ifx(point.ix(), point.iy(), point.iz());
	}

	@Override
	public Point3ifx convertToPoint(Vector3D<?, ?> vector) {
		assert (vector != null) : "Vector must be not null"; //$NON-NLS-1$
		return new Point3ifx(vector.ix(), vector.iy(), vector.iz());
	}
	
	@Override
	public Vector3ifx convertToVector(Vector3D<?, ?> vector) {
		assert (vector != null) : "Vector must be not null"; //$NON-NLS-1$
		Vector3ifx vv;
		try {
			vv = (Vector3ifx) vector;
		} catch (Throwable exception) {
			vv = new Vector3ifx(vector.ix(), vector.iy(), vector.iz());
		}
		return vv;
	}

	@Override
	public Point3ifx newPoint(int x, int y, int z) {
		return new Point3ifx(x, y, z);
	}

	@Override
	public Vector3ifx newVector(int x, int y, int z) {
		return new Vector3ifx(x, y, z);
	}

	@Override
	public Point3ifx newPoint(double x, double y, double z) {
		return new Point3ifx(x, y, z);
	}

	@Override
	public Vector3ifx newVector(double x, double y, double z) {
		return new Vector3ifx(x, y, z);
	}

	@Override
	public Point3ifx newPoint() {
		return new Point3ifx();
	}
	
	/** Create a point with properties.
	 *
	 * @param x the x property.
	 * @param y the y property.
	 * @param z the z property.
	 * @return the vector.
	 */
	@SuppressWarnings("static-method")
	public Point3ifx newPoint(IntegerProperty x, IntegerProperty y, IntegerProperty z) {
		return new Point3ifx(x, y, z);
	}

	@Override
	public Vector3ifx newVector() {
		return new Vector3ifx();
	}

	@Override
	public Path3ifx newPath(PathWindingRule rule) {
		assert (rule != null) : "Path winding rule must be not null"; //$NON-NLS-1$
		return new Path3ifx(rule);
	}
	
	@Override
	public RectangularPrism3ifx newBox() {
		return new RectangularPrism3ifx();
	}
	
	@Override
	public RectangularPrism3ifx newBox(int x, int y, int z, int width, int height, int depth) {
		assert (width >= 0) : "Width must be positive or zero"; //$NON-NLS-1$
		assert (height >= 0) : "Height must be positive or zero"; //$NON-NLS-1$
		assert (depth >= 0) : "Depth must be positive or zero"; //$NON-NLS-1$
		return new RectangularPrism3ifx(x, y, z, width, height, depth);
	}

	@Override
	public PathElement3ifx newMovePathElement(int x, int y, int z) {
		return new PathElement3ifx.MovePathElement3ifx(
				new SimpleIntegerProperty(x),
				new SimpleIntegerProperty(y),
				new SimpleIntegerProperty(z));
	}

	@Override
	public PathElement3ifx newLinePathElement(int startX, int startY, int startZ, int targetX, int targetY, int targetZ) {
		return new PathElement3ifx.LinePathElement2ifx(
				new SimpleIntegerProperty(startX),
				new SimpleIntegerProperty(startY),
				new SimpleIntegerProperty(startZ),
				new SimpleIntegerProperty(targetX),
				new SimpleIntegerProperty(targetY),
				new SimpleIntegerProperty(targetZ));
	}

	@Override
	public PathElement3ifx newClosePathElement(int lastPointX, int lastPointY, int lastPointZ, int firstPointX,
			int firstPointY, int firstPointZ) {
		return new PathElement3ifx.ClosePathElement3ifx(
				new SimpleIntegerProperty(lastPointX),
				new SimpleIntegerProperty(lastPointY),
				new SimpleIntegerProperty(lastPointZ),
				new SimpleIntegerProperty(firstPointX),
				new SimpleIntegerProperty(firstPointY),
				new SimpleIntegerProperty(firstPointZ));
	}

	@Override
	public PathElement3ifx newCurvePathElement(int startX, int startY, int startZ, int controlX, int controlY, int controlZ,
			int targetX, int targetY, int targetZ) {
		return new PathElement3ifx.QuadPathElement3ifx(
				new SimpleIntegerProperty(startX),
				new SimpleIntegerProperty(startY),
				new SimpleIntegerProperty(startZ),
				new SimpleIntegerProperty(controlX),
				new SimpleIntegerProperty(controlY),
				new SimpleIntegerProperty(controlZ),
				new SimpleIntegerProperty(targetX),
				new SimpleIntegerProperty(targetY),
				new SimpleIntegerProperty(targetZ));
	}

	@Override
	public PathElement3ifx newCurvePathElement(int startX, int startY, int startZ, int controlX1, int controlY1, int controlZ1,
			int controlX2, int controlY2, int controlZ2, int targetX, int targetY, int targetZ) {
		return new PathElement3ifx.CurvePathElement3ifx(
				new SimpleIntegerProperty(startX),
				new SimpleIntegerProperty(startY),
				new SimpleIntegerProperty(startZ),
				new SimpleIntegerProperty(controlX1),
				new SimpleIntegerProperty(controlY1),
				new SimpleIntegerProperty(controlZ1),
				new SimpleIntegerProperty(controlX2),
				new SimpleIntegerProperty(controlY2),
				new SimpleIntegerProperty(controlZ2),
				new SimpleIntegerProperty(targetX),
				new SimpleIntegerProperty(targetY),
				new SimpleIntegerProperty(targetZ));
	}

	@Override
	public Segment3ifx newSegment(int x1, int y1, int z1, int x2, int y2, int z2) {
		return new Segment3ifx(x1, y1, z1, x2, y2, z2);
	}

	@Override
	public MultiShape3ifx<?> newMultiShape() {
		return new MultiShape3ifx<>();
	}

	@Override
	public Quaternion newQuaternion(Vector3D<?, ?> axis, double angle) {
		throw new UnsupportedOperationException("Not yet implemented"); //$NON-NLS-1$ // TODO
	}

	@Override
	public Quaternion newQuaternion(double attitude, double bank, double heading) {
		throw new UnsupportedOperationException("Not yet implemented"); //$NON-NLS-1$ // TODO
	}

}