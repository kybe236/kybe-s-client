package de.kybe.baseCommands;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Command {

	private final String name;
	private final String description;
	private final String[] aliases;

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

	@SuppressWarnings("unused")
	public String[] getAliases() {
		return aliases;
	}

	public List<String> getAllNamesAndAliases() {
		return Stream.concat(Stream.of(name), Arrays.stream(aliases))
				.collect(Collectors.toList());
	}

	public abstract void execute(String name, String[] args);
}
