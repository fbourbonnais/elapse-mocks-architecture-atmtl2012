package com.elapsetech.confsubmission;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.elapsetech.auth.User;

import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SubmissionServiceTest {

	private static final String A_TALK_DESCRIPTION = "Talk description text";
	private static final String A_TITLE = "Title";
	
	private SubmissionService service;
	
	@Mock
	private User aUser;
	
	@Mock
	private SubmissionPool submissionPool;
	
	@Mock
	private Submission submission;
	
	@Mock
	private SubmissionFactory submissionFactory;
	
	@Mock
	private ProgramCommittee programCommittee;
	
	@Before
	public void setupFakeSubmissionCreation() {
		willReturn(submission).given(submissionFactory).createSubmission(A_TITLE, A_TALK_DESCRIPTION);
	}
	
	@Before
	public void setupSubmissionService() {
		service = new SubmissionService(submissionPool, programCommittee, submissionFactory);
		// Hum... Beaucoup de parametres... Ils se ressemblent conceptuellement...
		// Ah! En fait il manque peut-etre l'objet Conference qui contient 
		// un SubmissionPool et une programCommittee...
	}
	
	@Test
	public void shouldAddSubmissionToTheSubmissionsPoolWhenTalkIsSubmitted() {
		service.submitTalk(aUser, A_TITLE, A_TALK_DESCRIPTION);
		
		verify(submissionFactory).createSubmission(A_TITLE, A_TALK_DESCRIPTION);
		verify(submissionPool).addSubmission(submission);
	}
	
	@Test
	public void shouldNotifyProgramCommitteeWhenTalkIsSubmitted() {
		service.submitTalk(aUser, A_TITLE, A_TALK_DESCRIPTION);
		verify(programCommittee).notifyNewSubmission(submission);
	}

}
