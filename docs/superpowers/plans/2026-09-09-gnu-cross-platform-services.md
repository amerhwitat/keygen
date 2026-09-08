# GNU Cross-Platform Services Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add a declarative GNU/open-source utility and service catalog for Linux/Windows interoperability and license the repository's original work under GPL-3.0-or-later.

**Architecture:** Add one compatibility catalog under `org.chimera.compat`, with typed records for tools, services and Windows providers. Keep execution delegated to host package managers and native runtimes. Add repository-level license and a third-party licensing manifest without copying external binaries.

**Tech Stack:** Java 25, Maven, JUnit 5, GitHub Actions.

**Spec:** `docs/superpowers/specs/2026-09-09-gnu-cross-platform-services-design.md`

## Global Constraints
- Preserve the existing ABI and native source-of-record boundaries.
- Do not modify `amerhwitat/test` or `amerhwitat/ChimeraIIOS`.
- Do not vendor third-party binaries or proprietary source.
- Linux package/service actions remain declarative until an explicit runtime manager executes them.
- Original repository source is GPL-3.0-or-later; third-party components retain their own licenses.
- Use TDD: tests must precede production implementation.

---

### Task 1: Add failing GNU catalog tests

**Files:**
- Create: `src/test/java/org/chimera/compat/GnuPlatformCatalogTest.java`

**Interfaces:**
- Consumes: future `GnuPlatformCatalog.tools()`, `services()`, `windowsProviders()`.
- Produces: executable coverage for representative GNU utilities, network services, and Windows provider mappings.

- [ ] **Step 1: Write the failing test**

```java
@Test
void catalogsCoreGnuUtilitiesAndServices() {
    assertTrue(GnuPlatformCatalog.tools().stream().anyMatch(t -> t.id().equals("coreutils")));
    assertTrue(GnuPlatformCatalog.tools().stream().anyMatch(t -> t.id().equals("findutils")));
    assertTrue(GnuPlatformCatalog.services().stream().anyMatch(s -> s.id().equals("inetutils-syslogd")));
    assertTrue(GnuPlatformCatalog.services().stream().anyMatch(s -> s.id().equals("inetutils-ftpd")));
}

@Test
void mapsGnuCapabilitiesToLinuxAndWindowsProviders() {
    assertTrue(GnuPlatformCatalog.windowsProviders().stream().anyMatch(p -> p.id().equals("msys2")));
    assertTrue(GnuPlatformCatalog.windowsProviders().stream().anyMatch(p -> p.id().equals("cygwin")));
    assertTrue(GnuPlatformCatalog.windowsProviders().stream().anyMatch(p -> p.id().equals("wsl2")));
}
```

- [ ] **Step 2: Run test to verify it fails**

Run: `mvn -q -Dtest=GnuPlatformCatalogTest test`
Expected: FAIL because `GnuPlatformCatalog` does not yet exist.

- [ ] **Step 3: Commit**

```bash
git add src/test/java/org/chimera/compat/GnuPlatformCatalogTest.java
git commit -m "test: define GNU cross-platform service catalog"
```

### Task 2: Implement GNU catalog

**Files:**
- Create: `src/main/java/org/chimera/compat/GnuPlatformCatalog.java`

**Interfaces:**
- Produces immutable lists of typed `Tool`, `Service`, and `WindowsProvider` records.
- Tool records expose id, package name, role, Linux support, Windows support.
- Service records expose id, package name, protocol/role, Linux support, Windows provider mapping.
- Provider records expose id, host model, execution model, and notes.

- [ ] **Step 1: Implement minimal catalog**

Include at minimum: coreutils, findutils, grep, sed, gawk, diffutils, patch, tar, gzip, bzip2, xz, cpio, make, binutils, gcc, gdb, m4, autoconf, automake, libtool, bash, nano, screen, emacs, wget, inetutils, and GnuTLS client/server roles.

Include inetutils service roles for syslogd, ftpd, telnetd, rshd/rexecd and talkd where available through the host package set.

Use declarative package names and never construct shell command strings.

- [ ] **Step 2: Run focused tests**

Run: `mvn -q -Dtest=GnuPlatformCatalogTest test`
Expected: PASS.

- [ ] **Step 3: Refactor only while green**

Keep records immutable and return unmodifiable lists.

- [ ] **Step 4: Commit**

```bash
git add src/main/java/org/chimera/compat/GnuPlatformCatalog.java src/test/java/org/chimera/compat/GnuPlatformCatalogTest.java
git commit -m "feat: add GNU utilities and service catalog"
```

### Task 3: Add GPL licensing metadata

**Files:**
- Create: `LICENSE`
- Create: `docs/LICENSING.md`
- Modify: `pom.xml`
- Modify: `README.md`

**Interfaces:**
- Repository source license: GPL-3.0-or-later.
- Documentation licensing is identified separately where a third-party or external license applies.
- Maven POM contains SPDX-compatible license metadata.

- [ ] **Step 1: Add license files and metadata**

Use the standard GNU GPLv3 text and identify the project as GPL-3.0-or-later. State that third-party dependencies and native runtimes retain their own licenses.

- [ ] **Step 2: Add POM license declaration**

Declare `GPL-3.0-or-later` in the Maven project metadata.

- [ ] **Step 3: Update README**

Add a licensing section and explain the separation between project-owned source and external components.

- [ ] **Step 4: Run build**

Run: `mvn -q test`
Expected: PASS.

- [ ] **Step 5: Commit**

```bash
git add LICENSE docs/LICENSING.md pom.xml README.md
git commit -m "legal: license Chimera II Java track under GPLv3-or-later"
```

### Task 4: Document GNU interoperability

**Files:**
- Create: `docs/GNU_CROSS_PLATFORM_SERVICES.md`
- Modify: `docs/DOCUMENTATION_INDEX.md`

- [ ] **Step 1: Document catalog coverage**

Describe Linux-native operation, Windows MSYS2/Cygwin/WSL2 compatibility, service-provider boundaries, and security considerations for network daemons.

- [ ] **Step 2: Run full verification**

Run: `mvn test`
Expected: all tests PASS.

- [ ] **Step 3: Commit**

```bash
git add docs/GNU_CROSS_PLATFORM_SERVICES.md docs/DOCUMENTATION_INDEX.md
git commit -m "docs: document GNU cross-platform services"
```
