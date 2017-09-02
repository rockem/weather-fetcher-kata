package com.heed.fetcher;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.ArrayList;
import java.util.List;

public class CmdArgs {

    @Option(name="--yahoo", required = true, usage="Yahoo's domain")
    private String yahooDomain;

    @Argument
    private List<String> arguments = new ArrayList<>();


    public static CmdArgs create(String[] args) {
        CmdArgs cmdArgs = new CmdArgs();
        CmdLineParser parser = new CmdLineParser(cmdArgs);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println("Weather Fetcher usage:");
            parser.printUsage(System.err);
            System.exit(1);
        }
        return cmdArgs;
    }

    public String getYahooDomain() {
        return yahooDomain;
    }

    public String[] getPlace() {
        return arguments.toArray(new String[0]);
    }
}