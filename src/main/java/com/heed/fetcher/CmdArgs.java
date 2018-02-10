package com.heed.fetcher;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.ArrayList;
import java.util.List;

public class CmdArgs {

    @Option(name="--yahoo", usage="Yahoo's domain")
    private String yahooDomain;

    @Argument
    private List<String> arguments = new ArrayList<>();

    @Option(name="--dummy")
    private Boolean dummy;

    public static CmdArgs create(String[] args) {
        CmdArgs cmdArgs = new CmdArgs();
        CmdLineParser parser = new CmdLineParser(cmdArgs);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println("Weather Fetcher usage:");
            parser.printUsage(System.out);
            System.exit(1);
        }
        return cmdArgs;
    }



    public String yahooDomain() {
        return yahooDomain;
    }

    public String place() {
        return String.join(", ", arguments);
    }

    public boolean dummy() {
        return dummy == null ? false : dummy;
    }
}
