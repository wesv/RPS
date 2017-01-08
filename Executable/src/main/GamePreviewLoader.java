package main;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Kayler Renslow<br>
 * <br>
 *
 */
public class GamePreviewLoader {
	private URI uri;
	public GamePreviewLoader(URI uri) {
		this.uri = uri;
	}
	public void load() throws IOException, URISyntaxException {
		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(uri);
		}
	}
}
