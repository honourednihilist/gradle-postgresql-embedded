package com.github.honourednihilist;

import de.flapdoodle.embed.process.distribution.IVersion;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.LinkedHashMap;
import java.util.Map;

import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.PRODUCTION;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_3;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_4;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_5;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_6;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.V9_3_15;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.V9_4_10;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.V9_5_5;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.V9_6_2;

public class GradlePostgresqlEmbeddedPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		project.getExtensions().create(PostgresqlEmbeddedExtension.NAME, PostgresqlEmbeddedExtension.class);
		StartPostgresTask startTask = project.getTasks().create(StartPostgresTask.NAME, StartPostgresTask.class);

		project.afterEvaluate(pro -> {
			PostgresqlEmbeddedExtension extension = pro.getExtensions().getByType(PostgresqlEmbeddedExtension.class);
			EmbeddedPostgres postgres = new EmbeddedPostgres(parseVersion(extension.getVersion()),
					extension.getHost(),
					extension.getPort(),
					extension.getDbName(),
					extension.getUsername(),
					extension.getPassword());

			startTask.setPostgres(postgres);
		});

		project.getGradle().buildFinished(buildResult -> startTask.stop());
	}

	IVersion parseVersion(String version) {
		Map<String, IVersion> versions = new LinkedHashMap<>();
		versions.put(PRODUCTION.name(), PRODUCTION);
		versions.put(V9_6.name(), V9_6);
		versions.put(V9_5.name(), V9_5);
		versions.put(V9_4.name(), V9_4);
		versions.put(V9_3.name(), V9_3);
		versions.put(V9_6_2.name(), V9_6_2);
		versions.put(V9_5_5.name(), V9_5_5);
		versions.put(V9_4_10.name(), V9_4_10);
		versions.put(V9_3_15.name(), V9_3_15);
		versions.put(V9_6_2.asInDownloadPath(), V9_6_2);
		versions.put(V9_5_5.asInDownloadPath(), V9_5_5);
		versions.put(V9_4_10.asInDownloadPath(), V9_4_10);
		versions.put(V9_3_15.asInDownloadPath(), V9_3_15);
		return versions.getOrDefault(version, () -> version);
	}
}
