package bobtranslate;

import jcli.annotations.CliCommand;
import jcli.annotations.CliOption;
import jcli.annotations.CliPositional;
import jcli.annotations.CliUnderscoreIsDash;

import java.nio.file.Path;

/**
 * The {@code CliTranslate} class.
 */
@CliCommand
public class CliTranslate {

    /**
     * The {@code path} field.
     */
    @CliOption(name = 'p', longName = "path", isMandatory = true, description = "Path to file or directory")
    public Path path;

    /**
     * The {@code logLevel} field.
     */
    @CliOption(name = 'l', longName = "log-level", description = "Log level to use for reporting", defaultValue = "2")
    public int logLevel;

    /**
     * The {@code command} field.
     */
    @CliPositional
    public TranslateCommand command;

    /**
     * The {@code TranslateCommand} enum.
     */
    @CliUnderscoreIsDash
    public enum TranslateCommand {
        /**
         * The {@code check_keys} constant.
         */
        check_keys, /**
     * The {@code check_values} constant.
     */
    check_values, /**
     * The {@code translate} constant.
     */
    translate
    }


    /**
     * Creates a new instance.
     */
    public CliTranslate() {
    }
}
