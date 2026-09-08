package org.chimera.compat;

import java.util.EnumSet;
import java.util.Set;

/** Cross-platform filesystem capability inventory; it is not a filesystem driver. */
public final class FileSystemCatalog {
    public enum Type {
        FAT12, FAT16, FAT32, EXFAT, NTFS, REFS, CDFS, UDF, CSVFS,
        EXT2, EXT3, EXT4, XFS, BTRFS, ZFS, F2FS, JFS, JFS2, NILFS2, REISERFS,
        TMPFS, OVERLAYFS, PROCFS, SYSFS, DEVPTS, NFS, NFS4, SMB_CIFS,
        UFS, UFS2, FFS, HFS_PLUS, APFS, HAMMER2, VXFS
    }
    private FileSystemCatalog() {}
    public static Set<Type> windows() { return set(Type.FAT12, Type.FAT16, Type.FAT32, Type.EXFAT, Type.NTFS, Type.REFS, Type.CDFS, Type.UDF, Type.CSVFS, Type.NFS, Type.SMB_CIFS); }
    public static Set<Type> linux() { return set(Type.EXT2, Type.EXT3, Type.EXT4, Type.XFS, Type.BTRFS, Type.ZFS, Type.F2FS, Type.JFS, Type.NILFS2, Type.REISERFS, Type.TMPFS, Type.OVERLAYFS, Type.PROCFS, Type.SYSFS, Type.DEVPTS, Type.NFS, Type.NFS4, Type.SMB_CIFS); }
    public static Set<Type> unix() { return set(Type.UFS, Type.UFS2, Type.FFS, Type.ZFS, Type.JFS2, Type.VXFS, Type.NFS, Type.NFS4, Type.SMB_CIFS); }
    public static Set<Type> apple() { return set(Type.APFS, Type.HFS_PLUS, Type.UDF, Type.NFS, Type.NFS4, Type.SMB_CIFS); }
    private static Set<Type> set(Type... types) { return EnumSet.of(types[0], types); }
}
