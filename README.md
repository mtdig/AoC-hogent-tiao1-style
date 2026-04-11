# AdventOfCode 2024 Hogent/TIAO1 style

## Introduction

Streams make java bearable (barely). I find AoC excellent for exploring new programming languages, less boring and tedious than the usual leetcode problems.

[about AdventOfCode](https://adventofcode.com/2024/about)

25 days, each day a new puzzle, each puzzle has 2 parts. Profit.

## Get Started

We start with a solved Day1. More solutions are/will be in my [solutions](https://github.com/mtdig/AoC-hogent-tiao1-style/tree/solutions) branch.

- Each day has an input.txt file which we save in `src/main/resources` as dayXX.txt, ie day01.txt.
- Write a new class DayXX in `src/main/java/domein`, ie `src/main/java/domein/Day01.java`.
- Write a new Test Class in `src/test/java/domein`, ie `src/test/java/domein/Day01Test.java`.
- Solve puzzles

That's it.

Run with `mvn javafx:run` or from whatever IDE you're using.

## nix/nixos

For the fellow nix-peeps, there's the flake.nix that should get you started with everything required to run this. Just execute `nix develop` in this repo directory and off you go.

## linux aarch64 with nix

For the fellow Linux aarch64 peeps: packages are picking up. Gradually.

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

So that's how I got to the javafx version 21.0.11-ea+4 in this pom.xml. There's no 21.0.10 available for us.

## More NixOS related

For more details on the nix part, or my entire currently used NixOS config: [go here](https://github.com/mtdig/nixos-cfg-asahi-apple-silicon).

## Disclaimer

I seem to like Nix and NixOS (still early days, few months in). I like Ansible (for over 10 years). I seem to like functional programming: no surprises, some limitations (WTF java checked exceptions?? - that second question mark was intentional)..
Declarative vs imperative. I need to have a closer look at kotlin and scala. With apache spark and kafka and minecraft, java can't be that bad. We'll see.

