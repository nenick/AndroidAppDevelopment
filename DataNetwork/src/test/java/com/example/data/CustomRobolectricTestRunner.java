package com.example.data;


import org.androidannotations.api.BackgroundExecutor;
import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.SdkConfig;
import org.robolectric.annotation.Config;
import org.robolectric.bytecode.ClassInfo;
import org.robolectric.bytecode.Setup;

import java.io.File;
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
public class CustomRobolectricTestRunner extends RobolectricTestRunner {

    public CustomRobolectricTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
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
        String path = "src/main/AndroidManifest.xml";

        // android studio has different execution root for tests than pure gradle
        // so we avoid here manual effort to get them running inside android studio
        if (!new File(path).exists()) {
            path = "Data/" + path;
        }

        Properties properties = new Properties();
        properties.setProperty("manifest", path);
        return super.getAppManifest(new Config.Implementation(config, Config.Implementation.fromProperties(properties)));
    }

    /*@Override
    protected void configureShadows(SdkEnvironment sdkEnvironment, Config config) {
        Properties properties = new Properties();
        properties.setProperty("shadows", CustomShadowApplication.class.getName()
                + " " + ShadowBackgroundExecutor.class.getName());
        super.configureShadows(sdkEnvironment, new Config.Implementation(config, Config.Implementation.fromProperties(properties)));
    }*/

    class ExtraShadows extends Setup {
        private Setup setup;

        public ExtraShadows(Setup setup) {
            this.setup = setup;
        }

        public boolean shouldInstrument(ClassInfo classInfo) {
            boolean shoudInstrument = setup.shouldInstrument(classInfo);
            return shoudInstrument
                    || classInfo.getName().equals(BackgroundExecutor.class.getName());
        }
    }
}
