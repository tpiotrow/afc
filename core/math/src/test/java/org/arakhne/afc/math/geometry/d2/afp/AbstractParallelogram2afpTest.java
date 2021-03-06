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

package org.arakhne.afc.math.geometry.d2.afp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import org.arakhne.afc.math.MathConstants;
import org.arakhne.afc.math.geometry.PathElementType;
import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.Shape2D;
import org.arakhne.afc.math.geometry.d2.Transform2D;
import org.arakhne.afc.math.geometry.d2.Tuple2D;
import org.arakhne.afc.math.geometry.d2.Vector2D;

@SuppressWarnings("all")
public abstract class AbstractParallelogram2afpTest<T extends Parallelogram2afp<?, T, ?, ?, ?, B>,
B extends Rectangle2afp<?, ?, ?, ?, ?, B>> extends AbstractShape2afpTest<T, B> {

    protected final double cx = 6;
    protected final double cy = 9;
    protected final double ux = 2.425356250363330e-01;   
    protected final double uy = 9.701425001453320e-01;
    protected final double e1 = 9.219544457292887;
    protected final double vx = -7.071067811865475e-01;
    protected final double vy = 7.071067811865475e-01;
    protected final double e2 = 1.264911064067352e+01;

    // Points' names are in the ggb diagram
    protected final double pEx = 12.7082;
    protected final double pEy = -8.88854;
    protected final double pFx = 17.18034;
    protected final double pFy = 9;
    protected final double pGx = -0.7082;
    protected final double pGy = 26.88854;
    protected final double pHx = -5.18034;
    protected final double pHy = 9;

    @Override
    protected final T createShape() {
        return (T) createParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2);
    }

    @Test
    public void staticCalculatesOrthogonalAxes() {
        double obrux = 0.8944271909999159;
        double obruy = -0.4472135954999579;
        double obrvx = 0.4472135954999579;
        double obrvy = 0.8944271909999159;
        List points = Arrays.asList(
                createPoint(11.7082, -0.94427), createPoint(16.18034, 8),
                createPoint(-1.7082, 16.94427), createPoint(-6.18034, 8));
        Vector2D R, S;
        R = createVector(Double.NaN, Double.NaN);
        S = createVector(Double.NaN, Double.NaN);
        Parallelogram2afp.calculatesOrthogonalAxes(points, R, S);
        assertEpsilonEquals(obrux, R.getX());
        assertEpsilonEquals(obruy, R.getY());
        assertEpsilonEquals(obrvx, S.getX());
        assertEpsilonEquals(obrvy, S.getY());
    }	

    @Test
    public void staticFindsVectorProjectionRAxisPoint() {
        assertEpsilonEquals(-this.e1, Parallelogram2afp.findsVectorProjectionRAxisPoint(this.ux, this.uy, this.vx, this.vy, this.pEx - this.cx, this.pEy - this.cy));
        assertEpsilonEquals(this.e1, Parallelogram2afp.findsVectorProjectionRAxisPoint(this.ux, this.uy, this.vx, this.vy, this.pFx - this.cx, this.pFy - this.cy));
        assertEpsilonEquals(this.e1, Parallelogram2afp.findsVectorProjectionRAxisPoint(this.ux, this.uy, this.vx, this.vy, this.pGx - this.cx, this.pGy - this.cy));
        assertEpsilonEquals(-this.e1, Parallelogram2afp.findsVectorProjectionRAxisPoint(this.ux, this.uy, this.vx, this.vy, this.pHx - this.cx, this.pHy - this.cy));
        assertEpsilonEquals(-12.36932, Parallelogram2afp.findsVectorProjectionRAxisPoint(this.ux, this.uy, this.vx, this.vy, -this.cx, -this.cy));
    }

    @Test
    public void staticFindsVectorProjectionSAxisVector() {
        assertEpsilonEquals(-this.e2, Parallelogram2afp.findsVectorProjectionSAxisVector(this.ux, this.uy, this.vx, this.vy, this.pEx - this.cx, this.pEy - this.cy));
        assertEpsilonEquals(-this.e2, Parallelogram2afp.findsVectorProjectionSAxisVector(this.ux, this.uy, this.vx, this.vy, this.pFx - this.cx, this.pFy - this.cy));
        assertEpsilonEquals(this.e2, Parallelogram2afp.findsVectorProjectionSAxisVector(this.ux, this.uy, this.vx, this.vy, this.pGx - this.cx, this.pGy - this.cy));
        assertEpsilonEquals(this.e2, Parallelogram2afp.findsVectorProjectionSAxisVector(this.ux, this.uy, this.vx, this.vy, this.pHx - this.cx, this.pHy - this.cy));
        assertEpsilonEquals(4.24264, Parallelogram2afp.findsVectorProjectionSAxisVector(this.ux, this.uy, this.vx, this.vy, -this.cx, -this.cy));
    }

    @Test
    public void staticCalculatesCenterPointAxisExtents() {
        List points = Arrays.asList(
                createPoint(this.pEx, this.pEy), createPoint(this.pGx, this.pGy),
                createPoint(this.pFx, this.pFy), createPoint(this.pEx, this.pEy));
        Vector2D R, S;
        Point2D center;
        Tuple2D extents;
        R = createVector(this.ux, this.uy);
        S = createVector(this.vx, this.vy);
        center = createPoint(Double.NaN, Double.NaN);
        extents = createVector(Double.NaN, Double.NaN);
        Parallelogram2afp.calculatesCenterPointAxisExtents(points, R, S, center, extents);
        assertEpsilonEquals(this.cx, center.getX());
        assertEpsilonEquals(this.cy, center.getY());
        assertEpsilonEquals(this.e1, extents.getX());
        assertEpsilonEquals(this.e2, extents.getY());
    }

    @Test
    public void staticFindsClosestPointPointParallelogram() {
        Point2D closest;

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(
                -20, 9,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                closest);
        assertFpPointEquals(this.pHx, this.pHy, closest);

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(
                0, 0,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                closest);
        assertFpPointEquals(1.90983, 1.90983, closest);

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(
                5, -10,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                closest);
        assertFpPointEquals(9.40983, -5.59017, closest);

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(
                14, -20,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                closest);
        assertFpPointEquals(this.pEx, this.pEy, closest);

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(
                -6, 15,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                closest);
        assertFpPointEquals(-3.81679, 14.4542, closest);

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(
                0, 10,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                closest);
        assertFpPointEquals(0, 10, closest);

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(
                10, 0,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                closest);
        assertFpPointEquals(10, 0, closest);

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(
                15, -4,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                closest);
        assertFpPointEquals(13.99326, -3.74832, closest);

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(
                -5, 25,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                closest);
        assertFpPointEquals(-1.40503, 24.10126, closest);

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(
                0, 20,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                closest);
        assertFpPointEquals(0, 20, closest);

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(
                10, 10,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                closest);
        assertFpPointEquals(10, 10, closest);

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(
                20, 0,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                closest);
        assertFpPointEquals(15.22856, 1.19286, closest);

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(
                -3, 35,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                closest);
        assertFpPointEquals(this.pGx, this.pGy, closest);

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(
                5, 35,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                closest);
        assertFpPointEquals(this.pGx, this.pGy, closest);

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(
                20, 15,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                closest);
        assertFpPointEquals(15.59017, 10.59017, closest);

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(
                35, 10,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                closest);
        assertFpPointEquals(this.pFx, this.pFy, closest);

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(
                -8, 29,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                closest);
        assertFpPointEquals(this.pGx, this.pGy, closest);

        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(0, 0,
                4.7, 15,
                0.12403, 0.99228,
                18.02776,
                -0.44721, 0.89443,
                20,
                closest);
        assertFpPointEquals(0.81573, 0.40786, closest);

        // In triangle.ggb
        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(-5.2964, 3.19501,
                -10, 7,
                -0.9863939238321437, 0.1643989873053573,
                1,
                0.9998000599800071, 0.01999600119960014,
                2,
                closest);
        assertFpPointEquals(-7.01401, 6.87559, closest);

        // In triangle.ggb
        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(-1, -2,
                0, -6,
                -0.9863939238321437, 0.1643989873053573,
                1,
                0.9998000599800071, 0.01999600119960014,
                2,
                closest);
        assertFpPointEquals(-0.92331, -5.83434, closest);

        // In segment.ggb
        closest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsClosestPointPointParallelogram(0, 0,
                -10, -3,
                -.8944271909999159, .4472135954999579,
                2,
                .5547001962252290, -.8320502943378436,
                1,
                closest);
        assertFpPointEquals(-7.65645, -4.72648, closest);		
    }

    @Test
    public void staticFindsFarthestPointPointParallelogram() {
        Point2D farthest;

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(
                -20, 9,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                farthest);
        assertFpPointEquals(this.pEx, this.pEy, farthest);

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(
                0, 0,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                farthest);
        assertFpPointEquals(this.pGx, this.pGy, farthest);

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(
                5, -10,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                farthest);
        assertFpPointEquals(this.pGx, this.pGy, farthest);

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(
                14, -20,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                farthest);
        assertFpPointEquals(this.pGx, this.pGy, farthest);

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(
                -6, 15,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                farthest);
        assertFpPointEquals(this.pEx, this.pEy, farthest);

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(
                0, 10,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                farthest);
        assertFpPointEquals(this.pEx, this.pEy, farthest);

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(
                10, 0,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                farthest);
        assertFpPointEquals(this.pGx, this.pGy, farthest);

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(
                15, -4,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                farthest);
        assertFpPointEquals(this.pGx, this.pGy, farthest);

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(
                -5, 25,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                farthest);
        assertFpPointEquals(this.pEx, this.pEy, farthest);

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(
                0, 20,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                farthest);
        assertFpPointEquals(this.pEx, this.pEy, farthest);

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(
                10, 10,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                farthest);
        assertFpPointEquals(this.pGx, this.pGy, farthest);

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(
                20, 0,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                farthest);
        assertFpPointEquals(this.pGx, this.pGy, farthest);

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(
                -3, 35,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                farthest);
        assertFpPointEquals(this.pEx, this.pEy, farthest);

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(
                5, 35,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                farthest);
        assertFpPointEquals(this.pEx, this.pEy, farthest);

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(
                20, 15,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                farthest);
        assertFpPointEquals(this.pHx, this.pHy, farthest);

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(
                35, 10,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                farthest);
        assertFpPointEquals(this.pHx, this.pHy, farthest);

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(
                -8, 29,
                this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                farthest);
        assertFpPointEquals(this.pEx, this.pEy, farthest);

        farthest = createPoint(Double.NaN, Double.NaN);
        Parallelogram2afp.findsFarthestPointPointParallelogram(0, 0,
                4.7, 15,
                0.12403, 0.99228,
                18.02776,
                -0.44721, 0.89443,
                20,
                farthest);
        assertFpPointEquals(-2.0082, 50.77719, farthest);
    }

    @Test
    public void staticContainsParallelogramPoint() {
        assertFalse(Parallelogram2afp.containsParallelogramPoint(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 0));
        assertFalse(Parallelogram2afp.containsParallelogramPoint(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -20, 0));
        assertTrue(Parallelogram2afp.containsParallelogramPoint(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                12, -4));
        assertTrue(Parallelogram2afp.containsParallelogramPoint(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                14, 0));
        assertFalse(Parallelogram2afp.containsParallelogramPoint(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                15, 0));
        assertFalse(Parallelogram2afp.containsParallelogramPoint(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                20, 8));
        assertTrue(Parallelogram2afp.containsParallelogramPoint(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                8, 16));
        assertFalse(Parallelogram2afp.containsParallelogramPoint(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -4, 20));
        assertFalse(Parallelogram2afp.containsParallelogramPoint(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -5, 12));

        assertTrue(Parallelogram2afp.containsParallelogramPoint(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 6));
        assertTrue(Parallelogram2afp.containsParallelogramPoint(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 7));
        assertTrue(Parallelogram2afp.containsParallelogramPoint(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 8));
        assertTrue(Parallelogram2afp.containsParallelogramPoint(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 9));
        assertTrue(Parallelogram2afp.containsParallelogramPoint(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 10));
        assertFalse(Parallelogram2afp.containsParallelogramPoint(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 27));

        assertTrue(Parallelogram2afp.containsParallelogramPoint(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                this.cx, this.cy));

        assertTrue(Parallelogram2afp.containsParallelogramPoint(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                16, 8));
    }

    @Test
    public void staticContainsParallelogramRectangle() {
        assertFalse(Parallelogram2afp.containsParallelogramRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 0, 1, 1));
        assertFalse(Parallelogram2afp.containsParallelogramRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 1, 1, 1));
        assertFalse(Parallelogram2afp.containsParallelogramRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 2, 1, 1));
        assertFalse(Parallelogram2afp.containsParallelogramRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 3, 1, 1));
        assertTrue(Parallelogram2afp.containsParallelogramRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 4, 1, 1));
        assertTrue(Parallelogram2afp.containsParallelogramRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 5, 1, 1));
        assertTrue(Parallelogram2afp.containsParallelogramRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 6, 1, 1));
    }

    @Test
    public void staticIntersectsParallelogramSegment() {
        assertFalse(Parallelogram2afp.intersectsParallelogramSegment(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 0, 1, 1));
        assertTrue(Parallelogram2afp.intersectsParallelogramSegment(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                5, 5, 4, 6));
        assertTrue(Parallelogram2afp.intersectsParallelogramSegment(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                2, -2, 5, 0));
        assertFalse(Parallelogram2afp.intersectsParallelogramSegment(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -20, -5, -10, 6));
        assertFalse(Parallelogram2afp.intersectsParallelogramSegment(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -5, 0, -10, 16));
        assertTrue(Parallelogram2afp.intersectsParallelogramSegment(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -10, 1, 10, 20));
    }

    @Test
    public void staticIntersectsParallelogramCircle() {
        assertFalse(Parallelogram2afp.intersectsParallelogramCircle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                .5, .5, .5));
        assertFalse(Parallelogram2afp.intersectsParallelogramCircle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                .5, 1.5, .5));
        assertFalse(Parallelogram2afp.intersectsParallelogramCircle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                .5, 2.5, .5));
        assertTrue(Parallelogram2afp.intersectsParallelogramCircle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                .5, 3.5, .5));
        assertTrue(Parallelogram2afp.intersectsParallelogramCircle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                4.5, 3.5, .5));

        assertFalse(Parallelogram2afp.intersectsParallelogramCircle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                10, -7, .5));
        assertFalse(Parallelogram2afp.intersectsParallelogramCircle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                10.1, -7, .5));
        assertTrue(Parallelogram2afp.intersectsParallelogramCircle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                10.2, -7, .5));

        assertTrue(Parallelogram2afp.intersectsParallelogramCircle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                10, -1, 5));
    }

    @Test
    public void staticIntersectsParallelogramEllipse() {
        assertFalse(Parallelogram2afp.intersectsParallelogramEllipse(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 0, 2, 1));
        assertFalse(Parallelogram2afp.intersectsParallelogramEllipse(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 1, 2, 1));
        assertTrue(Parallelogram2afp.intersectsParallelogramEllipse(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 2, 2, 1));
        assertTrue(Parallelogram2afp.intersectsParallelogramEllipse(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 3, 2, 1));
        assertTrue(Parallelogram2afp.intersectsParallelogramEllipse(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 4, 2, 1));
        assertTrue(Parallelogram2afp.intersectsParallelogramEllipse(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                1, 3, 2, 1));

        assertTrue(Parallelogram2afp.intersectsParallelogramEllipse(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                5, 5, 2, 1));

        assertFalse(Parallelogram2afp.intersectsParallelogramEllipse(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0.1, 1, 2, 1));
        assertFalse(Parallelogram2afp.intersectsParallelogramEllipse(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0.2, 1, 2, 1));
        assertTrue(Parallelogram2afp.intersectsParallelogramEllipse(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0.3, 1, 2, 1));
        assertTrue(Parallelogram2afp.intersectsParallelogramEllipse(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0.4, 1, 2, 1));

        assertFalse(Parallelogram2afp.intersectsParallelogramEllipse(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -7, 7.5, 2, 1));
    }

    @Test
    public void staticIntersectsParallelogramTriangle() {
        assertTrue(Parallelogram2afp.intersectsParallelogramTriangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -5, 15, -3, 16, -8, 19));
        assertTrue(Parallelogram2afp.intersectsParallelogramTriangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -5, 15, -8, 19, -3, 16));
        assertFalse(Parallelogram2afp.intersectsParallelogramTriangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, -5, 2, -4, -3, -1));
        assertFalse(Parallelogram2afp.intersectsParallelogramTriangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, -5, -3, -1, 2, -4));
        assertFalse(Parallelogram2afp.intersectsParallelogramTriangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                20, 0, 22, 1, 17, 4));
        assertFalse(Parallelogram2afp.intersectsParallelogramTriangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                20, 0, 17, 4, 22, 1));
        assertFalse(Parallelogram2afp.intersectsParallelogramTriangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                17.18034, 9, 19.18034, 10, 14.18034, 13));
        assertFalse(Parallelogram2afp.intersectsParallelogramTriangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                17.18034, 9, 14.18034, 13, 19.18034, 10));
        assertTrue(Parallelogram2afp.intersectsParallelogramTriangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 10, 2, 11, -3, 14));
        assertTrue(Parallelogram2afp.intersectsParallelogramTriangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 10, -3, 14, 2, 11));
        assertTrue(Parallelogram2afp.intersectsParallelogramTriangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 20, 2, 21, -3, 24));
    }

    @Test
    public void staticIntersectsParallelogramParallelogram() {
        double ux2 = -0.9284766908852592;
        double uy2 = 0.3713906763541037;
        double et1 = 5;
        double vx2 = 0.3713906763541037;
        double vy2 = 0.9284766908852592;
        double et2 = 3;
        // P + (-0.9284766908852592,0.3713906763541037) * 5 + (0.3713906763541037,0.9284766908852592) * 3
        // P - (-0.9284766908852592,0.3713906763541037) * 5 + (0.3713906763541037,0.9284766908852592) * 3
        // P - (-0.9284766908852592,0.3713906763541037) * 5 - (0.3713906763541037,0.9284766908852592) * 3
        // P + (-0.9284766908852592,0.3713906763541037) * 5 - (0.3713906763541037,0.9284766908852592) * 3
        assertFalse(Parallelogram2afp.intersectsParallelogramParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -10, 0,
                ux2, uy2, et1, vx2, vy2, et2));
        assertFalse(Parallelogram2afp.intersectsParallelogramParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -15, 25,
                ux2, uy2, et1, vx2, vy2, et2));
        assertFalse(Parallelogram2afp.intersectsParallelogramParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                2, -6,
                ux2, uy2, et1, vx2, vy2, et2));
        assertFalse(Parallelogram2afp.intersectsParallelogramParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                2, -5,
                ux2, uy2, et1, vx2, vy2, et2));
        assertTrue(Parallelogram2afp.intersectsParallelogramParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                2, -4,
                ux2, uy2, et1, vx2, vy2, et2));
        assertTrue(Parallelogram2afp.intersectsParallelogramParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                this.pEx, this.pEy,
                ux2, uy2, et1, vx2, vy2, et2));
        assertTrue(Parallelogram2afp.intersectsParallelogramParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                6, 6,
                ux2, uy2, et1, vx2, vy2, et2));
        assertTrue(Parallelogram2afp.intersectsParallelogramParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                6, 6,
                ux2, uy2, 10 * et1, vx2, vy2, 10 * et2));
    }

    @Test
    public void staticIntersectsParallelogramRectangle() {
        assertFalse(Parallelogram2afp.intersectsParallelogramRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 0, 1, 1));
        assertTrue(Parallelogram2afp.intersectsParallelogramRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 2, 1, 1));
        assertTrue(Parallelogram2afp.intersectsParallelogramRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -5.5, 8.5, 1, 1));
        assertFalse(Parallelogram2afp.intersectsParallelogramRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -6, 16, 1, 1));
        assertFalse(Parallelogram2afp.intersectsParallelogramRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                146, 16, 1, 1));
        assertTrue(Parallelogram2afp.intersectsParallelogramRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                12, 14, 1, 1));
        assertTrue(Parallelogram2afp.intersectsParallelogramRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 8, 1, 1));
        assertTrue(Parallelogram2afp.intersectsParallelogramRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                10, -1, 1, 1));
        assertTrue(Parallelogram2afp.intersectsParallelogramRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -15, -10, 35, 40));
        assertTrue(Parallelogram2afp.intersectsParallelogramRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -4.79634, 14.50886, 1, 1));
    }

    @Test
    public void staticIntersectsParallelogramRoundRectangle() {
        assertFalse(Parallelogram2afp.intersectsParallelogramRoundRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 0, 1, 1, .1, .05));
        assertTrue(Parallelogram2afp.intersectsParallelogramRoundRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 2, 1, 1, .1, .05));
        assertTrue(Parallelogram2afp.intersectsParallelogramRoundRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -5.5, 8.5, 1, 1, .1, .05));
        assertFalse(Parallelogram2afp.intersectsParallelogramRoundRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -6, 16, 1, 1, .1, .05));
        assertFalse(Parallelogram2afp.intersectsParallelogramRoundRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                146, 16, 1, 1, .1, .05));
        assertTrue(Parallelogram2afp.intersectsParallelogramRoundRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                12, 14, 1, 1, .1, .05));
        assertTrue(Parallelogram2afp.intersectsParallelogramRoundRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                0, 8, 1, 1, .1, .05));
        assertTrue(Parallelogram2afp.intersectsParallelogramRoundRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                10, -1, 1, 1, .1, .05));
        assertTrue(Parallelogram2afp.intersectsParallelogramRoundRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -15, -10, 35, 40, .1, .05));
        assertFalse(Parallelogram2afp.intersectsParallelogramRoundRectangle(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2,
                -4.79634, 14.50886, 1, 1, .1, .05));
    }

    @Override
    public void testClone() {
        T clone = this.shape.clone();
        assertNotNull(clone);
        assertNotSame(this.shape, clone);
        assertEquals(this.shape.getClass(), clone.getClass());
        assertEpsilonEquals(this.cx, clone.getCenterX());
        assertEpsilonEquals(this.cy, clone.getCenterY());
        assertEpsilonEquals(this.ux, clone.getFirstAxisX());
        assertEpsilonEquals(this.uy, clone.getFirstAxisY());
        assertEpsilonEquals(this.e1, clone.getFirstAxisExtent());
        assertEpsilonEquals(this.vx, clone.getSecondAxisX());
        assertEpsilonEquals(this.vy, clone.getSecondAxisY());
        assertEpsilonEquals(this.e2, clone.getSecondAxisExtent());
    }

    @Override
    public void equalsObject() {
        assertFalse(this.shape.equals(null));
        assertFalse(this.shape.equals(new Object()));
        assertFalse(this.shape.equals(createParallelogram(0, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2)));
        assertFalse(this.shape.equals(createParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, 20)));
        assertFalse(this.shape.equals(createSegment(5, 8, 6, 10)));
        assertTrue(this.shape.equals(this.shape));
        assertTrue(this.shape.equals(createParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2)));
    }

    @Override
    public void equalsObject_withPathIterator() {
        assertFalse(this.shape.equals((PathIterator2afp) null));
        assertFalse(this.shape.equals(createParallelogram(0, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2).getPathIterator()));
        assertFalse(this.shape.equals(createParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, 20).getPathIterator()));
        assertFalse(this.shape.equals(createSegment(5, 8, 6, 10).getPathIterator()));
        assertTrue(this.shape.equals(this.shape.getPathIterator()));
        assertTrue(this.shape.equals(createParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2).getPathIterator()));
    }

    @Override
    public void equalsToPathIterator() {
        assertFalse(this.shape.equalsToPathIterator(null));
        assertFalse(this.shape.equalsToPathIterator(createParallelogram(0, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2).getPathIterator()));
        assertFalse(this.shape.equalsToPathIterator(createParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, 20).getPathIterator()));
        assertFalse(this.shape.equalsToPathIterator(createSegment(5, 8, 6, 10).getPathIterator()));
        assertTrue(this.shape.equalsToPathIterator(this.shape.getPathIterator()));
        assertTrue(this.shape.equalsToPathIterator(createParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2).getPathIterator()));
    }

    @Override
    public void equalsToShape() {
        assertFalse(this.shape.equalsToShape(null));
        assertFalse(this.shape.equalsToShape((T) createParallelogram(0, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2)));
        assertFalse(this.shape.equalsToShape((T) createParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, 20)));
        assertTrue(this.shape.equalsToShape(this.shape));
        assertTrue(this.shape.equalsToShape((T) createParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2)));
    }

    @Override
    public void isEmpty() {
        assertFalse(this.shape.isEmpty());
        this.shape.clear();
        assertTrue(this.shape.isEmpty());
    }

    @Override
    public void clear() {
        this.shape.clear();
        assertEpsilonEquals(0, this.shape.getCenterX());
        assertEpsilonEquals(0, this.shape.getCenterY());
        assertEpsilonEquals(1, this.shape.getFirstAxisX());
        assertEpsilonEquals(0, this.shape.getFirstAxisY());
        assertEpsilonEquals(0, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(0, this.shape.getSecondAxisX());
        assertEpsilonEquals(1, this.shape.getSecondAxisY());
        assertEpsilonEquals(0, this.shape.getSecondAxisExtent());
    }

    @Override
    public void containsDoubleDouble() {
        assertFalse(this.shape.contains(0, 0));
        assertFalse(this.shape.contains(-20, 0));
        assertTrue(this.shape.contains(12, -4));
        assertTrue(this.shape.contains(14, 0));
        assertFalse(this.shape.contains(15, 0));
        assertFalse(this.shape.contains(20, 8));
        assertTrue(this.shape.contains(8, 16));
        assertFalse(this.shape.contains(-4, 20));
        assertFalse(this.shape.contains(-5, 12));
        assertTrue(this.shape.contains(0, 6));
        assertTrue(this.shape.contains(0, 7));
        assertTrue(this.shape.contains(0, 8));
        assertTrue(this.shape.contains(0, 9));
        assertTrue(this.shape.contains(0, 10));
        assertFalse(this.shape.contains(0, 27));
        assertTrue(this.shape.contains(this.cx, this.cy));
        assertTrue(this.shape.contains(16, 8));
    }

    @Override
    public void containsPoint2D() {
        assertFalse(this.shape.contains(createPoint(0, 0)));
        assertFalse(this.shape.contains(createPoint(-20, 0)));
        assertTrue(this.shape.contains(createPoint(12, -4)));
        assertTrue(this.shape.contains(createPoint(14, 0)));
        assertFalse(this.shape.contains(createPoint(15, 0)));
        assertFalse(this.shape.contains(createPoint(20, 8)));
        assertTrue(this.shape.contains(createPoint(8, 16)));
        assertFalse(this.shape.contains(createPoint(-4, 20)));
        assertFalse(this.shape.contains(createPoint(-5, 12)));
        assertTrue(this.shape.contains(createPoint(0, 6)));
        assertTrue(this.shape.contains(createPoint(0, 7)));
        assertTrue(this.shape.contains(createPoint(0, 8)));
        assertTrue(this.shape.contains(createPoint(0, 9)));
        assertTrue(this.shape.contains(createPoint(0, 10)));
        assertFalse(this.shape.contains(createPoint(0, 27)));
        assertTrue(this.shape.contains(createPoint(this.cx, this.cy)));
        assertTrue(this.shape.contains(createPoint( 16, 8)));
    }

    @Override
    public void getClosestPointTo() {
        Point2D closest;

        closest = this.shape.getClosestPointTo(createPoint(-20, 9));
        assertEpsilonEquals(this.pHx, closest.getX());
        assertEpsilonEquals(this.pHy, closest.getY());

        closest = this.shape.getClosestPointTo(createPoint(0, 0));
        assertEpsilonEquals(1.90983, closest.getX());
        assertEpsilonEquals(1.90983, closest.getY());

        closest = this.shape.getClosestPointTo(createPoint(5, -10));
        assertEpsilonEquals(9.40983, closest.getX());
        assertEpsilonEquals(-5.59017, closest.getY());

        closest = this.shape.getClosestPointTo(createPoint(14, -20));
        assertEpsilonEquals(this.pEx, closest.getX());
        assertEpsilonEquals(this.pEy, closest.getY());

        closest = this.shape.getClosestPointTo(createPoint(-6, 15));
        assertEpsilonEquals(-3.81679, closest.getX());
        assertEpsilonEquals(14.4542, closest.getY());

        closest = this.shape.getClosestPointTo(createPoint(0, 10));
        assertEpsilonEquals(0, closest.getX());
        assertEpsilonEquals(10, closest.getY());

        closest = this.shape.getClosestPointTo(createPoint(10, 0));
        assertEpsilonEquals(10, closest.getX());
        assertEpsilonEquals(0, closest.getY());

        closest = this.shape.getClosestPointTo(createPoint(15, -4));
        assertEpsilonEquals(13.99326, closest.getX());
        assertEpsilonEquals(-3.74832, closest.getY());

        closest = this.shape.getClosestPointTo(createPoint(-5, 25));
        assertEpsilonEquals(-1.40503, closest.getX());
        assertEpsilonEquals(24.10126, closest.getY());

        closest = this.shape.getClosestPointTo(createPoint(0, 20));
        assertEpsilonEquals(0, closest.getX());
        assertEpsilonEquals(20, closest.getY());

        closest = this.shape.getClosestPointTo(createPoint(10, 10));
        assertEpsilonEquals(10, closest.getX());
        assertEpsilonEquals(10, closest.getY());

        closest = this.shape.getClosestPointTo(createPoint(20, 0));
        assertEpsilonEquals(15.22856, closest.getX());
        assertEpsilonEquals(1.19286, closest.getY());

        closest = this.shape.getClosestPointTo(createPoint(-3, 35));
        assertEpsilonEquals(this.pGx, closest.getX());
        assertEpsilonEquals(this.pGy, closest.getY());

        closest = this.shape.getClosestPointTo(createPoint(5, 35));
        assertEpsilonEquals(this.pGx, closest.getX());
        assertEpsilonEquals(this.pGy, closest.getY());

        closest = this.shape.getClosestPointTo(createPoint(20, 15));
        assertEpsilonEquals(15.59017, closest.getX());
        assertEpsilonEquals(10.59017, closest.getY());

        closest = this.shape.getClosestPointTo(createPoint(35, 10));
        assertEpsilonEquals(this.pFx, closest.getX());
        assertEpsilonEquals(this.pFy, closest.getY());

        closest = this.shape.getClosestPointTo(createPoint(-8, 29));
        assertEpsilonEquals(this.pGx, closest.getX());
        assertEpsilonEquals(this.pGy, closest.getY());
    }

    @Override
    public void getFarthestPointTo() {
        Point2D farthest;

        farthest = this.shape.getFarthestPointTo(createPoint(-20, 9));
        assertEpsilonEquals(this.pEx, farthest.getX());
        assertEpsilonEquals(this.pEy, farthest.getY());

        farthest = this.shape.getFarthestPointTo(createPoint(0, 0));
        assertEpsilonEquals(this.pGx, farthest.getX());
        assertEpsilonEquals(this.pGy, farthest.getY());

        farthest = this.shape.getFarthestPointTo(createPoint(5, -10));
        assertEpsilonEquals(this.pGx, farthest.getX());
        assertEpsilonEquals(this.pGy, farthest.getY());

        farthest = this.shape.getFarthestPointTo(createPoint(14, -20));
        assertEpsilonEquals(this.pGx, farthest.getX());
        assertEpsilonEquals(this.pGy, farthest.getY());

        farthest = this.shape.getFarthestPointTo(createPoint(-6, 15));
        assertEpsilonEquals(this.pEx, farthest.getX());
        assertEpsilonEquals(this.pEy, farthest.getY());

        farthest = this.shape.getFarthestPointTo(createPoint(0, 10));
        assertEpsilonEquals(this.pEx, farthest.getX());
        assertEpsilonEquals(this.pEy, farthest.getY());

        farthest = this.shape.getFarthestPointTo(createPoint(10, 0));
        assertEpsilonEquals(this.pGx, farthest.getX());
        assertEpsilonEquals(this.pGy, farthest.getY());

        farthest = this.shape.getFarthestPointTo(createPoint(15, -4));
        assertEpsilonEquals(this.pGx, farthest.getX());
        assertEpsilonEquals(this.pGy, farthest.getY());

        farthest = this.shape.getFarthestPointTo(createPoint(-5, 25));
        assertEpsilonEquals(this.pEx, farthest.getX());
        assertEpsilonEquals(this.pEy, farthest.getY());

        farthest = this.shape.getFarthestPointTo(createPoint(0, 20));
        assertEpsilonEquals(this.pEx, farthest.getX());
        assertEpsilonEquals(this.pEy, farthest.getY());

        farthest = this.shape.getFarthestPointTo(createPoint(10, 10));
        assertEpsilonEquals(this.pGx, farthest.getX());
        assertEpsilonEquals(this.pGy, farthest.getY());

        farthest = this.shape.getFarthestPointTo(createPoint(20, 0));
        assertEpsilonEquals(this.pGx, farthest.getX());
        assertEpsilonEquals(this.pGy, farthest.getY());

        farthest = this.shape.getFarthestPointTo(createPoint(-3, 35));
        assertEpsilonEquals(this.pEx, farthest.getX());
        assertEpsilonEquals(this.pEy, farthest.getY());

        farthest = this.shape.getFarthestPointTo(createPoint(5, 35));
        assertEpsilonEquals(this.pEx, farthest.getX());
        assertEpsilonEquals(this.pEy, farthest.getY());

        farthest = this.shape.getFarthestPointTo(createPoint(20, 15));
        assertEpsilonEquals(this.pHx, farthest.getX());
        assertEpsilonEquals(this.pHy, farthest.getY());

        farthest = this.shape.getFarthestPointTo(createPoint(35, 10));
        assertEpsilonEquals(this.pHx, farthest.getX());
        assertEpsilonEquals(this.pHy, farthest.getY());

        farthest = this.shape.getFarthestPointTo(createPoint(-8, 29));
        assertEpsilonEquals(this.pEx, farthest.getX());
        assertEpsilonEquals(this.pEy, farthest.getY());
    }

    @Override
    public void translateDoubleDouble() {
        this.shape.translate(123.456, 789.123);
        assertEpsilonEquals(this.cx + 123.456, this.shape.getCenterX());
        assertEpsilonEquals(this.cy + 789.123, this.shape.getCenterY());
        assertEpsilonEquals(this.ux, this.shape.getFirstAxisX());
        assertEpsilonEquals(this.uy, this.shape.getFirstAxisY());
        assertEpsilonEquals(this.e1, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(this.vx, this.shape.getSecondAxisX());
        assertEpsilonEquals(this.vy, this.shape.getSecondAxisY());
        assertEpsilonEquals(this.e2, this.shape.getSecondAxisExtent());
    }

    @Override
    public void translateVector2D() {
        this.shape.translate(createVector(123.456, 789.123));
        assertEpsilonEquals(this.cx + 123.456, this.shape.getCenterX());
        assertEpsilonEquals(this.cy + 789.123, this.shape.getCenterY());
        assertEpsilonEquals(this.ux, this.shape.getFirstAxisX());
        assertEpsilonEquals(this.uy, this.shape.getFirstAxisY());
        assertEpsilonEquals(this.e1, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(this.vx, this.shape.getSecondAxisX());
        assertEpsilonEquals(this.vy, this.shape.getSecondAxisY());
        assertEpsilonEquals(this.e2, this.shape.getSecondAxisExtent());
    }

    @Override
    public void getDistance() {
        assertEpsilonEquals(14.81966, this.shape.getDistance(createPoint(-20, 9)));
        assertEpsilonEquals(2.7009, this.shape.getDistance(createPoint(0, 0)));
        assertEpsilonEquals(6.23644, this.shape.getDistance(createPoint(5, -10)));
        assertEpsilonEquals(11.1863, this.shape.getDistance(createPoint(14, -20)));
        assertEpsilonEquals(2.25040, this.shape.getDistance(createPoint(-6, 15)));
        assertEpsilonEquals(0, this.shape.getDistance(createPoint(0, 10)));
        assertEpsilonEquals(0, this.shape.getDistance(createPoint(10, 0)));
        assertEpsilonEquals(1.03772, this.shape.getDistance(createPoint(15, -4)));
        assertEpsilonEquals(3.70561, this.shape.getDistance(createPoint(-5, 25)));
        assertEpsilonEquals(0, this.shape.getDistance(createPoint(0, 20)));
        assertEpsilonEquals(0, this.shape.getDistance(createPoint(10, 10)));
        assertEpsilonEquals(4.91829, this.shape.getDistance(createPoint(20, 0)));
        assertEpsilonEquals(8.42901, this.shape.getDistance(createPoint(-3, 35)));
        assertEpsilonEquals(9.91864, this.shape.getDistance(createPoint(5, 35)));
        assertEpsilonEquals(6.23644, this.shape.getDistance(createPoint(20, 15)));
        assertEpsilonEquals(17.8477, this.shape.getDistance(createPoint(35, 10)));
        assertEpsilonEquals(7.59135, this.shape.getDistance(createPoint(-8, 29)));
    }

    @Override
    public void getDistanceSquared() {
        assertEpsilonEquals(219.62232, this.shape.getDistanceSquared(createPoint(-20, 9)));
        assertEpsilonEquals(7.29486, this.shape.getDistanceSquared(createPoint(0, 0)));
        assertEpsilonEquals(38.89318, this.shape.getDistanceSquared(createPoint(5, -10)));
        assertEpsilonEquals(125.13319, this.shape.getDistanceSquared(createPoint(14, -20)));
        assertEpsilonEquals(5.0643, this.shape.getDistanceSquared(createPoint(-6, 15)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createPoint(0, 10)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createPoint(10, 0)));
        assertEpsilonEquals(1.07686, this.shape.getDistanceSquared(createPoint(15, -4)));
        assertEpsilonEquals(13.73155, this.shape.getDistanceSquared(createPoint(-5, 25)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createPoint(0, 20)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createPoint(10, 10)));
        assertEpsilonEquals(24.18958, this.shape.getDistanceSquared(createPoint(20, 0)));
        assertEpsilonEquals(71.04805, this.shape.getDistanceSquared(createPoint(-3, 35)));
        assertEpsilonEquals(98.37931, this.shape.getDistanceSquared(createPoint(5, 35)));
        assertEpsilonEquals(38.89318, this.shape.getDistanceSquared(createPoint(20, 15)));
        assertEpsilonEquals(318.54029, this.shape.getDistanceSquared(createPoint(35, 10)));
        assertEpsilonEquals(57.62859, this.shape.getDistanceSquared(createPoint(-8, 29)));
    }

    @Override
    public void getDistanceL1() {
        assertEpsilonEquals(14.81966, this.shape.getDistanceL1(createPoint(-20, 9)));
        assertEpsilonEquals(3.81966, this.shape.getDistanceL1(createPoint(0, 0)));
        assertEpsilonEquals(8.81966, this.shape.getDistanceL1(createPoint(5, -10)));
        assertEpsilonEquals(12.40325, this.shape.getDistanceL1(createPoint(14, -20)));
        assertEpsilonEquals(2.72901, this.shape.getDistanceL1(createPoint(-6, 15)));
        assertEpsilonEquals(0, this.shape.getDistanceL1(createPoint(0, 10)));
        assertEpsilonEquals(0, this.shape.getDistanceL1(createPoint(10, 0)));
        assertEpsilonEquals(1.25842, this.shape.getDistanceL1(createPoint(15, -4)));
        assertEpsilonEquals(4.49371, this.shape.getDistanceL1(createPoint(-5, 25)));
        assertEpsilonEquals(0, this.shape.getDistanceL1(createPoint(0, 20)));
        assertEpsilonEquals(0, this.shape.getDistanceL1(createPoint(10, 10)));
        assertEpsilonEquals(5.9643, this.shape.getDistanceL1(createPoint(20, 0)));
        assertEpsilonEquals(10.40326, this.shape.getDistanceL1(createPoint(-3, 35)));
        assertEpsilonEquals(13.81966, this.shape.getDistanceL1(createPoint(5, 35)));
        assertEpsilonEquals(8.81966, this.shape.getDistanceL1(createPoint(20, 15)));
        assertEpsilonEquals(18.81966, this.shape.getDistanceL1(createPoint(35, 10)));
        assertEpsilonEquals(9.40326, this.shape.getDistanceL1(createPoint(-8, 29)));
    }

    @Override
    public void getDistanceLinf() {
        assertEpsilonEquals(14.81966, this.shape.getDistanceLinf(createPoint(-20, 9)));
        assertEpsilonEquals(1.90983, this.shape.getDistanceLinf(createPoint(0, 0)));
        assertEpsilonEquals(4.40983, this.shape.getDistanceLinf(createPoint(5, -10)));
        assertEpsilonEquals(11.11146, this.shape.getDistanceLinf(createPoint(14, -20)));
        assertEpsilonEquals(2.18321, this.shape.getDistanceLinf(createPoint(-6, 15)));
        assertEpsilonEquals(0, this.shape.getDistanceLinf(createPoint(0, 10)));
        assertEpsilonEquals(0, this.shape.getDistanceLinf(createPoint(10, 0)));
        assertEpsilonEquals(1.00674, this.shape.getDistanceLinf(createPoint(15, -4)));
        assertEpsilonEquals(3.59497, this.shape.getDistanceLinf(createPoint(-5, 25)));
        assertEpsilonEquals(0, this.shape.getDistanceLinf(createPoint(0, 20)));
        assertEpsilonEquals(0, this.shape.getDistanceLinf(createPoint(10, 10)));
        assertEpsilonEquals(4.77144, this.shape.getDistanceLinf(createPoint(20, 0)));
        assertEpsilonEquals(8.11146, this.shape.getDistanceLinf(createPoint(-3, 35)));
        assertEpsilonEquals(8.11146, this.shape.getDistanceLinf(createPoint(5, 35)));
        assertEpsilonEquals(4.40983, this.shape.getDistanceLinf(createPoint(20, 15)));
        assertEpsilonEquals(17.81966, this.shape.getDistanceLinf(createPoint(35, 10)));
        assertEpsilonEquals(7.2918, this.shape.getDistanceLinf(createPoint(-8, 29)));
    }

    @Override
    public void setIT() {
        this.shape.set((T) createParallelogram(17, 20, 1, 0, 15, 0, 1, 14));
        assertEpsilonEquals(17, this.shape.getCenterX());
        assertEpsilonEquals(20, this.shape.getCenterY());
        assertEpsilonEquals(1, this.shape.getFirstAxisX());
        assertEpsilonEquals(0, this.shape.getFirstAxisY());
        assertEpsilonEquals(15, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(0, this.shape.getSecondAxisX());
        assertEpsilonEquals(1, this.shape.getSecondAxisY());
        assertEpsilonEquals(14, this.shape.getSecondAxisExtent());
    }

    @Override
    public void getPathIterator() {
        PathIterator2afp pi = this.shape.getPathIterator();
        assertElement(pi, PathElementType.MOVE_TO, this.pGx, this.pGy);
        assertElement(pi, PathElementType.LINE_TO, this.pHx, this.pHy);
        assertElement(pi, PathElementType.LINE_TO, this.pEx, this.pEy);
        assertElement(pi, PathElementType.LINE_TO, this.pFx, this.pFy);
        assertElement(pi, PathElementType.CLOSE, this.pGx, this.pGy);
        assertNoElement(pi);
    }

    @Override
    public void getPathIteratorTransform2D() {
        PathIterator2afp pi = this.shape.getPathIterator(null);
        assertElement(pi, PathElementType.MOVE_TO, this.pGx, this.pGy);
        assertElement(pi, PathElementType.LINE_TO, this.pHx, this.pHy);
        assertElement(pi, PathElementType.LINE_TO, this.pEx, this.pEy);
        assertElement(pi, PathElementType.LINE_TO, this.pFx, this.pFy);
        assertElement(pi, PathElementType.CLOSE, this.pGx, this.pGy);
        assertNoElement(pi);

        Transform2D transform;

        transform = new Transform2D();
        pi = this.shape.getPathIterator(transform);
        assertElement(pi, PathElementType.MOVE_TO, this.pGx, this.pGy);
        assertElement(pi, PathElementType.LINE_TO, this.pHx, this.pHy);
        assertElement(pi, PathElementType.LINE_TO, this.pEx, this.pEy);
        assertElement(pi, PathElementType.LINE_TO, this.pFx, this.pFy);
        assertElement(pi, PathElementType.CLOSE, this.pGx, this.pGy);
        assertNoElement(pi);

        transform = new Transform2D();
        transform.setTranslation(18,  -45);
        pi = this.shape.getPathIterator(transform);
        assertElement(pi, PathElementType.MOVE_TO, this.pGx + 18, this.pGy - 45);
        assertElement(pi, PathElementType.LINE_TO, this.pHx + 18, this.pHy - 45);
        assertElement(pi, PathElementType.LINE_TO, this.pEx + 18, this.pEy - 45);
        assertElement(pi, PathElementType.LINE_TO, this.pFx + 18, this.pFy - 45);
        assertElement(pi, PathElementType.CLOSE, this.pGx + 18, this.pGy - 45);
        assertNoElement(pi);
    }

    @Override
    public void createTransformedShape() {
        PathIterator2afp pi = this.shape.createTransformedShape(null).getPathIterator();
        assertElement(pi, PathElementType.MOVE_TO, this.pGx, this.pGy);
        assertElement(pi, PathElementType.LINE_TO, this.pHx, this.pHy);
        assertElement(pi, PathElementType.LINE_TO, this.pEx, this.pEy);
        assertElement(pi, PathElementType.LINE_TO, this.pFx, this.pFy);
        assertElement(pi, PathElementType.CLOSE, this.pGx, this.pGy);
        assertNoElement(pi);

        Transform2D transform;

        transform = new Transform2D();
        pi = this.shape.createTransformedShape(transform).getPathIterator();
        assertElement(pi, PathElementType.MOVE_TO, this.pGx, this.pGy);
        assertElement(pi, PathElementType.LINE_TO, this.pHx, this.pHy);
        assertElement(pi, PathElementType.LINE_TO, this.pEx, this.pEy);
        assertElement(pi, PathElementType.LINE_TO, this.pFx, this.pFy);
        assertElement(pi, PathElementType.CLOSE, this.pGx, this.pGy);
        assertNoElement(pi);

        transform = new Transform2D();
        transform.setTranslation(18,  -45);
        pi = this.shape.createTransformedShape(transform).getPathIterator();
        assertElement(pi, PathElementType.MOVE_TO, this.pGx + 18, this.pGy - 45);
        assertElement(pi, PathElementType.LINE_TO, this.pHx + 18, this.pHy - 45);
        assertElement(pi, PathElementType.LINE_TO, this.pEx + 18, this.pEy - 45);
        assertElement(pi, PathElementType.LINE_TO, this.pFx + 18, this.pFy - 45);
        assertElement(pi, PathElementType.CLOSE, this.pGx + 18, this.pGy - 45);
        assertNoElement(pi);
    }

    @Override
    public void toBoundingBox() {
        B box = this.shape.toBoundingBox();
        assertEpsilonEquals(this.pHx, box.getMinX());
        assertEpsilonEquals(this.pEy, box.getMinY());
        assertEpsilonEquals(this.pFx, box.getMaxX());
        assertEpsilonEquals(this.pGy, box.getMaxY());
    }

    @Override
    public void toBoundingBoxB() {
        B box = createRectangle(0, 0, 0, 0);
        this.shape.toBoundingBox(box);
        assertEpsilonEquals(this.pHx, box.getMinX());
        assertEpsilonEquals(this.pEy, box.getMinY());
        assertEpsilonEquals(this.pFx, box.getMaxX());
        assertEpsilonEquals(this.pGy, box.getMaxY());
    }

    @Override
    public void containsRectangle2afp() {
        assertFalse(this.shape.contains(createRectangle(0, 0, 1, 1)));
        assertFalse(this.shape.contains(createRectangle(0, 1, 1, 1)));
        assertFalse(this.shape.contains(createRectangle(0, 2, 1, 1)));
        assertFalse(this.shape.contains(createRectangle(0, 3, 1, 1)));
        assertTrue(this.shape.contains(createRectangle(0, 4, 1, 1)));
        assertTrue(this.shape.contains(createRectangle(0, 5, 1, 1)));
        assertTrue(this.shape.contains(createRectangle(0, 6, 1, 1)));
    }

    @Override
    public void containsShape2D() {
        assertFalse(this.shape.contains(createCircle(0, 0, 1)));
        assertFalse(this.shape.contains(createCircle(0, 1, 1)));
        assertFalse(this.shape.contains(createCircle(0, 2, 1)));
        assertFalse(this.shape.contains(createCircle(0, 3, 1)));
        assertFalse(this.shape.contains(createCircle(0, 4, 1)));
        assertFalse(this.shape.contains(createCircle(0, 5, 1)));
        assertTrue(this.shape.contains(createCircle(0, 6, 1)));
    }

    @Test
    public void rotateDouble() {
        this.shape.rotate(-MathConstants.DEMI_PI);
        assertEpsilonEquals(6, this.shape.getCenterX());
        assertEpsilonEquals(9, this.shape.getCenterY());
        assertEpsilonEquals(9.701400000000000e-01, this.shape.getFirstAxisX());
        assertEpsilonEquals(-2.425400000000000e-01, this.shape.getFirstAxisY());
        assertEpsilonEquals(9.21954, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(7.071100000000000e-01, this.shape.getSecondAxisX());
        assertEpsilonEquals(7.071100000000000e-01, this.shape.getSecondAxisY());
        assertEpsilonEquals(12.64911, this.shape.getSecondAxisExtent());
    }

    @Test
    public void getCenter() {
        Point2D c = this.shape.getCenter();
        assertEpsilonEquals(6, c.getX());
        assertEpsilonEquals(9, c.getY());
    }

    @Test
    public void getCenterX() {
        assertEpsilonEquals(6, this.shape.getCenterX());
    }

    @Test
    public void getCenterY() {
        assertEpsilonEquals(9, this.shape.getCenterY());
    }

    @Test
    public void setCenterDoubleDouble() {
        this.shape.setCenter(123.456, -789.123);
        assertEpsilonEquals(123.456, this.shape.getCenterX());
        assertEpsilonEquals(-789.123, this.shape.getCenterY());
        assertEpsilonEquals(this.ux, this.shape.getFirstAxisX());
        assertEpsilonEquals(this.uy, this.shape.getFirstAxisY());
        assertEpsilonEquals(this.e1, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(this.vx, this.shape.getSecondAxisX());
        assertEpsilonEquals(this.vy, this.shape.getSecondAxisY());
        assertEpsilonEquals(this.e2, this.shape.getSecondAxisExtent());
    }

    @Test
    public void setCenterPoint2D() {
        this.shape.setCenter(createPoint(123.456, -789.123));
        assertEpsilonEquals(123.456, this.shape.getCenterX());
        assertEpsilonEquals(-789.123, this.shape.getCenterY());
        assertEpsilonEquals(this.ux, this.shape.getFirstAxisX());
        assertEpsilonEquals(this.uy, this.shape.getFirstAxisY());
        assertEpsilonEquals(this.e1, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(this.vx, this.shape.getSecondAxisX());
        assertEpsilonEquals(this.vy, this.shape.getSecondAxisY());
        assertEpsilonEquals(this.e2, this.shape.getSecondAxisExtent());
    }

    @Test
    public void setCenterX() {
        this.shape.setCenterX(123.456);
        assertEpsilonEquals(123.456, this.shape.getCenterX());
        assertEpsilonEquals(this.cy, this.shape.getCenterY());
        assertEpsilonEquals(this.ux, this.shape.getFirstAxisX());
        assertEpsilonEquals(this.uy, this.shape.getFirstAxisY());
        assertEpsilonEquals(this.e1, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(this.vx, this.shape.getSecondAxisX());
        assertEpsilonEquals(this.vy, this.shape.getSecondAxisY());
        assertEpsilonEquals(this.e2, this.shape.getSecondAxisExtent());
    }

    @Test
    public void setCenterY() {
        this.shape.setCenterY(123.456);
        assertEpsilonEquals(this.cx, this.shape.getCenterX());
        assertEpsilonEquals(123.456, this.shape.getCenterY());
        assertEpsilonEquals(this.ux, this.shape.getFirstAxisX());
        assertEpsilonEquals(this.uy, this.shape.getFirstAxisY());
        assertEpsilonEquals(this.e1, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(this.vx, this.shape.getSecondAxisX());
        assertEpsilonEquals(this.vy, this.shape.getSecondAxisY());
        assertEpsilonEquals(this.e2, this.shape.getSecondAxisExtent());
    }

    @Test
    public void getFirstAxis() {
        Vector2D v = this.shape.getFirstAxis();
        assertEpsilonEquals(this.ux, v.getX());
        assertEpsilonEquals(this.uy, v.getY());
    }

    @Test
    public void getFirstAxisX() {
        assertEpsilonEquals(this.ux, this.shape.getFirstAxisX());
    }

    @Test
    public void getFirstAxisY() {
        assertEpsilonEquals(this.uy, this.shape.getFirstAxisY());
    }

    @Test
    public void getSecondAxis() {
        Vector2D v = this.shape.getSecondAxis();
        assertEpsilonEquals(this.vx, v.getX());
        assertEpsilonEquals(this.vy, v.getY());
    }

    @Test
    public void getSecondAxisX() {
        assertEpsilonEquals(this.vx, this.shape.getSecondAxisX());
    }

    @Test
    public void getSecondAxisY() {
        assertEpsilonEquals(this.vy, this.shape.getSecondAxisY());
    }

    @Test
    public void getFirstAxisExtent() {
        assertEpsilonEquals(this.e1, this.shape.getFirstAxisExtent());
    }

    @Test
    public void setFirstAxisExtent() {
        this.shape.setFirstAxisExtent(123.456);
        assertEpsilonEquals(this.cx, this.shape.getCenterX());
        assertEpsilonEquals(this.cy, this.shape.getCenterY());
        assertEpsilonEquals(this.ux, this.shape.getFirstAxisX());
        assertEpsilonEquals(this.uy, this.shape.getFirstAxisY());
        assertEpsilonEquals(123.456, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(this.vx, this.shape.getSecondAxisX());
        assertEpsilonEquals(this.vy, this.shape.getSecondAxisY());
        assertEpsilonEquals(this.e2, this.shape.getSecondAxisExtent());
    }

    @Test
    public void getSecondAxisExtent() {
        assertEpsilonEquals(this.e2, this.shape.getSecondAxisExtent());
    }

    @Test
    public void setSecondAxisExtent() {
        this.shape.setSecondAxisExtent(123.456);
        assertEpsilonEquals(this.cx, this.shape.getCenterX());
        assertEpsilonEquals(this.cy, this.shape.getCenterY());
        assertEpsilonEquals(this.ux, this.shape.getFirstAxisX());
        assertEpsilonEquals(this.uy, this.shape.getFirstAxisY());
        assertEpsilonEquals(this.e1, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(this.vx, this.shape.getSecondAxisX());
        assertEpsilonEquals(this.vy, this.shape.getSecondAxisY());
        assertEpsilonEquals(123.456, this.shape.getSecondAxisExtent());
    }

    @Test
    public void setFirstAxisDoubleDouble_unitVector() {
        Vector2D newU = createVector(123.456, 456.789).toUnitVector();
        this.shape.setFirstAxis(newU.getX(), newU.getY());
        assertEpsilonEquals(this.cx, this.shape.getCenterX());
        assertEpsilonEquals(this.cy, this.shape.getCenterY());
        assertEpsilonEquals(newU.getX(), this.shape.getFirstAxisX());
        assertEpsilonEquals(newU.getY(), this.shape.getFirstAxisY());
        assertEpsilonEquals(this.e1, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(this.vx, this.shape.getSecondAxisX());
        assertEpsilonEquals(this.vy, this.shape.getSecondAxisY());
        assertEpsilonEquals(this.e2, this.shape.getSecondAxisExtent());
    }

    @Test(expected = AssertionError.class)
    public void setFirstAxisDoubleDouble_notUnitVector() {
        this.shape.setFirstAxis(123.456, 456.789);
    }

    @Test
    public void setFirstAxisVector2D_unitVector() {
        Vector2D newU = createVector(123.456, 456.789).toUnitVector();
        this.shape.setFirstAxis(newU);
        assertEpsilonEquals(this.cx, this.shape.getCenterX());
        assertEpsilonEquals(this.cy, this.shape.getCenterY());
        assertEpsilonEquals(newU.getX(), this.shape.getFirstAxisX());
        assertEpsilonEquals(newU.getY(), this.shape.getFirstAxisY());
        assertEpsilonEquals(this.e1, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(this.vx, this.shape.getSecondAxisX());
        assertEpsilonEquals(this.vy, this.shape.getSecondAxisY());
        assertEpsilonEquals(this.e2, this.shape.getSecondAxisExtent());
    }

    @Test(expected = AssertionError.class)
    public void setFirstAxisVector2D_notUnitVector() {
        this.shape.setFirstAxis(createVector(123.456, 456.789));
    }

    @Test
    public void setFirstAxisVector2DDouble_unitVector() {
        Vector2D newU = createVector(123.456, 456.789).toUnitVector();
        this.shape.setFirstAxis(newU, 159.753);
        assertEpsilonEquals(this.cx, this.shape.getCenterX());
        assertEpsilonEquals(this.cy, this.shape.getCenterY());
        assertEpsilonEquals(newU.getX(), this.shape.getFirstAxisX());
        assertEpsilonEquals(newU.getY(), this.shape.getFirstAxisY());
        assertEpsilonEquals(159.753, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(this.vx, this.shape.getSecondAxisX());
        assertEpsilonEquals(this.vy, this.shape.getSecondAxisY());
        assertEpsilonEquals(this.e2, this.shape.getSecondAxisExtent());
    }

    @Test
    public void setFirstAxisDoubleDoubleDouble() {
        Vector2D newU = createVector(123.456, 456.789).toUnitVector();
        this.shape.setFirstAxis(newU.getX(), newU.getY(), 159.753);
        assertEpsilonEquals(this.cx, this.shape.getCenterX());
        assertEpsilonEquals(this.cy, this.shape.getCenterY());
        assertEpsilonEquals(newU.getX(), this.shape.getFirstAxisX());
        assertEpsilonEquals(newU.getY(), this.shape.getFirstAxisY());
        assertEpsilonEquals(159.753, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(this.vx, this.shape.getSecondAxisX());
        assertEpsilonEquals(this.vy, this.shape.getSecondAxisY());
        assertEpsilonEquals(this.e2, this.shape.getSecondAxisExtent());
    }

    @Test
    public void setSecondAxisDoubleDouble_unitVector() {
        Vector2D newV = createVector(123.456, 456.789).toUnitVector();
        this.shape.setSecondAxis(newV.getX(), newV.getY());
        assertEpsilonEquals(this.cx, this.shape.getCenterX());
        assertEpsilonEquals(this.cy, this.shape.getCenterY());
        assertEpsilonEquals(this.ux, this.shape.getFirstAxisX());
        assertEpsilonEquals(this.uy, this.shape.getFirstAxisY());
        assertEpsilonEquals(this.e1, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(newV.getX(), this.shape.getSecondAxisX());
        assertEpsilonEquals(newV.getY(), this.shape.getSecondAxisY());
        assertEpsilonEquals(this.e2, this.shape.getSecondAxisExtent());
    }

    @Test(expected = AssertionError.class)
    public void setSecondAxisDoubleDouble_notUnitVector() {
        this.shape.setSecondAxis(123.456, 456.789);
    }

    @Test
    public void setSecondAxisVector2D_unitVector() {
        Vector2D newV = createVector(123.456, 456.789).toUnitVector();
        this.shape.setSecondAxis(newV);
        assertEpsilonEquals(this.cx, this.shape.getCenterX());
        assertEpsilonEquals(this.cy, this.shape.getCenterY());
        assertEpsilonEquals(this.ux, this.shape.getFirstAxisX());
        assertEpsilonEquals(this.uy, this.shape.getFirstAxisY());
        assertEpsilonEquals(this.e1, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(newV.getX(), this.shape.getSecondAxisX());
        assertEpsilonEquals(newV.getY(), this.shape.getSecondAxisY());
        assertEpsilonEquals(this.e2, this.shape.getSecondAxisExtent());
    }

    @Test(expected = AssertionError.class)
    public void setSecondAxisVector2D_notUnitVector() {
        this.shape.setSecondAxis(createVector(123.456, 456.789));
    }

    @Test
    public void setSecondAxisVector2DDouble() {
        Vector2D newV = createVector(123.456, 456.789).toUnitVector();
        this.shape.setSecondAxis(newV, 159.753);
        assertEpsilonEquals(this.cx, this.shape.getCenterX());
        assertEpsilonEquals(this.cy, this.shape.getCenterY());
        assertEpsilonEquals(this.ux, this.shape.getFirstAxisX());
        assertEpsilonEquals(this.uy, this.shape.getFirstAxisY());
        assertEpsilonEquals(this.e1, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(newV.getX(), this.shape.getSecondAxisX());
        assertEpsilonEquals(newV.getY(), this.shape.getSecondAxisY());
        assertEpsilonEquals(159.753, this.shape.getSecondAxisExtent());
    }

    @Test
    public void setSecondAxisDoubleDoubleDouble() {
        Vector2D newV = createVector(123.456, 456.789).toUnitVector();
        this.shape.setSecondAxis(newV.getX(), newV.getY(), 159.753);
        assertEpsilonEquals(this.cx, this.shape.getCenterX());
        assertEpsilonEquals(this.cy, this.shape.getCenterY());
        assertEpsilonEquals(this.ux, this.shape.getFirstAxisX());
        assertEpsilonEquals(this.uy, this.shape.getFirstAxisY());
        assertEpsilonEquals(this.e1, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(newV.getX(), this.shape.getSecondAxisX());
        assertEpsilonEquals(newV.getY(), this.shape.getSecondAxisY());
        assertEpsilonEquals(159.753, this.shape.getSecondAxisExtent());
    }

    @Test
    public void setDoubleDoubleDoubleDoubleDoubleDoubleDoubleDouble() {
        Vector2D newU = createVector(-456.789, 159.753).toUnitVector();
        Vector2D newV = createVector(123.456, 456.789).toUnitVector();
        this.shape.set(-6, -4, newU.getX(), newU.getY(), 147.369, newV.getX(), newV.getY(), 159.753);
        assertEpsilonEquals(-6, this.shape.getCenterX());
        assertEpsilonEquals(-4, this.shape.getCenterY());
        assertEpsilonEquals(newU.getX(), this.shape.getFirstAxisX());
        assertEpsilonEquals(newU.getY(), this.shape.getFirstAxisY());
        assertEpsilonEquals(147.369, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(newV.getX(), this.shape.getSecondAxisX());
        assertEpsilonEquals(newV.getY(), this.shape.getSecondAxisY());
        assertEpsilonEquals(159.753, this.shape.getSecondAxisExtent());
    }

    @Test
    public void setPoint2DVector2DDoubleVector2DDouble() {
        Vector2D newU = createVector(-456.789, 159.753).toUnitVector();
        Vector2D newV = createVector(123.456, 456.789).toUnitVector();
        this.shape.set(createPoint(-6, -4), newU, 147.369, newV, 159.753);
        assertEpsilonEquals(-6, this.shape.getCenterX());
        assertEpsilonEquals(-4, this.shape.getCenterY());
        assertEpsilonEquals(newU.getX(), this.shape.getFirstAxisX());
        assertEpsilonEquals(newU.getY(), this.shape.getFirstAxisY());
        assertEpsilonEquals(147.369, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(newV.getX(), this.shape.getSecondAxisX());
        assertEpsilonEquals(newV.getY(), this.shape.getSecondAxisY());
        assertEpsilonEquals(159.753, this.shape.getSecondAxisExtent());
    }

    @Test
    public void setFromPointCloudIterable() {
        double obrux = 0.8944271909999159;
        double obruy = -0.4472135954999579;
        double obrvx = 0.4472135954999579;
        double obrvy = 0.8944271909999159;

        this.shape.setFromPointCloud((List) Arrays.asList(
                createPoint(11.7082, -0.94427), createPoint(16.18034, 8),
                createPoint(-1.7082, 16.94427), createPoint(-6.18034, 8)));

        assertEpsilonEquals(5, this.shape.getCenterX());
        assertEpsilonEquals(8, this.shape.getCenterY());
        assertEpsilonEquals(obrux, this.shape.getFirstAxisX());
        assertEpsilonEquals(obruy, this.shape.getFirstAxisY());
        assertEpsilonEquals(10, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(obrvx, this.shape.getSecondAxisX());
        assertEpsilonEquals(obrvy, this.shape.getSecondAxisY());
        assertEpsilonEquals(5, this.shape.getSecondAxisExtent());
    }

    @Test
    public void setFromPointCloudPoint2DArray() {
        double obrux = 0.8944271909999159;
        double obruy = -0.4472135954999579;
        double obrvx = 0.4472135954999579;
        double obrvy = 0.8944271909999159;

        this.shape.setFromPointCloud(
                createPoint(11.7082, -0.94427), createPoint(16.18034, 8),
                createPoint(-1.7082, 16.94427), createPoint(-6.18034, 8));

        assertEpsilonEquals(5, this.shape.getCenterX());
        assertEpsilonEquals(8, this.shape.getCenterY());
        assertEpsilonEquals(obrux, this.shape.getFirstAxisX());
        assertEpsilonEquals(obruy, this.shape.getFirstAxisY());
        assertEpsilonEquals(10, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(obrvx, this.shape.getSecondAxisX());
        assertEpsilonEquals(obrvy, this.shape.getSecondAxisY());
        assertEpsilonEquals(5, this.shape.getSecondAxisExtent());
    }

    @Override
    public void intersectsCircle2afp() {
        assertFalse(this.shape.intersects(createCircle(.5, .5, .5)));
        assertFalse(this.shape.intersects(createCircle(.5, 1.5, .5)));
        assertFalse(this.shape.intersects(createCircle(.5, 2.5, .5)));
        assertTrue(this.shape.intersects(createCircle(.5, 3.5, .5)));
        assertTrue(this.shape.intersects(createCircle(4.5, 3.5, .5)));
        assertFalse(this.shape.intersects(createCircle(10, -7, .5)));
        assertFalse(this.shape.intersects(createCircle(10.1, -7, .5)));
        assertTrue(this.shape.intersects(createCircle(10.2, -7, .5)));
        assertTrue(this.shape.intersects(createCircle(10, -1, 5)));
    }

    @Override
    public void intersectsEllipse2afp() {
        assertFalse(this.shape.intersects(createEllipse(0, 0, 2, 1)));
        assertFalse(this.shape.intersects(createEllipse(0, 1, 2, 1)));
        assertTrue(this.shape.intersects(createEllipse(0, 2, 2, 1)));
        assertTrue(this.shape.intersects(createEllipse(0, 3, 2, 1)));
        assertTrue(this.shape.intersects(createEllipse(0, 4, 2, 1)));
        assertTrue(this.shape.intersects(createEllipse(1, 3, 2, 1)));
        assertTrue(this.shape.intersects(createEllipse(5, 5, 2, 1)));
        assertFalse(this.shape.intersects(createEllipse(0.1, 1, 2, 1)));
        assertFalse(this.shape.intersects(createEllipse(0.2, 1, 2, 1)));
        assertTrue(this.shape.intersects(createEllipse(0.3, 1, 2, 1)));
        assertTrue(this.shape.intersects(createEllipse(0.4, 1, 2, 1)));
        assertFalse(this.shape.intersects(createEllipse(-7, 7.5, 2, 1)));
    }

    @Override
    public void intersectsSegment2afp() {
        assertFalse(this.shape.intersects(createSegment(0, 0, 1, 1)));
        assertTrue(this.shape.intersects(createSegment(5, 5, 4, 6)));
        assertTrue(this.shape.intersects(createSegment(2, -2, 5, 0)));
        assertFalse(this.shape.intersects(createSegment(-20, -5, -10, 6)));
        assertFalse(this.shape.intersects(createSegment(-5, 0, -10, 16)));
        assertTrue(this.shape.intersects(createSegment(-10, 1, 10, 20)));
    }

    @Override
    public void intersectsPath2afp() {
        Path2afp<?, ?, ?, ?, ?, B> path = createPath();
        path.moveTo(-15,  2);
        path.lineTo(6, -9);
        path.lineTo(19, -9);
        path.lineTo(20, 26);
        path.lineTo(-6, 30);
        assertFalse(this.shape.intersects(path));
        path.closePath();
        assertTrue(this.shape.intersects(path));
    }

    @Override
    public void intersectsPathIterator2afp() {
        Path2afp<?, ?, ?, ?, ?, B> path = createPath();
        path.moveTo(-15,  2);
        path.lineTo(6, -9);
        path.lineTo(19, -9);
        path.lineTo(20, 26);
        path.lineTo(-6, 30);
        assertFalse(this.shape.intersects(path.getPathIterator()));
        path.closePath();
        assertTrue(this.shape.intersects(path.getPathIterator()));
    }

    @Override
    public void intersectsTriangle2afp() {
        assertTrue(this.shape.intersects(createTriangle(-5, 15, -3, 16, -8, 19)));
        assertTrue(this.shape.intersects(createTriangle(-5, 15, -8, 19, -3, 16)));
        assertFalse(this.shape.intersects(createTriangle(0, -5, 2, -4, -3, -1)));
        assertFalse(this.shape.intersects(createTriangle(0, -5, -3, -1, 2, -4)));
        assertFalse(this.shape.intersects(createTriangle(20, 0, 22, 1, 17, 4)));
        assertFalse(this.shape.intersects(createTriangle(20, 0, 17, 4, 22, 1)));
        assertFalse(this.shape.intersects(createTriangle(17.18034, 9, 19.18034, 10, 14.18034, 13)));
        assertFalse(this.shape.intersects(createTriangle(17.18034, 9, 14.18034, 13, 19.18034, 10)));
        assertTrue(this.shape.intersects(createTriangle(0, 10, 2, 11, -3, 14)));
        assertTrue(this.shape.intersects(createTriangle(0, 10, -3, 14, 2, 11)));
        assertTrue(this.shape.intersects(createTriangle(0, 20, 2, 21, -3, 24)));
    }

    @Override
    public void intersectsRectangle2afp() {
        assertFalse(this.shape.intersects(createRectangle(0, 0, 1, 1)));
        assertTrue(this.shape.intersects(createRectangle(0, 2, 1, 1)));
        assertTrue(this.shape.intersects(createRectangle(-5.5, 8.5, 1, 1)));
        assertFalse(this.shape.intersects(createRectangle(-6, 16, 1, 1)));
        assertFalse(this.shape.intersects(createRectangle(146, 16, 1, 1)));
        assertTrue(this.shape.intersects(createRectangle(12, 14, 1, 1)));
        assertTrue(this.shape.intersects(createRectangle(0, 8, 1, 1)));
        assertTrue(this.shape.intersects(createRectangle(10, -1, 1, 1)));
        assertTrue(this.shape.intersects(createRectangle(-15, -10, 35, 40)));
    }

    @Override
    public void intersectsParallelogram2afp() {
        double ux2 = -0.9284766908852592;
        double uy2 = 0.3713906763541037;
        double et1 = 5;
        double vx2 = 0.3713906763541037;
        double vy2 = 0.9284766908852592;
        double et2 = 3;
        assertFalse(this.shape.intersects(createParallelogram(-10, 0,
                ux2, uy2, et1, vx2, vy2, et2)));
        assertFalse(this.shape.intersects(createParallelogram(-15, 25,
                ux2, uy2, et1, vx2, vy2, et2)));
        assertFalse(this.shape.intersects(createParallelogram(2, -6,
                ux2, uy2, et1, vx2, vy2, et2)));
        assertFalse(this.shape.intersects(createParallelogram(2, -5,
                ux2, uy2, et1, vx2, vy2, et2)));
        assertTrue(this.shape.intersects(createParallelogram(2, -4,
                ux2, uy2, et1, vx2, vy2, et2)));
        assertTrue(this.shape.intersects(createParallelogram(this.pEx, this.pEy,
                ux2, uy2, et1, vx2, vy2, et2)));
        assertTrue(this.shape.intersects(createParallelogram(6, 6,
                ux2, uy2, et1, vx2, vy2, et2)));
        assertTrue(this.shape.intersects(createParallelogram(6, 6,
                ux2, uy2, 10 * et1, vx2, vy2, 10 * et2)));
    }

    @Override
    public void intersectsRoundRectangle2afp() {
        assertFalse(this.shape.intersects(createRoundRectangle(0, 0, 1, 1, .1, .05)));
        assertTrue(this.shape.intersects(createRoundRectangle(0, 2, 1, 1, .1, .05)));
        assertTrue(this.shape.intersects(createRoundRectangle(-5.5, 8.5, 1, 1, .1, .05)));
        assertFalse(this.shape.intersects(createRoundRectangle(-6, 16, 1, 1, .1, .05)));
        assertFalse(this.shape.intersects(createRoundRectangle(146, 16, 1, 1, .1, .05)));
        assertTrue(this.shape.intersects(createRoundRectangle(12, 14, 1, 1, .1, .05)));
        assertTrue(this.shape.intersects(createRoundRectangle(0, 8, 1, 1, .1, .05)));
        assertTrue(this.shape.intersects(createRoundRectangle(10, -1, 1, 1, .1, .05)));
        assertTrue(this.shape.intersects(createRoundRectangle(-15, -10, 35, 40, .1, .05)));
        assertFalse(this.shape.intersects(createRoundRectangle(-4.79634, 14.50886, 1, 1, .1, .05)));
    }

    @Override
    public void intersectsOrientedRectangle2afp() {
        OrientedRectangle2afp rectangle = createOrientedRectangle(
                6, 9,
                0.894427190999916, -0.447213595499958, 13.999990000000002,
                12.999989999999997);
        double ux2 = 0.55914166827779;
        double uy2 = 0.829072128825671;
        double et1 = 10;
        double vx2 = -0.989660599000356;
        double vy2 = -0.143429072318889;
        double et2 = 15;
        assertFalse(createParallelogram(
                -20, -20, ux2, uy2, et1, vx2, vy2, et2).intersects(rectangle));
        assertFalse(createParallelogram(
                -40, 20, ux2, uy2, et1, vx2, vy2, et2).intersects(rectangle));
        assertTrue(createParallelogram(
                -20, -10, ux2, uy2, et1, vx2, vy2, et2).intersects(rectangle));
        assertTrue(createParallelogram(
                10, -10, ux2, uy2, et1, vx2, vy2, et2).intersects(rectangle));
        assertTrue(createParallelogram(
                5, 5, ux2, uy2, et1, vx2, vy2, et2).intersects(rectangle));
    }

    @Override
    public void intersectsShape2D() {
        assertTrue(this.shape.intersects((Shape2D) createCircle(.5, 3.5, .5)));
        assertTrue(this.shape.intersects((Shape2D) createRectangle(12, 14, 1, 1)));
    }

    @Override
    public void operator_addVector2D() {
        this.shape.operator_add(createVector(123.456, 789.123));
        assertEpsilonEquals(this.cx + 123.456, this.shape.getCenterX());
        assertEpsilonEquals(this.cy + 789.123, this.shape.getCenterY());
        assertEpsilonEquals(this.ux, this.shape.getFirstAxisX());
        assertEpsilonEquals(this.uy, this.shape.getFirstAxisY());
        assertEpsilonEquals(this.e1, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(this.vx, this.shape.getSecondAxisX());
        assertEpsilonEquals(this.vy, this.shape.getSecondAxisY());
        assertEpsilonEquals(this.e2, this.shape.getSecondAxisExtent());
    }

    @Override
    public void operator_plusVector2D() {
        T shape = this.shape.operator_plus(createVector(123.456, 789.123));
        assertEpsilonEquals(this.cx + 123.456, shape.getCenterX());
        assertEpsilonEquals(this.cy + 789.123, shape.getCenterY());
        assertEpsilonEquals(this.ux, shape.getFirstAxisX());
        assertEpsilonEquals(this.uy, shape.getFirstAxisY());
        assertEpsilonEquals(this.e1, shape.getFirstAxisExtent());
        assertEpsilonEquals(this.vx, shape.getSecondAxisX());
        assertEpsilonEquals(this.vy, shape.getSecondAxisY());
        assertEpsilonEquals(this.e2, shape.getSecondAxisExtent());
    }

    @Override
    public void operator_removeVector2D() {
        this.shape.operator_remove(createVector(123.456, 789.123));
        assertEpsilonEquals(this.cx - 123.456, this.shape.getCenterX());
        assertEpsilonEquals(this.cy - 789.123, this.shape.getCenterY());
        assertEpsilonEquals(this.ux, this.shape.getFirstAxisX());
        assertEpsilonEquals(this.uy, this.shape.getFirstAxisY());
        assertEpsilonEquals(this.e1, this.shape.getFirstAxisExtent());
        assertEpsilonEquals(this.vx, this.shape.getSecondAxisX());
        assertEpsilonEquals(this.vy, this.shape.getSecondAxisY());
        assertEpsilonEquals(this.e2, this.shape.getSecondAxisExtent());
    }

    @Override
    public void operator_minusVector2D() {
        T shape = this.shape.operator_minus(createVector(123.456, 789.123));
        assertEpsilonEquals(this.cx - 123.456, shape.getCenterX());
        assertEpsilonEquals(this.cy - 789.123, shape.getCenterY());
        assertEpsilonEquals(this.ux, shape.getFirstAxisX());
        assertEpsilonEquals(this.uy, shape.getFirstAxisY());
        assertEpsilonEquals(this.e1, shape.getFirstAxisExtent());
        assertEpsilonEquals(this.vx, shape.getSecondAxisX());
        assertEpsilonEquals(this.vy, shape.getSecondAxisY());
        assertEpsilonEquals(this.e2, shape.getSecondAxisExtent());
    }

    @Override
    public void operator_multiplyTransform2D() {
        PathIterator2afp pi = this.shape.operator_multiply(null).getPathIterator();
        assertElement(pi, PathElementType.MOVE_TO, this.pGx, this.pGy);
        assertElement(pi, PathElementType.LINE_TO, this.pHx, this.pHy);
        assertElement(pi, PathElementType.LINE_TO, this.pEx, this.pEy);
        assertElement(pi, PathElementType.LINE_TO, this.pFx, this.pFy);
        assertElement(pi, PathElementType.CLOSE, this.pGx, this.pGy);
        assertNoElement(pi);

        Transform2D transform;

        transform = new Transform2D();
        pi = this.shape.operator_multiply(transform).getPathIterator();
        assertElement(pi, PathElementType.MOVE_TO, this.pGx, this.pGy);
        assertElement(pi, PathElementType.LINE_TO, this.pHx, this.pHy);
        assertElement(pi, PathElementType.LINE_TO, this.pEx, this.pEy);
        assertElement(pi, PathElementType.LINE_TO, this.pFx, this.pFy);
        assertElement(pi, PathElementType.CLOSE, this.pGx, this.pGy);
        assertNoElement(pi);

        transform = new Transform2D();
        transform.setTranslation(18,  -45);
        pi = this.shape.operator_multiply(transform).getPathIterator();
        assertElement(pi, PathElementType.MOVE_TO, this.pGx + 18, this.pGy - 45);
        assertElement(pi, PathElementType.LINE_TO, this.pHx + 18, this.pHy - 45);
        assertElement(pi, PathElementType.LINE_TO, this.pEx + 18, this.pEy - 45);
        assertElement(pi, PathElementType.LINE_TO, this.pFx + 18, this.pFy - 45);
        assertElement(pi, PathElementType.CLOSE, this.pGx + 18, this.pGy - 45);
        assertNoElement(pi);
    }

    @Override
    public void operator_andPoint2D() {
        assertFalse(this.shape.operator_and(createPoint(0, 0)));
        assertFalse(this.shape.operator_and(createPoint(-20, 0)));
        assertTrue(this.shape.operator_and(createPoint(12, -4)));
        assertTrue(this.shape.operator_and(createPoint(14, 0)));
        assertFalse(this.shape.operator_and(createPoint(15, 0)));
        assertFalse(this.shape.operator_and(createPoint(20, 8)));
        assertTrue(this.shape.operator_and(createPoint(8, 16)));
        assertFalse(this.shape.operator_and(createPoint(-4, 20)));
        assertFalse(this.shape.operator_and(createPoint(-5, 12)));
        assertTrue(this.shape.operator_and(createPoint(0, 6)));
        assertTrue(this.shape.operator_and(createPoint(0, 7)));
        assertTrue(this.shape.operator_and(createPoint(0, 8)));
        assertTrue(this.shape.operator_and(createPoint(0, 9)));
        assertTrue(this.shape.operator_and(createPoint(0, 10)));
        assertFalse(this.shape.operator_and(createPoint(0, 27)));
        assertTrue(this.shape.operator_and(createPoint(this.cx, this.cy)));
        assertTrue(this.shape.operator_and(createPoint( 16, 8)));
    }

    @Override
    public void operator_andShape2D() {
        assertTrue(this.shape.operator_and(createCircle(.5, 3.5, .5)));
        assertTrue(this.shape.operator_and(createRectangle(12, 14, 1, 1)));
    }

    @Override
    public void operator_upToPoint2D() {
        assertEpsilonEquals(14.81966, this.shape.operator_upTo(createPoint(-20, 9)));
        assertEpsilonEquals(2.7009, this.shape.operator_upTo(createPoint(0, 0)));
        assertEpsilonEquals(6.23644, this.shape.operator_upTo(createPoint(5, -10)));
        assertEpsilonEquals(11.1863, this.shape.operator_upTo(createPoint(14, -20)));
        assertEpsilonEquals(2.25040, this.shape.operator_upTo(createPoint(-6, 15)));
        assertEpsilonEquals(0, this.shape.operator_upTo(createPoint(0, 10)));
        assertEpsilonEquals(0, this.shape.operator_upTo(createPoint(10, 0)));
        assertEpsilonEquals(1.03772, this.shape.operator_upTo(createPoint(15, -4)));
        assertEpsilonEquals(3.70561, this.shape.operator_upTo(createPoint(-5, 25)));
        assertEpsilonEquals(0, this.shape.operator_upTo(createPoint(0, 20)));
        assertEpsilonEquals(0, this.shape.operator_upTo(createPoint(10, 10)));
        assertEpsilonEquals(4.91829, this.shape.operator_upTo(createPoint(20, 0)));
        assertEpsilonEquals(8.42901, this.shape.operator_upTo(createPoint(-3, 35)));
        assertEpsilonEquals(9.91864, this.shape.operator_upTo(createPoint(5, 35)));
        assertEpsilonEquals(6.23644, this.shape.operator_upTo(createPoint(20, 15)));
        assertEpsilonEquals(17.8477, this.shape.operator_upTo(createPoint(35, 10)));
        assertEpsilonEquals(7.59135, this.shape.operator_upTo(createPoint(-8, 29)));
    }

    @Test
    public void isCCW() {
        assertTrue(this.shape.isCCW());
        assertTrue(createParallelogram(this.cx, this.cy, this.ux, this.uy, this.e1, this.vx, this.vy, this.e2).isCCW());
        assertTrue(createParallelogram(
                4.7, 15,
                0.12403, 0.99228, 18.02776,
                -0.44721, 0.89443, 20).isCCW());
        assertTrue(createParallelogram(
                -10, -3,
                -.8944271909999159, .4472135954999579, 2,
                .5547001962252290, -.8320502943378436, 1).isCCW());
        assertFalse(createParallelogram(
                -10, 7,
                -0.9863939238321437, 0.1643989873053573, 1,
                0.9998000599800071, 0.01999600119960014, 2).isCCW());
        assertFalse(createParallelogram(
                0, -6,
                -0.9863939238321437, 0.1643989873053573, 1,
                0.9998000599800071, 0.01999600119960014, 2).isCCW());
    }

    @Override
	@Test
    public void getClosestPointToCircle2afp() {
        assertFpPointEquals(0.90983, 2.90983, this.shape.getClosestPointTo(createCircle(0, 2, 1)));
        assertFpPointEquals(-5.18034, 9, this.shape.getClosestPointTo(createCircle(-12, 8, 1)));
        assertClosestPointInBothShapes(this.shape, createCircle(16, 2, 1));
        assertClosestPointInBothShapes(this.shape, createCircle(12, 10, 1));
    }

    @Override
	@Test
    public void getDistanceSquaredCircle2afp() {
        assertEpsilonEquals(0.08219, this.shape.getDistanceSquared(createCircle(0, 2, 1)));
        assertEpsilonEquals(34.72259, this.shape.getDistanceSquared(createCircle(-12, 8, 1)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createCircle(16, 2, 1)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createCircle(12, 10, 1)));
    }

    @Override
	@Test
    public void getClosestPointToSegment2afp() {
        assertFpPointEquals(0.40983, 3.40983, this.shape.getClosestPointTo(createSegment(-2, 2, 0, 3)));
        assertFpPointEquals(-5.18034, 9, this.shape.getClosestPointTo(createSegment(-12, 8, -10, 9)));
        assertClosestPointInBothShapes(this.shape, createSegment(15, 2, 17, 3));
        assertClosestPointInBothShapes(this.shape, createSegment(12, 10, 14, 11));
    }

    @Override
	@Test
    public void getDistanceSquaredSegment2afp() {
        assertEpsilonEquals(0.33592, this.shape.getDistanceSquared(createSegment(-2, 2, 0, 3)));
        assertEpsilonEquals(23.22912, this.shape.getDistanceSquared(createSegment(-12, 8, -10, 9)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createSegment(15, 2, 17, 3)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createSegment(12, 10, 14, 11)));
    }

    protected Triangle2afp createTestTriangle(double dx, double dy) {
        return createTriangle(dx, dy, dx + 6, dy + 3, dx - 1, dy + 2.5);
    }

    @Override
	@Test
    public void getClosestPointToTriangle2afp() {
        assertFpPointEquals(3.40983, 0.40983, this.shape.getClosestPointTo(createTestTriangle(-5, -5)));
        assertFpPointEquals(-5.18034, 9, this.shape.getClosestPointTo(createTestTriangle(-14, 5)));
        assertClosestPointInBothShapes(this.shape, createTestTriangle(15, 2));
        assertClosestPointInBothShapes(this.shape, createTestTriangle(5, 5));
    }

    @Override
	@Test
    public void getDistanceSquaredTriangle2afp() {
        assertEpsilonEquals(11.61456, this.shape.getDistanceSquared(createTestTriangle(-5, -5)));
        assertEpsilonEquals(8.95048, this.shape.getDistanceSquared(createTestTriangle(-14, 5)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createTestTriangle(15, 2)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createTestTriangle(5, 5)));
    }

    @Override
	@Test
    public void getClosestPointToRectangle2afp() {
        assertFpPointEquals(2.40983, 1.40983, this.shape.getClosestPointTo(createRectangle(-5, -5, 2, 1)));
        assertFpPointEquals(-5.18034, 9, this.shape.getClosestPointTo(createRectangle(-14, 5, 2, 1)));
        assertClosestPointInBothShapes(this.shape, createRectangle(15, 2, 2, 1));
        assertClosestPointInBothShapes(this.shape, createRectangle(5, 5, 2, 1));
    }

    @Override
	@Test
    public void getDistanceSquaredRectangle2afp() {
        assertEpsilonEquals(58.53252, this.shape.getDistanceSquared(createRectangle(-5, -5, 2, 1)));
        assertEpsilonEquals(55.50776, this.shape.getDistanceSquared(createRectangle(-14, 5, 2, 1)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createRectangle(15, 2, 2, 1)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createRectangle(5, 5, 2, 1)));
    }

    @Override
	@Test
    public void getClosestPointToEllipse2afp() {
        assertFpPointEquals(2.52323, 1.29643, this.shape.getClosestPointTo(createEllipse(-5, -5, 2, 1)));
        assertFpPointEquals(-5.18034, 9, this.shape.getClosestPointTo(createEllipse(-14, 5, 2, 1)));
        assertClosestPointInBothShapes(this.shape, createRectangle(15, 2, 2, 1));
        assertClosestPointInBothShapes(this.shape, createRectangle(5, 5, 2, 1));
    }

    @Override
	@Test
    public void getDistanceSquaredEllipse2afp() {
        assertEpsilonEquals(62.73969, this.shape.getDistanceSquared(createEllipse(-5, -5, 2, 1)));
        assertEpsilonEquals(58.33165, this.shape.getDistanceSquared(createEllipse(-14, 5, 2, 1)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createEllipse(15, 2, 2, 1)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createEllipse(5, 5, 2, 1)));
    }

    @Override
	@Test
    public void getClosestPointToRoundRectangle2afp() {
        assertFpPointEquals(2.39519, 1.42447, this.shape.getClosestPointTo(createRoundRectangle(-5, -5, 2, 1, .2, .1)));
        assertFpPointEquals(-5.18034, 9, this.shape.getClosestPointTo(createRoundRectangle(-14, 5, 2, 1, .2, .1)));
        assertClosestPointInBothShapes(this.shape, createRoundRectangle(15, 2, 2, 1, .2, .1));
        assertClosestPointInBothShapes(this.shape, createRoundRectangle(5, 5, 2, 1, .2, .1));
    }

    @Override
	@Test
    public void getDistanceSquaredRoundRectangle2afp() {
        assertEpsilonEquals(59.36397, this.shape.getDistanceSquared(createRoundRectangle(-5, -5, 2, 1, .2, .1)));
        assertEpsilonEquals(56.0487, this.shape.getDistanceSquared(createRoundRectangle(-14, 5, 2, 1, .2, .1)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createRoundRectangle(15, 2, 2, 1, .2, .1)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createRoundRectangle(5, 5, 2, 1, .2, .1)));
    }

    protected MultiShape2afp createTestMultiShape(double dx, double dy) {
        Circle2afp circle = createCircle(dx - 3, dy + 2, 2);
        Triangle2afp triangle = createTestTriangle(dx +1, dy - 1);
        MultiShape2afp multishape = createMultiShape();
        multishape.add(circle);
        multishape.add(triangle);
        return multishape;
    }

    @Override
	@Test
    public void getClosestPointToMultiShape2afp() {
        assertFpPointEquals(4.40983, -0.59017, this.shape.getClosestPointTo(createTestMultiShape(-5, -5)));
        assertFpPointEquals(-5.18034, 9, this.shape.getClosestPointTo(createTestMultiShape(-18, 5)));
        assertClosestPointInBothShapes(this.shape, createTestMultiShape(15, 2));
        assertClosestPointInBothShapes(this.shape, createTestMultiShape(5, 5));
    }

    @Override
	@Test
    public void getDistanceSquaredMultiShape2afp() {
        assertEpsilonEquals(11.61456, this.shape.getDistanceSquared(createTestMultiShape(-5, -5)));
        assertEpsilonEquals(37.86844, this.shape.getDistanceSquared(createTestMultiShape(-18, 5)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createTestMultiShape(15, 2)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createTestMultiShape(5, 5)));
    }

    protected Path2afp<?, ?, ?, ?, ?, ?> createNonEmptyPath(double x, double y) {
        Path2afp<?, ?, ?, ?, ?, ?> path = createPath();
        path.moveTo(x, y);
        path.lineTo(x + 1, y + .5);
        path.lineTo(x, y + 1);
        return path;
    }

    @Override
	@Test
    public void getClosestPointToPath2afp() {
        assertFpPointEquals(2.15983, 1.65983, this.shape.getClosestPointTo(createNonEmptyPath(-5, -5)));
        assertFpPointEquals(-5.18034, 9, this.shape.getClosestPointTo(createNonEmptyPath(-18, 5)));
        assertClosestPointInBothShapes(this.shape, createNonEmptyPath(15, 2));
        assertClosestPointInBothShapes(this.shape, createNonEmptyPath(5, 5));
    }

    @Override
	@Test
    public void getDistanceSquaredPath2afp() {
        assertEpsilonEquals(75.88701, this.shape.getDistanceSquared(createNonEmptyPath(-5, -5)));
        assertEpsilonEquals(151.95437, this.shape.getDistanceSquared(createNonEmptyPath(-18, 5)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createNonEmptyPath(15, 2)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createNonEmptyPath(5, 5)));
    }

    protected Parallelogram2afp createTestParallelogram(double dx, double dy) {
        Vector2D r = createVector(4, 1).toUnitVector();
        Vector2D s = createVector(-1, -1).toUnitVector();
        return createParallelogram(dx, dy, r.getX(), r.getY(), 2, s.getX(), s.getY(), 1);
    }

    @Override
	@Test
    public void getClosestPointToParallelogram2afp() {
        assertFpPointEquals(2.63744, 1.18222, this.shape.getClosestPointTo(createTestParallelogram(-5, -5)));
        assertFpPointEquals(-5.18034, 9, this.shape.getClosestPointTo(createTestParallelogram(-18, 5)));
        assertClosestPointInBothShapes(this.shape, createTestParallelogram(15, 2));
        assertClosestPointInBothShapes(this.shape, createTestParallelogram(5, 5));
    }

    @Override
	@Test
    public void getDistanceSquaredParallelogram2afp() {
        assertEpsilonEquals(49.8011, this.shape.getDistanceSquared(createTestParallelogram(-5, -5)));
        assertEpsilonEquals(111.35891, this.shape.getDistanceSquared(createTestParallelogram(-18, 5)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createTestParallelogram(15, 2)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createTestParallelogram(5, 5)));
    }
    
    protected OrientedRectangle2afp createTestOrientedRectangle(double dx, double dy) {
        Vector2D r = createVector(4, 1).toUnitVector();
        return createOrientedRectangle(dx, dy, r.getX(), r.getY(), 2, 1);
    }

    @Override
	@Test
    public void getClosestPointToOrientedRectangle2afp() {
        assertFpPointEquals(2.0311, 1.78856, this.shape.getClosestPointTo(createTestOrientedRectangle(-5, -5)));
        assertFpPointEquals(-5.18034, 9, this.shape.getClosestPointTo(createTestOrientedRectangle(-18, 5)));
        assertClosestPointInBothShapes(this.shape, createTestOrientedRectangle(15, 2));
        assertClosestPointInBothShapes(this.shape, createTestOrientedRectangle(5, 5));
    }

    @Override
	@Test
    public void getDistanceSquaredOrientedRectangle2afp() {
        assertEpsilonEquals(56.88921, this.shape.getDistanceSquared(createTestOrientedRectangle(-5, -5)));
        assertEpsilonEquals(130.12055, this.shape.getDistanceSquared(createTestOrientedRectangle(-18, 5)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createTestOrientedRectangle(15, 2)));
        assertEpsilonEquals(0, this.shape.getDistanceSquared(createTestOrientedRectangle(5, 5)));
    }

}