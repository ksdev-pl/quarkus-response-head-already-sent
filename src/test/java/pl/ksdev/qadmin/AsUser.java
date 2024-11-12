package pl.ksdev.qadmin;

import io.quarkus.test.security.TestSecurity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for simulating a regular user in test environments.
 *
 * <p>This annotation is used to configure test security for methods or classes,
 * setting up a regular user context for the annotated test(s). It leverages
 * Quarkus' TestSecurity annotation to provide a predefined user.</p>
 *
 * <p>When applied, it sets the following test security parameters:</p>
 * <ul>
 *   <li>User: user@example.com</li>
 *   <li>Role: user</li>
 * </ul>
 *
 * <p>Usage example:</p>
 * <pre>
 * {@code
 * @AsUser
 * @Test
 * public void testUserFeature() {
 *     // Test code here will run with regular user privileges
 * }
 * }
 * </pre>
 *
 * @see io.quarkus.test.security.TestSecurity
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@TestSecurity(user = "user@example.com", roles = "user")
public @interface AsUser {}
