SUMMARY = "meta-go001: go001 graphical user interface"
DESCRIPTION = "meta-go001 This Bitbake file is used for the go001 user interface application"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit cmake pkgconfig
DEPENDS = "lvgl libdrm dbus"

python do_display_banner() {
    bb.plain("********************************************");
    bb.plain("* meta-go001: --- Go001 UI application --- *");
    bb.plain("********************************************");
}

TERMINAL_UI_LOCAL_PATH = "${THISDIR}/../../../sources/terminal-ui"
# This task has been completed by copying everything from the submodule data.
do_unpack() {
    cp -r ${TERMINAL_UI_LOCAL_PATH}/* ${S}/
}

do_configure() {
    cmake ${S}/ \
      -DCMAKE_SYSROOT=${STAGING_DIR_HOST} \
      -DLVGL_INCLUDE_DIR=${STAGING_DIR_HOST}/usr/include/lvgl \
      -DCMAKE_LIBRARY_PATH=${STAGING_DIR_HOST}/usr/lib
}

do_compile() {
    cmake --build . --target terminal-ui
    cmake --build . --target ui-utils
}

do_install() {
    install -d ${D}${bindir}
    install -m 755 terminal-ui ${D}${bindir}
    install -m 755 ui-utils ${D}${bindir}

    install -d ${D}${sysconfdir}/dbus-1/system.d
    install -m 0644 ${S}/conf/com.TerminalUI.Service.conf ${D}${sysconfdir}/dbus-1/system.d/
}

addtask display_banner before do_build
