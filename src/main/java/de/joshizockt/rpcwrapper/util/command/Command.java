package de.joshizockt.rpcwrapper.util.command;

public abstract class Command {

    private final String label;
    private final String description;
    private final String[] aliases;

    public Command(String label, String description, String... aliases) {
        this.label = label;
        this.description = description;
        this.aliases = aliases;
    }

    public abstract void execute(String label, String[] args);

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }

}
