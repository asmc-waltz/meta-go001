SUMMARY = "meta-go001: lvgl mod"
DESCRIPTION = "meta-go001 This Bitbake file is used for a custom lvgl configuration"

FILESEXTRAPATHS:append := "${THISDIR}/files:"
SRC_URI += "file://lv_conf.h"


# Disable Kconfig
EXTRA_OECMAKE += "-DLV_BUILD_CONF_PATH=${WORKDIR}/lv_conf.h"
EXTRA_OECMAKE += "-DLV_BUILD_USE_KCONFIG=OFF"
EXTRA_OECMAKE += "-DLV_BUILD_SET_CONFIG_OPTS=OFF"
EXTRA_OECMAKE += "-DLV_BUILD_LVGL_H_SIMPLE_INCLUDE=ON"

inherit cmake

python do_display_banner() {
    bb.plain("******************************************")
    bb.plain("* meta-go001: --- lvgl configuration --- *")
    bb.plain("******************************************")
}

addtask display_banner before do_build
