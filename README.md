# AdventOfCode 2024 Hogent/TIAO1 style

## Introduction

I find AoC excellent for exploring new programming languages.  This works really well with java streams.

[about AdventOfCode](https://adventofcode.com/2024/about)

25 days, each day a new puzzle, each puzzle has 2 parts. Profit.

## Get Started

Starting with a solved Day1. More solutions will be in my [solutions](https://github.com/mtdig/AoC-hogent-tiao1-style/tree/solutions) branch.

- Each day has an input.txt file which we save in `src/main/resources` as dayXX.txt, ie day01.txt.
- Write a new class DayXX in `src/main/java/domein`, ie `src/main/java/domein/Day01.java`.
- Write a new Test Class in `src/test/java/domein`, ie `src/test/java/domein/Day01Test.java`.
- Solve puzzles

That's it.

Run with `mvn javafx:run` or from whatever IDE you're using.

## nix/nixos

For nix, there's the flake.nix that gets us started with everything required to run this. Executing `nix develop` in this repo directory prepares everything.

## linux aarch64 with nix

If you're looking for the latest available nix package for a specific java library, here's this:
Add this to your .bashrc or whatever your shell .xxrc. For NixOS, check [this](#more-nixos-related).

```bash
get_maven_versions() {
  local coord="${1:?Usage: get_maven_versions <group:artifact> [version_prefix] [classifier_filter]}"
  local version_prefix="${2:-}"
  local classifier="${3:-}"

  local group="${coord%%:*}"
  local artifact="${coord##*:}"
  local path="${group//.//}/${artifact}"
  local base_url="https://repo1.maven.org/maven2/${path}"

  curl -s "${base_url}/" \
    | grep -oP "href=\"\K${version_prefix}[0-9.][^/\"]*" \
    | while read -r v; do
        if [[ -n "$classifier" ]]; then
          curl -sf "${base_url}/${v}/" | grep -q "$classifier" && echo "$v"
        else
          echo "$v"
        fi
      done
}
```

an example for javafx, in this case, version 21:

```bash
$ gmv org.openjfx:javafx-controls 21 linux-aarch64
21.0.1
21.0.11-ea+2
21.0.11-ea+3
21.0.11-ea+4
21.0.6-ea+2
21.0.7-ea+1
$
```


I use this alias: `alias gmv='get_maven_versions '` (mind the trailing space)

So that's how I got to the javafx version 21.0.11-ea+4 in this pom.xml. There's no 21.0.10 available for us yet.

## More NixOS related

For more details on the nix part, or my entire currently used NixOS config: [go here](https://github.com/mtdig/nixos-cfg-asahi-apple-silicon).

