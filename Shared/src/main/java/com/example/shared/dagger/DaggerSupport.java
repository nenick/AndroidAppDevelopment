package com.example.shared.dagger;


import dagger.ObjectGraph;

public class DaggerSupport {
    private static ObjectGraph graph;

    public static void init(Object... modules) {
        graph = ObjectGraph.create(modules);
    }

    public static void inject(Object object) {
        if (graph != null)
            graph.inject(object);
    }
}
