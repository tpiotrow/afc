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

package org.arakhne.afc.math.continous.object2d;

import org.arakhne.afc.math.generic.Point2D;
import org.arakhne.afc.math.geometry.d2.d.AbstractRectangularShape2d;



/** Abstract implementation of 2D rectangular shapes.
 *
 * @param <T> is the type of the shape implemented by the instance of this class.
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @deprecated see {@link AbstractRectangularShape2d}
 */
@Deprecated
@SuppressWarnings("all")
public abstract class AbstractRectangularShape2f<T extends AbstractRectangularShape2f<T>>
extends AbstractShape2f<T> {

	private static final long serialVersionUID = -2330319571109966087L;

	/** Lowest x-coordinate covered by this rectangular shape. */
	protected float minx = 0f;
	/** Lowest y-coordinate covered by this rectangular shape. */
	protected float miny = 0f;
	/** Highest x-coordinate covered by this rectangular shape. */
	protected float maxx = 0f;
	/** Highest y-coordinate covered by this rectangular shape. */
	protected float maxy = 0f;
	
	/**
	 */
	public AbstractRectangularShape2f() {
		//
	}
	
	/**
	 * @param min is the min corner of the rectangle.
	 * @param max is the max corner of the rectangle.
	 */
	public AbstractRectangularShape2f(Point2f min, Point2f max) {
		setFromCorners(min.getX(), min.getY(), max.getX(), max.getY());
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public AbstractRectangularShape2f(float x, float y, float width, float height) {
		setFromCorners(x, y, x+width, y+height);
	}
	
	/**
	 * @param s
	 */
	public AbstractRectangularShape2f(AbstractRectangularShape2f<?> s) {
		this.minx = s.minx;
		this.miny = s.miny;
		this.maxx = s.maxx;
		this.maxy = s.maxy;
	}

	/** {@inheritDoc}
	 */
	@Override
	public Rectangle2f toBoundingBox() {
		return new Rectangle2f(this.minx, this.miny, this.maxx-this.minx, this.maxy-this.miny);
	}
	
	/** {@inheritDoc}
	 */
	@Override
	public void toBoundingBox(Rectangle2f box) {
		box.setFromCorners(this.minx, this.miny, this.maxx, this.maxy);
	}


	@Override
	public void clear() {
		this.minx = this.miny = this.maxx = this.maxy = 0f;
	}
	
	/** Change the frame of the rectangle.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void set(float x, float y, float width, float height) {
		setFromCorners(x, y, x+width, y+height);
	}
	
	/** Change the frame of te rectangle.
	 * 
	 * @param min is the min corner of the rectangle.
	 * @param max is the max corner of the rectangle.
	 */
	public void set(Point2f min, Point2f max) {
		setFromCorners(min.getX(), min.getY(), max.getX(), max.getY());
	}
	
	@Override
	public void set(Shape2f s) {
		Rectangle2f r = s.toBoundingBox();
		setFromCorners(r.getMinX(), r.getMinY(), r.getMaxX(), r.getMaxY());
	}

	/** Change the width of the rectangle, not the min corner.
	 * 
	 * @param width
	 */
	public void setWidth(float width) {
		this.maxx = this.minx + Math.max(0f, width);
	}

	/** Change the height of the rectangle, not the min corner.
	 * 
	 * @param height
	 */
	public void setHeight(float height) {
		this.maxy = this.miny + Math.max(0f, height);
	}

	/** Change the frame of the rectangle.
	 * 
	 * @param p1 is the coordinate of the first corner.
	 * @param p2 is the coordinate of the second corner.
	 */
	public void setFromCorners(Point2D p1, Point2D p2) {
		setFromCorners(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	/** Change the frame of the rectangle.
	 * 
	 * @param x1 is the coordinate of the first corner.
	 * @param y1 is the coordinate of the first corner.
	 * @param x2 is the coordinate of the second corner.
	 * @param y2 is the coordinate of the second corner.
	 */
	public void setFromCorners(float x1, float y1, float x2, float y2) {
		if (x1<x2) {
			this.minx = x1;
			this.maxx = x2;
		}
		else {
			this.minx = x2;
			this.maxx = x1;
		}
		if (y1<y2) {
			this.miny = y1;
			this.maxy = y2;
		}
		else {
			this.miny = y2;
			this.maxy = y1;
		}
	}
	
	/**
     * Sets the framing rectangle of this <code>Shape</code>
     * based on the specified center point coordinates and corner point
     * coordinates.  The framing rectangle is used by the subclasses of
     * <code>RectangularShape</code> to define their geometry.
     *
     * @param centerX the X coordinate of the specified center point
     * @param centerY the Y coordinate of the specified center point
     * @param cornerX the X coordinate of the specified corner point
     * @param cornerY the Y coordinate of the specified corner point
     */
	public void setFromCenter(float centerX, float centerY, float cornerX, float cornerY) {
		float dx = centerX - cornerX;
		float dy = centerY - cornerY;
		setFromCorners(cornerX, cornerY, centerX + dx, centerY + dy);
	}
	
	/** Replies the min X.
	 * 
	 * @return the min x.
	 */
	public float getMinX() {
		return this.minx;
	}

	/** Set the min X.
	 * 
	 * @param x the min x.
	 */
	public void setMinX(float x) {
		float o = this.maxx;
		if (o<x) {
			this.minx = o;
			this.maxx = x;
		}
		else {
			this.minx = x;
		}
	}

	/** Replies the center x.
	 * 
	 * @return the center x.
	 */
	public float getCenterX() {
		return (this.minx + this.maxx) / 2f;
	}

	/** Replies the max x.
	 * 
	 * @return the max x.
	 */
	public float getMaxX() {
		return this.maxx;
	}

	/** Set the max X.
	 * 
	 * @param x the max x.
	 */
	public void setMaxX(float x) {
		float o = this.minx;
		if (o>x) {
			this.maxx = o;
			this.minx = x;
		}
		else {
			this.maxx = x;
		}
	}

	/** Replies the min y.
	 * 
	 * @return the min y.
	 */
	public float getMinY() {
		return this.miny;
	}

	/** Set the min Y.
	 * 
	 * @param y the min y.
	 */
	public void setMinY(float y) {
		float o = this.maxy;
		if (o<y) {
			this.miny = o;
			this.maxy = y;
		}
		else {
			this.miny = y;
		}
	}

	/** Replies the center y.
	 * 
	 * @return the center y.
	 */
	public float getCenterY() {
		return (this.miny + this.maxy) / 2f;
	}

	/** Replies the max y.
	 * 
	 * @return the max y.
	 */
	public float getMaxY() {
		return this.maxy;
	}
	
	/** Set the max Y.
	 * 
	 * @param y the max y.
	 */
	public void setMaxY(float y) {
		float o = this.miny;
		if (o>y) {
			this.maxy = o;
			this.miny = y;
		}
		else {
			this.maxy = y;
		}
	}

	/** Replies the width.
	 * 
	 * @return the width.
	 */
	public float getWidth() {
		return this.maxx - this.minx;
	}

	/** Replies the height.
	 * 
	 * @return the height.
	 */
	public float getHeight() {
		return this.maxy - this.miny;
	}
	
	@Override
	public void translate(float dx, float dy) {
		this.minx += dx;
		this.miny += dy;
		this.maxx += dx;
		this.maxy += dy;
	}

	/** Replies if this rectangular shape is empty.
	 * The rectangular shape is empty when the
	 * two corners are at the same location.
	 * 
	 * @return <code>true</code> if the rectangular shape is empty;
	 * otherwise <code>false</code>.
	 */
	@Override
	public boolean isEmpty() {
		return this.minx==this.maxx && this.miny==this.maxy; 
	}
	
	/** Inflate this rectangle with the given amounts.
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void inflate(float left, float top, float right, float bottom) {
		this.minx -= left;
		this.miny -= top;
		this.maxx += right;
		this.maxy += bottom;
	}

}
