package ca.menglish.undetected;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.service.DriverService;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableList;
import static org.openqa.selenium.remote.Browser.CHROME;

public class StealthyChromiumDriverService extends DriverService {

    /**
     * System property that defines the location of the chromedriver executable that will be used by
     * the {@link #createDefaultService() default service}.
     */
    public static final String UNDETECTED_DRIVER_EXE_PROPERTY = "webdriver.undetected.driver";

    /**
     * System property that defines the location of the log that will be written by
     * the {@link #createDefaultService() default service}.
     */
    public static final String UNDETECTED_DRIVER_LOG_PROPERTY = "webdriver.undetected.logfile";

    /**
     * Boolean system property that defines whether chromedriver should append to existing log file.
     */
    public static final String UNDETECTED_DRIVER_APPEND_LOG_PROPERTY =
            "webdriver.undetected.appendLog";

    /**
     * Boolean system property that defines whether the chromedriver executable should be started
     * with verbose logging.
     */
    public static final String UNDETECTED_DRIVER_VERBOSE_LOG_PROPERTY =
            "webdriver.undetected.verboseLogging";

    /**
     * Boolean system property that defines whether the chromedriver executable should be started
     * in silent mode.
     */
    public static final String UNDETECTED_DRIVER_SILENT_OUTPUT_PROPERTY =
            "webdriver.undetected.silentOutput";

    /**
     * System property that defines comma-separated list of remote IPv4 addresses which are
     * allowed to connect to ChromeDriver.
     */
    public static final String UNDETECTED_DRIVER_WHITELISTED_IPS_PROPERTY =
            "webdriver.undetected.whitelistedIps";

    /**
     * System property that defines whether the chromedriver executable should check for build
     * version compatibility between chromedriver and the browser.
     */
    public static final String UNDETECTED_DRIVER_DISABLE_BUILD_CHECK =
            "webdriver.undetected.disableBuildCheck";

    /**
     * @param executable  The chromedriver executable.
     * @param port        Which port to start the ChromeDriver on.
     * @param args        The arguments to the launched server.
     * @param environment The environment for the launched server.
     * @throws IOException If an I/O error occurs.
     */
    public StealthyChromiumDriverService(
            File executable,
            int port,
            List<String> args,
            Map<String, String> environment) throws IOException {
        super(executable, port, DEFAULT_TIMEOUT, args, environment);
    }

    /**
     * @param executable  The chromedriver executable.
     * @param port        Which port to start the ChromeDriver on.
     * @param timeout     Timeout waiting for driver server to start.
     * @param args        The arguments to the launched server.
     * @param environment The environment for the launched server.
     * @throws IOException If an I/O error occurs.
     */
    public StealthyChromiumDriverService(
            File executable,
            int port,
            Duration timeout,
            List<String> args,
            Map<String, String> environment) throws IOException {
        super(executable, port, timeout, args, environment);
    }

    /**
     * Configures and returns a new {@link StealthyChromiumDriverService} using the default configuration. In
     * this configuration, the service will use the chromedriver executable identified by the
     * {@link #UNDETECTED_DRIVER_EXE_PROPERTY} system property. Each service created by this method will
     * be configured to use a free port on the current system.
     *
     * @return A new ChromeDriverService using the default configuration.
     */
    public static StealthyChromiumDriverService createDefaultService() {
        return new Builder().build();
    }

    /**
     * Configures and returns a new {@link StealthyChromiumDriverService} using the supplied configuration. In
     * this configuration, the service will use the chromedriver executable identified by the
     * {@link #UNDETECTED_DRIVER_EXE_PROPERTY} system property. Each service created by this method will
     * be configured to use a free port on the current system.
     *
     * @return A new ChromeDriverService using the supplied configuration from {@link ChromeOptions}.
     */
    public static StealthyChromiumDriverService createServiceWithConfig(ChromeOptions options) {
        return new Builder()
                .withLogLevel(options.getLogLevel())
                .build();
    }

