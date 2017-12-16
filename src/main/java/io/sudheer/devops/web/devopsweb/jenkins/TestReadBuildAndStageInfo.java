package io.sudheer.devops.web.devopsweb.jenkins;

import io.sudheer.devops.web.devopsweb.jenkins.ReadBuildAndStageInfo;
import io.sudheer.devops.web.devopsweb.jenkins.utils.JobDetailsDAO;

public class TestReadBuildAndStageInfo {

	public static void main(String[] args) throws Exception {
		String jenkinsURL = "http://192.168.43.115:8080";
		String projectName = "devops-web-hackathon";
		String projectBranch = "master";
		int buildNumber = 6;
		boolean isMultiBranchPipeline = true;
		
		JobDetailsDAO jobDetailsObj = new JobDetailsDAO();
		jobDetailsObj.setJenkinsURL(jenkinsURL);
		jobDetailsObj.setProjectName(projectName);
		jobDetailsObj.setProjectBranch(projectBranch);
		
		jobDetailsObj.setIsMultiBranchPipeline(isMultiBranchPipeline);
		
		//URL for MultiBranch Configuration Pipelines
		//String mainURL = "http://192.168.43.115:8080/job/devops-web-hackathon/job/master/4";
		
		//URL for Simple Pipelines
		//String mainURL = "http://192.168.43.115:8080/job/devops-web-maven/26";
		
		System.out.println(ReadBuildAndStageInfo.getFullInfo(jobDetailsObj));
		
		/*
		jobDetailsObj.setBuildNumber(buildNumber);
		String temp = ReadBuildAndStageInfo.getBuildInfo(jobDetailsObj);
		System.out.println(ReadBuildAndStageInfo.getHeaderInfo() + "\n" + temp);
		*/

	}

}
