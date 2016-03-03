/* 
 * $Id$
 * 
 * Copyright (c) 2013 Christophe BOHRHAUER
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
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
package org.arakhne.afc.math.stochastic;

import java.util.Map;
import java.util.Random;

import org.arakhne.afc.math.MathException;
import org.arakhne.afc.math.MathFunctionRange;

/**
 * Law that representes a gaussian density.
 * <p>
 * Reference:
 * <a href="http://en.wikipedia.org/wiki/Log-normal_distribution">Log-Normal Distribution</a>.
 * <p>
 * This class uses the gaussian random number distribution provided by {@link Random}. 
 * 
 * @author $Author: cbohrhauer$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public class LogNormalStochasticLaw extends StochasticLaw {
	
	private static final double SQRT2PI = Math.sqrt(2.f*Math.PI);
	
	/** Replies a random value that respect
	 * the current stochastic law.
	 * 
	 * @param mean is the mean of the normal distribution.
	 * @param standardDeviation is the standard deviation associated to the nromal distribution.
	 * @return a value depending of the stochastic law parameters
	 * @throws MathException 
	 */
	public static double random(double mean, double standardDeviation) throws MathException {
		return StochasticGenerator.generateRandomValue(new LogNormalStochasticLaw(mean, standardDeviation));
	}

	private double mean;
	private double standardDeviation;

	/**
	 * Construct a law with the following parameters.
	 * <ul>
	 * <li><code>mean</code></li>
	 * <li><code>standardDeviation</code></li>
	 * </ul>
	 * 
	 * @param parameters is the set of accepted paramters.
	 * @throws LawParameterNotFoundException if the list of parameters does not permits to create the law.
	 * @throws OutsideDomainException when standardDevisition is negative or nul.
	 */
	public LogNormalStochasticLaw(Map<String,String> parameters) throws OutsideDomainException, LawParameterNotFoundException {
		this.mean = paramFloat("mean",parameters); //$NON-NLS-1$
		this.standardDeviation = paramFloat("standardDeviation",parameters); //$NON-NLS-1$
		if (this.standardDeviation<=0) throw new OutsideDomainException(this.standardDeviation);
	}

	/**
	 * @param mean1 is the mean of the normal distribution.
	 * @param standardDeviation1 is the standard deviation associated to the nromal distribution.
	 * @throws OutsideDomainException when standardDevisition is negative or nul.
	 */
	public LogNormalStochasticLaw(double mean1, double standardDeviation1) throws OutsideDomainException {
		if (standardDeviation1<=0) throw new OutsideDomainException(standardDeviation1);
		this.mean = mean1;
		this.standardDeviation = standardDeviation1;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("LOGNORMAL(mean="); //$NON-NLS-1$
		b.append(this.mean);
		b.append(";deviation="); //$NON-NLS-1$
		b.append(this.standardDeviation);
		b.append(')');
		return b.toString();
	}
	
	/** {@inheritDoc}
	 */
	@Override
	public double f(double x)  throws MathException {
		if (x<=0)
			throw new OutsideDomainException(x);
		double ex = Math.log(x) - this.mean;
		ex = ex * ex;
		return Math.exp((-ex)/(2.f*this.standardDeviation*this.standardDeviation)) / (x * this.standardDeviation * SQRT2PI);
	}

	/** {@inheritDoc}
	 */
	@Override
	public MathFunctionRange[] getRange() {
		return new MathFunctionRange[] {new MathFunctionRange(0, false, Double.POSITIVE_INFINITY, false) };
	}
	
	/** Replies the x according to the value of the distribution function.
	 * 
	 * @param u is a value given by the uniform random variable generator {@code U(0,1)}.
	 * @return {@code F<sup>-1</sup>(u)}
	 * @throws MathException in case {@code F<sup>-1</sup>(u)} could not be computed
	 */
	@Override
	public double inverseF(double u) throws MathException {
		return Math.exp(this.standardDeviation*u + this.mean);
	}

	/** Replies the x according to the value of the inverted 
	 * cummulative distribution function {@code F<sup>-1</sup>(u)}
	 * where {@code u = U(0,1)}.
	 * 
	 * @param U is the uniform random variable generator {@code U(0,1)}.
	 * @return {@code F<sup>-1</sup>(u)}
	 * @throws MathException in case {@code F<sup>-1</sup>(u)} could not be computed
	 */
	@Override
	protected final double inverseF(Random U) throws MathException {
		double u = (U.nextGaussian()+1)/2.f;
		return inverseF(u);
	}

}