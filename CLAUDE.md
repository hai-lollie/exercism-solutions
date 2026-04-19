# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository Structure

Exercism solutions organised by language and exercise:

```
clojure/{exercise-name}/1/
  src/{exercise_name}.clj    ← solution (the only file to edit)
  test/{exercise_name}_test.clj  ← provided by Exercism, do not modify
  project.clj                ← Leiningen project (Clojure 1.12.0)
  deps.edn                   ← Clojure CLI with :test alias (cognitect test-runner)
  README.md                  ← problem description
```

Each exercise is an independent, self-contained project. There is no shared build at the repo root.

## Commands (run from the exercise directory)

```bash
# Navigate to an exercise first
cd clojure/{exercise-name}/1

# Run all tests (Leiningen)
lein test

# Run all tests (Clojure CLI)
clj -X:test

# Run a single test var
lein test :only {test-ns}/{test-var}

# Submit solution to Exercism
exercism submit src/{exercise_name}.clj
```

## Clojure Conventions in This Repo

- Solutions are in `(ns {exercise-name})` — no sub-namespaces needed
- Tests use `clojure.test` with `deftest` + `testing` + `is`; exercism generates one `deftest` per case
- `project.clj` declares only `[org.clojure/clojure "1.12.0"]` as a dependency — no extra libs unless the exercise requires them
- Line limit: 120 characters

## TDD Workflow for New Exercises

1. Start nREPL (`start-nrepl` skill) from the exercise directory
2. Stub the required function(s) so the namespace loads
3. RED → GREEN → REFACTOR one test at a time via `clj-nrepl-eval`
4. After each edit: `(load-file "src/…clj")` then `clj-kondo --lint src --lint test`
5. Full suite (`lein test :all`) must be green before committing
