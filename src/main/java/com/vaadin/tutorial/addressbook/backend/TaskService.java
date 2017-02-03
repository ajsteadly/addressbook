package com.vaadin.tutorial.addressbook.backend;

import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Separate Java service class.
 * Backend implementation for the address book application, with "detached entities"
 * simulating real world DAO. Typically these something that the Java EE
 * or Spring backend services provide.
 */
// Backend service class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
public class TaskService {

    // Create dummy data by randomly combining first and last names
    static String[] fnames = { "Peter", "Alice", "John", "Mike", "Olivia",
            "Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene", "Lisa",
            "Linda", "Timothy", "Daniel", "Brian", "George", "Scott",
            "Jennifer" };
    static String[] lnames = { "Smith", "Johnson", "Williams", "Jones",
            "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor",
            "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin",
            "Thompson", "Young", "King", "Robinson" };
    static String[] tasks = {"T.1.1", "T.1.2", "T.1.3", "T.1.4",
    		"T.2.1", "T.2.2", "T.2.3", 
    		"T.3.1", "T.3.2",
    		"T.4.1", "T.4.2", "T.4.3", "T.4.4", "T.4.5",
    		"T.5.1"};

    private static TaskService instance;

    public static TaskService createDemoService() {
        if (instance == null) {

            final TaskService taskService = new TaskService();

            Random r = new Random(0);
            Calendar cal = Calendar.getInstance();
            for (int i = 0; i < tasks.length; i++) {
                Task task = new Task();
                task.setFirstName(fnames[r.nextInt(fnames.length)]);
                task.setLastName(lnames[r.nextInt(fnames.length)]);
                task.setTaskName(tasks[i]);
                task.setStartDate(cal.getTime());
                task.setExpectedEndDate(new Date(System.currentTimeMillis() + System.currentTimeMillis()));
                taskService.save(task);
            }
            instance = taskService;
        }

        return instance;
    }

    private HashMap<Long, Task> contacts = new HashMap<>();
    private long nextId = 0;

    public synchronized List<Task> findAll(String stringFilter) {
        ArrayList<Task> arrayList = new ArrayList<Task>();
        for (Task contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase()
                                .contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(TaskService.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Task>() {

            @Override
            public int compare(Task o1, Task o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    public synchronized long count() {
        return contacts.size();
    }

    public synchronized void delete(Task value) {
        contacts.remove(value.getId());
    }

    public synchronized void save(Task entry) {
        if (entry.getId() == null) {
            entry.setId(nextId++);
        }
        try {
            entry = (Task) BeanUtils.cloneBean(entry);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        contacts.put(entry.getId(), entry);
    }

}
