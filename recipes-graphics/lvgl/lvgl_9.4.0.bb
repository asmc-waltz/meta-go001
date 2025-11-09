# SPDX-FileCopyrightText: Huawei Inc.
#
# SPDX-License-Identifier: MIT

HOMEPAGE = "https://lvgl.io/"
DESCRIPTION = "LVGL is an OSS graphics library to create embedded GUI"
SUMMARY = "Light and Versatile Graphics Library"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENCE.txt;md5=4570b6241b4fced1d1d18eb691a0e083"

SRC_URI = "\
	git://github.com/lvgl/lvgl;protocol=https;branch=release/v9.4 \
	"
SRCREV = "c016f72d4c125098287be5e83c0f1abed4706ee5"

inherit cmake

EXTRA_OECMAKE = "-DLIB_INSTALL_DIR=${baselib} -DBUILD_SHARED_LIBS=ON"
EXTRA_OECMAKE += "-DCONFIG_LV_USE_PRIVATE_API=ON"

EXTRA_OECMAKE += "-DCONFIG_LV_BUILD_USE_KCONFIG=OFF"
EXTRA_OECMAKE += "-DCONFIG_LV_BUILD_SET_CONFIG_OPTS=OFF"
EXTRA_OECMAKE += "-DLV_BUILD_CONF_PATH=${WORKDIR}/lv_conf.h"
LV_BUILD_USE_KCONFIG = "OFF"
LV_BUILD_SET_CONFIG_OPTS = "OFF"
S = "${WORKDIR}/git"

require lv-conf.inc

# do_install:append() {
#     install -m 0644 "${S}/lv_conf.h" "${D}${includedir}/${BPN}/lv_conf.h"
# }
