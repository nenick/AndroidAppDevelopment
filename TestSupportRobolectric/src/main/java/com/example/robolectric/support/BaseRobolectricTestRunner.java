package com.example.robolectric.support;


import org.androidannotations.api.BackgroundExecutor;
import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.SdkConfig;
import org.robolectric.SdkEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.bytecode.ClassInfo;
import org.robolectric.bytecode.Setup;

import java.util.ArrayList;
import java.util.Properties;

/**
 * Robolectric Support
 * <p/>
 * Robolectric supports org.robolectric.Config.properties but this project setup does not support
 * loading files from resources folder in android studio https://github.com/evant/android-studio-unit-test-plugin/issues/4
 * <p/>
 * So do here:
 * <p/>
 * <li>Set default shadows for all tests</li>*
 * <li>Set default emulated SDK version</li>
 * <li>Set path to the android manifest file</li>
 * <p/>
 * -
 */
public abstract class BaseRobolectricTestRunner extends RobolectricTestRunner {

    /**
     * Register which classes may be shadowed.
     * <p/>
     * Example implementation:
     * <p/>
     * <pre>
     * protected Class[] getClassesToShadow() {
     *     return new Class[0];
     *     return new Class[] {ClassToShadow.class};
     * }
     * </pre>
     *
     * @return empty collection or classes to shadow.
     */
    protected abstract Class[] getClassesToShadow();

    /**
     * Register default shadow classes.
     * <p/>
     * Example implementation:
     * <p/>
     * <pre>
     * protected Class[] getDefaultShadowClasses() {
     *     return new Class[0];
     *     return new Class[] {ShadowClass.class};
     * }
     * </pre>
     *
     * @return empty collection or shadow classes.
     */
    protected abstract Class[] getDefaultShadowClasses();

    private ArrayList<String> classesToShadow = new ArrayList<String>();
    private String defaultShadowClasses = CustomShadowApplication.class.getName()
            + " " + ShadowBackgroundExecutor.class.getName();

    public BaseRobolectricTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);

        for (Class aClass : getClassesToShadow()) {
            classesToShadow.add(aClass.getName());
        }

        for (Class aClass : getDefaultShadowClasses()) {
            defaultShadowClasses += " " + aClass.getName();
        }

        // default exception handler for background threads don't report exceptions to test thread
        // but a test should fail if a background job failed with an exception
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, final Throwable ex) {
                Robolectric.getBackgroundScheduler().post(new Runnable() {
                    @Override
                    public void run() {
                        throw new RuntimeException(ex);
                    }
                });
            }
        });
    }

    @Override
    protected ClassLoader createRobolectricClassLoader(Setup setup, SdkConfig sdkConfig) {
        return super.createRobolectricClassLoader(new ExtraShadows(setup), sdkConfig);
    }

    @Override
    protected SdkConfig pickSdkVersion(AndroidManifest appManifest, Config config) {
        Properties properties = new Properties();

        // current robolectric supports not the latest android sdk version
        // so we must downgrade to simulate the latest supported version.
        properties.setProperty("emulateSdk", "18");

        Config.Implementation implementation = new Config.Implementation(config, Config.Implementation.fromProperties(properties));
        return super.pickSdkVersion(appManifest, implementation);
    }

    protected AndroidManifest getAppManifest(Config config) {
        Properties properties = new Properties();
        properties.setProperty("manifest", "src/main/AndroidManifest.xml");
        return super.getAppManifest(new Config.Implementation(config, Config.Implementation.fromProperties(properties)));
    }

    @Override
    protected void configureShadows(SdkEnvironment sdkEnvironment, Config config) {
        Properties properties = new Properties();
        properties.setProperty("shadows", defaultShadowClasses);
        super.configureShadows(sdkEnvironment, new Config.Implementation(config, Config.Implementation.fromProperties(properties)));
    }

    class ExtraShadows extends Setup {
        private Setup setup;

        public ExtraShadows(Setup setup) {
            this.setup = setup;
        }

        public boolean shouldInstrument(ClassInfo classInfo) {
            boolean shouldInstrument = setup.shouldInstrument(classInfo);
            return shouldInstrument
                    || classesToShadow.contains(classInfo.getName())
                    || classInfo.getName().equals(BackgroundExecutor.class.getName());
        }
    }
}
