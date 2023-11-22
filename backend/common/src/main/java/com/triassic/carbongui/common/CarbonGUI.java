package com.triassic.carbongui.common;

import com.triassic.carbongui.common.configuration.Configuration;
import com.triassic.carbongui.common.configuration.ConfigurationContainer;
import com.triassic.carbongui.common.manager.PlayerManager;
import com.triassic.carbongui.common.manager.ScoreboardManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.nio.file.Path;

public final class CarbonGUI {

    private final Platform platform;
    private final Path dataFolder;

    private PlayerManager playerManager;
    private ScoreboardManager scoreboardManager;
    private ConfigurationContainer<Configuration> config;

    public CarbonGUI(
            final Platform platform,
            final Path dataFolder
    ) {
        this.platform = platform;
        this.dataFolder = dataFolder;
        this.init();
    }

    private void init() {
        this.playerManager = new PlayerManager();

        if (!isProxy()) {
            this.loadConfig();
            this.scoreboardManager = new ScoreboardManager(platform, playerManager, config);
        }

        logger().info("CarbonGUI loaded & initialized successfully.");
    }

    private void loadConfig() {
        try {
            this.config = ConfigurationContainer.load(dataFolder, Configuration.class);
        } catch (Throwable e) {
            logger().error("Could not load config.conf file", e);
        }
    }

    /**
     * Retrieves the logger associated with CarbonGUI.
     *
     * @return the logger for CarbonGUI.
     */

    @NotNull
    public Logger logger() {
        return this.platform.logger();
    }

    /**
     * Checks whether the platform is operating in a proxy environment.
     *
     * @return {@code true} if the platform is operating in a proxy environment, {@code false} otherwise.
     */

    public boolean isProxy() {
        return this.platform.isProxy();
    }

    /**
     * Retrieves the configuration associated with CarbonGUI.
     *
     * @return the configuration for CarbonGUI.
     */

    @Nullable
    public ConfigurationContainer<Configuration> config() {
        return this.config;
    }

    /**
     * Retrieves the player manager associated with CarbonGUI.
     *
     * @return the player manager for CarbonGUI.
     */

    @NotNull
    public PlayerManager playerManager() {
        return this.playerManager;
    }

    /**
     * Retrieves the scoreboard manager associated with CarbonGUI.
     *
     * @return the scoreboard manager for CarbonGUI.
     */

    @NotNull
    public ScoreboardManager scoreboardManager() {
        return this.scoreboardManager;
    }
}
