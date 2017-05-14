package com.github.honourednihilist.gradle.postgresql.embedded;

import lombok.Data;

import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.PRODUCTION;

@Data
public class PostgresqlEmbeddedExtension {

	static final String NAME = "postgresEmbedded";

	private String version = PRODUCTION.name();
	private String host = "localhost";
	private int port = 0;
	private String dbName = "embedded";
	private String username = "username";
	private String password = "password";

	private boolean stopWhenBuildFinished = true;
	private int timeoutMillisBeforeStop = 0;
}
