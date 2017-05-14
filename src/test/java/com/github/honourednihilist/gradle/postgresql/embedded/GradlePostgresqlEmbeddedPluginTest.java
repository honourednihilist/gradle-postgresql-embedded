package com.github.honourednihilist.gradle.postgresql.embedded;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.PRODUCTION;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_3;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_4;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_5;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_6;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.V9_3_15;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.V9_4_10;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.V9_5_5;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.V9_6_2;

public class GradlePostgresqlEmbeddedPluginTest {

	@Test
	public void testParseVersion() {
		GradlePostgresqlEmbeddedPlugin plugin = new GradlePostgresqlEmbeddedPlugin();

		assertThat(plugin.parseVersion(PRODUCTION.name()), is(PRODUCTION));
		assertThat(plugin.parseVersion(V9_6.name()), is(V9_6));
		assertThat(plugin.parseVersion(V9_5.name()), is(V9_5));
		assertThat(plugin.parseVersion(V9_4.name()), is(V9_4));
		assertThat(plugin.parseVersion(V9_3.name()), is(V9_3));

		assertThat(plugin.parseVersion(V9_6_2.name()), is(V9_6_2));
		assertThat(plugin.parseVersion(V9_5_5.name()), is(V9_5_5));
		assertThat(plugin.parseVersion(V9_4_10.name()), is(V9_4_10));
		assertThat(plugin.parseVersion(V9_3_15.name()), is(V9_3_15));

		assertThat(plugin.parseVersion(V9_6_2.asInDownloadPath()), is(V9_6_2));
		assertThat(plugin.parseVersion(V9_5_5.asInDownloadPath()), is(V9_5_5));
		assertThat(plugin.parseVersion(V9_4_10.asInDownloadPath()), is(V9_4_10));
		assertThat(plugin.parseVersion(V9_3_15.asInDownloadPath()), is(V9_3_15));

		assertThat(plugin.parseVersion("any version").asInDownloadPath(), is("any version"));
	}

}
