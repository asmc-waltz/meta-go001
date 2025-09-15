SUMMARY = "meta-go001: go001 system manager"
DESCRIPTION = "meta-go001 This Bitbake file is used for the go001 system manager"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit cmake pkgconfig
DEPENDS = "dbus networkmanager glib-2.0 alsa-lib"

python do_display_banner() {
    bb.plain("********************************************");
    bb.plain("* meta-go001: --- Go001 System Manager --- *");
    bb.plain("********************************************");
}

SYSTEM_MANAGER_LOCAL_PATH = "${THISDIR}/../../../sources/sys-mgr"
# This task has been completed by copying everything from the submodule data.
do_unpack() {
    cp -r ${SYSTEM_MANAGER_LOCAL_PATH}/* ${S}/
}

do_configure() {
    cmake ${S}/ \
      -DCMAKE_SYSROOT=${STAGING_DIR_HOST} \
      -DCMAKE_LIBRARY_PATH=${STAGING_DIR_HOST}/usr/lib
}

do_compile() {
    cmake --build . --target sys-mgr
    cmake --build . --target sys-utils
}

do_install() {
    install -d ${D}${bindir}
    install -m 755 sys-mgr ${D}${bindir}
    install -m 755 sys-utils ${D}${bindir}

    install -d ${D}${sysconfdir}/dbus-1/system.d
    install -m 0644 ${S}/conf/com.SystemManager.Service.conf ${D}${sysconfdir}/dbus-1/system.d/
}

addtask display_banner before do_build
