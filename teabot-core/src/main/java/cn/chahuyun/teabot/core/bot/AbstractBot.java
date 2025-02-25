package cn.chahuyun.teabot.core.bot;

/**
 *
 *
 * @author Moyuyanli
 * @date 2025-2-21 14:34
 */
@SuppressWarnings("FieldCanBeLocal")
public abstract class AbstractBot implements Bot {

    private final BotConfiguration configuration;

    private final String id;

    public AbstractBot(BotConfiguration configuration, String id) {
        this.configuration = configuration;
        this.id = id;
    }

    @Override
    public BotConfiguration getConfiguration() {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public Bot getBot() {
        return null;
    }

    @Override
    public boolean isOnline() {
        return false;
    }


}
