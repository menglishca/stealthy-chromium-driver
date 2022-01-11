package ca.menglish.undetected;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.chromium.ChromiumDriverCommandExecutor;
import org.openqa.selenium.chromium.ChromiumNetworkConditions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.logging.EventType;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;

import java.net.URI;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Level;

public class StealthyChromiumDriver extends ChromiumDriver {

    /**
     * Creates a new StealthyChromiumDriver using the {@link StealthyChromiumDriverService#createDefaultService default}
     * server configuration.
     *
     * @see #StealthyChromiumDriver(StealthyChromiumDriverService, ChromeOptions)
     */
    public StealthyChromiumDriver() {
        this(StealthyChromiumDriverService.createDefaultService(), new ChromeOptions());
    }

    /**
     * Creates a new StealthyChromiumDriver instance. The {@code service} will be started along with the driver,
     * and shutdown upon calling {@link #quit()}.
     *
     * @param service The service to use.
     * @see RemoteWebDriver#RemoteWebDriver(org.openqa.selenium.remote.CommandExecutor, Capabilities)
     */
    public StealthyChromiumDriver(StealthyChromiumDriverService service) {
        this(service, new ChromeOptions());
    }

    /**
     * Creates a new StealthyChromiumDriver instance. The {@code capabilities} will be passed to the
     * StealthyChromiumDriver service.
     *
     * @param capabilities The capabilities required from the StealthyChromiumDriver.
     * @see #StealthyChromiumDriver(StealthyChromiumDriverService, Capabilities)
     * @deprecated Use {@link StealthyChromiumDriver (ChromeOptions)} instead.
     */
    @Deprecated
    public StealthyChromiumDriver(Capabilities capabilities) {
        this(StealthyChromiumDriverService.createDefaultService(), capabilities);
    }

    /**
     * Creates a new StealthyChromiumDriver instance with the specified options.
     *
     * @param options The options to use.
     * @see #StealthyChromiumDriver(StealthyChromiumDriverService, ChromeOptions)
     */
    public StealthyChromiumDriver(ChromeOptions options) {
        this(StealthyChromiumDriverService.createServiceWithConfig(options), options);
    }

    /**
     * Creates a new StealthyChromiumDriver instance with the specified options. The {@code service} will be
     * started along with the driver, and shutdown upon calling {@link #quit()}.
     *
     * @param service The service to use.
     * @param options The options to use.
     */
    public StealthyChromiumDriver(StealthyChromiumDriverService service, ChromeOptions options) {
        this(service, (Capabilities) options);
    }

    /**
     * Creates a new StealthyChromiumDriver instance. The {@code service} will be started along with the
     * driver, and shutdown upon calling {@link #quit()}.
     *
     * @param service      The service to use.
     * @param capabilities The capabilities required from the StealthyChromiumDriver.
     * @deprecated Use {@link StealthyChromiumDriver(StealthyChromiumDriverService, ChromeOptions)} instead.
     */
    @Deprecated
    public StealthyChromiumDriver(StealthyChromiumDriverService service, Capabilities capabilities) {
        super(new StealthyChromiumDriver.StealthyChromiumDriverCommandExecutor(service), capabilities, ChromeOptions.CAPABILITY);
        casting = new AddHasCasting().getImplementation(getCapabilities(), getExecuteMethod());
        cdp = new AddHasCdp().getImplementation(getCapabilities(), getExecuteMethod());
    }

    protected StealthyChromiumDriver(CommandExecutor commandExecutor, Capabilities capabilities, String capabilityKey) {
        super(commandExecutor, capabilities, capabilityKey);
    }

    @Override
    public Capabilities getCapabilities() {
        return super.getCapabilities();
    }

    @Override
    public void setFileDetector(FileDetector detector) {
        super.setFileDetector(detector);
    }

    @Override
    public <X> void onLogEvent(EventType<X> kind) {
        super.onLogEvent(kind);
    }

    @Override
    public void register(Predicate<URI> whenThisMatches, Supplier<Credentials> useTheseCredentials) {
        super.register(whenThisMatches, useTheseCredentials);
    }

    @Override
    public LocalStorage getLocalStorage() {
        return super.getLocalStorage();
    }

    @Override
    public SessionStorage getSessionStorage() {
        return super.getSessionStorage();
    }

    @Override
    public Location location() {
        return super.location();
    }

    @Override
    public void setLocation(Location location) {
        super.setLocation(location);
    }

    @Override
    public TouchScreen getTouch() {
        return super.getTouch();
    }

    @Override
    public ConnectionType getNetworkConnection() {
        return super.getNetworkConnection();
    }

    @Override
    public ConnectionType setNetworkConnection(ConnectionType type) {
        return super.setNetworkConnection(type);
    }

    @Override
    public void launchApp(String id) {
        super.launchApp(id);
    }

    @Override
    public Map<String, Object> executeCdpCommand(String commandName, Map<String, Object> parameters) {
        return super.executeCdpCommand(commandName, parameters);
    }

    @Override
    public Optional<DevTools> maybeGetDevTools() {
        return super.maybeGetDevTools();
    }

    @Override
    public List<Map<String, String>> getCastSinks() {
        return super.getCastSinks();
    }

    @Override
    public String getCastIssueMessage() {
        return super.getCastIssueMessage();
    }

    @Override
    public void selectCastSink(String deviceName) {
        super.selectCastSink(deviceName);
    }

    @Override
    public void startTabMirroring(String deviceName) {
        super.startTabMirroring(deviceName);
    }

