package de.kybe.baseCommands;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Command {

    public String name;
    public String description;
    public String[] aliases;

    public Command(String name, String description, String... aliases) {
        this.name = name;
        this.description = description;
        this.aliases = aliases;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getAliases() {
        return aliases;
    }

    public List<String> getAllNames() {
        return Stream.concat(Stream.of(name), Arrays.stream(aliases))
                .collect(Collectors.toList());
    }

    public abstract void execute(String name, String[] args);
}
