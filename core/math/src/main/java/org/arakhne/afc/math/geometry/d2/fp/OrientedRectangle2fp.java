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

package org.arakhne.afc.math.geometry.d2.fp;

import java.util.Arrays;

import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.Vector2D;
import org.arakhne.afc.math.geometry.d2.afp.OrientedRectangle2afp;
import org.eclipse.xtext.xbase.lib.Pure;

/** Oriented rectangle with 2 double precision floating-point numbers.
 *
 * @author $Author: sgalland$
 * @author $Author: ngaud$
 * @author $Author: mgrolleau$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public class OrientedRectangle2fp extends AbstractShape2fp<OrientedRectangle2fp>
	implements OrientedRectangle2afp<Shape2fp<?>, OrientedRectangle2fp, PathElement2fp, Point2fp, Rectangle2fp> {

	private static final long serialVersionUID = 7619908159953423088L;

	/**
	 * Center of the OBR
	 */
	private double cx;

	/**
	 * Center of the OBR
	 */
	private double cy;

	/**
	 * X coordinate of the first axis of the OBR
	 */
	private double rx;

	/**
	 * Y coordinate of the first axis of the OBR
	 */
	private double ry;

	/**
	 * X coordinate of the second axis of the OBR
	 */
	private double sx;

	/**
	 * Y coordinate of the second axis of the OBR
	 */
	private double sy;

	/**
	 * Half-size of the first axis of the OBR
	 */
	private double extentR;

	/**
	 * Half-size of the second axis of the OBR
	 */
	private double extentS;

	/** Create an empty oriented rectangle.
	 */
	public OrientedRectangle2fp() {
		//
	}

	/** Create an oriented rectangle from the given OBR.
	 * 
	 * @param obr
	 */
	public OrientedRectangle2fp(OrientedRectangle2afp<?, ?, ?, ?, ?> obr) {
		set(obr.getCenterX(), obr.getCenterY(),
				obr.getFirstAxisX(), obr.getFirstAxisY(),
				obr.getFirstAxisExtent(),
				obr.getSecondAxisX(), obr.getSecondAxisY(), obr.getSecondAxisExtent());
	}

	/** Construct an oriented rectangle from the given cloud of points.
	 *
	 * @param pointCloud - the cloud of points.
	 */
	public OrientedRectangle2fp(Iterable<? extends Point2D> pointCloud) {
		setFromPointCloud(pointCloud);
	}

	/** Construct an oriented rectangle from the given cloud of points.
	 *
	 * @param pointCloud - the cloud of points.
	 */
	public OrientedRectangle2fp(Point2D... pointCloud) {
		setFromPointCloud(Arrays.asList(pointCloud));
	}

	/** Construct an oriented rectangle.
	 *
	 * @param centerX is the X coordinate of the OBR center.
	 * @param centerY is the Y coordinate of the OBR center.
	 * @param axis1X is the X coordinate of first axis of the OBR.
	 * @param axis1Y is the Y coordinate of first axis of the OBR.
	 * @param axis1Extent is the extent of the first axis.
	 * @param axis2Extent is the extent of the second axis.
	 */
	public OrientedRectangle2fp(double centerX, double centerY,
			double axis1X, double axis1Y, double axis1Extent,
			double axis2Extent) {
		set(centerX, centerY, axis1X, axis1Y, axis1Extent, axis2Extent);
	}

	/** Construct an oriented rectangle.
	 *
	 * @param center is the OBR center.
	 * @param axis1 is the first axis of the OBR.
	 * @param axis1Extent is the extent of the first axis.
	 * @param axis2Extent is the extent of the second axis.
	 */
	public OrientedRectangle2fp(Point2D center, Vector2D axis1, double axis1Extent, double axis2Extent) {
		set(center, axis1, axis1Extent, axis2Extent);
	}

	@Pure
	@Override
	public int hashCode() {
		long bits = 1;
		bits = 31 * bits + Double.doubleToLongBits(this.cx);
		bits = 31 * bits + Double.doubleToLongBits(this.cy);
		bits = 31 * bits + Double.doubleToLongBits(this.rx);
		bits = 31 * bits + Double.doubleToLongBits(this.ry);
		bits = 31 * bits + Double.doubleToLongBits(this.extentR);
		bits = 31 * bits + Double.doubleToLongBits(this.sx);
		bits = 31 * bits + Double.doubleToLongBits(this.sy);
		bits = 31 * bits + Double.doubleToLongBits(this.extentS);
		int b = (int) bits;
		return b ^ (b >> 32);
	}

	@Pure
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("["); //$NON-NLS-1$
		b.append(getCenterX());
		b.append(";"); //$NON-NLS-1$
		b.append(getCenterY());
		b.append(";"); //$NON-NLS-1$
		b.append(getFirstAxisX());
		b.append(";"); //$NON-NLS-1$
		b.append(getFirstAxisY());
		b.append(";"); //$NON-NLS-1$
		b.append(getFirstAxisExtent());
		b.append(";"); //$NON-NLS-1$
		b.append(getSecondAxisX());
		b.append(";"); //$NON-NLS-1$
		b.append(getSecondAxisY());
		b.append(";"); //$NON-NLS-1$
		b.append(getSecondAxisExtent());
		b.append("]"); //$NON-NLS-1$
		return b.toString();
	}

	@Pure
	@Override
	public Point2D getCenter() {
		return new Point2fp(this.cx, this.cy);
	}

	@Pure
	@Override
	public double getCenterX() {
		return this.cx;
	}
	
	@Override
	public void setCenterX(double cx) {
		this.cx = cx;
	}

	@Override
	public void setCenterY(double cy) {
		this.cy = cy;
	}

	@Pure
	@Override
	public double getCenterY() {
		return this.cy;
	}

	@Override
	public void setCenter(double cx, double cy) {
		this.cx = cx;
		this.cy = cy;
	}

	@Pure
	@Override
	public Vector2D getFirstAxis() {
		return new Vector2fp(this.rx, this.ry);
	}

	@Pure
	@Override
	public double getFirstAxisX() {
		return this.rx;
	}

	@Pure
	@Override
	public double getFirstAxisY() {
		return this.ry;
	}

	@Pure
	@Override
	public Vector2D getSecondAxis() {
		return new Vector2fp(this.sx, this.sy);
	}

	@Pure
	@Override
	public double getSecondAxisX() {
		return this.sx;
	}

	@Pure
	@Override
	public double getSecondAxisY() {
		return this.sy;
	}

	@Pure
	@Override
	public double getFirstAxisExtent() {
		return this.extentR;
	}

	@Override
	public void setFirstAxisExtent(double extent) {
		this.extentR = Math.max(0, extent);
	}

	@Pure
	@Override
	public double getSecondAxisExtent() {
		return this.extentS;
	}

	@Override
	public void setSecondAxisExtent(double extent) {
		this.extentS = Math.max(0,  extent);
	}

	@Override
	public void setFirstAxis(double x, double y, double extent) {
		assert(Vector2D.isUnitVector(x, y));
		this.rx = x;
		this.ry = y;
		this.extentR = Math.max(0, extent);
	}

	@Override
	public void setSecondAxis(double x, double y, double extent) {
		assert(Vector2D.isUnitVector(x, y));
		this.sx = x;
		this.sy = y;
		this.extentS = Math.max(0, extent);
	}

	@Override
	public void set(double centerX, double centerY, double axis1x, double axis1y, double axis1Extent, double axis2x,
			double axis2y, double axis2Extent) {
		this.cx = centerX;
		this.cy = centerY;
		assert(Vector2D.isUnitVector(axis1x, axis1y));
		this.rx = axis1x;
		this.ry = axis1y;
		this.extentR = Math.max(0, axis1Extent);
		assert(Vector2D.isUnitVector(axis2x, axis2y));
		this.sx = axis2x;
		this.sy = axis2y;
		this.extentS = Math.max(0, axis2Extent);
	}

}
