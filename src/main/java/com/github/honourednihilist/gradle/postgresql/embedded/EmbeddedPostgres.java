package com.github.honourednihilist.gradle.postgresql.embedded;

import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.io.ProcessOutput;
import de.flapdoodle.embed.process.config.store.IDownloadConfig;
import de.flapdoodle.embed.process.distribution.IVersion;
import de.flapdoodle.embed.process.io.Slf4jLevel;
import de.flapdoodle.embed.process.io.Slf4jStreamProcessor;
import de.flapdoodle.embed.process.io.directories.FixedPath;
import de.flapdoodle.embed.process.runtime.Network;
import de.flapdoodle.embed.process.store.ArtifactStoreBuilder;
import de.flapdoodle.embed.process.store.PostgresArtifactStoreBuilder;

import java.io.IOException;
import java.util.HashSet;

import ru.yandex.qatools.embed.postgresql.Command;
import ru.yandex.qatools.embed.postgresql.PostgresExecutable;
import ru.yandex.qatools.embed.postgresql.PostgresProcess;
import ru.yandex.qatools.embed.postgresql.PostgresStarter;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresDownloadConfigBuilder;
import ru.yandex.qatools.embed.postgresql.config.RuntimeConfigBuilder;
import ru.yandex.qatools.embed.postgresql.ext.LogWatchStreamProcessor;

import static java.util.Collections.singletonList;
import static org.slf4j.LoggerFactory.getLogger;

public class EmbeddedPostgres {

	private final IVersion version;
	private final String host;
	private final int port;
	private final String dbName;
	private final String username;
	private final String password;
	private final String artifactStorePath;

	private PostgresProcess process;
	private String jdbcUrl;

	public EmbeddedPostgres(IVersion version, String host, int port, String dbName, String username, String password, String artifactStorePath) {
		this.version = version;
		this.host = host;
		this.port = port;
		this.dbName = dbName;
		this.username = username;
		this.password = password;
		this.artifactStorePath = artifactStorePath;
	}

	public synchronized void start() throws IOException {
		if (process != null) {
			throw new IllegalStateException();
		}

		Command command = Command.Postgres;

		IDownloadConfig downloadConfig = new PostgresDownloadConfigBuilder()
				.defaultsForCommand(command)
				.artifactStorePath(new FixedPath(artifactStorePath))
				.build();

		ArtifactStoreBuilder artifactStoreBuilder = new PostgresArtifactStoreBuilder()
				.defaults(command)
				.download(downloadConfig);

		LogWatchStreamProcessor logWatch = new LogWatchStreamProcessor("started",
				new HashSet<>(singletonList("failed")),
				new Slf4jStreamProcessor(getLogger("postgres"), Slf4jLevel.TRACE));

		IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder()
				.defaults(command)
				.processOutput(new ProcessOutput(logWatch, logWatch, logWatch))
				.artifactStore(artifactStoreBuilder)
				.build();

		PostgresStarter<PostgresExecutable, PostgresProcess> starter = new PostgresStarter<>(PostgresExecutable.class, runtimeConfig);

		PostgresConfig config = new PostgresConfig(version,
				new AbstractPostgresConfig.Net(host, port == 0 ? Network.getFreeServerPort() : port),
				new AbstractPostgresConfig.Storage(dbName),
				new AbstractPostgresConfig.Timeout(),
				new AbstractPostgresConfig.Credentials(username, password));
		process = starter.prepare(config).start();
		jdbcUrl = "jdbc:postgresql://" + config.net().host() + ":" + config.net().port() + "/" + config.storage().dbName();
	}

	public synchronized void stop() {
		if (process == null) {
			throw new IllegalStateException();
		}

		process.stop();
		process = null;
	}

	public synchronized boolean isStarted() {
		return process != null;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}
}