    /**
     * Builder used to configure new {@link StealthyChromiumDriverService} instances.
     */
    public static class Builder extends DriverService.Builder<
            StealthyChromiumDriverService, Builder> {

        private boolean appendLog = Boolean.getBoolean(UNDETECTED_DRIVER_APPEND_LOG_PROPERTY);
        private boolean verbose = Boolean.getBoolean(UNDETECTED_DRIVER_VERBOSE_LOG_PROPERTY);
        private boolean silent = Boolean.getBoolean(UNDETECTED_DRIVER_SILENT_OUTPUT_PROPERTY);
        private String whitelistedIps = System.getProperty(UNDETECTED_DRIVER_WHITELISTED_IPS_PROPERTY);
        private boolean disableBuildCheck = Boolean.getBoolean(UNDETECTED_DRIVER_DISABLE_BUILD_CHECK);
        private ChromeDriverLogLevel logLevel = null;

        @Override
        public int score(Capabilities capabilities) {
            int score = 0;

            if (CHROME.is(capabilities.getBrowserName())) {
                score++;
            }

            if (capabilities.getCapability(ChromeOptions.CAPABILITY) != null) {
                score++;
            }

            return score;
        }

        /**
         * Configures the driver server appending to log file.
         *
         * @param appendLog True for appending to log file, false otherwise.
         * @return A self reference.
         */
        public Builder withAppendLog(boolean appendLog) {
            this.appendLog = appendLog;
            return this;
        }

        /**
         * Configures the driver server verbosity.
         *
         * @param verbose True for verbose output, false otherwise.
         * @return A self reference.
         */
        public Builder withVerbose(boolean verbose) {
            this.verbose = verbose;
            return this;
        }

        /**
         * Configures the driver server verbosity.
         *
         * @param logLevel {@link ChromeDriverLogLevel} for desired log level output.
         * @return A self reference.
         */
        public Builder withLogLevel(ChromeDriverLogLevel logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        /**
         * Configures the driver server for silent output.
         *
         * @param silent True for silent output, false otherwise.
         * @return A self reference.
         */
        public Builder withSilent(boolean silent) {
            this.silent = silent;
            return this;
        }

        /**
         * Configures the comma-separated list of remote IPv4 addresses which are allowed to connect
         * to the driver server.
         *
         * @param whitelistedIps Comma-separated list of remote IPv4 addresses.
         * @return A self reference.
         */
        public Builder withWhitelistedIps(String whitelistedIps) {
            this.whitelistedIps = whitelistedIps;
            return this;
        }

        @Override
        protected File findDefaultExecutable() {
            return findExecutable(
                    "chromedriver", UNDETECTED_DRIVER_EXE_PROPERTY,
                    "https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver",
                    "https://chromedriver.storage.googleapis.com/index.html");
        }

        @Override
        protected List<String> createArgs() {
            if (getLogFile() == null) {
                String logFilePath = System.getProperty(UNDETECTED_DRIVER_LOG_PROPERTY);
                if (logFilePath != null) {
                    withLogFile(new File(logFilePath));
                }
            }

            if (logLevel != null) {
                withLogLevel(logLevel);
                withVerbose(false);
            }
            if (verbose) {
                withLogLevel(ChromeDriverLogLevel.ALL);
            }

            List<String> args = new ArrayList<>();
            args.add("--disable-blink-features=AutomationControlled");

            args.add(String.format("--port=%d", getPort()));
            if (getLogFile() != null) {
                args.add(String.format("--log-path=%s", getLogFile().getAbsolutePath()));
            }
            if (appendLog) {
                args.add("--append-log");
            }
            if (logLevel != null) {
                args.add(String.format("--log-level=%s", logLevel.toString().toUpperCase()));
            }
            if (silent) {
                args.add("--silent");
            }
            if (whitelistedIps != null) {
                args.add(String.format("--whitelisted-ips=%s", whitelistedIps));
            }
            if (disableBuildCheck) {
                args.add("--disable-build-check");
            }

            return unmodifiableList(args);
        }

        @Override
        protected StealthyChromiumDriverService createDriverService(
                File exe,
                int port,
                Duration timeout,
                List<String> args,
                Map<String, String> environment) {
            try {
                return new StealthyChromiumDriverService(exe, port, timeout, args, environment);
            } catch (IOException e) {
                throw new WebDriverException(e);
            }
        }
    }
}
