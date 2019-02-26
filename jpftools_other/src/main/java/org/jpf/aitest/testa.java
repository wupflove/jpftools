/**
 * @author wupf@asiainfo. com
 */
package org.jpf.aitest;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;

/**
 * @author wupf
 *
 */
public class testa extends AntClassLoader {

	/**
	 * 
	 */
	public testa() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param project
	 * @param classpath
	 */
	public testa(Project project, Path classpath) {
		super(project, classpath);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parent
	 * @param parentFirst
	 */
	public testa(ClassLoader parent, boolean parentFirst) {
		super(parent, parentFirst);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param project
	 * @param classpath
	 * @param parentFirst
	 */
	public testa(Project project, Path classpath, boolean parentFirst) {
		super(project, classpath, parentFirst);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parent
	 * @param project
	 * @param classpath
	 * @param parentFirst
	 */
	public testa(ClassLoader parent, Project project, Path classpath, boolean parentFirst) {
		super(parent, project, classpath, parentFirst);
		// TODO Auto-generated constructor stub
	}

}
