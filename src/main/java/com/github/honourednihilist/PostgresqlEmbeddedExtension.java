package com.github.honourednihilist;

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

	private int timeoutMillisBeforeStop = 0;
}
