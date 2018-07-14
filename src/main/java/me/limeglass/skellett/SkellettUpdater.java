package me.limeglass.skellett;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SkellettUpdater {

	public static boolean validate() {
		try {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(new URL("https://pastebin.com/raw/i9ExZiP0").openStream());
			List<String> versions = new ArrayList<String>();
			while (scanner.hasNext()) {
				versions.add(scanner.next());
			}
			if (versions.contains(Skellett.getInstance().getDescription().getVersion())) {
				Skellett.consoleMessage("&c---------------------------------------------------------", "&eThis Skellett version has been terminated and can no longer be used!", "&ePlease update your Skellett version at https://www.spigotmc.org/resources/skellett.34361","&c---------------------------------------------------------");
				return false;
			}
	    } catch(IOException error) {
			error.printStackTrace();
	    }
		return true;
	}
}
