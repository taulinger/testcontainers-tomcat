package de.taulinger.tctomcat;

import jakarta.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Paths;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

/**
 * @author taulinger
 */
@Testcontainers
public class BasicTest {

	final static DockerImageName TOMCAT_IMAGE = DockerImageName.parse("tomcat:10.1.0-jdk17-temurin");

	final static String WAR_FILE_NAME = "testcontainers-tomcat";

	final static MountableFile WAR_FILE = MountableFile
			.forHostPath(Paths.get("target/testcontainers-tomcat-1.0-SNAPSHOT.war").toAbsolutePath(), 0777);

	@Container
	GenericContainer tomcatContainer = new GenericContainer(TOMCAT_IMAGE).withExposedPorts(8080).withReuse(true)
			.withCopyFileToContainer(WAR_FILE, "/usr/local/tomcat/webapps/" + WAR_FILE_NAME + ".war")
			.waitingFor(Wait.forHttp("/" + WAR_FILE_NAME).forStatusCode(200));

	@DisplayName("Get /index.html")
	@Order(1)
	@Test
	public void getIndexHtml() throws IOException, InterruptedException {
		// System.out.println(tomcatContainer.getLogs());
		// System.out.println(buildURI(tomcatContainer, "index.html"));
		var client = HttpClient.newBuilder().build();
		var request = HttpRequest.newBuilder().uri(buildURI(tomcatContainer, "index.html"))
				.timeout(Duration.ofMinutes(1)).GET().build();
		var response = client.send(request, BodyHandlers.ofString());

		assertEquals(200, response.statusCode());
		assertTrue(response.body().contains("Hello World!"));

	}

	@DisplayName("Get /helloservlet")
	@Order(2)
	@Test
	public void getTestHtml() throws IOException, InterruptedException {
		// System.out.println(microContainer.getLogs());
		// System.out.println(buildURI(tomcatContainer, "helloservlet"));

		var client = HttpClient.newBuilder().build();
		var request = HttpRequest.newBuilder().uri(buildURI(tomcatContainer, "helloservlet"))
				.timeout(Duration.ofMinutes(1)).GET().build();

		var response = client.send(request, BodyHandlers.ofString());

		assertEquals(200, response.statusCode());
		assertTrue(response.body().contains("This is a Jakarta EE 9 Servlet!"));
	}

	static URI buildURI(GenericContainer container, String path) {
		return UriBuilder.fromUri("http://" + container.getHost()).port(container.getMappedPort(8080))
				.path(WAR_FILE_NAME).path(path).build();
	}

}
