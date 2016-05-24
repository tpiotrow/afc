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
import org.arakhne.afc.math.generic.Tuple2D;
import org.arakhne.afc.math.generic.Vector2D;
import org.arakhne.afc.math.geometry.d2.d.Point2d;

/** 2D Point with 2 floating-point numbers.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @deprecated see {@link Point2d}
 */
@Deprecated
@SuppressWarnings("all")
public class Point2f extends Tuple2f<Point2D> implements Point2D {

	private static final long serialVersionUID = 8963319137253544821L;

	/**
	 */
	public Point2f() {
		//
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Point2f(Tuple2D<?> tuple) {
		super(tuple);
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Point2f(int[] tuple) {
		super(tuple);
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Point2f(float[] tuple) {
		super(tuple);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Point2f(int x, int y) {
		super(x,y);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Point2f(float x, float y) {
		super(x,y);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Point2f(double x, double y) {
		super((float)x,(float)y);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Point2f(long x, long y) {
		super(x,y);
	}

	/** {@inheritDoc}
	 */
	@Override
	public Point2f clone() {
		return (Point2f)super.clone();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float distanceSquared(Point2D p1) {
	      float dx, dy;
	      dx = this.x-p1.getX();  
	      dy = this.y-p1.getY();
	      return (dx*dx+dy*dy);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float distance(Point2D p1) {
	      float  dx, dy;
	      dx = this.x-p1.getX();  
	      dy = this.y-p1.getY();
	      return (float)Math.sqrt(dx*dx+dy*dy);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float distanceL1(Point2D p1) {
	      return (Math.abs(this.x-p1.getX()) + Math.abs(this.y-p1.getY()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public float distanceLinf(Point2D p1) {
	      return Math.max( Math.abs(this.x-p1.getX()), Math.abs(this.y-p1.getY()));
	}

	@Override
	public void add(Point2D t1, Vector2D t2) {
		this.x = t1.getX() + t2.getX();
		this.y = t1.getY() + t2.getY();
	}

	@Override
	public void add(Vector2D t1, Point2D t2) {
		this.x = t1.getX() + t2.getX();
		this.y = t1.getY() + t2.getY();
	}

	@Override
	public void add(Vector2D t1) {
		this.x += t1.getX();
		this.y += t1.getY();
	}

	@Override
	public void scaleAdd(int s, Vector2D t1, Point2D t2) {
		this.x = s * t1.getX() + t2.getX();
		this.y = s * t1.getY() + t2.getY();
	}

	@Override
	public void scaleAdd(float s, Vector2D t1, Point2D t2) {
		this.x = s * t1.getX() + t2.getX();
		this.y = s * t1.getY() + t2.getY();
	}

	@Override
	public void scaleAdd(int s, Point2D t1, Vector2D t2) {
		this.x = s * t1.getX() + t2.getX();
		this.y = s * t1.getY() + t2.getY();
	}

	@Override
	public void scaleAdd(float s, Point2D t1, Vector2D t2) {
		this.x = s * t1.getX() + t2.getX();
		this.y = s * t1.getY() + t2.getY();
	}

	@Override
	public void scaleAdd(int s, Vector2D t1) {
		this.x = s * this.x + t1.getX();
		this.y = s * this.y + t1.getY();
	}

	@Override
	public void scaleAdd(float s, Vector2D t1) {
		this.x = s * this.x + t1.getX();
		this.y = s * this.y + t1.getY();
	}

	@Override
	public void sub(Point2D t1, Vector2D t2) {
		this.x = t1.getX() - t2.getX();
		this.y = t1.getY() - t2.getY();
	}

	@Override
	public void sub(Vector2D t1) {
		this.x -= t1.getX();
		this.y -= t1.getY();
	}

}
