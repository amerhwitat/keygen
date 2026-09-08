# Chimera II Linux Runtime Installation Planning

## Purpose

Chimera II does not vendor third-party Linux binaries. Instead, `LinuxRuntimeCatalog` describes the host software stack and `LinuxRuntimeManager` produces explicit package-manager and systemd command plans. This keeps updates, distribution signatures, licensing and native architecture in the host distribution.

## Supported package families

| Distribution | Package manager | Package command |
|---|---|---|
| Fedora | dnf | `dnf install -y ...` |
| Aurora | dnf | `dnf install -y ...` |
| Ubuntu | apt | `apt-get install -y ...` |
| Debian | apt | `apt-get install -y ...` |
| Kali | apt | `apt-get install -y ...` |

The Java layer returns commands as `List<String>` values. It does not concatenate shell strings, invoke a shell, or silently request root privileges.

## Runtime stack

The catalog currently tracks Python 3.14.7, OpenJDK 26.0.2.1, Firefox and Chrome as `latest-stable`, Bash as `latest-distro-stable`, Zsh 5.9.2, PowerShell 7.6.2, .NET 10.0.400, NGINX 1.30.4-stable, BIND 9.20.27 and Postfix 3.11.7. Browser versions deliberately remain refreshable rather than hard-coded when upstream release cadence is faster than the Java release cycle.

## Servers

The standard service identifiers are `nginx`, `named`/BIND and `postfix`. Service plans use systemd actions such as `start`, `stop`, `restart`, `enable` and `status`. Chimera II does not enable or start privileged services automatically.

## Kali

Kali integration uses official metapackage names such as `kali-tools-top10` and category metapackages rather than attempting to copy the entire Kali repository into the Java project. The catalog also identifies common authorized security-lab tools including Nmap, Burp Suite Community, Metasploit Framework, Wireshark, Aircrack-ng, Hydra, John, NetExec, Responder and SQLmap.

These tools are exposed as catalog/install-plan data only. Their use must comply with applicable authorization and security policy.

## Security requirements

1. Review every generated command before execution.
2. Use the distribution package manager and its signed repositories.
3. Run privileged installation through the host administrator's normal elevation mechanism.
4. Prefer HTTPS for Chimera node federation; plain HTTP is limited to explicit localhost/lab endpoints.
5. Never store private node keys in the trust registry.
6. Keep the trust registry and federation endpoints protected by host permissions and network policy.

## Build/test

The Java project remains buildable with JDK 25. Runtime catalog entries may describe a newer host JDK; this does not change the Java source/ABI baseline.

```bash
mvn test
mvn package
```
