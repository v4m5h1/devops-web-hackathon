package io.sudheer.devops.web.devopsweb.jenkins;

public class TestReadBuildAndStageInfo {

	public static void main(String[] args) throws Exception {
		String jenkinsURL = "http://192.168.43.115:8080";
		String projectName = "Pipeline-devops-web-maven";
		String projectBranch = "master";
		String buildNumber = "26";
		boolean multiBranchPipeline = false;
		//URL for MultiBranch Configuration Pipelines
		//String mainURL = "http://192.168.43.115:8080/job/devops-web-hackathon/job/master/4";
		
		//URL for Simple Pipelines
		//String mainURL = "http://192.168.43.115:8080/job/Pipeline-devops-web-maven/26";
		
		ReadBuildAndStageInfo infoObject = new ReadBuildAndStageInfo();
		infoObject.setJenkinsURL(jenkinsURL);
		infoObject.setProjectName(projectName);
		infoObject.setProjectBranch(projectBranch);
		infoObject.setBuildNumber(buildNumber);
		infoObject.setMultiBranchPipeline(multiBranchPipeline);
		System.out.println(infoObject.getFullInfo());

	}

}
