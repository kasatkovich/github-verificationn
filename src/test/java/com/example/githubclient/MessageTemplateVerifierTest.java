package com.example.githubclient;

import org.junit.Test;
import org.junit.Assert;

import static com.example.githubclient.MessageTemplateVerifier.process;
import static org.junit.Assert.assertFalse;


public class MessageTemplateVerifierTest extends AbstractGithubClientTest {



	@Test //проверяем, на каких заголовках проходят, на каких нет
	public void testProcessFunc() {
		String title1 = "LEETCODE 2022 Added solution for course-schedule";
		String title2 = "LEETCODE 2022 Added solution for squares-of-a-sorted-array";
		String title3 = "LEETCODE 1021 Added solution for valid-palindrome";

		String title4 = "Added My Generator";
		String title5 = "Design";
		String title6 = "LEETCODE 1021";
		String title7 = "Triangle solution 21.11.20";
		String title8 = "LEETCODE 3032 Added solution for sort-list";
		String title9 = "LEETCODE 2022 Added";
		String title10 = "SMTHELSE 2022 Added solution for flatten-nested-list-iterator)";

		Assert.assertTrue(process(title1));
		Assert.assertTrue(process(title2));
		Assert.assertTrue(process(title3));

		assertFalse(process(title4));
		assertFalse(process(title5));
		assertFalse(process(title6));
		assertFalse(process(title7));
		assertFalse(process(title8));
		assertFalse(process(title9));
		assertFalse(process(title10));

	}

}