    @Override
    public void stopCasting(String deviceName) {
        super.stopCasting(deviceName);
    }

    @Override
    public void setPermission(String name, String value) {
        super.setPermission(name, value);
    }

    @Override
    public ChromiumNetworkConditions getNetworkConditions() {
        return super.getNetworkConditions();
    }

    @Override
    public void setNetworkConditions(ChromiumNetworkConditions networkConditions) {
        super.setNetworkConditions(networkConditions);
    }

    @Override
    public void deleteNetworkConditions() {
        super.deleteNetworkConditions();
    }

    @Override
    public void quit() {
        super.quit();
    }

    @Override
    public void register(Supplier<Credentials> alwaysUseTheseCredentials) {

    }

    @Override
    public DevTools getDevTools() {
        return null;
    }

    @Override
    public SessionId getSessionId() {
        return super.getSessionId();
    }

    @Override
    protected void setSessionId(String opaqueKey) {
        super.setSessionId(opaqueKey);
    }

    @Override
    protected void startSession(Capabilities capabilities) {
        super.startSession(capabilities);
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return super.getErrorHandler();
    }

    @Override
    public void setErrorHandler(ErrorHandler handler) {
        super.setErrorHandler(handler);
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return super.getCommandExecutor();
    }

    @Override
    protected void setCommandExecutor(CommandExecutor executor) {
        super.setCommandExecutor(executor);
    }

    @Override
    public void get(String url) {
        super.get(url);
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public String getCurrentUrl() {
        return super.getCurrentUrl();
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return super.getScreenshotAs(outputType);
    }

    @Override
    public Pdf print(PrintOptions printOptions) throws WebDriverException {
        return super.print(printOptions);
    }

    @Override
    public WebElement findElement(By locator) {
        return super.findElement(locator);
    }

    @Override
    public List<WebElement> findElements(By locator) {
        return super.findElements(locator);
    }

    @Override
    public List<WebElement> findElements(SearchContext context, BiFunction<String, Object, CommandPayload> findCommand, By locator) {
        return super.findElements(context, findCommand, locator);
    }

    @Override
    protected WebElement findElement(String by, String using) {
        return super.findElement(by, using);
    }

    @Override
    protected List<WebElement> findElements(String by, String using) {
        return super.findElements(by, using);
    }

    @Override
    protected void setFoundBy(SearchContext context, WebElement element, String by, String using) {
        super.setFoundBy(context, element, by, using);
    }

    @Override
    public String getPageSource() {
        return super.getPageSource();
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public Set<String> getWindowHandles() {
        return super.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return super.getWindowHandle();
    }

    @Override
    public Object executeScript(String script, Object... args) {
        return super.executeScript(script, args);
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        return super.executeAsyncScript(script, args);
    }

    @Override
    public TargetLocator switchTo() {
        return super.switchTo();
    }

    @Override
    public Navigation navigate() {
        return super.navigate();
    }

    @Override
    public Options manage() {
        return super.manage();
    }

    @Override
    protected JsonToWebElementConverter getElementConverter() {
        return super.getElementConverter();
    }

    @Override
    protected void setElementConverter(JsonToWebElementConverter converter) {
        super.setElementConverter(converter);
    }

    @Override
    public void setLogLevel(Level level) {
        super.setLogLevel(level);
    }

    @Override
    protected Response execute(CommandPayload payload) {
        return super.execute(payload);
    }

    @Override
    protected Response execute(String driverCommand, Map<String, ?> parameters) {
        return super.execute(driverCommand, parameters);
    }

    @Override
    protected Response execute(String command) {
        return super.execute(command);
    }

    @Override
    protected ExecuteMethod getExecuteMethod() {
        return super.getExecuteMethod();
    }

    @Override
    public void perform(Collection<Sequence> actions) {
        super.perform(actions);
    }

    @Override
    public void resetInputState() {
        super.resetInputState();
    }

    @Override
    public Keyboard getKeyboard() {
        return super.getKeyboard();
    }

    @Override
    public Mouse getMouse() {
        return super.getMouse();
    }

    @Override
    public VirtualAuthenticator addVirtualAuthenticator(VirtualAuthenticatorOptions options) {
        return super.addVirtualAuthenticator(options);
    }

    @Override
    public void removeVirtualAuthenticator(VirtualAuthenticator authenticator) {
        super.removeVirtualAuthenticator(authenticator);
    }

    @Override
    protected void log(SessionId sessionId, String commandName, Object toLog, When when) {
        super.log(sessionId, commandName, toLog, when);
    }

    @Override
    public FileDetector getFileDetector() {
        return super.getFileDetector();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public ScriptKey pin(String script) {
        return null;
    }

    @Override
    public void unpin(ScriptKey key) {

    }

    @Override
    public Set<ScriptKey> getPinnedScripts() {
        return null;
    }

    @Override
    public Object executeScript(ScriptKey key, Object... args) {
        return null;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    private static class StealthyChromiumDriverCommandExecutor extends ChromiumDriverCommandExecutor {
        public StealthyChromiumDriverCommandExecutor(DriverService service) {
            super(service, getExtraCommands());
        }

        private static Map<String, CommandInfo> getExtraCommands() {
            return ImmutableMap.<String, CommandInfo>builder()
                    .putAll(new AddHasCasting().getAdditionalCommands())
                    .putAll(new AddHasCdp().getAdditionalCommands())
                    .build();
        }
    }
}
