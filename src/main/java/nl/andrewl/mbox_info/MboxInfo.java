package nl.andrewl.mbox_info;

import nl.andrewl.mboxparser.CollectingEmailHandler;
import nl.andrewl.mboxparser.MBoxParser;

import java.io.IOException;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;

public class MboxInfo {
	public static void main(String[] args) throws IOException {
		CollectingEmailHandler handler = new CollectingEmailHandler();
		MBoxParser parser = new MBoxParser(handler);
		for (String arg : args) {
			parser.parse(Path.of(arg));
		}
		System.out.println("Total emails: " + handler.getEmails().size());
		ZonedDateTime earliest = handler.getEmails().stream()
				.map(e -> e.date)
				.min(ChronoZonedDateTime::compareTo)
				.orElseThrow();
		System.out.println("First email sent at: " + earliest.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
		ZonedDateTime latest = handler.getEmails().stream()
				.map(e -> e.date)
				.max(ChronoZonedDateTime::compareTo)
				.orElseThrow();
		System.out.println("Last email sent at: " + latest.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
	}
}
