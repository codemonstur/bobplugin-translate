# Bob Translate

A [Bob the Build Tool](https://github.com/codemonstur/bobthebuildtool) plugin for managing internationalisation (i18n) resource files.

[![GitHub Release](https://img.shields.io/github/release/codemonstur/bobplugin-translate.svg)](https://github.com/codemonstur/bobplugin-translate/releases)
[![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://opensource.org/licenses/mit-license.php)

## Features

- **Check missing keys** (`check_keys`): compares all language files in a directory and reports keys that are present in some files but missing in others.
- **Check missing values** (`check_values`): reports keys whose translated value is empty.
- (Planned) Automatically complete translations by correlating identical source phrases.
- (Planned) Automatically translate missing text using online translation services.

## Requirements

- Java 17 or newer
- [Bob the Build Tool](https://github.com/codemonstur/bobthebuildtool) 7 or newer

## Installation

Add this line to the top of your `bobfile`:

```
plugin codemonstur/bobplugin-translate:3
```

The `translate` command is then available in all your bob tasks.

Or build the jar manually:

```sh
mvn -DskipTests clean package
```

## Usage

Run the plugin from within a Bob project:

```sh
bob translate -p <path> <command>
```

### Options

| Option | Long form | Description | Default |
|---|---|---|---|
| `-p` | `--path` | Path to a language file or directory (mandatory) | — |
| `-l` | `--log-level` | Log level for reporting (`1`=info, `2`=warning, `4`=error) | `2` |

### Commands

| Command | Description |
|---|---|
| `check_keys` | Report keys missing from individual language files |
| `check_values` | Report keys with empty translated values |
| `translate` | *(not implemented yet)* |

### Examples

Check for missing keys in a language directory:

```sh
bob translate -p src/test/lang check_keys
```

Check for empty translations:

```sh
bob translate -p src/test/lang check_values
```

## Building

```sh
make build       # compile and package (skips tests)
make test        # run tests
make coverage    # run tests with JaCoCo coverage report
make validate    # check dependency versions, cves, licenses, and code quality
```

## Project structure

```
src/main/java/bobtranslate/
  BobPlugin.java          # plugin entry point
  CliTranslate.java       # command line interface
  actions/                # check implementations
  util/                   # helpers (Functions, MultiPartBodyPublisher, ...)
```

## License

Released under the [MIT License](https://opensource.org/licenses/MIT).
