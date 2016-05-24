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

package org.arakhne.afc.ui.vector;

/** Interface that is representing a generic margins. 
 * See {@link VectorToolkit} to create an instance.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @deprecated see JavaFX API
 */
@Deprecated
public interface Margins {

    /**
     * Returns the size of the top margin.
     * 
     * @return the size.
     */
    public float top();

    /**
     * Returns the size of the left margin.
     * 
     * @return the size.
     */
    public float left();

    /**
     * Returns the size of the bottom margin.
     * 
     * @return the size.
     */
    public float bottom();

    /**
     * Returns the size of the right margin.
     * 
     * @return the size.
     */
    public float right();

    /**
     * Set the margins.
     * 
     * @param top
     * @param left
     * @param right
     * @param bottom
     */
    public void set(float top, float left, float right, float bottom);

}
