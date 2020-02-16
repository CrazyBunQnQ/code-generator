package org.crazybun.codegen.ssm.assist;


/**
 * {@link AbstractFinals#TEMPLATE_PATH}
 */
public enum Template {
	SAMPLE("sample/", new String[]{"manage.jsp","add.jsp","edit.jsp","view.jsp","row.jsp","queryResult.jsp","result.jsp"}),
	MSM("msm/", new String[]{"manage.jsp","add.jsp","edit.jsp","view.jsp","row.jsp","queryResult.jsp","result.jsp"});
	
	private String path;
	private String[] jspFiles;
	private Template(String path, String[] jspFiles) {
		this.path = path;
		this.jspFiles = jspFiles;
	}
	public String getPath() {
		return path;
	}
	public String[] getJspFiles() {
		return jspFiles;
	}
}
