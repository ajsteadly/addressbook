package com.vaadin.tutorial.addressbook.backend;

import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * A simple DTO for the address book example.
 *
 * Serializable and cloneable Java Object that are typically persisted
 * in the database and can also be easily converted to different formats like JSON.
 */

//practice comment
//4

// Backend DTO class. This is just a typical Java backend implementation
// class and nothing Vaadin specific.
public class Task implements Serializable, Cloneable {

    private Long id;
    private String firstName = "";
    private String lastName = "";
    private String taskName = "";
    private Date startDate;
    private Date expectedEndDate;

    @Override
    public Task clone() throws CloneNotSupportedException {
        try {
            return (Task) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ", taskName=" + taskName + ", startDate="
                + startDate + ", expectedEndDate=" + expectedEndDate + '}';
    }

    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTask() {
		return taskName;
	}

	public void setTask(String task) {
		this.taskName = task;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpectedEndDate() {
		return expectedEndDate;
	}

	public void setExpectedEndDate(Date expectedEndDate) {
		this.expectedEndDate = expectedEndDate;
	}

}
