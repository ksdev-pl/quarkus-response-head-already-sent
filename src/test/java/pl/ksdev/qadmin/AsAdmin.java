package pl.ksdev.qadmin;

import io.quarkus.test.security.TestSecurity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for simulating an admin user in test environments.
 *
 * <p>This annotation is used to configure test security for methods or classes,
 * setting up an admin user context for the annotated test(s). It leverages
 * Quarkus' TestSecurity annotation to provide a predefined admin user.</p>
 *
 * <p>When applied, it sets the following test security parameters:</p>
 * <ul>
 *   <li>User: admin@example.com</li>
 *   <li>Role: admin</li>
 * </ul>
 *
 * <p>Usage example:</p>
 * <pre>
 * {@code
 * @AsAdmin
 * @Test
 * public void testAdminFeature() {
 *     // Test code here will run with admin privileges
 * }
 * }
 * </pre>
 *
 * @see io.quarkus.test.security.TestSecurity
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@TestSecurity(user = "admin@example.com", roles = "admin")
public @interface AsAdmin {}
