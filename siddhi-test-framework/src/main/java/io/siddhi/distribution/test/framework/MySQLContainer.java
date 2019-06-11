package io.siddhi.distribution.test.framework;

import org.testcontainers.containers.ContainerLaunchException;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class MySQLContainer extends JdbcDatabaseContainer {
    public static final String IMAGE = "mysql";
    public static final String DEFAULT_TAG = "5.7.22";

    private static final String MY_CNF_CONFIG_OVERRIDE_PARAM_NAME = "TC_MY_CNF";
    public static final Integer MYSQL_PORT = 3306;
    private String databaseName = "test-siddhi";
    private String username = "test";
    private String password = "test";
    private static final String MYSQL_ROOT_USER = "root";

    public MySQLContainer() {
        super(IMAGE + ":" + DEFAULT_TAG);
    }

    public MySQLContainer(String dockerImageName) {
        super(dockerImageName);
    }

    @Override
    protected Set<Integer> getLivenessCheckPorts() {
        return new HashSet<>(getMappedPort(MYSQL_PORT));
    }

    @Override
    protected void configure() {
//        optionallyMapResourceParameterAsVolume(MY_CNF_CONFIG_OVERRIDE_PARAM_NAME, "/etc/mysql/conf.d",
//                "mysql-default-conf");

        addExposedPort(MYSQL_PORT);
        addEnv("MYSQL_DATABASE", databaseName);
        addEnv("MYSQL_USER", username);
        if (password != null && !password.isEmpty()) {
            addEnv("MYSQL_PASSWORD", password);
            addEnv("MYSQL_ROOT_PASSWORD", password);
        } else if (MYSQL_ROOT_USER.equalsIgnoreCase(username)) {
            addEnv("MYSQL_ALLOW_EMPTY_PASSWORD", "yes");
        } else {
            throw new ContainerLaunchException("Empty password can be used only with the root user");
        }
        setStartupAttempts(3);
    }

    @Override
    public String getDriverClassName() {
        return "com.mysql.jdbc.Driver";
    }

    @Override
    public String getJdbcUrl() {
        return "jdbc:mysql://" + getContainerIpAddress() + ":" + getMappedPort(MYSQL_PORT) + "/" + databaseName;
    }

    @Override
    protected String constructUrlForConnection(String queryString) {
        String url = super.constructUrlForConnection(queryString);

        if (! url.contains("useSSL=")) {
            String separator = url.contains("?") ? "&" : "?";
            url = url + separator + "useSSL=false";
        }

        if (! url.contains("allowPublicKeyRetrieval=")) {
            url = url + "&allowPublicKeyRetrieval=true";
        }

        return url;
    }

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getTestQueryString() {
        return "SELECT 1";
    }

    public MySQLContainer withConfigurationOverride(String s) {
        parameters.put(MY_CNF_CONFIG_OVERRIDE_PARAM_NAME, s);
        return this;
    }

    @Override
    public MySQLContainer withDatabaseName(final String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    @Override
    public MySQLContainer withUsername(final String username) {
        this.username = username;
        return this;
    }

    @Override
    public MySQLContainer withPassword(final String password) {
        this.password = password;
        return this;
    }
}