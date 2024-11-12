package pl.ksdev.qadmin.shared;

import jakarta.interceptor.InterceptorBinding;
import jakarta.transaction.Transactional;

import java.lang.annotation.*;

/**
 * A stricter version of {@link Transactional} that rolls back the transaction on any {@link Throwable},
 * including checked exceptions and errors.
 *
 * <p>While standard {@link Transactional} only rolls back on runtime exceptions by default,
 * this annotation ensures rollback happens on:
 * <ul>
 *   <li>Checked exceptions (e.g. IOException, SQLException)</li>
 *   <li>Runtime exceptions (e.g. NullPointerException)</li>
 *   <li>Errors (e.g. OutOfMemoryError)</li>
 * </ul>
 *
 * <p>Use this annotation when you want to guarantee that any failure during method execution
 * will trigger a transaction rollback, ensuring data consistency in error scenarios.
 *
 * <p>Example usage:
 * <pre>
 * {@code
 * @StrictTransactional
 * public void transferMoney(Account from, Account to, BigDecimal amount) throws InsufficientFundsException {
 *     // Any exception or error will trigger rollback
 * }
 * }
 * </pre>
 *
 * @see Transactional
 */
@Inherited
@InterceptorBinding
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(rollbackOn = Throwable.class)
public @interface StrictTransactional {}
