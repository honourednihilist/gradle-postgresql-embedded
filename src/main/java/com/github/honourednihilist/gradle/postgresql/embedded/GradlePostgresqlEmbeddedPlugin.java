package com.github.honourednihilist.gradle.postgresql.embedded;

import de.flapdoodle.embed.process.distribution.IVersion;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.LinkedHashMap;
import java.util.Map;

import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.PRODUCTION;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V10;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_5;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_6;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.V10_0;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.V9_6_5;

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
					extension.getPassword(),
					extension.getArtifactStorePath());
			startTask.setPostgres(postgres);

			if (extension.isStopWhenBuildFinished()) {
				project.getGradle().buildFinished(buildResult -> startTask.stop());
			}
		});
	}

	IVersion parseVersion(String version) {
		Map<String, IVersion> versions = new LinkedHashMap<>();

		versions.put(PRODUCTION.name(), PRODUCTION);
		versions.put(V10.name(), V10);
		versions.put(V9_6.name(), V9_6);
		versions.put(V9_5.name(), V9_5);

		versions.put(V10_0.name(), V10_0);
		versions.put(V9_6_5.name(), V9_6_5);

		versions.put(V10_0.asInDownloadPath(), V10_0);
		versions.put(V9_6_5.asInDownloadPath(), V9_6_5);

		return versions.getOrDefault(version, () -> version);
	}
}
