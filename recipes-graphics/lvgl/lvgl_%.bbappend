SUMMARY = "meta-go001: lvgl mod"
SUMMARY = "meta-go001: LVGL configuration override"
DESCRIPTION = "Custom LVGL build configuration for Cronos project (no Kconfig, static lv_conf.h)"

FILESEXTRAPATHS:append := "${THISDIR}/files:"

SRC_URI += " \
    file://lv_conf.h \
"

PACKAGECONFIG ??= "drm"

PACKAGECONFIG[drm] = ",,libdrm"
PACKAGECONFIG[fbdev] = ",,"
PACKAGECONFIG[thorvg] = ",,"
PACKAGECONFIG[sdl] = ",,virtual/libsdl2 libsdl2-image"

# --- Add libdrm include if drm enabled ---
TARGET_CFLAGS += "${@bb.utils.contains('PACKAGECONFIG', 'drm', '-I${STAGING_INCDIR}/libdrm', '', d)}"

# --- Optional feature flags for reference (not used by CMake) ---
LVGL_CONFIG_USE_DRM = "${@bb.utils.contains('PACKAGECONFIG', 'drm', '1', '0', d)}"
LVGL_CONFIG_USE_FBDEV = "${@bb.utils.contains('PACKAGECONFIG', 'fbdev', '1', '0', d)}"
LVGL_CONFIG_USE_SDL = "${@bb.utils.contains('PACKAGECONFIG', 'sdl', '1', '0', d)}"
LVGL_CONFIG_USE_EVDEV = "${@bb.utils.contains_any('PACKAGECONFIG', 'drm fbdev', '1', '0', d)}"

# --- Force static config and disable Kconfig ---
EXTRA_OECMAKE += " \
    -DLV_BUILD_CONF_PATH=${WORKDIR}/lv_conf.h \
    -DLV_BUILD_USE_KCONFIG=OFF \
    -DLV_BUILD_SET_CONFIG_OPTS=OFF \
    -DLV_BUILD_LVGL_H_SIMPLE_INCLUDE=ON \
    -DCONFIG_LV_USE_PRIVATE_API=ON \
"

S = "${WORKDIR}/git"

inherit cmake

python do_display_banner() {
    bb.plain("******************************************")
    bb.plain("* meta-go001: --- LVGL configuration --- *")
    bb.plain("******************************************")
}

addtask display_banner before do_build
