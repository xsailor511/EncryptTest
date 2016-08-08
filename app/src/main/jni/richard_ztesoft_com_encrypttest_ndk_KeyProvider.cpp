//
// Created by richard on 16/7/21.
//
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
//导入日志头文件
#include <android/log.h>
#include "richard_ztesoft_com_encrypttest_ndk_KeyProvider.h"

	const char keyValue[] = {
		21, 25, 21, -45, 25, 98, -55, -45, 10, 35, -45, 35,
		26, -5, 25, -65, -78, -99, 85, 45, -5, 10, -0, 11,
		-35, -48, -98, 65, -32, 14, -67, 25, 36, -56, -45, -5,
		12, 15, 35, -15, 25, -14, 62, -25, 33, -45, 55, 12, -8,
	};

	const char iv[] =  {	//16 bit
			-33, 32, -25, 25, 35, -27, 55, -12, -15,32,
			23, 45, -26, 32, 5,16
	};

JNIEXPORT jbyteArray JNICALL Java_richard_ztesoft_com_encrypttest_ndk_KeyProvider_getKeyValue
  (JNIEnv *env, jclass){
		jbyteArray kvArray = env->NewByteArray(sizeof(keyValue));
		jbyte *bytes = env->GetByteArrayElements(kvArray,0);

		int i;
		for (i = 0; i < sizeof(keyValue);i++){
			bytes[i] = (jbyte)keyValue[i];
		}

		env->SetByteArrayRegion(kvArray, 0, sizeof(keyValue),bytes);
		env->ReleaseByteArrayElements(kvArray,bytes,0);

		return kvArray;
  }

/*
 * Class:     richard_ztesoft_com_encrypttest_ndk_KeyProvider
 * Method:    getIV
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_richard_ztesoft_com_encrypttest_ndk_KeyProvider_getIV
  (JNIEnv *env, jclass){
    jbyteArray ivArray = env->NewByteArray( sizeof(iv));
	jbyte *bytes = env->GetByteArrayElements(ivArray, 0);

	int i;
	for (i = 0; i < sizeof(iv); i++){
		bytes[i] = (jbyte)iv[i];
	}

	env->SetByteArrayRegion(ivArray, 0, sizeof(iv), bytes);
	env->ReleaseByteArrayElements(ivArray,bytes,0);

	return ivArray;

  }
