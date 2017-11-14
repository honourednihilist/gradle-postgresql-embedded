package com.github.honourednihilist.gradle.postgresql.embedded;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.PRODUCTION;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V10;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_5;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_6;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.V10_0;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.V9_6_5;

public class GradlePostgresqlEmbeddedPluginTest {

	@Test
	public void testParseVersion() {
		GradlePostgresqlEmbeddedPlugin plugin = new GradlePostgresqlEmbeddedPlugin();

		assertThat(plugin.parseVersion(PRODUCTION.name()), is(PRODUCTION));
		assertThat(plugin.parseVersion(V10.name()), is(V10));
		assertThat(plugin.parseVersion(V9_6.name()), is(V9_6));
		assertThat(plugin.parseVersion(V9_5.name()), is(V9_5));

		assertThat(plugin.parseVersion(V9_6_5.name()), is(V9_6_5));
		assertThat(plugin.parseVersion(V9_6_5.asInDownloadPath()), is(V9_6_5));

		assertThat(plugin.parseVersion(V10_0.name()), is(V10_0));
		assertThat(plugin.parseVersion(V10_0.asInDownloadPath()), is(V10_0));

		assertThat(plugin.parseVersion("any version").asInDownloadPath(), is("any version"));
	}

}
