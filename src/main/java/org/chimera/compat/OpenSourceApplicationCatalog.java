package org.chimera.compat;

import java.util.List;

/** Curated integration targets for mature open-source Windows/Linux applications. */
public final class OpenSourceApplicationCatalog {
    public record Application(String id, String project, String category, String role,
                              boolean linux, boolean windows, String licenseFamily,
                              List<String> delivery) {}

    private OpenSourceApplicationCatalog() {}

    public static List<Application> applications() {
        return List.of(
            app("libreoffice", "LibreOffice", "office", "documents spreadsheets presentations", true, true, "MPL-2.0/GPL-family", "native-packages", "official-installer"),
            app("gimp", "GIMP", "graphics", "raster image editing", true, true, "GPL-3.0-or-later", "native-packages", "official-installer"),
            app("inkscape", "Inkscape", "graphics", "vector graphics and SVG", true, true, "GPL-2.0-or-later", "native-packages", "official-installer"),
            app("blender", "Blender", "graphics", "3D modeling animation rendering", true, true, "GPL-3.0-or-later", "native-packages", "official-installer"),
            app("krita", "Krita", "graphics", "digital painting illustration", true, true, "GPL-3.0-or-later", "native-packages", "official-installer"),
            app("vlc", "VLC", "multimedia", "audio video playback streaming", true, true, "GPL-2.0-or-later", "native-packages", "official-installer"),
            app("audacity", "Audacity", "multimedia", "audio recording editing", true, true, "GPL-3.0-or-later", "native-packages", "official-installer"),
            app("obs-studio", "OBS Studio", "multimedia", "recording capture streaming", true, true, "GPL-2.0-or-later", "native-packages", "official-installer"),
            app("thunderbird", "Mozilla Thunderbird", "communication", "mail calendar contacts", true, true, "MPL-2.0", "native-packages", "official-installer"),
            app("keepassxc", "KeePassXC", "security", "password and credential management", true, true, "GPL-2.0-or-later", "native-packages", "official-installer"),
            app("7zip", "7-Zip", "archive", "archive creation extraction", true, true, "LGPL-2.1+/BSD-3-Clause", "native-packages", "official-installer"),
            app("kdenlive", "Kdenlive", "multimedia", "non-linear video editing", true, true, "GPL-2.0-or-later", "native-packages", "official-installer"),
            app("calibre", "Calibre", "productivity", "ebook library and conversion", true, true, "GPL-3.0-or-later", "native-packages", "official-installer"),
            app("okular", "Okular", "documents", "PDF and multi-format document viewing", true, true, "GPL-2.0-or-later", "native-packages", "KDE/Windows"),
            app("dolphin", "KDE Dolphin", "desktop", "file management", true, true, "GPL-2.0-or-later", "native-packages", "KDE/Windows"),
            app("kate", "KDE Kate", "development", "advanced text and code editing", true, true, "LGPL-2.0-or-later", "native-packages", "KDE/Windows"),
            app("konsole", "KDE Konsole", "desktop", "terminal emulator", true, true, "GPL-2.0-or-later", "native-packages", "KDE/Windows"),
            app("kde-connect", "KDE Connect", "desktop", "device integration clipboard notifications file transfer", true, true, "GPL-2.0-or-later", "native-packages", "KDE/Windows"),
            app("kde-discover", "KDE Discover", "desktop", "application discovery installation updates", true, false, "GPL-2.0-or-later", "native-packages", "Linux-package-backends"),
            app("kde-ark", "KDE Ark", "archive", "archive browsing extraction creation", true, true, "GPL-2.0-or-later", "native-packages", "KDE/Windows"),
            app("kde-spectacle", "KDE Spectacle", "desktop", "screenshots screen capture", true, true, "GPL-2.0-or-later", "native-packages", "KDE/Windows"),
            app("kde-smb4k", "Smb4K", "network", "SMB/CIFS network browsing and mounts", true, false, "GPL-2.0-or-later", "native-packages", "Linux-package"),
            app("kde-krdc", "KDE KRDC", "network", "RDP VNC remote desktop", true, true, "GPL-2.0-or-later", "native-packages", "KDE/Windows"),
            app("kde-kwallet", "KWalletManager", "security", "desktop credential wallet", true, true, "GPL-2.0-or-later", "native-packages", "KDE/Windows"),
            app("gnome-files", "GNOME Files", "desktop", "file management", true, false, "GPL-family", "native-packages"),
            app("gnome-system-monitor", "GNOME System Monitor", "desktop", "resource and process monitoring", true, false, "GPL-family", "native-packages"),
            app("gnome-connections", "GNOME Connections", "network", "RDP and VNC remote desktop", true, false, "GPL-3.0-or-later", "native-packages", "Flatpak"),
            app("gnome-disks", "GNOME Disks", "storage", "disk partition storage and image management", true, false, "GPL-family", "native-packages"),
            app("gnome-software", "GNOME Software", "desktop", "application discovery installation updates", true, false, "GPL-family", "native-packages", "Flatpak"),
            app("gnome-logs", "GNOME Logs", "desktop", "system journal log viewing", true, false, "GPL-family", "native-packages"),
            app("git", "Git", "development", "distributed source control", true, true, "GPL-2.0-only", "native-packages", "official-installer"),
            app("openssh", "OpenSSH", "network", "secure remote shell copy tunneling", true, true, "BSD-family", "native-packages", "Windows-port"),
            app("wireshark", "Wireshark", "network", "packet capture protocol analysis", true, true, "GPL-2.0-or-later", "native-packages", "official-installer"),
            app("rclone", "rclone", "storage", "cloud and local filesystem synchronization", true, true, "MIT", "native-packages", "official-binary"),
            app("syncthing", "Syncthing", "storage", "peer-to-peer file synchronization", true, true, "MPL-2.0", "native-packages", "official-installer"),
            app("restic", "restic", "backup", "encrypted deduplicated backup", true, true, "BSD-2-Clause", "native-packages", "official-binary"),
            app("filezilla", "FileZilla", "network", "FTP FTPS SFTP transfer", true, true, "GPL-2.0-or-later", "native-packages", "official-installer"),
            app("putty", "PuTTY", "network", "SSH and serial terminal", true, true, "MIT-like", "native-packages", "official-installer"),
            app("curl", "curl", "network", "HTTP HTTPS and transfer tooling", true, true, "curl-license", "native-packages", "official-binary"),
            app("wget", "GNU Wget", "network", "HTTP HTTPS FTP retrieval", true, true, "GPL-3.0-or-later", "native-packages", "MSYS2/Cygwin"),
            app("wireguard", "WireGuard", "network", "modern encrypted VPN", true, true, "GPL-2.0", "native-packages", "official-client"),
            app("openvpn", "OpenVPN", "network", "TLS-based VPN", true, true, "GPL-2.0", "native-packages", "official-client"),
            app("qemu", "QEMU", "virtualization", "system and machine virtualization", true, true, "GPL-2.0-or-later", "native-packages", "official-installer"),
            app("virtualbox", "VirtualBox", "virtualization", "desktop virtualization", true, true, "GPL-3.0", "native-packages", "official-installer"),
            app("docker-engine", "Docker Engine", "containers", "container runtime image management", true, true, "Apache-2.0", "native-packages", "Windows-Server"),
            app("podman", "Podman", "containers", "daemonless rootless containers", true, true, "Apache-2.0", "native-packages", "WSL2"),
            app("virt-manager", "Virtual Machine Manager", "virtualization", "libvirt VM administration", true, false, "GPL-3.0-or-later", "native-packages"),
            app("libvirt", "libvirt", "virtualization", "virtualization management API daemon", true, false, "LGPL-2.1-or-later", "native-packages"),
            app("virt-viewer", "Virtual Machine Viewer", "virtualization", "SPICE VNC remote VM display", true, true, "GPL-family", "native-packages", "official-installer"),
            app("gnome-boxes", "GNOME Boxes", "virtualization", "desktop virtual machine management", true, false, "GPL-family", "native-packages", "Flatpak"),
            app("nextcloud-client", "Nextcloud Desktop Client", "storage", "file synchronization and collaboration", true, true, "GPL-2.0-or-later", "native-packages", "official-installer"),
            app("joplin", "Joplin", "productivity", "notes and synchronization", true, true, "AGPL-3.0-or-later", "native-packages", "official-installer"),
            app("element", "Element", "communication", "Matrix messaging and collaboration", true, true, "Apache-2.0", "native-packages", "official-installer"),
            app("gnumeric", "Gnumeric", "office", "spreadsheet calculation", true, true, "GPL-3.0-or-later", "native-packages", "official-installer"),
            app("scribus", "Scribus", "publishing", "desktop publishing and PDF production", true, true, "GPL-2.0-or-later", "native-packages", "official-installer"),
            app("shotcut", "Shotcut", "multimedia", "cross-platform video editing", true, true, "GPL-3.0-or-later", "native-packages", "official-installer")
        );
    }

    private static Application app(String id, String project, String category, String role,
                                   boolean linux, boolean windows, String license, String... delivery) {
        return new Application(id, project, category, role, linux, windows, license, List.of(delivery));
    }
}
