# bobplugin-translate

A [bob](https://github.com/codemonstur/bobthebuildtool) plugin for validating
internationalized (i18n) resource files: it checks that every language file
defines the same keys, and that no key is left untranslated.

## Features

- `check_keys` — report keys present in one language file but missing from
  another.
- `check_values` — report keys that still hold untranslated values.
- `translate` — *(planned)* machine-translate missing values.

## Requirements

- Java 17+
- bob 7+

## Installation

```
plugin codemonstur/translate:<version>
```

## Usage

```
bob translate <command> [options]
```

| Option | Description |
| --- | --- |
| `-p`, `--path` | **Mandatory.** Directory containing the language files. |
| `-l`, `--log-level` | `1` = info, `2` = warn, `4` = error. |

### Examples

```sh
bob translate check_keys   -p src/main/resources/i18n
bob translate check_values -p src/main/resources/i18n -l 2
```

## Expected project structure

```
src/main/resources/i18n/
├── messages.properties        # base language (usually English)
├── messages_nl.properties
├── messages_de.properties
└── ...
```

All files must share the same base name and use the standard Java `ResourceBundle`
`.properties` convention.

## Building

```sh
make build
```

## License

MIT
