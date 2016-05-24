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

package org.arakhne.maven.plugins.tagreplacer;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.maven.plugin.MojoExecutionException;

import org.arakhne.maven.JavaSourceFileFilter;

/**
 * Generate the Java source files and replace the macros by the corresponding values
 * on the fly.
 * Supported macros are described in {@link AbstractReplaceMojo}.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 *
 * @goal generatesrc
 * @phase generate-sources
 * @requireProject true
 * @threadSafe
 */
public class GenerateSourceMojo extends AbstractReplaceMojo {

	/** Are the directories where the source files are located.
	 * By default, the directory "src/main/java" is used.
	 *
	 * @parameter
	 */
	private File[] sources;

	/** Is the directory where the generated files are located.
	 * By default, the directory "target/generated-sources/java" is used.
	 *
	 * @parameter
	 */
	private File outputLocation;

	/** Set of the files generated by the plugin.
	 * This set permits to not generate the file more than one time.
	 */
	private final Set<File> generateTargets = new TreeSet<>();

	/** Set of the files inside the file system.
	 */
	private final Map<File, Map<File, File>> bufferedFiles = new TreeMap<>();

	@Override
	protected final synchronized void executeMojo() throws MojoExecutionException {
		File targetDir = null;
		if (this.outputLocation == null) {
			targetDir = new File(getGeneratedSourceDirectory(), "java"); //$NON-NLS-1$
		} else {
			targetDir = this.outputLocation;
		}

		executeMojo(targetDir);
	}

	/** Execute the replacement mojo inside the given target directory.
	 *
	 * @param targetDir the target dir.
	 * @throws MojoExecutionException on error.
	 */
	protected synchronized void executeMojo(File targetDir) throws MojoExecutionException {
		if (this.generateTargets.contains(targetDir)
				&& targetDir.isDirectory()) {
			getLog().debug("Skiping " + targetDir + " because is was already generated");  //$NON-NLS-1$//$NON-NLS-2$
			return;
		}

		this.generateTargets.add(targetDir);

		final File[] sourceDirs;
		final File mainDirectory = new File(getSourceDirectory(), "main"); //$NON-NLS-1$
		if (this.sources == null || this.sources.length == 0) {
			sourceDirs = new File[] {
				new File(mainDirectory, "java"), //$NON-NLS-1$
			};
		} else {
			sourceDirs = this.sources;
		}

		clearInternalBuffers();

		for (final File sourceDir : sourceDirs) {
			Map<File, File> htmlBasedFiles = this.bufferedFiles.get(sourceDir);

			if (htmlBasedFiles == null) {
				htmlBasedFiles = new TreeMap<>();
				if (sourceDir.isDirectory()) {
					// Search for .java files
					findFiles(sourceDir, new JavaSourceFileFilter(), htmlBasedFiles);
				}

				this.bufferedFiles.put(sourceDir, htmlBasedFiles);
			}

			for (final Entry<File, File> entry : htmlBasedFiles.entrySet()) {
				final String baseFile = removePathPrefix(entry.getValue(), entry.getKey());
				replaceInFileBuffered(
						entry.getKey(),
						new File(targetDir, baseFile),
						ReplacementType.HTML,
						sourceDirs,
						false);
			}
		}
	}

}
