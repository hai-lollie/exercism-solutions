<div align="center">
  <img src="./assets/transformation.gif" width="500" alt="Sailor Moon transformation" />
</div>

# exercism-solutions ✨

A collaborative [Exercism.io](https://exercism.org) solution archive where **Lollie** and
**Kuro-chan** (Claude Code 🖤💕) tackle programming challenges together — one failing test
at a time.

## What this is 🌸

This repo backs up solutions to Exercism exercises — primarily in Clojure. It also doubles as
Lollie's experiment in **pair programming with Claude Code**: what does genuine AI collaboration
actually look like when the human stays firmly in the architect's chair and the AI does the typing?

Spoiler: it's pretty fun. 🎀

## Who does what 💫

### Lollie — Senior Engineer / Architect 👑

- Selects which exercises to tackle and sets the acceptance criteria
- Challenges proposed designs before accepting them (and yes, she will push back)
- Makes the final call on architecture, naming, and structure
- Reviews and approves every solution — **the code is her responsibility**
- Brings the Clojure expertise and functional design sensibility to each session

### Kuro-chan (Claude Code) — Enthusiastic Pair Programmer 🖤

- Proposes solution designs and walks through the reasoning step by step
- Drives the TDD inner loop: RED → GREEN → REFACTOR
- Runs the nREPL, kondo, and test suite between each baby step
- Surfaces trade-offs and alternative approaches for Lollie to evaluate
- Asks permission before creating or editing any file (manners matter! 🌸)

## How we approach exercises 🧪

### Spec-first, data-driven

Before writing any code we read the problem description and identify:

- The shape of input and output — **data, not objects**
- Edge cases and invariants the spec calls out explicitly
- Which Clojure idiom fits: pure function, `reduce`, `loop/recur`, transducer, etc.

The data shape drives the design. We resist reaching for abstraction until a pattern repeats
at least three times.

### TDD with a live nREPL ⚡

We follow a strict **RED → GREEN → REFACTOR** cycle backed by an open nREPL session:

1. **RED** — load the stub namespace, run one target test, confirm it fails with the expected message
2. **GREEN** — write the minimal implementation in the REPL, then persist it to disk immediately
3. **REFACTOR** — clean up with kondo, remove dead code, sharpen names
4. Verify the *next* test still fails (no accidental over-implementation), then repeat

We avoid JVM restarts by using `(load-file "src/…")` and targeted
`(clojure.test/test-vars [#'ns/var])` calls rather than re-running the full suite from bash
on every iteration.

### Quality gate before every commit ✅

```bash
clj-kondo --lint src --lint test   # 0 errors, 0 warnings
lein test :all                     # 0 failures, 0 errors
```

Nothing merges until both are clean. No exceptions, no shortcuts. 💪

## Clojure style conventions 📐

| Topic | Convention |
|---|---|
| Line length | 120 characters |
| Private definitions | `^:private` metadata (`def ^:private …`) |
| Collection emptiness | `(seq x)` / `(empty? x)`, never `(= x [])` |
| Pre-filter before transform | `filter`/`remove` before `reduce`/`map` |
| `case` with nil | `(case (:error r) :validation … nil …)` handles the success path cleanly |
| No shadowed core names | rename params away from `name`, `get`, `list`, etc. |
| Repeated structures | extract to `^:private def` at namespace top, not repeated in `let` |

## Repository layout 📁

```
clojure/
  {exercise-name}/
    1/
      src/{exercise_name}.clj        ← solution (the only file we edit)
      test/{exercise_name}_test.clj  ← Exercism-provided, never modified
      project.clj                    ← Leiningen (Clojure 1.12.0)
      deps.edn                       ← Clojure CLI with :test alias
      README.md                      ← problem description from Exercism
```

Each exercise is a fully self-contained project with no shared root build.

---

*Not a "Claude did my homework" archive — every solution reflects Lollie's design decisions.*
*Kuro-chan just keeps the tests green and the vibes immaculate.* ✨🖤
