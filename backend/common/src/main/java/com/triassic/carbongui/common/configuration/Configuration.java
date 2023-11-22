package com.triassic.carbongui.common.configuration;

import com.triassic.carbongui.common.enums.MessagingMode;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@ConfigSerializable
@SuppressWarnings("FieldMayBeFinal")
public final class Configuration {

    private MessagingSettings messagingSettings = new MessagingSettings();

    private ScoreboardSettings scoreboardSettings = new ScoreboardSettings();

    public MessagingSettings getMessagingSettings() {
        return this.messagingSettings;
    }

    public ScoreboardSettings getScoreboardSettings() {
        return this.scoreboardSettings;
    }

    @ConfigSerializable
    public static class MessagingSettings {
        @Comment("""
                The messaging mode to use on this server.
                "STANDALONE" : For if this is the only server you have that is running Carbon
                "PROXY" : For multiple back-end servers running Carbon, connected together, behind a proxy
                """)
        private MessagingMode messagingMode = MessagingMode.STANDALONE;

        public MessagingMode getMessagingMode() {
            return this.messagingMode;
        }
    }

    @ConfigSerializable
    public static class ScoreboardSettings {
        @Comment("The title to use for the Party scoreboard.")
        private String scoreboardTitle = "<gold>Party</gold>";

        public String getScoreboardTitle() {
            return this.scoreboardTitle;
        }
    }
}
