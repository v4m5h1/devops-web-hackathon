package io.sudheer.devops.web.devopsweb.jenkins.utils;

public class JobDetailsDAO {

	private String jenkinsURL = ""; 
	private String projectName = "";
	private String projectBranch = "";
	private int buildNumber = -1;
	private boolean isMultiBranchPipeline = false;
	
	/**
	 * Adjusts the main jenkins url based on the input arguments
	 * @return queryURL
	 */
	public String getQueryURL() {
		String queryURL = this.jenkinsURL + "/job/" + this.projectName + "/";
		if(this.isMultiBranchPipeline) {
			queryURL = queryURL + "job/" + this.projectBranch;
		} 
		return queryURL;
	}
	
	/**
	 * @return the jenkinsurl
	 */
	public String getJenkinsURL() {
		return jenkinsURL;
	}
	/**
	 * @return the projectname
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @return the projectbranch
	 */
	public String getProjectBranch() {
		return projectBranch;
	}
	/**
	 * @return the buildnumber
	 */
	public int getBuildNumber() {
		return buildNumber;
	}
	/**
	 * @return the ismultibranchpipeline
	 */
	public boolean isMultiBranchPipeline() {
		return isMultiBranchPipeline;
	}
	/**
	 * @param jenkinsurl the jenkinsurl to set
	 */
	public void setJenkinsURL(String jenkinsURL) {
		this.jenkinsURL = jenkinsURL;
	}
	/**
	 * @param projectname the projectname to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @param projectbranch the projectbranch to set
	 */
	public void setProjectBranch(String projectBranch) {
		this.projectBranch = projectBranch;
	}
	/**
	 * @param buildnumber the buildnumber to set
	 */
	public void setBuildNumber(int buildNumber) {
		this.buildNumber = buildNumber;
	}
	/**
	 * @param ismultibranchpipeline the ismultibranchpipeline to set
	 */
	public void setIsMultiBranchPipeline(boolean isMultiBranchPipeline) {
		this.isMultiBranchPipeline = isMultiBranchPipeline;
	}
	
}
