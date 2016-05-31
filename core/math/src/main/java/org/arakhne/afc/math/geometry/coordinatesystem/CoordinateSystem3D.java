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

package org.arakhne.afc.math.geometry.coordinatesystem;

import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Represents the different kind of 3D referencials and provides the convertion utilities.
 *
 * <p>
 * A referencial axis is expressed by the front, left and top directions. For example <code>XYZ_LEFT_HAND</code> is for the
 * coordinate system with front direction along <code>+/-X</code> axis, left direction along the <code>+/-Y</code> axis and top
 * direction along the <code>+/-Z</code> axis according to a "left-hand" heuristic.
 *
 * <p>
 * The default coordinate system is:
 * <ul>
 * <li>front: <code>(1, 0, 0)</code></li>
 * <li>left: <code>(0, 1, 0)</code></li>
 * <li>top: <code>(0, 0, 1)</code></li>
 * </ul>
 *
 * <h3>Rotations</h3>
 *
 * <p>
 * Rotations in a 3D coordinate system follow the right/left hand rules (assuming that <code>OX</code>, <code>OY</code> and
 * <code>OZ</code> are the three axis of the coordinate system):
 * <table border="1">
 * <tr>
 * <td>Right-handed rule:</td>
 * <td>
 * <ul>
 * <li>axis cycle is: <code>OX</code> &gt; <code>OY</code> &gt; <code>OZ</code> &gt; <code>OX</code> &gt; <code>OY</code>;</li>
 * <li>when rotating around <code>OX</code>, positive rotation angle is going from <code>OY</code> to <code>OZ</code></li>
 * <li>when rotating around <code>OY</code>, positive rotation angle is going from <code>OZ</code> to <code>OX</code></li>
 * <li>when rotating around <code>OZ</code>, positive rotation angle is going from <code>OX</code> to <code>OY</code></li>
 * </ul>
 * <br>
 * <a href=""><img border="0" width="200" src="doc-files/rotation_right.png" alt="[Right-handed Rotation Rule]"></a></td>
 * </tr>
 * <tr>
 * <td>Left-handed rule:</td>
 * <td>
 * <ul>
 * <li>axis cycle is: <code>OX</code> &gt; <code>OY</code> &gt; <code>OZ</code> &gt; <code>OX</code> &gt; <code>OY</code>;</li>
 * <li>when rotating around <code>OX</code>, positive rotation angle is going from <code>OY</code> to <code>OZ</code></li>
 * <li>when rotating around <code>OY</code>, positive rotation angle is going from <code>OZ</code> to <code>OX</code></li>
 * <li>when rotating around <code>OZ</code>, positive rotation angle is going from <code>OX</code> to <code>OY</code></li>
 * </ul>
 * <br>
 * <a href=""><img border="0" width="200" src="doc-files/rotation_left.png" alt="[Left-handed Rotation Rule]"></a></td>
 * </tr>
 * </table>
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public enum CoordinateSystem3D implements CoordinateSystem {

    /**
     * Left handed XZY coordinate system.
     *
     * <p>
     * <a hef="doc-files/xzy_left.png"><img src="doc-files/xzy_left.png" border="0" width="200" alt=
     * "[Left Handed XZY Coordinate System]"></a>
     */
    XZY_LEFT_HAND(0, 1, 1, 0),

    /**
     * Left handed XYZ coordinate system.
     *
     * <p>
     * <a hef="doc-files/xyz_left.png"><img src="doc-files/xyz_left.png" border="0" width="200" alt=
     * "[Left Handed XYZ Coordinate System]"></a>
     */
    XYZ_LEFT_HAND(-1, 0, 0, 1),

    /**
     * Right handed XZY coordinate system.
     *
     * <p>
     * <a hef="doc-files/xzy_right.png"><img src="doc-files/xzy_right.png" border="0" width="200" alt=
     * "[Right Handed XZY Coordinate System]"></a>
     */
    XZY_RIGHT_HAND(0, -1, 1, 0),

    /**
     * Right handed XYZ coordinate system.
     *
     * <p>
     * <a hef="doc-files/xyz_right.png"><img src="doc-files/xyz_right.png" border="0" width="200" alt=
     * "[Right Handed XYZ Coordinate System]"></a>
     */
    XYZ_RIGHT_HAND(1, 0, 0, 1);

    private static final byte PIVOT_SYSTEM = 0;

    private static CoordinateSystem3D userDefault;

    private final byte system;

    CoordinateSystem3D(int lefty, int leftz, int topy, int topz) {
        this.system = toSystemIndex(lefty, leftz, topy, topz);
    }

    @Pure
    @SuppressWarnings({ "checkstyle:returncount", "checkstyle:cyclomaticcomplexity" })
    private static byte toSystemIndex(int lefty, int leftz, int topy, int topz) {
        if (lefty < 0) {
            if (leftz == 0 && topy == 0 && topz != 0) {
                if (topz < 0) {
                    return 1;
                }
                return 2;
            }
        } else if (lefty > 0) {
            if (leftz == 0 && topy == 0 && topz != 0) {
                if (topz < 0) {
                    return 3;
                }
                return 0;
            }
        } else {
            if (lefty == 0 && leftz != 0) {
                if (leftz < 0) {
                    if (topz == 0 && topy != 0) {
                        if (topy < 0) {
                            return 4;
                        }
                        return 5;
                    }
                } else {
                    if (topz == 0 && topy != 0) {
                        if (topy < 0) {
                            return 6;
                        }
                        return 7;
                    }
                }
            }
        }
        throw new CoordinateSystemNotFoundException();
    }

    @Pure
    private static double[] fromSystemIndex(int index) {
        // Compute the lower right sub-matrix
        final double c1;
        final double c2;
        final double c3;
        final double c4;
        switch (index) {
        case 1:
            c1 = -1;
            c2 = 0;
            c3 = 0;
            c4 = -1;
            break;
        case 2:
            c1 = -1;
            c2 = 0;
            c3 = 0;
            c4 = 1;
            break;
        case 3:
            c1 = 1;
            c2 = 0;
            c3 = 0;
            c4 = -1;
            break;
        case 4:
            c1 = 0;
            c2 = -1;
            c3 = -1;
            c4 = 0;
            break;
        case 5:
            c1 = 0;
            c2 = -1;
            c3 = 1;
            c4 = 0;
            break;
        case 6:
            c1 = 0;
            c2 = 1;
            c3 = -1;
            c4 = 0;
            break;
        case 7:
            c1 = 0;
            c2 = 1;
            c3 = 1;
            c4 = 0;
            break;
        default:
            c1 = 1;
            c2 = 0;
            c3 = 0;
            c4 = 1;
            break;
        }

        return new double[] {c1, c2, c3, c4 }
    }

    /**
     * {@inheritDoc}
     */
    @Pure
    @Override
    public boolean isRightHanded() {
        return this == XYZ_RIGHT_HAND || this == XZY_RIGHT_HAND;
    }

    /**
     * {@inheritDoc}
     */
    @Pure
    @Override
    public boolean isLeftHanded() {
        return this == XYZ_LEFT_HAND || this == XZY_LEFT_HAND;
    }

    /**
     * Replies the preferred coordinate system.
     *
     * @return the preferred coordinate system.
     */
    @Pure
    public static CoordinateSystem3D getDefaultCoordinateSystem() {
        if (userDefault != null) {
            return userDefault;
        }
        return CoordinateSystemConstants.SIMULATION_3D;
    }

    /**
     * {@inheritDoc}
     */
    @Pure
    @Override
    public final int getDimensions() {
        return 3;
    }

    /**
     * {@inherit-doc}
     * 
     * @param userDefault
     */
    public static void setDefaultCoordinateSystem(CoordinateSystem3D userDefault) {
        CoordinateSystem3D.userDefault = userDefault;
    }
}
