//
// Created by zhouyizirui on 10/5/16.
//

#include <string.h>
#include <jni.h>
#include <android/log.h>

#if defined(__arm__)
#if defined(__ARM_ARCH_7A__)
#if defined(__ARM_NEON__)
 #if defined(__ARM_PCS_VFP)
   #define ABI "armeabi-v7a/NEON (hard-float)"
 #else
   #define ABI "armeabi-v7a/NEON"
 #endif
#else
 #if defined(__ARM_PCS_VFP)
   #define ABI "armeabi-v7a (hard-float)"
 #else
   #define ABI "armeabi-v7a"
 #endif
#endif
#else
#define ABI "armeabi"
#endif
#elif defined(__i386__)
#define ABI "x86"
#elif defined(__x86_64__)
#define ABI "x86_64"
#elif defined(__mips64)  /* mips64el-* toolchain defines __mips__ too */
#define ABI "mips64"
#elif defined(__mips__)
#define ABI "mips"
#elif defined(__aarch64__)
#define ABI "arm64-v8a"
#else
#define ABI "unknown"
#endif

#define JNIREG_CLASS "joey/com/thermometer/TemperatureConverter"
#define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))
#define ALOGI(...) __android_log_print(ANDROID_LOG_INFO, "Tag", __VA_ARGS__)

static jfloatArray native_convert(JNIEnv* env, jclass clazz, jfloatArray arr, jboolean ctof) {
    ALOGI("Convert the temperature array");
    int len = (*env)->GetArrayLength(env, arr);
    if (len == 0) {
        ALOGI("Length is 0");
        return arr;
    }

    jfloat* carr = (*env)->GetFloatArrayElements(env, arr, NULL);
    jfloatArray result = (*env)->NewFloatArray(env, len);

    float buffer[5];

    int i = 0;

    for (; i < len; i++) {
        if (ctof == JNI_TRUE) {
            buffer[i] = carr[i] * 1.8 + 32.0;
        } else {
            buffer[i] = (carr[i] - 32.0) / 1.8;
        }
    }

    (*env)->ReleaseFloatArrayElements(env, arr, carr, 0);
    (*env)->SetFloatArrayRegion(env, result, 0, len, buffer);

    return result;
}

static jfloat native_convert_single(JNIEnv* en, jclass clazz, jfloat input, jboolean ctof) {
    ALOGI("Convert the single temperature");
    jfloat result = 0.0;
    if (ctof == JNI_TRUE) {
        result = input * 1.8 + 32.0;
    } else {
        result = (input - 32.0) / 1.8;
    }
    return result;
}

static JNINativeMethod method_table[] = {
    {"convert", "([FZ)[F", (void*) native_convert},
    {"convertSingle", "(FZ)F", (void*) native_convert_single},
};

static int registerNativeMethods(JNIEnv* env, const char* className, JNINativeMethod* gMethods, int numMethods) {
    jclass clazz;
    clazz = (*env)->FindClass(env, className);

    if (clazz == NULL) {
        return JNI_FALSE;
    }

    ALOGI("Register native methods with name %s", className);
    if ((*env) -> RegisterNatives(env, clazz, gMethods, numMethods) < 0) {
        return JNI_FALSE;
    }

    return JNI_TRUE;
}

int register_ndk_load(JNIEnv* env) {
    return registerNativeMethods(env, JNIREG_CLASS, method_table, NELEM(method_table));
}

jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    ALOGI("JNI_OnLoad function called");

    JNIEnv* env = NULL;
    jint result = -1;

    if ((*vm) -> GetEnv(vm, (void**) &env, JNI_VERSION_1_6) != JNI_OK) {
        return result;
    }

    register_ndk_load(env);
    return JNI_VERSION_1_6;
}