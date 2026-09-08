# Chimera II Desktop API Reference

## Base path

The Jakarta EE application is rooted at:

```text
/api
```

Desktop resources are therefore exposed below `/api/desktop`.

## Endpoints

### `GET /api/desktop/profiles`

Returns the known desktop/session profiles and their current runtime availability.

The inventory includes Aurora, Fedora GNOME, Ubuntu GNOME, Debian GNOME, KDE Plasma, Xfce, Cinnamon, MATE, LXQt, GNOME Flashback, Safe/Minimal and Headless/Server.

Availability is host-specific and may change when the session environment or installed executables change.

### `GET /api/desktop/current`

Returns the current/default desktop selection state represented by the Java runtime.

The result is descriptive. It does not imply that the corresponding native compositor is currently running.

### `GET /api/desktop/select/{id}`

Validates a profile identifier and returns a structured launch plan when the profile is available.

The endpoint **does not execute** the selected process.

An unavailable or unknown profile produces a structured selection failure.

## Launch-plan model

A launch plan consists conceptually of:

```text
executable: string
arguments: ordered list<string>
environment: map<string,string>
```

The executable and arguments are kept separate so a native adapter can invoke them without shell interpolation.

## Example conceptual response

```json
{
  "profile": "kde-plasma",
  "executable": "startplasma-wayland",
  "arguments": [],
  "environment": {}
}
```

The exact JSON representation is controlled by the Jakarta resource implementation and should be treated as an implementation contract subject to compatibility tests.

## Security model

The API is intentionally split into two operations:

1. **selection/plan generation** — exposed through REST;
2. **native process execution** — performed only by an explicitly configured process/native adapter.

The REST layer therefore does not become a general-purpose shell endpoint.

## Availability policy

A normal desktop profile must satisfy the runtime's session/capability requirements and have at least one installed launcher candidate. If no preferred desktop is available, the session manager falls back to Safe/Minimal and then Headless/Server.

## Client integration

A future GUI, TUI, web UI, native Aurora shell or display-manager integration can consume the same profile inventory and launch-plan model. Clients should display unavailable profiles as disabled rather than attempting to invoke their commands directly.

## Testing

API integration tests should verify:

- profile inventory is deterministic;
- required desktop families are present;
- unavailable profiles cannot produce executable launch plans;
- selection never starts a compositor;
- launch arguments remain structured;
- recovery selection remains deterministic.
