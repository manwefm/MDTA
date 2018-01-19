package fr.mdta.mdta.Tools;

import java.util.HashMap;

/**
 * Created by manwefm on 19/01/18.
 */

public class DangerousMethodPatternMap {

    private static HashMap<String, DangerousMethodCall> mapDangerousMethodPattern = null;

    private static DangerousMethodPatternMap INSTANCE = null;

    public static HashMap<String, DangerousMethodCall> getMapDangerousMethodPattern() {

        if ( INSTANCE == null ) {
            INSTANCE = new DangerousMethodPatternMap();
        }
        return mapDangerousMethodPattern;
    }

    private DangerousMethodPatternMap() {

        mapDangerousMethodPattern = new HashMap<>();

        mapDangerousMethodPattern.put(
                "Ljava/lang/Runtime.+getRuntime()Ljava/lang/Runtime".toLowerCase(), DangerousMethodCall.SHELL
        );
        mapDangerousMethodPattern.put(
                "Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class".toLowerCase(), DangerousMethodCall.REFLECTION
        );
        mapDangerousMethodPattern.put(
                "Ljava/lang/Class;->forName(Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class".toLowerCase(), DangerousMethodCall.REFLECTION
        );
        mapDangerousMethodPattern.put(
                "Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method".toLowerCase(), DangerousMethodCall.REFLECTION
        );
        mapDangerousMethodPattern.put(
                "Ljava/lang/Class;->getMethods()[Ljava/lang/reflect/Method".toLowerCase(), DangerousMethodCall.REFLECTION
        );
        mapDangerousMethodPattern.put(
                "Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V".toLowerCase(), DangerousMethodCall.LOAD_CPP_LIBRARY
        );
        mapDangerousMethodPattern.put(
                "Ljava/lang/Class;->getClassLoader()Ljava/lang/ClassLoader", DangerousMethodCall.REFLECTION
        );
        mapDangerousMethodPattern.put(
                "shell".toLowerCase(), DangerousMethodCall.SHELL
        );
        mapDangerousMethodPattern.put(
                "superuser".toLowerCase(), DangerousMethodCall.SHELL
        );
    }
}
