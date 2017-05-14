package com.github.honourednihilist.gradle.postgresql.embedded;

import de.flapdoodle.embed.process.distribution.IVersion;
import de.flapdoodle.embed.process.runtime.Network;

import java.io.IOException;

import ru.yandex.qatools.embed.postgresql.PostgresProcess;
import ru.yandex.qatools.embed.postgresql.PostgresStarter;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;

public class EmbeddedPostgres {

	private final IVersion version;
	private final String host;
	private final int port;
	private final String dbName;
	private final String username;
	private final String password;

	private PostgresProcess process;
	private String jdbcUrl;

	public EmbeddedPostgres(IVersion version, String host, int port, String dbName, String username, String password) {
		this.version = version;
		this.host = host;
		this.port = port;
		this.dbName = dbName;
		this.username = username;
		this.password = password;
	}

	public synchronized void start() throws IOException {
		if (process != null) {
			throw new IllegalStateException();
		}

		PostgresConfig config = new PostgresConfig(version,
				new AbstractPostgresConfig.Net(host, port == 0 ? Network.getFreeServerPort() : port),
				new AbstractPostgresConfig.Storage(dbName),
				new AbstractPostgresConfig.Timeout(),
				new AbstractPostgresConfig.Credentials(username, password));
		process = PostgresStarter.getDefaultInstance().prepare(config).start();
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
