/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 - Part 3: Coordination
 * Demo22 - invokeAny User Validation
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * invokeAny() is a race-to-first-success coordination primitive.
 *
 * We submit multiple Callable validators (LDAP and Database) and ask:
 * "Return the first successful result; cancel the rest."
 *
 * Behavior contract:
 *   - If at least one task returns normally: invokeAny returns that value.
 *   - If all tasks fail: invokeAny throws ExecutionException.
 *
 * This pattern appears in multi-provider auth, replicated services,
 * and failover systems where we only need one successful path.
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part3_coordination;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class Demo25_InvokeAnyUserValidation {

    public static void main(String[] args) throws InterruptedException {
        String username = "test";
        String password = "test";

        // Success scenario: at least one validator accepts the user.
        runScenario("Success Path", username, password, true);

        // Failure scenario: all validators reject -> ExecutionException.
        runScenario("All Fail Path", username, "wrong", false);

        System.out.println("Main: End of the Execution");
        System.out.println();
        System.out.println("TAKEAWAY:");
        System.out.println("  - invokeAny() returns the first successful Callable result.");
        System.out.println("  - Remaining tasks are cancelled once success is found.");
        System.out.println("  - If all tasks fail, invokeAny() throws ExecutionException.");
    }

    private static void runScenario(String label, String username, String password, boolean expectSuccess)
            throws InterruptedException {
        System.out.println("=== Demo22: " + label + " ===");

        UserValidator ldapValidator = new UserValidator("LDAP", 2, false);
        UserValidator dbValidator = new UserValidator("DataBase", 1, true);

        ValidatorTask ldapTask = new ValidatorTask(ldapValidator, username, password);
        ValidatorTask dbTask = new ValidatorTask(dbValidator, username, password);

        List<Callable<String>> taskList = new ArrayList<>();
        taskList.add(ldapTask);
        taskList.add(dbTask);

        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            String result = executor.invokeAny(taskList);
            System.out.printf("Main: Result: %s%n", result);
            if (!expectSuccess) {
                System.out.println("Main: Unexpected success in failure scenario");
            }
        } catch (ExecutionException e) {
            System.out.printf("Main: Validation failed (%s)%n", e.getClass().getSimpleName());
            if (expectSuccess) {
                System.out.println("Main: Unexpected failure in success scenario");
            }
        } finally {
            executor.shutdownNow();
            executor.awaitTermination(2, TimeUnit.SECONDS);
        }

        System.out.println();
    }

    static final class UserValidator {

        private final String name;
        private final int delaySeconds;
        private final boolean acceptsValidCredentials;

        UserValidator(String name, int delaySeconds, boolean acceptsValidCredentials) {
            this.name = name;
            this.delaySeconds = delaySeconds;
            this.acceptsValidCredentials = acceptsValidCredentials;
        }

        boolean validate(String user, String password) {
            try {
                System.out.printf("Validator %s: Validating a user during %d seconds%n", name, delaySeconds);
                TimeUnit.SECONDS.sleep(delaySeconds);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }

            boolean correctCredentials = "test".equals(user) && "test".equals(password);
            return correctCredentials && acceptsValidCredentials;
        }

        String getName() {
            return name;
        }
    }

    static final class ValidatorTask implements Callable<String> {

        private final UserValidator validator;
        private final String user;
        private final String password;

        ValidatorTask(UserValidator validator, String user, String password) {
            this.validator = validator;
            this.user = user;
            this.password = password;
        }

        @Override
        public String call() throws Exception {
            if (!validator.validate(user, password)) {
                System.out.printf("%s: The user has not been found%n", validator.getName());
                throw new Exception("Error validating user");
            }
            System.out.printf("%s: The user has been found%n", validator.getName());
            return validator.getName();
        }
    }

    private Demo25_InvokeAnyUserValidation() {}
}
