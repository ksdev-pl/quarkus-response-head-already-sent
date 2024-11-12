package pl.ksdev.qadmin;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;

/**
 * Abstract base class for tests that require a clean, reset database state before each test.
 * <p>
 * This class utilizes Flyway to manage database migrations and provides a mechanism
 * to reset the database to a known state before each test method is executed. This
 * ensures that each test runs in isolation with a consistent database schema and
 * initial data set.
 * <p>
 * The reset process involves two steps:
 * <ol>
 *   <li>Cleaning the database (removing all objects)</li>
 *   <li>Running all migrations to rebuild the schema and insert any seed data</li>
 * </ol>
 * <p>
 * To use this class, extend it in your test classes that require a fresh database
 * state for each test. The database reset will be performed automatically before
 * each test method in the subclass.
 */
@QuarkusTest
public abstract class CleanDatabaseTest {

    @Inject
    Flyway flyway;

    @BeforeEach
    void resetDatabase() {
        flyway.clean();
        flyway.migrate();
    }
}
