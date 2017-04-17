package com.github.honourednihilist;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;

import lombok.Getter;
import lombok.Setter;

public class StartPostgresTask extends DefaultTask {

	static final String NAME = "startPostgres";

	@Internal
	@Getter
	@Setter
	private EmbeddedPostgres postgres;

	public StartPostgresTask() {
		super();
		setGroup("Embedded PostgreSQL server");
		setDescription("runs embedded PostgreSQL server.");
	}

	@TaskAction
	public void start() throws IOException {
		getLogger().info("Starting postgres...");
		postgres.start();
		getLogger().info("Postgres has been started. Url = " + postgres.getJdbcUrl());
	}

	public void stop() {
		if (postgres.isStarted()) {
			getLogger().info("Stopping postgres...");

			sleepBeforeStop();
			postgres.stop();

			getLogger().info("Postgres has been stopped");
		}
	}

	private void sleepBeforeStop() {
		PostgresqlEmbeddedExtension extension = getProject().getExtensions().getByType(PostgresqlEmbeddedExtension.class);

		if (extension.getTimeoutMillisBeforeStop() > 0) {
			getLogger().info("Sleeping {} millis before stopping postgres", extension.getTimeoutMillisBeforeStop());
			try {
				Thread.sleep(extension.getTimeoutMillisBeforeStop());
			} catch (InterruptedException e) {
				getLogger().error("", e);
			}
		}
	}
}
