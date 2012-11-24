package com.elapsetech.confsubmission;

import com.elapsetech.auth.User;

public class SubmissionService {

	private final SubmissionFactory submissionFactory;
	private final SubmissionPool pool;
	private final ProgramCommittee programCommittee;
 
	public SubmissionService(SubmissionPool pool, ProgramCommittee programCommittee, SubmissionFactory submissionFactory) {
		this.pool = pool;
		this.programCommittee = programCommittee;
		this.submissionFactory = submissionFactory;
	}

	public void submitTalk(User user, String title, String description) {
		Submission submission = submissionFactory.createSubmission(title, description);
		pool.addSubmission(submission);
		programCommittee.notifyNewSubmission(submission);
	}

}
