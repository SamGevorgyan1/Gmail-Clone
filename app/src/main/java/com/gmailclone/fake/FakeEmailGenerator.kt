package com.gmailclone.fake

import android.graphics.Color
import com.github.javafaker.Faker
import com.gmailclone.model.EmailContent


object FakeEmailGenerator {

    private val faker = Faker()
    val fakeEmailList: List<EmailContent> = fakeEmailList()

    private fun fakeEmailList(): List<EmailContent> {

        return listOf(
            EmailContent(
                id = "234567890",
                subject = "Important: Account Security Notice",
                recipient = "johndoe@gmail.com",
                userName = "John Doe",
                body = faker.lorem().paragraphs(2).joinToString("\n"),
                userImage = Color.RED
            ),
            EmailContent(
                id = "9876543210",
                subject = "Important Announcement: Changes to Our Services",
                recipient = "avataylor@gmail.com",
                userName = "Ava Taylor",
                body = "We hope this email finds you well. We want to inform you about some important changes to our services that will enhance your overall experience.",
                userImage = Color.GREEN
            ),
            EmailContent(
                id = "2468135790",
                subject = "Action Required: Verify Your Account Information",
                recipient = "sarahthompson@gmail.com",
                userName = "Sarah Thompson",
                body = "We have recently updated our security protocols, and we kindly request you to verify your account information. This step is necessary to ensure the security of your account and protect it from unauthorized access.",
                userImage = Color.BLUE
            ),
            EmailContent(
                id = "1357924680",
                subject = " Congratulations! You've Won a Prize",
                recipient = "michaelbrown@gmail.com",
                userName = "Michael Brown",
                body = "To claim your prize, please reply to this email or contact our customer service within [claim period]. We will provide you with further instructions on how to procee",
                userImage = Color.GRAY
            ),
            EmailContent(
                id = "5678901234",
                subject = "Upcoming Event: Save the Date",
                recipient = "oliviadavis@gmail.com",
                userName = "Olivia Davis",
                body = "This event will feature keynote speakers, interactive workshops, and networking opportunities. It promises to be a valuable experience for professionals in your field.\n" +
                        "\n" +
                        "We will share more details and the official invitation with you in the coming weeks. We look forward to your presence at the event.",
                userImage = Color.CYAN
            ),
            EmailContent(
                id = "4321098765",
                subject = "Important Update: Change in Meeting Schedule",
                recipient = "miajohnson@gmail.com",
                userName = "Mia Johnson",
                body = "I hope this email finds you well. I wanted to inform you about a change in the meeting schedule for [project/team/group].",
                userImage = Color.GRAY
            ),
            EmailContent(
                id = "9876543210",
                subject = "Important Announcement: Changes to Our Services",
                recipient = "emmajohnson@gmail.com",
                userName = "Emma Johnson",
                body = "We hope this email finds you well. We want to inform you about some important changes to our services that will enhance your overall experience.",
                userImage = Color.BLUE
            ),
            EmailContent(
                id = "9876543210",
                subject = "Important Announcement: Changes to Our Services",
                recipient = "johnsmith@gmail.com",
                userName = "John Smith",
                body = "We hope this email finds you well. We want to inform you about some important changes to our services that will enhance your overall experience.",
                userImage = Color.GRAY
            ),
            EmailContent(
                id = "9876543210",
                subject = "Important Announcement: Changes to Our Services",
                recipient = "michaeldavis@gmail.com",
                userName = "Michael Davis",
                body = "We hope this email finds you well. We want to inform you about some important changes to our services that will enhance your overall experience.",
                userImage = Color.CYAN
            ),
        )
    }
}