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

package org.arakhne.afc.math;

/** Several mathematical constants.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public final class MathConstants {

	/** Inside zone according to the Cohen-Sutherland algorithm: {@code x in [xmin;xmax] and y in [ymin;ymax]}.
	 */
	public static final int COHEN_SUTHERLAND_INSIDE = 0;

	/** Left zone according to the Cohen-Sutherland algorithm: {@code x < xmin}.
	 */
	public static final int COHEN_SUTHERLAND_LEFT = 1;

	/** Right zone according to the Cohen-Sutherland algorithm: {@code x > xmax}.
	 */
	public static final int COHEN_SUTHERLAND_RIGHT = 2;

	/** Bottom zone according to the Cohen-Sutherland algorithm: {@code y < ymin}.
	 */
	public static final int COHEN_SUTHERLAND_BOTTOM = 4;

	/** Top zone according to the Cohen-Sutherland algorithm: {@code y > ymax}.
	 */
	public static final int COHEN_SUTHERLAND_TOP = 8;

	/** Front zone according to the Cohen-Sutherland algorithm.
	 */
	public static final int COHEN_SUTHERLAND_FRONT = 16;

	/** Behind zone according to the Cohen-Sutherland algorithm.
	 */
	public static final int COHEN_SUTHERLAND_BACK = 32;

	/** PI.
	 */
	public static final double PI = Math.PI;

	/** E.
	 */
	public static final double E = Math.E;

	/** 2 * PI.
	 */
	public static final double TWO_PI = 2. * PI;

	/** PI + PI/2.
	 */
	public static final double ONE_HALF_PI = 1.5 * PI;

	/** PI/2.
	 */
	public static final double DEMI_PI = .5 * PI;

	/** PI/4.
	 */
	public static final double QUARTER_PI = .25 * PI;

	/** 3*PI/4.
	 */
	public static final double THREE_QUARTER_PI = .75 * PI;

	/** Square root of 2.
	 */
	public static final double SQRT_2 = Math.sqrt(2.);

	/**
	 * Max sweeps in the Jacoby's algorithms.
	 */
	public static final int JACOBI_MAX_SWEEPS = 32;

	/**
	 * Number precision for the Jacoby's algorithms.
	 *
	 * <p>Given by the Java3D's implementation of the Matrix3d class.
	 */
	public static final double JACOBI_EPSILON = 1.110223024E-16;

	/**
	 * Max sweeps in the Ellipse's algorithms.
	 *
	 * @deprecated since 13.0
	 */
	@Deprecated
	public static final int ELLIPSE_MAX_SWEEPS = 32;

	/** This is the maximale distance that
	 * permits to detect hits.
	 *
	 * @deprecated since 13.0
	 */
	@Deprecated
	public static final double HIT_DISTANCE = 5;

	/** The maximum distance that the line
	 *  segments used to approximate the
	 *  curved segments are allowed to
	 *  deviate from any point on the
	 *  original curve.
	 *
	 *  <p>This attributes is used to parameter the approximation
	 *  of the curve rendering.
	 */
	public static final double SPLINE_APPROXIMATION_RATIO = .1;

	/**
	 * The rectangle intersection test counts the number of times
	 * that the path crosses through the shadow that the rectangle
	 * projects to the right towards (x =&gt; +INFINITY).
	 *
	 * <p>During processing of the path it actually counts every time
	 * the path crosses either or both of the top and bottom edges
	 * of that shadow.  If the path enters from the top, the count
	 * is incremented.  If it then exits back through the top, the
	 * same way it came in, the count is decremented and there is
	 * no impact on the winding count.  If, instead, the path exits
	 * out the bottom, then the count is incremented again and a
	 * full pass through the shadow is indicated by the winding count
	 * having been incremented by 2.
	 *
	 * <p>Thus, the winding count that it accumulates is actually double
	 * the real winding count.  Since the path is continuous, the
	 * final answer should be a multiple of 2, otherwise there is a
	 * logic error somewhere.
	 *
	 * <p>If the path ever has a direct hit on the rectangle, then a
	 * special value is returned.  This special value terminates
	 * all ongoing accumulation on up through the call chain and
	 * ends up getting returned to the calling function which can
	 * then produce an answer directly.  For intersection tests,
	 * the answer is always "true" if the path intersects the
	 * rectangle.  For containment tests, the answer is always
	 * "false" if the path intersects the rectangle.  Thus, no
	 * further processing is ever needed if an intersection occurs.
	 */
	public static final int SHAPE_INTERSECTS = 0x80000000;

	/**
	 * Number precision for testing is a vector is a unit vector.
	 *
	 * <p>The approximation introduced by the vector's length computation make this
	 * epsilon still mandatory.
	 */
	public static final double UNIT_VECTOR_EPSILON = 1E-5;

	/**
	 * Number precision for testing is the orthogonality of two unit vectors.
	 *
	 * <p>The approximation introduced by the vector's length computation make this
	 * epsilon still mandatory.
	 */
	public static final double ORTHOGONAL_VECTOR_EPSILON = 1E-5;

	private MathConstants() {
		//
	}

}
